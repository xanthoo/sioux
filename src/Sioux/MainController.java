package Sioux;

import Sioux.appointment.AppointmentController;
import Sioux.appointment.Event;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class MainController {
    private final AppointmentController appointmentController;
    private List<Event> eventList;

    //FXML vars
    @FXML
    private ListView<Event> lvAllAppointments;
    @FXML
    private TextField tfVisitorName;
    @FXML
    private DatePicker dpAppointmentDate;
    @FXML
    private TextField tfNotes;
    @FXML
    private TextField tfSearchAppointments;

    @FXML
    private DatePicker dpDateSearch;

    public MainController(){
        eventList = new ArrayList<>();
        appointmentController = new AppointmentController();
    }

    public void initialize() {
        getAllAppointments();
        lvAllAppointments.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    private void getAllAppointments(){
        eventList = appointmentController.getEvents();
        for (Event event : eventList) {
            lvAllAppointments.getItems().add(event);
        }
    }

    public void viewSelectedAppointment(){
            Event selectedEvent = lvAllAppointments.getSelectionModel().getSelectedItem();
            appointmentController.getEventById(selectedEvent.getId());
            tfVisitorName.setText(selectedEvent.getVisitor());
            dpAppointmentDate.setValue(selectedEvent.getStart());
            tfNotes.setText(selectedEvent.getSubject());
    }

    public void saveAppointment(){
        Event selectedEvent = lvAllAppointments.getSelectionModel().getSelectedItem();
        selectedEvent.setVisitor(tfVisitorName.getText());
        selectedEvent.setSubject(tfNotes.getText());
        selectedEvent.setStart(dpAppointmentDate.getValue());
        appointmentController.editEvent(selectedEvent);
        lvAllAppointments.refresh();
    }

    public void searchForAppointment(){
        List<Event> filteredList;
        if (!tfSearchAppointments.getText().isEmpty() && dpDateSearch.getValue() == null){
            filteredList = appointmentController.searchForEventString(tfSearchAppointments.getText());
            lvAllAppointments.getItems().removeAll(lvAllAppointments.getItems());
            for (Event e : filteredList){
                lvAllAppointments.getItems().add(e);
            }
        }
        else if (tfSearchAppointments.getText().isEmpty() && dpDateSearch.getValue() != null){
            filteredList = appointmentController.searchForEventDate(dpDateSearch.getValue());
            lvAllAppointments.getItems().removeAll(lvAllAppointments.getItems());
            for (Event e: filteredList){
                lvAllAppointments.getItems().add(e);
            }
        }
        else if (!tfSearchAppointments.getText().isEmpty() && dpDateSearch.getValue() != null){
            filteredList = appointmentController.searchEventStringDate(tfSearchAppointments.getText(), dpDateSearch.getValue());
            lvAllAppointments.getItems().removeAll(lvAllAppointments.getItems());
            for (Event e: filteredList){
                lvAllAppointments.getItems().add(e);
            }
        }
        else{
            lvAllAppointments.getItems().removeAll(lvAllAppointments.getItems());
            getAllAppointments();
        }
    }
}
