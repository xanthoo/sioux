package Sioux;

import Sioux.appointment.Appointment;
import Sioux.appointment.AppointmentController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    private Appointment selectedEvent;
    private String subject;
    private String startDate;
    private String visitorName;
    private String endDate;

    void initData(Appointment appointment, AppointmentController appointmentController) {
        this.selectedEvent = appointment;
        tfSubject.setText(appointment.getSubject());
        tfStartDate.setText(appointment.getStart().toString());
        tfVisitor.setText(appointment.getVisitor().getName());
        tfEndDate.setText(appointment.getEnd().toString());
        this.appointmentController = appointmentController;
    }

    public void saveAppointmentDetails(){

        subject = tfSubject.getText();
        startDate = tfStartDate.getText();
        endDate = tfEndDate.getText();
        visitorName = tfVisitor.getText();

        if(checkEnteredDataCorrect()){
            appointmentController.updateAppointment(new Appointment(subject, selectedEvent.getId(), LocalDate.parse(startDate), LocalDate.parse(endDate), selectedEvent.getVisitor()));
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
