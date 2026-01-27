package Staff;

import main.User;
import main.*;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ManageCustomer {
    private ManageCustomerPage view;
    private User currentUser; 

    public ManageCustomer(ManageCustomerPage view, User currentUser) {
    this.view = view;
    this.currentUser = currentUser;
    initController();
    loadCustomerData(DataIO.allCustomers);
    }
    
    private void initController() {
        view.getSearchButton().addActionListener(e -> searchCustomer());
        view.getDeleteButton().addActionListener(e -> deleteCustomer());
        view.getCreateButton().addActionListener(e -> createCustomer());
        view.getUpdateButton().addActionListener(e -> updateCustomer());
        view.getBackButton().addActionListener(e -> goBack());
    }
    
    private void loadCustomerData(List<Customer> customers) {
        DefaultTableModel model = (DefaultTableModel) view.getCustomerTable().getModel();
        model.setRowCount(0);
        for (Customer c : customers) {
            model.addRow(new Object[]{
                c.getUserId(),
                c.getUsername(),
                c.getName(),
                c.getGender(),
                c.getEmail(),
                c.getPhone(),
                c.getAge()
            });
        }
    }
    
    private void searchCustomer() {
        String keyword = view.getSearchField().getText().trim().toLowerCase();
        List<Customer> filtered = DataIO.allCustomers.stream()
            .filter(c -> c.getUsername().toLowerCase().contains(keyword)
                    || c.getName().toLowerCase().contains(keyword)
                    || c.getEmail().toLowerCase().contains(keyword))
            .collect(Collectors.toList());
        loadCustomerData(filtered);
    }
    
    private void createCustomer() {
    CustomerForm form = new CustomerForm((JFrame) view, null);
    form.setVisible(true);

    if (form.isSaved()) {
        loadCustomerData(DataIO.allCustomers);
    }
    }

    private void updateCustomer() {
    int selectedRow = view.getCustomerTable().getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(view, "Choose the customer you want to update.");
        return;
    }

    String username = (String) view.getCustomerTable().getValueAt(selectedRow, 1);
    Customer customer = DataIO.checkCustomerName(username);

    CustomerForm form = new CustomerForm((JFrame) view, customer);
    form.setVisible(true);

    if (form.isSaved()) {
        loadCustomerData(DataIO.allCustomers);
    }
    }
    
    private void deleteCustomer() {
        int selectedRow = view.getCustomerTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Choose the customer you want to delete.");
            return;
        }
        String username = (String) view.getCustomerTable().getValueAt(selectedRow, 1);
        int confirm = JOptionPane.showConfirmDialog(view, "Are you confirm delete " + username + " ？", "confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            DataIO.allCustomers.removeIf(c -> c.getUsername().equals(username));
            DataIO.write();
            loadCustomerData(DataIO.allCustomers);
        }
    }
    private void goBack() {
    view.dispose(); 
    new StaffDashboard(currentUser).setVisible(true);
    }
}

    

