/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Doctor;

import main.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JButton;



/**
 *
 * @author User
 */
public class EnterChargesFeedbackPage extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(EnterChargesFeedbackPage.class.getName());
    private Appointment selectedAppointment = null;
    private ArrayList<Appointment> doctorAppointments = new ArrayList<>();
    private Doctor currentDoctor;
    

    // detail fields
    private JTextField txtAppointmentId;
    private JTextField txtCustomer;
    private JTextField txtDate;
    private JTextField txtTime;
    private JTextField txtStatus;
    private JTextField txtCharges;
    private JTextArea txtFeedback;
    private JTable tblAppointments;
    private JButton btnLoadSelected, btnUpdate;
    private javax.swing.JButton UpdateChargesBtn;
    private javax.swing.JButton UpdateFeedbackBtn;

    

    /**
     * Creates new form EnterChargesFeedbackPage
     */
    public EnterChargesFeedbackPage(Doctor currentDoctor) {
        this.currentDoctor = currentDoctor;
        DataIO.read();
        initComponents();
        loadAppointments();
        setLocationRelativeTo(null);
        panelForm.setVisible(false);
        
        if (txtCharges != null) {
            txtCharges.addKeyListener(new java.awt.event.KeyAdapter() {
                @Override
                public void keyTyped(java.awt.event.KeyEvent evt) {
                    char c = evt.getKeyChar();
                    if (!Character.isDigit(c) && c != '.' && c != KeyEvent.VK_BACK_SPACE) {
                        evt.consume();
                    }
                }
            });
        }
    }
    
    

    
    private void loadAppointments() {
        doctorAppointments.clear();
        for (Appointment a : DataIO.allAppointments) {
            if (
                a.getDoctorUsername() != null &&
                a.getDoctorUsername().equalsIgnoreCase(currentDoctor.getUsername()) &&
                a.getStatus() != null &&
                a.getStatus().equalsIgnoreCase("completed")
            ) {
                doctorAppointments.add(a);
            }
        }
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        for (Appointment a : doctorAppointments) {
            // Find payment status for this appointment
            String paymentStatus = "Pending"; // default
            for (Payment p : DataIO.allPayments) {
                if (p.getAppointmentId() != null && p.getAppointmentId().equals(a.getAppointmentId())) {
                    paymentStatus = p.getPaymentStatus();
                    break;
                }
            }
            
            model.addRow(new Object[]{
                    a.getAppointmentId(),
                    a.getCustomerUsername(),
                    a.getDate(),
                    a.getTime(),
                    a.getStatus(),
                    a.getCharges(),
                    a.getFeedback(),
                    paymentStatus
            });
        }
    }
    
    private void loadSelectedAppointment() {
        int row = jTable1.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select an appointment first.");
            return;
        }
        selectedAppointment = doctorAppointments.get(row);

        txtAppointmentId.setText(selectedAppointment.getAppointmentId());
        txtCustomer.setText(selectedAppointment.getCustomerUsername());
        txtDate.setText(selectedAppointment.getDate());
        txtTime.setText(selectedAppointment.getTime());
        txtStatus.setText(selectedAppointment.getStatus());
        txtCharges.setText(selectedAppointment.getCharges() == 0 ? "" : String.valueOf(selectedAppointment.getCharges()));
        txtFeedback.setText(selectedAppointment.getFeedback() == null ? "" : selectedAppointment.getFeedback());
        
        txtAppointmentId.setEditable(false);
        txtCustomer.setEditable(false);
        txtDate.setEditable(false);
        txtTime.setEditable(false);
        txtStatus.setEditable(false);

        
        panelForm.setVisible(true);
    }
    
    private void updateAppointment() {
        if (selectedAppointment == null) {
            JOptionPane.showMessageDialog(this, "No appointment loaded.");
            return;
        }

        String chargesStr = txtCharges.getText().trim();
        String feedback = txtFeedback.getText().trim();

        if (chargesStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Charges cannot be empty.");
            return;
        }

        double charges;
        try {
            charges = Double.parseDouble(chargesStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid charges value.");
            return;
        }

        // Update Appointment
        selectedAppointment.setCharges(charges);
        selectedAppointment.setFeedback(feedback.isEmpty() ? null : feedback);

        // Save Appointment
        DataIO.write();

        // Generate Payment
        String paymentId = "P" + (DataIO.allPayments.size() + 1);
        Payment payment = new Payment(paymentId,
                selectedAppointment.getAppointmentId(),
                charges,
                feedback.isEmpty() ? null : feedback,
                "Pending",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
        );

        DataIO.allPayments.add(payment);
        DataIO.write();

        JOptionPane.showMessageDialog(this, "Appointment updated and payment generated!");
        clearFields();
        loadAppointments();
    }

    private void clearFields() {
        selectedAppointment = null;
        txtAppointmentId.setText("");
        txtCustomer.setText("");
        txtDate.setText("");
        txtTime.setText("");
        txtStatus.setText("");
        txtCharges.setText("");
        txtFeedback.setText("");
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelTable = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        BacktoDashboard = new javax.swing.JButton();
        UpdateBtn = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        Search = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        SearchBtn = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        LogoutBtn = new javax.swing.JButton();
        LoadSelectedBtn = new javax.swing.JButton();
        panelForm = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Appointment ID", "Customer", "Date", "Time", "Status", "Charges", "Feedback", "Payment Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanelTableLayout = new javax.swing.GroupLayout(jPanelTable);
        jPanelTable.setLayout(jPanelTableLayout);
        jPanelTableLayout.setHorizontalGroup(
            jPanelTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanelTableLayout.setVerticalGroup(
            jPanelTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTableLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        BacktoDashboard.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        BacktoDashboard.setText("Back");
        BacktoDashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BacktoDashboardActionPerformed(evt);
            }
        });

        UpdateBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        UpdateBtn.setText("Update");
        UpdateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateBtnActionPerformed(evt);
            }
        });

        UpdateChargesBtn = new javax.swing.JButton();
        UpdateChargesBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        UpdateChargesBtn.setText("Update Charges");
        UpdateChargesBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateChargesBtnActionPerformed(evt);
            }
        });

        UpdateFeedbackBtn = new javax.swing.JButton();
        UpdateFeedbackBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        UpdateFeedbackBtn.setText("Update Feedback");
        UpdateFeedbackBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateFeedbackBtnActionPerformed(evt);
            }
        });

        Search.setText("Search");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        SearchBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        SearchBtn.setText("Search");
        SearchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Search)
                .addGap(18, 18, 18)
                .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SearchBtn)
                .addGap(49, 49, 49))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Search)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SearchBtn))
                .addGap(10, 10, 10))
        );

        jPanel4.setBackground(new java.awt.Color(70, 130, 180));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel1.setText("APU Medical Centre");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Enter Charges & Feedback");

        LogoutBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LogoutBtn.setText("Logout");
        LogoutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(168, 168, 168)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                .addComponent(LogoutBtn)
                .addGap(62, 62, 62))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(LogoutBtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        LoadSelectedBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LoadSelectedBtn.setText("Load Selected");
        LoadSelectedBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoadSelectedBtnActionPerformed(evt);
            }
        });

        // Initialize detail inputs
        javax.swing.JLabel lblAppointmentId = new javax.swing.JLabel();
        javax.swing.JLabel lblCustomer = new javax.swing.JLabel();
        javax.swing.JLabel lblDate = new javax.swing.JLabel();
        javax.swing.JLabel lblTime = new javax.swing.JLabel();
        javax.swing.JLabel lblStatus = new javax.swing.JLabel();
        javax.swing.JLabel lblCharges = new javax.swing.JLabel();
        javax.swing.JLabel lblFeedback = new javax.swing.JLabel();
        javax.swing.JScrollPane feedbackScrollPane = new javax.swing.JScrollPane();

        lblAppointmentId.setText("Appointment ID");
        lblCustomer.setText("Customer");
        lblDate.setText("Date");
        lblTime.setText("Time");
        lblStatus.setText("Status");
        lblCharges.setText("Charges");
        lblFeedback.setText("Feedback");

        txtAppointmentId = new javax.swing.JTextField();
        txtCustomer = new javax.swing.JTextField();
        txtDate = new javax.swing.JTextField();
        txtTime = new javax.swing.JTextField();
        txtStatus = new javax.swing.JTextField();
        txtCharges = new javax.swing.JTextField();
        txtFeedback = new javax.swing.JTextArea();
        txtFeedback.setLineWrap(true);
        txtFeedback.setWrapStyleWord(true);
        feedbackScrollPane.setViewportView(txtFeedback);
        feedbackScrollPane.setPreferredSize(new java.awt.Dimension(300, 90));

        javax.swing.GroupLayout panelFormLayout = new javax.swing.GroupLayout(panelForm);
        panelForm.setLayout(panelFormLayout);
        panelFormLayout.setHorizontalGroup(
            panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAppointmentId)
                    .addComponent(lblCustomer)
                    .addComponent(lblDate)
                    .addComponent(lblTime)
                    .addComponent(lblStatus)
                    .addComponent(lblCharges)
                    .addComponent(lblFeedback))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtAppointmentId)
                    .addComponent(txtCustomer)
                    .addComponent(txtDate)
                    .addComponent(txtTime)
                    .addComponent(txtStatus)
                    .addComponent(txtCharges)
                    .addComponent(feedbackScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelFormLayout.setVerticalGroup(
            panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAppointmentId)
                    .addComponent(txtAppointmentId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCustomer)
                    .addComponent(txtCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDate)
                    .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTime)
                    .addComponent(txtTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStatus)
                    .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCharges)
                    .addComponent(txtCharges, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFeedback)
                    .addComponent(feedbackScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelTable, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(LoadSelectedBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(UpdateChargesBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(UpdateFeedbackBtn)
                        .addGap(30, 30, 30)
                        .addComponent(panelForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BacktoDashboard)))
                .addContainerGap())
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(BacktoDashboard)
                        .addComponent(UpdateFeedbackBtn)
                        .addComponent(UpdateChargesBtn)
                        .addComponent(LoadSelectedBtn))
                    .addComponent(panelForm, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BacktoDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BacktoDashboardActionPerformed
        new DoctorDashboard(currentDoctor).setVisible(true);
        dispose();
    }//GEN-LAST:event_BacktoDashboardActionPerformed

    private void UpdateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateBtnActionPerformed
        if (selectedAppointment == null) {
            JOptionPane.showMessageDialog(this, "Please load an appointment first.");
            return;
        }

        // Preserve current user input so it doesn't get reset after validation blocks
        String currentChargesInput = txtCharges.getText();
        String currentFeedbackInput = txtFeedback.getText();

        // Find existing payment for this appointment (if any)
        Payment existingPayment = null;
        for (Payment p : DataIO.allPayments) {
            if (p.getAppointmentId() != null && p.getAppointmentId().equals(selectedAppointment.getAppointmentId())) {
                existingPayment = p;
                break;
            }
        }

        String chargesStr = txtCharges.getText().trim();
        String feedback = txtFeedback.getText().trim();

        // Check if user is trying to update charges
        boolean updatingCharges = !chargesStr.isEmpty();
        // Check if user is trying to update feedback
        boolean updatingFeedback = !feedback.isEmpty() || (selectedAppointment.getFeedback() != null && !selectedAppointment.getFeedback().equals(feedback));

        // Check PaymentStatus: if "Paid" → not allowed to enter charges or feedback
        if (existingPayment != null && existingPayment.getPaymentStatus() != null && existingPayment.getPaymentStatus().equalsIgnoreCase("paid")) {
            if (updatingCharges) {
                JOptionPane.showMessageDialog(this, "Appointment paid not allow to enter charges.");
                txtCharges.setText(currentChargesInput);
                txtCharges.requestFocus();
                return;
            }
            if (updatingFeedback) {
                JOptionPane.showMessageDialog(this, "Appointment paid not allow to enter feedback.");
                txtFeedback.setText(currentFeedbackInput);
                txtFeedback.requestFocus();
                return;
            }
        }

        // Validate charges if updating
        if (updatingCharges) {
            if (chargesStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Charges cannot be empty.");
                return;
            }

            double charges;
            try {
                charges = Double.parseDouble(chargesStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid charges value.");
                return;
            }

            // Update charges
            selectedAppointment.setCharges(charges);
        }

        // Update feedback if allowed
        selectedAppointment.setFeedback(feedback.isEmpty() ? null : feedback);

        // Update existing pending payment or create new pending payment
        if (existingPayment != null && existingPayment.getPaymentStatus() != null && existingPayment.getPaymentStatus().equalsIgnoreCase("pending")) {
            if (updatingCharges) {
                existingPayment.setCharges(Double.parseDouble(chargesStr));
            }
            existingPayment.setFeedback(feedback.isEmpty() ? null : feedback);
            // keep paymentId and status as Pending
        } else if (existingPayment == null && updatingCharges) {
            String paymentId = "P" + (DataIO.allPayments.size() + 1);
            Payment payment = new Payment(
                paymentId,
                selectedAppointment.getAppointmentId(),
                Double.parseDouble(chargesStr),
                feedback.isEmpty() ? null : feedback,
                "Pending",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
            );
            DataIO.allPayments.add(payment);
        }

        DataIO.write();
        loadAppointments();

        // Show appropriate success message
        if (updatingCharges && updatingFeedback) {
            JOptionPane.showMessageDialog(this, "Charges and Feedback saved successfully.");
        } else if (updatingCharges) {
            JOptionPane.showMessageDialog(this, "Charges saved successfully.");
        } else if (updatingFeedback) {
            JOptionPane.showMessageDialog(this, "Feedback saved successfully.");
        }

        panelForm.setVisible(false);
    }//GEN-LAST:event_UpdateBtnActionPerformed

    private void SearchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchBtnActionPerformed
        String query = jTextField1.getText().trim();
        if (query.isEmpty()) {
            loadAppointments();
            return;
        }

        String lower = query.toLowerCase();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        for (Appointment a : doctorAppointments) {
            String id = a.getAppointmentId() == null ? "" : a.getAppointmentId();
            String customer = a.getCustomerUsername() == null ? "" : a.getCustomerUsername();

            if (id.toLowerCase().contains(lower) || customer.toLowerCase().contains(lower)) {
                // Find payment status for this appointment
                String paymentStatus = "Pending"; // default
                for (Payment p : DataIO.allPayments) {
                    if (p.getAppointmentId() != null && p.getAppointmentId().equals(a.getAppointmentId())) {
                        paymentStatus = p.getPaymentStatus();
                        break;
                    }
                }
                
                model.addRow(new Object[]{
                        a.getAppointmentId(),
                        a.getCustomerUsername(),
                        a.getDate(),
                        a.getTime(),
                        a.getStatus(),
                        a.getCharges(),
                        a.getFeedback(),
                        paymentStatus
                });
            }
        }
    }//GEN-LAST:event_SearchBtnActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        SearchBtnActionPerformed(evt);
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void LogoutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutBtnActionPerformed
        new LoginPage().setVisible(true);
        dispose();
    }//GEN-LAST:event_LogoutBtnActionPerformed

    private void LoadSelectedBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoadSelectedBtnActionPerformed
    int selectedRow = jTable1.getSelectedRow();
    if (selectedRow < 0) {
        JOptionPane.showMessageDialog(this, "Please select an appointment first.");
        return;
    }

    String appointmentId = jTable1.getValueAt(selectedRow, 0).toString();
    selectedAppointment = DataIO.findAppointmentById(appointmentId);

    if (selectedAppointment != null) {
        txtAppointmentId.setText(selectedAppointment.getAppointmentId());
        txtCustomer.setText(selectedAppointment.getCustomerUsername());
        txtDate.setText(selectedAppointment.getDate());
        txtTime.setText(selectedAppointment.getTime());
        txtStatus.setText(selectedAppointment.getStatus());
        txtCharges.setText(selectedAppointment.getCharges() == 0 ? "" : String.valueOf(selectedAppointment.getCharges()));
        txtFeedback.setText(selectedAppointment.getFeedback() == null ? "" : selectedAppointment.getFeedback());

        
        txtAppointmentId.setEditable(false);
        txtCustomer.setEditable(false);
        txtDate.setEditable(false);
        txtTime.setEditable(false);
        txtStatus.setEditable(false);

        
        panelForm.setVisible(true);
    }

    }//GEN-LAST:event_LoadSelectedBtnActionPerformed

    private void UpdateChargesBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateChargesBtnActionPerformed
        if (selectedAppointment == null) {
            JOptionPane.showMessageDialog(this, "Please load an appointment first.");
            return;
        }

        // Preserve current user input
        String currentChargesInput = txtCharges.getText();

        // Find existing payment for this appointment (if any)
        Payment existingPayment = null;
        for (Payment p : DataIO.allPayments) {
            if (p.getAppointmentId() != null && p.getAppointmentId().equals(selectedAppointment.getAppointmentId())) {
                existingPayment = p;
                break;
            }
        }

        // Check PaymentStatus: if "Paid" → not allowed to enter charges
        if (existingPayment != null && existingPayment.getPaymentStatus() != null && existingPayment.getPaymentStatus().equalsIgnoreCase("paid")) {
            JOptionPane.showMessageDialog(this, "Appointment paid not allow to enter charges.");
            txtCharges.setText(currentChargesInput);
            txtCharges.requestFocus();
            return;
        }

        String chargesStr = txtCharges.getText().trim();

        if (chargesStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Charges cannot be empty.");
            return;
        }

        double charges;
        try {
            charges = Double.parseDouble(chargesStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid charges value.");
            return;
        }

        // Update charges
        selectedAppointment.setCharges(charges);

        // Update existing pending payment or create new pending payment
        if (existingPayment != null && existingPayment.getPaymentStatus() != null && existingPayment.getPaymentStatus().equalsIgnoreCase("pending")) {
            existingPayment.setCharges(charges);
        } else if (existingPayment == null) {
            String paymentId = "P" + (DataIO.allPayments.size() + 1);
            Payment payment = new Payment(
                paymentId,
                selectedAppointment.getAppointmentId(),
                charges,
                null, // no feedback for charges-only update
                "Pending",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
            );
            DataIO.allPayments.add(payment);
        }

        DataIO.write();
        loadAppointments();

        JOptionPane.showMessageDialog(this, "Charges saved successfully.");
    }//GEN-LAST:event_UpdateChargesBtnActionPerformed

    private void UpdateFeedbackBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateFeedbackBtnActionPerformed
        if (selectedAppointment == null) {
            JOptionPane.showMessageDialog(this, "Please load an appointment first.");
            return;
        }

        // Preserve current user input
        String currentFeedbackInput = txtFeedback.getText();

        // Find existing payment for this appointment (if any)
        Payment existingPayment = null;
        for (Payment p : DataIO.allPayments) {
            if (p.getAppointmentId() != null && p.getAppointmentId().equals(selectedAppointment.getAppointmentId())) {
                existingPayment = p;
                break;
            }
        }

        // Check PaymentStatus: if "Paid" → not allowed to enter feedback
        if (existingPayment != null && existingPayment.getPaymentStatus() != null && existingPayment.getPaymentStatus().equalsIgnoreCase("paid")) {
            JOptionPane.showMessageDialog(this, "Appointment paid not allow to enter feedback.");
            txtFeedback.setText(currentFeedbackInput);
            txtFeedback.requestFocus();
            return;
        }

        String feedback = txtFeedback.getText().trim();

        // Update feedback
        selectedAppointment.setFeedback(feedback.isEmpty() ? null : feedback);

        // Update existing pending payment
        if (existingPayment != null && existingPayment.getPaymentStatus() != null && existingPayment.getPaymentStatus().equalsIgnoreCase("pending")) {
            existingPayment.setFeedback(feedback.isEmpty() ? null : feedback);
        }

        DataIO.write();
        loadAppointments();

        JOptionPane.showMessageDialog(this, "Feedback saved successfully.");
    }//GEN-LAST:event_UpdateFeedbackBtnActionPerformed

    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            DataIO.read();
            if (!DataIO.allDoctors.isEmpty()) {
                Doctor demo = DataIO.allDoctors.get(0);
                new EnterChargesFeedbackPage(demo).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "No doctor data found!");
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BacktoDashboard;
    private javax.swing.JButton LoadSelectedBtn;
    private javax.swing.JButton LogoutBtn;
    private javax.swing.JLabel Search;
    private javax.swing.JButton SearchBtn;
    private javax.swing.JButton UpdateBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanelTable;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JPanel panelForm;
    // End of variables declaration//GEN-END:variables
}