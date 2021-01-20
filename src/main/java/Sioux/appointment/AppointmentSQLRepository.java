package Sioux.appointment;

import Sioux.visitor.Visitor;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AppointmentSQLRepository implements IAppointmentRepository {

    private final WebTarget serviceTarget;

    public AppointmentSQLRepository(){
        ClientConfig config = new ClientConfig();
        Client client =  ClientBuilder.newClient(config);
        URI baseURI = UriBuilder.fromUri("http://localhost:9000/Sioux/Appointments").build();
        serviceTarget = client.target(baseURI);
    }

  //  @Override
    public void CreateNewAppointment(Appointment appointment) {
        serviceTarget.request()
                .accept(MediaType.TEXT_PLAIN)
                .post(Entity.entity(appointment, MediaType.APPLICATION_JSON));
    }

    @Override
    public void DeleteAppointmentById(int appointmentId) {
        serviceTarget.path(Integer.toString(appointmentId)).request()
                .accept(MediaType.TEXT_PLAIN).delete();
    }


    @Override
    public void UpdateAppointmentById(Appointment updatedAppointment) {
        serviceTarget.path(Integer.toString(updatedAppointment.getId())).request()
                .accept(MediaType.TEXT_PLAIN)
                .put(Entity.entity(updatedAppointment, MediaType.APPLICATION_JSON));
    }

    @Override
    public List<Appointment> GetAppointmentsByDate(LocalDateTime date) {
        DateTimeFormatter formatterForSearching = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Response response =  serviceTarget.queryParam("Date",date.format(formatterForSearching)).request()
                .accept(MediaType.APPLICATION_JSON).get();
        GenericType<List<Appointment>> genericType = new GenericType<>(){};
        return response.readEntity(genericType);
    }

    @Override
    public Appointment GetAppointmentById(int appointmentId) {
        Response response =  serviceTarget.path(Integer.toString(appointmentId)).request()
                .accept(MediaType.APPLICATION_JSON).get();
        GenericType<Appointment> genericType = new GenericType<>(){};
        return response.readEntity(genericType);
    }

    public List<Appointment> getAllAppointments(){
        Response response =  serviceTarget.request().accept(MediaType.APPLICATION_JSON).get();
        GenericType<List<Appointment>> genericType = new GenericType<>(){};
        return response.readEntity(genericType);
    }

    @Override
    public List<Appointment> GetAppointmentsByCustomerId(int customerId) {
        Response response =  serviceTarget.queryParam("Customer",customerId).request()
                .accept(MediaType.APPLICATION_JSON).get();
        GenericType<List<Appointment>> genericType = new GenericType<>(){};
        return response.readEntity(genericType);
    }

    @Override
    public void SetCustomersOnAppointmentById(int eventId, List<Visitor> visitorToSet) {
        //Not needed
    }

    @Override
    public List<Appointment> searchForAppointmentString(String searchTerm){
        Response response =  serviceTarget.queryParam("Customer", searchTerm).request()
                .accept(MediaType.APPLICATION_JSON).get();
        GenericType<List<Appointment>> genericType = new GenericType<>(){};
        return response.readEntity(genericType);
    }

    @Override
    public List<Appointment> searchAppointmentStringDate(String term, LocalDateTime searchDate){
        Response response =  serviceTarget.queryParam("Customer", term).queryParam("Date", searchDate).request()
                .accept(MediaType.APPLICATION_JSON).get();
        GenericType<List<Appointment>> genericType = new GenericType<>(){};
        return response.readEntity(genericType);
    }

    public List<Appointment> searchEventsVisitorID(int id) {
        Response response =  serviceTarget.queryParam("Customer", id).request()
                .accept(MediaType.APPLICATION_JSON).get();
        GenericType<List<Appointment>> genericType = new GenericType<>(){};
        return response.readEntity(genericType);
    }
}
