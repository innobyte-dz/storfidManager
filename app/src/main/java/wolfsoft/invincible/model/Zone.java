package wolfsoft.invincible.model;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by ibrahim.boukhenna on 3/9/2015.
 */
public class Zone extends SugarRecord {
    private String name;
    // @Ignore to skip a property
    private String description;
    private Repository repository;

    public Zone() {
    }

    public Zone(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Zone(String name, String description, Repository repository) {
        this.name = name;
        this.description = description;
        this.repository = repository;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Product> getProducts() {
        return Product.find(Product.class, "zone = ?", new String[]{"" + this.getId()});
    }
}