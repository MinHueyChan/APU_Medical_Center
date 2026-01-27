package main;

import Staff.*;

public class Payment {
    private String paymentId;
    private String appointmentId;
    private double charges;
    private String feedback;
    private String paymentStatus; 
    private String paymentDate;
    private String paymentTime;

    public Payment(String paymentId, String appointmentId, double charges, String feedback, String paymentStatus) {
        this.paymentId = paymentId;
        this.appointmentId = appointmentId;
        this.charges = charges;
        this.feedback = feedback;
        this.paymentStatus = paymentStatus;
        this.paymentDate = "";
        this.paymentTime = "";
    }

    public Payment(String paymentId, String appointmentId, double charges, String feedback, String paymentStatus, String paymentDate, String paymentTime) {
        this.paymentId = paymentId;
        this.appointmentId = appointmentId;
        this.charges = charges;
        this.feedback = feedback;
        this.paymentStatus = paymentStatus;
        this.paymentDate = paymentDate;
        this.paymentTime = paymentTime;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public double getCharges() {
        return charges;
    }

    public String getFeedback() {
        return feedback;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public void setCharges(double charges) {
        this.charges = charges;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }
    
    @Override
    public String toString() {
        return "Payment{" +
                "paymentId='" + paymentId + '\'' +
                ", appointmentId='" + appointmentId + '\'' +
                ", charges=" + charges +
                ", paymentStatus='" + paymentStatus + '\'' +
                '}';
    }
}
