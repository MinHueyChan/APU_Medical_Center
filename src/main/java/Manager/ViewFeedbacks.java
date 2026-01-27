package Manager;

import main.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

// Window to view appointments with feedback or comments
public class ViewFeedbacks extends JFrame {
    private JTable feedbackTable;
    private DefaultTableModel tableModel;
    private JButton refreshBtn, exportBtn, backBtn;
    private JLabel statsLabel;
    private ArrayList<Feedback> allFeedbacks;
    
    // Constructor
    public ViewFeedbacks() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        loadFeedbacks();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("View Appointments with Feedback/Comments");
        setSize(1000, 700);
        setLocationRelativeTo(null);
    }
    
    // Create UI components
    private void initializeComponents() {
        // Table setup - Updated column headers to reflect the new approach
        String[] columns = {"Appointment ID", "Customer", "Doctor", "Appointment Type", 
                           "Feedback", "Comment", "Rating", "Date", "Time"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        feedbackTable = new JTable(tableModel);
        feedbackTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        feedbackTable.getTableHeader().setReorderingAllowed(false);
        
        // Buttons
        refreshBtn = new JButton("Refresh");
        exportBtn = new JButton("Export to File");
        backBtn = new JButton("Back to Manager Dashboard");
        
        // Stats label
        statsLabel = new JLabel();
        statsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        statsLabel.setForeground(new Color(25, 25, 112));
        
        // Style buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 12);
        refreshBtn.setFont(buttonFont);
        exportBtn.setFont(buttonFont);
        backBtn.setFont(buttonFont);
        
        refreshBtn.setBackground(new Color(34, 139, 34));
        exportBtn.setBackground(new Color(70, 130, 180));
        backBtn.setBackground(new Color(220, 20, 60));
        
        refreshBtn.setForeground(Color.WHITE);
        exportBtn.setForeground(Color.WHITE);
        backBtn.setForeground(Color.WHITE);
    }
    
    // Arrange components on screen
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(25, 25, 112));
        JLabel titleLabel = new JLabel("Appointments with Feedback/Comments");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(new Color(240, 248, 255));
        buttonPanel.add(refreshBtn);
        buttonPanel.add(exportBtn);
        
        // Table panel
        JScrollPane scrollPane = new JScrollPane(feedbackTable);
        scrollPane.setPreferredSize(new Dimension(950, 400));
        
        // Stats panel
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statsPanel.setBackground(new Color(240, 248, 255));
        statsPanel.add(statsLabel);
        
        // Back button panel
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        backPanel.setBackground(new Color(240, 248, 255));
        backPanel.add(backBtn);
        
        // Main content panel
        JPanel mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.add(buttonPanel, BorderLayout.NORTH);
        mainContentPanel.add(scrollPane, BorderLayout.CENTER);
        mainContentPanel.add(statsPanel, BorderLayout.SOUTH);
        
        add(titlePanel, BorderLayout.NORTH);
        add(mainContentPanel, BorderLayout.CENTER);
        add(backPanel, BorderLayout.SOUTH);
    }
    
    // Set up button click events
    private void setupEventHandlers() {
        refreshBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadFeedbacks();
            }
        });
        
        exportBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportFeedbacks();
            }
        });
        
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void loadFeedbacks() {
        allFeedbacks = new ArrayList<>();

        File feedbackFile = new File("feedback.txt");
        if (feedbackFile.exists()) {
            try (Scanner sc = new Scanner(feedbackFile)) {
                while (sc.hasNext()) {
                    String feedbackId = sc.nextLine();
                    String appointmentId = sc.nextLine();
                    String customerUsername = sc.nextLine();
                    String doctorUsername = sc.nextLine();
                    String feedbackText = sc.nextLine();
                    String ratingStr = sc.nextLine();
                    String date = sc.nextLine();
                    String time = sc.nextLine();
                    if (sc.hasNextLine()) sc.nextLine(); // Skip empty line
                    
                    Integer rating = null;
                    if (!ratingStr.isEmpty()) {
                        try {
                            rating = Integer.parseInt(ratingStr);
                        } catch (NumberFormatException e) {
                            rating = null;
                        }
                    }
                    
                    allFeedbacks.add(new Feedback(feedbackId, appointmentId, customerUsername, 
                                                doctorUsername, feedbackText, null, rating, date, time));
                }
            } catch (Exception e) {
                System.out.println("Error reading feedback data: " + e.getMessage());
            }
        }
        
        for (Appointment appointment : main.DataIO.allAppointments) {
            boolean hasFeedback = appointment.getFeedback() != null && !appointment.getFeedback().trim().isEmpty();
            boolean hasComment = appointment.getComment() != null && !appointment.getComment().trim().isEmpty();

            if (hasFeedback || hasComment) {
                String feedbackId = "A" + appointment.getAppointmentId(); // Changed prefix to 'A' for Appointment
                allFeedbacks.add(new Feedback(feedbackId, appointment.getAppointmentId(), 
                                            appointment.getCustomerUsername(), 
                                            appointment.getDoctorUsername() != null ? appointment.getDoctorUsername() : "N/A",
                                            hasFeedback ? appointment.getFeedback() : "No Feedback", 
                                            hasComment ? appointment.getComment() : "No Comment",
                                            appointment.getRating(), 
                                            appointment.getDate(), 
                                            appointment.getTime()));
            }
        }
        
        updateTable();
        updateStatistics();
    }
    
    private void updateTable() {
        tableModel.setRowCount(0);
        for (Feedback feedback : allFeedbacks) {
            // Get appointment type for this appointment
            String appointmentType = "N/A";
            for (Appointment appointment : main.DataIO.allAppointments) {
                if (appointment.getAppointmentId().equals(feedback.getAppointmentId())) {
                    appointmentType = appointment.getType();
                    break;
                }
            }
            
            tableModel.addRow(new Object[]{
                feedback.getAppointmentId(),
                feedback.getCustomerUsername(),
                feedback.getDoctorUsername(),
                appointmentType,
                feedback.getFeedbackText().equals("No Feedback") ? "No Feedback" : feedback.getFeedbackText(),
                feedback.getComment().equals("No Comment") ? "No Comment" : feedback.getComment(),
                feedback.getRating() != null ? feedback.getRating().toString() : "No Rating",
                feedback.getDate(),
                feedback.getTime()
            });
        }
    }

    private void updateStatistics() {
        int totalAppointments = allFeedbacks.size();
        int appointmentsWithFeedback = 0;
        int appointmentsWithComments = 0;
        int appointmentsWithBoth = 0;
        int positiveFeedbacks = 0;
        int negativeFeedbacks = 0;
        int neutralFeedbacks = 0;
        double totalRating = 0;
        int ratedFeedbacks = 0;
        
        for (Feedback feedback : allFeedbacks) {
            boolean hasFeedback = !feedback.getFeedbackText().equals("No Feedback");
            boolean hasComment = !feedback.getComment().equals("No Comment");
            
            if (hasFeedback) appointmentsWithFeedback++;
            if (hasComment) appointmentsWithComments++;
            if (hasFeedback && hasComment) appointmentsWithBoth++;
            
            if (feedback.getRating() != null) {
                ratedFeedbacks++;
                totalRating += feedback.getRating();
                
                if (feedback.isPositive()) {
                    positiveFeedbacks++;
                } else if (feedback.isNegative()) {
                    negativeFeedbacks++;
                } else {
                    neutralFeedbacks++;
                }
            }
        }
        
        double averageRating = ratedFeedbacks > 0 ? totalRating / ratedFeedbacks : 0;
        
        String stats = String.format(
            "Total Appointments: %d | With Feedback: %d | With Comments: %d | With Both: %d | Positive: %d | Negative: %d | Neutral: %d | Avg Rating: %.2f",
            totalAppointments, appointmentsWithFeedback, appointmentsWithComments, appointmentsWithBoth, 
            positiveFeedbacks, negativeFeedbacks, neutralFeedbacks, averageRating
        );
        
        statsLabel.setText(stats);
    }

    private void exportFeedbacks() {
        try {
            PrintWriter writer = new PrintWriter("appointments_with_feedback_comments_export.txt");
            writer.println("APU Medical Center - Appointments with Feedback/Comments Report");
            writer.println("Generated on: " + java.time.LocalDateTime.now());
            writer.println("=".repeat(60));
            writer.println();
            
            for (Feedback feedback : allFeedbacks) {
                // Get appointment type
                String appointmentType = "N/A";
                for (Appointment appointment : main.DataIO.allAppointments) {
                    if (appointment.getAppointmentId().equals(feedback.getAppointmentId())) {
                        appointmentType = appointment.getType();
                        break;
                    }
                }
                
                writer.println("Appointment ID: " + feedback.getAppointmentId());
                writer.println("Customer: " + feedback.getCustomerUsername());
                writer.println("Doctor: " + feedback.getDoctorUsername());
                writer.println("Appointment Type: " + appointmentType);
                writer.println("Feedback: " + (feedback.getFeedbackText().equals("No Feedback") ? "No Feedback" : feedback.getFeedbackText()));
                writer.println("Comment: " + (feedback.getComment().equals("No Comment") ? "No Comment" : feedback.getComment()));
                writer.println("Rating: " + (feedback.getRating() != null ? feedback.getRating() : "No Rating"));
                writer.println("Date: " + feedback.getDate());
                writer.println("Time: " + feedback.getTime());
                writer.println("-".repeat(40));
                writer.println();
            }
            
            writer.close();
            JOptionPane.showMessageDialog(this, "Appointments with feedback/comments exported to appointments_with_feedback_comments_export.txt successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error exporting data: " + e.getMessage());
        }
    }
}
