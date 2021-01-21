package Sioux.visitor;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.regex.Pattern;

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
    @FXML
    private Label lblTitle;

    private VisitorController visitorController;
    private Visitor selectedVisitor;

    private String nameVisitor;
    private String licenseplateNumber;
    private String phoneNumber;
    private String visitorNotes;

    public void initData(Visitor visitor, VisitorController visitorController) {
        this.selectedVisitor = visitor;
        tfNameVisitor.setText(visitor.getName());
        tfLicenseplateNumber.setText(visitor.getLicensePlateNumber());
        tfPhoneNumber.setText(visitor.getPhoneNumber());
        tfVisitorNotes.setText(visitor.getNotes());
        this.visitorController = visitorController;
        lblTitle.setText("Edit visitor details");
    }

    public void saveVisitorDetails(){

        nameVisitor = tfNameVisitor.getText();
        licenseplateNumber = tfLicenseplateNumber.getText();
        phoneNumber = tfPhoneNumber.getText();
        visitorNotes = tfVisitorNotes.getText();

        if(checkEnteredDataCorrect() && phoneNumber.matches("[0-9]+")){
            visitorController.updateVisitor(new Visitor(selectedVisitor.getVisitorID(), nameVisitor, licenseplateNumber, phoneNumber, visitorNotes));
            cancelEditing();
        }
        else{
            String errorMessage = "Fields incorrect: ";
            if (nameVisitor.equals("")){
                errorMessage += "\nName of the visitor!";
            }
            if (licenseplateNumber.equals("")){
                errorMessage += "\nLicenseplate field!";
            }
            if (phoneNumber.equals("")){
                errorMessage += "\nPhone number!";
            }
            if (visitorNotes.equals("")){
                errorMessage += "\nNotes!";
            }
            //Some information is not provided
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Not all information in filled in correctly.");
            alert.setContentText(errorMessage);
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
