package main;

import Doctor.*;

public class Receipt {
    private String receiptId;
    private String appointmentId;
    private String date;
    private String time;
    private double charges;
    private String customerName;
    private String doctorName;
    private String serviceType;

    public Receipt(String receiptId, String appointmentId, String date, String time, 
                   double charges, String customerName, String doctorName, String serviceType) {
        this.receiptId = receiptId;
        this.appointmentId = appointmentId;
        this.date = date;
        this.time = time;
        this.charges = charges;
        this.customerName = customerName;
        this.doctorName = doctorName;
        this.serviceType = serviceType;
    }

    // Getters
    public String getReceiptId() { return receiptId; }
    public String getAppointmentId() { return appointmentId; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public double getCharges() { return charges; }
    public String getCustomerName() { return customerName; }
    public String getDoctorName() { return doctorName; }
    public String getServiceType() { return serviceType; }

    // Setters
    public void setReceiptId(String receiptId) { this.receiptId = receiptId; }
    public void setAppointmentId(String appointmentId) { this.appointmentId = appointmentId; }
    public void setDate(String date) { this.date = date; }
    public void setTime(String time) { this.time = time; }
    public void setCharges(double charges) { this.charges = charges; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }
    
    @Override
    public String toString() {
        return "Receipt{" +
                "receiptId='" + receiptId + '\'' +
                ", appointmentId='" + appointmentId + '\'' +
                ", charges=" + charges +
                ", customerName='" + customerName + '\'' +
                '}';
    }
}
