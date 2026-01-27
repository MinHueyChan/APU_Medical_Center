package Manager;

import main.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Main dashboard for managers to access different functions
public class ManagerMain extends JFrame {
    private JButton manageUsersBtn;
    private JButton viewAppointmentsBtn;
    private JButton viewFeedbacksBtn;
    private JButton generateReportsBtn;
    private JButton logoutBtn;
    private JLabel welcomeLabel;
    private Manager currentManager;
    
    // Constructor without manager
    public ManagerMain() {
        this.currentManager = null;
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("APU Medical Center - Manager Dashboard");
        setSize(650, 500);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    // Constructor with manager
    public ManagerMain(Manager currentManager) {
        this.currentManager = currentManager;
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("APU Medical Center - Manager Dashboard");
        setSize(650, 500);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    // Create all UI components
    private void initializeComponents() {
        // Create welcome label with manager's name
        String managerName = (currentManager != null) ? currentManager.getName() : "Manager";
        welcomeLabel = new JLabel("Welcome, " + managerName);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Create buttons with icons
        manageUsersBtn = createButtonWithIcon("Manage Users", "user_icon.png");
        viewAppointmentsBtn = createButtonWithIcon("View All Appointments", "appointment_icon.png");
        viewFeedbacksBtn = createButtonWithIcon("View Feedbacks", "Feedback_icon.png");
        generateReportsBtn = createButtonWithIcon("Generate Reports", "Report_icon.png");
        logoutBtn = new JButton("Logout");
        
        // Style the buttons with medium fonts
        Font buttonFont = new Font("Arial", Font.BOLD, 13);
        manageUsersBtn.setFont(buttonFont);
        viewAppointmentsBtn.setFont(buttonFont);
        viewFeedbacksBtn.setFont(buttonFont);
        generateReportsBtn.setFont(buttonFont);
        logoutBtn.setFont(new Font("Arial", Font.BOLD, 11));
        
        // Set button colors
        manageUsersBtn.setBackground(new Color(173, 216, 230)); // light blue
        viewAppointmentsBtn.setBackground(new Color(173, 216, 230)); // light blue
        viewFeedbacksBtn.setBackground(new Color(173, 216, 230)); // light blue
        generateReportsBtn.setBackground(new Color(173, 216, 230)); // light blue
        logoutBtn.setForeground(new Color(220, 20, 60)); // red text
        logoutBtn.setBackground(new Color(255,255,255));
        
        manageUsersBtn.setForeground(new Color(25, 25, 112)); // dark blue text
        viewAppointmentsBtn.setForeground(new Color(25, 25, 112)); // dark blue text
        viewFeedbacksBtn.setForeground(new Color(25, 25, 112)); // dark blue text
        generateReportsBtn.setForeground(new Color(25, 25, 112)); // dark blue text
        
        // Set button size
        Dimension buttonSize = new Dimension(230, 80);
        manageUsersBtn.setPreferredSize(buttonSize);
        viewAppointmentsBtn.setPreferredSize(buttonSize);
        viewFeedbacksBtn.setPreferredSize(buttonSize);
        generateReportsBtn.setPreferredSize(buttonSize);
        logoutBtn.setPreferredSize(new Dimension(80, 35));
        
        // Add rounded border effect
        addRoundedButtonEffect(manageUsersBtn);
        addRoundedButtonEffect(viewAppointmentsBtn);
        addRoundedButtonEffect(viewFeedbacksBtn);
        addRoundedButtonEffect(generateReportsBtn);
        addLogoutButtonEffect(logoutBtn);
        
        // Ensure buttons are visible
        manageUsersBtn.setVisible(true);
        viewAppointmentsBtn.setVisible(true);
        viewFeedbacksBtn.setVisible(true);
        generateReportsBtn.setVisible(true);
        logoutBtn.setVisible(true);
    }
    
    // Create button with icon
    private JButton createButtonWithIcon(String text, String iconPath) {
        JButton button = new JButton();
        button.setText(text);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
            if (icon.getIconWidth() > 0) {
        // Scale the icon to be medium size
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(28, 28, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(scaledImg));
            }
        } catch (Exception e) {
            System.out.println("Could not load icon: " + iconPath);
        }
        
        return button;
    }
    
    // Add rounded border effect to buttons
    private void addRoundedButtonEffect(JButton button) {
        button.setFocusPainted(false);
        button.setBorderPainted(true); // Enable border painting
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 1), // thin black border
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Color currentColor = button.getBackground();
                if (currentColor.equals(new Color(173, 216, 230))) {
                    button.setBackground(new Color(200, 230, 250)); // brighter light blue
                } else if (currentColor.equals(new Color(255, 182, 193))) {
                    button.setBackground(new Color(255, 200, 210)); // brighter light pink
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (button == manageUsersBtn || button == viewAppointmentsBtn || 
                    button == viewFeedbacksBtn || button == generateReportsBtn) {
                    button.setBackground(new Color(173, 216, 230)); 
                } else if (button == logoutBtn) {
                    button.setBackground(new Color(255, 182, 193)); 
                }
            }
        });
    }
    
    private void addLogoutButtonEffect(JButton button) {
        button.setFocusPainted(false);
        button.setBorderPainted(true); // Enable border painting
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 200, 210)); 
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 255, 255)); 
            }
        });
    }
    
    // Arrange components on screen
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        getContentPane().setBackground(new Color(240, 248, 255));

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(25, 25, 112));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JPanel leftSection = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftSection.setBackground(new Color(25, 25, 112));
        
        JLabel titleLabel = new JLabel("Manager Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        welcomeLabel.setForeground(new Color(200, 200, 200));

        JPanel titleStack = new JPanel();
        titleStack.setLayout(new BoxLayout(titleStack, BoxLayout.Y_AXIS));
        titleStack.setBackground(new Color(25, 25, 112));
        titleStack.add(titleLabel);
        titleStack.add(Box.createVerticalStrut(2));
        titleStack.add(welcomeLabel);
        
        leftSection.add(titleStack);

        JPanel rightSection = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        rightSection.setBackground(new Color(25, 25, 112));
        rightSection.add(logoutBtn);
        
        titlePanel.add(leftSection, BorderLayout.WEST);
        titlePanel.add(rightSection, BorderLayout.EAST);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(240, 248, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.NONE; // Don't stretch buttons
        gbc.anchor = GridBagConstraints.CENTER; // Center buttons in their cells
        
        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(manageUsersBtn, gbc);
        
        gbc.gridx = 1; gbc.gridy = 0;
        mainPanel.add(viewAppointmentsBtn, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(viewFeedbacksBtn, gbc);
        
        gbc.gridx = 1; gbc.gridy = 1;
        mainPanel.add(generateReportsBtn, gbc);
        
        add(titlePanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }
    
    // Set up button click events
    private void setupEventHandlers() {
        manageUsersBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ManageUsers(currentManager).setVisible(true);
            }
        });
        
        viewAppointmentsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewAppointments().setVisible(true);
            }
        });
        
        viewFeedbacksBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewFeedbacks().setVisible(true);
            }
        });
        
        generateReportsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GenerateReports().setVisible(true);
            }
        });
        
        logoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                    ManagerMain.this,
                    "Are you sure you want to logout?",
                    "Logout Confirmation",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.PLAIN_MESSAGE
                );
                if (choice == JOptionPane.YES_OPTION) {
                    new main.LoginPage().setVisible(true);
                    dispose();
                }
            }
        });
    }
    
    public static void main(String[] args) {
        // Load data first
        DataIO.read();
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ManagerMain().setVisible(true);
                //new LoginPage().setVisible(true);
            }
        });
    }
}

