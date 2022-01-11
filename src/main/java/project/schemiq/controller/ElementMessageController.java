package project.schemiq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.schemiq.Service.ElementMessageService;
import project.schemiq.Service.MessageService;
import project.schemiq.model.ElementMessageModel;
import project.schemiq.model.MessageModel;

import java.util.List;

@CrossOrigin
@RestController
public class ElementMessageController {

    @Autowired
    private ElementMessageService elementMessageService;

    @PostMapping("/sendMessageUnderElement/{elementID}/{userID}")
    public ResponseEntity<ElementMessageModel> sendMessageUnderElement(
            @PathVariable Long elementID, @PathVariable Long userID,
            @RequestBody ElementMessageModel elementMessageModel){
        ElementMessageModel sendThisElementMessageModel = elementMessageService.sendMessageUnderElement(
                elementID, userID, elementMessageModel);

        return ResponseEntity.ok(sendThisElementMessageModel);
    }

    @DeleteMapping("/deleteElementMessageByID/{ID}")
    public ResponseEntity<Void> deleteMessageByID(@PathVariable Long ID)
    {
        elementMessageService.deleteElementMessageByID(ID);
        return ResponseEntity.ok().build();
    }


}
