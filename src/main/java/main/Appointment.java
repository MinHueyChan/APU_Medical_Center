
package main;
import Staff.*;
import main.*;
import java.time.LocalDate;

public class Appointment {
    private String appointmentId;
    private String customerUsername;
    private String date; 
    private String time; 
    private String type; 
    private String doctorUsername; 
    private String staffId;
    private String status;
    private double charges;
    private String feedback;
    private Integer rating;
    private String comment; 

    public Appointment(String appointmentId, String customerUsername, String date, String time, String type, String doctorUsername, String staffId, String status, double charges, String feedback, Integer rating, String comment) {
        this.appointmentId = appointmentId;
        this.customerUsername = customerUsername;
        this.date = date;
        this.time = time;
        this.type = type;
        this.doctorUsername = doctorUsername;
        this.staffId = staffId;
        this.status = status;
        this.charges = charges;
        this.feedback = feedback;
        this.rating = rating;
        this.comment = comment;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getType() {
        return type;
    }

    public String getDoctorUsername() {
        return doctorUsername;
    }

    public String getStaffId() {
        return staffId;
    }

    public String getStatus() {
        return status;
    }

    public void setDoctorUsername(String doctorUsername) {
        this.doctorUsername = doctorUsername;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public void setCustomerUsername(String customerUsername) {
    this.customerUsername = customerUsername;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public void setType(String type) {
        this.type = type;
    }

    public double getCharges() {
        return charges;
    }

    public void setCharges(double charges) {
        this.charges = charges;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getDateAsLocalDate() {
        return LocalDate.parse(this.date);
    }
    
    public boolean isPaid() {
        for (Payment p : DataIO.allPayments) {
            if (p.getAppointmentId().equals(this.appointmentId)) {
                return "Paid".equalsIgnoreCase(p.getPaymentStatus());
            }
        }
        return false;
    }
    
    @Override
        public String toString() {
        return "Appointment{" +
                "appointmentId='" + appointmentId + '\'' +
                ", customerUsername='" + customerUsername + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", charges=" + charges +
                '}';
    }
}

