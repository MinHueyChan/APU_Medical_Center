
package main;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class DataIO {
    public static ArrayList<Manager> allManagers = new ArrayList<Manager>();
    public static ArrayList<Staff> allStaffs = new ArrayList<Staff>();
    public static ArrayList<Customer> allCustomers = new ArrayList<Customer>();
    public static ArrayList<Doctor> allDoctors = new ArrayList<Doctor>();
    public static ArrayList<Appointment> allAppointments = new ArrayList<Appointment>();
    public static ArrayList<Payment> allPayments = new ArrayList<Payment>();
    public static ArrayList<Receipt> allReceipts = new ArrayList<Receipt>();
    
        public static Manager checkManagerName(String name){
        for(Manager m : allManagers){
            if(m.getUsername().equalsIgnoreCase(name)){
                return m;
            }
        }
        return null;
    }
    
    public static Manager checkManagerId(String Id){
        for(Manager m : allManagers){
            if(m.getUserId().equalsIgnoreCase(Id)){
                return m;
            }
        }
        return null;
    }
    
    public static Staff checkStaffName(String name){
        for(Staff s : allStaffs){
            if(s.getUsername().equalsIgnoreCase(name)){
                return s;
            }
        }
        return null;
    }
    
    public static Staff checkStaffId(String Id){
        for(Staff s : allStaffs){
            if(s.getUserId().equalsIgnoreCase(Id)){
                return s;
            }
        }
        return null;
    }
    
    public static Doctor checkDoctorName(String name) {
    for (Doctor d : allDoctors) {
        if (d.getUsername().equalsIgnoreCase(name)) {
            return d;
        }
    }
    return null;
    }

    public static Doctor checkDoctorId(String id) {
        for (Doctor d : allDoctors) {
            if (d.getUserId().equalsIgnoreCase(id)) {
                return d;
            }
        }
        return null;
    }
    
    public static Customer checkCustomerName(String name) {
    for (Customer c : allCustomers) {
        if (c.getUsername().equalsIgnoreCase(name)) {
            return c;
        }
    }
    return null;
    }
    
    public static Customer checkCustomerId(String Id){
        for(Customer c : allCustomers){
            if(c.getUserId().equalsIgnoreCase(Id)){
                return c;
            }
        }
        return null;
    }
    
    public static void updateUser(Customer customer) throws IOException {
        Customer c = checkCustomerId(customer.getUserId());
        if (c != null) {
            c.setName(customer.getName());
            c.setEmail(customer.getEmail());
            c.setPhone(customer.getPhone());
            c.setGender(customer.getGender());
            c.setAge(customer.getAge());
            c.setPassword(customer.getPassword());
        }
        write();
    }
    
    public static Appointment findAppointmentById(String appointmentId) {
    for (Appointment a : allAppointments) {
        if (a.getAppointmentId().equals(appointmentId)) {
            return a;
        }
    }
    return null; 
}

    public static void updateAppointmentStatus(String appointmentId, String newStatus) {
        for (Appointment a : allAppointments) {
            if (a.getAppointmentId().equals(appointmentId)) {
                a.setStatus(newStatus);
                break;
            }
        }
        write();
    }
    
    public static void updateAppointmentFeedback(String appointmentId, String target, int rating, String comment) {
        for (Appointment a : allAppointments) {
            if (a.getAppointmentId().equals(appointmentId)) {
                a.setFeedback(target);
                a.setRating(rating);
                a.setComment(comment);
                break;
            }
        }
        write();
    }
    
    public static void updateCustomerComment(String appointmentId, int rating, String comment) {
        for (Appointment a : allAppointments) {
            if (a.getAppointmentId().equals(appointmentId)) {
                a.setRating(rating);
                a.setComment(comment);
                break;
            }
        }
        write();
    }
    
    public static void write(){
        try{
            PrintWriter manager = new PrintWriter("manager.txt");
            for (Manager m : allManagers) {
                manager.println(m.getUserId());
                manager.println(m.getUsername());
                manager.println(m.getPassword());
                manager.println(m.getName());
                manager.println(m.getGender());
                manager.println(m.getEmail());
                manager.println(m.getPhone());
                manager.println(m.getAge());
                manager.println();
            }
            manager.close();
            
            PrintWriter staff = new PrintWriter("staff.txt");
            for(Staff s : allStaffs){
                staff.println(s.getUserId());
                staff.println(s.getUsername());
                staff.println(s.getPassword());
                staff.println(s.getName());
                staff.println(s.getGender());
                staff.println(s.getEmail());
                staff.println(s.getPhone());
                staff.println(s.getAge());
                staff.println();
            }
            staff.close();
            
            PrintWriter cus = new PrintWriter("customer.txt");
            for(Customer c : allCustomers){
                cus.println(c.getUserId());
                cus.println(c.getUsername());
                cus.println(c.getPassword());
                cus.println(c.getName());
                cus.println(c.getGender());
                cus.println(c.getEmail());
                cus.println(c.getPhone());
                cus.println(c.getAge());
                cus.println();
            }
            cus.close();
            
            PrintWriter doctor = new PrintWriter("doctor.txt");
            for (Doctor d : allDoctors) {
                doctor.println(d.getUserId());
                doctor.println(d.getUsername());
                doctor.println(d.getPassword());
                doctor.println(d.getName());
                doctor.println(d.getGender());
                doctor.println(d.getEmail());
                doctor.println(d.getPhone());
                doctor.println(d.getAge());
                doctor.println();
            }
            doctor.close();
            
            PrintWriter appFile = new PrintWriter("appointment.txt");
            for (Appointment a : allAppointments) {
                appFile.println(a.getAppointmentId());
                appFile.println(a.getCustomerUsername());
                appFile.println(a.getDate());
                appFile.println(a.getTime());
                appFile.println(a.getType());
                appFile.println(a.getDoctorUsername() == null ? "" : a.getDoctorUsername());
                appFile.println(a.getStaffId() == null ? "" : a.getStaffId());
                appFile.println(a.getStatus());
                appFile.println(a.getCharges());
                appFile.println(a.getFeedback() == null ? "" : a.getFeedback());
                appFile.println(a.getRating() == null ? "" : a.getRating());
                appFile.println(a.getComment() == null ? "" : a.getComment());
                appFile.println();
            }
            appFile.close();
            
            PrintWriter paymentFile = new PrintWriter("payment.txt");
            for (Payment p : allPayments) {
                paymentFile.println(p.getPaymentId());
                paymentFile.println(p.getAppointmentId());
                paymentFile.println(p.getCharges());
                paymentFile.println(p.getFeedback() == null ? "" : p.getFeedback());
                paymentFile.println(p.getPaymentStatus());
                paymentFile.println(p.getPaymentDate() == null ? "" : p.getPaymentDate());
                paymentFile.println(p.getPaymentTime() == null ? "" : p.getPaymentTime());
                paymentFile.println();
            }
            paymentFile.close();
            
            PrintWriter receiptFile = new PrintWriter("receipt.txt");
            for (Receipt r : allReceipts) {
                receiptFile.println(r.getReceiptId());
                receiptFile.println(r.getAppointmentId());
                receiptFile.println(r.getDate());
                receiptFile.println(r.getTime());
                receiptFile.println(r.getCharges());
                receiptFile.println(r.getCustomerName() == null ? "" : r.getCustomerName());
                receiptFile.println(r.getDoctorName() == null ? "" : r.getDoctorName());
                receiptFile.println(r.getServiceType() == null ? "" : r.getServiceType());
                receiptFile.println();
            }
            receiptFile.close();
        }
        catch(Exception e){
            System.out.println("Error writing data");
            e.printStackTrace();
        }
    }
    public static void read() {
        allManagers.clear();
        allStaffs.clear();
        allCustomers.clear();
        allDoctors.clear();
        allAppointments.clear();
        allPayments.clear();
        allReceipts.clear();

        File managerFile = new File("manager.txt");
        File staffFile = new File("staff.txt");
        File customerFile = new File("customer.txt");
        File appointmentFile = new File("appointment.txt");
        File doctorFile = new File("doctor.txt");
        File paymentFile = new File("payment.txt");
        File receiptFile = new File("receipt.txt");
        
        // Read managers
        if (!managerFile.exists() || managerFile.length() == 0) {
            allManagers.add(new Manager("M0001", "admin", "12345", "Administrator", "M", 
                "admin@amc.com", "0123456789", 30));
            try (PrintWriter manager = new PrintWriter("manager.txt")) {
                for (Manager m : allManagers) {
                    manager.println(m.getUserId());
                    manager.println(m.getUsername());
                    manager.println(m.getPassword());
                    manager.println(m.getName());
                    manager.println(m.getGender());
                    manager.println(m.getEmail());
                    manager.println(m.getPhone());
                    manager.println(m.getAge());
                    manager.println();
                }
            } catch (Exception e) {
                System.out.println("Error writing default manager data");
                e.printStackTrace();
            }
        } else {
            try (Scanner sc = new Scanner(managerFile)) {
                while (sc.hasNext()) {
                    String userId = sc.nextLine();
                    String username = sc.nextLine();
                    String password = sc.nextLine();
                    String name = sc.nextLine();
                    String gender = sc.nextLine();
                    String email = sc.nextLine();
                    String phone = sc.nextLine();
                    int age = Integer.parseInt(sc.nextLine());
                    if (sc.hasNextLine()) sc.nextLine(); // Skip empty line
                    allManagers.add(new Manager(userId, username, password, name, gender, email, phone, age));
                }
            } catch (Exception e) {
                System.out.println("Error reading manager data");
                e.printStackTrace();
            }
        }
        
        // read staff
        if (!staffFile.exists() || staffFile.length() == 0) {
            allStaffs.add(new Staff("S0001", "staff", "12345", "Staff", "M", 
                "staff@amc.com", "0123456789", 30));
            try (PrintWriter staff = new PrintWriter("staff.txt")) {
                for (Staff s : allStaffs) {
                    staff.println(s.getUserId());
                    staff.println(s.getUsername());
                    staff.println(s.getPassword());
                    staff.println(s.getName());
                    staff.println(s.getGender());
                    staff.println(s.getEmail());
                    staff.println(s.getPhone());
                    staff.println(s.getAge());
                    staff.println();
                }
            } catch (Exception e) {
                System.out.println("Error writing default staff data");
                e.printStackTrace();
            }
        }

        try (Scanner sc = new Scanner(staffFile)) {
        while (sc.hasNext()) {
            String userId= sc.nextLine();
            String username = sc.nextLine();
            String password = sc.nextLine();
            String name = sc.nextLine();
            String gender = sc.nextLine();
            String email = sc.nextLine();
            String phone = sc.nextLine();
            int age = Integer.parseInt(sc.nextLine());
            if (sc.hasNextLine()) sc.nextLine(); 
            allStaffs.add(new Staff(userId, username, password, name, gender, email, phone, age));
        }
        } catch (Exception e) {
            System.out.println("Error reading staff data");
            e.printStackTrace();
    }

        // read customer
        if (!customerFile.exists()) {
            try (PrintWriter cus = new PrintWriter("customer.txt")) {
                for (Customer c : allCustomers) {
                    cus.println(c.getUserId());
                    cus.println(c.getUsername());
                    cus.println(c.getPassword());
                    cus.println(c.getName());
                    cus.println(c.getGender());
                    cus.println(c.getEmail());
                    cus.println(c.getPhone());
                    cus.println(c.getAge());
                    cus.println();
                }
            } catch (Exception e) {
                System.out.println("Error writing default customer data");
                e.printStackTrace();
            }
        } else {
            try (Scanner sc = new Scanner(customerFile)) {
                while (sc.hasNext()) {
                    String userId = sc.nextLine();
                    String username = sc.nextLine();
                    String password = sc.nextLine();
                    String name = sc.nextLine();
                    String gender = sc.nextLine();
                    String email = sc.nextLine();
                    String phone = sc.nextLine();
                    int age = Integer.parseInt(sc.nextLine());
                    if (sc.hasNextLine()) sc.nextLine();
                    allCustomers.add(new Customer(userId, username, password, name, gender, email, phone, age));
                }
            } catch (Exception e) {
                System.out.println("Error reading customer data");
                e.printStackTrace();
            }
        }

        //read doctor
        if (!doctorFile.exists() || doctorFile.length() == 0) {
            allDoctors.add(new Doctor("D0001", "doc1", "12345", "Dr. John", "M", "john@hospital.com", "0123456789", 40));
            try (PrintWriter doctorWriter = new PrintWriter("doctor.txt")) {
                for (Doctor d : allDoctors) {
                    doctorWriter.println(d.getUserId());
                    doctorWriter.println(d.getUsername());
                    doctorWriter.println(d.getPassword());
                    doctorWriter.println(d.getName());
                    doctorWriter.println(d.getGender());
                    doctorWriter.println(d.getEmail());
                    doctorWriter.println(d.getPhone());
                    doctorWriter.println(d.getAge());
                    doctorWriter.println();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try (Scanner sc = new Scanner(doctorFile)) {
                while (sc.hasNext()) {
                    String userId = sc.nextLine();
                    String username = sc.nextLine();
                    String password = sc.nextLine();
                    String name = sc.nextLine();
                    String gender = sc.nextLine();
                    String email = sc.nextLine();
                    String phone = sc.nextLine();
                    int age = Integer.parseInt(sc.nextLine());
                    if (sc.hasNextLine()) sc.nextLine();
                    allDoctors.add(new Doctor(userId, username, password, name, gender, email, phone, age));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    
    if (appointmentFile.exists()) {
        try (Scanner sc = new Scanner(appointmentFile)) {
            while (sc.hasNext()) {
                String id = sc.nextLine();
                String customerUsername = sc.nextLine();
                String date = sc.nextLine();
                String time = sc.nextLine();
                String type = sc.nextLine();
                String doctor = sc.nextLine();
                if (doctor.isEmpty()) doctor = null;
                String staffId = sc.nextLine();
                if (staffId.isEmpty()) staffId = null;
                String status = sc.nextLine();
                String chargesStr = sc.nextLine();
                double charges = 0.0;
                if (!chargesStr.isEmpty()) {
                    try {
                        charges = Double.parseDouble(chargesStr);
                    } catch (NumberFormatException e) {
                        charges = 0.0;
                    }
                }
                String feedback = sc.nextLine();
                String ratingStr = sc.nextLine();
                Integer rating = null;
                if (!ratingStr.isEmpty()) {
                    try {
                        rating = Integer.parseInt(ratingStr);
                    } catch (NumberFormatException e) {
                        rating = null;
                    }
                }
                String comment = sc.nextLine();
                if (comment.isEmpty()) comment = null;
                if (sc.hasNextLine()) sc.nextLine();
                allAppointments.add(new Appointment(id, customerUsername, date, time, type, doctor, staffId, status, charges, feedback, rating, comment));
            }
        } catch (Exception e) {
            System.out.println("Error reading appointment data");
            e.printStackTrace();
        }
    }
    
    if (paymentFile.exists()) {
        try (Scanner sc = new Scanner(paymentFile)) {
            while (sc.hasNext()) {
                String paymentId = sc.nextLine();
                String appointmentId = sc.nextLine();
                String chargesStr = sc.nextLine();
                double charges = 0.0;
                if (!chargesStr.isEmpty()) {
                    try {
                        charges = Double.parseDouble(chargesStr);
                    } catch (NumberFormatException e) {
                        charges = 0.0;
                    }
                }
                String feedback = sc.nextLine();
                String paymentStatus = sc.nextLine();
                String paymentDate = sc.nextLine();
                String paymentTime = sc.nextLine();
                if (sc.hasNextLine()) sc.nextLine();
                allPayments.add(new Payment(paymentId, appointmentId, charges, feedback, paymentStatus, paymentDate, paymentTime));
            }
        } catch (Exception e) {
            System.out.println("Error reading payment data");
            e.printStackTrace();
        }
    }
    
    if (receiptFile.exists()) {
        try (Scanner sc = new Scanner(receiptFile)) {
            while (sc.hasNext()) {
                String receiptId = sc.nextLine();
                String appointmentId = sc.nextLine();
                String date = sc.nextLine();
                String time = sc.nextLine();
                String chargesStr = sc.nextLine();
                double charges = 0.0;
                if (!chargesStr.isEmpty()) {
                    try {
                        charges = Double.parseDouble(chargesStr);
                    } catch (NumberFormatException e) {
                        charges = 0.0;
                    }
                }
                String customerName = sc.nextLine();
                String doctorName = sc.nextLine();
                String serviceType = sc.nextLine();
                if (sc.hasNextLine()) sc.nextLine();
                allReceipts.add(new Receipt(receiptId, appointmentId, date, time, charges, customerName, doctorName, serviceType));
            }
        } catch (Exception e) {
            System.out.println("Error reading receipt data");
            e.printStackTrace();
        }
    }
    }
    
    public static String generateManagerId() {
        int maxId = 0;
        for (Manager m : allManagers) {
            try {
                int num = Integer.parseInt(m.getUserId().substring(1)); 
                if (num > maxId) {
                    maxId = num;
                }
            } catch (Exception e) {
                // Ignore invalid IDs
            }
        }
        return String.format("M%04d", maxId + 1); 
    }
    
    public static String generateStaffId() {
        int maxId = 0;
        for (Staff s : allStaffs) {
            try {
                int num = Integer.parseInt(s.getUserId().substring(1)); 
                if (num > maxId) {
                    maxId = num;
                }
            } catch (Exception e) {
                // Ignore invalid IDs
            }
        }
        return String.format("S%04d", maxId + 1); 
    }
    
    public static String generateCustomerId() {
        int maxId = 0;
        for (Customer c : allCustomers) {
            try {
                int num = Integer.parseInt(c.getUserId().substring(1)); 
                if (num > maxId) {
                    maxId = num;
                }
            } catch (Exception e) {
                // Ignore invalid IDs
            }
        }
        return String.format("C%04d", maxId + 1); 
    }
    
    public static String generateDoctorId() {
        int maxId = 0;
        for (Doctor d : allDoctors) {
            try {
                int num = Integer.parseInt(d.getUserId().substring(1)); 
                if (num > maxId) {
                    maxId = num;
                }
            } catch (Exception e) {

            }
        }
        return String.format("D%04d", maxId + 1); 
    }
}
