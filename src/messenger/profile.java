package messenger;
import com.mysql.cj.util.StringUtils;
import messenger.DBconnection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.IDN;
import java.sql.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.tree.DefaultMutableTreeNode;



public class profile extends JFrame{
    DBconnection db ;
    public static final Color KAKAO_YELLOW = new Color(247,230,0);
    public static final Color KAKAO_BLUE = new Color(155,187,212);
    public static final Color KAKAO_BROWN= new Color(58,29,29);
    public static final Color KAKAO_WHITE= new Color(255,255,255);

    private String id;
    private String pw;
    private String nickname;
    private String birth;
    private String email;
    private String name;

    Connection con = null;
    Statement stmt = null;
    String url = "jdbc:mysql://localhost/messenger";	//user 스키마
    String user = "root";
    String passwd = "1111";		//본인이 설정한 root 계정의 비밀번호를 입력하면 된다.


    public profile(String userID){
        this.id=userID;
        db=new DBconnection();

        Container cp = getContentPane();
        cp.setBackground(KAKAO_WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(409, 152);
        setVisible(true);
        setTitle(db.getUserName(userID)+"님의 프로필");
        getContentPane().setLayout(null);

        JPanel upPanel = new JPanel();
        upPanel.setBounds(0, 0, 400, 120);
        upPanel.setBackground(KAKAO_BLUE);
        cp.add(upPanel);

        Icon e = new ImageIcon("src/images/exit.PNG");
        JButton exit = new JButton(e);
        exit.setBounds(345, 0, 40, 40);
        exit.setBackground(KAKAO_BLUE);

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();
            }
        });
        upPanel.setLayout(null);
        upPanel.add(exit);

        JLabel lblNewLabel = new JLabel(db.getUserName(userID));
        lblNewLabel.setBounds(114, 10, 122, 15);
        upPanel.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("이름:");
        lblNewLabel_1.setBounds(42, 10, 52, 15);
        upPanel.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("별명:");
        lblNewLabel_2.setBounds(42, 35, 52, 15);
        upPanel.add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel(db.getNickname(userID));
        lblNewLabel_3.setBounds(114, 35, 112, 15);
        upPanel.add(lblNewLabel_3);

        JLabel lblNewLabel_4 = new JLabel("최근 종료 시간:");
        lblNewLabel_4.setBounds(12, 61, 100, 15);
        upPanel.add(lblNewLabel_4);

        JLabel lblNewLabel_5 = new JLabel("비어있음(3)");
        lblNewLabel_5.setBounds(114, 60, 122, 15);
        upPanel.add(lblNewLabel_5);

        JLabel lblNewLabel_6 = new JLabel("접속 여부:");
        lblNewLabel_6.setBounds(12, 86, 82, 15);
        upPanel.add(lblNewLabel_6);

        JLabel lblNewLabel_7 = new JLabel("비어있음(4)");
        lblNewLabel_7.setBounds(114, 86, 112, 15);
        upPanel.add(lblNewLabel_7);
    }
}
