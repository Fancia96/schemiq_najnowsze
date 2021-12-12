package project.schemiq.model;

import javax.persistence.*;

@Entity(name = "element")
public class ElementModel {
    @Id
    private Long id;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private ElementStatus elementStatus;

    @ManyToOne
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
}
