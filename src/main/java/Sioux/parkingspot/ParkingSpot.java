package Sioux.parkingspot;

public class ParkingSpot {

    private boolean occupied;
    private int id;

    public ParkingSpot(){ }

    public ParkingSpot(boolean occupied, int id) {
        this.occupied = occupied;
        this.id = id;
    }

    public boolean isOccupied() { return occupied; }
    public int getId() { return id; }

    public void setOccupied(boolean occupied) { this.occupied = occupied; }
    public void setId(int id) { this.id = id; }

    @Override
    public String toString() {
        String occupiedString = "free";
        if(occupied) occupiedString = "taken";
        return "spot: " + id + " | " + occupiedString;
    }

}
