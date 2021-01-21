package Sioux;

import Sioux.LicensePlateApi.LicensePlateApi;
import Sioux.LicensePlateApi.LicensePlateApiContext;
import Sioux.appointment.Appointment;
import Sioux.appointment.AppointmentController;
import Sioux.appointment.AppointmentMemoryRepository;
import Sioux.appointment.AppointmentSQLRepository;
import Sioux.parkingspot.ParkingSpot;
import Sioux.parkingspot.ParkingSpotController;
import Sioux.parkingspot.ParkingSpotMemoryRepository;
import Sioux.parkingspot.ParkingSpotRepository;
import Sioux.visitor.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
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
import java.io.File;
import java.io.IOException;
import java.net.URI;
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
    WebsocketClientEndpoint clientEndPoint;

    //FXML vars
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
    TableView<Appointment> appointmentTable;
    @FXML
    TableColumn<Appointment, String> appointmentNameColumn;
    @FXML
    TableColumn<Appointment, LocalDateTime> dateColumn;
    @FXML
    TableColumn<Appointment, String> subjectColumn;

    
//    @FXML
//    private Button btnCancel;
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
    @FXML
    TableView<Visitor> visitorsTable;
    @FXML
    TableColumn<Visitor, String> visitorNameColumn;
    @FXML
    TableColumn<Visitor, String> licensePlateColumn;

   //Parkingspot vars
    @FXML
    TableView<ParkingSpot> parkingspotTable;
    @FXML
    TableColumn<ParkingSpot, Integer> parkingspotColumn;
    @FXML
    TableColumn<ParkingSpot, Boolean> availableColumn;
    @FXML
    private Button btnSocketConnection;

    DateTimeFormatter formatter2;

    public MainController()  {
        appointmentList = new ArrayList<>();
        appointmentController = new AppointmentController(new AppointmentMemoryRepository());
        visitorController = new VisitorController(new VisitorMemoryRepository());
        parkingSpotController = new ParkingSpotController(new ParkingSpotMemoryRepository());
        clientEndPoint = new WebsocketClientEndpoint();
    }

    public void initialize() {
        formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        getAllAppointments();
        getAllParkingSpots();
        appointmentTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        //Visitor page
        getAllVisitors();
        visitorsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        btnEditVisitor.setDisable(true);
        btnEditAppointment.setDisable(true);
        btnDeleteAppointment.setDisable(true);

        connectWebsocket();
    }

    public void connectWebsocket(){
        try {
            clientEndPoint.connect(URI.create("ws://localhost:9988/sioux/parking"));
            clientEndPoint.addMessageHandler(message -> {
                try {
                    if(!message.equals("connected")) updateParkingSpots(message);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText(e.getMessage() + " Could not connect to websocket.");
            a.show();
        }
    }

    private void getAllAppointments() {
        ObservableList<Appointment> data = FXCollections.observableList(appointmentController.getAppointments());
        fillAppointmentTable(data);
    }

    private void fillAppointmentTable(ObservableList<Appointment> appointments){
        appointmentNameColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("customer"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("start"));
        subjectColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("subject"));

        appointmentTable.setItems(null);
        appointmentTable.setItems(appointments);
    }

    public void viewSelectedAppointment() {
        selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            appointmentController.getAppointmentById(selectedAppointment.getId());
            tfVisitorName.setText(selectedAppointment.getCustomer().getName());
            tfStartDate.setText(selectedAppointment.getStart().toString().replace("T",""));
            tfNotes.setText(selectedAppointment.getSubject());
            btnEditAppointment.setDisable(false);
            btnDeleteAppointment.setDisable(false);
        } else {
            clearInfo();
            btnEditAppointment.setDisable(true);
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
                appointmentTable.refresh();
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
            getAllAppointments();
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
                appointmentController.deleteAppointment(selectedAppointment);
                appointmentList.remove(selectedAppointment);
                appointmentTable.getItems().remove(selectedAppointment);
                appointmentTable.refresh();
                clearInfo();
            }
        }
    }

    public void searchForAppointment() {
        if (!tfSearchAppointments.getText().isEmpty() && dpDateSearch.getValue() == null) {
            ObservableList<Appointment> data = FXCollections.observableList(appointmentController.searchForAppointmentString(tfSearchAppointments.getText()));
            fillAppointmentTable(data);
        } else if (tfSearchAppointments.getText().isEmpty() && dpDateSearch.getValue() != null) {
            ObservableList<Appointment> data = FXCollections.observableList(appointmentController.searchForAppointmentByDate(dpDateSearch.getValue().atStartOfDay()));
            fillAppointmentTable(data);
        } else if (!tfSearchAppointments.getText().isEmpty() && dpDateSearch.getValue() != null) {
            ObservableList<Appointment> data = FXCollections.observableList(appointmentController.searchAppointmentStringDate(tfSearchAppointments.getText(), dpDateSearch.getValue().atStartOfDay()));
            fillAppointmentTable(data);
        } else {
            getAllAppointments();
        }
        dpDateSearch.setValue(null);
    }

    public List<Appointment> searchAppointmentVisitorID(int id) {
        return appointmentController.searchEventsVisitorID(id);
    }

    private void getAllVisitors() {
        ObservableList<Visitor> data = FXCollections.observableList(visitorController.getVisitorList());
        fillVisitorTable(data);
    }

    public void viewSelectedVisitor() {
        selectedVisitor = visitorsTable.getSelectionModel().getSelectedItem();
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
            lvVisitorAppointments.getItems().removeAll(appointmentTable.getItems());
            lvVisitorAppointments.getItems().addAll(searchAppointmentVisitorID(selectedVisitor.getVisitorID()));
        }
    }

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
                getAllVisitors();
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
            getAllVisitors();
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
                visitorsTable.getItems().remove(selectedVisitor);
                visitorController.getVisitorList().remove(selectedVisitor);
                //Deleting all the appointments of the visitor
                List<Appointment> appointmentsToDelete = appointmentController.searchEventsVisitorID(selectedVisitor.getVisitorID());
                for (Appointment p : appointmentsToDelete) {
                    lvVisitorAppointments.getItems().removeAll(appointmentTable.getItems());
                    appointmentController.deleteAppointment(p);
                    appointmentList.remove(p);
                    appointmentTable.getItems().remove(p);
                    lvVisitorAppointments.getSelectionModel().clearSelection();
                }
                clearInfo();
            }
            lvVisitorAppointments.refresh();
            getAllVisitors();
            getAllAppointments();
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
        clearInfo();
        if(tfSearchVisitor.getText().equals("")){
            getAllVisitors();
        } else {
            ObservableList<Visitor> data = FXCollections.observableList(visitorController.searchVisitorByName(tfSearchVisitor.getText()));
            fillVisitorTable(data);
        }
    }

    public void clearInfo() {
        //Visitor page
        btnEditVisitor.setDisable(true);
        selectedVisitor = null;
        tfNameVisitor.setText("");
        tfLicenseplateNumber.setText("");
        tfVisitorNotes.setText("");
        tfPhoneNumber.setText("");
        lvVisitorAppointments.getItems().removeAll(appointmentTable.getItems());

        //Appointment page
        selectedAppointment = null;
        tfVisitorName.setText("");
        tfNotes.setText("");
        tfStartDate.setText("");
        btnEditAppointment.setDisable(true);
        btnAddAppointment.setDisable(false);
        btnDeleteAppointment.setDisable(true);
        appointmentTable.getSelectionModel().clearSelection();
    }
    public void getAllParkingSpots(){

        ObservableList<ParkingSpot> data = FXCollections.observableList(parkingSpotController.GetAllParkingSpots());

        availableColumn.setCellValueFactory(new PropertyValueFactory<ParkingSpot, Boolean>("occupied"));
        parkingspotColumn.setCellValueFactory(new PropertyValueFactory<ParkingSpot, Integer>("id"));

        parkingspotTable.setItems(null);
        parkingspotTable.setItems(data);
    }

    private void fillVisitorTable(ObservableList<Visitor> visitors) {
        visitorNameColumn.setCellValueFactory(new PropertyValueFactory<Visitor, String>("name"));
        licensePlateColumn.setCellValueFactory(new PropertyValueFactory<Visitor, String>("licensePlateNumber"));
        visitorsTable.setItems(null);
        visitorsTable.setItems(visitors);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initialize();
    }

    public void updateParkingSpots(String JSONParkingSpots) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        var parkingSpots = mapper.readValue(JSONParkingSpots, new TypeReference<List<ParkingSpot>>() {});
        var data = FXCollections.observableArrayList(parkingSpots);
        Platform.runLater(() -> {
            availableColumn.setCellValueFactory(new PropertyValueFactory<ParkingSpot, Boolean>("occupied"));
            parkingspotColumn.setCellValueFactory(new PropertyValueFactory<ParkingSpot, Integer>("id"));
            parkingspotTable.setItems(null);
            parkingspotTable.setItems(data);
        });
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
