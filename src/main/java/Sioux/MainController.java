package Sioux;

import Sioux.appointment.Appointment;
import Sioux.appointment.AppointmentController;
import Sioux.appointment.AppointmentMemoryRepository;
import Sioux.parkingspot.ParkingSpot;
import Sioux.parkingspot.ParkingSpotController;
import Sioux.parkingspot.ParkingSpotMemoryRepository;
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

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MainController {
    private final AppointmentController appointmentController;
    private final VisitorController visitorController;
    private List<Appointment> appointmentList;
    private List<Visitor> visitorList;
    private List<ParkingSpot> parkingSpotList;
    Visitor selectedVisitor;
    Visitor selectedVisitorForAppointment;
    Appointment selectedAppointment;
    ParkingSpotController parkingSpotController;

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
    @FXML
    private Button btnEditAppointment;
    @FXML
    private Button btnCancel;
    @FXML
    private TextField tfStartDate;
    @FXML
    private Button btnDeleteAppointment;
    @FXML
    private Button btnSelectVisitor;
    @FXML
    private ListView<ParkingSpot> lvAllParkingSpots;
    @FXML
    private RadioButton rbtnFreeSpots;

    //FXML vars visitor page
    @FXML
    private ListView<Visitor> lvAllVisitors;
    @FXML
    private TextField tfNameVisitor;
    @FXML
    private TextField tfLicenseplateNumber;
    @FXML
    private TextField tfVisitorNotes;
    @FXML
    private TextField tfPhoneNumber;
    @FXML
    private TextField tfSearchVisitor;
    @FXML
    Button btnEditVisitor;
    @FXML
    Button btnAddVisitor;
    @FXML
    ListView<Appointment> lvVisitorAppointments;

    DateTimeFormatter formatter2;

    public MainController() {
        appointmentList = new ArrayList<>();
        appointmentController = new AppointmentController(new AppointmentMemoryRepository());
        visitorController = new VisitorController(new VisitorMemoryRepository());
        parkingSpotController = new ParkingSpotController(new ParkingSpotMemoryRepository());
    }

    public void initialize() {

        formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        getAllAppointments();
        getAllParkingSpots();
        lvAllParkingSpots.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        lvAllAppointments.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        //Visitor page
        getAllVisitors();
        lvAllVisitors.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        btnEditVisitor.setDisable(true);
        btnEditAppointment.setDisable(true);
        btnDeleteAppointment.setDisable(true);
    }

    private void getAllAppointments() {
        appointmentList = appointmentController.getAppointments();
        // Formatter is set for hours so the appointment is still visible for one hour
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
        for (Appointment appointment : appointmentList) {
            if (LocalDateTime.now().format(formatter).compareTo(appointment.getStart().format(formatter)) < 1) {
                lvAllAppointments.getItems().add(appointment);
            }
        }
    }

    public void viewSelectedAppointment() {
        selectedAppointment = lvAllAppointments.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            appointmentController.getAppointmentById(selectedAppointment.getId());
            tfVisitorName.setText(selectedAppointment.getVisitor().getName());
            tfStartDate.setText(selectedAppointment.getStart().toString());
            tfNotes.setText(selectedAppointment.getSubject());
            btnEditAppointment.setDisable(false);
            btnDeleteAppointment.setDisable(false);
            btnCancel.setText("Clear");
            btnSelectVisitor.setDisable(true);
        } else {
            clearInfo();
            btnEditAppointment.setDisable(true);
            btnCancel.setText("Cancel");
        }
    }

    public void editAppointment() {
        //Code for editing screen for the appointment
        if (selectedAppointment != null) {
            try {
                //Creating the loader
                EditAppointmentController editAppointmentController = new EditAppointmentController();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditAppointmentView.fxml"));
                fxmlLoader.setController(editAppointmentController);
                Parent root1 = fxmlLoader.load();
                //Initializing the controller
                editAppointmentController.initData(selectedAppointment, appointmentController, visitorController);
                //Making the stage
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.DECORATED);
                stage.setTitle("Edit appointment");
                stage.setScene(new Scene(root1));
                stage.showAndWait();
                viewSelectedAppointment();
                lvAllAppointments.refresh();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //No appointment selected
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("There is no appointment selected.");
            alert.setContentText("Please select an appointment.");
            alert.showAndWait();
        }

    }

    public void selectVisitor() {
        //Code for editing screen for the appointment
        try {
            //Creating the loader
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ChooseVisitorView.fxml"));
            Parent root1 = fxmlLoader.load();
            //Adding the controller to the view
            ChooseVisitorController chooseVisitorController = fxmlLoader.getController();
            //Initializing the controller
            chooseVisitorController.initData(visitorController);
            //Making the stage
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Choose visitor");
            stage.setScene(new Scene(root1));
            stage.showAndWait();
            selectedVisitorForAppointment = chooseVisitorController.getSelectedVisitor();
            tfVisitorName.setText(selectedVisitorForAppointment.getName());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveAppointment() {
        try {
            //Creating the loader
            AddAppointmentController addAppointmentController = new AddAppointmentController();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditAppointmentView.fxml"));
            fxmlLoader.setController(addAppointmentController);
            Parent root1 = fxmlLoader.load();
            addAppointmentController.initData(appointmentController, visitorController);
            //Making the stage
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Create appointment");
            stage.setScene(new Scene(root1));
            stage.showAndWait();
            viewSelectedAppointment();
            lvAllAppointments.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteAppointment() {
        if (selectedAppointment != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete the appointment?");
            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK) {
                appointmentList.remove(selectedAppointment);
                lvAllAppointments.getItems().remove(selectedAppointment);
                lvAllAppointments.refresh();
                clearInfo();
            }
        }
    }

    public void searchForAppointment() {
        List<Appointment> filteredList;
        if (!tfSearchAppointments.getText().isEmpty() && dpDateSearch.getValue() == null) {
            filteredList = appointmentController.searchForAppointmentString(tfSearchAppointments.getText());
            lvAllAppointments.getItems().removeAll(lvAllAppointments.getItems());
            for (Appointment e : filteredList) {
                lvAllAppointments.getItems().add(e);
            }
        } else if (tfSearchAppointments.getText().isEmpty() && dpDateSearch.getValue() != null) {
            filteredList = appointmentController.searchForAppointmentByDate(dpDateSearch.getValue().atStartOfDay());
            lvAllAppointments.getItems().removeAll(lvAllAppointments.getItems());
            for (Appointment e : filteredList) {
                lvAllAppointments.getItems().add(e);
            }
        } else if (!tfSearchAppointments.getText().isEmpty() && dpDateSearch.getValue() != null) {
            filteredList = appointmentController.searchAppointmentStringDate(tfSearchAppointments.getText(), dpDateSearch.getValue().atStartOfDay());
            lvAllAppointments.getItems().removeAll(lvAllAppointments.getItems());
            for (Appointment e : filteredList) {
                lvAllAppointments.getItems().add(e);
            }
        } else {
            lvAllAppointments.getItems().removeAll(lvAllAppointments.getItems());
            getAllAppointments();
        }
        dpDateSearch.setValue(null);
    }

    public List<Appointment> searchAppointmentVisitorID(int id) {
        return appointmentController.searchEventsVisitorID(id);
    }

    private void getAllVisitors() {
        visitorList = visitorController.getVisitorList();
        for (Visitor visitor : visitorList) {
            lvAllVisitors.getItems().add(visitor);
        }
    }

    public void viewSelectedVisitor() {
        selectedVisitor = lvAllVisitors.getSelectionModel().getSelectedItem();
        if (selectedVisitor == null) {
            btnEditVisitor.setDisable(true);
            btnAddVisitor.setDisable(false);
            btnSelectVisitor.setDisable(false);
            tfNameVisitor.setText("");
            tfLicenseplateNumber.setText("");
            tfVisitorNotes.setText("");
            tfPhoneNumber.setText("");
        } else {
            btnEditVisitor.setDisable(false);
            btnAddVisitor.setDisable(true);
            btnSelectVisitor.setDisable(true);
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

    public void EditVisitor() {
        if (selectedVisitor != null) {
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
        } else {
            //No visitor selected
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("There is no visitor selected.");
            alert.setContentText("Please select a visitor.");
            alert.showAndWait();
        }
    }

    public void addVisitor() {
        String newVisitorName = tfNameVisitor.getText();
        if (!newVisitorName.equals("")) {
            lvAllVisitors.getItems().add(visitorController.addVisitor(new Visitor(visitorList.toArray().length + 1, newVisitorName, tfLicenseplateNumber.getText(), tfPhoneNumber.getText(), tfVisitorNotes.getText())));
            lvAllVisitors.refresh();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Not all information is (correctly) provided.");
            alert.setContentText("Please fill in all information correctly.");
            alert.showAndWait();
        }
    }

    public void deleteVisitor() {
        if (selectedVisitor != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete the visitor?\nThis will also delete all the appointments of the visitor.");
            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK) {
                visitorController.deleteVisitor(selectedVisitor.getVisitorID());
                lvAllVisitors.getItems().remove(selectedVisitor);
                visitorController.getVisitorList().remove(selectedVisitor);
                //Deleting all the appointments of the visitor
                List<Appointment> appointmentsToDelete = appointmentController.searchEventsVisitorID(selectedVisitor.getVisitorID());
                for (Appointment p : appointmentsToDelete) {
                    lvVisitorAppointments.getItems().removeAll(lvAllAppointments.getItems());
                    appointmentController.deleteAppointment(p);
                    appointmentList.remove(p);
                    lvAllAppointments.getItems().remove(p);
                    lvVisitorAppointments.getSelectionModel().clearSelection();
                }
                clearInfo();
            }
            lvAllVisitors.refresh();
            lvAllAppointments.refresh();
            lvVisitorAppointments.refresh();
        } else {
            //No visitor selected
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("There is no visitor selected.");
            alert.setContentText("Please select a visitor.");
            alert.showAndWait();
        }
    }


    public void searchVisitorByName() {
        lvAllVisitors.getItems().clear();
        clearInfo();
        List<Visitor> foundVisitors = visitorController.searchVisitorByName(tfSearchVisitor.getText());
        if (foundVisitors.toArray().length != 0) {
            for (Visitor visitor : foundVisitors) {
                lvAllVisitors.getItems().add(visitor);
            }
        } else if (tfSearchVisitor.getText().equals("")) {
            getAllVisitors();
        }
        lvAllVisitors.refresh();
    }

    public void clearInfo() {
        //Visitor page
        btnEditVisitor.setDisable(true);
        btnAddVisitor.setDisable(false);
        selectedVisitor = null;
        tfNameVisitor.setText("");
        tfLicenseplateNumber.setText("");
        tfVisitorNotes.setText("");
        tfPhoneNumber.setText("");
        lvVisitorAppointments.getItems().removeAll(lvAllAppointments.getItems());

        //Appointment page
        selectedAppointment = null;
        tfVisitorName.setText("");
        tfNotes.setText("");
        tfStartDate.setText("");
        dpAppointmentDate.setValue(null);
        btnEditAppointment.setDisable(true);
        btnDeleteAppointment.setDisable(true);
        btnSelectVisitor.setDisable(false);
        lvAllAppointments.getSelectionModel().clearSelection();
        btnCancel.setText("Cancel");
    }
    public void getAllParkingSpots(){
        ClearParkingSpotList();
        parkingSpotList = parkingSpotController.GetAllParkingSpots();
        for(var parkingSpot : parkingSpotList){
            if(rbtnFreeSpots.isSelected()){
                if(!parkingSpot.isOccupied()){
                    lvAllParkingSpots.getItems().add(parkingSpot);
                }
            }else{
                lvAllParkingSpots.getItems().add(parkingSpot);
            }
        }
    }
    private void ClearParkingSpotList(){
        lvAllParkingSpots.getItems().clear();
    }

}


