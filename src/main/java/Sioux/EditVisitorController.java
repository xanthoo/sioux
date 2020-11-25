package Sioux;

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

public class EditVisitorController {

    @FXML
    private TextField tfNameVisitor;
    @FXML
    private  TextField tfLicenseplateNumber;
    @FXML
    private TextField tfVisitorNotes;
    @FXML
    private TextField tfPhoneNumber;
    @FXML
    private Button btnCancel;

    private VisitorController visitorController;
    private Visitor selectedVisitor;

    private String nameVisitor;
    private String licenseplateNumber;
    private String phoneNumber;
    private String visitorNotes;

    void initData(Visitor visitor, VisitorController visitorController) {
        this.selectedVisitor = visitor;
        tfNameVisitor.setText(visitor.getName());
        tfLicenseplateNumber.setText(visitor.getLicensePlateNumber());
        tfPhoneNumber.setText(visitor.getPhoneNumber());
        tfVisitorNotes.setText(visitor.getNotes());
        this.visitorController = visitorController;
    }

    public void saveVisitorDetails(){

        nameVisitor = tfNameVisitor.getText();
        licenseplateNumber = tfLicenseplateNumber.getText();
        phoneNumber = tfPhoneNumber.getText();
        visitorNotes = tfVisitorNotes.getText();

        if(checkEnteredDataCorrect()){
            visitorController.updateVisitor(new Visitor(selectedVisitor.getVisitorID(), nameVisitor, licenseplateNumber, phoneNumber, visitorNotes));
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
        if(!nameVisitor.equals("") && !licenseplateNumber.equals("") && !phoneNumber.equals("") && !visitorNotes.equals("")){
            return true;
        }
        return false;
    }
}
