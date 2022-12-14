package messenger;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.*;

public class Login extends JFrame{
    private static String images_path = "src/graphics/";
    private CardLayout card;
    private JPanel pnlFirst;

    // 첫번째 화면의 컴포넌트
    private JTextField tfUserId;
    private JPasswordField tfpassword;
    private JButton btnLogin, btnExit, Facebook, PWFind, SignUp;
    //private JCheckBox chAutologin;
    private JLabel OR;  //------ 또는 ------
    private JLabel lblImg, lblInfo; //이미지 추가용
    private JTabbedPane tab;


    //색깔
    public static final Color KAKAO_YELLOW = new Color(247,230,0);
    public static final Color KAKAO_BLUE = new Color(155,187,212);
    public static final Color KAKAO_BROWN= new Color(58,29,29);
    FrameDragListener frameDragListener;

    private DBconnection db; //DB 연결할 때 이용해야 함.
    String ip=null;
    int port=0;
    public Login() {
        // LoginApp는 Frame을 상속받았다
        setLayout(card = new CardLayout());
        // 윈도우창의 상단메뉴 해제시켜 인스타그램의 테마를 주기위해  해제시킨다.
        setUndecorated(true);
        // 해제한 후에는 창의 이동이 안된다.
        // 반드시  MouseEvent로 Drag 이동을 구현해줘야함
        frameDragListener = new FrameDragListener(this);

        //DB와 연결 생성
        //내가 새로 만든 class랑 연결시키는 것.
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

        ImageIcon icon = new ImageIcon(images_path+ "logo.png");

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

        //아이디, 비밀번호 텍스트 박스 정의.
        tfUserId = new JTextField("전화번호, 사용자 이름 또는 이메일");
        tfpassword = new JPasswordField();
        //tfpassword.setText("비밀번호");

        //로그인부분 정의. 로그인이 되면 이제 프로필 창으로 넘어가야 함.
        btnLogin = new JButton("로그인");
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {  //버튼 이벤트용. 로그인을 누르면 다음화면으로 넘어갈 수 있도록.
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

                //다만, if문을 사용해서 데이터베이스에 들어가 있는 코드일 경우에만 사용이 가능하게 해야함.
                //또한 if문에서 아이디 혹은 비밀번호를 하나라도 입력하지 않으면 입력하라는 창이 뜨도록 해야함.
                if(tfUserId.equals("") || tfpassword.equals("")){ //만약 아이디 혹은 비밀번호를 입력하지 않았다면
                    JOptionPane.showMessageDialog(null,
                            "아이디와 비밀번호 모두 입력해주세요","로그인 실패", JOptionPane.ERROR_MESSAGE);
                } else if(tfUserId != null && tfpassword != null){
                    //데이터베이스 확인하는 부분.
                    if(db.logincheck(tfUserId.getText(), pw)) {	//이 부분이 데이터베이스에 접속해 로그인 정보를 확인하는 부분이다.
                        System.out.println("로그인 성공");
                        JOptionPane.showMessageDialog(null, "로그인에 성공하였습니다");
                        //이 부분에 이제 main페이지로 넘어갈 수 있도록 하기.
                        //new ScrollSwing1(tfUserId.getText());
                        dispose();
                        BufferedReader br=null;
                        try {
                            br=new BufferedReader(new FileReader("ServerIP.dat"));
                            port=Integer.parseInt(br.readLine());
                            ip=br.readLine();


                            //new ClientGui_h(ip, port);
                            //new home(tfUserId.getText());
                            new home(tfUserId.getText());
                            db.updateLogin(tfUserId.getText());
                        }
                        catch (Exception ee){
                            ee.printStackTrace();
                        }
                    } else {  //로그인 정보 실패 시에 뜨는 부분.
                        JOptionPane.showMessageDialog(null,
                                "로그인 정보 불일치","로그인 실패", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        btnLogin.setForeground(Color.WHITE);  //글자 색깔.
        btnLogin.setBorderPainted(false);  //JButton의 Border(외곽선)을 없애준다.
        btnLogin.setBackground(KAKAO_BROWN);


        OR = new JLabel("──────────────── 또는 ──────────────────");
        OR.setForeground(Color.gray);
        OR.setOpaque(false);

        //chAutologin = new JCheckBox("잠금모드로 자동로그인");
        //chAutologin.setOpaque(false); //이 setOpaque가 중요한데, 배경색을 false로 적용해야 투명이 된다.

        //facebook으로 로그인 버튼 정의.
        Facebook = new JButton("QR코드 로그인");
        //Facebook.setForeground(Instargram_Blue);  //글자 색깔.
        Facebook.setBorderPainted(false);  //JButton의 Border(외곽선)을 없애준다.
        Facebook.setBackground(Color.WHITE);

        //비밀 번호를 잊었을 때.
        PWFind = new JButton("비밀번호를 변경하실건가요?");
        //PWFind.setForeground(Instargram_Blue2);  //글자 색깔.
        PWFind.setBorderPainted(false);  //JButton의 Border(외곽선)을 없애준다.
        PWFind.setBackground(KAKAO_YELLOW);
        //비밀번호를 변경하는 PWchange 코드로 넘어간다.
        PWFind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                //new PWchange();
            }
        });

        //회원가입 부분 버튼 제작.
        SignUp = new JButton("계정이 없으신가요? 가입하기");
        SignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {  //버튼 이벤트용. 가입하기을 누르면 가입화면으로 넘어갈 수 있도록.
                dispose(); // 현재 프레임만 종료시키는 코드.
                new SignUp(); //버튼을 누르면 프로필창으로 가게된다.

            }
        });
        SignUp.setBackground(KAKAO_YELLOW);
        SignUp.setBorderPainted(false);  //JButton의 Border(외곽선)을 없애준다.

    }

    public void addComponent() {
        // 첫번째 화면 컴포넌트 부착
        //100,130
        lblImg.setBounds(45, 65, 260, 120);  //로고 이미지 위치
        pnlFirst.add(lblImg);

        //레이아웃 배치로 해서 시작점을 4,1로 한 것임.
        JPanel pnlLogin = new JPanel(new GridLayout(7,1,5,5));  //여기서 뒤의 2개 5,5는 간격이라고 보면된다.
        pnlLogin.setBackground(KAKAO_YELLOW);
        pnlLogin.add(tfUserId);
        pnlLogin.add(tfpassword);
        pnlLogin.add(btnLogin);
        //pnlLogin.add(new JPanel().add(chAutologin));
        pnlLogin.add(OR);
        pnlLogin.add(Facebook);
        pnlLogin.add(PWFind);
        pnlLogin.add(SignUp);
        pnlLogin.setSize(250, 300); //컴포넌트의 크기를 지정한다.
        pnlLogin.setLocation(50,200);  //component의 좌표, 즉 위치를 지정한다.
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
                card.last(Login.this.getContentPane());
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
   // String

}
