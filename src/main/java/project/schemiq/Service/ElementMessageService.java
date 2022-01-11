package project.schemiq.Service;

import org.hibernate.ObjectNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import project.schemiq.SchemiqApplication;
import project.schemiq.model.*;
import project.schemiq.repository.*;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Service
public class ElementMessageService {
    private ElementMessageRepository elementMessageRepository;
    private UserRepository userRepository;
    private ElementRepository elementRepository;

    public void deleteElementMessageByID(Long ID){
        Optional<ElementMessageModel> messageModel = elementMessageRepository.findById(ID);
        if(messageModel.isPresent()){
            elementMessageRepository.deleteById(ID);
        }
        else{
            throw new ObjectNotFoundException(ElementMessageService.class, SchemiqApplication.messageNotFound);
        }
    }

    public ElementMessageModel sendMessageUnderElement (Long elementID, Long userID, ElementMessageModel elementMessageModel) throws ObjectNotFoundException {
        Optional<UserModel> user = userRepository.findById(userID);
        Optional<ElementModel> element = elementRepository.findById(elementID);

        if(user.isPresent() && element.isPresent()){
            UserModel u = user.get();
            ElementModel e = element.get();

            ElementMessageModel em = new ElementMessageModel(elementMessageModel.getMsg());
            em.setElementModel(e);
            em.setUserMessageModel(u.getName());
            return elementMessageRepository.save(em);
        }
        throw new ObjectNotFoundException(MessageService.class, SchemiqApplication.userNotFound);

    }

    public List<ElementMessageModel> getAllElementMessages(){
        return elementMessageRepository.findAll();
    }

    public ElementMessageService(
            ElementMessageRepository elementMessageRepository, UserRepository userRepository, ElementRepository elementRepository){
        this.elementMessageRepository = elementMessageRepository;
        this.userRepository = userRepository;
        this.elementRepository = elementRepository;
    }


}