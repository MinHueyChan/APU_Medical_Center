package Manager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DoctorSchedule class as specified in the UML diagram
 * Manages doctor availability and scheduling
 */
public class DoctorSchedule {
    private String doctorId;
    private List<LocalDateTime> availableSlots;
    
    public DoctorSchedule(String doctorId) {
        this.doctorId = doctorId;
        this.availableSlots = new ArrayList<>();
    }
    
    public DoctorSchedule(String doctorId, List<LocalDateTime> availableSlots) {
        this.doctorId = doctorId;
        this.availableSlots = availableSlots != null ? new ArrayList<>(availableSlots) : new ArrayList<>();
    }
    
    // Getters and Setters
    public String getDoctorId() {
        return doctorId;
    }
    
    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
    
    public List<LocalDateTime> getAvailableSlots() {
        return new ArrayList<>(availableSlots);
    }
    
    public void setAvailableSlots(List<LocalDateTime> availableSlots) {
        this.availableSlots = availableSlots != null ? new ArrayList<>(availableSlots) : new ArrayList<>();
    }
    
    /**
     * Check if doctor is available at a specific date and time
     * @param dt Date and time to check
     * @return boolean indicating availability
     */
    public boolean checkAvailability(LocalDateTime dt) {
        if (dt == null || availableSlots == null) {
            return false;
        }
        
        // Check if the specific datetime is in available slots
        for (LocalDateTime slot : availableSlots) {
            if (slot.equals(dt)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Block a time slot (remove from available slots)
     * @param dt Date and time to block
     */
    public void blockSlot(LocalDateTime dt) {
        if (dt != null && availableSlots != null) {
            availableSlots.removeIf(slot -> slot.equals(dt));
        }
    }
    
    /**
     * Add a time slot to available slots
     * @param dt Date and time to add
     */
    public void addSlot(LocalDateTime dt) {
        if (dt != null && availableSlots != null && !availableSlots.contains(dt)) {
            availableSlots.add(dt);
        }
    }
    
    /**
     * Get all available slots for a specific date
     * @param date Date to get slots for
     * @return List of available slots for that date
     */
    public List<LocalDateTime> getSlotsForDate(java.time.LocalDate date) {
        List<LocalDateTime> slotsForDate = new ArrayList<>();
        if (date != null && availableSlots != null) {
            for (LocalDateTime slot : availableSlots) {
                if (slot.toLocalDate().equals(date)) {
                    slotsForDate.add(slot);
                }
            }
        }
        return slotsForDate;
    }
    
    /**
     * Clear all available slots
     */
    public void clearAllSlots() {
        if (availableSlots != null) {
            availableSlots.clear();
        }
    }
    
    /**
     * Get the number of available slots
     * @return Number of available slots
     */
    public int getSlotCount() {
        return availableSlots != null ? availableSlots.size() : 0;
    }
    
    /**
     * Check if doctor has any available slots
     * @return boolean indicating if there are any available slots
     */
    public boolean hasAvailableSlots() {
        return availableSlots != null && !availableSlots.isEmpty();
    }
    
    @Override
    public String toString() {
        return "DoctorSchedule{" +
                "doctorId='" + doctorId + '\'' +
                ", availableSlots=" + (availableSlots != null ? availableSlots.size() : 0) + " slots" +
                '}';
    }
}

