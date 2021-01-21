package Sioux.visitor;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.regex.Pattern;

public class AddVisitorController {

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

    private String nameVisitor;
    private String licenseplateNumber;
    private String phoneNumber;
    private String visitorNotes;

    public void initData(Visitor visitor, VisitorController visitorController) {

    }

    public void initData(VisitorController visitorController) {
        this.visitorController = visitorController;
        lblTitle.setText("Add visitor");
    }

    public void saveVisitorDetails() {
        String text = null;
        nameVisitor = tfNameVisitor.getText();
        licenseplateNumber = tfLicenseplateNumber.getText();
        phoneNumber = tfPhoneNumber.getText();
        visitorNotes = tfVisitorNotes.getText();
        if(checkEnteredDataCorrect() && phoneNumber.matches("[0-9]+")) {
            visitorController.addVisitor(new Visitor(visitorController.getVisitorList().size(),nameVisitor, licenseplateNumber, phoneNumber, visitorNotes));
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

    public void cancelEditing() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    private boolean checkEnteredDataCorrect() {
        if(!nameVisitor.equals("") && !licenseplateNumber.equals("") && !phoneNumber.equals("") && !visitorNotes.equals("")){
            return true;
        }
        return false;
    }
}
