package messenger;
import com.mysql.cj.util.StringUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

public class home extends JFrame {
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
    private String ID;
    private String pw;
    private String nickname;
    private String birth;
    private String email;
    private String nam;
    String nodeID;
    JTree tree;
    Connection con = null;
    Statement stmt = null;
    String url = "jdbc:mysql://localhost/messenger";	//user 스키마
    String user = "root";
    String passwd = "1111";		//본인이 설정한 root 계정의 비밀번호를 입력하면 된다.

    public home(String ID) {
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
        } catch (Exception e) {
            System.out.println("MySQL 서버 연동 실패 > " + e.toString());
        }
        String sql = "select id, pw, nickname, birth, email, name from user where id = '" + ID + "'";
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
        setting.setBackground(KAKAO_GRAY);
        String finalID = ID;
        setting.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new changeProfile(finalID);
                dispose();
            }
        });
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
        name.setBounds(65, 0, 60, 50);
        p.add(name);

        maintab.add(p);
        JLabel lblNewLabel = new JLabel("12월14일 16시 "+getPublic("SKY","public.txt")+" "+getPublic("PTY","public.txt")+" "+getPublic("TMP","public.txt"));
        lblNewLabel.setBounds(100, 0, 200, 50);
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

        int cnt_friend = db.countFriend(ID);
        DefaultMutableTreeNode Friend = new DefaultMutableTreeNode("가천대");


        String[] fid_list = db.getfriendsID(ID, cnt_friend);
        String[] fname_list = db.getfriendsName(fid_list);
        DefaultMutableTreeNode f1 = null;
        for(int i=0;i<cnt_friend;i++){
            f1 = db.makeNode(fid_list[i]);
            Friend.add(f1);
        }


        tree = new JTree(Friend);

        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem pprofile = new JMenuItem("프로필 보기");
        JMenuItem mmessage = new JMenuItem("메세지 보내기");
        popupMenu.add(pprofile);
        popupMenu.add(mmessage);
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {

                DefaultMutableTreeNode node;
                node = ( DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                if(node == null) return;

                String nodeName = (String) node.getUserObject(); //캐스팅은 꼭해주어야 한다.
                if(nodeName.equals("가천대")){

                }
                else {
                    nodeID = nodeName.substring(0, nodeName.indexOf("/"));
                    // System.out.println("선택된 노드 : " + nodeID);
                }
            }
        });
        //String finalID1 = ID;
        pprofile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // System.out.println(e.getSource());
                new profile(nodeID);

            }
        });

        tree.setPreferredSize(new Dimension(280, tree.getPreferredSize().height));
        tree.setComponentPopupMenu(popupMenu);
        initPopupListener(tree, popupMenu);
        tree.setBounds(3, 170, 280, 380);
        borderlayoutpanel.add(tree, BorderLayout.WEST);

        cp.add(maintab);

        mmessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int msg = JOptionPane.showConfirmDialog(
                        null,"채팅을 진행하실건가요?",
                        "Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE);

                //사용자가 "예"를 선택한 경우
                if(msg == JOptionPane.YES_OPTION) {
                    //채팅방 실행
                    try {
                        InetAddress ia = InetAddress.getLocalHost();
                        String ip_str = ia.toString();
                        String ip = ip_str.substring(ip_str.indexOf("/") + 1);
                        new ChatClientGui(ip,5420);
                    } catch (UnknownHostException u) {
                        u.printStackTrace();
                    }
                }
            }
        });
        String finalID1 = ID;
        addf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //DBconnection db = new DBconnection();
                Statement stmt = null;
                ResultSet rs = null;
                dispose();
                new addFriend(finalID1);
                /*try {
                    //Class의 forname()함수를 이용해 해당 클래스를 메모리로 로드.
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    //url, ID, password를 입력하여 데이터베이스에 접속.
                    con = DriverManager.getConnection(url, user, passwd);
                    stmt = con.createStatement(); //순방향으로 데이터를 읽어내려간다.
                    System.out.println("MySQL 서버 연동 성공");
                    System.out.println(con);
                } catch(Exception e1) {
                    System.out.println("MySQL 서버 연동 실패 > " + e1.toString());
                }*/
                /*String res_name = null;
                String addString = JOptionPane.showInputDialog("추가할 친구의 아이디을 입력하세요");
                String sql = "select name from user where id = '" + addString + "'";
                try {
                    rs = stmt.executeQuery(sql);
                } catch (SQLException e4) {
                   e4.printStackTrace();
                }

                while (true) {
                    try {
                        if (!rs.next()) break;
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
*//*
                    try {
                        System.out.println(rs.getString(1));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
*//*

                    try {
                        res_name = rs.getString(1);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                if(res_name != null){
                    DefaultMutableTreeNode f = new DefaultMutableTreeNode(res_name);
                    Friend.add(f);
                }*/
            }
        });

        String finalID2 = ID;
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new searchUser(finalID2);
                dispose();

            }

        });

        setVisible(true);

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
    }




    String[] userSearch(String word){
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

        String[] result=new String[100];
        String sql="select id from user where name like '%"+word+"%'";
        ResultSet rs=null;
        int i=0;
        try {
            rs=stmt.executeQuery(sql);
            while (rs.next()){
                result[i]=rs.getString(1);
                i++;
            }
        }
        catch (Exception e){e.printStackTrace();}
        if(i==0)
            result[0]=null;
        return result;
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
    String getPublic(String categori,String fileName){
        String result="";
        int resultInt=0;
        try{
            BufferedReader br= new BufferedReader(new FileReader(fileName));
            String cursor;
            while(true) {
                cursor=br.readLine();
                //System.out.println("cursor.equals(categori):"+cursor.equals(categori));
                if (cursor.equals(categori) && categori.equals("TMP")) {
                    result=br.readLine()+"\'C";

                } else if (cursor.equals(categori) && categori.equals("SKY")) {
                    resultInt=Integer.parseInt(br.readLine());
                    if(resultInt==1){
                        result="맑음";
                    }
                    else if(resultInt==3){
                        result="구름많음";
                    }
                    else {
                        result="흐림";
                    }
                } else if (cursor.equals(categori) && categori.equals("PTY")) {
                    resultInt=Integer.parseInt(br.readLine());
                    if(resultInt==0){
                        result="눈비 없음";
                    }
                    else if(resultInt==1){
                        result="비";
                    }
                    else if(resultInt==2){
                        result="눈/비";
                    }
                    else if(resultInt==3){
                        result="눈";
                    }
                    else{
                        result="소나기";
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }
}

