import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.awt.*;

public class ViewInvoices extends JPanel {
    public ViewInvoices(){
        setLayout(new BorderLayout());
        String[] cols={"Invoice ID", "Customer ID", "Date"};
        DefaultTableModel model=new DefaultTableModel(cols, 0);
        JTable table=new JTable(model);
        JScrollPane pane=new JScrollPane(table);
        add(pane);
        try (Connection con=DBConnection.getConnection()) {
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("SELECT * FROM invoices");
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("invoice_id"),
                        rs.getInt("customer_id"),
                        rs.getTimestamp("date")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }

    }
    // public static void main(String[] args){
    //     JFrame frame = new JFrame("Invoice details");
    //     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //     frame.setSize(400, 250);
    //     frame.setContentPane(new ViewInvoices());
    //     frame.setVisible(true);
    // }
}
