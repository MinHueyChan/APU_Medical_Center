package Manager;

import main.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// Class to generate different types of reports
public class ManagerReport {
    
    // Generate appointment summary report
    public String generateAppointmentSummaryReport() {
        StringBuilder report = new StringBuilder();
        report.append("APU MEDICAL CENTER - APPOINTMENT SUMMARY REPORT\n");
        report.append("=".repeat(50)).append("\n");
        report.append("Generated on: ").append(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).append("\n\n");
        
        ArrayList<Appointment> appointments = main.DataIO.allAppointments;
        
        // Basic counts
        int totalAppointments = appointments.size();
        int completedAppointments = 0;
        int pendingAppointments = 0;
        int cancelledAppointments = 0;
        int todayAppointments = 0;
        int thisMonthAppointments = 0;
        
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfMonth = today.withDayOfMonth(1);
        
        for (Appointment appointment : appointments) {
            String status = appointment.getStatus().toLowerCase();
            if (status.equals("completed") || status.equals("successful")) completedAppointments++;
            else if (status.equals("pending") || status.equals("booked")) pendingAppointments++;
            else if (status.equals("cancelled") || status.equals("cancellation in progress")) cancelledAppointments++;
            
            try {
                LocalDate appointmentDate = LocalDate.parse(appointment.getDate());
                if (appointmentDate.equals(today)) todayAppointments++;
                if (appointmentDate.isAfter(firstDayOfMonth.minusDays(1)) && 
                    appointmentDate.isBefore(today.plusDays(1))) thisMonthAppointments++;
            } catch (Exception e) {
                // Skip invalid dates
            }
        }
        
        report.append("TOTAL APPOINTMENTS: ").append(totalAppointments).append("\n");
        report.append("COMPLETED: ").append(completedAppointments).append("\n");
        report.append("PENDING: ").append(pendingAppointments).append("\n");
        report.append("CANCELLED: ").append(cancelledAppointments).append("\n");
        report.append("TODAY: ").append(todayAppointments).append("\n");
        report.append("THIS MONTH: ").append(thisMonthAppointments).append("\n\n");
        
        // Status breakdown
        double completedPercentage = totalAppointments > 0 ? (double) completedAppointments / totalAppointments * 100 : 0;
        double pendingPercentage = totalAppointments > 0 ? (double) pendingAppointments / totalAppointments * 100 : 0;
        double cancelledPercentage = totalAppointments > 0 ? (double) cancelledAppointments / totalAppointments * 100 : 0;
        
        report.append("STATUS BREAKDOWN:\n");
        report.append(String.format("Completed: %.1f%%\n", completedPercentage));
        report.append(String.format("Pending: %.1f%%\n", pendingPercentage));
        report.append(String.format("Cancelled: %.1f%%\n", cancelledPercentage));
        
        return report.toString();
    }
    
    // Generate appointments by doctor report
    public String generateAppointmentsByDoctorReport() {
        StringBuilder report = new StringBuilder();
        report.append("APU MEDICAL CENTER - APPOINTMENTS BY DOCTOR REPORT\n");
        report.append("=".repeat(50)).append("\n");
        report.append("Generated on: ").append(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).append("\n\n");
        
        Map<String, Integer> doctorAppointmentCount = new HashMap<>();
        Map<String, String> doctorNames = new HashMap<>();
        
        // Initialize doctor counts
        for (User doctor : main.DataIO.allDoctors) {
            doctorAppointmentCount.put(doctor.getUsername(), 0);
            doctorNames.put(doctor.getUsername(), doctor.getName());
        }
        
        // Count appointments per doctor
        for (Appointment appointment : main.DataIO.allAppointments) {
            String doctorUsername = appointment.getDoctorUsername();
            if (doctorUsername != null && !doctorUsername.isEmpty()) {
                doctorAppointmentCount.put(doctorUsername, 
                    doctorAppointmentCount.getOrDefault(doctorUsername, 0) + 1);
            }
        }
        
        report.append("DOCTOR APPOINTMENT COUNTS:\n");
        report.append("-".repeat(30)).append("\n");
        
        for (Map.Entry<String, Integer> entry : doctorAppointmentCount.entrySet()) {
            String doctorUsername = entry.getKey();
            int count = entry.getValue();
            String doctorName = doctorNames.getOrDefault(doctorUsername, "Unknown Doctor");
            report.append(String.format("%-20s (%s): %d appointments\n", 
                doctorName, doctorUsername, count));
        }
        
        return report.toString();
    }
    
    // Generate revenue report
    public String generateRevenueReport() {
        StringBuilder report = new StringBuilder();
        report.append("APU MEDICAL CENTER - REVENUE REPORT\n");
        report.append("=".repeat(50)).append("\n");
        report.append("Generated on: ").append(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).append("\n\n");
        
        double totalRevenue = 0;
        Map<String, Double> doctorRevenue = new HashMap<>();
        Map<String, Double> monthlyRevenue = new HashMap<>();
        
        // Calculate revenue
        for (Appointment appointment : main.DataIO.allAppointments) {
            if (appointment.getStatus().toLowerCase().equals("completed") || 
                appointment.getStatus().toLowerCase().equals("successful")) {
                double charges = appointment.getCharges();
                totalRevenue += charges;
                
                // Doctor revenue
                String doctorUsername = appointment.getDoctorUsername();
                if (doctorUsername != null && !doctorUsername.isEmpty()) {
                    doctorRevenue.put(doctorUsername, 
                        doctorRevenue.getOrDefault(doctorUsername, 0.0) + charges);
                }
                
                // Monthly revenue
                try {
                    LocalDate appointmentDate = LocalDate.parse(appointment.getDate());
                    String monthKey = appointmentDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));
                    monthlyRevenue.put(monthKey, 
                        monthlyRevenue.getOrDefault(monthKey, 0.0) + charges);
                } catch (Exception e) {
                    // Skip invalid dates
                }
            }
        }
        
        report.append(String.format("TOTAL REVENUE: RM %.2f\n\n", totalRevenue));
        
        // Revenue by doctor
        report.append("REVENUE BY DOCTOR:\n");
        report.append("-".repeat(30)).append("\n");
        for (Map.Entry<String, Double> entry : doctorRevenue.entrySet()) {
            String doctorUsername = entry.getKey();
            double revenue = entry.getValue();
            String doctorName = getDoctorName(doctorUsername);
            report.append(String.format("%-20s (%s): RM %.2f\n", 
                doctorName, doctorUsername, revenue));
        }
        
        // Revenue by month
        report.append("\nREVENUE BY MONTH:\n");
        report.append("-".repeat(30)).append("\n");
        for (Map.Entry<String, Double> entry : monthlyRevenue.entrySet()) {
            String month = entry.getKey();
            double revenue = entry.getValue();
            report.append(String.format("%s: RM %.2f\n", month, revenue));
        }
        
        return report.toString();
    }
    
    public String generateDoctorPerformanceReport() {
        StringBuilder report = new StringBuilder();
        report.append("APU MEDICAL CENTER - DOCTOR PERFORMANCE REPORT\n");
        report.append("=".repeat(50)).append("\n");
        report.append("Generated on: ").append(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).append("\n\n");
        
        Map<String, DoctorPerformance> doctorStats = new HashMap<>();
        
        // Initialize doctor stats
        for (User doctor : main.DataIO.allDoctors) {
            doctorStats.put(doctor.getUsername(), new DoctorPerformance(doctor.getName()));
        }
        
        // Calculate stats
		for (Appointment appointment : main.DataIO.allAppointments) {
            String doctorUsername = appointment.getDoctorUsername();
            if (doctorUsername != null && !doctorUsername.isEmpty()) {
                DoctorPerformance stats = doctorStats.get(doctorUsername);
                if (stats != null) {
					String statusLower = appointment.getStatus() == null ? "" : appointment.getStatus().toLowerCase();
					// Skip cancelled appointments entirely from statistics
					if (statusLower.equals("cancelled") || statusLower.equals("cancellation in progress")) {
						continue;
					}

					stats.totalAppointments++;
					if (statusLower.equals("completed") || statusLower.equals("successful")) {
						stats.completedAppointments++;
						stats.totalRevenue += appointment.getCharges();
					}
					if (appointment.getRating() != null) {
						stats.totalRating += appointment.getRating();
						stats.ratedAppointments++;
					}
                }
            }
        }
        
        report.append("DOCTOR PERFORMANCE METRICS:\n");
        report.append("-".repeat(80)).append("\n");
        report.append(String.format("%-20s %-8s %-8s %-10s %-10s %-8s\n", 
            "Doctor", "Total", "Completed", "Revenue", "Avg Rating", "Success%"));
        report.append("-".repeat(80)).append("\n");
        
        for (Map.Entry<String, DoctorPerformance> entry : doctorStats.entrySet()) {
            DoctorPerformance stats = entry.getValue();
            double successRate = stats.totalAppointments > 0 ? 
                (double) stats.completedAppointments / stats.totalAppointments * 100 : 0;
            double avgRating = stats.ratedAppointments > 0 ? 
                (double) stats.totalRating / stats.ratedAppointments : 0;
            
            report.append(String.format("%-20s %-8d %-8d %-10.2f %-10.1f %-8.1f\n",
                stats.doctorName, stats.totalAppointments, stats.completedAppointments,
                stats.totalRevenue, avgRating, successRate));
        }
        
        return report.toString();
    }
    
    public String generateFeedbackSummaryReport() {
        StringBuilder report = new StringBuilder();
        report.append("APU MEDICAL CENTER - FEEDBACK SUMMARY REPORT\n");
        report.append("=".repeat(50)).append("\n");
        report.append("Generated on: ").append(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).append("\n\n");
        
        int totalFeedbacks = 0;
        int positiveFeedbacks = 0;
        int negativeFeedbacks = 0;
        int neutralFeedbacks = 0;
        double totalRating = 0;
        int ratedFeedbacks = 0;
        
        // Analyze feedbacks from appointments
        for (Appointment appointment : main.DataIO.allAppointments) {
            if (appointment.getFeedback() != null && !appointment.getFeedback().isEmpty()) {
                totalFeedbacks++;
                
                if (appointment.getRating() != null) {
                    ratedFeedbacks++;
                    totalRating += appointment.getRating();
                    
                    if (appointment.getRating() >= 4) positiveFeedbacks++;
                    else if (appointment.getRating() <= 2) negativeFeedbacks++;
                    else neutralFeedbacks++;
                }
            }
        }
        
        double averageRating = ratedFeedbacks > 0 ? totalRating / ratedFeedbacks : 0;
        
        report.append("FEEDBACK STATISTICS:\n");
        report.append("-".repeat(30)).append("\n");
        report.append("Total Feedbacks: ").append(totalFeedbacks).append("\n");
        report.append("Positive (4-5 stars): ").append(positiveFeedbacks).append("\n");
        report.append("Negative (1-2 stars): ").append(negativeFeedbacks).append("\n");
        report.append("Neutral (3 stars): ").append(neutralFeedbacks).append("\n");
        report.append("Average Rating: ").append(String.format("%.2f", averageRating)).append("\n");
        
        return report.toString();
    }
    
    private String getDoctorName(String username) {
        for (User doctor : main.DataIO.allDoctors) {
            if (doctor.getUsername().equals(username)) {
                return doctor.getName();
            }
        }
        return "Unknown Doctor";
    }
    
    private static class DoctorPerformance {
        String doctorName;
        int totalAppointments = 0;
        int completedAppointments = 0;
        double totalRevenue = 0;
        int totalRating = 0;
        int ratedAppointments = 0;
        
        DoctorPerformance(String doctorName) {
            this.doctorName = doctorName;
        }
    }
}