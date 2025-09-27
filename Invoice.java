import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.*;

public class Invoice extends JPanel{
    public Invoice(){
        setLayout(new BorderLayout());

        JPanel invoice=new JPanel(new GridLayout(2,2,10,10));
        JLabel id=new JLabel("Invoice ID:");
        JTextField id_=new JTextField();
        JLabel cust=new JLabel("Customer ID:");
        JTextField cust_=new JTextField();
        JButton save= new JButton("Save");
        invoice.add(id);
        invoice.add(id_);
        invoice.add(cust);
        invoice.add(cust_);
        add(invoice,BorderLayout.CENTER);
        add(save,BorderLayout.SOUTH);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        save.addActionListener(e->{
            try (Connection con=DBConnection.getConnection()) {
                String query="INSERT INTO invoices VALUES (?, ?,NOW())";
                PreparedStatement ps=con.prepareStatement(query);
                ps.setInt(1, Integer.parseInt(id_.getText()));
                ps.setInt(2, Integer.parseInt(cust_.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this,"Invoice added!");
                id_.setText("");
                cust_.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
    }
    // public static void main(String[] args){
    //     JFrame frame = new JFrame("Invoice Form");
    //     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //     frame.setSize(400, 250);
    //     frame.setContentPane(new Invoice());
    //     frame.setVisible(true);
    // }
}