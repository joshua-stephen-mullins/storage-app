package joshua.storageapp.models;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "items")
public class Item {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "container_id")
    private Container container;

    @Column(nullable = false)
    private Date created;

    @Column(nullable = false, length = 25)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private long quantity;

    @Column(nullable = false)
    private String imageUrl;

    @ManyToMany
    @JoinTable(name = "item_tags", joinColumns = @JoinColumn(name = "items"), inverseJoinColumns = @JoinColumn(name = "tags"))
    private Set<Tag> tags;

    // Getters
    public long getId() {
        return id;
    }

    public Container getContainer() {
        return container;
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

    public long getQuantity() {
        return quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    // Setters
    public void setId(long id) {
        this.id = id;
    }

    public void setContainer(Container container) {
        this.container = container;
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

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    // Constructors
    public Item(Date created, String name, String description, long quantity) {
        this.created = created;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
    }

    public Item() {
    }

}
