package Sioux.visitor;

public class Visitor {

    private int visitorID;
    private String name;
    private String licensePlateNumber;
    private String phoneNumber;
    private String notes;

    public Visitor(int visitorID, String name, String licensePlateNumber, String phoneNumber, String notes){
        this.visitorID = visitorID;
        this.name = name;
        this.licensePlateNumber = licensePlateNumber;
        this.phoneNumber = phoneNumber;
        this.notes = notes;
    }

    public int getVisitorID() {
        return visitorID;
    }

    public void setVisitorID(int visitorID) {
        this.visitorID = visitorID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString(){
        return "Visitor: "+name;
    }
}
