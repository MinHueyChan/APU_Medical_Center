package Staff;

import main.User;
import main.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class PaymentManagement {
    private PaymentManagementPage view;
    private User currentUser;

    public PaymentManagement(PaymentManagementPage view, User currentUser) {
        this.view = view;
        this.currentUser = currentUser;
        initController();
        loadPaymentData();
    }
    
    public PaymentManagement(PaymentManagementPage view) {
        this(view, null); 
    }
    
    private void initController() {
        view.getProcessPaymentButton().addActionListener(e -> processPayment());
        view.getViewReceiptButton().addActionListener(e -> viewReceipt());
        view.getSearchButton().addActionListener(e -> searchPayments());
        view.getBackButton().addActionListener(e -> goBack());
    }
    
    private void loadPaymentData() {
        DefaultTableModel model = (DefaultTableModel) view.getPaymentTable().getModel();
        model.setRowCount(0);
        
        List<Appointment> completedAppointments = DataIO.allAppointments.stream()
                .filter(a -> "completed".equalsIgnoreCase(a.getStatus()))
                .collect(Collectors.toList());
        
        for (Appointment a : completedAppointments) {
            String paymentStatus = "Pending";
            String receiptId = "N/A";
            
            for (Payment p : DataIO.allPayments) {
                if (p.getAppointmentId().equals(a.getAppointmentId())) {
                    paymentStatus = p.getPaymentStatus();
                    break;
                }
            }
            
            // 查找对应的receipt
            for (Receipt r : DataIO.allReceipts) {
                if (r.getAppointmentId().equals(a.getAppointmentId())) {
                    receiptId = r.getReceiptId();
                    break;
                }
            }
            
            model.addRow(new Object[]{
                a.getAppointmentId(),
                a.getCustomerUsername(),
                a.getDate(),
                a.getTime(),
                a.getType(),
                a.getDoctorUsername(),
                String.format("RM %.2f", a.getCharges()),
                paymentStatus,
                receiptId
            });
        }
    }
    
    private void searchPayments() {
        String keyword = view.getSearchField().getText().trim().toLowerCase();
        List<Appointment> filtered = DataIO.allAppointments.stream()
                .filter(a -> "completed".equalsIgnoreCase(a.getStatus()))
                .filter(a -> a.getAppointmentId().toLowerCase().contains(keyword)
                        || a.getCustomerUsername().toLowerCase().contains(keyword)
                        || (a.getDoctorUsername() != null && a.getDoctorUsername().toLowerCase().contains(keyword)))
                .collect(Collectors.toList());
        
        DefaultTableModel model = (DefaultTableModel) view.getPaymentTable().getModel();
        model.setRowCount(0);
        
        for (Appointment a : filtered) {
            String paymentStatus = "Pending";
            String receiptId = "N/A";
            
            for (Payment p : DataIO.allPayments) {
                if (p.getAppointmentId().equals(a.getAppointmentId())) {
                    paymentStatus = p.getPaymentStatus();
                    break;
                }
            }
            
            // 查找对应的receipt
            for (Receipt r : DataIO.allReceipts) {
                if (r.getAppointmentId().equals(a.getAppointmentId())) {
                    receiptId = r.getReceiptId();
                    break;
                }
            }
            
            model.addRow(new Object[]{
                a.getAppointmentId(),
                a.getCustomerUsername(),
                a.getDate(),
                a.getTime(),
                a.getType(),
                a.getDoctorUsername(),
                String.format("RM %.2f", a.getCharges()),
                paymentStatus,
                receiptId
            });
        }
    }
    
    
    private void processPayment() {
        int selectedRow = view.getPaymentTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Please select a payment to process.");
            return;
        }
        
        String appointmentId = (String) view.getPaymentTable().getValueAt(selectedRow, 0);
        String currentStatus = (String) view.getPaymentTable().getValueAt(selectedRow, 7);
        
        if ("Paid".equalsIgnoreCase(currentStatus)) {
            JOptionPane.showMessageDialog(view, "This payment has already been processed.");
            return;
        }
        
        double charges = 0.0;
        for (Appointment a : DataIO.allAppointments) {
            if (a.getAppointmentId().equals(appointmentId)) {
                charges = a.getCharges();
                break;
            }
        }
        
        if (charges <= 0) {
            JOptionPane.showMessageDialog(view, "No charges found for this appointment.");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(
                view,
                String.format("Confirm payment of RM %.2f for appointment %s?", charges, appointmentId),
                "Confirm Payment",
                JOptionPane.YES_NO_OPTION
        );
        
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        
        processPaymentForAppointment(appointmentId, charges);
        
        // 创建receipt
        createReceiptForAppointment(appointmentId);
        
        loadPaymentData();
        JOptionPane.showMessageDialog(view, "Payment processed successfully!");
    }
    
    private void processPaymentForAppointment(String appointmentId, double charges) {
        Payment existingPayment = null;
        for (Payment p : DataIO.allPayments) {
            if (p.getAppointmentId().equals(appointmentId)) {
                existingPayment = p;
                break;
            }
        }
        
        if (existingPayment != null) {
            existingPayment.setPaymentStatus("Paid");
            existingPayment.setPaymentDate(LocalDate.now().toString());
            existingPayment.setPaymentTime(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        } else {
            String paymentId = "P" + String.format("%04d", DataIO.allPayments.size() + 1);
            Payment newPayment = new Payment(
                paymentId,
                appointmentId,
                charges,
                "", 
                "Paid",
                LocalDate.now().toString(),
                LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
            );
            DataIO.allPayments.add(newPayment);
        }
        
        DataIO.write();
    }
    
    private void createReceiptForAppointment(String appointmentId) {
        // 查找预约信息
        Appointment appointment = null;
        for (Appointment a : DataIO.allAppointments) {
            if (a.getAppointmentId().equals(appointmentId)) {
                appointment = a;
                break;
            }
        }
        
        if (appointment == null) {
            return;
        }
        
        // 生成receipt ID
        String receiptId = "R" + String.format("%04d", DataIO.allReceipts.size() + 1);
        
        // 获取客户和医生姓名
        String customerName = appointment.getCustomerUsername();
        String doctorName = appointment.getDoctorUsername();
        
        // 创建receipt
        Receipt receipt = new Receipt(
            receiptId,
            appointmentId,
            appointment.getDate(),
            appointment.getTime(),
            appointment.getCharges(),
            customerName,
            doctorName,
            appointment.getType()
        );
        
        DataIO.allReceipts.add(receipt);
        DataIO.write();
    }
    
    private void viewReceipt() {
        int selectedRow = view.getPaymentTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Please select a row to view receipt.");
            return;
        }
        
        String appointmentId = (String) view.getPaymentTable().getValueAt(selectedRow, 0);
        String paymentStatus = (String) view.getPaymentTable().getValueAt(selectedRow, 7);
        
        if (!"Paid".equalsIgnoreCase(paymentStatus)) {
            JOptionPane.showMessageDialog(view, "Haven't payment yet. Please make payment first.");
            return;
        }
        
        // 查找receipt
        Receipt receipt = null;
        for (Receipt r : DataIO.allReceipts) {
            if (r.getAppointmentId().equals(appointmentId)) {
                receipt = r;
                break;
            }
        }
        
        if (receipt == null) {
            JOptionPane.showMessageDialog(view, "Receipt not found.");
            return;
        }
        
        // 显示receipt信息
        ReceiptViewer viewer = new ReceiptViewer(receipt);
        viewer.setVisible(true);
    }
    
    private void goBack() {
        view.dispose(); 
        if (currentUser != null) {
            new StaffDashboard(currentUser).setVisible(true);
        } else {
            new StaffDashboard(currentUser).setVisible(true);
        }
    }
}
