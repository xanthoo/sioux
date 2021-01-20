package Sioux.appointment;

import Sioux.visitor.Visitor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AppointmentMemoryRepository implements IAppointmentRepository {
    List<Appointment> appointmentList;
    DateTimeFormatter formatter;

    public AppointmentMemoryRepository(){
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        appointmentList = new ArrayList<>();
        appointmentList.add(new Appointment("Test event", 0, LocalDateTime.now(), new Visitor(1, "Piet", "123-abc-45", "0612345678", "Needs a whiteboard.")));
        appointmentList.add(new Appointment("Test event 2", 1, LocalDateTime.now(),new Visitor(1, "Piet", "123-abc-45", "0612345678", "Needs a whiteboard.")));
        appointmentList.add(new Appointment("Test event 2", 2, LocalDateTime.now(), new Visitor(2, "Jan", "456-def-78", "0690123456", "Brings his assistant with him.")));
    }
    public void CreateNewAppointment(Appointment appointment) {
    appointmentList.add(appointment);
    }

    public void DeleteAppointmentById(int appointmentId) {
        appointmentList.removeIf(a -> a.getId() == appointmentId);
    }

    public void UpdateAppointmentById(Appointment updatedAppointment) {
        for (Appointment a : appointmentList){
            if (a.getId() == updatedAppointment.getId()){
                a.setStart(updatedAppointment.getStart());
                a.setSubject(updatedAppointment.getSubject());
                a.setCustomer(updatedAppointment.getCustomer());
                appointmentList.set(appointmentList.indexOf(a), a);
            }
        }

    }

    public List<Appointment> GetAppointmentsByDate(LocalDateTime date) {
        List<Appointment> filteredList= new ArrayList<>();
        for (Appointment a : appointmentList){
            if (a.getStart().format(formatter).equals(date.format(formatter))){
                filteredList.add(a);
            }
        }
        return filteredList;

    }

    public Appointment GetAppointmentById(int appointmentId) {
        for (Appointment a : appointmentList) {
            if (a.getId() == appointmentId)
                return a;
        }
        return null;

    }

    public List<Appointment> getAllAppointments() {
        return appointmentList;
    }

    public List<Appointment> GetAppointmentsByCustomerId(int visitorId) {
        List<Appointment> filteredList= new ArrayList<>();
        for (Appointment a : appointmentList){
            if (a.getCustomer().getVisitorID() == visitorId){
                filteredList.add(a);
            }
        }
        return filteredList;
    }

    public void SetCustomersOnAppointmentById(int eventId, List<Visitor> visitorToSet) {
        //TODO
    }

    public List<Appointment> searchForAppointmentString(String searchTerm) {
        List<Appointment> filteredList= new ArrayList<>();
        for (Appointment a : appointmentList)
            if (a.getSubject().contains(searchTerm) || a.getCustomer().getName().contains(searchTerm)){
                filteredList.add(a);
            }
        return filteredList;
    }

    public List<Appointment> searchAppointmentStringDate(String term, LocalDateTime searchDate) {
        List<Appointment> filteredList = new ArrayList<>();
        for (Appointment a : appointmentList){
            if (a.getSubject().contains(term) || a.getCustomer().getName().contains(term)){
                if (a.getStart().equals(searchDate)){
                    filteredList.add(a);
                }
            }
        }
        return filteredList;
    }

    public List<Appointment> searchEventsVisitorID(int id) {
        return appointmentList.stream().filter(e -> e.getCustomer().getVisitorID() == id).collect(Collectors.toList());
    }
}
