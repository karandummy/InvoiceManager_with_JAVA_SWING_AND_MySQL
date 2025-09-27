import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Item extends JPanel{
    public Item(){
        setLayout(new BorderLayout());
        JPanel item=new JPanel(new GridLayout(4,2,10,10));
        JLabel inv=new JLabel("Invoice ID:");
        JTextField inv_=new JTextField();
        JLabel name=new JLabel("Item Name:");
        JTextField name_=new JTextField();
        JLabel qty=new JLabel("Quantity:");
        JTextField qty_=new JTextField();
        JLabel price=new JLabel("Price per Unit:");
        JTextField price_=new JTextField();
        JButton save=new JButton("Save");
        item.add(inv);
        item.add(inv_);
        item.add(qty);
        item.add(qty_);
        item.add(name);
        item.add(name_);
        item.add(price);
        item.add(price_);
        add(item,BorderLayout.CENTER);
        add(save,BorderLayout.SOUTH);
        save.addActionListener(e->{
            try (Connection con=DBConnection.getConnection()) {
                String query="INSERT INTO items (invoice_id, item_name, quantity, price_per_unit) VALUES (?, ?, ?, ?)";
                PreparedStatement ps=con.prepareStatement(query);
                ps.setInt(1,Integer.parseInt(inv_.getText()));
                ps.setString(2,name_.getText());
                ps.setInt(3,Integer.parseInt(qty_.getText()));
                ps.setDouble(4,Double.parseDouble(price_.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this,"Item added!");
                inv_.setText("");
                name_.setText("");
                qty_.setText("");
                price_.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,"Error: "+ex.getMessage());
            }
        });
    }
    // public static void main(String[] args){
    //     JFrame frame = new JFrame("Item");
    //     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //     frame.setSize(400, 250);
    //     frame.setContentPane(new Item());
    //     frame.setVisible(true);
    // }
}