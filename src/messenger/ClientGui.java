package messenger;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;


public class ClientGui extends JFrame implements ActionListener, Runnable {

	public static final Color KAKAO_YELLOW = new Color(247,230,0);
	public static final Color KAKAO_BLUE = new Color(155,187,212);
	public static final Color KAKAO_BROWN= new Color(58,29,29);

	private static String graphics_path = "src/graphics/";

	private static final long serialVersionUID = 1L;
	private JTextArea textArea;
	private JTextField textField;
	private static String nickName;
	private JPanel contentPane, header;
	private JButton btnInput, btnBack, btnMenu, btnsend;
	private JLabel text;



	// 통신용 변수들.
	Socket socket;
	PrintWriter out;
	BufferedReader in;
	String str; 		// 채팅 문자열 저장

	public ClientGui(String ip, int port) {

		createComponent();
		setTitle("Chatting Program_client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(350, 550);
		setResizable(false);  //창의 사이즈를 고정.
		//프로그램 실행시 화면 가운데에서 시작하도록 한다.
		setLocationRelativeTo(null);
		setVisible(true);
		start();



		// 통신 초기화
		initNet(ip, port);
		System.out.println("ip = " + ip);

	}
	// 통신 초기화 코드 부분.
	private void initNet(String ip, int port) {
		try {
			// 서버에 접속 시도
			socket = new Socket(ip, port);
			// 통신용 input, output 클래스 설정
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			// ture : auto flush 설정
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
		} catch (UnknownHostException e) {
			System.out.println("IP 주소가 다릅니다.");
			//e.printStackTrace();
		} catch (IOException e) {
			System.out.println("접속 실패");
			//e.printStackTrace();
		}
		// 쓰레드 구동
		Thread thread = new Thread(this); // run 함수 -> this
		thread.start();
	}

	private void start() {
		textField.addActionListener(this);
	}


	//gui 배치 코드.
	public void createComponent(){

		contentPane = new JPanel();
		contentPane.setBackground(KAKAO_BLUE);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//메뉴바 보이는 헤더바인 패널을 구현.
		header = new JPanel();
		header.setBounds(0, 0, 350, 40);
		header.setBackground(KAKAO_BLUE);
		contentPane.add(header);

		//메뉴바 부분에서 뒤로 가는 부분.
		ImageIcon icon1 = new ImageIcon(graphics_path + "back.png");
		Image image1 = icon1.getImage();
		Image newImg1 = image1.getScaledInstance(20,15, Image.SCALE_DEFAULT);
		icon1 = new ImageIcon(newImg1);
		btnBack = new JButton(icon1);
		//btnBack.setBackground(Color.WHITE);
		btnBack.setBorderPainted(false);
		btnBack.setBounds(8, 8, 20, 20);
		contentPane.add(btnBack);
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//뒤로 가게 될 경우 친구 관리 창으로 넘어갈 수 있도록 코드 추가.
				dispose();
				//new home(ID);
			}
		});

		//메뉴 헤더바에 클라이언트 관련 글자 추가.
		JLabel label = new JLabel("클라이언트");
		label.setBounds(14,8,40,40);
		contentPane.add(label);

		//메뉴바 버튼 구현.
		//초대하는 버튼임.
		//왜 이것만 한 3초 늦게 뜨는지 알 수 없음.
		ImageIcon icon2 = new ImageIcon(graphics_path +"menu.png");
		Image image2 = icon2.getImage();
		Image newImg2 = image2.getScaledInstance(40,40, Image.SCALE_DEFAULT);
		icon2 = new ImageIcon(newImg2);
		btnMenu = new JButton(icon2);
		btnMenu.setBackground(KAKAO_BLUE);
		btnMenu.setBorderPainted(false);
		btnMenu.setBounds(285, 5, 40, 40);
		contentPane.add(btnMenu);

		//채팅 화면 TextArea 구현
		textArea = new JTextArea(40, 25);
		textArea = new JTextArea(){
			@Override
			public Dimension getPreferredSize(){
				//가로 세로 넣이의 사각형의 위치를 지정.
				return new Dimension(new Dimension(320, 420));
			}
		};
		textArea.setEditable(false);
		textArea.setBackground(KAKAO_BLUE);
		textArea.setBounds(0, 8+24, 340, 430);
		contentPane.add(textArea);



		//채팅 textfield 구현
		textField = new JTextField();
		textField.setBounds(40, 465, 238, 50);
		textField.setColumns(10);
		contentPane.add(textField);


		//채팅 파일 전송버튼 부분 구현.
		ImageIcon icon3 = new ImageIcon(graphics_path +"send.png");
		Image image3 = icon3.getImage();
		Image newImg3 = image3.getScaledInstance(40,50, Image.SCALE_DEFAULT);
		icon3 = new ImageIcon(newImg3);
		btnsend = new JButton(icon3);
		btnsend.setBorderPainted(false);
		btnsend.setBounds(0, 467, 40, 50);
		btnsend.setBackground(Color.WHITE);
		contentPane.add(btnsend);
		btnsend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//이 부분 파일 전송하는 코드 추가.

//                //서버 실행.
//                TcpFileServer fileserver = new TcpFileServer();
//                fileserver.serverStart();
				//클라이언트 실행
				TcpFileClient file = new TcpFileClient();
				file.clientStart();


				//파일 전송관련 if문.
				if(file.clientStart()== 1){
					text = new JLabel("파일 전송이 성공하였습니다. " +
							"d:/JAVA_project에 업로드 되었습니다. 확인하여 주시기 바랍니다.");
					textArea.add(text);

				}else if(file.clientStart()== 0){
					text = new JLabel("파일 전송이 실패하였습니다. 다시 한번 시도해주시길 바랍니다.");
					textArea.add(text);

				}
			}

		});


		//채팅 버튼 부분 구현.
		//enter만으로도 올라가는 것이 아닌 버튼을 누를 경우에도 글에 올라갈 수 있도록.
		btnInput = new JButton("전송");
		btnInput.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				//원래 코드.
				str = textField.getText();
				out.println(str);
				// textField 초기화
				textField.setText("");
			}
		});
		btnInput.setFocusPainted(false);
		btnInput.setForeground(Color.WHITE);
		btnInput.setBackground(KAKAO_YELLOW);
		btnInput.setBounds(275+4, 465, 60, 50);
		contentPane.add(btnInput);


	}



	// 응답 대기
	// -> 서버로부터 응답으로 전달된 문자열을 읽어서, textArea에 출력하기
	@Override
	public void run() {
		while(true) {
			try {
				str = in.readLine();
				textArea.append(str + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// textField의 문자열을 읽어와서 서버로 전송함
		str = textField.getText();
		out.println(str);
		// textField 초기화
		textField.setText("");
	}





}
