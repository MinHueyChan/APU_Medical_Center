package Manager;

import main.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

// Window to generate different types of reports
public class GenerateReports extends JFrame {
    private JTextArea reportArea;
    private JScrollPane scrollPane;
    private JButton appointmentSummaryBtn, doctorAppointmentsBtn, revenueReportBtn, 
                   doctorPerformanceBtn, feedbackSummaryBtn, exportBtn, clearBtn, createSampleDataBtn, refreshDataBtn, backBtn;
    private ManagerReport reportGenerator;
    
    // Constructor
    public GenerateReports() {
        reportGenerator = new ManagerReport();
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Generate Reports");
        setSize(1000, 800);
        setLocationRelativeTo(null);
        
        // Show initial status
        showDataStatus();
    }
    
    // Create UI components
    private void initializeComponents() {
        // Report area
        reportArea = new JTextArea();
        reportArea.setFont(new Font("Courier New", Font.PLAIN, 12));
        reportArea.setEditable(false);
        reportArea.setBackground(Color.WHITE);
        scrollPane = new JScrollPane(reportArea);
        scrollPane.setPreferredSize(new Dimension(950, 500));
        
        // Buttons
        appointmentSummaryBtn = new JButton("Appointment Summary");
        doctorAppointmentsBtn = new JButton("Appointments by Doctor");
        revenueReportBtn = new JButton("Revenue Report");
        doctorPerformanceBtn = new JButton("Doctor Performance");
        feedbackSummaryBtn = new JButton("Feedback Summary");
        exportBtn = new JButton("Export to File");
        clearBtn = new JButton("Clear");
        createSampleDataBtn = new JButton("Create Sample Data");
        refreshDataBtn = new JButton("Refresh Data");
        backBtn = new JButton("Back to Manager Dashboard");
        
        // Style buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 12);
        appointmentSummaryBtn.setFont(buttonFont);
        doctorAppointmentsBtn.setFont(buttonFont);
        revenueReportBtn.setFont(buttonFont);
        doctorPerformanceBtn.setFont(buttonFont);
        feedbackSummaryBtn.setFont(buttonFont);
        exportBtn.setFont(buttonFont);
        clearBtn.setFont(buttonFont);
        createSampleDataBtn.setFont(buttonFont);
        refreshDataBtn.setFont(buttonFont);
        backBtn.setFont(buttonFont);
        
        appointmentSummaryBtn.setBackground(new Color(70, 130, 180));
        doctorAppointmentsBtn.setBackground(new Color(70, 130, 180));
        revenueReportBtn.setBackground(new Color(70, 130, 180));
        doctorPerformanceBtn.setBackground(new Color(70, 130, 180));
        feedbackSummaryBtn.setBackground(new Color(70, 130, 180));
        exportBtn.setBackground(new Color(34, 139, 34));
        clearBtn.setBackground(new Color(220, 20, 60));
        createSampleDataBtn.setBackground(new Color(255, 140, 0));
        refreshDataBtn.setBackground(new Color(50, 205, 50));
        backBtn.setBackground(new Color(220, 20, 60));
        
        appointmentSummaryBtn.setForeground(Color.WHITE);
        doctorAppointmentsBtn.setForeground(Color.WHITE);
        revenueReportBtn.setForeground(Color.WHITE);
        doctorPerformanceBtn.setForeground(Color.WHITE);
        feedbackSummaryBtn.setForeground(Color.WHITE);
        exportBtn.setForeground(Color.WHITE);
        clearBtn.setForeground(Color.WHITE);
        createSampleDataBtn.setForeground(Color.WHITE);
        refreshDataBtn.setForeground(Color.WHITE);
        backBtn.setForeground(Color.WHITE);
    }
    
    // Arrange components on screen
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(25, 25, 112));
        JLabel titleLabel = new JLabel("Report Generator");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        
        // Button panel - using FlowLayout for better visibility
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(240, 248, 255));
        buttonPanel.add(appointmentSummaryBtn);
        buttonPanel.add(doctorAppointmentsBtn);
        buttonPanel.add(revenueReportBtn);
        buttonPanel.add(doctorPerformanceBtn);
        buttonPanel.add(feedbackSummaryBtn);
        buttonPanel.add(exportBtn);
        buttonPanel.add(clearBtn);
        buttonPanel.add(createSampleDataBtn);
        buttonPanel.add(refreshDataBtn);
        
        // Back button panel
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        backPanel.setBackground(new Color(240, 248, 255));
        backPanel.add(backBtn);
        
        // Create a main panel to hold buttons and scroll pane
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(titlePanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(backPanel, BorderLayout.SOUTH);
    }
    
    // Set up button click events
    private void setupEventHandlers() {
        appointmentSummaryBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateAppointmentSummaryReport();
            }
        });
        
        doctorAppointmentsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateDoctorAppointmentsReport();
            }
        });
        
        revenueReportBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateRevenueReport();
            }
        });
        
        doctorPerformanceBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateDoctorPerformanceReport();
            }
        });
        
        feedbackSummaryBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateFeedbackSummaryReport();
            }
        });
        
        exportBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportReport();
            }
        });
        
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reportArea.setText("");
            }
        });
        
        createSampleDataBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createSampleData();
            }
        });
        
        refreshDataBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshData();
            }
        });
        
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    private void generateAppointmentSummaryReport() {
        try {
            // Check if there's any data first
            if (DataIO.allAppointments.isEmpty()) {
                reportArea.setText("No appointment data available. Please create sample data first using the 'Create Sample Data' button.");
                return;
            }
            
            String report = reportGenerator.generateAppointmentSummaryReport();
            reportArea.setText(report);
        } catch (Exception e) {
            reportArea.setText("Error generating appointment summary report: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void generateDoctorAppointmentsReport() {
        try {
            if (DataIO.allAppointments.isEmpty()) {
                reportArea.setText("No appointment data available. Please create sample data first using the 'Create Sample Data' button.");
                return;
            }
            
            String report = reportGenerator.generateAppointmentsByDoctorReport();
            reportArea.setText(report);
        } catch (Exception e) {
            reportArea.setText("Error generating doctor appointments report: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void generateRevenueReport() {
        try {
            if (DataIO.allAppointments.isEmpty()) {
                reportArea.setText("No appointment data available. Please create sample data first using the 'Create Sample Data' button.");
                return;
            }
            
            String report = reportGenerator.generateRevenueReport();
            reportArea.setText(report);
        } catch (Exception e) {
            reportArea.setText("Error generating revenue report: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void generateDoctorPerformanceReport() {
        try {
            if (DataIO.allAppointments.isEmpty()) {
                reportArea.setText("No appointment data available. Please create sample data first using the 'Create Sample Data' button.");
                return;
            }
            
            String report = reportGenerator.generateDoctorPerformanceReport();
            reportArea.setText(report);
        } catch (Exception e) {
            reportArea.setText("Error generating doctor performance report: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void generateFeedbackSummaryReport() {
        try {
            if (DataIO.allAppointments.isEmpty()) {
                reportArea.setText("No appointment data available. Please create sample data first using the 'Create Sample Data' button.");
                return;
            }
            
            String report = reportGenerator.generateFeedbackSummaryReport();
            reportArea.setText(report);
        } catch (Exception e) {
            reportArea.setText("Error generating feedback summary report: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void exportReport() {
        if (reportArea.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No report to export. Please generate a report first.");
            return;
        }
        
        try {
            PrintWriter writer = new PrintWriter("report.txt");
            writer.println(reportArea.getText());
            writer.close();
            JOptionPane.showMessageDialog(this, "Report exported to report.txt successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error exporting report: " + e.getMessage());
        }
    }
    
    private void createSampleData() {
        try {
            // Create sample customers
            if (DataIO.allCustomers.isEmpty()) {
                DataIO.allCustomers.add(new Customer("C0001", "customer1", "12345", "John Smith", "M", "john@email.com", "0123456789", 35));
            }
            
            // Create sample appointments matching text file format
            if (DataIO.allAppointments.isEmpty()) {
                DataIO.allAppointments.add(new Appointment("A0001", "minhuey11", "2025-09-08", "09:30", "Medical checkup", "doc1", "S0001", "completed", 0.0, "body good", 1, "vg"));
                DataIO.allAppointments.add(new Appointment("A0002", "minhuey11", "2025-10-20", "09:20", "Medical checkup", "doc2", "S0002", "Cancellation In Progress", 0.0, "na", 3, "hehe"));
            }
            
            // Save the data
            DataIO.write();
            
            reportArea.setText("Sample data created successfully!\n\n" +
                "Created:\n" +
                "- " + DataIO.allCustomers.size() + " customers\n" +
                "- " + DataIO.allAppointments.size() + " appointments\n" +
                "- " + DataIO.allDoctors.size() + " doctors\n\n" +
                "You can now generate reports with this sample data.");
                
            JOptionPane.showMessageDialog(this, "Sample data created successfully!");
            
        } catch (Exception e) {
            reportArea.setText("Error creating sample data: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void showDataStatus() {
        StringBuilder status = new StringBuilder();
        status.append("=== DATA STATUS ===\n\n");
        status.append("Managers: ").append(DataIO.allManagers.size()).append("\n");
        status.append("Doctors: ").append(DataIO.allDoctors.size()).append("\n");
        status.append("Staff: ").append(DataIO.allStaffs.size()).append("\n");
        status.append("Customers: ").append(DataIO.allCustomers.size()).append("\n");
        status.append("Appointments: ").append(DataIO.allAppointments.size()).append("\n");
        status.append("Payments: ").append(DataIO.allPayments.size()).append("\n");
        status.append("Receipts: ").append(DataIO.allReceipts.size()).append("\n\n");
        
        if (DataIO.allAppointments.isEmpty()) {
            status.append("️NO APPOINTMENT DATA FOUND!\n");
        } else {
            status.append("Data available for report generation.\n");
            status.append("You can now generate reports using the buttons above.\n\n");
        }
        
        status.append("=== INSTRUCTIONS ===\n");
        status.append("1. Use 'Refresh Data' to reload from files\n");
        status.append("2. Use 'Export to File' to save reports\n");
        
        reportArea.setText(status.toString());
    }
    
    private void refreshData() {
        try {
            // Reload data from files
            DataIO.read();
            showDataStatus();
            JOptionPane.showMessageDialog(this, "Data refreshed successfully!");
        } catch (Exception e) {
            reportArea.setText("Error refreshing data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}