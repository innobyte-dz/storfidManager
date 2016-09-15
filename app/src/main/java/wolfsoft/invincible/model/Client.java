package wolfsoft.invincible.model;

import com.orm.SugarRecord;

/**
 * Created by kimbooX on 17/06/2016.
 */
public class Client extends SugarRecord {

    private String nom;
    private String contact;
    private String adresse;


    public Client(String nom, String contact, String adresse) {
        this.nom = nom;
        this.contact = contact;
        this.adresse = adresse;
    }

    public Client() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
}
