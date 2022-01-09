package project.schemiq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import project.schemiq.Service.MessageService;
import project.schemiq.model.MessageModel;

import java.util.List;

@CrossOrigin
@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/addCustomDataMessages")
    public ResponseEntity<String> addCustomDataMessages(){

        messageService.sendMessageBetweenIDs(
                2l,
                1l, new MessageModel(
                        "Hai how are you"));

        messageService.sendMessageBetweenIDs(
                1l,
                2l, new MessageModel(
                        "O hello im good how are yooouuu"));

        messageService.sendMessageBetweenIDs(
                2l,
                1l, new MessageModel(
                        "eating and soon playing leagueoflegends"));

        messageService.sendMessageBetweenIDs(
                1l,
                2l, new MessageModel(
                        "what?"));

        messageService.sendMessageBetweenIDs(
                2l,
                1l, new MessageModel(
                        "lol"));

        messageService.sendMessageBetweenIDs(
                1l,
                2l, new MessageModel(
                        "hahahaha xD"));

        //wiecej innych
//        messageService.sendMessageBetweenIDs(
//                2l,
//                4l, new MessageModel(
//                        "Hi wona play with me"));
//        messageService.sendMessageBetweenIDs(
//                4l,
//                2l, new MessageModel(
//                        "yes sure brotha"));
//
//        messageService.sendMessageBetweenIDs(
//                4l,
//                1l, new MessageModel(
//                        "Come play"));
//        messageService.sendMessageBetweenIDs(
//                1l,
//                4l, new MessageModel(
//                        "no"));

        return ResponseEntity.ok("i added test MessageModels :D");

    }

    @PostMapping("/sendMessageBetweenIDs/{personOneID}/{personTwoID}")
    public ResponseEntity<MessageModel> sendMessageBetweenIDs(
            @PathVariable Long personOneID, @PathVariable Long personTwoID,
            @RequestBody MessageModel MessageModel){
        MessageModel sendThisMessageModel = messageService.sendMessageBetweenIDs(
                personOneID, personTwoID, MessageModel);

        return ResponseEntity.ok(sendThisMessageModel);
    }

    @DeleteMapping("/deleteMessageByID/{ID}")
    public ResponseEntity<Void> deleteMessageByID(@PathVariable Long ID){
        messageService.deleteMessageByID(ID);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteAllMessagesBetweenIDs/{personOneID}/{personTwoID}")
    public ResponseEntity<Void> deleteAllMessagesBetweenIDs(
            @PathVariable Long personOneID, @PathVariable Long personTwoID){
        messageService.deleteAllMessagesBetweenIDs(personOneID, personTwoID);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAllMessagesBetweenIDs/{personOneID}/{personTwoID}")
    public ResponseEntity<List<Object>> getAllMessagesBetweenIDs(
            @PathVariable Long personOneID, @PathVariable Long personTwoID){

        return ResponseEntity.ok( messageService.getAllMessagesBetweenIDs(personOneID,personTwoID));
    }

    @GetMapping("/findMessagesContainATextBetweenIDs/{personOneID}/{personTwoID}")
    public ResponseEntity<List<MessageModel>> findMessagesContainATextBetweenIDs(
            @PathVariable Long personOneID, @PathVariable Long personTwoID,
            @RequestBody MessageModel MessageModel){

        return ResponseEntity.ok( messageService.findMessagesContainATextBetweenIDs(personOneID, personTwoID, MessageModel));
    }

    @GetMapping("/getMessageByID/{ID}")
    public ResponseEntity<MessageModel> getMessageModelByID(
            @PathVariable Long ID){

            return ResponseEntity.ok(messageService.getMessageByID(ID));
    }

    @GetMapping("/getAllMessages")
    public ResponseEntity<List<MessageModel>> getAllMessageModels(){
        return ResponseEntity.ok( messageService.getAllMessages());
    }

}
