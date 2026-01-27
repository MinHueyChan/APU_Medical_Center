package Manager;

import java.time.LocalDate;

public class Feedback {
    private String feedbackId;
    private String appointmentId;
    private String customerUsername;
    private String doctorUsername;
    private String feedbackText;
    private String comment;
    private Integer rating;
    private String date;
    private String time;
    
    public Feedback(String feedbackId, String appointmentId, String customerUsername, 
                   String doctorUsername, String feedbackText, String comment, Integer rating, 
                   String date, String time) {
        this.feedbackId = feedbackId;
        this.appointmentId = appointmentId;
        this.customerUsername = customerUsername;
        this.doctorUsername = doctorUsername;
        this.feedbackText = feedbackText;
        this.comment = comment;
        this.rating = rating;
        this.date = date;
        this.time = time;
    }
    
    // Getters
    public String getFeedbackId() { return feedbackId; }
    public String getAppointmentId() { return appointmentId; }
    public String getCustomerUsername() { return customerUsername; }
    public String getDoctorUsername() { return doctorUsername; }
    public String getFeedbackText() { return feedbackText; }
    public String getComment() { return comment; }
    public Integer getRating() { return rating; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    
    // Setters
    public void setFeedbackId(String feedbackId) { this.feedbackId = feedbackId; }
    public void setAppointmentId(String appointmentId) { this.appointmentId = appointmentId; }
    public void setCustomerUsername(String customerUsername) { this.customerUsername = customerUsername; }
    public void setDoctorUsername(String doctorUsername) { this.doctorUsername = doctorUsername; }
    public void setFeedbackText(String feedbackText) { this.feedbackText = feedbackText; }
    public void setComment(String comment) { this.comment = comment; }
    public void setRating(Integer rating) { this.rating = rating; }
    public void setDate(String date) { this.date = date; }
    public void setTime(String time) { this.time = time; }
    
    public LocalDate getDateAsLocalDate() {
        try {
            return LocalDate.parse(this.date);
        } catch (Exception e) {
            return null;
        }
    }
    
    public boolean isPositive() {
        return rating != null && rating >= 4;
    }
    
    public boolean isNegative() {
        return rating != null && rating <= 2;
    }
}
