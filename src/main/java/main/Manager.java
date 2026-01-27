package main;

import Manager.*;
import java.util.*;

public class Manager extends User {
    
    public Manager(String userId, String username, String password, String name, String gender, String email, String phone, int age) {
        super(userId, username, password, name, gender, email, phone, age, "Manager");
    }
    
    /**
     * Manage staff members - CRUD operations for Staff accounts
     * @param staffData Map containing staff information
     * @return boolean indicating success
     */
    public boolean manageStaff(Map<String, Object> staffData) {
        try {
            String action = (String) staffData.get("action");
            
            switch (action.toLowerCase()) {
                case "create":
                    return createStaff(staffData);
                case "update":
                    return updateStaff(staffData);
                case "delete":
                    return deleteStaff(staffData);
                case "view":
                    return viewStaff(staffData);
                default:
                    return false;
            }
        } catch (Exception e) {
            System.err.println("Error managing staff: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Manage doctors - CRUD operations for Doctor accounts
     * @param doctorData Map containing doctor information
     * @return boolean indicating success
     */
    public boolean manageDoctor(Map<String, Object> doctorData) {
        try {
            String action = (String) doctorData.get("action");
            
            switch (action.toLowerCase()) {
                case "create":
                    return createDoctor(doctorData);
                case "update":
                    return updateDoctor(doctorData);
                case "delete":
                    return deleteDoctor(doctorData);
                case "view":
                    return viewDoctor(doctorData);
                default:
                    return false;
            }
        } catch (Exception e) {
            System.err.println("Error managing doctor: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * View all appointments in the system
     * @return List of all appointments
     */
    public List<Appointment> viewAllAppointments() {
        return new ArrayList<>(DataIO.allAppointments);
    }
    
    /**
     * Generate reports based on criteria
     * @param criteria Map containing report criteria
     * @return Report object
     */
    public Report generateReport(Map<String, Object> criteria) {
        ManagerReport reportGenerator = new ManagerReport();
        String reportType = (String) criteria.getOrDefault("type", "summary");
        
        Report report = new Report();
        report.setId("RPT" + System.currentTimeMillis());
        report.setType(reportType);
        report.setGeneratedDate(java.time.LocalDate.now());
        
        String content = "";
        switch (reportType.toLowerCase()) {
            case "appointment_summary":
                content = reportGenerator.generateAppointmentSummaryReport();
                break;
            case "appointments_by_doctor":
                content = reportGenerator.generateAppointmentsByDoctorReport();
                break;
            case "revenue":
                content = reportGenerator.generateRevenueReport();
                break;
            case "doctor_performance":
                content = reportGenerator.generateDoctorPerformanceReport();
                break;
            case "feedback_summary":
                content = reportGenerator.generateFeedbackSummaryReport();
                break;
            default:
                content = reportGenerator.generateAppointmentSummaryReport();
        }
        
        report.setContent(content);
        return report;
    }
    
    // Private helper methods for staff management
    private boolean createStaff(Map<String, Object> staffData) {
        try {
            // Validate data first
            if (!ManagerHelper.validateUserData(staffData)) {
                return false;
            }
            
            String username = (String) staffData.get("username");
            if (ManagerHelper.usernameExists(username, null)) {
                return false; // Username already exists
            }
            
            String userId = generateStaffId();
            String password = (String) staffData.get("password");
            String name = (String) staffData.get("name");
            String gender = (String) staffData.get("gender");
            String email = (String) staffData.get("email");
            String phone = (String) staffData.get("phone");
            int age = (Integer) staffData.get("age");
            
            Staff newStaff = new Staff(userId, username, password, name, gender, email, phone, age);
            DataIO.allStaffs.add(newStaff);
            DataIO.write();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    private boolean updateStaff(Map<String, Object> staffData) {
        try {
            String userId = (String) staffData.get("userId");
            for (int i = 0; i < DataIO.allStaffs.size(); i++) {
                if (DataIO.allStaffs.get(i).getUserId().equals(userId)) {
                    User staff = DataIO.allStaffs.get(i);
                    staff.setName((String) staffData.get("name"));
                    staff.setGender((String) staffData.get("gender"));
                    staff.setEmail((String) staffData.get("email"));
                    staff.setPhone((String) staffData.get("phone"));
                    staff.setAge((Integer) staffData.get("age"));
                    DataIO.write();
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
    private boolean deleteStaff(Map<String, Object> staffData) {
        try {
            String userId = (String) staffData.get("userId");
            DataIO.allStaffs.removeIf(staff -> staff.getUserId().equals(userId));
            DataIO.write();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    private boolean viewStaff(Map<String, Object> staffData) {
        // This would typically return staff information
        // For now, just return true as the GUI handles display
        return true;
    }
    
    // Private helper methods for doctor management
    private boolean createDoctor(Map<String, Object> doctorData) {
        try {
            // Validate data first
            if (!ManagerHelper.validateUserData(doctorData)) {
                return false;
            }
            
            String username = (String) doctorData.get("username");
            if (ManagerHelper.usernameExists(username, null)) {
                return false; // Username already exists
            }
            
            String userId = generateDoctorId();
            String password = (String) doctorData.get("password");
            String name = (String) doctorData.get("name");
            String gender = (String) doctorData.get("gender");
            String email = (String) doctorData.get("email");
            String phone = (String) doctorData.get("phone");
            int age = (Integer) doctorData.get("age");
            
            Doctor newDoctor = new Doctor(userId, username, password, name, gender, email, phone, age);
            DataIO.allDoctors.add(newDoctor);
            DataIO.write();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    private boolean updateDoctor(Map<String, Object> doctorData) {
        try {
            String userId = (String) doctorData.get("userId");
            for (int i = 0; i < DataIO.allDoctors.size(); i++) {
                if (DataIO.allDoctors.get(i).getUserId().equals(userId)) {
                    User doctor = DataIO.allDoctors.get(i);
                    doctor.setName((String) doctorData.get("name"));
                    doctor.setGender((String) doctorData.get("gender"));
                    doctor.setEmail((String) doctorData.get("email"));
                    doctor.setPhone((String) doctorData.get("phone"));
                    doctor.setAge((Integer) doctorData.get("age"));
                    DataIO.write();
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
    private boolean deleteDoctor(Map<String, Object> doctorData) {
        try {
            String userId = (String) doctorData.get("userId");
            DataIO.allDoctors.removeIf(doctor -> doctor.getUserId().equals(userId));
            DataIO.write();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    private boolean viewDoctor(Map<String, Object> doctorData) {
        // This would typically return doctor information
        // For now, just return true as the GUI handles display
        return true;
    }
    
    // Helper methods for ID generation
    private String generateStaffId() {
        int maxId = 0;
        for (User staff : DataIO.allStaffs) {
            try {
                int num = Integer.parseInt(staff.getUserId().substring(1));
                if (num > maxId) maxId = num;
            } catch (Exception e) {
                // Skip invalid IDs
            }
        }
        return String.format("S%04d", maxId + 1);
    }
    
    private String generateDoctorId() {
        int maxId = 0;
        for (User doctor : DataIO.allDoctors) {
            try {
                int num = Integer.parseInt(doctor.getUserId().substring(1));
                if (num > maxId) maxId = num;
            } catch (Exception e) {
                // Skip invalid IDs
            }
        }
        return String.format("D%04d", maxId + 1);
    }
}