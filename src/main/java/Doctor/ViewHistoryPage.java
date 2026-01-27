/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Doctor;

import main.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class ViewHistoryPage extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ViewHistoryPage.class.getName());
    private Doctor currentUser;
    private DefaultTableModel tableModel;

    /**
     * Creates new form ViewHistoryPage
     */
    public ViewHistoryPage(Doctor currentUser) {
        this.currentUser = currentUser;
        initComponents();
        setupTable();
        loadHistory();
        setLocationRelativeTo(null); 

        // Allow viewing full text via dialog when clicking Feedback/Comment cells
        Historytable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int viewRow = Historytable.rowAtPoint(e.getPoint());
                int viewCol = Historytable.columnAtPoint(e.getPoint());
                if (viewRow < 0 || viewCol < 0) return;

                String colName = Historytable.getColumnModel().getColumn(viewCol).getHeaderValue().toString();
                boolean isFeedbackOrComment = "Feedback".equalsIgnoreCase(colName) || "Comment".equalsIgnoreCase(colName);
                if (!isFeedbackOrComment) return;

                Object value = Historytable.getValueAt(viewRow, viewCol);
                String text = value == null ? "" : value.toString();

                JTextArea area = new JTextArea(text);
                area.setEditable(false);
                area.setLineWrap(true);
                area.setWrapStyleWord(true);
                area.setCaretPosition(0);
                JScrollPane scroller = new JScrollPane(area);
                scroller.setPreferredSize(new java.awt.Dimension(480, 240));
                JOptionPane.showMessageDialog(ViewHistoryPage.this, scroller, colName + " - Full Text", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        // Remove previous mouseMotion tooltip handler; renderer-based tooltips are active.
    }
    
    private void setupTable() {
        String[] columns = {"Appointment ID", "Customer", "Date", "Time", "Status", "Charges", "Feedback", "Rating", "Comment"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        Historytable.setModel(tableModel);

        // Enable tooltip via renderer for Feedback/Comment columns only
        ToolTipManager.sharedInstance().registerComponent(Historytable);
        final int feedbackModelIndex = tableModel.findColumn("Feedback");
        final int commentModelIndex = tableModel.findColumn("Comment");
        javax.swing.table.DefaultTableCellRenderer tooltipRenderer = new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                java.awt.Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                int modelCol = table.convertColumnIndexToModel(column);
                if (modelCol == feedbackModelIndex || modelCol == commentModelIndex) {
                    setToolTipText(value == null ? null : value.toString());
                } else {
                    setToolTipText(null);
                }
                return comp;
            }
        };
        javax.swing.table.TableColumnModel colModel = Historytable.getColumnModel();
        int feedbackViewIndex = Historytable.convertColumnIndexToView(feedbackModelIndex);
        int commentViewIndex = Historytable.convertColumnIndexToView(commentModelIndex);
        if (feedbackViewIndex >= 0) {
            colModel.getColumn(feedbackViewIndex).setCellRenderer(tooltipRenderer);
        }
        if (commentViewIndex >= 0) {
            colModel.getColumn(commentViewIndex).setCellRenderer(tooltipRenderer);
        }
    }
    
    


    private void loadHistory() {
        DataIO.read();
        tableModel.setRowCount(0);

        for (Appointment appointment : DataIO.allAppointments) {
            if (appointment.getDoctorUsername() != null &&
                appointment.getDoctorUsername().equals(currentUser.getUsername()) &&
                (appointment.getStatus().equalsIgnoreCase("completed") ||
                 appointment.getStatus().equalsIgnoreCase("cancelled"))) {

                Object[] row = {
                    appointment.getAppointmentId(),
                    appointment.getCustomerUsername(),
                    appointment.getDate(),
                    appointment.getTime(),
                    appointment.getStatus(),
                    appointment.getCharges(),
                    appointment.getFeedback() != null ? appointment.getFeedback() : "",
                    appointment.getRating() != null ? appointment.getRating() : "",
                    appointment.getComment() != null ? appointment.getComment() : ""
                };
                tableModel.addRow(row);
            }
        }
    }
    
    private void filterHistory() {
        String dateFilter = txtDate.getText().trim();
        tableModel.setRowCount(0);

        for (Appointment appointment : DataIO.allAppointments) {
            if (appointment.getDoctorUsername() != null &&
                appointment.getDoctorUsername().equals(currentUser.getUsername()) &&
                (appointment.getStatus().equalsIgnoreCase("completed") ||
                 appointment.getStatus().equalsIgnoreCase("cancelled"))) {

                boolean dateMatch = dateFilter.isEmpty() || appointment.getDate().equals(dateFilter);

                if (dateMatch) {
                    Object[] row = {
                        appointment.getAppointmentId(),
                        appointment.getCustomerUsername(),
                        appointment.getDate(),
                        appointment.getTime(),
                        appointment.getStatus(),
                        appointment.getCharges(),
                        appointment.getFeedback() != null ? appointment.getFeedback() : "",
                        appointment.getRating() != null ? appointment.getRating() : "",
                        appointment.getComment() != null ? appointment.getComment() : ""
                    };
                    tableModel.addRow(row);
                }
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        Historytable = new javax.swing.JTable();
        txtDate = new javax.swing.JTextField();
        FilterBtn = new javax.swing.JButton();
        RefreshBtn = new javax.swing.JButton();
        BacktoDashboard = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        LogoutBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Historytable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Appointment ID", "Customer", "Date", "Time", "Status", "Charges", "Feedback", "Rating", "Comment"
            }
        ));
        jScrollPane1.setViewportView(Historytable);

        txtDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDateActionPerformed(evt);
            }
        });

        FilterBtn.setText("Filter");
        FilterBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FilterBtnActionPerformed(evt);
            }
        });

        RefreshBtn.setText("Refresh All");
        RefreshBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshBtnActionPerformed(evt);
            }
        });

        BacktoDashboard.setText("Back");
        BacktoDashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BacktoDashboardActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Filter by Date (YYYY-MM-DD):");

        jPanel1.setBackground(new java.awt.Color(70, 130, 180));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel1.setText("APU Medical Centre");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("View History");

        LogoutBtn.setText("Logout");
        LogoutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(133, 133, 133)
                .addComponent(LogoutBtn)
                .addGap(18, 18, 18))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(LogoutBtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(BacktoDashboard))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(FilterBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(RefreshBtn)
                        .addGap(0, 291, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FilterBtn)
                    .addComponent(RefreshBtn)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(BacktoDashboard)
                .addGap(39, 39, 39))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void FilterBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FilterBtnActionPerformed
        filterHistory();
    }//GEN-LAST:event_FilterBtnActionPerformed

    private void RefreshBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshBtnActionPerformed
        loadHistory();
        txtDate.setText("");
    }//GEN-LAST:event_RefreshBtnActionPerformed

    private void txtDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDateActionPerformed
        filterHistory();
    }//GEN-LAST:event_txtDateActionPerformed

    private void LogoutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutBtnActionPerformed
        new LoginPage().setVisible(true);
        dispose();
    }//GEN-LAST:event_LogoutBtnActionPerformed

    private void BacktoDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BacktoDashboardActionPerformed
        new DoctorDashboard(currentUser).setVisible(true);
        dispose();
    }//GEN-LAST:event_BacktoDashboardActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        DataIO.read();
        if (!DataIO.allDoctors.isEmpty()) {
            Doctor demo = DataIO.allDoctors.get(0);
            new ViewHistoryPage(demo).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "No doctor data found!");
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BacktoDashboard;
    private javax.swing.JButton FilterBtn;
    private javax.swing.JTable Historytable;
    private javax.swing.JButton LogoutBtn;
    private javax.swing.JButton RefreshBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtDate;
    // End of variables declaration//GEN-END:variables
}
