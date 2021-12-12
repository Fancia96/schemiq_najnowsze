package project.schemiq.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Board")
public class BoardModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String boardName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "boardModel")
    private List<ElementModel> elementModelList;

    public BoardModel(Long id, String boardName) {
        this.id = id;
        this.boardName = boardName;
        this.elementModelList = new ArrayList<>();
    }

    public BoardModel(Long id, String boardName, List<ElementModel> elementModelList) {
        this.id = id;
        this.boardName = boardName;
        this.elementModelList = elementModelList;
    }


    public BoardModel() {
    }

    public Long getId() {
        return id;
    }

    public String getBoardName() {
        return boardName;
    }

    public List<ElementModel> getElementModelList() {
        return elementModelList;
    }
}
