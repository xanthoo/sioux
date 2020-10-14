package Sioux;

import Sioux.appointment.AppointmentController;
import Sioux.appointment.Event;
import Sioux.visitor.Visitor;
import Sioux.visitor.VisitorController;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    private final AppointmentController appointmentController;
    private final VisitorController visitorController;
    private List<Event> eventList;
    private List<Visitor> visitorList;
    Visitor selectedVisitor;

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

    //FXML vars visitor page
    @FXML
    private ListView lvAllVisitors;
    @FXML
    private TextField tfNameVisitor;
    @FXML
    private  TextField tfLicenseplateNumber;
    @FXML
    private TextField tfVisitorNotes;
    @FXML
    private TextField tfPhoneNumber;
    @FXML
    private TextField tfSearchVisitor;
    @FXML
    Button btnSaveVisitor;
    @FXML
    Button btnAddVisitor;

    public MainController(){
        eventList = new ArrayList<>();
        appointmentController = new AppointmentController();
        visitorController = new VisitorController();
    }

    public void initialize() {
        getAllAppointments();
        lvAllAppointments.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        //Visitor page
        getAllVisitors();
        lvAllVisitors.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        btnSaveVisitor.setDisable(true);
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

    private void getAllVisitors(){
        visitorList = visitorController.getVisitorList();
        for (Visitor visitor : visitorList) {
            lvAllVisitors.getItems().add(visitor);
        }
    }
    public void viewSelectedVisitor(){
        selectedVisitor = (Visitor) lvAllVisitors.getSelectionModel().getSelectedItem();
        if(selectedVisitor == null){
            btnSaveVisitor.setDisable(true);
            btnAddVisitor.setDisable(false);
            tfNameVisitor.setText("");
            tfLicenseplateNumber.setText("");
            tfVisitorNotes.setText("");
            tfPhoneNumber.setText("");
        }
        else {
            btnSaveVisitor.setDisable(false);
            btnAddVisitor.setDisable(true);
            visitorController.getVisitorByID(selectedVisitor.getVisitorID());
            tfNameVisitor.setText(selectedVisitor.getName());
            tfLicenseplateNumber.setText(selectedVisitor.getLicensePlateNumber());
            tfVisitorNotes.setText(selectedVisitor.getNotes());
            tfPhoneNumber.setText(selectedVisitor.getPhoneNumber());
        }
    }
    public void saveVisitorDetails(){
        visitorController.updateVisitor(new Visitor(selectedVisitor.getVisitorID(), tfNameVisitor.getText(), tfLicenseplateNumber.getText(), tfPhoneNumber.getText(), tfVisitorNotes.getText()));
        lvAllVisitors.refresh();
    }
    public void addVisitor(){
        String newVisitorName = tfNameVisitor.getText();
        if(!newVisitorName.equals("")){
            lvAllVisitors.getItems().add(visitorController.addVisitor(new Visitor(visitorList.toArray().length+1, newVisitorName, tfLicenseplateNumber.getText(), tfPhoneNumber.getText(), tfVisitorNotes.getText())));
            lvAllVisitors.refresh();
        }
    }
    public void deleteVisitor(){
        int opt = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this user?", "Delete", JOptionPane.YES_NO_OPTION);
        if(opt == 0){
            Visitor visitorToDelete = visitorController.deleteVisitor(selectedVisitor.getVisitorID());
            if(visitorToDelete != null){
                lvAllVisitors.getItems().remove(visitorToDelete);
                visitorController.getVisitorList().remove(visitorToDelete);
                clearInfo();
            }
            lvAllVisitors.refresh();
        }

    }
    public void searchVisitorByName(){
        lvAllVisitors.getItems().clear();
        clearInfo();
        List<Visitor> foundVisitors = visitorController.searchVisitorByName(tfSearchVisitor.getText());
        if(foundVisitors.toArray().length != 0){
            for (Visitor visitor : foundVisitors) {
                lvAllVisitors.getItems().add(visitor);
            }
        }
        else if(tfSearchVisitor.getText().equals("")) {
            getAllVisitors();
        }
        lvAllVisitors.refresh();
    }
    public void clearInfo(){
        btnSaveVisitor.setDisable(true);
        btnAddVisitor.setDisable(false);
        selectedVisitor =null;
        tfNameVisitor.setText("");
        tfLicenseplateNumber.setText("");
        tfVisitorNotes.setText("");
        tfPhoneNumber.setText("");
    }
}
