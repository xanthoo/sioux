package Sioux;

import Sioux.appointment.Appointment;
import Sioux.appointment.AppointmentController;
import Sioux.visitor.Visitor;
import Sioux.visitor.VisitorController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.time.LocalDate;

public class EditAppointmentController {

    @FXML
    private TextField tfSubject;
    @FXML
    private  TextField tfStartDate;
    @FXML
    private TextField tfEndDate;
    @FXML
    private TextField tfVisitor;
    @FXML
    private Button btnCancel;

    private AppointmentController appointmentController;
    private VisitorController visitorController;
    private Appointment selectedEvent;
    private String subject;
    private String startDate;
    private String visitorName;
    private String endDate;
    private Visitor visitorOfAppointment;

    void initData(Appointment appointment, AppointmentController appointmentController, VisitorController visitorController) {
        this.selectedEvent = appointment;
        tfSubject.setText(appointment.getSubject());
        tfStartDate.setText(appointment.getStart().toString());
        tfVisitor.setText(appointment.getVisitor().getName());
        tfEndDate.setText(appointment.getEnd().toString());
        visitorOfAppointment = selectedEvent.getVisitor();
        this.appointmentController = appointmentController;
        this.visitorController = visitorController;
    }

    public void saveAppointmentDetails(){

        subject = tfSubject.getText();
        startDate = tfStartDate.getText();
        endDate = tfEndDate.getText();
        visitorName = tfVisitor.getText();

        if(checkEnteredDataCorrect()){
            appointmentController.updateAppointment(new Appointment(subject, selectedEvent.getId(), LocalDate.parse(startDate), LocalDate.parse(endDate), visitorOfAppointment));
            appointmentController.updateAppointment(new Appointment(subject, selectedEvent.getId(), LocalDateTime.parse(startDate), LocalDate.parse(endDate), selectedEvent.getVisitor()));
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
            LocalDate.parse(startDate);
            LocalDate.parse(endDate);
        } catch (Exception e){
            return false;
        }
        if(!subject.equals("") && !endDate.equals("") && !startDate.equals("") && !visitorName.equals("")){
            return true;
        }
        return false;
    }
}
