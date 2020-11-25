package Sioux.parkingspot;

import java.util.ArrayList;
import java.util.List;

public class ParkingSpotMemoryRepository implements IParkingSpotRepository{

    private final List<ParkingSpot> parkingSpots;

    public ParkingSpotMemoryRepository(){
        parkingSpots = new ArrayList<>();
        parkingSpots.add(new ParkingSpot(false, 1));
        parkingSpots.add(new ParkingSpot(true, 2));
        parkingSpots.add(new ParkingSpot(true, 3));
        parkingSpots.add(new ParkingSpot(false, 4));
        parkingSpots.add(new ParkingSpot(true, 5));
        parkingSpots.add(new ParkingSpot(false, 6));
        parkingSpots.add(new ParkingSpot(false, 7));
        parkingSpots.add(new ParkingSpot(true, 8));
        parkingSpots.add(new ParkingSpot(true, 9));
        parkingSpots.add(new ParkingSpot(true, 10));
    }

    @Override
    public List<ParkingSpot> GetAllParkingSpots() {
        return parkingSpots;
    }

    @Override
    public ParkingSpot GetParkingSpotByNumber(int number) {
        for(ParkingSpot spot : parkingSpots){
            if(spot.getNumber() == number){
                return spot;
            }
        }
        return null;
    }

    public ParkingSpot getAvParkingspot() {
        for (ParkingSpot spot : parkingSpots){
            if (!spot.isOccupied()){
                return spot;
            }
        }
        return null;
    }
}
