package Sioux;

import Sioux.appointment.Appointment;
import Sioux.appointment.AppointmentController;
import Sioux.appointment.AppointmentNEED_FIX;
import Sioux.appointment.AppointmentMemoryRepository;
import Sioux.visitor.Visitor;
import Sioux.visitor.VisitorController;
import Sioux.visitor.VisitorMemoryRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MainController {
    private final AppointmentController appointmentController;
    private final VisitorController visitorController;
    private List<Appointment> appointmentList;
    private List<Visitor> visitorList;
    Visitor selectedVisitor;

    //FXML vars
    @FXML
    private ListView<Appointment> lvAllAppointments;
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
    private ListView<Visitor> lvAllVisitors;
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
    @FXML
    ListView<Appointment> lvVisitorAppointments;


    public MainController(){
        appointmentList = new ArrayList<>();
        appointmentController = new AppointmentController(new AppointmentMemoryRepository());
        visitorController = new VisitorController(new VisitorMemoryRepository());
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
        appointmentList = appointmentController.getAppointments();
        for (Appointment appointment : appointmentList) {
            lvAllAppointments.getItems().add(appointment);
        }
    }

    public void viewSelectedAppointment(){
        Appointment selectedAppointment = lvAllAppointments.getSelectionModel().getSelectedItem();
            appointmentController.getAppointmentById(selectedAppointment.getId());
            //tfVisitorName.setText(selectedAppointment.getVisitor());
            dpAppointmentDate.setValue(selectedAppointment.getStart());
            tfNotes.setText(selectedAppointment.getSubject());
           /* Event selectedEvent = lvAllAppointments.getSelectionModel().getSelectedItem();
            appointmentController.getEventById(selectedEvent.getId());
            tfVisitorName.setText(selectedEvent.getVisitor().getName());
            dpAppointmentDate.setValue(selectedEvent.getStart());
            tfNotes.setText(selectedEvent.getSubject());*/
    }

    public void saveAppointment(){
        Appointment selectedAppointment = lvAllAppointments.getSelectionModel().getSelectedItem();
        //selectedAppointment.setVisitor(tfVisitorName.getText());
        selectedAppointment.setSubject(tfNotes.getText());
        selectedAppointment.setStart(LocalDate.from(dpAppointmentDate.getValue()));
        appointmentController.updateAppointment(selectedAppointment);
        /*Event selectedEvent = lvAllAppointments.getSelectionModel().getSelectedItem();
        selectedEvent.setVisitor(visitorController.getVisitorByID(selectedEvent.getVisitor().getVisitorID()));
        selectedEvent.setSubject(tfNotes.getText());
        selectedEvent.setStart(dpAppointmentDate.getValue());
        appointmentController.editEvent(selectedEvent); */
        lvAllAppointments.refresh();
    }

    public void searchForAppointment(){
        List<Appointment> filteredList;
        if (!tfSearchAppointments.getText().isEmpty() && dpDateSearch.getValue() == null){
            filteredList = appointmentController.searchForAppointmentString(tfSearchAppointments.getText());
            lvAllAppointments.getItems().removeAll(lvAllAppointments.getItems());
            for (Appointment e : filteredList){
                lvAllAppointments.getItems().add(e);
            }
        }
        else if (tfSearchAppointments.getText().isEmpty() && dpDateSearch.getValue() != null){
            filteredList = appointmentController.searchForAppointmentByDate(dpDateSearch.getValue());
            lvAllAppointments.getItems().removeAll(lvAllAppointments.getItems());
            for (Appointment e: filteredList){
                lvAllAppointments.getItems().add(e);
            }
        }
        else if (!tfSearchAppointments.getText().isEmpty() && dpDateSearch.getValue() != null){
            filteredList = appointmentController.searchAppointmentStringDate(tfSearchAppointments.getText(), dpDateSearch.getValue());
            lvAllAppointments.getItems().removeAll(lvAllAppointments.getItems());
            for (Appointment e: filteredList){
                lvAllAppointments.getItems().add(e);
            }
        }
        else{
            lvAllAppointments.getItems().removeAll(lvAllAppointments.getItems());
            getAllAppointments();
        }
    }

    public List<Appointment> searchAppointmentVisitorID(int id){
       return appointmentController.searchEventsVisitorID(id);
    }

    private void getAllVisitors(){
        visitorList = visitorController.getVisitorList();
        for (Visitor visitor : visitorList) {
            lvAllVisitors.getItems().add(visitor);
        }
    }
    public void viewSelectedVisitor(){
        selectedVisitor = lvAllVisitors.getSelectionModel().getSelectedItem();
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
            lvVisitorAppointments.getItems().removeAll(lvAllAppointments.getItems());
            lvVisitorAppointments.getItems().addAll(searchAppointmentVisitorID(selectedVisitor.getVisitorID()));

        }
    }
  /*  public void saveVisitorDetails(){
        visitorController.updateVisitor(new Visitor(selectedVisitor.getVisitorID(), tfNameVisitor.getText(), tfLicenseplateNumber.getText(), tfPhoneNumber.getText(), tfVisitorNotes.getText()));
        lvAllVisitors.refresh();
    } */

    public void EditVisitor(){
        if(selectedVisitor != null){
            try {
                //Creating the loader
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditVisitorView.fxml"));
                Parent root1 = fxmlLoader.load();
                //Adding the controller to the view
                EditVisitorController editVisitorController = fxmlLoader.getController();
                //Initializing the controller
                editVisitorController.initData(selectedVisitor, visitorController);
                //Making the stage
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.DECORATED);
                stage.setTitle("Edit visitor");
                stage.setScene(new Scene(root1));
                stage.showAndWait();
                lvAllVisitors.refresh();
                viewSelectedVisitor();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            //No visitor selected
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("There is no visitor selected.");
            alert.setContentText("Please select a visitor.");
            alert.showAndWait();
        }
    }
    public void addVisitor(){
        String newVisitorName = tfNameVisitor.getText();
        if(!newVisitorName.equals("")){
            lvAllVisitors.getItems().add(visitorController.addVisitor(new Visitor(visitorList.toArray().length+1, newVisitorName, tfLicenseplateNumber.getText(), tfPhoneNumber.getText(), tfVisitorNotes.getText())));
            lvAllVisitors.refresh();
        }
    }
    public void deleteVisitor(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete the user?");
        Optional <ButtonType> action = alert.showAndWait();
            if(action.get() == ButtonType.OK) {
                Visitor visitorToDelete = visitorController.deleteVisitor(selectedVisitor.getVisitorID());
                if (visitorToDelete != null) {
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
        lvVisitorAppointments.getItems().removeAll(lvAllAppointments.getItems());
    }
}
