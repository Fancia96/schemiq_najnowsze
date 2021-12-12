package project.schemiq.Service;

import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import project.schemiq.SchemiqApplication;
import project.schemiq.model.BoardModel;
import project.schemiq.model.ElementModel;
import project.schemiq.repository.ElementRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ElementService {

    private static ElementRepository elementRepository;

    public ElementModel createElement(ElementModel elementModel) {
        ElementModel elementUserModel = elementModel;
        return elementRepository.save(elementUserModel);
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

    public ElementService(ElementRepository elementRepository){
        this.elementRepository = elementRepository;

    }

    public ElementModel findOne(ElementModel elementModel) {
        Optional<ElementModel> element = elementRepository.findById(elementModel.getId());
        if(element.isPresent()){

            return element.get();
        }
        return null;
    }

}
