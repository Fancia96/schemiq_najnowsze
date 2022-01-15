package project.schemiq.controller;

import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.schemiq.Service.BoardService;
import project.schemiq.Service.ElementService;
import project.schemiq.model.*;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class ElementController {

    public ElementController(ElementService elementService, BoardService boardService){
        this.elementService = elementService;
        this.boardService = boardService;
    }

    private final ElementService elementService;

    private final BoardService boardService;

//    @PostMapping("/addCustomDataElements")
//    public ResponseEntity<String> addCustomDataBoards() throws Exception{
//        ElementModel createThisElement1 = elementService.createElement(
//                new ElementModel(1l,
//                        " element raz" , " opis razvbbbbbbbbbb dwa trzy ", ElementStatus.FAILED, 1l));
//
//        ElementModel createThisElement2 = elementService.createElement(
//                new ElementModel(2l,
//                        " element dwa" , " opis razvvvvvvvvv dwa trzy ", ElementStatus.IN_PROGRESS, 1l));
//
//        ElementModel createThisElement3 = elementService.createElement(
//                new ElementModel(3l,
//                        " element raz dwa " , " opis raz dwddddddddddda trzy ", ElementStatus.CLOSED, 2l));
//
//        //Long id, String name, String description, ElementStatus elementStatus, board model
//
//        return ResponseEntity.ok("i added test elements :D");
//
//    }

    @GetMapping("/findBoardElementsByBoardID/{ID}")
    public ResponseEntity<List<ElementModel>> findBoardElementsByID(@PathVariable Long ID){
        return ResponseEntity.ok(elementService.findBoardElementsByID(boardService.findBoardByID(ID)));
    }

    @GetMapping("/findElementByID/{ID}")
    public ResponseEntity<ElementModel> findPersonByID(@PathVariable Long ID){
        return ResponseEntity.ok(elementService.findElementByID(ID));
    }

    @GetMapping("/getAllExistingElements")
    public ResponseEntity<List<ElementModel>> getEverything() {
        return ResponseEntity.ok(elementService.getEverything());
    }

    @PutMapping("/updateElement/{elementID}/{userID}")
    public ResponseEntity<ElementModel> updateElementByElementModel(@RequestBody ElementModel element, @PathVariable Long elementID, @PathVariable Long userID){
        ElementModel  editThisPerson = elementService.updateElementByElementModel(element, elementID, userID);

        return ResponseEntity.ok(editThisPerson);
    }

    @PostMapping("/trackElementTime/{elementID}/{userID}")
    public ResponseEntity<ElementActivityModel> trackTime(
        @PathVariable Long elementID,
        @PathVariable Long userID,
        @RequestBody Map<String, String> payload
    ) {
        Date startedAt = Date.from(Instant.from(OffsetDateTime.parse(payload.get("startedAt"), DateTimeFormatter.ISO_DATE_TIME)));
        Long time = Long.parseLong(payload.get("time"));
        ElementActivityModel activity = elementService.createActivity(elementID, userID, startedAt, time);
        return ResponseEntity.ok(activity);
    }

    @PostMapping("/createElement/{boardID}/{userID}")
    public ResponseEntity<ElementModel> createElement(@RequestBody ElementModel element, @PathVariable Long boardID, @PathVariable Long userID){
        ElementModel  editThisPerson = elementService.createElement(element, boardID, userID);

        return ResponseEntity.ok(editThisPerson);
    }

    @DeleteMapping("/deleteElement/{elementID}")
    public ResponseEntity<Void> deleteElement(@PathVariable Long elementID){
        elementService.deleteElementById(elementID);

        return ResponseEntity.ok().build();
    }

//    @PutMapping("/updateElement")
//    public ResponseEntity<UserModel> editPersonByID(@RequestBody UserModel person){
//        UserModel  editThisPerson = userService.editUserByID(person);
//
//        return ResponseEntity.ok(editThisPerson);
//    }

    //createElement

}
