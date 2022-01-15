package project.schemiq.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "element")
public class ElementModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private ElementStatus elementStatus;

    @OneToMany(mappedBy = "elementModel")
    private Set<ElementHistoryModel> elementHistoryModel = new HashSet<ElementHistoryModel>();

    @OneToMany(mappedBy = "elementModel")
    private Set<ElementMessageModel> elementMessageModel = new HashSet<ElementMessageModel>();

    @OneToMany(mappedBy = "element")
    private Set<ElementActivityModel> activity = new HashSet<ElementActivityModel>();

    @ManyToOne
    @JsonIgnore
    private BoardModel boardModel;

    @Temporal(TemporalType.TIMESTAMP)
    private Date addChangeTime;

    @ManyToOne
    private UserModel userChangeModel;

    public ElementModel(Long id, String name, String description, ElementStatus elementStatus, UserModel userChangeModel) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.elementStatus = elementStatus;
        this.addChangeTime = new Date() ;
        this.userChangeModel = userChangeModel;
    }

    public ElementModel(Long id, String name, String description, ElementStatus elementStatus, BoardModel boardModel, UserModel userChangeModel) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.elementStatus = elementStatus;
        this.boardModel = boardModel;
        this.userChangeModel = userChangeModel;

    }
//x

    public Date getAddChangeTime() {
        return addChangeTime;
    }

    public void setAddChangeTime(Date addChangeTime) {
        this.addChangeTime = addChangeTime;
    }

    public UserModel getUserChangeModel() {
        return userChangeModel;
    }

    public void setUserChangeModel(UserModel userChangeId) {
        this.userChangeModel = userChangeId;
    }

    public ElementModel() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ElementStatus getElementStatus() {
        return elementStatus;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setElementStatus(ElementStatus elementStatus) {
        this.elementStatus = elementStatus;
    }

    public BoardModel getBoardModel() {
        return boardModel;
    }

    public void setBoardModel(BoardModel boardModel) {
        this.boardModel = boardModel;
    }

    public Set<ElementHistoryModel> getElementHistoryModel() {
        return elementHistoryModel;
    }

    public void setElementHistoryModel(Set<ElementHistoryModel> elementHistoryModel) {
        this.elementHistoryModel = elementHistoryModel;
    }

    public Set<ElementMessageModel> getElementMessageModel() {
        return elementMessageModel;
    }

    public Set<ElementActivityModel> getActivity() {
        return activity;
    }
}
