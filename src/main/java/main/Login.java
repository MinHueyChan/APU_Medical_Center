package main;

import Staff.*;
import Customer.*;
import Doctor.*;
import Manager.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
public class Login implements ActionListener{
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public Login(JFrame frame, JTextField usernameField, JPasswordField passwordField) {
        this.frame = frame;
        this.usernameField = usernameField;
        this.passwordField = passwordField;
    }
    
    public void actionPerformed(ActionEvent e){
        try{
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            
            DataIO.read();
            
            Manager manager = DataIO.checkManagerName(username);
            if (manager == null) {
                manager = DataIO.checkManagerId(username);
            }

            if (manager != null) {
                if (!manager.getPassword().equals(password)) {
                    JOptionPane.showMessageDialog(frame, "Wrong password!");
                    return;
                }
                JOptionPane.showMessageDialog(frame, "Login successful! Welcome " + manager.getName());
                frame.setVisible(false);

                ManagerMain dashboard = new ManagerMain(manager);
                dashboard.setVisible(true);
                return;
            }
            
            Staff staff = DataIO.checkStaffName(username);
            if (staff == null) {
                staff = DataIO.checkStaffId(username);
            }
            
            if (staff != null) {
                if (!staff.getPassword().equals(password)) {
                    JOptionPane.showMessageDialog(frame, "Wrong password!");
                    return;
                }
                JOptionPane.showMessageDialog(frame, "Login successful! Welcome " + staff.getName());
                frame.setVisible(false);

                StaffDashboard dashboard = new StaffDashboard(staff);
                dashboard.setVisible(true);
                return; 
            }
            
            Doctor doctor = DataIO.checkDoctorName(username);
            if (doctor == null) {
                doctor = DataIO.checkDoctorId(username);
            }

            if (doctor != null) {
                if (!doctor.getPassword().equals(password)) {
                    JOptionPane.showMessageDialog(frame, "Wrong password!");
                    return;
                }
                JOptionPane.showMessageDialog(frame, "Login successful! Welcome " + doctor.getName());
                frame.setVisible(false);

                DoctorDashboard dashboard = new DoctorDashboard(doctor);
                dashboard.setVisible(true);
                return;
            }

            Customer customer = DataIO.checkCustomerName(username);
            if (customer == null) {
                customer = DataIO.checkCustomerId(username);
            }

            if (customer != null) {
                if (!customer.getPassword().equals(password)) {
                    JOptionPane.showMessageDialog(frame, "Wrong password!");
                    return;
                }
                JOptionPane.showMessageDialog(frame, "Login successful! Welcome " + customer.getName());
                frame.setVisible(false);

                CusDashboardForm dashboard = new CusDashboardForm(customer);
                dashboard.setVisible(true);
                return;
            }
            
            JOptionPane.showMessageDialog(frame, "User not found!");
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    }
}
