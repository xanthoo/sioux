package Sioux.visitor;

import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;

public class VisitorRepository implements IVisitorRepository{

    private final WebTarget serviceTarget;

    public VisitorRepository(){
        ClientConfig config = new ClientConfig();
        Client client =  ClientBuilder.newClient(config);
        URI baseURI = UriBuilder.fromUri("http://localhost:9000/Sioux/Customers").build();
        serviceTarget = client.target(baseURI);
    }

    @Override
    public List<Visitor> GetAllVisitors() {
        Response response = serviceTarget.request().accept(MediaType.APPLICATION_JSON).get();
        GenericType<List<Visitor>> genericType = new GenericType<>(){};
        return response.readEntity(genericType);
    }

    @Override
    public Visitor getVisitorByID(int id) {
        Response response = serviceTarget.path(Integer.toString(id)).request()
                .accept(MediaType.APPLICATION_JSON).get();
        GenericType<Visitor> genericType = new GenericType<>(){};
        return response.readEntity(genericType);
    }

    @Override
    public void updateVisitor(Visitor updatedVisitor) {
        serviceTarget.path(Integer.toString(updatedVisitor.getVisitorID())).request()
                .accept(MediaType.APPLICATION_JSON)
                .put(Entity.entity(updatedVisitor, MediaType.APPLICATION_JSON));
    }

    @Override
    public Visitor addVisitor(Visitor newVisitor) {
        Response response = serviceTarget.request()
                .accept(MediaType.APPLICATION_JSON)
                .post(Entity.entity(newVisitor, MediaType.APPLICATION_JSON));
        GenericType<Visitor> genericType = new GenericType<>(){};
        return response.readEntity(genericType);
    }

    @Override
    public Visitor deleteVisitor(int id) {
        Response response = serviceTarget.path(Integer.toString(id)).request()
                .accept(MediaType.APPLICATION_JSON).delete();
        GenericType<Visitor> genericType = new GenericType<>(){};
        return response.readEntity(genericType);
    }

    @Override
    public List<Visitor> searchVisitorByName(String name) {
        Response response =  serviceTarget.path("/name").queryParam("name",name).request()
                .accept(MediaType.APPLICATION_JSON).get();
        GenericType<List<Visitor>> genericType = new GenericType<>(){};
        return response.readEntity(genericType);
    }

    @Override
    public Visitor getVisitorByLicencePlate(String licencePlate){
        Response response =  serviceTarget.queryParam("LicensePlate",licencePlate).request()
                .accept(MediaType.APPLICATION_JSON).get();
        GenericType<Visitor> genericType = new GenericType<>(){};
        return response.readEntity(genericType);
    }
}
