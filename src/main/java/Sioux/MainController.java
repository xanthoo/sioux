package Sioux;

import Sioux.LicensePlateApi.LicensePlateApi;
import Sioux.LicensePlateApi.LicensePlateApiContext;
import Sioux.SMS.sendSms;
import Sioux.appointment.Appointment;
import Sioux.appointment.AppointmentController;
import Sioux.appointment.AppointmentMemoryRepository;
import Sioux.parkingspot.ParkingSpot;
import Sioux.parkingspot.ParkingSpotController;
import Sioux.parkingspot.ParkingSpotMemoryRepository;
import Sioux.visitor.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.*;


import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable{

    LicensePlateApi licensePlateApi = new LicensePlateApiContext();
    private final AppointmentController appointmentController;
    private final VisitorController visitorController;
    private List<Appointment> appointmentList;
    private List<Visitor> visitorList;
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
    private Button btnAddAppointment;
    @FXML
    private TextField tfStartDate;
    @FXML
    private AnchorPane licensePlateAnchor;
    @FXML
    private Button btnDeleteAppointment;
    @FXML
    private TextField tbBrowsePlate;

    @FXML
    private Button btnBrowse;

    @FXML
    private Label lblLicensePlate;


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
    ListView<Appointment> lvVisitorAppointments;

   //Parkingspot vars
    @FXML
    TableView<ParkingSpot> parkingspotTable;
    @FXML
    TableColumn<ParkingSpot, Integer> parkingspotColumn;
    @FXML
    TableColumn<ParkingSpot, Boolean> availableColumn;

    DateTimeFormatter formatter2;

    public MainController()  {
        appointmentList = new ArrayList<>();
        appointmentController = new AppointmentController(new AppointmentMemoryRepository());
        visitorController = new VisitorController(new VisitorMemoryRepository());
        parkingSpotController = new ParkingSpotController(new ParkingSpotMemoryRepository());
    }

    public void initialize() {

        formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        getAllAppointments();
        getAllParkingSpots();
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
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditAppointmentView.fxml"));
                EditAppointmentController editAppointmentController = new EditAppointmentController();
                fxmlLoader.setController(editAppointmentController);
                Parent root1 = fxmlLoader.load();
                //Adding the controller to the view

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
            lvAllAppointments.getItems().removeAll(lvAllAppointments.getItems());
            getAllAppointments();
            lvAllAppointments.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            lvAllAppointments.getItems().clear();
            getAllAppointments();
            new Thread(new Runnable() {
                public void run() {
                    while (true){
                        if(LocalDateTime.now().format(formatter).compareTo(newAppointment.getStart().minusMinutes(5).format(formatter))==0){
                            String[] arguments = new String[] {"123"};
                            Sioux.SMS.sendSms smsSender = new sendSms();
                            Sioux.SMS.sendSms.main(arguments);
                            break;
                        }
                    }
                }
            }).start();
*/
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
            tfNameVisitor.setText("");
            tfLicenseplateNumber.setText("");
            tfVisitorNotes.setText("");
            tfPhoneNumber.setText("");
        } else {
            btnEditVisitor.setDisable(false);
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
                EditVisitorController editVisitorController = new EditVisitorController();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VisitorView.fxml"));
                fxmlLoader.setController(editVisitorController);
                Parent root1 = fxmlLoader.load();
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
        try {
            //Creating the loader
            AddVisitorController addVisitorController = new AddVisitorController();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VisitorView.fxml"));
            fxmlLoader.setController(addVisitorController);
            Parent root1 = fxmlLoader.load();
            addVisitorController.initData(visitorController);
            //Making the stage
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Add visitor");
            stage.setScene(new Scene(root1));
            stage.showAndWait();
            lvAllVisitors.getItems().clear();
            getAllVisitors();
            lvAllVisitors.refresh();
            viewSelectedVisitor();
        } catch (IOException e) {
            e.printStackTrace();
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
        btnEditAppointment.setDisable(true);
        btnAddAppointment.setDisable(false);
        btnDeleteAppointment.setDisable(true);
        lvAllAppointments.getSelectionModel().clearSelection();
        btnCancel.setText("Cancel");
    }
    public void getAllParkingSpots(){

        ObservableList<ParkingSpot> data = FXCollections.observableList(parkingSpotController.GetAllParkingSpots());

        availableColumn.setCellValueFactory(new PropertyValueFactory<ParkingSpot, Boolean>("occupied"));
        parkingspotColumn.setCellValueFactory(new PropertyValueFactory<ParkingSpot, Integer>("number"));

        parkingspotTable.setItems(null);
        parkingspotTable.setItems(data);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initialize();
    }


    public void browseLicensePlate(){

        FileChooser fc = new FileChooser();


        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG files", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG files", "*.png")
        );


        File selectedFile = fc.showOpenDialog(null);

        if(selectedFile != null){
            var result = selectedFile.getAbsolutePath();
            tbBrowsePlate.setText(result);
            lblLicensePlate.setText(licensePlateApi.getLicensePlate(result));
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Error");
            alert.setContentText("File is not valid!");
            alert.showAndWait();
        }
    }

}


