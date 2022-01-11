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

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    private MessageRepository messageRepository;
    private UserRepository userRepository;
    private FriendshipRepository friendshipModelRepository;

    public MessageModel sendMessageBetweenIDs (
            Long userModelOneID, Long userModelTwoID, MessageModel messageModel) throws ObjectNotFoundException {
        //szukam obiektow osoby po id
        Optional<UserModel> userModelOne = userRepository.findById(userModelOneID);
        Optional<UserModel> userModelTwo = userRepository.findById(userModelTwoID);

        if(userModelOne.isPresent() && userModelTwo.isPresent()){
            Optional<FriendshipModel> findFriendshipModelOne = friendshipModelRepository.findFriendshipBetweenPeople(
                    userModelOne.get(), userModelTwo.get());

            Optional<FriendshipModel> findFriendshipModelTwo = friendshipModelRepository.findFriendshipBetweenPeople(
                    userModelTwo.get(), userModelOne.get());

            if(findFriendshipModelOne.isPresent() && findFriendshipModelTwo.isPresent()){
                String MessageModelEdited = correctMessage(messageModel.getMsg());

                MessageModel MessageModelAdd = new MessageModel(
                        userModelOne.get(), userModelTwo.get(), MessageModelEdited);

                return messageRepository.save(MessageModelAdd);
            }
            else{
                throw new ObjectNotFoundException(MessageService.class, SchemiqApplication.friendshipNotFound);
            }
        }
        throw new ObjectNotFoundException(MessageService.class, SchemiqApplication.userNotFound);

    }

    private String correctMessage(String msg){
        String messageModel = msg;
        for(String bad : SchemiqApplication.badWords) {
            if(msg.contains(bad)) {
                String replaceWithThis = "";
                for (int i = 0; i <= bad.length(); i++) {
                    replaceWithThis += "*";
                }
                messageModel = messageModel.replaceAll(bad, replaceWithThis);
            }
        }
        return messageModel;
    }

    public void deleteMessageByID(Long ID){
        Optional<MessageModel> messageModel = messageRepository.findById(ID);
        if(messageModel.isPresent()){
            messageRepository.deleteById(ID);
        }
        else{
            throw new ObjectNotFoundException(MessageService.class, SchemiqApplication.messageNotFound);
        }
    }

    public void deleteAllMessagesBetweenIDs(Long userModelOneID, Long userModelTwoID){
        Optional<UserModel> userModelOne = userRepository.findById(userModelOneID);
        Optional<UserModel> userModelTwo = userRepository.findById(userModelTwoID);

        if(userModelOne.isPresent() && userModelTwo.isPresent()) {
            Optional<List<MessageModel>> list = messageRepository.findAllMessagesBetweenPeople(userModelOne.get(), userModelTwo.get());
            if(list.isPresent()){
                messageRepository.deleteAll(list.get());
            }
        }
        else {
            throw new ObjectNotFoundException(MessageService.class, SchemiqApplication.userNotFound);
        }

    }

    public List<Object> getAllMessagesBetweenIDs(
            Long userModelOneID, Long userModelTwoID){
        Optional<UserModel> userModelOne = userRepository.findById(userModelOneID);
        Optional<UserModel> userModelTwo = userRepository.findById(userModelTwoID);

        if(userModelOne.isPresent() && userModelTwo.isPresent()) {
            Optional<List<Object>> list = messageRepository.findAllMessagesBetweenPeopleOrderByTime(userModelOne.get(), userModelTwo.get());
            if (list.isPresent()) {
                return list.get();
            }
        }
        throw new ObjectNotFoundException(MessageService.class, SchemiqApplication.userNotFound);
    }

    public List<MessageModel> findMessagesContainATextBetweenIDs(
            Long userModelOneID, Long userModelTwoID, MessageModel messageModel){
        Optional<UserModel> userModelOne = userRepository.findById(userModelOneID);
        Optional<UserModel> userModelTwo = userRepository.findById(userModelTwoID);

        if(userModelOne.isPresent() && userModelTwo.isPresent()) {
            Optional<List<MessageModel>> list = messageRepository.findMessagesContainATextBetweenIDs(userModelOne.get(), userModelTwo.get(), messageModel.getMsg().toUpperCase());
            if (list.isPresent()) {
                return list.get();
            }
            return null;
        }
        throw new ObjectNotFoundException(MessageService.class, SchemiqApplication.userNotFound);
    }

    public MessageModel getMessageByID(Long ID){
        Optional<MessageModel> msg = messageRepository.findById(ID);
        if(msg.isPresent()){
            return msg.get();
        }
        throw new ObjectNotFoundException(MessageService.class, SchemiqApplication.messageNotFound);
    }

    public List<MessageModel> getAllMessages(){
        return messageRepository.findAll();
    }

    public MessageService(
            FriendshipRepository friendshipModelRepository, UserRepository userRepository,
            MessageRepository messageRepository){
        this.friendshipModelRepository = friendshipModelRepository;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    public void deleteAll(){
        messageRepository.deleteAll();
    }

    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
}