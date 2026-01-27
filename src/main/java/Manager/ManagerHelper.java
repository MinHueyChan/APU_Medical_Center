package Manager;

import main.*;
import java.util.*;

/**
 * Helper class for Manager operations
 Provides utility methods for Manager functionality
 */
public class ManagerHelper {
    
    /**
     * Validate user data before creating/updating
     * @param userData Map containing user information
     * @return boolean indicating if data is valid
     */
    public static boolean validateUserData(Map<String, Object> userData) {
        try {
            // Check required fields
            if (userData.get("username") == null || userData.get("username").toString().trim().isEmpty()) {
                return false;
            }
            if (userData.get("name") == null || userData.get("name").toString().trim().isEmpty()) {
                return false;
            }
            if (userData.get("email") == null || userData.get("email").toString().trim().isEmpty()) {
                return false;
            }
            if (userData.get("phone") == null || userData.get("phone").toString().trim().isEmpty()) {
                return false;
            }
            if (userData.get("gender") == null || userData.get("gender").toString().trim().isEmpty()) {
                return false;
            }
            if (userData.get("age") == null) {
                return false;
            }
            
            // Validate email format
            String email = userData.get("email").toString();
            if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                return false;
            }
            
            // Validate gender
            String gender = userData.get("gender").toString().toUpperCase();
            if (!gender.equals("M") && !gender.equals("F")) {
                return false;
            }
            
            // Validate age
            int age = (Integer) userData.get("age");
            if (age < 1 || age > 120) {
                return false;
            }
            
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Check if username already exists
     * @param username Username to check
     * @param excludeUserId User ID to exclude from check (for updates)
     * @return boolean indicating if username exists
     */
    public static boolean usernameExists(String username, String excludeUserId) {
        // Check in staff
        for (User staff : main.DataIO.allStaffs) {
            if (!staff.getUserId().equals(excludeUserId) && staff.getUsername().equals(username)) {
                return true;
            }
        }
        
        // Check in doctors
        for (User doctor : main.DataIO.allDoctors) {
            if (!doctor.getUserId().equals(excludeUserId) && doctor.getUsername().equals(username)) {
                return true;
            }
        }
        
        // Check in managers
        for (Manager manager : main.DataIO.allManagers) {
            if (!manager.getUserId().equals(excludeUserId) && manager.getUsername().equals(username)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Get user by ID from all user types
     * @param userId User ID to search for
     * @return User object or null if not found
     */
    public static User getUserById(String userId) {
        // Check in staff
        for (User staff : main.DataIO.allStaffs) {
            if (staff.getUserId().equals(userId)) {
                return staff;
            }
        }
        
        // Check in doctors
        for (User doctor : main.DataIO.allDoctors) {
            if (doctor.getUserId().equals(userId)) {
                return doctor;
            }
        }
        
        // Check in managers
        for (Manager manager : main.DataIO.allManagers) {
            if (manager.getUserId().equals(userId)) {
                return manager;
            }
        }
        
        return null;
    }
    
    /**
     * Get user role by ID
     * @param userId User ID to check
     * @return String indicating user role
     */
    public static String getUserRole(String userId) {
        // Check in staff
        for (User staff : main.DataIO.allStaffs) {
            if (staff.getUserId().equals(userId)) {
                return "Staff";
            }
        }
        
        // Check in doctors
        for (User doctor : main.DataIO.allDoctors) {
            if (doctor.getUserId().equals(userId)) {
                return "Doctor";
            }
        }
        
        // Check in managers
        for (Manager manager : main.DataIO.allManagers) {
            if (manager.getUserId().equals(userId)) {
                return "Manager";
            }
        }
        
        return "Unknown";
    }
    
    /**
     * Format user data for display
     * @param user User object to format
     * @return Formatted string
     */
    public static String formatUserInfo(User user) {
        if (user == null) return "User not found";
        
        return String.format("ID: %s, Username: %s, Name: %s, Role: %s, Email: %s", 
                           user.getUserId(), user.getUsername(), user.getName(), 
                           getUserRole(user.getUserId()), user.getEmail());
    }
    
    /**
     * Get statistics for dashboard
     * @return Map containing system statistics
     */
    public static Map<String, Object> getSystemStatistics() {
        Map<String, Object> stats = new HashMap<>();
        
        stats.put("totalStaff", main.DataIO.allStaffs.size());
        stats.put("totalDoctors", main.DataIO.allDoctors.size());
        stats.put("totalManagers", main.DataIO.allManagers.size());
        stats.put("totalAppointments", main.DataIO.allAppointments.size());
        stats.put("totalPayments", main.DataIO.allPayments.size());
        stats.put("totalReceipts", main.DataIO.allReceipts.size());
        
        // Count appointments by status
        int completedAppointments = 0;
        int pendingAppointments = 0;
        int cancelledAppointments = 0;
        
        for (Appointment appointment : main.DataIO.allAppointments) {
            String status = appointment.getStatus().toLowerCase();
            if (status.equals("completed") || status.equals("successful")) completedAppointments++;
            else if (status.equals("pending") || status.equals("booked")) pendingAppointments++;
            else if (status.equals("cancelled") || status.equals("cancellation in progress")) cancelledAppointments++;
        }
        
        stats.put("completedAppointments", completedAppointments);
        stats.put("pendingAppointments", pendingAppointments);
        stats.put("cancelledAppointments", cancelledAppointments);
        
        return stats;
    }
}