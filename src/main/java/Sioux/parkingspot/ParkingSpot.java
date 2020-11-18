package Sioux.parkingspot;

public class ParkingSpot {

    private boolean occupied;
    private int number;

    public ParkingSpot(){ }

    public ParkingSpot(boolean occupied, int number) {
        this.occupied = occupied;
        this.number = number;
    }

    public boolean isOccupied() { return occupied; }
    public int getNumber() { return number; }

    public void setOccupied(boolean occupied) { this.occupied = occupied; }
    public void setNumber(int number) { this.number = number; }

    @Override
    public String toString() {
        String occupiedString = "free";
        if(occupied) occupiedString = "taken";
        return "spot: " + number + " | " + occupiedString;
    }

}
