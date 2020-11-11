package Sioux.parkingspot;

import Sioux.visitor.Visitor;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;

public class ParkingSpotRepository implements IParkingSpotRepository{

    private final WebTarget serviceTarget;

    public ParkingSpotRepository(){
        ClientConfig config = new ClientConfig();
        Client client =  ClientBuilder.newClient(config);
        URI baseURI = UriBuilder.fromUri("http://localhost:9000/Sioux/ParkingSpots").build();
        serviceTarget = client.target(baseURI);
    }

    @Override
    public List<ParkingSpot> GetAllParkingSpots() {
        Response response = serviceTarget.request().accept(MediaType.APPLICATION_JSON).get();
        GenericType<List<ParkingSpot>> genericType = new GenericType<>(){};
        return response.readEntity(genericType);
    }

    @Override
    public ParkingSpot GetParkingSpotByNumber(int number) {
        return null;
    }
}
