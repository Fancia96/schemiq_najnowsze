package project.schemiq.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "ElementActivity")
public class ElementActivityModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    private ElementModel element;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startedAt;

    private Long time;

    public ElementActivityModel(ElementModel element, UserModel user, Date startedAt, Long time) {
        this.element = element;
        this.user = user;
        this.startedAt = startedAt;
        this.time = time;
    }

    public ElementActivityModel() {

    }

    public UserModel getUser() {
        return user;
    }

    public Long getId() {
        return id;
    }

    public ElementModel getElement() {
        return element;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    public Long getTime() {
        return time;
    }
}
