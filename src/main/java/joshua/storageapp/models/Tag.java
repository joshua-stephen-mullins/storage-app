package joshua.storageapp.models;

import jakarta.persistence.*;

import java.util.Set;


@Entity
@Table(name = "tags")
public class Tag {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @ManyToMany(mappedBy = "tags")
    Set<Item> items;

    // Getters
    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    // Setters
    public void setId(long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }

    // Constructors
    public Tag(){}
    public Tag(String name) {
        this.name = name;
    }

}
