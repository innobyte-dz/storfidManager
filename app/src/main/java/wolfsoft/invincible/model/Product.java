package wolfsoft.invincible.model;


import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.util.List;

/**
 */
public class Product extends SugarRecord {

    private String tag;
    private String photo;
    private String name;
    private String category;
    private String description;
    private String point;
    private Zone zone;
    private int exist = 1;
    private double mesure;
    private String mesureUnite;

    public List<Field> getFields() {
        return Field.find(Field.class, "product_id = ?", new String[]{ this.getTagId() });
    }

    public double getMesure() {
        return mesure;
    }

    public void setMesure(double mesure) {
        this.mesure = mesure;
    }

    public String getMesureUnite() {
        return mesureUnite;
    }

    public void setMesureUnite(String mesureUnite) {
        this.mesureUnite = mesureUnite;
    }

    public Product(String tag, String photo, String name, String category, String description, String point, Zone zone, int exist, double mesure, String mesureUnite) {
        this.tag = tag;
        this.photo = photo;
        this.name = name;
        this.category = category;
        this.description = description;
        this.point = point;
        this.zone = zone;
        this.exist = exist;
        this.mesure = mesure;
        this.mesureUnite = mesureUnite;
    }

    public Product() {
    }

    public Product(String tag, String photo, String name, String category, String description, String point, Zone zone) {
        this.tag = tag;
        this.photo = photo;
        this.name = name;
        this.category = category;
        this.description = description;
        this.point = point;
        this.zone = zone;
    }

    public Product(String tag, String photo, String name, String category, String description, String point) {
        this.tag = tag;
        this.photo = photo;
        this.name = name;
        this.category = category;
        this.description = description;
        this.point = point;
    }

    public String getTagId() {
        return tag;
    }

    public void setTagId(String tag) {
        this.tag = tag;
    }

    public String getPhotoPath() {
        return photo;
    }

    public void setPhotoPath(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public int getExist() {
        return exist;
    }

    public void setExist(int exist) {
        this.exist = exist;
    }

    public List<Action> getActions() {
        return Action.find(Action.class, "product = ?", new String[]{"" + this.getId()});
    }

    @Override
    public String toString() {
        return "tag :" + this.tag;
    }
}
