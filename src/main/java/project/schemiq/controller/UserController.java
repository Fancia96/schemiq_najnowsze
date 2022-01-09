package project.schemiq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.schemiq.Service.UserService;
import project.schemiq.model.BoardModel;
import project.schemiq.model.UserModel;

import java.util.List;
import java.util.Set;

@CrossOrigin
@Controller
public class UserController {

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private final UserService userService;

    @GetMapping("/itsok")
    public ResponseEntity<String> check(){
        return ResponseEntity.ok("ok :D");

    }

    @GetMapping("/getUsersBoards/{ID}")
    public ResponseEntity<Set<BoardModel>> getUsersBoards(@PathVariable Long ID){
        return ResponseEntity.ok(userService.getUsersBoards(userService.findUserByID(ID)));

    }

    @GetMapping("/findBoardsByUserID/{ID}")
    public ResponseEntity<Set<BoardModel>> findBoardByUserID(@PathVariable Long ID){
        return ResponseEntity.ok(userService.findBoardsByUserID(ID));
    }


    @PostMapping("/addCustomDataUsers")
    public ResponseEntity<String> addCustomDataUsers() throws Exception{
        UserModel  createThisPerson1 = userService.createUser(
                new UserModel(1l,
                        "email@testowy.pl", "wanda", "boj" ,
                        "Fancia", "1234" ));

        UserModel  createThisPerson2 = userService.createUser(
                new UserModel(2l,
                        "email@testowyszymq.pl", "szymon", "olk" ,
                        "Szym qu qu", "4321" ));

        return ResponseEntity.ok("i added test people :D");

    }

    @PostMapping("/createUser")
    public ResponseEntity<UserModel> createPerson(@RequestBody UserModel userModel){
        UserModel createThisPerson = UserService.createUser(userModel);
        return ResponseEntity.ok(createThisPerson);
    }

    @PutMapping("/editUser")
    public ResponseEntity<UserModel> editPersonByID(@RequestBody UserModel userModel){
        UserModel  editThisPerson = userService.editUserByID(userModel);

        return ResponseEntity.ok(editThisPerson);
    }


    @DeleteMapping("/deleteUser")
    public ResponseEntity<Void> deleteUserByID(@RequestBody UserModel userModel){
        userService.deleteUserByID(userModel.getId());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteUserByID/{ID}")
    public ResponseEntity<Void> deleteUserByID(@PathVariable Long ID){
        userService.deleteUserByID(ID);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/findUserByName")
    public ResponseEntity<UserModel> findPersonByName(@RequestBody UserModel userModel){
        return ResponseEntity.ok(userService.findUserByName(userModel));
    }


    @PostMapping("/findUserByEmailAndPassword")
    public ResponseEntity<UserModel> findUserByEmailAndPassword(@RequestBody UserModel userModel){
        return ResponseEntity.ok(userService.findUserByEmailAndPassword(userModel));
    }

    @GetMapping("/findUserByID/{ID}")
    public ResponseEntity<UserModel> findPersonByID(@PathVariable Long ID){
        return ResponseEntity.ok(userService.findUserByID(ID));
    }

    @GetMapping("/getAllExistingUsers")
    public ResponseEntity<List<UserModel>> getEverything() {
        return ResponseEntity.ok(userService.getEverything());
    }

}