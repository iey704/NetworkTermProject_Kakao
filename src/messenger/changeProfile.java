package messenger;



import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class changeProfile extends JFrame {

    private JPanel contentPane;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    DBconnection db=new DBconnection();
    /**
     * Launch the application.
     */


    /**
     * Create the frame.
     */
    public changeProfile(String userID) {
        setTitle("프로필 변경");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 479, 179);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btnNewButton = new JButton("뒤로가기");
        btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new home(userID);
                dispose();
            }
        });
        btnNewButton.setBounds(12, 10, 95, 23);
        contentPane.add(btnNewButton);

        textField = new JTextField();
        textField.setText("");
        textField.setBounds(12, 61, 143, 21);
        contentPane.add(textField);
        textField.setColumns(10);

        JLabel lblNewLabel = new JLabel("바꿀 비밀번호");
        lblNewLabel.setBounds(12, 43, 143, 15);
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("바꿀 프로필 메세지");
        lblNewLabel_1.setBounds(12, 90, 143, 15);
        contentPane.add(lblNewLabel_1);

        textField_1 = new JTextField();
        textField_1.setBounds(12, 111, 143, 21);
        contentPane.add(textField_1);
        textField_1.setColumns(10);

        JButton btnNewButton_1 = new JButton("전송");
        btnNewButton_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String newPW=textField.getText();
                db.changePW(userID,newPW);
            }
        });
        btnNewButton_1.setBounds(164, 60, 66, 23);
        contentPane.add(btnNewButton_1);

        JButton btnNewButton_2 = new JButton("전송");
        btnNewButton_2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String newProfileMessage=textField_1.getText();
                db.changeProfileMessage(userID,newProfileMessage);
            }
        });
        btnNewButton_2.setBounds(164, 110, 66, 23);
        contentPane.add(btnNewButton_2);

        JLabel lblNewLabel_2 = new JLabel("바꿀 이름");
        lblNewLabel_2.setBounds(243, 43, 128, 15);
        contentPane.add(lblNewLabel_2);

        textField_2 = new JTextField();
        textField_2.setBounds(242, 61, 129, 21);
        contentPane.add(textField_2);
        textField_2.setColumns(10);

        JButton btnNewButton_3 = new JButton("전송");
        btnNewButton_3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String newName=textField_2.getText();
                db.changeName(userID,newName);
            }
        });
        btnNewButton_3.setBounds(383, 60, 70, 23);
        contentPane.add(btnNewButton_3);

        JLabel lblNewLabel_3 = new JLabel("바꿀 별명");
        lblNewLabel_3.setBounds(243, 90, 128, 15);
        contentPane.add(lblNewLabel_3);

        textField_3 = new JTextField();
        textField_3.setBounds(242, 111, 129, 21);
        contentPane.add(textField_3);
        textField_3.setColumns(10);

        JButton btnNewButton_4 = new JButton("전송");
        btnNewButton_4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String newNickname=textField_3.getText();
                db.changeNickname(userID,newNickname);
            }
        });
        btnNewButton_4.setBounds(383, 110, 70, 23);
        contentPane.add(btnNewButton_4);
        setVisible(true);
    }

}
