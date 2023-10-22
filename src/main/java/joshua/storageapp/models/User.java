package joshua.storageapp.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;


@Entity
@Table(name = "users")
public class User {
    
    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 25)
    private String username;

    @Column(nullable = false, length = 25)
    private String firstName;

    @Column(nullable = false, length = 25)
    private String lastName;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Date created;

    @Column(nullable = false)
    private boolean isAdmin;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Collection> collections;


    // Getters
    public long getId(){
        return id;
    }
    public String getUsername(){
        return username;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
    public Date getCreated(){
        return created;
    }
    public boolean getIsAdmin(){
        return isAdmin;
    }

    // Setters
    public void setId(long id){
        this.id = id;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setCreated(Date created){
        this.created = created;
    }
    public void setIsAdmin(boolean isAdmin){
        this.isAdmin = isAdmin;
    }

    // Contructors
    public User(){};

    public User(long id, String username, String firstName, String lastName, String email, String password, Date created, boolean isAdmin){
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.created = created;
        this.isAdmin = isAdmin;
    }
}
