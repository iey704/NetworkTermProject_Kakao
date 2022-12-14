/*
package messenger;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;

public class searchUser extends JFrame {

    private JPanel contentPane;
    private JTextField textField;
    DBconnection db =new DBconnection();
    */
/**
     * Launch the application.
     *//*



    */
/**
     * Create the frame.
     *//*

    public searchUser(String userID) {
        setTitle("아이디 검색");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("검색할 아이디를 입력해주세요");
        lblNewLabel.setBounds(12, 10, 390, 15);
        contentPane.add(lblNewLabel);

        textField = new JTextField();
        textField.setBounds(12, 28, 390, 21);
        contentPane.add(textField);
        textField.setColumns(10);
        DefaultMutableTreeNode result= new DefaultMutableTreeNode("결과");
        JButton btnNewButton = new JButton("검색");
        btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            String word=textField.getText();
            String[] resultIDs=db.userSearch(word);
            DefaultMutableTreeNode f1;
            for(int i=0;i<resultIDs.length;i++){
                f1=new DefaultMutableTreeNode(db.getUserName(resultIDs[i]));
                //System.out.println("db.getUserName(resultIDs["+i+"]):"+db.getUserName(resultIDs[i]));
                result.add(f1);
            }
            db.userSearch(word);
            }
        });
        JButton btnNewButton_1 = new JButton("뒤로가기");
        btnNewButton_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new home(userID);
                dispose();
            }
        });
        btnNewButton_1.setBounds(307, 0, 95, 23);
        contentPane.add(btnNewButton_1);
        btnNewButton.setBounds(307, 59, 95, 23);
        contentPane.add(btnNewButton);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(12, 93, 390, 160);
        contentPane.add(scrollPane);
        JTree tree = new JTree(result);

        scrollPane.setColumnHeaderView(tree);
        setVisible(true);
    }

}
*/
package messenger;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

public class searchUser extends JFrame {
    DefaultMutableTreeNode f1;
    private JPanel contentPane;
    private JTextField textField;
    DBconnection db =new DBconnection();
    /**
     * Launch the application.
     */
    String nodeID;

    /**
     * Create the frame.
     */
    public searchUser(String userID) {
        JPanel main = new JPanel();

        setTitle("아이디 검색");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        contentPane.setBackground(Color.white);
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel borderlayoutpanel = new JPanel();
        borderlayoutpanel.setLayout(new BorderLayout(0, 0));
        borderlayoutpanel.setBounds(12, 93, 390, 160);

        JLabel lblNewLabel = new JLabel("검색할 아이디를 입력해주세요");
        lblNewLabel.setBounds(12, 10, 390, 15);
        contentPane.add(lblNewLabel);

        textField = new JTextField();
        textField.setBounds(12, 28, 390, 21);
        contentPane.add(textField);
        textField.setColumns(10);
        DefaultMutableTreeNode result= new DefaultMutableTreeNode("결과");
        JButton btnNewButton = new JButton("검색");
        btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String word=textField.getText();
                String[] resultIDs=db.userSearch(word);

                for(int i=0;resultIDs[i]!=null;i++){
                    f1=new DefaultMutableTreeNode(resultIDs[i]+"/"+db.getUserName(resultIDs[i]));
                    //System.out.println("db.getUserName(resultIDs["+i+"]):"+db.getUserName(resultIDs[i]));
                    result.add(f1);
                }
                db.userSearch(word);
            }
        });
        JButton btnNewButton_1 = new JButton("X");
        btnNewButton_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new home(userID);
                dispose();
            }
        });
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem pprofile = new JMenuItem("프로필 보기");
        JMenuItem mmessage = new JMenuItem("메세지 보내기");
        popupMenu.add(pprofile);
        popupMenu.add(mmessage);
        pprofile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // System.out.println(e.getSource());
                new profile(nodeID);

            }
        });
        btnNewButton_1.setBounds(329, 2, 40, 23);
        contentPane.add(btnNewButton_1);
        btnNewButton.setBounds(307, 59, 95, 23);
        contentPane.add(btnNewButton);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(12, 93, 390, 160);
        scrollPane.setViewportView(borderlayoutpanel);

        contentPane.add(scrollPane);
        JTree tree = new JTree(result);
        scrollPane.setColumnHeaderView(tree);
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {

                DefaultMutableTreeNode node;
                node = ( DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                if(node == null) return;

                String nodeName = (String) node.getUserObject(); //캐스팅은 꼭해주어야 한다.
                if(nodeName.equals("결과"))
                {

                }
                else{
                nodeID=nodeName.substring(0,nodeName.indexOf("/"));
                // System.out.println("선택된 노드 : " + nodeID);

            }
        }});
       // borderlayoutpanel.add(tree, BorderLayout.CENTER);
        //tree.setPreferredSize(new Dimension(280, tree.getPreferredSize().height));
        tree.setComponentPopupMenu(popupMenu);
        //initPopupListener(tree, popupMenu);
        tree.setBounds(3, 170, 280, 380);
        borderlayoutpanel.add(tree, BorderLayout.WEST);
        setVisible(true);
    }

}
