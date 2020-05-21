package pl.kremiec.carconfigurator.objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class CompletedCar {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private long id;

private LocalDateTime localDateTime = LocalDateTime.now();

private String modelName;
private double basicPrice;
private short amountOfDoors;
private String modelSrc;

private double engineSize;
private short hpAmount;
private double enginePrice;
private String fuelType;
private String engineSrc;

private String colorName;
private String colorCategory;
private int colorPrice;
private String colorSrc;

    public CompletedCar() {
    }

    public static class Builder{

         String modelName;
         double basicPrice;
         short amountOfDoors;
         String modelSrc;

         double engineSize;
         short hpAmount;
         double enginePrice;
         String fuelType;
         String engineSrc;

         String colorName;
         String colorCategory;
         int colorPrice;
         String colorSrc;

         public Builder modelName(String modelName){
             this.modelName=modelName;
             return this;
         }

         public Builder basicPrice(double basicPrice){
             this.basicPrice=basicPrice;
             return this;
         }

         public Builder amountOfDoors(short amountOfDoors){
             this.amountOfDoors=amountOfDoors;
             return this;
         }

         public Builder modelSrc(String modelSrc){
             this.modelSrc=modelSrc;
             return this;
         }

        public Builder engineSize(double engineSize){
             this.engineSize=engineSize;
             return this;
        }

        public Builder hpAmount(short hpAmount){
             this.hpAmount=hpAmount;
             return this;
        }

        public Builder enginePrice(int enginePrice){
             this.enginePrice=enginePrice;
             return this;
        }

        public Builder fuelType(String fuelType){
             this.fuelType=fuelType;
             return this;
        }

        public Builder engineSrc(String engineSrc){
             this.engineSrc=engineSrc;
             return this;
        }

        public Builder colorName(String colorName){
             this.colorName=colorName;
             return this;
        }

        public Builder colorCategory(String colorCategory){
             this.colorCategory=colorCategory;
             return this;
        }

        public Builder colorPrice(int colorPrice){
             this.colorPrice=colorPrice;
             return this;
        }

        public Builder colorSrc(String colorSrc){
             this.colorSrc=colorSrc;
             return this;
        }

        public CompletedCar build(){
             return new CompletedCar(this);
        }
    }

    private CompletedCar(Builder builder){
        this.modelName=builder.modelName;
        this.basicPrice=builder.basicPrice;
        this.amountOfDoors=builder.amountOfDoors;
        this.modelSrc=builder.modelSrc;
        this.engineSize=builder.engineSize;
        this.hpAmount=builder.hpAmount;
        this.enginePrice=builder.enginePrice;
        this.fuelType=builder.fuelType;
        this.engineSrc=builder.engineSrc;
        this.colorName=builder.colorName;
        this.colorCategory=builder.colorCategory;
        this.colorPrice=builder.colorPrice;
        this.colorSrc=builder.colorSrc;
    }

    public String getModelName() {
        return modelName;
    }

    public double getBasicPrice() {
        return basicPrice;
    }

    public short getAmountOfDoors() {
        return amountOfDoors;
    }

    public String getModelSrc() {
        return modelSrc;
    }

    public double getEngineSize() {
        return engineSize;
    }

    public short getHpAmount() {
        return hpAmount;
    }

    public double getEnginePrice() {
        return enginePrice;
    }

    public String getFuelType() {
        return fuelType;
    }

    public String getEngineSrc() {
        return engineSrc;
    }

    public String getColorName() {
        return colorName;
    }

    public String getColorCategory() {
        return colorCategory;
    }

    public int getColorPrice() {
        return colorPrice;
    }

    public String getColorSrc() {
        return colorSrc;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}
