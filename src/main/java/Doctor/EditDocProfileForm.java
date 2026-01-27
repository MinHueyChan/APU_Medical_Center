/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Doctor;

import main.User;
import main.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;

/**
 *
 * @author User
 */
public class EditDocProfileForm extends JDialog {

    private Doctor currentUser;
    private boolean saved = false;

    private JTextField txtUserId, txtUsername, txtName, txtEmail, txtPhone, txtAge;
    private JComboBox<String> cmbGender;

    public EditDocProfileForm(JFrame parent, Doctor doctor) {
        super(parent, "Edit Doctor Profile", true);
        this.currentUser = doctor;

        setLayout(new GridLayout(8, 2, 10, 10));

        
        add(new JLabel("User ID:"));
        txtUserId = new JTextField(doctor.getUserId());
        txtUserId.setEditable(false);
        add(txtUserId);

        add(new JLabel("Username:"));
        txtUsername = new JTextField(doctor.getUsername());
        txtUsername.setEditable(false);
        add(txtUsername);

        add(new JLabel("Name:"));
        txtName = new JTextField(doctor.getName());
        add(txtName);

        add(new JLabel("Gender:"));
        cmbGender = new JComboBox<>(new String[]{"M", "F"});
        cmbGender.setSelectedItem(doctor.getGender());
        add(cmbGender);

        add(new JLabel("Email:"));
        txtEmail = new JTextField(doctor.getEmail());
        add(txtEmail);

        add(new JLabel("Phone:"));
        txtPhone = new JTextField(doctor.getPhone());
        add(txtPhone);

        add(new JLabel("Age:"));
        txtAge = new JTextField(String.valueOf(doctor.getAge()));
        add(txtAge);

        
        JButton btnSave = new JButton("Save");
        JButton btnCancel = new JButton("Cancel");

        add(btnSave);
        add(btnCancel);

        
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveChanges();
            }
        });

        
        btnCancel.addActionListener(e -> dispose());

        setSize(400, 300);
        setLocationRelativeTo(parent);
    }

    private void saveChanges() {
        try {
            
            
            currentUser.setName(txtName.getText().trim());
            currentUser.setGender((String) cmbGender.getSelectedItem());
            currentUser.setEmail(txtEmail.getText().trim());
            currentUser.setPhone(txtPhone.getText().trim());
            currentUser.setAge(Integer.parseInt(txtAge.getText().trim()));

            
            for (int i = 0; i < DataIO.allDoctors.size(); i++) {
                if (DataIO.allDoctors.get(i).getUserId().equals(currentUser.getUserId())) {
                    DataIO.allDoctors.set(i, currentUser);
                    break;
                }
            }

            DataIO.write();

            saved = true;
            JOptionPane.showMessageDialog(this, "Profile updated successfully!");
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error saving changes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isSaved() {
        return saved;
    }
}