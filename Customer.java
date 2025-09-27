import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Customer extends JPanel {
    public Customer(){
        setLayout(new BorderLayout());
        JPanel customer=new JPanel(new GridLayout(4, 2, 10, 10));
        JLabel id=new JLabel("Customer ID:");
        JTextField id_=new JTextField();
        JLabel name=new JLabel("Name:");
        JTextField name_=new JTextField();
        JLabel email=new JLabel("Email:");
        JTextField email_=new JTextField();
        JLabel phone=new JLabel("Phone:");
        JTextField phone_=new JTextField();
        customer.add(id);
        customer.add(id_);
        customer.add(name);
        customer.add(name_);
        customer.add(email);
        customer.add(email_);
        customer.add(phone);
        customer.add(phone_);
        JButton save = new JButton("Save");
        add(customer,BorderLayout.CENTER);
        add(save,BorderLayout.SOUTH);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        save.addActionListener(e -> {
            try (Connection con=DBConnection.getConnection()) {
                String query="INSERT INTO customers VALUES (?, ?, ?, ?)";
                PreparedStatement ps=con.prepareStatement(query);
                ps.setInt(1,Integer.parseInt(id_.getText()));
                ps.setString(2,name_.getText());
                ps.setString(3,email_.getText());
                ps.setString(4,phone_.getText());
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Customer added successfully!");
                id_.setText("");
                name_.setText("");
                email_.setText("");
                phone_.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
    }
    // public static void main(String[] args){
    //     JFrame frame = new JFrame("Customer Form");
    //     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //     frame.setSize(400, 250);
    //     frame.setContentPane(new Customer());
    //     frame.setVisible(true);
    // }
}




