package Sioux.parkingspot;

import java.util.List;

public interface IParkingSpotRepository {

    List<ParkingSpot> GetAllParkingSpots();
    ParkingSpot GetParkingSpotByNumber(int number);
    ParkingSpot getAvParkingspot();
}
