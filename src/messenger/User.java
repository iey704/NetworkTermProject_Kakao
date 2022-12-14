package messenger;

import java.util.Date;

public class User {
    int mycount=0,myport,myconnected=0;
    String myid,mypw,myname,mynicname,mybirth,myemail,myip,myprofileMessage;
    Date mylatestLogin,mylatestLogout;
    String[] myfriends=new String[100];
    public User(){}
}
