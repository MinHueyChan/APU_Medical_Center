
package Staff;

import main.User;
import main.*;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class AppointmentManagement {
    private AppointmentManagementPage view;
    private User currentUser;

    public AppointmentManagement(AppointmentManagementPage view, User currentUser) {
        this.view = view;
        this.currentUser = currentUser;
        initController();
        loadAppointmentData(DataIO.allAppointments);
    }
    
    public AppointmentManagement(AppointmentManagementPage view) {
        this(view, null);
    }
    
    private void initController() {
        view.getBookButton().addActionListener(e -> bookAppointment());
        view.getSearchButton().addActionListener(e -> searchAppointment());
        view.getAssignButton().addActionListener(e -> assignAppointment());
        view.getUpdateButton().addActionListener(e -> updateAppointment());
        view.getCancelButton().addActionListener(e -> cancelAppointment());
        view.getBackButton().addActionListener(e -> goBack());
    }
    
    private void loadAppointmentData(List<Appointment> appointments) {
        updateCompletedAppointments();
        
        DefaultTableModel model = (DefaultTableModel) view.getAppointmentTable().getModel();
        model.setRowCount(0);
        for (Appointment a : appointments) {
            model.addRow(new Object[]{
                a.getAppointmentId(),
                a.getCustomerUsername(),
                a.getDate(),
                a.getTime(),
                a.getType(),
                a.getDoctorUsername(),
                a.getStaffId(),
                a.getStatus()
            });
        }
    }
    
    private void searchAppointment() {
        String keyword = view.getSearchField().getText().trim().toLowerCase();
        List<Appointment> filtered = DataIO.allAppointments.stream()
                .filter(a -> a.getAppointmentId().toLowerCase().contains(keyword)
                        || a.getCustomerUsername().toLowerCase().contains(keyword)
                        || (a.getDoctorUsername() != null && a.getDoctorUsername().toLowerCase().contains(keyword)))
                .collect(Collectors.toList());
        loadAppointmentData(filtered);
    }
    
    private void updateCompletedAppointments() {
        java.time.LocalDate today = java.time.LocalDate.now();
        java.time.LocalTime now = java.time.LocalTime.now();

        for (Appointment a : DataIO.allAppointments) {
            if ("cancel".equalsIgnoreCase(a.getStatus()) || "completed".equalsIgnoreCase(a.getStatus())) {
                continue;
            }

            try {
                java.time.LocalDate appointmentDate = java.time.LocalDate.parse(a.getDate());
                java.time.LocalTime appointmentTime = java.time.LocalTime.parse(a.getTime());

                if (appointmentDate.isBefore(today) ||
                    (appointmentDate.isEqual(today) && appointmentTime.isBefore(now))) {
                    a.setStatus("completed");
                }
            } catch (Exception e) {
                // Skip appointments with invalid date/time format
                System.out.println("Skipping appointment with invalid date/time: " + a.getAppointmentId());
            }
        }

        DataIO.write();
    }
    
    private void bookAppointment() {
        AppointmentForm form = new AppointmentForm(view, currentUser);
        form.setVisible(true);
        if (form.isSaved()) {
            loadAppointmentData(DataIO.allAppointments);
        }
    }
    
    private void assignAppointment() {
        int selectedRow = view.getAppointmentTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Choose the appointment you want to assign doctor.");
            return;
        }
        
        String status = (String) view.getAppointmentTable().getValueAt(selectedRow, 7);
        if (!"pending".equalsIgnoreCase(status)) {
            JOptionPane.showMessageDialog(view, "This appointment already has a doctor assigned.");
            return;
        }
        String appointmentId = (String) view.getAppointmentTable().getValueAt(selectedRow, 0);
        String date = (String) view.getAppointmentTable().getValueAt(selectedRow, 2);
        String time = (String) view.getAppointmentTable().getValueAt(selectedRow, 3);

        
        AssignDoctorForm form = new AssignDoctorForm(view, date, time);
        form.setVisible(true);
        
        
        if (form.isSaved()) {
            String selectedDoctor = form.getSelectedDoctor();

            for (Appointment a : DataIO.allAppointments) {
                if (a.getAppointmentId().equals(appointmentId)) {
                    a.setDoctorUsername(selectedDoctor);
                    a.setStatus("booked");
                    break;
                }
            }
        
        DataIO.write();
        loadAppointmentData(DataIO.allAppointments);
        JOptionPane.showMessageDialog(view, "Doctor assigned successfully.");
        }
    }
    
    private void updateAppointment() {
        int selectedRow = view.getAppointmentTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Choose the appointment you want to update.");
            return;
        }
        
        String appointmentId = (String) view.getAppointmentTable().getValueAt(selectedRow, 0);
        Appointment target = null;
        for (Appointment a : DataIO.allAppointments) {
            if (a.getAppointmentId().equals(appointmentId)) {
                target = a;
                break;
            }
        }
        if (target == null) {
            JOptionPane.showMessageDialog(view, "Appointment not found.");
            return;
        }

        AppointmentForm form = new AppointmentForm(view, target, currentUser);
        form.setVisible(true);

        if (form.isSaved()) {
            loadAppointmentData(DataIO.allAppointments);
            JOptionPane.showMessageDialog(view, "Appointment updated successfully!");
        }
    }
    
    private void cancelAppointment() {
        int selectedRow = view.getAppointmentTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Choose the appointment you want to cancel.");
            return;
        }

        String status = (String) view.getAppointmentTable().getValueAt(selectedRow, 7);
        if ("cancelled".equalsIgnoreCase(status)) {
            JOptionPane.showMessageDialog(view, "This appointment is already cancelled.");
            return;
        }
        if ("completed".equalsIgnoreCase(status)) {
            JOptionPane.showMessageDialog(view, "This appointment is already completed.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                view,
                "Are you sure you want to cancel this appointment?",
                "Confirm Cancel",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        String appointmentId = (String) view.getAppointmentTable().getValueAt(selectedRow, 0);
        for (Appointment a : DataIO.allAppointments) {
            if (a.getAppointmentId().equals(appointmentId)) {
                a.setStatus("cancelled");
                break;
            }
        }

        DataIO.write();
        loadAppointmentData(DataIO.allAppointments);
        JOptionPane.showMessageDialog(view, "Appointment cancelled successfully.");
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
