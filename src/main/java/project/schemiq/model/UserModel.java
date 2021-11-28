package project.schemiq.model;


import javax.persistence.*;
import java.util.Set;

@Entity(name = "user")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String name;
    private String password;

    @ManyToMany
    private Set<BoardModel> boardModel;

    public UserModel(Long id, String email, String firstName, String lastName, String name, String password) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.name = name;
        this.password = password;
    }

    public UserModel() {
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
