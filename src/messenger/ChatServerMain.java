package messenger;

public class ChatServerMain {

	public static void main(String[] args) {
		ChatServer server = new ChatServer();
		server.giveAndTake();

		//파일 전송 관련 서버 실행.
		TcpFileServer fileserver = new TcpFileServer();
		fileserver.serverStart();
	}
}
