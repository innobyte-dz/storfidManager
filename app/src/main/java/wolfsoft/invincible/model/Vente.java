package wolfsoft.invincible.model;

import com.orm.SugarRecord;

import java.util.Date;
import java.util.List;

/**
 * Created by kimbooX on 17/06/2016.
 */
public class Vente extends SugarRecord {

    private String tag;
    private String date;
    private double value;
    private Product produits;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Vente(String tag, String date, double value, Product produits) {
        this.tag = tag;
        this.date = date;
        this.value = value;
        this.produits = produits;
    }

    public Vente() {
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }



    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Product getProduits() {
        return produits;
    }

    public void setProduits(Product produits) {
        this.produits = produits;
    }
}
