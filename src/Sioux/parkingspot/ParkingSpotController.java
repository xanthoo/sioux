package Sioux.parkingspot;

import java.util.List;

public class ParkingSpotController {

    IParkingSpotRepository repository;

    public ParkingSpotController(IParkingSpotRepository repository){
        this.repository = repository;
    }

    public List<ParkingSpot> GetAllParkingSpots(){
        return repository.GetAllParkingSpots();
    }

    public ParkingSpot GetParkingSpotByNumber(int number){
        return repository.GetParkingSpotByNumber(number);
    }
}
