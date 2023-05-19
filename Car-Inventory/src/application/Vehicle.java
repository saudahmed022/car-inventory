package application;

import java.util.Objects;

public class Vehicle {
    private String vin;
    private int modelYear;
    private String make;
    private String model;
    private String color;
    
    public Vehicle(String vin, int modelYear, String make, String model, String color) {
        this.vin = vin;
        this.modelYear = modelYear;
        this.make = make;
        this.model = model;
        this.color = color;
    }
    
    public String getVin() {
        return vin;
    }
    
    public int getModelYear() {
        return modelYear;
    }
    
    public String getMake() {
        return make;
    }
    
    public String getModel() {
        return model;
    }
    
    public String getColor() {
        return color;
    }
    
    public void setVin(String vin) {
    	this.vin = vin;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    public void setMake(String make) {
        this.make = make;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    
    public void setModelYear(int modelYear) {
        this.modelYear = modelYear;
    }
    
    @Override
    public String toString() {
        return String.format("%s %d %s %s %s", vin, modelYear, make, model, color);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Vehicle)) {
            return false;
        }
        Vehicle other = (Vehicle) obj;
        return Objects.equals(vin, other.vin) && modelYear == other.modelYear
                && Objects.equals(make, other.make) && Objects.equals(model, other.model)
                && Objects.equals(color, other.color);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(vin, modelYear, make, model, color);
    }
}
