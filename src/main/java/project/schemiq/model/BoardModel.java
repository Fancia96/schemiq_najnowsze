package project.schemiq.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.*;
import java.util.List;

@Entity(name = "Board")
public class BoardModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String boardName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "boardModel")
    private List<ElementModel> elementModelList;

}
