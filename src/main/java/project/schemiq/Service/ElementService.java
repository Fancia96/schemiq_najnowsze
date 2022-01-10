package project.schemiq.Service;

import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import project.schemiq.SchemiqApplication;
import project.schemiq.model.*;
import project.schemiq.repository.BoardRepository;
import project.schemiq.repository.ElementHistoryRepository;
import project.schemiq.repository.ElementRepository;
import project.schemiq.repository.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ElementService {

    private static ElementRepository elementRepository;
    private static BoardRepository boardRepository;
    private static UserRepository userRepository;
    private static ElementHistoryRepository elementHistoryRepository;

    public ElementModel createElement(ElementModel elementModel, Long boardID, Long userID) {

        Optional<BoardModel> boardModel = boardRepository.findById(boardID);
        if(boardModel.isPresent()){

            Optional<UserModel> userModel = userRepository.findById(userID);

            if(userModel.isPresent()){
                ElementModel elementUserModel = elementModel;
                elementUserModel.setUserChangeModel(userModel.get());
                elementUserModel.setAddChangeTime(new Date());
                elementUserModel.setBoardModel(boardModel.get());

                ElementHistoryModel elementHistoryUserModel = new ElementHistoryModel(
                        elementUserModel.getName(),
                        elementUserModel.getDescription(),
                        elementUserModel.getElementStatus(),
                        elementUserModel.getAddChangeTime(),
                        getUserForHistory(elementUserModel.getUserChangeModel()), elementUserModel
                );
                elementRepository.save(elementUserModel);

                elementHistoryRepository.save(elementHistoryUserModel);
                elementUserModel.getElementHistoryModel().add(elementHistoryUserModel);

                elementRepository.save(elementUserModel);

                return elementUserModel;
            }
            throw new ObjectNotFoundException(UserService.class, SchemiqApplication.userNotFound);
        }
        else{
            throw new ObjectNotFoundException(UserService.class, SchemiqApplication.boardNotFound);
        }

    }

    private String getUserForHistory(UserModel userModel){

            return userModel.getId() + " | " +
                    userModel.getName() + " | " +
                    userModel.getFirstName() + " | " +
                    userModel.getLastName();
    }

    public List<ElementModel> findBoardElementsByID(BoardModel boardModel){
        List<ElementModel> listOfElements = elementRepository.findBoardElementsByBoardID(boardModel);

        return listOfElements;

    }

    public ElementModel findElementByID(Long ID){
        Optional<ElementModel> element = elementRepository.findById(ID);
        if(element.isPresent()){
            return element.get();
        }
        throw new ObjectNotFoundException(UserService.class, SchemiqApplication.elementNotFound);
    }

    public List<ElementModel> getEverything(){
        return elementRepository.findAll();
    }

    public void deleteAll(){
        elementRepository.deleteAll();
    }

    public ElementService(ElementRepository elementRepository, BoardRepository boardRepository, UserRepository userRepository, ElementHistoryRepository elementHistoryRepository){
        this.elementRepository = elementRepository;
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
        this.elementHistoryRepository = elementHistoryRepository;
    }

    public void deleteElementById(Long ID){
        elementHistoryRepository.deleteElementHistory(ID);
        elementRepository.deleteById(ID);
    }

    public ElementModel updateElementByElementModel(ElementModel elementModel, Long elementID, Long userID) {
        Optional<ElementModel> element = elementRepository.findById(elementID);
        if(element.isPresent()){
            Optional<UserModel> userModel = userRepository.findById(userID);

            if(userModel.isPresent()) {
                ElementModel new_element = element.get();
                new_element.setName(elementModel.getName());
                new_element.setDescription(elementModel.getDescription());
                new_element.setElementStatus(elementModel.getElementStatus());
                new_element.setUserChangeModel(userModel.get());
                new_element.setAddChangeTime(new Date());

                ElementHistoryModel elementHistoryUserModel = new ElementHistoryModel(
                        new_element.getName(),
                        new_element.getDescription(),
                        new_element.getElementStatus(),
                        new_element.getAddChangeTime(),
                        getUserForHistory(new_element.getUserChangeModel()),
                        new_element
                );

                elementHistoryRepository.save(elementHistoryUserModel);
                new_element.getElementHistoryModel().add(elementHistoryUserModel);

                return elementRepository.save(new_element);
            }

            throw new ObjectNotFoundException(UserService.class, SchemiqApplication.userNotFound);
        }
        throw new ObjectNotFoundException(UserService.class, SchemiqApplication.elementNotFound);
    }

    public ElementModel findOne(ElementModel elementModel) {
        Optional<ElementModel> element = elementRepository.findById(elementModel.getId());
        if(element.isPresent()){

            return element.get();
        }
        return null;
    }

}
