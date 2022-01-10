package project.schemiq.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ElementHistoryModel {
    public ElementHistoryModel(String name, String description, ElementStatus elementStatus, Date addChangeTime, String userChangeModel,ElementModel elementModel) {
        this.name = name;
        this.description = description;
        this.elementStatus = elementStatus;
        this.addChangeTime = addChangeTime;
        this.userChangeModel = userChangeModel;
        this.elementModel = elementModel;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    private ElementModel elementModel;

    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private ElementStatus elementStatus;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date addChangeTime;

    private String userChangeModel;

    public ElementHistoryModel() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ElementStatus getElementStatus() {
        return elementStatus;
    }

    public void setElementStatus(ElementStatus elementStatus) {
        this.elementStatus = elementStatus;
    }

    public Date getAddChangeTime() {
        return addChangeTime;
    }

    public void setAddChangeTime(Date addChangeTime) {
        this.addChangeTime = addChangeTime;
    }

    public String getUserChangeModel() {
        return userChangeModel;
    }

    public void setUserChangeModel(String userChangeModel) {
        this.userChangeModel = userChangeModel;
    }

    public ElementModel getElementModel() {
        return elementModel;
    }

    public void setElementModel(ElementModel elementModel) {
        this.elementModel = elementModel;
    }
}
