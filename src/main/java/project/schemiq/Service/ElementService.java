package project.schemiq.Service;

import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import project.schemiq.SchemiqApplication;
import project.schemiq.model.BoardModel;
import project.schemiq.model.ElementModel;
import project.schemiq.model.UserModel;
import project.schemiq.repository.BoardRepository;
import project.schemiq.repository.ElementRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ElementService {

    private static ElementRepository elementRepository;
    private static BoardRepository boardRepository;

    public ElementModel createElement(ElementModel elementModel, Long boardID, Long userID) {

        Optional<BoardModel> boardModel = boardRepository.findById(boardID);
        if(boardModel.isPresent()){

            //elementModel.
            ElementModel elementUserModel = elementModel;
            elementUserModel.setUserChangeId(userID);
            elementUserModel.setAddChangeTime(new Date());
            elementUserModel.setBoardModel(boardModel.get());


            return elementRepository.save(elementUserModel);
        }
        else{
            throw new ObjectNotFoundException(UserService.class, SchemiqApplication.boardNotFound);
        }

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

    public ElementService(ElementRepository elementRepository, BoardRepository boardRepository){
        this.elementRepository = elementRepository;
        this.boardRepository = boardRepository;

    }

    public void deleteElementById(Long ID){
        elementRepository.deleteById(ID);
    }

    public static ElementModel updateElementByElementModel(ElementModel elementModel, Long elementID, Long userID) {
        Optional<ElementModel> element = elementRepository.findById(elementID);
        if(element.isPresent()){
            ElementModel new_element = element.get();
            new_element.setName(elementModel.getName());
            new_element.setDescription(elementModel.getDescription());
            new_element.setElementStatus(elementModel.getElementStatus());
            new_element.setUserChangeId(userID);
            new_element.setAddChangeTime(new Date());

            return elementRepository.save(new_element);
        }
        throw new ObjectNotFoundException(UserService.class, SchemiqApplication.userNotFound);
    }

    public ElementModel findOne(ElementModel elementModel) {
        Optional<ElementModel> element = elementRepository.findById(elementModel.getId());
        if(element.isPresent()){

            return element.get();
        }
        return null;
    }

}
