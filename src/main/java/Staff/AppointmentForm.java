
package Staff;

import main.User;
import main.*;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
public class AppointmentForm extends JDialog {
    private JComboBox<String> customerBox;
    private JComboBox<String> dateBox;
    private JComboBox<String> timeBox;
    private JComboBox<String> typeBox;
    private JComboBox<String> doctorBox;
    private boolean saved = false;
    private User currentUser;
    
    public AppointmentForm(JFrame parent, User currentUser) {
        super(parent, "Book Appointment", true);
        this.currentUser = currentUser;
        setLayout(new BorderLayout(20, 20));

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 15, 10));

        customerBox = new JComboBox<>();
        for (Customer c : DataIO.allCustomers) {
            customerBox.addItem(c.getUsername());
        }

        dateBox = new JComboBox<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (int i = 0; i < 7; i++) {
            LocalDate date = LocalDate.now().plusDays(i);
            dateBox.addItem(date.format(formatter));
        }

        timeBox = new JComboBox<>(new String[]{
            "09:00", "09:30", "10:00", "10:30", "11:00",
            "12:00", "12:30", "13:00","14:00", "14:30", 
            "15:00", "15:30", "16:00","16:30", "17:00"
        });

        typeBox = new JComboBox<>(new String[]{
            "General consultation", "Medical checkup", "Follow-up appointment"
        });

        doctorBox = new JComboBox<>();
        doctorBox.addItem("");
        for (Doctor d : DataIO.allDoctors) {
            doctorBox.addItem(d.getUsername());
        }

        formPanel.add(new JLabel("Customer Username:"));
        formPanel.add(customerBox);
        formPanel.add(new JLabel("Date (yyyy-MM-dd):"));
        formPanel.add(dateBox);
        formPanel.add(new JLabel("Time:"));
        formPanel.add(timeBox);
        formPanel.add(new JLabel("Type:"));
        formPanel.add(typeBox);
        formPanel.add(new JLabel("Doctor (optional):"));
        formPanel.add(doctorBox);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");

        saveBtn.addActionListener(e -> saveAppointment());
        cancelBtn.addActionListener(e -> dispose());

        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }
    
    public AppointmentForm(JFrame parent, Appointment existingAppointment, User currentUser) {
        super(parent, "Update Appointment", true);
        this.currentUser = currentUser;
        setLayout(new BorderLayout(20, 20));

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 15, 10));

        customerBox = new JComboBox<>();
        for (Customer c : DataIO.allCustomers) {
            customerBox.addItem(c.getUsername());
        }
        customerBox.setSelectedItem(existingAppointment.getCustomerUsername());

        dateBox = new JComboBox<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (int i = 0; i < 7; i++) {
            LocalDate date = LocalDate.now().plusDays(i);
            dateBox.addItem(date.format(formatter));
        }
        dateBox.setSelectedItem(existingAppointment.getDate());

        timeBox = new JComboBox<>(new String[]{
            "09:00", "09:30", "10:00", "10:30", "11:00",
            "12:00", "12:30", "13:00","14:00", "14:30",
            "15:00", "15:30", "16:00","16:30", "17:00"
        });
        timeBox.setSelectedItem(existingAppointment.getTime());

        typeBox = new JComboBox<>(new String[]{"General consultation", "Medical checkup", "Follow-up appointment"});
        typeBox.setSelectedItem(existingAppointment.getType());

        doctorBox = new JComboBox<>();
        doctorBox.addItem("");
        for (Doctor d : DataIO.allDoctors) {
            doctorBox.addItem(d.getUsername());
        }
        doctorBox.setSelectedItem(existingAppointment.getDoctorUsername());

        formPanel.add(new JLabel("Customer Username:"));
        formPanel.add(customerBox);
        formPanel.add(new JLabel("Date:"));
        formPanel.add(dateBox);
        formPanel.add(new JLabel("Time:"));
        formPanel.add(timeBox);
        formPanel.add(new JLabel("Type:"));
        formPanel.add(typeBox);
        formPanel.add(new JLabel("Doctor (optional):"));
        formPanel.add(doctorBox);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");

        saveBtn.addActionListener(e -> {
            existingAppointment.setCustomerUsername((String) customerBox.getSelectedItem());
            existingAppointment.setDate((String) dateBox.getSelectedItem());
            existingAppointment.setTime((String) timeBox.getSelectedItem());
            existingAppointment.setType((String) typeBox.getSelectedItem());

            String selectedDoctor = (String) doctorBox.getSelectedItem();
            if (selectedDoctor != null && selectedDoctor.isEmpty()) {
                selectedDoctor = null;
            }
            existingAppointment.setDoctorUsername(selectedDoctor);
            existingAppointment.setStatus(selectedDoctor == null ? "pending" : "booked");

            DataIO.write();
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
    
    private void saveAppointment() {
        String customer = (String) customerBox.getSelectedItem();
        String date = (String) dateBox.getSelectedItem();
        String time = (String) timeBox.getSelectedItem();
        String type = (String) typeBox.getSelectedItem();
        String doctor = (String) doctorBox.getSelectedItem();
        if (doctor != null && doctor.isEmpty()) {
            doctor = null;
        }
        
        if (customer == null || date.isEmpty() || time == null || type == null) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields.");
            return;
        }
        
        for (Appointment a : DataIO.allAppointments) {
        if (doctor != null && doctor.equals(a.getDoctorUsername()) &&
            date.equals(a.getDate()) &&
            time.equals(a.getTime()) &&
            !"cancel".equalsIgnoreCase(a.getStatus())) {
            JOptionPane.showMessageDialog(this,
                "This doctor already has an appointment at this time.",
                "Schedule Conflict",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
        String appId = generateAppointmentId();
        String status = (doctor == null) ? "pending" : "booked";

        String staffId = (currentUser != null) ? currentUser.getUserId() : null;
        Appointment a = new Appointment(appId, customer, date, time, type, doctor, staffId, status, 0.0, null, null, null);
        
        DataIO.allAppointments.add(a);
        DataIO.write();

        saved = true;
        JOptionPane.showMessageDialog(this, "Appointment booked successfully! ID: " + appId);
        dispose();
    }
    
    private String generateAppointmentId() {
        int maxNum = 0;
        for (Appointment a : DataIO.allAppointments) {
            String numPart = a.getAppointmentId().substring(1); 
            try {
                int num = Integer.parseInt(numPart);
                if (num > maxNum) maxNum = num;
            } catch (NumberFormatException ignored) {}
        }
        return String.format("A%04d", maxNum + 1);
    }

    public boolean isSaved() {
        return saved;
    }
}
