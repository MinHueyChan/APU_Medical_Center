package Manager;

import main.*;
import java.io.*;
import java.util.*;

/**
 * TextFileStorage class as specified in the UML diagram
 * Handles saving and loading of data to/from text files
 */
public class TextFileStorage {
    
    /**
     * Save users to text files
     * @param users List of users to save
     */
    public void saveUsers(List<User> users) {
        if (users == null) return;
        
        try {
            // Separate users by type
            List<User> staffs = new ArrayList<>();
            List<User> customers = new ArrayList<>();
            List<User> doctors = new ArrayList<>();
            List<Manager> managers = new ArrayList<>();
            
            for (User user : users) {
                if (user.getType() != null) {
                    switch (user.getType()) {
                        case "Staff":
                            staffs.add(user);
                            break;
                        case "Customer":
                            customers.add(user);
                            break;
                        case "Doctor":
                            doctors.add(user);
                            break;
                    }
                } else if (user instanceof Manager) {
                    managers.add((Manager) user);
                }
            }
            
            // Save each type to separate files
            saveStaffs(staffs);
            saveCustomers(customers);
            saveDoctors(doctors);
            saveManagers(managers);
            
        } catch (Exception e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }
    
    /**
     * Load users from text files
     * @return List of all users
     */
    public List<User> loadUsers() {
        List<User> allUsers = new ArrayList<>();
        
        try {
            // Load each type from separate files
            allUsers.addAll(loadStaffs());
            allUsers.addAll(loadCustomers());
            allUsers.addAll(loadDoctors());
            allUsers.addAll(loadManagers());
            
        } catch (Exception e) {
            System.err.println("Error loading users: " + e.getMessage());
        }
        
        return allUsers;
    }
    
    /**
     * Save appointments to text file
     * @param appointments List of appointments to save
     */
    public void saveAppointments(List<Appointment> appointments) {
        if (appointments == null) return;
        
        try (PrintWriter writer = new PrintWriter("appointment.txt")) {
            for (Appointment appointment : appointments) {
                writer.println(appointment.getAppointmentId());
                writer.println(appointment.getCustomerUsername());
                writer.println(appointment.getDate());
                writer.println(appointment.getTime());
                writer.println(appointment.getType());
                writer.println(appointment.getDoctorUsername() != null ? appointment.getDoctorUsername() : "");
                writer.println(appointment.getStaffId() != null ? appointment.getStaffId() : "");
                writer.println(appointment.getStatus());
                writer.println(appointment.getCharges());
                writer.println(appointment.getFeedback() != null ? appointment.getFeedback() : "");
                writer.println(appointment.getRating() != null ? appointment.getRating() : "");
                writer.println(appointment.getComment() != null ? appointment.getComment() : "");
                writer.println(); // Empty line separator
            }
        } catch (Exception e) {
            System.err.println("Error saving appointments: " + e.getMessage());
        }
    }
    
    /**
     * Load appointments from text file
     * @return List of appointments
     */
    public List<Appointment> loadAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        File file = new File("appointment.txt");
        
        if (!file.exists()) {
            return appointments;
        }
        
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String id = scanner.nextLine();
                String customerUsername = scanner.nextLine();
                String date = scanner.nextLine();
                String time = scanner.nextLine();
                String type = scanner.nextLine();
                String doctorUsername = scanner.nextLine();
                if (doctorUsername.isEmpty()) doctorUsername = null;
                String staffId = scanner.nextLine();
                if (staffId.isEmpty()) staffId = null;
                String status = scanner.nextLine();
                String chargesStr = scanner.nextLine();
                double charges = 0.0;
                if (!chargesStr.isEmpty()) {
                    try {
                        charges = Double.parseDouble(chargesStr);
                    } catch (NumberFormatException e) {
                        charges = 0.0;
                    }
                }
                String feedback = scanner.nextLine();
                String ratingStr = scanner.nextLine();
                Integer rating = null;
                if (!ratingStr.isEmpty()) {
                    try {
                        rating = Integer.parseInt(ratingStr);
                    } catch (NumberFormatException e) {
                        rating = null;
                    }
                }
                String comment = scanner.nextLine();
                if (comment.isEmpty()) comment = null;
                if (scanner.hasNextLine()) scanner.nextLine(); // Skip empty line
                
                appointments.add(new Appointment(id, customerUsername, date, time, type, 
                                              doctorUsername, staffId, status, charges, 
                                              feedback, rating, comment));
            }
        } catch (Exception e) {
            System.err.println("Error loading appointments: " + e.getMessage());
        }
        
        return appointments;
    }
    
    /**
     * Save payments to text file
     * @param payments List of payments to save
     */
    public void savePayments(List<Payment> payments) {
        if (payments == null) return;
        
        try (PrintWriter writer = new PrintWriter("payment.txt")) {
            for (Payment payment : payments) {
                writer.println(payment.getPaymentId());
                writer.println(payment.getAppointmentId());
                writer.println(payment.getCharges());
                writer.println(payment.getFeedback() != null ? payment.getFeedback() : "");
                writer.println(payment.getPaymentStatus());
                writer.println(payment.getPaymentDate() != null ? payment.getPaymentDate() : "");
                writer.println(payment.getPaymentTime() != null ? payment.getPaymentTime() : "");
                writer.println(); // Empty line separator
            }
        } catch (Exception e) {
            System.err.println("Error saving payments: " + e.getMessage());
        }
    }
    
    /**
     * Load payments from text file
     * @return List of payments
     */
    public List<Payment> loadPayments() {
        List<Payment> payments = new ArrayList<>();
        File file = new File("payment.txt");
        
        if (!file.exists()) {
            return payments;
        }
        
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String paymentId = scanner.nextLine();
                String appointmentId = scanner.nextLine();
                String chargesStr = scanner.nextLine();
                double charges = 0.0;
                if (!chargesStr.isEmpty()) {
                    try {
                        charges = Double.parseDouble(chargesStr);
                    } catch (NumberFormatException e) {
                        charges = 0.0;
                    }
                }
                String feedback = scanner.nextLine();
                if (feedback.isEmpty()) feedback = null;
                String paymentStatus = scanner.nextLine();
                String paymentDate = scanner.nextLine();
                if (paymentDate.isEmpty()) paymentDate = null;
                String paymentTime = scanner.nextLine();
                if (paymentTime.isEmpty()) paymentTime = null;
                if (scanner.hasNextLine()) scanner.nextLine(); // Skip empty line
                
                payments.add(new Payment(paymentId, appointmentId, charges, feedback, 
                                       paymentStatus, paymentDate, paymentTime));
            }
        } catch (Exception e) {
            System.err.println("Error loading payments: " + e.getMessage());
        }
        
        return payments;
    }
    
    // Private helper methods for saving specific user types
    private void saveStaffs(List<User> staffs) throws IOException {
        try (PrintWriter writer = new PrintWriter("staff.txt")) {
            for (User staff : staffs) {
                writer.println(staff.getUserId());
                writer.println(staff.getUsername());
                writer.println(staff.getPassword());
                writer.println(staff.getName());
                writer.println(staff.getGender());
                writer.println(staff.getEmail());
                writer.println(staff.getPhone());
                writer.println(staff.getAge());
                writer.println(); // Empty line separator
            }
        }
    }
    
    private void saveCustomers(List<User> customers) throws IOException {
        try (PrintWriter writer = new PrintWriter("customer.txt")) {
            for (User customer : customers) {
                writer.println(customer.getUserId());
                writer.println(customer.getUsername());
                writer.println(customer.getPassword());
                writer.println(customer.getName());
                writer.println(customer.getGender());
                writer.println(customer.getEmail());
                writer.println(customer.getPhone());
                writer.println(customer.getAge());
                writer.println(); // Empty line separator
            }
        }
    }
    
    private void saveDoctors(List<User> doctors) throws IOException {
        try (PrintWriter writer = new PrintWriter("doctor.txt")) {
            for (User doctor : doctors) {
                writer.println(doctor.getUserId());
                writer.println(doctor.getUsername());
                writer.println(doctor.getPassword());
                writer.println(doctor.getName());
                writer.println(doctor.getGender());
                writer.println(doctor.getEmail());
                writer.println(doctor.getPhone());
                writer.println(doctor.getAge());
                writer.println(); // Empty line separator
            }
        }
    }
    
    private void saveManagers(List<Manager> managers) throws IOException {
        try (PrintWriter writer = new PrintWriter("manager.txt")) {
            for (Manager manager : managers) {
                writer.println(manager.getUserId());
                writer.println(manager.getUsername());
                writer.println(manager.getPassword());
                writer.println(manager.getName());
                writer.println(manager.getGender());
                writer.println(manager.getEmail());
                writer.println(manager.getPhone());
                writer.println(manager.getAge());
                writer.println(); // Empty line separator
            }
        }
    }
    
    // Private helper methods for loading specific user types
    private List<User> loadStaffs() throws IOException {
        List<User> staffs = new ArrayList<>();
        File file = new File("staff.txt");
        
        if (!file.exists()) {
            return staffs;
        }
        
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String userId = scanner.nextLine();
                String username = scanner.nextLine();
                String password = scanner.nextLine();
                String name = scanner.nextLine();
                String gender = scanner.nextLine();
                String email = scanner.nextLine();
                String phone = scanner.nextLine();
                int age = Integer.parseInt(scanner.nextLine());
                if (scanner.hasNextLine()) scanner.nextLine(); // Skip empty line
                
                staffs.add(new main.Staff(userId, username, password, name, gender, email, phone, age));
            }
        }
        
        return staffs;
    }
    
    private List<User> loadCustomers() throws IOException {
        List<User> customers = new ArrayList<>();
        File file = new File("customer.txt");
        
        if (!file.exists()) {
            return customers;
        }
        
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String userId = scanner.nextLine();
                String username = scanner.nextLine();
                String password = scanner.nextLine();
                String name = scanner.nextLine();
                String gender = scanner.nextLine();
                String email = scanner.nextLine();
                String phone = scanner.nextLine();
                int age = Integer.parseInt(scanner.nextLine());
                if (scanner.hasNextLine()) scanner.nextLine(); // Skip empty line
                
                customers.add(new main.Customer(userId, username, password, name, gender, email, phone, age));
            }
        }
        
        return customers;
    }
    
    private List<User> loadDoctors() throws IOException {
        List<User> doctors = new ArrayList<>();
        File file = new File("doctor.txt");
        
        if (!file.exists()) {
            return doctors;
        }
        
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String userId = scanner.nextLine();
                String username = scanner.nextLine();
                String password = scanner.nextLine();
                String name = scanner.nextLine();
                String gender = scanner.nextLine();
                String email = scanner.nextLine();
                String phone = scanner.nextLine();
                int age = Integer.parseInt(scanner.nextLine());
                if (scanner.hasNextLine()) scanner.nextLine(); // Skip empty line
                
                doctors.add(new main.Doctor(userId, username, password, name, gender, email, phone, age));
            }
        }
        
        return doctors;
    }
    
    private List<Manager> loadManagers() throws IOException {
        List<Manager> managers = new ArrayList<>();
        File file = new File("manager.txt");
        
        if (!file.exists()) {
            return managers;
        }
        
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String userId = scanner.nextLine();
                String username = scanner.nextLine();
                String password = scanner.nextLine();
                String name = scanner.nextLine();
                String gender = scanner.nextLine();
                String email = scanner.nextLine();
                String phone = scanner.nextLine();
                int age = Integer.parseInt(scanner.nextLine());
                if (scanner.hasNextLine()) scanner.nextLine(); // Skip empty line
                
                managers.add(new Manager(userId, username, password, name, gender, email, phone, age));
            }
        }
        
        return managers;
    }
}