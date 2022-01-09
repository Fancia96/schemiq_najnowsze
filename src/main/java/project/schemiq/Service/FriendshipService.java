package project.schemiq.Service;

import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import project.schemiq.SchemiqApplication;
import project.schemiq.model.FriendshipModel;
import project.schemiq.model.MessageModel;
import project.schemiq.model.UserModel;
import project.schemiq.repository.FriendshipRepository;
import project.schemiq.repository.MessageRepository;
import project.schemiq.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FriendshipService {
    //jak usune osobe to usuam viadomosci z nia zviazene

    private FriendshipRepository friendshipRepository;
    private UserRepository userRepository;
    private MessageRepository messageRepository;

    public List<FriendshipModel> addFriendshipBetweenIDs(Long userModelOneID, Long userModelTwoID){
        //szukam czy istnieją osoby
        Optional<UserModel> userModelOne = userRepository.findById(userModelOneID);
        Optional<UserModel> userModelTwo = userRepository.findById(userModelTwoID);

        if(userModelOne.isPresent() && userModelTwo.isPresent()){
            // szukam już czy przypadkiem nie istnieją relacje  pomiędzy tymi ID
            Optional<FriendshipModel> list = friendshipRepository.findFriendshipBetweenPeople(
                    userModelOne.get(), userModelTwo.get());

            //blokada. jesli nie istnieje to dodaje
            if(list.isEmpty()){
                //dodaje FriendshipModel w dwie strony
                FriendshipModel FriendshipModelAddOneSide = new FriendshipModel(
                        userModelOne.get(), userModelTwo.get());
                FriendshipModel FriendshipModelAddSecondSide = new FriendshipModel(
                        userModelTwo.get(), userModelOne.get());
                return friendshipRepository.saveAll(List.of(FriendshipModelAddOneSide,FriendshipModelAddSecondSide));
            }
            throw new ObjectNotFoundException(MessageService.class, "Relation exists already");

        }
        throw new ObjectNotFoundException(MessageService.class, SchemiqApplication.userNotFound);
    }

    public void deleteFriendshipBetweenIDs(Long UserModelOneID, Long UserModelTwoID){
        //usuwam dwie relacje plus wiadomosci
            //szukam czy istnieją osoby
            Optional<UserModel> userModelOne = userRepository.findById(UserModelOneID);
            Optional<UserModel> userModelTwo = userRepository.findById(UserModelTwoID);

            if(userModelOne.isPresent() && userModelTwo.isPresent()){

                // szukam czy istnieje relacja
                Optional<FriendshipModel> relationOne = friendshipRepository.findFriendshipBetweenPeople(
                        userModelOne.get(), userModelTwo.get());
                Optional<FriendshipModel> relationTwo = friendshipRepository.findFriendshipBetweenPeople(
                        userModelTwo.get(), userModelOne.get());

                //blokada. jesli nie istnieje to dodaje
                if(relationOne.isPresent() && relationTwo.isPresent()){
                    //usuwam dwa FriendshipModely szukam

                    //szukam wiadomosci tak wiem te nazwy są piekne
                    Optional<List<MessageModel>> messages = messageRepository.findAllMessagesBetweenPeople(userModelOne.get(), userModelTwo.get());
                    if(messages.isPresent()){
                        messageRepository.deleteAll(messages.get());
                        //zakladam ze przejdzie
                    }
                    friendshipRepository.deleteAll(List.of(relationOne.get(), relationTwo.get()));

                    //tutaj mam problem zeby ladnie zatwierdzic wszystkie delete za jednym razem HMMMM
                }
                else {
                    throw new ObjectNotFoundException(MessageService.class, SchemiqApplication.friendshipNotFound);
                }
            }
            else {
                throw new ObjectNotFoundException(MessageService.class, SchemiqApplication.userNotFound);
            }
    }

    public List<UserModel> findFriendsForID(Long ID){
        Optional<UserModel> userModel = userRepository.findById(ID);
        if(userModel.isPresent()){
           Optional<List<FriendshipModel>> list = friendshipRepository.findFriendsForID(userModel.get());
            if(list.isPresent()){
                List<UserModel> friends = new ArrayList<UserModel>();
                for (FriendshipModel f: list.get()) {
                    friends.add(f.getPersonTwo());
                }
                return friends;
            }
            throw new ObjectNotFoundException(MessageService.class, SchemiqApplication.friendshipNotFound);
        }
        throw new ObjectNotFoundException(MessageService.class, SchemiqApplication.userNotFound);
    }

    public FriendshipModel findIfFriendshipExistsForIDs(Long UserModelOneID, Long UserModelTwoID){
        //szukam czy istnieją osoby
        Optional<UserModel> userModelOne = userRepository.findById(UserModelOneID);
        Optional<UserModel> userModelTwo = userRepository.findById(UserModelTwoID);

        if(userModelOne.isPresent() && userModelTwo.isPresent()){
            //szukam tylko jednej FriendshipModel bo jedna wystarczy żeby potem wiedzieć
            // czy usunąć je obydwie czy mogę też dodać
            Optional<FriendshipModel> FriendshipModel = friendshipRepository.findFriendshipBetweenPeople(
                    userModelOne.get(), userModelTwo.get());
            if(FriendshipModel.isPresent()){
                return FriendshipModel.get();
            }
            return null;
        }
        else {
            throw new ObjectNotFoundException(MessageService.class, SchemiqApplication.userNotFound);
        }
    }

    public List<FriendshipModel> getAllFriendships(){
        Optional<List<FriendshipModel>> list = friendshipRepository.findAllFriendships();
        if(list.isPresent()){
            return list.get();
        }
        return new ArrayList<FriendshipModel>();
    }

    public void deleteAll(){
        friendshipRepository.deleteAll();
    }

    public FriendshipService(FriendshipRepository friendshipRepository, UserRepository userRepository, MessageRepository messageRepository){
        this.friendshipRepository = friendshipRepository;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }
}