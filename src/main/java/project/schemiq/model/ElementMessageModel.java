package project.schemiq.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ElementMessageModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String msg;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private String userMessageModel;

    @JsonIgnore
    @ManyToOne
    private ElementModel elementModel;

    public ElementMessageModel() {}

    public ElementMessageModel(String msg){
        this.msg = msg;
        this.date = new Date();
    }

    public ElementMessageModel(String userMessageModel, String msg){
        this.userMessageModel = userMessageModel;
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

    public String getUserMessageModel() {
        return userMessageModel;
    }

    public void setUserMessageModel(String userMessageModel) {
        this.userMessageModel = userMessageModel;
    }

    public ElementModel getElementModel() {
        return elementModel;
    }

    public void setElementModel(ElementModel elementModel) {
        this.elementModel = elementModel;
    }
}
/**
 *
 *
 {"msg": " XD"}
 *
 */
