package messenger;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;


public class DBconnection {

    Connection con = null;
    Statement stmt = null;
    String url = "jdbc:mysql://localhost/messenger";	//user 스키마
    String user = "root";
    String passwd = "1111";		//본인이 설정한 root 계정의 비밀번호를 입력하면 된다.
    DBconnection db;
    DBconnection(){  //데이터베이스에 연결한다. 서버와의 연결
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
    }

    /* 회원 정보를 확인 */
    boolean SignUp_check(String _i) {
        boolean flag = false;

        String id = _i;

        try {

            String checkingStr = "SELECT ID FROM user";
            //mysql에다 위의 문장을 보내서 정보를 query 해오는 것.
            //resultset은 결과갑을 저장할 수 있음.
            ResultSet result = stmt.executeQuery(checkingStr);

            int count = 0;
            while(result.next()) {  //next는 개행문자를 무시하고 입력을 받는다. 즉 result의 값을 읽어온다.
                if(id.equals(result.getString("ID"))) {  //같은 아이디가 존재하는지 확인
                    flag = true;
                    System.out.println("중복된 아이디가 존재합니다.");
                    JOptionPane.showMessageDialog(null, "같은 아이디가 존재합니다. " +
                            "다른 아이디를 선택하여 주십시오.");
                    break;
                }
                else {
                    flag = false;
                    System.out.println("회원가입 성공");

                }
                count++;
            }
        } catch(Exception e) {
            flag = false;
            System.out.println("로그인 실패 > " + e.toString());
        }

        return flag;
    }

    /*회원정보를 업로드 하는 부분.*/
    void userlineinsert(String Email, String name, String id, String pw,  String nickname, String birth){
        PreparedStatement pstmt = null;
        //boolean result = false;
        try{
            String insertStr = "insert into user(Email, name, ID, PW, nickname, birth) values(?,?,?,?,?,?)";
            pstmt = con.prepareStatement(insertStr);   //sql구문을 전달해줌.

            pstmt.setString(1, Email);
            pstmt.setString(2, name);
            pstmt.setString(3, id);
            pstmt.setString(4, pw);
            pstmt.setString(5, nickname);
            pstmt.setString(6, birth);

            pstmt.execute();

            pstmt.close();

        }catch (SQLException e){
            System.out.println("유저의 정보를 데이터베이스 업로드에 실패했습니다.");
        }

    }

    /* 로그인 정보를 확인 */
    boolean logincheck(String _i, String _p) {
        boolean flag = false;

        String id = _i;
        String pw = _p;

        try {
            String checkingStr = "SELECT PW FROM user WHERE ID='" + id + "'";
            //mysql에다 위의 문장을 보내서 정보를 query 해오는 것.
            ResultSet result = stmt.executeQuery(checkingStr);

            int count = 0;
            while(result.next()) {  //next는 개행문자를 무시하고 입력을 받는다.
                if(pw.equals(result.getString("PW"))) {
                    flag = true;
                    System.out.println("로그인 성공");
                }

                else {
                    flag = false;
                    System.out.println("로그인 실패");
                }
                count++;
            }
        } catch(Exception e) {
            flag = false;
            System.out.println("로그인 실패 > " + e.toString());
        }

        return flag;
    }
    String[] userSearch(String word){
        String[] result=new String[100];
        String sql="select id from user where id like '%"+word+"%'";
        ResultSet rs=null;
        int i=0;
        try {
            rs=stmt.executeQuery(sql);
            while (rs.next()){
                result[i]=rs.getString("id");
               // System.out.println("result["+i+"]:"+result[i]);
                i++;
            }
        }
        catch (Exception e){e.printStackTrace();}
        if(i==0)
            result[0]="검색 결과가 없습니다.";
        return result;
    }
    void updateLogin(String userID){
        try{
            String str="update user set count=count+1,latestLogin=NOW(),connected=true where id='"+userID+"'";
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
    void changePW(String userID,String newPW){
        try{
            String sql="update user set pw='"+newPW+"' where id='"+userID+"'";
            int result=stmt.executeUpdate(sql);
            if(result > 0) {
                System.out.println("변경 성공");
            }else {
                System.out.println("변경 실패");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    void changeName(String userID,String newName){
        try{
            String sql="update user set name='"+newName+"' where id='"+userID+"'";
            int result=stmt.executeUpdate(sql);
            if(result > 0) {
                System.out.println("변경 성공");
            }else {
                System.out.println("변경 실패");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    void changeProfileMessage(String userID,String newProfileMessage){
        try{
            String sql="update user set profileMessage='"+newProfileMessage+"' where id='"+userID+"'";
            int result=stmt.executeUpdate(sql);
            if(result > 0) {
                System.out.println("변경 성공");
            }else {
                System.out.println("변경 실패");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    void changeNickname(String userID,String newNickname){
        try{
            String sql="update user set nickname='"+newNickname+"' where id='"+userID+"'";
            int result=stmt.executeUpdate(sql);
            if(result > 0) {
                System.out.println("변경 성공");
            }else {
                System.out.println("변경 실패");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    JTree addFriend(String userID, DefaultMutableTreeNode Friend) {
        String res_name = null;
        String addString = JOptionPane.showInputDialog("추가할 친구의 아이디를 입력하세요");
        String sql = "select name from user where id = '" + addString + "'";
        DefaultMutableTreeNode f;

        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                res_name = rs.getString("name");
            }
        } catch (SQLException e4) {
            throw new RuntimeException(e4);
        }

        JTree tree = null;
        if (res_name != null) {
            friendinsert(userID, addString);
            f = new DefaultMutableTreeNode(res_name);
            Friend.add(f);
            tree = new JTree(Friend);

            JPopupMenu popupMenu = new JPopupMenu();
            JMenuItem pprofile = new JMenuItem("프로필 보기");
            JMenuItem mmessage = new JMenuItem("메세지 보내기");
            popupMenu.add(pprofile);
            popupMenu.add(mmessage);

            pprofile.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    profile view = new profile(f.getUserObject().toString());
                }
            });

            tree.setPreferredSize(new Dimension(280, tree.getPreferredSize().height));
            tree.setComponentPopupMenu(popupMenu);
            tree.setBounds(3, 170, 280, 380);
        } else {
            //alert 창 띄우기
        }

        return tree;
    }

    int countFriend(String userID){
        int count=0;

        try{
            String sql="select count(friendsID) from friends where userid='"+userID+"'";
            ResultSet rs=stmt.executeQuery(sql);
            rs.next();
            count=rs.getInt("count(friendsID)");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return count;
    }

    String[] getfriendsID(String userID, int size){

        String fid[] = new String[size];
        int i=0;
        try{
            String sql="select friendsID from friends where userID = '"+userID+"'";
            ResultSet rs=stmt.executeQuery(sql);
            while (rs.next()){
                fid[i]=rs.getString("friendsID");
                i++;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return fid;
    }

    String[] getfriendsName(String[] flist){

        String fname[] = new String[flist.length];
        try {
            for (int i = 0; i < flist.length; i++) {
                String sql = "select name from user where id ='" + flist[i] + "'";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    fname[i] = rs.getString("name");
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return fname;
    }
    void friendinsert(String userID, String friendsID){
        //PreparedStatement pstmt = null;
        //boolean result = false;
        try{
            if(ifFriend(userID,friendsID)||!existID(userID)){
                JOptionPane.showMessageDialog(null,
                        "아이디가 존재하지않거나 이미 친구입니다.","친구추가 실패", JOptionPane.ERROR_MESSAGE);
            }
                else{
                String insertStr = "insert into friends(userID, friendsID) values('" + userID + "','" + friendsID + "')";
                int result1 = stmt.executeUpdate(insertStr);//sql구문을 전달해줌.
                String insertStr2 = "insert into friends(userID, friendsID) values('" + friendsID + "','" + userID + "')";
                int result2 = stmt.executeUpdate(insertStr2);//sql구문을 전달해줌.
                if (result1 > 0) {
                    System.out.println("입력 성공");
                } else {
                    System.out.println("입력 실패");
                }
                if (result2 > 0) {
                    System.out.println("입력 성공");
                } else {
                    System.out.println("입력 실패");
                }
            }
        }catch (SQLException e){
            System.out.println("유저의 정보를 데이터베이스 업로드에 실패했습니다.");
        }

    }
    boolean ifFriend(String userID,String friendsID){
        boolean result=false;
        String sql="select friendsID from Friends where userid='"+userID+"'";
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                if(rs.getString("friendsID").equals(friendsID)){
                    result=true;
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return  result;
    }
    boolean existID(String userID){
        boolean result=false;
        try{
            String sql="select id from user";
            ResultSet rs=stmt.executeQuery(sql);
            while(rs.next()){
                if(rs.getString("id").equals(userID)){
                    result=true;
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    String getUserName(String userID){
        String name="";
        try{
            String sql="select name from user where id='"+userID+"'";
            ResultSet rs=stmt.executeQuery(sql);
           while(rs.next()) {
               name = rs.getString("name");
           }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return name;
    }
    DefaultMutableTreeNode makeNode(String userID){
        String myID=userID;
        db= new DBconnection();
        String nodeName=db.getUserName(userID);
        DefaultMutableTreeNode newNODE=new DefaultMutableTreeNode(userID+"/"+nodeName);

        return newNODE;
    }
    String getNicname(String userID){
        String result="";
        try{
            String sql="select nickname from user where id='"+userID+"'";
            ResultSet rs=stmt.executeQuery(sql);
            while  (rs.next()){
                result=rs.getString("nickname");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    String getNickname(String userID){
        String result="";
        try{
            String sql="select nickname from user where id='"+userID+"'";
            ResultSet rs=stmt.executeQuery(sql);
            while  (rs.next()){
                result=rs.getString("nickname");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    String getUserConnected(String userID){
        String result="";
        try{
            String sql="selcet connected from user where id='"+userID+"'";
            ResultSet rs=stmt.executeQuery(sql);
            while  (rs.next()) {
                result = rs.getString("connected");
            }
            if(result.equals("1")) {
                result="접속중";
            }
            else{
                result="접속중 아님";
            }
        }
        catch (Exception e){e.printStackTrace();}
        return result;
    }
}
