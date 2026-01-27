package Staff;

import main.User;
import main.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EditProfileForm extends JDialog {
    private JTextField nameField;
    private JTextField passwordField;
    private JTextField genderField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField ageField;
    private boolean saved = false;
    private User user;

    public EditProfileForm(JFrame parent, User user) {
        super(parent, "Edit Profile", true);
        this.user = user;
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));

        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField(user.getName());
        formPanel.add(nameField);
        
        formPanel.add(new JLabel("Password:"));
        passwordField = new JTextField(user.getPassword());
        formPanel.add(passwordField);

        formPanel.add(new JLabel("Gender:"));
        genderField = new JTextField(user.getGender());
        formPanel.add(genderField);

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField(user.getEmail());
        formPanel.add(emailField);

        formPanel.add(new JLabel("Phone:"));
        phoneField = new JTextField(user.getPhone());
        formPanel.add(phoneField);

        formPanel.add(new JLabel("Age:"));
        ageField = new JTextField(String.valueOf(user.getAge()));
        formPanel.add(ageField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");

        saveBtn.addActionListener(e -> saveProfile());
        cancelBtn.addActionListener(e -> dispose());

        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }

    private void saveProfile() {
        try {
            String name = nameField.getText().trim();
            String password = passwordField.getText().trim();
            String gender = genderField.getText().trim().toUpperCase();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            int age = Integer.parseInt(ageField.getText().trim());

            if (name.isEmpty() || password.isEmpty() || gender.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.");
                return;
            }
            if (!gender.equals("M") && !gender.equals("F")) {
                JOptionPane.showMessageDialog(this, "Gender must be M or F.");
                return;
            }
            if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                JOptionPane.showMessageDialog(this, "Invalid email format.");
                return;
            }
            if (!phone.matches("\\d{10,11}")) {
                JOptionPane.showMessageDialog(this, "Phone must be 10-11 digits.");
                return;
            }
            if (age < 1 || age > 120) {
                JOptionPane.showMessageDialog(this, "Age must be between 1 and 120.");
                return;
            }

            user.setName(name);
            user.setPassword(password);
            user.setGender(gender);
            user.setEmail(email);
            user.setPhone(phone);
            user.setAge(age);

            for (int i = 0; i < DataIO.allStaffs.size(); i++) {
                if (DataIO.allStaffs.get(i).getUsername().equals(user.getUsername())) {
                    DataIO.allStaffs.set(i, (Staff) user);
                    break;
                }
            }

            DataIO.write();
            saved = true;
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid age.");
        }
    }

    public boolean isSaved() {
        return saved;
    }
}

