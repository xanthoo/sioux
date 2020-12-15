package Sioux.parkingspot;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class ParkingSpot {

    private SimpleBooleanProperty occupied;
    private SimpleIntegerProperty number;

    public ParkingSpot(Boolean occupied, Integer number){
        this.occupied = new SimpleBooleanProperty(occupied);
        this.number = new SimpleIntegerProperty(number);
    }

   /* public ParkingSpot(boolean occupied, int number) {
        this.occupied = occupied;
        this.number = number;
    }*/

    public boolean isOccupied() { return occupied.get(); }
    public int getNumber() { return number.get(); }

    public void setOccupied(boolean occupied) { this.occupied = new SimpleBooleanProperty(occupied); }
    public void setNumber(int number) { this.number = new SimpleIntegerProperty(number); }

    @Override
    public String toString() {
        String occupiedString = "free";
        if(occupied.get()) occupiedString = "taken";
        return "spot: " + number + " | " + occupiedString;
    }

}
