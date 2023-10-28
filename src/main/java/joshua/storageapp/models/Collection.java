package joshua.storageapp.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "collections")
public class Collection {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private Date created;

    @Column(nullable = false, length = 25)
    private String name;

    @Column(nullable = false)
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "collection")
    private List<Container> containers;

    @Column(nullable = false)
    private String imageUrl;

    // Getters
    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public List<Container> getContainers() {
       return containers;
    }

    // Setters
    public void setId(long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
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

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setContainers(List<Container> containers) {
        this.containers = containers;
    }

    // Constructors
    public Collection() {
    }

    public Collection(User user, Date created, String name, String description) {
        this.user = user;
        this.created = created;
        this.name = name;
        this.description = description;
    }

    // Methods
    public int getNumberOfItems(){
        int total = 0;
        for (Container container : this.getContainers()){
            total += container.getItems().size();
        }
        return total;
    }

    public List<Item> getItems(){
        List<Item> items = new ArrayList<Item>();
        for (Container container : this.getContainers()){
            for (Item item : container.getItems()){
                items.add(item);
            }
        }
        return items;
    }
}
