package pl.kremiec.carconfigurator.objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Engines {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String decicatedToModel;
    private String typeOfFuel;
    private double engineSize;
    private int hpAmount;
    private int price;
    private String link;


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDecicatedToModel() {
        return decicatedToModel;
    }

    public void setDecicatedToModel(String decicatedToModel) {
        this.decicatedToModel = decicatedToModel;
    }

    public String getTypeOfFuel() {
        return typeOfFuel;
    }

    public void setTypeOfFuel(String typeOfFuel) {
        this.typeOfFuel = typeOfFuel;
    }

    public double getEngineSize() {
        return engineSize;
    }

    public void setEngineSize(double engineSize) {
        this.engineSize = engineSize;
    }

    public int getHpAmount() {
        return hpAmount;
    }

    public void setHpAmount(int hpAmount) {
        this.hpAmount = hpAmount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
