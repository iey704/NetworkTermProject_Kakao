package messenger;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;

import javax.swing.*;

public class SignUp extends JFrame{
    private static String file_path = "src/chat/";
    private static String images_path = "src/graphics/";
    private CardLayout card;
    private JPanel pnlFirst;

    // 회원가입 화면의 컴포넌트
    //logo-> 휴대폰 번호 또는 이메일 주소-> 성명-> 아이디-> 비밀번호 -> 닉네임(별명) -> 생년월일 -> 가입 버튼 -> 로그인창 전환 버튼
    private JTextField Email, Name, tfUserId, NickName, Birth;
    private JPasswordField tfpassword;
    private JButton btnLogin, btnExit, SignUp;
    private JLabel OR;  //------ 또는 ------
    private JLabel lblImg; //이미지 추가용
    private JTabbedPane tab;

    //색깔
    public static final Color KAKAO_YELLOW = new Color(247,230,0);
    public static final Color KAKAO_BLUE = new Color(155,187,212);
    public static final Color KAKAO_BROWN= new Color(58,29,29);
    FrameDragListener frameDragListener;

    private DBconnection db; //DB 연결할 때 이용해야 함.

    public SignUp() {
        // LoginApp는 Frame을 상속받았다
        setLayout(card = new CardLayout());
        // 윈도우창의 상단메뉴 해제시켜 인스타그램의 테마를 주기위해  해제시킨다.
        setUndecorated(true);
        // 해제한 후에는 창의 이동이 안된다.
        // 반드시  MouseEvent로 Drag 이동을 구현해줘야함
        frameDragListener = new FrameDragListener(this);

        //DB와 연결 생성
        //내가 새로 만든 class랑 연결시키는 것.
        //이 부분에 연결을 해야 서버와 우선 연동이 되므로 같이 할 수 있다.
        db = new DBconnection();


        createComponent();
        addComponent();
        addListener();

        setSize(350, 550); //창의 사이즈를 설정.
        setVisible(true);  //가시성 부여(눈에 보이게 하기, gui는 원래 안보이게 설정이 되어 있음.)
        setResizable(false);  //창의 사이즈를 고정.

        //프로그램 실행시 화면 가운데에서 시작하도록 한다.
        setLocationRelativeTo(null);
    }
    public void createComponent() {

        // 첫번째 화면 컴포넌트 생성
        pnlFirst = new JPanel(null); // 레이아웃 해제
        pnlFirst.setBackground(KAKAO_YELLOW);

        //카카오톡 로고 부분.
        ImageIcon icon = new ImageIcon(images_path + "logo.png");
        Image image = icon.getImage();
        Image newImg = image.getScaledInstance(185,85, Image.SCALE_DEFAULT); //로고 사진 사이즈 설정.
        icon = new ImageIcon(newImg);
        lblImg = new JLabel(icon);

        //윗부분을 그대로 베겨서 이미지 경로만 변경하기.
        ImageIcon icon2 = new ImageIcon("src/graphics/icon.png");
        Image image2 = icon2.getImage();
        Image newImg2 = image2.getScaledInstance(20,20, Image.SCALE_DEFAULT);
        icon2 = new ImageIcon(newImg2);
        btnExit = new JButton(icon2);  //image로 변경해야겟당
        btnExit.setBackground(Color.gray);
        btnExit.setBounds(325, 0, 25, 25); //끝 위치 X자로 해서 닫기.



        //이메일 부분 텍스트 박스 정의.
        Email = new JTextField("휴대폰 번호 또는 이메일 주소");
        //Email.setEditable(false);
        Email.setBackground(Color.WHITE);

        //성명(이름), 아이디, 비밀번호 텍스트 박스 정의.
        Name = new JTextField("성명");
        tfUserId = new JTextField("아이디");
        tfpassword = new JPasswordField(); //비밀번호
        NickName =new JTextField("닉네임");
        Birth = new JTextField("생년월일 (ex) XXXX-XX-XX");


        //회원가입 부분 정의. 회원 가입이 되면 로그인 화면 창으로 넘어가야 함.
        SignUp = new JButton("가입");
        //회원 가입 버튼을 누를때 데이터베이스에 데이터를 추가하는 부분.

        /*SignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {  //버튼 이벤트용. 회원가입 시에 그 정보가 저장되는.
                String email = Email.getText();//메일텍스트 박스 내의 문자열을 읽어 email이라는 String 변수에 저장함.
                String name = Name.getText(); //이름텍스트 박스 내의 문자열을 읽어 name이라는 String 변수에 저장함.
                String id = tfUserId.getText(); // 아이디텍스트 박스 내의 문자열을 읽어 id 라는 String 변수에 저장.
                //비밀번호textfiled를 char로 가져오는 코드 부분.
                String pw = "";
                //tf_pw 필드에서 패스워드를 얻어옴, char[] 배열에 저장
                char[] secret_pw = tfpassword.getPassword();
                //secret_pw 배열에 저장된 암호의 자릿수 만큼 for문 돌리면서 cha 에 한 글자씩 저장
                for(char cha : secret_pw){
                    Character.toString(cha);       //cha 에 저장된 값 string으로 변환
                    //pw 에 저장하기, pw 에 값이 비어있으면 저장, 값이 있으면 이어서 저장하는 삼항연산자
                    pw += ""+cha+"";
                }
                String nickname = NickName.getText(); //닉네임텍스트 박스 내의 문자열을 읽어 nickname이라는 String 변수에 저장함.
                String birth = Birth.getText();


                *//* 회원가입해서 값을 데이터베이스에다가 짚어넣는 부분. *//*
                //또한 if문에서 개인정보를 하나라도 입력하지 않으면 입력하라는 창이 뜨도록 해야함.
                if(Email.equals("") || Name.equals("") || tfUserId.equals("")
                        || tfpassword.equals("") || NickName.equals("") || birth.equals("")){
                    JOptionPane.showMessageDialog(null,
                            "개인정보를 전부 입력해주세요.","회원가입 실패", JOptionPane.ERROR_MESSAGE);
                } else if(Email != null && Name != null && tfUserId != null
                        && tfpassword != null && NickName != null && birth != null){ //전부 입력했다면
                    if(db.SignUp_check(tfUserId.getText())){ //데이터베이스 안에 같은 아이디가 있는지 확인하는 것.
                        JOptionPane.showMessageDialog(null, "같은 아이디가 존재합니다. " +
                                "다른 아이디를 선택하여 주십시오.");
                    } else { //새로운 아이디일 경우
                       db.userlineinsert(email, name, id, pw, nickname, birth);
                       JOptionPane.showMessageDialog(null, "회원가입에 성공하였습니다");

                        new Login();
                    }
                }
            }
        });*/
        SignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {  //버튼 이벤트용. 회원가입 시에 그 정보가 저장되는.
                String email = Email.getText();//메일텍스트 박스 내의 문자열을 읽어 email이라는 String 변수에 저장함.
                String name = Name.getText(); //이름텍스트 박스 내의 문자열을 읽어 name이라는 String 변수에 저장함.
                String id = tfUserId.getText(); // 아이디텍스트 박스 내의 문자열을 읽어 id 라는 String 변수에 저장.
                //비밀번호textfiled를 char로 가져오는 코드 부분.
                String pw = "";
                //tf_pw 필드에서 패스워드를 얻어옴, char[] 배열에 저장
                char[] secret_pw = tfpassword.getPassword();
                //secret_pw 배열에 저장된 암호의 자릿수 만큼 for문 돌리면서 cha 에 한 글자씩 저장
                for(char cha : secret_pw){
                    Character.toString(cha);       //cha 에 저장된 값 string으로 변환
                    //pw 에 저장하기, pw 에 값이 비어있으면 저장, 값이 있으면 이어서 저장하는 삼항연산자
                    pw += ""+cha+"";
                }
                String nickname = NickName.getText(); //닉네임텍스트 박스 내의 문자열을 읽어 nickname이라는 String 변수에 저장함.
                String birth = Birth.getText();


                /* 회원가입해서 값을 데이터베이스에다가 짚어넣는 부분. */
                //또한 if문에서 개인정보를 하나라도 입력하지 않으면 입력하라는 창이 뜨도록 해야함.
                if(Email.equals("") || Name.equals("") || tfUserId.equals("")
                        || tfpassword.equals("") || NickName.equals("") || birth.equals("")){
                    JOptionPane.showMessageDialog(null,
                            "개인정보를 전부 입력해주세요.","회원가입 실패", JOptionPane.ERROR_MESSAGE);
                } else if(Email != null && Name != null && tfUserId != null
                        && tfpassword != null && NickName != null && birth != null){ //전부 입력했다면
                    if(db.SignUp_check(tfUserId.getText())){ //데이터베이스 안에 같은 아이디가 있는지 확인하는 것.
                        JOptionPane.showMessageDialog(null, "같은 아이디가 존재합니다. " +
                                "다른 아이디를 선택하여 주십시오.");
                    } else { //새로운 아이디일 경우
                        db.userlineinsert(email, name, id, pw, nickname, birth);
                        JOptionPane.showMessageDialog(null, "회원가입에 성공하였습니다");
                        dispose();
                        new Login();
                    }
                }
            }
        });
        SignUp.setForeground(Color.WHITE);  //글자 색깔.
        SignUp.setBorderPainted(false);  //JButton의 Border(외곽선)을 없애준다.
        SignUp.setBackground(KAKAO_BROWN);

        //로그인 관련 라벨.
        OR = new JLabel("──────────────── 또는 ──────────────────");
        OR.setForeground(Color.gray);
        OR.setOpaque(false);

        //회원가입 부분 버튼 제작.
        btnLogin = new JButton("계정이 있으신가요? 로그인");
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {  //버튼 이벤트용. 로그인을 누르면 다음화면으로 넘어갈 수 있도록.
                dispose(); // 현재 프레임만 종료시키는 코드.
                new Login(); //버튼을 누르면 로그인창으로 가게된다.

            }
        });
        btnLogin.setForeground(Color.BLACK);  //글자 색깔.
        btnLogin.setBorderPainted(false);  //JButton의 Border(외곽선)을 없애준다.
        btnLogin.setBackground(KAKAO_YELLOW);

    }

    public void addComponent() {
        // 첫번째 화면 컴포넌트 부착
        //100,130
        lblImg.setBounds(95, 15, 160, 80); //로고 이미지 위치
        pnlFirst.add(lblImg);

        //레이아웃 배치로 해서 시작점을 4,1로 한 것임.
        JPanel pnlLogin = new JPanel(new GridLayout(9,1,5,5));  //여기서 뒤의 2개 5,5는 간격이라고 보면된다.
        pnlLogin.setBackground(KAKAO_YELLOW);

        pnlLogin.add(Email);
        pnlLogin.add(Name);
        pnlLogin.add(tfUserId);
        pnlLogin.add(tfpassword);
        pnlLogin.add(NickName);
        pnlLogin.add(Birth);
        pnlLogin.add(SignUp);
        pnlLogin.add(OR);
        pnlLogin.add(btnLogin);


        pnlLogin.setSize(250, 420); //컴포넌트의 크기를 지정한다.
        pnlLogin.setLocation(50,100);  //component의 좌표, 즉 위치를 지정한다.
        pnlFirst.add(pnlLogin);
        pnlFirst.add(btnExit);

        add(pnlFirst);
    }

    public void addListener() {
        //윈도우 닫기버튼 이벤트 처리
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card.last(SignUp.this.getContentPane());
            }
        });

        //화면에 마우스 이벤트 처리를 통해 드래그 이동을 구현한다.
        addMouseListener(frameDragListener);
        addMouseMotionListener(frameDragListener);

    }

    public static class FrameDragListener extends MouseAdapter {

        private final JFrame frame;
        private Point mouseDownCompCoords = null;

        public FrameDragListener(JFrame frame) {
            this.frame = frame;
        }

        public void mouseReleased(MouseEvent e) {
            mouseDownCompCoords = null;
        }

        public void mousePressed(MouseEvent e) {
            mouseDownCompCoords = e.getPoint();
        }

        public void mouseDragged(MouseEvent e) {
            Point currCoords = e.getLocationOnScreen();
            frame.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
        }
    }

    public static void main(String[] args){
//        try{
//            //config 파일 객체 생성.
//            File file = new File(file_path+"config.dat");
//            //입력 스트림 생성.
//            FileReader filereader= new FileReader(file);
//            //입력 버퍼 생성.
//            BufferedReader bufReader = new BufferedReader(filereader);
//            String line = "";
//            int count=0;
//            String host = "", port_s =""; //이 부분은 좀 더 생각해보기.
//            while((line =bufReader.readLine()) != null){
//                if (count == 0){
//                    host = line;
//                    count++;
//                }else{
//                    port_s = line;
//                    count++;
//                }
//
//            }
//            bufReader.close();
//
//            int port = Integer.parseInt(port_s);
//            System.out.println("host 번호는: " + host);
//            System.out.println("port 번호는: " + port);
//            Socket socket = new Socket(host, port);

            new SignUp();
//        }catch (IOException e) {
//            e.printStackTrace();
//        }


    }


}
