package project.schemiq.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity(name = "element")
public class ElementModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private ElementStatus elementStatus;

    @ManyToOne
    @JsonIgnore
    private BoardModel boardModel;

    public ElementModel(Long id, String name, String description, ElementStatus elementStatus) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.elementStatus = elementStatus;
    }

    public ElementModel(Long id, String name, String description, ElementStatus elementStatus, BoardModel boardModel) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.elementStatus = elementStatus;
        this.boardModel = boardModel;
    }
//x

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
}
