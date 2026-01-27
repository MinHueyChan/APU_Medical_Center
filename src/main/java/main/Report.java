package main;

import java.time.LocalDate;

/**
 * Report class representing generated reports in the system
 * Follows the UML diagram specification
 */
public class Report {
    private String id;
    private String type;
    private LocalDate generatedDate;
    private String content;
    
    public Report() {
        this.generatedDate = LocalDate.now();
    }
    
    public Report(String id, String type, LocalDate generatedDate, String content) {
        this.id = id;
        this.type = type;
        this.generatedDate = generatedDate;
        this.content = content;
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public LocalDate getGeneratedDate() {
        return generatedDate;
    }
    
    public void setGeneratedDate(LocalDate generatedDate) {
        this.generatedDate = generatedDate;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    /**
     * Generate report content based on criteria
     * @param from Start date
     * @param to End date
     * @param criteria Additional criteria
     */
    public void generate(LocalDate from, LocalDate to, java.util.Map<String, Object> criteria) {
        // This method would be implemented to generate report content
        // For now, it's a placeholder as the actual generation is handled by ManagerReport
        this.generatedDate = LocalDate.now();
    }
    
    /**
     * Export report to file
     * @param path File path to export to
     */
    public void exportToFile(String path) {
        try {
            java.io.PrintWriter writer = new java.io.PrintWriter(path);
            writer.println("Report ID: " + this.id);
            writer.println("Type: " + this.type);
            writer.println("Generated Date: " + this.generatedDate);
            writer.println("=".repeat(50));
            writer.println(this.content);
            writer.close();
        } catch (Exception e) {
            System.err.println("Error exporting report to file: " + e.getMessage());
        }
    }
    
    @Override
    public String toString() {
        return "Report{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", generatedDate=" + generatedDate +
                ", contentLength=" + (content != null ? content.length() : 0) +
                '}';
    }
}

