import javax.swing.*;

public class Main {
    private JTabbedPane tabbedPane1;
    private JTextField nameTextField;
    private JTextField licensePlateTextField;
    private JTextField notesTextField;
    private JButton saveButton;
    private JButton cancelButton;
    private JTextArea upcommingAppoinmentsTextArea;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Appointment");
        frame.setContentPane(new Main().tabbedPane1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
