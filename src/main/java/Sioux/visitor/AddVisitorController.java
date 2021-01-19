package Sioux.visitor;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
        nameVisitor = tfNameVisitor.getText();
        licenseplateNumber = tfLicenseplateNumber.getText();
        phoneNumber = tfPhoneNumber.getText();
        visitorNotes = tfVisitorNotes.getText();
        if(checkEnteredDataCorrect()) {
            visitorController.addVisitor(new Visitor(visitorController.getVisitorList().size(),nameVisitor, licenseplateNumber, phoneNumber, visitorNotes));
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
