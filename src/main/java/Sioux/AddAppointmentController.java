package Sioux;

import Sioux.appointment.Appointment;
import Sioux.appointment.AppointmentController;
import Sioux.visitor.ChooseVisitorController;
import Sioux.visitor.Visitor;
import Sioux.visitor.VisitorController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AddAppointmentController {

    @FXML
    private TextField tfSubject;
    @FXML
    private  TextField tfStartDate;
    @FXML
    private TextField tfVisitor;
    @FXML
    private Button btnCancel;
    @FXML
    private Text txtTitle;
    @FXML
    private Button btnSave;
    @FXML
    private DatePicker dpStartDate;

    private AppointmentController appointmentController;
    private VisitorController visitorController;
    private String subject;
    private String startTime;
    private LocalDate startDate;
    private String visitorName;
    private Visitor visitorOfAppointment;

    void initData(AppointmentController appointmentController, VisitorController visitorController) {
        this.appointmentController = appointmentController;
        this.visitorController = visitorController;
        txtTitle.setText("Create appointment");
        btnSave.setText("Save");
    }

    public void saveAppointmentDetails(){
        subject = tfSubject.getText();
        startTime = tfStartDate.getText();
        visitorName = tfVisitor.getText();
        startDate = dpStartDate.getValue();

        if(checkEnteredDataCorrect()){
            appointmentController.createAppointment(new Appointment(subject, appointmentController.getAppointments().size(), startDateTimeOfAppointment(startTime, startDate), visitorOfAppointment));
            // appointmentController.updateAppointment(new Appointment(subject, selectedEvent.getId(), LocalDateTime.parse(startDate), LocalDate.parse(endDate), selectedEvent.getVisitor()));
            cancelEditing();
        }
        else{
            //Some information is not provided
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Not all information in filled in correctly.");
            alert.setContentText("Please fill in all information.");
            alert.showAndWait();
        }
    }

    private LocalDateTime startDateTimeOfAppointment(String startTime, LocalDate startDate){
        return LocalDateTime.parse(startDate + "T" +startTime);
    }

    public void selectVisitor(){
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
            //TODO add visitor with object
            visitorOfAppointment = chooseVisitorController.getSelectedVisitor();
            tfVisitor.setText(visitorOfAppointment.getName());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void cancelEditing(){
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
    private boolean checkEnteredDataCorrect(){
        try{
            LocalTime.parse(startTime);
        } catch (Exception e){
            return false;
        }
        if(!subject.equals("") && !startTime.equals("") && !startDate.equals(null) && !visitorName.equals("")){
            return true;
        }
        return false;
    }
}
