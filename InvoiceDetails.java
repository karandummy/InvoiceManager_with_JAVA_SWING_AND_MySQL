import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InvoiceDetails extends JPanel {
    public InvoiceDetails() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel invDetails = new JPanel();
        JLabel id = new JLabel("Enter Invoice ID:");
        JTextField id_ = new JTextField(10);
        JButton view = new JButton("View");
        invDetails.add(id);
        invDetails.add(id_);
        invDetails.add(view);
        JLabel custName_ = new JLabel("Customer: - ");
        String[] cols = {"Item ID", "Item Name", "Quantity", "Price per Unit", "Total"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        JTable table = new JTable(model);
        JScrollPane pane = new JScrollPane(table);
        JLabel total = new JLabel("Total Amount: 0.00");
        JButton print = new JButton("Print Invoice");
        print.setEnabled(false);
        view.addActionListener(e -> {
            model.setRowCount(0);
            double TotalPrice=0;
            String custName="";
            boolean flag=false;
            print.setEnabled(false);
            try (Connection con = DBConnection.getConnection()) {
                String cust="SELECT c.name FROM invoices i JOIN customers c ON i.customer_id = c.customer_id WHERE i.invoice_id = ?";
                PreparedStatement cust_=con.prepareStatement(cust);
                cust_.setInt(1, Integer.parseInt(id_.getText()));
                ResultSet res=cust_.executeQuery();
                if (!res.next()) {
                    JOptionPane.showMessageDialog(this,
                            "Invoice not found!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    custName_.setText("Customer: Not found");
                    total.setText("Total Amount: 0.00");
                    print.setEnabled(false);
                }else{
                    custName = res.getString("name");
                    custName_.setText("Customer: "+custName);
                    String query = "SELECT item_id, item_name, quantity, price_per_unit, (quantity * price_per_unit) AS total FROM items WHERE invoice_id = ?";
                    PreparedStatement ps = con.prepareStatement(query);
                    ps.setInt(1, Integer.parseInt(id_.getText()));
                    ResultSet rs=ps.executeQuery();
                    while (rs.next()) {
                        flag=true;
                        model.addRow(new Object[]{
                                rs.getInt("item_id"),
                                rs.getString("item_name"),
                                rs.getInt("quantity"),
                                rs.getDouble("price_per_unit"),
                                rs.getDouble("total")
                        });
                        TotalPrice += rs.getDouble("total");
                    }
                    if (flag) {
                       total.setText("Total Amount: " + TotalPrice);
                        print.setEnabled(true);
                        String finalInvoiceId=id_.getText();
                        String finalCustomer=custName;
                        double finalGrandTotal=TotalPrice;
                        print.addActionListener(ev -> {
                            try {
                                String filename="Invoice_"+finalInvoiceId+".txt";
                                try (PrintWriter writer=new PrintWriter(new FileWriter(filename))) {
                                    writer.println("========= INVOICE =========");
                                    writer.println("Invoice ID: "+finalInvoiceId);
                                    writer.println("Customer: "+finalCustomer);
                                    writer.println("Date: "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                                    writer.println("---------------------------");
                                    writer.printf("%-10s %-20s %-10s %-15s %-10s%n","ItemID", "Name", "Qty", "Price/Unit", "Total");
                                    for (int i = 0; i < model.getRowCount(); i++) {
                                        writer.printf("%-10s %-20s %-10s %-15s %-10s%n",
                                                model.getValueAt(i, 0),
                                                model.getValueAt(i, 1),
                                                model.getValueAt(i, 2),
                                                model.getValueAt(i, 3),
                                                model.getValueAt(i, 4));
                                    }
                                    writer.println("---------------------------");
                                    writer.println("Grand Total: " + finalGrandTotal);
                                    writer.println("===========================");
                                }
                                JOptionPane.showMessageDialog(this,
                                        "Invoice saved as "+"Invoice_" +finalInvoiceId +".txt",
                                        "Success",
                                        JOptionPane.INFORMATION_MESSAGE);
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(this, "Error writing file: " + ex.getMessage());
                            }
                        });
                    } else {
                       total.setText("Total Amount: 0.00");
                        JOptionPane.showMessageDialog(this,
                                "No items found for this invoice!",
                                "Warning",
                                JOptionPane.WARNING_MESSAGE);
                        print.setEnabled(false);
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
        add(invDetails);
        add(custName_);
        add(pane);
        add(total);
        add(print);
        setVisible(true);
    }
    public static void main(String[] args){
        JFrame frame = new JFrame("Invoice Details");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250);
        frame.setContentPane(new InvoiceDetails());
        frame.setVisible(true);
    }
}
