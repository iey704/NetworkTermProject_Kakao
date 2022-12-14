package messenger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

//채팅방에서의 server 구현 
public class ChatServer {
	ServerSocket serverSocket;
	Socket socket;
	// ServerSocketThread 객체 저장
	// list 를 관리
	List<Thread> list;		
	
	public ChatServer() {
		list = new ArrayList<Thread>();
		System.out.println("서버가 시작되었습니다.");
	}
	public void giveAndTake() {
		try {
			// 소켓 접속 대기
			serverSocket = new ServerSocket(5420);		
			// ServerSocket이 port를 바로 다시 사용한다 설정(port를 잡고있음)
			serverSocket.setReuseAddress(true); 		
			
			while(true) {
				// accept -> 1. 소켓 접속 대기 2. 소켓 접속 허락
				socket = serverSocket.accept();			
				// this -> ChatServer 자신
				ChatServerSocketThread thread = new ChatServerSocketThread(this, socket);	
				// 리스트에 쓰레드 객체 저장
				addClient(thread);		
				thread.start();
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	// synchronized : 쓰레드들이 공유데이터를 함께 사용하지 못하도록 하는 것
	// 클라이언트가 입장 시 호출되며, 리스트에 클라이언트 담당 쓰레드 저장
	private synchronized void addClient(ChatServerSocketThread thread) {
		// 리스트에 ServerSocketThread 객체 저장
		list.add(thread);
		System.out.println("Client 1명 입장. 총 " + list.size() + "명");
	}		
	// 클라이언트가 퇴장 시 호출되며, 리스트에 클라이언트 담당 쓰레드 제거
	public synchronized void removeClient(Thread thread) {
		list.remove(thread);
		System.out.println("Client 1명 퇴장. 총 " + list.size() + "명");
	}
	// 모든 클라이언트에게 채팅 내용 전달
	public synchronized void broadCasting(String str) {
		for(int i = 0; i < list.size(); i++) {
			ChatServerSocketThread thread = (ChatServerSocketThread)list.get(i);
			thread.sendMessage(str);
		}
	}
}