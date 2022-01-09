package project.schemiq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.schemiq.Service.FriendshipService;
import project.schemiq.model.FriendshipModel;
import project.schemiq.model.UserModel;

import java.util.List;

@CrossOrigin
@RestController
public class FriendshipController {

    @Autowired
    private FriendshipService friendshipService;

    @PostMapping("/addCustomDataFriendship")
    public ResponseEntity<String> addCustomDataFriendshipl(){

        List<FriendshipModel> addFriends1 = friendshipService.addFriendshipBetweenIDs(
                1l,
                2l);

        List<FriendshipModel> addFriends2 = friendshipService.addFriendshipBetweenIDs(
                1l,
                3l);

//        //da info ze nie moze bo uzytkownik zabanowany lol
//        List<FriendshipModel>  addFriends2 = friendshipService.addFriendshipBetweenIDs(
//                1l,
//                3l);
//
//        List<FriendshipModel> addFriends3 = friendshipService.addFriendshipBetweenIDs(
//                4l,
//                2l);
//
//        List<FriendshipModel> addFriends4 = friendshipService.addFriendshipBetweenIDs(
//                4l,
//                1l);

        return ResponseEntity.ok("i added test friendshibs. PS BAN SHOBI :D");

    }

    //czy istnieje , szukam po nicku
    @PostMapping("/addFriendshipBetweenIDs/{personOneID}/{personTwoID}")
    public ResponseEntity<List<FriendshipModel>> addFriendshipBetweenIDs(
            @PathVariable Long personOneID, @PathVariable Long personTwoID){
        List<FriendshipModel>  createThisFriendshipModel = friendshipService.addFriendshipBetweenIDs(
                personOneID, personTwoID);

        return ResponseEntity.ok(createThisFriendshipModel);
    }

    @DeleteMapping("/deleteFriendshipBetweenIDs/{personOneID}/{personTwoID}")
    public ResponseEntity<Void> deleteFriendshipBetweenIDs(
            @PathVariable Long personOneID,@PathVariable Long personTwoID){
        friendshipService.deleteFriendshipBetweenIDs(personOneID, personTwoID);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/findIfFriendshipExistsForIDs/{personOneID}/{personTwoID}")
    public ResponseEntity<FriendshipModel> findIfFriendshipExistsForIDs(
            @PathVariable Long personOneID, @PathVariable Long personTwoID){
        return  ResponseEntity.ok(friendshipService.findIfFriendshipExistsForIDs(personOneID, personTwoID));
    }

    @GetMapping("/findFriendsForID/{personID}")
    public ResponseEntity<List<UserModel>> findFriendsForID(@PathVariable Long personID){
        return ResponseEntity.ok(friendshipService.findFriendsForID(personID));
    }

    @GetMapping("/getAllFriendships")
    public ResponseEntity<List<FriendshipModel>> getAllFriendshipModels() {
        return ResponseEntity.ok(friendshipService.getAllFriendships());
    }

}
