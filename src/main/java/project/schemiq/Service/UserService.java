package project.schemiq.Service;

import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import project.schemiq.SchemiqApplication;
import project.schemiq.model.BoardModel;
import project.schemiq.model.UserModel;
import project.schemiq.repository.BoardRepository;
import project.schemiq.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private static UserRepository userRepository;

    private static BoardRepository boardRepository;

    public static UserModel createUser(UserModel userModel) {
        return userRepository.save(userModel);
    }

    public static void deleteUser(UserModel userModel) {
        userRepository.delete(userModel);
    }

    public void deleteUserByID(Long ID){
        userRepository.deleteById(ID);
    }

    public UserModel findUserByID(Long ID){
        Optional<UserModel> user = userRepository.findById(ID);
        if(user.isPresent()){
            return user.get();
        }
        throw new ObjectNotFoundException(UserService.class, SchemiqApplication.userNotFound);
    }

    public static UserModel editUserByID(UserModel userModel) {
        Optional<UserModel> user = userRepository.findById(userModel.getId());
        if(user.isPresent()){
            UserModel new_user = user.get();
            new_user.setName(userModel.getName());
            new_user.setFirstName(userModel.getFirstName());
            new_user.setLastName(userModel.getLastName());
            new_user.setPassword(userModel.getPassword());

            return userRepository.save(new_user);
        }
        throw new ObjectNotFoundException(UserService.class, SchemiqApplication.userNotFound);
    }

    public UserModel addUserToABoard(Long userID, Long boardID) {

        Optional<UserModel> user = userRepository.findById(userID);
        if(user.isPresent()){
            Optional<BoardModel> board = boardRepository.findById(boardID);
            if(board.isPresent()){

                UserModel userModel = user.get();
                //boardRepository.save(board.get());
                userModel.getBoardModel().add(board.get());
                userRepository.save(userModel);
                return userModel;
            }
            else{
                throw new ObjectNotFoundException(UserService.class, SchemiqApplication.boardNotFound);
            }
        }
        else{
            throw new ObjectNotFoundException(UserService.class, SchemiqApplication.userNotFound);
        }
    }

    public Set<BoardModel> getUsersBoards(UserModel userModel) {
        Optional<UserModel> user = userRepository.findUserByName(userModel.getName());
        if(user.isPresent()){
            return user.get().getBoardModel();
        }
        throw new ObjectNotFoundException(UserService.class, SchemiqApplication.userNotFound);
    }

    public Set<BoardModel> findBoardsByUserID(Long userID) {
        Optional<UserModel> user = userRepository.findById(userID);
        if(user.isPresent()){
            return user.get().getBoardModel();
        }
        throw new ObjectNotFoundException(UserService.class, SchemiqApplication.userNotFound);
    }

    public UserModel findUserByEmailAndPassword(UserModel userModelFind){
        Optional<UserModel> user = userRepository.findUserByEmail(userModelFind.getEmail());
        if(user.isPresent()){

            UserModel foundUser = user.get();
            if(userModelFind.getPassword().equals(foundUser.getPassword())){
                return foundUser;
            }
        }
        throw new ObjectNotFoundException(UserService.class, SchemiqApplication.userNotFound);
    }

    public UserModel findUserByName(UserModel userModelFind){
        Optional<UserModel> user = userRepository.findUserByName(userModelFind.getName());
        if(user.isPresent()){
            return user.get();
        }
        throw new ObjectNotFoundException(UserService.class, SchemiqApplication.userNotFound);
    }

    public List<UserModel> getEverything(){
        return userRepository.findAll();
    }

    public void deleteAll(){
        userRepository.deleteAll();
    }

    public UserService(UserRepository userRepository, BoardRepository boardRepository){
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;

    }

}
