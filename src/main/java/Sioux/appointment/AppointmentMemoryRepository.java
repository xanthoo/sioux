package Sioux.appointment;

import Sioux.visitor.Visitor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

public class AppointmentMemoryRepository implements IAppointmentRepository {
    List<Appointment> appointmentList;

    public AppointmentMemoryRepository(){
        appointmentList = new ArrayList<>();
        appointmentList.add(new Appointment("Test event", 1, LocalDateTime.now(), LocalDate.now(), new Visitor(1, "Piet", "123-abc-45", "0612345678", "Needs a whiteboard.")));
        appointmentList.add(new Appointment("Test event 2", 2, LocalDateTime.now(), LocalDate.now(),new Visitor(1, "Piet", "123-abc-45", "0612345678", "Needs a whiteboard.")));
        appointmentList.add(new Appointment("Test event 2", 3, LocalDateTime.now(), LocalDate.now(), new Visitor(2, "Jan", "456-def-78", "0690123456", "Brings his assistant with him.")));
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
                a.setEnd(updatedAppointment.getEnd());
                a.setSubject(updatedAppointment.getSubject());
                a.setVisitor(updatedAppointment.getVisitor());
                appointmentList.set(appointmentList.indexOf(a), a);
            }
        }

    }

    public List<Appointment> GetAppointmentsByDate(LocalDate date) {
        List<Appointment> filteredList= new ArrayList<>();
        for (Appointment a : appointmentList){
            if (a.getStart().equals(date)){
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
            if (a.getVisitor().getVisitorID() == visitorId){
                filteredList.add(a);
            }
        }
        return filteredList;
    }

    public void SetCustomersOnAppointmentById(int eventId, List<Visitor> customerToSet) {
        //TODO
    }

    public List<Appointment> searchForAppointmentString(String searchTerm) {
        List<Appointment> filteredList= new ArrayList<>();
        for (Appointment a : appointmentList)
            if (a.getSubject().contains(searchTerm) || a.getVisitor().getName().contains(searchTerm)){
                filteredList.add(a);
            }
        return filteredList;
    }

    public List<Appointment> searchAppointmentStringDate(String term, LocalDate searchDate) {
        List<Appointment> filteredList = new ArrayList<>();
        for (Appointment a : appointmentList){
            if (a.getSubject().contains(term) || a.getVisitor().getName().contains(term)){
                if (a.getStart().equals(searchDate)){
                    filteredList.add(a);
                }
            }
        }
        return filteredList;
    }

    public List<Appointment> searchEventsVisitorID(int id) {
        return appointmentList.stream().filter(e -> e.getVisitor().getVisitorID() == id).collect(Collectors.toList());
    }
}
