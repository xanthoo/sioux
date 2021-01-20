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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.security.auth.Subject;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class EditAppointmentController {

    @FXML
    private TextField tfSubject;
    @FXML
    private  TextField tfStartDate;
    @FXML
    private DatePicker dpStartDate;
    @FXML
    private TextField tfVisitor;
    @FXML
    private Button btnCancel;

    private AppointmentController appointmentController;
    private VisitorController visitorController;
    private Appointment selectedEvent;
    private String subject;
    private String startTime;
    private LocalDate startDate;
    private String visitorName;
    private Visitor visitorOfAppointment;

    void initData(Appointment appointment, AppointmentController appointmentController, VisitorController visitorController) {
        this.selectedEvent = appointment;
        tfSubject.setText(appointment.getSubject());
        tfStartDate.setText(getStartTimeString(appointment.getStart().toString()));
        dpStartDate.setValue(getStartDate(appointment.getStart()));
        tfVisitor.setText(appointment.getVisitor().getName());
        visitorOfAppointment = selectedEvent.getVisitor();
        this.appointmentController = appointmentController;
        this.visitorController = visitorController;
    }

    private LocalDateTime startDateTimeOfAppointment(String startTime, LocalDate startDate){
        return LocalDateTime.parse(startDate + "T" +startTime);
    }
    private String getStartTimeString(String startTimeDate){
        int index = 11;
        char[] chs = startTimeDate.toCharArray();
        return new String(chs, index, chs.length - index);
    }
    private LocalDate getStartDate(LocalDateTime start){
        int index = 10;
        char[] chs = start.toString().toCharArray();
        return LocalDate.parse(new String(chs, 0, index));
    }


    public void saveAppointmentDetails(){

        subject = tfSubject.getText();
        startTime = tfStartDate.getText();
        visitorName = tfVisitor.getText();
        startDate = dpStartDate.getValue();

        if(checkEnteredDataCorrect()){
            appointmentController.updateAppointment(new Appointment(subject, selectedEvent.getId(), startDateTimeOfAppointment(startTime, startDate), visitorOfAppointment));
            // appointmentController.updateAppointment(new Appointment(subject, selectedEvent.getId(), LocalDateTime.parse(startDate), LocalDate.parse(endDate), selectedEvent.getVisitor()));
            cancelEditing();
        }
        else{
            String errorMessage = "Fields incorrect: ";
            if (subject.equals("")){
                errorMessage += "\n Subject is empty!";
            }
            if (startDate == null){
                errorMessage += "\n Start date is not filled in!";
            }
            if (startTime.equals("")){
                errorMessage += "\n Start time is empty!";
            }
            //check which data is incorrect
            try{
                LocalTime.parse(startTime);
            } catch (Exception e){
                errorMessage += "\n Start time format is wrong!";
            }
            if (visitorName.equals("")){
                errorMessage += "\n Visitor field is empty!";
            }
            //Some information is not provided
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Not all information in filled in correctly.");
            alert.setContentText(errorMessage);
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
            LocalTime.parse(startTime);
        } catch (Exception e){
            return false;
        }
        return !subject.equals("") && !startTime.equals("") && !visitorName.equals("");
    }
}
