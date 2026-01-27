package Manager;

import main.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * This window lets managers manage staff and doctor accounts
 * They can add, update, and delete users
 * They can also search and filter users by role
 */
public class ManageUsers extends JFrame {
    // Table components for showing users
    private JTable userTable;           
    private DefaultTableModel tableModel; 
    
    // Buttons for different actions
    private JButton addBtn;
    private JButton updateBtn;
    private JButton deleteBtn;
    private JButton refreshBtn;
    private JButton backBtn;
    
    // Search and filter components
    private JTextField searchField;
    private JComboBox<String> roleCombo;
    
    // Data storage
    private ArrayList<User> allUsers;
    private User currentUser;
    private JFrame parentFrame;
    /**
     * Constructor when no user or parent window is provided
     * This creates the user management window
     */
    public ManageUsers() {
        this.currentUser = null;
        this.parentFrame = null;
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        loadUsers();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Manage Users");
        setSize(800, 600);
        setLocationRelativeTo(null);
    }
    
    /**
     * Constructor when a user is logged in
     * @param currentUser - The manager who is logged in
     */
    public ManageUsers(User currentUser) {
        this.currentUser = currentUser; // Store the logged in manager
        this.parentFrame = null;        // No parent window
        initializeComponents();         // Create all buttons and table
        setupLayout();                 // Arrange everything on screen
        setupEventHandlers();          // Make buttons work when clicked
        loadUsers();                   // Load user data into table
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close window when X is clicked
        setTitle("Manage Users");
        setSize(800, 600);             // Make window big enough for table
        setLocationRelativeTo(null);   // Center window on screen
    }
    
    /**
     * Constructor when called from main dashboard
     * This allows going back to the dashboard
     * @param currentUser - The manager who is logged in
     * @param parent - The main dashboard window
     */
    public ManageUsers(User currentUser, JFrame parent) {
        this.currentUser = currentUser; 
        this.parentFrame = parent;      
        initializeComponents();         
        setupLayout();
        setupEventHandlers();
        loadUsers();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Manage Users");
        setSize(800, 600);
        setLocationRelativeTo(null);
    }
    
    private void initializeComponents() {
        // Set up the table columns for displaying users
        String[] columns = {"User ID", "Username", "Name", "Role", "Gender", "Email", "Phone", "Age"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only so users can't edit it directly
            }
        };
        userTable = new JTable(tableModel);
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Only select one row at a time
        userTable.getTableHeader().setReorderingAllowed(false); // Don't let users move columns
        
        // Create all the action buttons
        addBtn = new JButton("Add User");
        updateBtn = new JButton("Update User");
        deleteBtn = new JButton("Delete User");
        refreshBtn = new JButton("Refresh");
        backBtn = new JButton("Back to Dashboard");
        
        // Create search and filter components
        searchField = new JTextField(15);  // Text field for searching
        roleCombo = new JComboBox<>(new String[]{"All", "Manager", "Staff", "Doctor"}); // Filter by role
        
        // Style buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 12);
        addBtn.setFont(buttonFont);
        updateBtn.setFont(buttonFont);
        deleteBtn.setFont(buttonFont);
        refreshBtn.setFont(buttonFont);
        backBtn.setFont(buttonFont);
        
        addBtn.setBackground(new Color(34, 139, 34));
        updateBtn.setBackground(new Color(255, 140, 0));
        deleteBtn.setBackground(new Color(220, 20, 60));
        refreshBtn.setBackground(new Color(70, 130, 180));
        backBtn.setBackground(new Color(105, 105, 105)); // Gray color for back button
        
        addBtn.setForeground(Color.WHITE);
        updateBtn.setForeground(Color.WHITE);
        deleteBtn.setForeground(Color.WHITE);
        refreshBtn.setForeground(Color.WHITE);
        backBtn.setForeground(Color.WHITE);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(25, 25, 112));
        JLabel titleLabel = new JLabel("User Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        
        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(new Color(240, 248, 255));
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(new JLabel("Role:"));
        searchPanel.add(roleCombo);
        searchPanel.add(refreshBtn);
        
        // Table panel
        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setPreferredSize(new Dimension(750, 400));
        
        // Button panel
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(new Color(240, 248, 255));
        
        // Left side buttons
        JPanel leftButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftButtonPanel.setBackground(new Color(240, 248, 255));
        leftButtonPanel.add(addBtn);
        leftButtonPanel.add(updateBtn);
        leftButtonPanel.add(deleteBtn);
        
        // Right side back button
        JPanel rightButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightButtonPanel.setBackground(new Color(240, 248, 255));
        rightButtonPanel.add(backBtn);
        
        buttonPanel.add(leftButtonPanel, BorderLayout.WEST);
        buttonPanel.add(rightButtonPanel, BorderLayout.EAST);
        
        // Create center panel to hold search and table
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(searchPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(titlePanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddUserDialog();
            }
        });
        
        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = userTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(ManageUsers.this, "Please select a user to update.");
                    return;
                }
                showUpdateUserDialog(selectedRow);
            }
        });
        
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = userTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(ManageUsers.this, "Please select a user to delete.");
                    return;
                }
                deleteUser(selectedRow);
            }
        });
        
        refreshBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadUsers();
            }
        });
        
        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterUsers();
            }
        });
        
        roleCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterUsers();
            }
        });
        
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Just close this window, don't open new ManagerMain
                dispose();
            }
        });
    }
    
    private void loadUsers() {
        allUsers = new ArrayList<>();
        
        // Add managers
        for (Manager manager : main.DataIO.allManagers) {
            allUsers.add(manager);
        }
        
        // Add staff
        for (User staff : main.DataIO.allStaffs) {
            allUsers.add(staff);
        }
        
        // Add doctors
        for (User doctor : main.DataIO.allDoctors) {
            allUsers.add(doctor);
        }
        
        // Don't add customers as per requirements
        updateTable();
    }
    
    private void updateTable() {
        tableModel.setRowCount(0);
        for (User user : allUsers) {
            String role = determineUserRole(user);
            tableModel.addRow(new Object[]{
                user.getUserId(),
                user.getUsername(),
                user.getName(),
                role,
                user.getGender(),
                user.getEmail(),
                user.getPhone(),
                user.getAge()
            });
        }
    }
    
    private String determineUserRole(User user) {
        if (user instanceof Manager) {
            return "Manager";
        } else if (user.getType() != null) {
            return user.getType();
        }
        return "Unknown";
    }
    
    private void filterUsers() {
        String searchText = searchField.getText().toLowerCase();
        String selectedRole = (String) roleCombo.getSelectedItem();
        
        tableModel.setRowCount(0);
        for (User user : allUsers) {
            String role = determineUserRole(user);
            
            boolean matchesSearch = searchText.isEmpty() || 
                user.getUsername().toLowerCase().contains(searchText) ||
                user.getName().toLowerCase().contains(searchText) ||
                user.getEmail().toLowerCase().contains(searchText);
            
            boolean matchesRole = selectedRole.equals("All") || role.equals(selectedRole);
            
            if (matchesSearch && matchesRole) {
                tableModel.addRow(new Object[]{
                    user.getUserId(),
                    user.getUsername(),
                    user.getName(),
                    role,
                    user.getGender(),
                    user.getEmail(),
                    user.getPhone(),
                    user.getAge()
                });
            }
        }
    }
    
    private void showAddUserDialog() {
        JDialog dialog = new JDialog(this, "Add New User", true);
        dialog.setSize(400, 500);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Role selection
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Role:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> roleCombo = new JComboBox<>(new String[]{"Manager", "Staff", "Doctor"});
        panel.add(roleCombo, gbc);
        
        // Username
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        JTextField usernameField = new JTextField(15);
        panel.add(usernameField, gbc);
        
        // Password
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);
        
        // Name
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        JTextField nameField = new JTextField(15);
        panel.add(nameField, gbc);
        
        // Gender
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Gender:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> genderCombo = new JComboBox<>(new String[]{"M", "F"});
        panel.add(genderCombo, gbc);
        
        // Email
        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        JTextField emailField = new JTextField(15);
        panel.add(emailField, gbc);
        
        // Phone
        gbc.gridx = 0; gbc.gridy = 6;
        panel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        JTextField phoneField = new JTextField(15);
        panel.add(phoneField, gbc);
        
        // Age
        gbc.gridx = 0; gbc.gridy = 7;
        panel.add(new JLabel("Age:"), gbc);
        gbc.gridx = 1;
        JTextField ageField = new JTextField(15);
        panel.add(ageField, gbc);
        
        // Buttons
        gbc.gridx = 0; gbc.gridy = 8;
        JButton saveBtn = new JButton("Save");
        panel.add(saveBtn, gbc);
        gbc.gridx = 1;
        JButton cancelBtn = new JButton("Cancel");
        panel.add(cancelBtn, gbc);
        
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String role = (String) roleCombo.getSelectedItem();
                    String username = usernameField.getText();
                    String password = new String(passwordField.getPassword());
                    String name = nameField.getText();
                    String gender = (String) genderCombo.getSelectedItem();
                    String email = emailField.getText();
                    String phone = phoneField.getText();
                    int age = Integer.parseInt(ageField.getText());
                    
                    if (username.isEmpty() || password.isEmpty() || name.isEmpty() || 
                        email.isEmpty() || phone.isEmpty()) {
                        JOptionPane.showMessageDialog(dialog, "Please fill in all fields.");
                        return;
                    }
                    
                    // Generate ID
                    String userId = generateUserId(role);
                    
                    // Create user
                    User newUser;
                    if (role.equals("Manager")) {
                        newUser = new Manager(userId, username, password, name, gender, email, phone, age);
                        main.DataIO.allManagers.add((Manager) newUser);
                    } else if (role.equals("Staff")) {
                        main.Staff newStaff = new main.Staff(userId, username, password, name, gender, email, phone, age);
                        main.DataIO.allStaffs.add(newStaff);
                    } else {
                        main.Doctor newDoctor = new main.Doctor(userId, username, password, name, gender, email, phone, age);
                        main.DataIO.allDoctors.add(newDoctor);
                    }
                    
                    // Save to file
                    main.DataIO.write();
                    
                    JOptionPane.showMessageDialog(dialog, "User added successfully!");
                    dialog.dispose();
                    loadUsers();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "Please enter a valid age.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Error adding user: " + ex.getMessage());
                }
            }
        });
        
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private void showUpdateUserDialog(int selectedRow) {
        User selectedUser = allUsers.get(selectedRow);
        String role = determineUserRole(selectedUser);
        
        // Manager can only edit their own account
        if (role.equals("Manager")) {
            if (currentUser == null || !currentUser.getUserId().equals(selectedUser.getUserId())) {
                JOptionPane.showMessageDialog(this, "Manager can only edit their own account.");
                return;
            }
        }
        
        JDialog dialog = new JDialog(this, "Update User - " + role, true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Set uniform text field size
        Dimension fieldSize = new Dimension(200, 25);
        
        // Username (editable)
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        JTextField usernameField = new JTextField(selectedUser.getUsername());
        usernameField.setPreferredSize(fieldSize);
        panel.add(usernameField, gbc);
        
        // Password change section (only for managers editing their own account)
        boolean canChangePassword = role.equals("Manager") && currentUser != null && 
                                   currentUser.getUserId().equals(selectedUser.getUserId());
        
        final JPasswordField currentPasswordField;
        final JPasswordField newPasswordField;
        final JPasswordField confirmPasswordField;
        
        if (canChangePassword) {
            // Current password
            gbc.gridx = 0; gbc.gridy = 1;
            panel.add(new JLabel("Current Password:"), gbc);
            gbc.gridx = 1;
            currentPasswordField = new JPasswordField();
            currentPasswordField.setPreferredSize(fieldSize);
            panel.add(currentPasswordField, gbc);
            
            // New password
            gbc.gridx = 0; gbc.gridy = 2;
            panel.add(new JLabel("New Password:"), gbc);
            gbc.gridx = 1;
            newPasswordField = new JPasswordField();
            newPasswordField.setPreferredSize(fieldSize);
            panel.add(newPasswordField, gbc);
            
            // Confirm new password
            gbc.gridx = 0; gbc.gridy = 3;
            panel.add(new JLabel("Confirm New Password:"), gbc);
            gbc.gridx = 1;
            confirmPasswordField = new JPasswordField();
            confirmPasswordField.setPreferredSize(fieldSize);
            panel.add(confirmPasswordField, gbc);
        } else {
            currentPasswordField = null;
            newPasswordField = null;
            confirmPasswordField = null;
        }
        
        // Name (editable)
        gbc.gridx = 0; gbc.gridy = canChangePassword ? 4 : 1;
        panel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        JTextField nameField = new JTextField(selectedUser.getName());
        nameField.setPreferredSize(fieldSize);
        panel.add(nameField, gbc);
        
        // Email (editable)
        gbc.gridx = 0; gbc.gridy = canChangePassword ? 5 : 2;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        JTextField emailField = new JTextField(selectedUser.getEmail());
        emailField.setPreferredSize(fieldSize);
        panel.add(emailField, gbc);
        
        // Phone (editable)
        gbc.gridx = 0; gbc.gridy = canChangePassword ? 6 : 3;
        panel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        JTextField phoneField = new JTextField(selectedUser.getPhone());
        phoneField.setPreferredSize(fieldSize);
        panel.add(phoneField, gbc);
        
        // Age (editable)
        gbc.gridx = 0; gbc.gridy = canChangePassword ? 7 : 4;
        panel.add(new JLabel("Age:"), gbc);
        gbc.gridx = 1;
        JTextField ageField = new JTextField(String.valueOf(selectedUser.getAge()));
        ageField.setPreferredSize(fieldSize);
        panel.add(ageField, gbc);
        
        // Buttons
        gbc.gridx = 0; gbc.gridy = canChangePassword ? 8 : 5;
        JButton saveBtn = new JButton("Save");
        panel.add(saveBtn, gbc);
        gbc.gridx = 1;
        JButton cancelBtn = new JButton("Cancel");
        panel.add(cancelBtn, gbc);
        
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String username = usernameField.getText();
                    String name = nameField.getText();
                    String email = emailField.getText();
                    String phone = phoneField.getText();
                    int age = Integer.parseInt(ageField.getText());
                    
                    if (username.isEmpty() || name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                        JOptionPane.showMessageDialog(dialog, "Please fill in all fields.");
                        return;
                    }
                    
                    // Handle password change if applicable
                    if (canChangePassword && currentPasswordField != null && newPasswordField != null && confirmPasswordField != null) {
                        String currentPassword = new String(currentPasswordField.getPassword());
                        String newPassword = new String(newPasswordField.getPassword());
                        String confirmPassword = new String(confirmPasswordField.getPassword());
                        
                        // Check if password fields are not empty
                        if (!currentPassword.isEmpty() || !newPassword.isEmpty() || !confirmPassword.isEmpty()) {
                            // Validate current password
                            if (!currentPassword.equals(selectedUser.getPassword())) {
                                JOptionPane.showMessageDialog(dialog, "Current password is incorrect.");
                                return;
                            }
                            
                            // Validate new password
                            if (newPassword.isEmpty()) {
                                JOptionPane.showMessageDialog(dialog, "New password cannot be empty.");
                                return;
                            }
                            
                            if (newPassword.length() < 6) {
                                JOptionPane.showMessageDialog(dialog, "New password must be at least 6 characters long.");
                                return;
                            }
                            
                            // Validate password confirmation
                            if (!newPassword.equals(confirmPassword)) {
                                JOptionPane.showMessageDialog(dialog, "New password and confirmation do not match.");
                                return;
                            }
                            
                            // Update password
                            selectedUser.setPassword(newPassword);
                        }
                    }
                    
                    // Update other fields
                    selectedUser.setUsername(username);
                    selectedUser.setName(name);
                    selectedUser.setEmail(email);
                    selectedUser.setPhone(phone);
                    selectedUser.setAge(age);
                    
                    // Save to file
                    main.DataIO.write();
                    
                    JOptionPane.showMessageDialog(dialog, "User updated successfully!");
                    dialog.dispose();
                    loadUsers();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "Please enter a valid age.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Error updating user: " + ex.getMessage());
                }
            }
        });
        
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private void deleteUser(int selectedRow) {
        User selectedUser = allUsers.get(selectedRow);
        String role = determineUserRole(selectedUser);
        
        // Cannot delete Manager accounts
        if (role.equals("Manager")) {
            JOptionPane.showMessageDialog(this, "Cannot delete Manager accounts.");
            return;
        }
        
        int choice = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete user: " + selectedUser.getName() + "?",
            "Delete Confirmation",
            JOptionPane.YES_NO_OPTION
        );
        
        if (choice == JOptionPane.YES_OPTION) {
            try {
                if (role.equals("Staff")) {
                    main.DataIO.allStaffs.remove(selectedUser);
                } else if (role.equals("Doctor")) {
                    main.DataIO.allDoctors.remove(selectedUser);
                }
                
                main.DataIO.write();
                JOptionPane.showMessageDialog(this, "User deleted successfully!");
                loadUsers();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error deleting user: " + ex.getMessage());
            }
        }
    }
    
    private String generateUserId(String role) {
        if (role.equals("Manager")) {
            return main.DataIO.generateManagerId();
        } else if (role.equals("Staff")) {
            int maxId = 0;
            for (User s : main.DataIO.allStaffs) {
                try {
                    int num = Integer.parseInt(s.getUserId().substring(1));
                    if (num > maxId) maxId = num;
                } catch (Exception e) {}
            }
            return String.format("S%04d", maxId + 1);
        } else {
            int maxId = 0;
            for (User d : main.DataIO.allDoctors) {
                try {
                    int num = Integer.parseInt(d.getUserId().substring(1));
                    if (num > maxId) maxId = num;
                } catch (Exception e) {}
            }
            return String.format("D%04d", maxId + 1);
        }
    }
}