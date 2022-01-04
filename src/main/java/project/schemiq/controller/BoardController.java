package project.schemiq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.schemiq.Service.BoardService;
import project.schemiq.Service.ElementService;
import project.schemiq.Service.UserService;
import project.schemiq.model.BoardModel;
import project.schemiq.model.UserModel;

import java.util.List;

@CrossOrigin
@Controller
public class BoardController {

    private final BoardService boardService;

    private final UserService userService;

    public BoardController(BoardService boardService, UserService userService){

        this.boardService = boardService;
        this.userService = userService;
    }

    @PostMapping("/createBoard")
    public ResponseEntity<BoardModel> createBoard(@RequestBody BoardModel boardModel){
        BoardModel createThisBoard = boardService.createBoard(boardModel);
        return ResponseEntity.ok(createThisBoard);
    }

    @PostMapping("/addCustomDataBoards")
    public ResponseEntity<String> addCustomDataBoards() throws Exception{
        BoardModel createThisBoard1 = boardService.createBoard(
                new BoardModel(1l,
                        "tablica jeden" ));

        BoardModel  createThisBoard2 = boardService.createBoard(
                new BoardModel(2l,
                        "tablica druga" ));

        userService.createBoardModelHavingUser(userService.findUserByID(1l), createThisBoard1);

        return ResponseEntity.ok("i added test boards :D");

    }

    @PostMapping("/addCustomDataBoardsConenction")
    public ResponseEntity<String> addCustomDataBoardsConenction() throws Exception{

        BoardModel  createThisBoard2 = boardService.createBoard(
                new BoardModel(4l,
                        "tablica tralalala" ));

        userService.createBoardModelHavingUser(userService.findUserByID(2l), createThisBoard2);

        return ResponseEntity.ok("i added test boards :D");

    }

//    @GetMapping("/findBoardByName")
//    public ResponseEntity<BoardModel> findBoardByBoardName(@RequestBody BoardModel boardModel){
//        return ResponseEntity.ok(boardService.findBoardByBoardName(boardModel));
//    }


    @GetMapping("/findBoardrByID/{ID}")
    public ResponseEntity<BoardModel> findBoardByID(@PathVariable Long ID){
        return ResponseEntity.ok(boardService.findBoardByID(ID));
    }

    @GetMapping("/findBoardrByUserID/{ID}")
    public ResponseEntity<BoardModel> findBoardByUserID(@PathVariable Long ID){
        return ResponseEntity.ok(boardService.findBoardByUserID(ID));
    }

    @GetMapping("/getAllExistingBoards")
    public ResponseEntity<List<BoardModel>> getEverything() {
        return ResponseEntity.ok(boardService.getEverything());
    }

}