package wolfsoft.invincible.model;

import com.orm.SugarRecord;

/**
 * Created by kimbooX on 28/06/2016.
 */
public class FieldList extends SugarRecord {

    private String fieldName;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public FieldList() {
    }

    public FieldList(String fieldName) {
        this.fieldName = fieldName;
    }
}
