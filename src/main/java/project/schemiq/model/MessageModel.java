package project.schemiq.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
public class MessageModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String msg;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne
    @JsonIgnoreProperties(value = { "listOfMessagesFrom", "listOfMessagesTo", "listOfFriends", "listOfFriendsTwo"  }, allowSetters=true)
    private UserModel personFrom;

    @ManyToOne
    @JsonIgnoreProperties(value = { "listOfMessagesFrom", "listOfMessagesTo", "listOfFriends", "listOfFriendsTwo"  }, allowSetters=true)
    private UserModel personTo;

    public MessageModel() {}

    public MessageModel(String msg){
        this.msg = msg;
        this.date = new Date();
    }

    public MessageModel(UserModel personFrom, UserModel personTo, String msg){
        this.personFrom = personFrom;
        this.personTo = personTo;
        this.msg = msg;
        this.date = new Date() ;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public UserModel getPersonFrom() {
        return personFrom;
    }

    public void setPersonFrom(UserModel personFrom) {
        this.personFrom = personFrom;
    }

    public UserModel getPersonTo() {
        return personTo;
    }

    public void setPersonTo(UserModel personTo) {
        this.personTo = personTo;
    }

}
/**
 *
 *
 {"msg": " XD"}
 *
 */
