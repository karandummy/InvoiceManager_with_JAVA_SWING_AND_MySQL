import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.awt.*;

public class ViewCustomers extends JPanel{
    public ViewCustomers(){
        setLayout(new BorderLayout());
        String[] cols={"ID", "Name", "Email", "Phone"};
        DefaultTableModel model=new DefaultTableModel(cols, 0);
        JTable table=new JTable(model);
        JScrollPane pane=new JScrollPane(table);
        add(pane);
        try (Connection con = DBConnection.getConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM customers");
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("customer_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // public static void main(String[] args){
    //     JFrame frame = new JFrame("Customer Details");
    //     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //     frame.setSize(400, 250);
    //     frame.setContentPane(new ViewCustomers());
    //     frame.setVisible(true);
    // }
}