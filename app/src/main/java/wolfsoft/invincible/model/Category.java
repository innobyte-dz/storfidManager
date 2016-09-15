package wolfsoft.invincible.model;

import com.orm.SugarRecord;

/**
 * Created by kimbooX on 25/06/2016.
 */
public class Category extends SugarRecord {

    private String nam;
    private String description;

    public Category() {
    }


    public Category(String nam, String description) {
        this.nam = nam;
        this.description = description;
    }


    public String getNam() {
        return nam;
    }

    public void setNam(String nam) {
        this.nam = nam;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
