package Staff;

import main.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CustomerForm extends JDialog {

    private JTextField usernameField;
    private JTextField passwordField;
    private JTextField nameField;
    private JTextField genderField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField ageField;

    private boolean saved = false;
    private Customer customer; 

    public CustomerForm(JFrame parent, Customer customer) {
        super(parent, (customer == null ? "Create Customer" : "Update Customer"), true);
        this.customer = customer;

        setLayout(new BorderLayout(50, 50));

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 20, 5));
        formPanel.setBorder(new EmptyBorder(20, 40, 20, 40)); 

        usernameField = new JTextField();
        passwordField = new JTextField();
        nameField = new JTextField();
        genderField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();
        ageField = new JTextField();

        formPanel.add(new JLabel("Username:"));
        formPanel.add(usernameField);
        formPanel.add(new JLabel("Password:"));
        formPanel.add(passwordField);
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Gender (M/F):"));
        formPanel.add(genderField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Phone:"));
        formPanel.add(phoneField);
        formPanel.add(new JLabel("Age:"));
        formPanel.add(ageField);

        if (customer != null) {
            usernameField.setText(customer.getUsername());
            usernameField.setEditable(false); 
            passwordField.setText(customer.getPassword());
            nameField.setText(customer.getName());
            genderField.setText(customer.getGender());
            emailField.setText(customer.getEmail());
            phoneField.setText(customer.getPhone());
            ageField.setText(String.valueOf(customer.getAge()));
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");

        saveBtn.addActionListener(e -> saveCustomer());
        cancelBtn.addActionListener(e -> dispose());

        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setSize(500, getHeight()); 
        setLocationRelativeTo(parent);
    }

    private void saveCustomer() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getText()).trim();
        String name = nameField.getText().trim();
        String gender = genderField.getText().trim().toUpperCase();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String ageStr = ageField.getText().trim();

        StringBuilder errors = new StringBuilder();

        if (username.isEmpty() || password.isEmpty() || name.isEmpty() ||
            gender.isEmpty() || email.isEmpty() || phone.isEmpty() || ageStr.isEmpty()) {
            errors.append("All fields are required.\n");
        }

        if (customer == null && DataIO.checkCustomerName(username) != null) {
            errors.append("Username already exists.\n");
        }

        if (!gender.equals("M") && !gender.equals("F")) {
            errors.append("Gender must be M or F.\n");
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            errors.append("Invalid email format. Please follow email format: username@mail.com\n");
        }

        if (!phone.matches("\\d{10,11}")) {
            errors.append("Phone must be 10-11 digits.\n");
        }

        int age = -1;
        try {
            age = Integer.parseInt(ageStr);
            if (age < 1 || age > 120) {
                errors.append("Age must be between 1 and 120.\n");
            }
        } catch (NumberFormatException e) {
            errors.append("Invalid age.\n");
        }

        if (errors.length() > 0) {
            JOptionPane.showMessageDialog(this, errors.toString(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (customer == null) { 
            String newId = DataIO.generateCustomerId();
            Customer newCustomer = new Customer(newId, username, password, name, gender, email, phone, age);
            DataIO.allCustomers.add(newCustomer);
        } else { 
            customer.setPassword(password);
            customer.setName(name);
            customer.setGender(gender);
            customer.setEmail(email);
            customer.setPhone(phone);
            customer.setAge(age);
        }

        DataIO.write();
        saved = true;
        JOptionPane.showMessageDialog(this, "Customer saved successfully!");
        dispose();
    }

    public boolean isSaved() {
        return saved;
    }
}
