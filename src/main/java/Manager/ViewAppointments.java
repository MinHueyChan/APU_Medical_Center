package Manager;

import main.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

// Window to view and search appointments
public class ViewAppointments extends JFrame {
    private JTable appointmentTable;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;
    private JButton searchBtn, sortBtn, refreshBtn, backBtn, clearBtn;
    private JTextField searchField;
    private JComboBox<String> searchTypeCombo, sortCombo, monthCombo;
    private ArrayList<Appointment> allAppointments;
    
    // Constructor
    public ViewAppointments() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        loadAppointments();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("View All Appointments");
        setSize(1000, 700);
        setLocationRelativeTo(null);
    }
    
    // Create UI components
    private void initializeComponents() {
        // Table setup
        String[] columns = {"Appointment ID", "Customer", "Date", "Time", "Type", 
                           "Doctor", "Staff ID", "Status", "Charges"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        appointmentTable = new JTable(tableModel);
        appointmentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        appointmentTable.getTableHeader().setReorderingAllowed(false);
        
        // Initialize sorter
        sorter = new TableRowSorter<>(tableModel);
        appointmentTable.setRowSorter(sorter);
        
        // Buttons
        searchBtn = new JButton("Search");
        sortBtn = new JButton("Sort");
        refreshBtn = new JButton("Refresh");
        clearBtn = new JButton("Clear");
        backBtn = new JButton("Back to Manager Dashboard");
        
        // Search components
        searchField = new JTextField(20);
        searchTypeCombo = new JComboBox<>(new String[]{"Doctor Name", "Date"});
        sortCombo = new JComboBox<>(new String[]{"Date", "Doctor", "Status", "Charges"});
        monthCombo = new JComboBox<>(new String[]{"All", "January", "February", "March", "April", "May", "June", 
                                                  "July", "August", "September", "October", "November", "December"});
        
        // Style buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 12);
        searchBtn.setFont(buttonFont);
        sortBtn.setFont(buttonFont);
        refreshBtn.setFont(buttonFont);
        clearBtn.setFont(buttonFont);
        backBtn.setFont(buttonFont);
        
        searchBtn.setBackground(new Color(70, 130, 180));
        sortBtn.setBackground(new Color(255, 140, 0));
        refreshBtn.setBackground(new Color(34, 139, 34));
        clearBtn.setBackground(new Color(128, 128, 128));
        backBtn.setBackground(new Color(220, 20, 60));
        
        searchBtn.setForeground(Color.WHITE);
        sortBtn.setForeground(Color.WHITE);
        refreshBtn.setForeground(Color.WHITE);
        clearBtn.setForeground(Color.WHITE);
        backBtn.setForeground(Color.WHITE);
    }
    
    // Arrange components on screen
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(25, 25, 112));
        JLabel titleLabel = new JLabel("All Appointments");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        
        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(new Color(240, 248, 255));
        searchPanel.add(new JLabel("Search by:"));
        searchPanel.add(searchTypeCombo);
        searchPanel.add(searchField);
        searchPanel.add(new JLabel("Month:"));
        searchPanel.add(monthCombo);
        searchPanel.add(searchBtn);
        searchPanel.add(clearBtn);
        searchPanel.add(new JLabel("Sort by:"));
        searchPanel.add(sortCombo);
        searchPanel.add(sortBtn);
        searchPanel.add(refreshBtn);
        
        // Table panel
        JScrollPane scrollPane = new JScrollPane(appointmentTable);
        scrollPane.setPreferredSize(new Dimension(950, 500));
        
        // Back button panel
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        backPanel.setBackground(new Color(240, 248, 255));
        backPanel.add(backBtn);
        
        // Main content panel
        JPanel mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.add(searchPanel, BorderLayout.NORTH);
        mainContentPanel.add(scrollPane, BorderLayout.CENTER);
        mainContentPanel.add(backPanel, BorderLayout.SOUTH);
        
        add(titlePanel, BorderLayout.NORTH);
        add(mainContentPanel, BorderLayout.CENTER);
    }
    
    // Set up button click events
    private void setupEventHandlers() {
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });
        
        sortBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSort();
            }
        });
        
        refreshBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadAppointments();
            }
        });
        
        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });
        
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearSearch();
            }
        });
        
        monthCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });
        
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    // Load appointments from data
    private void loadAppointments() {
        allAppointments = new ArrayList<>(main.DataIO.allAppointments);
        updateTable();
    }
    
    // Update table with appointment data
    private void updateTable() {
        tableModel.setRowCount(0);
        for (Appointment appointment : allAppointments) {
            tableModel.addRow(new Object[]{
                appointment.getAppointmentId(),
                appointment.getCustomerUsername(),
                appointment.getDate(),
                appointment.getTime(),
                appointment.getType(),
                appointment.getDoctorUsername() != null ? appointment.getDoctorUsername() : "Not Assigned",
                appointment.getStaffId() != null ? appointment.getStaffId() : "N/A",
                appointment.getStatus(),
                String.format("%.2f", appointment.getCharges())
            });
        }
    }
    
    // Search appointments by doctor name, date, and month
    private void performSearch() {
        String searchText = searchField.getText().trim();
        String searchType = (String) searchTypeCombo.getSelectedItem();
        String selectedMonth = (String) monthCombo.getSelectedItem();
        
        // Create a list to hold all filters
        java.util.List<RowFilter<Object, Object>> filters = new java.util.ArrayList<>();
        
        // Apply search filter
        if (!searchText.isEmpty()) {
            if (searchType.equals("Doctor Name")) {
                filters.add(RowFilter.regexFilter("(?i)" + searchText, 5)); // Doctor column
            } else if (searchType.equals("Date")) {
                filters.add(RowFilter.regexFilter(searchText, 2)); // Date column
            }
        }
        
        // Apply month filter
        if (!selectedMonth.equals("All")) {
            int monthNumber = getMonthNumber(selectedMonth);
            filters.add(new RowFilter<Object, Object>() {
                @Override
                public boolean include(Entry<? extends Object, ? extends Object> entry) {
                    String dateStr = (String) entry.getValue(2); // Date column
                    try {
                        LocalDate date = LocalDate.parse(dateStr);
                        return date.getMonthValue() == monthNumber;
                    } catch (Exception e) {
                        return false;
                    }
                }
            });
        }
        
        // Apply all filters
        if (filters.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.andFilter(filters));
        }
    }
    
    // Clear all search filters and reset form
    private void clearSearch() {
        searchField.setText("");
        searchTypeCombo.setSelectedIndex(0);
        monthCombo.setSelectedIndex(0);
        sorter.setRowFilter(null);
    }
    
    // Helper method to get month number
    private int getMonthNumber(String monthName) {
        String[] months = {"", "January", "February", "March", "April", "May", "June", 
                          "July", "August", "September", "October", "November", "December"};
        for (int i = 1; i < months.length; i++) {
            if (months[i].equals(monthName)) {
                return i;
            }
        }
        return 0;
    }
    
    // Sort appointments by selected column
    private void performSort() {
        String sortType = (String) sortCombo.getSelectedItem();
        
        switch (sortType) {
            case "Date":
                sorter.setComparator(2, new Comparator<String>() {
                    @Override
                    public int compare(String date1, String date2) {
                        try {
                            LocalDate d1 = LocalDate.parse(date1);
                            LocalDate d2 = LocalDate.parse(date2);
                            return d1.compareTo(d2);
                        } catch (Exception e) {
                            return date1.compareTo(date2);
                        }
                    }
                });
                sorter.sort();
                break;
            case "Doctor":
                sorter.setComparator(5, new Comparator<String>() {
                    @Override
                    public int compare(String doctor1, String doctor2) {
                        return doctor1.compareToIgnoreCase(doctor2);
                    }
                });
                sorter.sort();
                break;
            case "Status":
                sorter.setComparator(7, new Comparator<String>() {
                    @Override
                    public int compare(String status1, String status2) {
                        return status1.compareToIgnoreCase(status2);
                    }
                });
                sorter.sort();
                break;
            case "Charges":
                sorter.setComparator(8, new Comparator<String>() {
                    @Override
                    public int compare(String charges1, String charges2) {
                        try {
                            double c1 = Double.parseDouble(charges1);
                            double c2 = Double.parseDouble(charges2);
                            return Double.compare(c1, c2);
                        } catch (Exception e) {
                            return charges1.compareTo(charges2);
                        }
                    }
                });
                sorter.sort();
                break;
        }
    }
}
