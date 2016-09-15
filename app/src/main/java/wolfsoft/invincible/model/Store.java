package wolfsoft.invincible.model;

import com.orm.SugarRecord;

/**
 * Created by kimbooX on 04/07/2016.
 */
public class Store extends SugarRecord {

    private String nom;
    private String adresse;
    private String service;
    private String tel;
    private String email;


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Store() {
    }

    public Store(String nom, String adresse, String service, String tel, String email) {
        this.nom = nom;
        this.adresse = adresse;
        this.service = service;
        this.tel = tel;
        this.email = email;
    }
}
