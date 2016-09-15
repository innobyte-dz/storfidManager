package wolfsoft.invincible.model;

import com.orm.SugarRecord;

/**
 * Created by ibrahim.boukhenna on 07/08/2015.
 */
public class Action extends SugarRecord {
    public static final String IN = "I";
    public static final String OUT = "O";
    private String type;
    private Long product;
    private Long zone;

    public Action() {
    }

    public Action(String type, Long product, Long zone) {
        this.type = type;
        this.product = product;
        this.zone = zone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getProduct() {
        return product;
    }

    public void setProduct(Long product) {
        this.product = product;
    }

    public Long getZone() {
        return zone;
    }

    public void setZone(Long zone) {
        this.zone = zone;
    }

}
