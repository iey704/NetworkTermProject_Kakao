package messenger;

import java.awt.*;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;
import java.io.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/*
 * 서버와 클라이언트가 접속이 완료되면
 * 클라이언트가 'd:/d_other/멍멍.jpg'파일을 서버로 전송한다.
 * 서버는 클라이언트가 전송한 파일을 받아서 'd:/d_other/upload/' 폴더에
 * 같은 이름으로 저장되도록 한다.
 *
 */

public class TcpFileClient extends JFrame {
    private JButton btnOpen = new JButton("열기");
    private JButton btnSend = new JButton("전송");
    private JLabel labelFile = new JLabel(" ");
    enum DialogChoice { OPEN, SAVE }

    public void sendGui(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 550);
        setResizable(false);  //창의 사이즈를 고정.
        setVisible(true);

        this.FileSendInit();
    }
    public void FileSendInit(){
        //getContentPane().setLayout(new FlowLayout());
        add(btnOpen);
        add(btnSend);
        add(labelFile);
    }

    public int clientStart(){
        //파일을 읽어서 Socket으로 쓰기: 전송

        //File file = new File("d:/d_other/멍멍.jpg");
        File file =showDialog(DialogChoice.OPEN);

        if(file == null){
            System.out.println("전송한 파일을 선택하지 않았습니다.");
            System.out.println("작업을 중단합니다...");
            return 0;
        }

        String fileName = file.getName();
        if(!file.exists()){
            System.out.println(fileName + "파일이 없습니다.");
            System.out.println("작업을 중단 합니다...");
            return 0;
        }

        try {
            Socket socket = new Socket("localhost" , 7777);
            System.out.println("파일 전송 시작...");
            int resultCheck = -1;

            //Socket용 OutputStream 객체 생성
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            BufferedOutputStream bos = new BufferedOutputStream(dos);

            // 서버에 접속하면 첫번째로 전송할 파일의 파일명을 전송한다.
            dos.writeUTF(fileName);


            //파일 입력용 InputStream 객체 생성
            BufferedInputStream bis = new BufferedInputStream(
                    new FileInputStream(file)
            );

            byte[] temp = new byte[1024];
            int length = 0;

            // 파일 내용을 읽어와 소켓으로 전송하기
            while((length = bis.read(temp)) > 0){
                //읽어온 데이터 갯수가 0개보다 많으면 출력
                bos.write(temp, 0, length);
            }
            bos.flush();//현재 버퍼에 저장되어 있는 내용을 클라이언트로 전송하고 버퍼를 비운다.

            System.out.println("파일 전송 완료..");
            resultCheck = 1;



            //스트림과 소켓 닫기
            bis.close();
            bos.close();
            socket.close();
            return resultCheck;


        } catch (Exception e) {
            int resultCheck = 0;
            System.out.println("파일 전송 실패 : " + e.getMessage());
            return resultCheck;
        }

    }


    public File showDialog(DialogChoice choice){
        // SWING의 파일 열기, 저장 창 연습
        JFileChooser chooser = new JFileChooser();

        // 선택할 파일의 확장자 설정
        FileNameExtensionFilter doc =
                new FileNameExtensionFilter("Word File", "docx", "doc");
        FileNameExtensionFilter img =
                new FileNameExtensionFilter("Image File", new String[]{"png", "jpg", "gif"});
        FileNameExtensionFilter txt =
                new FileNameExtensionFilter("text파일(.txt)", "txt");

        chooser.addChoosableFileFilter(doc);
        chooser.addChoosableFileFilter(img);
        chooser.addChoosableFileFilter(txt);

        // 확장자 목록 중 기본적으로 선택될 확장자 지정
        chooser.setFileFilter(txt);

        // 모든 파일 목록 표시 여부 설정(true: 설정, false: 해제)
        //chooser.setAcceptAllFileFilterUsed(false);
        chooser.setAcceptAllFileFilterUsed(true);

        // Dialog창에 나타날 기본 경로 설정하기
        chooser.setCurrentDirectory(new File("d:/JAVA_project"));
        int result = -1;
        if(choice==DialogChoice.OPEN){
            // 열기 창
            result = chooser.showOpenDialog(new Panel());//부모창?
            labelFile.setText("열기 경로 : " + chooser.getSelectedFile().toString());
        }else if(choice==DialogChoice.SAVE){
            // 저장 창
            result = chooser.showSaveDialog(new Panel());
            //저장경로를 표시하는 label을 띄움.
            labelFile.setText("저장 경로 : " + chooser.getSelectedFile().toString()
                    + "." + chooser.getFileFilter().getDescription());
        }
        // '열기 창' 또는 '저장 창'에서 선택한 파일 정보 가져오기


        File selectedFile = null;
        // '열기' 또는 '저장'버튼을 눌렀을 경우에는...
        if(result == JFileChooser.APPROVE_OPTION){
            // 선택한 파일 정보 구하기
            selectedFile = chooser.getSelectedFile();
            //System.out.println("선택 파일 : " + selectedFile.getAbsolutePath());
        }
        return selectedFile;
    }

//    public static void main(String[] args) {
//        new TcpFileClient().clientStart();
//
//    }

}