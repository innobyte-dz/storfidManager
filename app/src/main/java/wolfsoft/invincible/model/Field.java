package wolfsoft.invincible.model;

import com.orm.SugarRecord;

/**
 * Created by kimbooX on 28/06/2016.
 */
public class Field extends SugarRecord {

    private String product_id;
    private String fieldName;
    private String fieldValue;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public Field() {
    }

    public Field(String product_id, String fieldName, String fieldValue) {
        this.product_id = product_id;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
