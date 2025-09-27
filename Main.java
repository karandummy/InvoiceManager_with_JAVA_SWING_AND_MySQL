import javax.swing.*;

public class Main extends JFrame {
    private JTabbedPane tabbedPane;
    public Main() {
        setTitle("Invoice Management System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Add Customer", new Customer());
        tabbedPane.addTab("Customers", new ViewCustomers());
        tabbedPane.addTab("Add Invoice", new Invoice());
        tabbedPane.addTab("Invoices", new ViewInvoices());
        tabbedPane.addTab("Invoice Details", new InvoiceDetails());
        tabbedPane.addTab("Add Item", new Item());
        add(tabbedPane);
        setVisible(true);
    }
    public static void main(String[] args) {
        new Main();
    }
}
