
package Staff;

import main.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class AssignDoctorForm extends JDialog {
    private JComboBox<String> doctorBox;
    private boolean saved = false;
    private String selectedDoctor;
    
    private String appointmentDate;
    private String appointmentTime;

    public AssignDoctorForm(JFrame parent, String date, String time) {
        super(parent, "Assign Doctor", true);
        this.appointmentDate = date;
        this.appointmentTime = time;

        setLayout(new BorderLayout(20, 20));

        JPanel formPanel = new JPanel(new GridLayout(1, 2, 15, 10));
        doctorBox = new JComboBox<>();
        for (Doctor d : DataIO.allDoctors) {
            doctorBox.addItem(d.getUsername());
        }

        formPanel.add(new JLabel("Select Doctor:"));
        formPanel.add(doctorBox);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveBtn = new JButton("Assign");
        JButton cancelBtn = new JButton("Cancel");

        saveBtn.addActionListener(e -> {
            selectedDoctor = (String) doctorBox.getSelectedItem();
            if (selectedDoctor == null || selectedDoctor.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select a doctor.");
                return;
            }
            
            for (Appointment a : DataIO.allAppointments) {
                if (selectedDoctor.equals(a.getDoctorUsername()) &&
                    appointmentDate.equals(a.getDate()) &&
                    appointmentTime.equals(a.getTime()) &&
                    !"cancel".equalsIgnoreCase(a.getStatus())) {
                    JOptionPane.showMessageDialog(this,
                        "This doctor already has an appointment at this time.",
                        "Schedule Conflict",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            saved = true;
            dispose();
        });
        
        

        cancelBtn.addActionListener(e -> dispose());

        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }

    public boolean isSaved() {
        return saved;
    }

    public String getSelectedDoctor() {
        return selectedDoctor;
    }
}
