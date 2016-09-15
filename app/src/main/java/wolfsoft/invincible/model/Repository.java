package wolfsoft.invincible.model;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by lamine on 5/2/2015.
 */
public class Repository extends SugarRecord {
    private String name;
    private String description;
    private String adresse;
    private String type; // central repos or subrepos

    public Repository() {

    }

    public Repository(String name, String description, String adresse, String type) {
        this.name = name;
        this.description = description;
        this.adresse = adresse;
        this.type = type;
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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Zone> getZones() {
        return Zone.find(Zone.class, "repository = ?", new String[]{"" + this.getId()});
    }
}
