package project.schemiq.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
public class FriendshipModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = { "listOfFriends", "listOfFriendsTwo","listOfMessagesFrom", "listOfMessagesTo" }, allowSetters=true)
    private UserModel personOne;

    @ManyToOne
    @JsonIgnoreProperties(value = { "listOfFriends", "listOfFriendsTwo","listOfMessagesFrom", "listOfMessagesTo" }, allowSetters=true)
    private UserModel personTwo;

    public FriendshipModel() {
    }

    public FriendshipModel(UserModel personOne, UserModel personTwo) {
        this.personOne = personOne;
        this.personTwo = personTwo;
    }

    public UserModel getPersonOne() {
        return personOne;
    }

    public void setPersonOne(UserModel personOne) {
        this.personOne = personOne;
    }

    public UserModel getPersonTwo() {
        return personTwo;
    }

    public void setPersonTwo(UserModel personTwo) {
        this.personTwo = personTwo;
    }
}
/**
 *

 *
 */