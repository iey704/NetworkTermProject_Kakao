package messenger;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.tree.DefaultMutableTreeNode;

public class newhome extends JFrame {
    private JPanel maintab;
    private JPanel lefttab;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JScrollPane scrollPane;
    public String searchString;
    public static final Color KAKAO_WHITE = new Color(255, 255, 255);
    public static final Color KAKAO_YELLOW = new Color(250, 225, 0);
    public static final Color KAKAO_GRAY = new Color(221, 221, 221);
    public static String ID;
    private String pw;
    private String nickname;
    private String birth;
    private String email;
    private String nam;

    Connection con = null;
    Statement stmt = null;
    String url = "jdbc:mysql://localhost/messenger";	//user 스키마
    String user = "root";
    String passwd = "1111";		//본인이 설정한 root 계정의 비밀번호를 입력하면 된다.

    public newhome(String ID) {
        this.ID = ID;
        DBconnection db = new DBconnection();
        Statement stmt = null;
        ResultSet rs = null;

        try {
            //Class의 forname()함수를 이용해 해당 클래스를 메모리로 로드.
            Class.forName("com.mysql.cj.jdbc.Driver");
            //url, ID, password를 입력하여 데이터베이스에 접속.
            con = DriverManager.getConnection(url, user, passwd);
            stmt = con.createStatement(); //순방향으로 데이터를 읽어내려간다.
            System.out.println("MySQL 서버 연동 성공");
            System.out.println(con);
        } catch(Exception e) {
            System.out.println("MySQL 서버 연동 실패 > " + e.toString());
        }
        String sql = "select id, pw, nickname, birth, email, name from user where id = " + ID;
        try {
            rs = stmt.executeQuery(sql);
        } catch (SQLException e4) {
            throw new RuntimeException(e4);
        }

        while (true) {
            try {
                if (!rs.next()) break;
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
/*
                    try {
                        System.out.println(rs.getString(1));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
*/

            try {
                ID = rs.getString(1);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            try {
                pw = rs.getString(2);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            try {
                nickname = rs.getString(3);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            try {
                birth = rs.getString(4);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            try {
                email = rs.getString(5);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            try {
                nam = rs.getString(6);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        }

        try {
            if (con != null)
                con.close();
            if (stmt != null && !stmt.isClosed())
                stmt.close();
            if (rs != null)
                rs.close();
        } catch (SQLException e6) {
            e6.printStackTrace();
        }


        Container cp = getContentPane();
        cp.setBackground(KAKAO_WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 600);
        setTitle("Home");
        cp.setLayout(null);

        //lefttab
        lefttab = new JPanel();
        lefttab.setBackground(KAKAO_GRAY);
        lefttab.setLayout(new BorderLayout());

        //lefttab (upper)
        JPanel minileft = new JPanel();
        minileft.setLayout(new GridLayout(3, 1));

        Icon img1 = new ImageIcon("src/images/profile.PNG");
        JButton profile = new JButton(img1);
        profile.setBackground(KAKAO_GRAY);
        minileft.add(profile);

        Icon img2 = new ImageIcon("src/images/chat.PNG");
        JButton chat = new JButton(img2);
        chat.setBackground(KAKAO_GRAY);
        minileft.add(chat);

        Icon img3 = new ImageIcon("src/images/more.PNG");
        JButton etc = new JButton(img3);
        etc.setBackground(KAKAO_GRAY);
        minileft.add(etc);

        lefttab.add(minileft, BorderLayout.NORTH);

        //lefttab (lower)
        JPanel lower = new JPanel();
        lower.setLayout(new GridLayout(2, 1));

        Icon img4 = new ImageIcon("src/images/emoticon.PNG");
        JButton imoji = new JButton(img4);
        imoji.setBackground(KAKAO_GRAY);
        lower.add(imoji);

        Icon img5 = new ImageIcon("src/images/setting.PNG");
        JButton setting = new JButton(img5);

        String finalID = ID;
        setting.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            new changeProfile(finalID);
            dispose();
            }
        });
        setting.setBackground(KAKAO_GRAY);
        lower.add(setting);

        lefttab.add(lower, BorderLayout.SOUTH);
        lefttab.setBounds(0, 0, 80, 562);
        cp.add(lefttab);


        //maintab
        maintab = new JPanel();
        maintab.setBackground(KAKAO_WHITE);
        maintab.setBounds(85, 0, 290, 555);
        maintab.setLayout(null);

        //maintab upper bound
        JPanel upper = new JPanel();
        upper.setLayout(null);
        upper.setBackground(KAKAO_WHITE);

        //maintab upper bound (left)
        JLabel text1 = new JLabel();
        text1.setText(" 프로필");
        text1.setFont(new Font("title", Font.BOLD, 18));
        text1.setBounds(0, 20, 80, 40);
        upper.add(text1);

        //maintab upper bound (right)
        JPanel op1 = new JPanel();
        op1.setBackground(KAKAO_WHITE);
        op1.setLayout(new FlowLayout());
        op1.setBounds(210, 0, 90, 40);

        Icon img6 = new ImageIcon("src/images/search.PNG");
        JButton search = new JButton(img6);
        search.setPreferredSize(new Dimension(30, 30));
        search.setForeground(KAKAO_WHITE);
        search.setBackground(KAKAO_WHITE);
        op1.add(search);

        Icon img7 = new ImageIcon("src/images/add_friend.PNG");
        JButton addf = new JButton(img7);
        addf.setPreferredSize(new Dimension(30, 30));
        addf.setForeground(KAKAO_WHITE);
        addf.setBackground(KAKAO_WHITE);
        op1.add(addf);

        upper.add(op1);
        upper.setBounds(0, 0, 290, 50);
        maintab.add(upper);

        //내 프로필
        JPanel p = new JPanel();
        p.setBounds(3, 60, 290, 60);
        p.setLayout(null);
        p.setBackground(KAKAO_WHITE);

        Icon img8 = new ImageIcon("src/images/me.PNG");             //UserID에 맞는 프로필 사진 받아와야함
        JButton me = new JButton(img8);
        me.setBackground(KAKAO_WHITE);
        me.setPreferredSize(new Dimension(50, 50));
        me.setBounds(0, 0, 60, 60);
        p.add(me);

        JLabel name = new JLabel();
        name.setText(nam);
        name.setBounds(65, 0, 60, 60);
        p.add(name);
        JLabel lblNewLabel = new JLabel("New label");
        lblNewLabel.setBounds(140, 10, 138, 50);
        p.add(lblNewLabel);
        maintab.add(p);

        JLabel temp = new JLabel("친구");
        temp.setBounds(3, 140, 50, 20);
        maintab.add(temp);


        //친구 list

        scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(3, 170, 280, 380);
        maintab.add(scrollPane);

        JPanel borderlayoutpanel = new JPanel();
        scrollPane.setViewportView(borderlayoutpanel);
        borderlayoutpanel.setLayout(new BorderLayout(0, 0));

        int cnt_friend = 10;
        DefaultMutableTreeNode Friend = new DefaultMutableTreeNode("가천대");

        for (int i = 0; i < cnt_friend; i++) {
            DefaultMutableTreeNode f1 = new DefaultMutableTreeNode("한결");
            DefaultMutableTreeNode f2 = new DefaultMutableTreeNode("혜원");
            DefaultMutableTreeNode f3 = new DefaultMutableTreeNode("하늘");
            Friend.add(f1);
            Friend.add(f2);
            Friend.add(f3);
        }

        JTree tree = new JTree(Friend);

        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.add(new JMenuItem("프로필 보기"));
        popupMenu.add(new JMenuItem("메세지 보내기"));
        tree.setPreferredSize(new Dimension(280, tree.getPreferredSize().height));
        tree.setComponentPopupMenu(popupMenu);
        initPopupListener(tree, popupMenu);
        tree.setBounds(3, 170, 280, 380);
        borderlayoutpanel.add(tree, BorderLayout.WEST);

        cp.add(maintab);


        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ;
                searchString = JOptionPane.showInputDialog("Enter the ID of your target.");
                if (searchString != null) {     //searchString과 친구의 name이 같을 경우
                    //개별 프로필 창 뜨게 해야함
                    dispose();

                }
            }
        });

        setVisible(true);
    }

    private static void initPopupListener(JTree tree, JPopupMenu popupMenu) {
        popupMenu.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                //get selected node's rectangle
                Rectangle rect = tree.getPathBounds(tree.getSelectionPath());
                Arrays.stream(popupMenu.getComponents()).forEach(c -> c.setEnabled(rect != null));
                if (rect == null) {
                    return;
                }

                Point p = new Point(rect.x + rect.width / 2, rect.y + rect.height);
                SwingUtilities.convertPointToScreen(p, tree);
                popupMenu.setLocation(p.x, p.y);
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {

            }
        });
    }
}

