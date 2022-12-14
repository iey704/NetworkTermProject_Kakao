package messenger;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class addFriend extends JFrame {

    private JPanel contentPane;
    private JTextField textField;
    DBconnection db=new DBconnection();
    /**
     * Launch the application.
     */


    /**
     * Create the frame.
     */
    public addFriend(String userID) {
        setTitle("친구 추가");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 453, 112);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("추가할 친구의 아이디를 입력해주세요");
        lblNewLabel.setBounds(12, 10, 334, 15);
        contentPane.add(lblNewLabel);

        textField = new JTextField();
        textField.setBounds(12, 35, 280, 21);
        contentPane.add(textField);
        textField.setColumns(10);

        JButton btnNewButton = new JButton("추가");
        btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String newFriendID=textField.getText();

                try{
                    db.friendinsert(userID,newFriendID);
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
                dispose();
                System.out.println("userID:"+userID);
                new home(userID);
            }
        });
        btnNewButton.setBounds(303, 35, 95, 23);
        contentPane.add(btnNewButton);
        setVisible(true);
    }

}
