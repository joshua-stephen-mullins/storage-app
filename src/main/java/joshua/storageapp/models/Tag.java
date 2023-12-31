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
    private Set<Item> items;

    // Getters
    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public Set<Item> getItems() {
        return items;
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
