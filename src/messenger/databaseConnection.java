package messenger;
import javax.swing.*;
import java.sql.*;
import java.util.*;
import java.text.*;
public class databaseConnection {
    Connection con=null;
    Statement stmt=null;
    String url="jdbc:mysql://localhost/messenger";
    String user = "root";
    String passwd = "1111";
    databaseConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, passwd);
            stmt = con.createStatement();
            System.out.println("MySQL 서버 연동 성공");
            System.out.println(con);
        }
        catch(Exception e){
            System.out.println("MySQL 서버 연동 실패 > ");
            e.printStackTrace();

        }

    }
    boolean newIDCheck(String userID){
        boolean check=false;
        try{
            String str="select id from user";
            ResultSet result=stmt.executeQuery(str);
            int count=0;
            while(result.next()){
                if(userID.equals(result.getString("id"))){
                    check=true;
                    break;
                    //System.out.println("아이디 중복");
                }
                else{
                    check=false;
                }
            }
        }
        catch(Exception e){
            check=false;
            e.printStackTrace();
        }
        return check;
    }
    void inputUserData(String id,String pw,String name,String nick,String email,String birth){
        PreparedStatement stmt=null;
        try{
            String str="insert into user(id ,pw,nickname,birth,email,name) values(?,?,?,?,?,?)";
            stmt=con.prepareStatement(str);

            stmt.setString(1,id);
            stmt.setString(2,pw);
            stmt.setString(3,nick);
            stmt.setString(4,birth);
            stmt.setString(5,email);
            stmt.setString(6,name);
            stmt.execute();
            stmt.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
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
    void updateLogin(String userID){
        try{
            String str="update user set count=count+1,latestLogin=NOW(),connected=true where id='"+userID+"'";
            stmt.executeUpdate(str);

        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
    String[] userSearch(String word){
        String[] result=new String[100];
        String sql="select * from user where name like '%"+word+"%'";
        ResultSet rs=null;
        int i=0;
        try {
            rs=stmt.executeQuery(sql);
            while (rs.next()){
                result[i]=rs.getString("id");
                i++;
            }
        }
        catch (Exception e){e.printStackTrace();}
        if(i==0)
            result[0]=null;
        return result;
    }
}
