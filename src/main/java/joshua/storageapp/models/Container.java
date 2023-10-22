package joshua.storageapp.models;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "containers")
public class Container {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn (name = "collection_id")
    private Collection collection;

    @Column(nullable = false)
    private Date created;

    @Column(nullable = false, length = 25)
    private String name;

    @Column(nullable = false)
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "container")
    private List<Item> items;

    // Getters
    public Collection getCollection() {
        return collection;
    }
    public Date getCreated() {
        return created;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public long getId() {
        return id;
    }

    // Setters
    public void setId(long id) {
        this.id = id;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    // Constructors
    public Container(){}
    public Container(long id, Collection collection, Date created, String name, String description) {
        this.id = id;
        this.collection = collection;
        this.created = created;
        this.name = name;
        this.description = description;
    }

}