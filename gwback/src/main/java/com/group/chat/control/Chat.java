package com.group.chat.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;

// ws://호스트/chat 접속시
@ServerEndpoint("/chat")
public class Chat {
	// 인라인 클래스 메시지 타입 클래스
	class ChatMessage {
		// id
		private String id;
		// state
		private int state;
		// 내용
		private String value;

		// getter, setter
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

	// 세션과 id를 가지고 있는 세션
	class ChatSession {
		// WebSocket 세션
		private Session session;
		// id
		private String id;

		// getter, setter
		public Session getSession() {
			return session;
		}

		public void setSession(Session session) {
			this.session = session;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}
	}

	// 유저 리스트
	private static List<ChatSession> users = new LinkedList<>();
	// JSON을 파싱하는 클래스
	private Gson gson = new Gson();

	// WebSocket session으로 ChatSession을 탐색하는 함수
	private ChatSession getSession(Session userSession) {
		// session 클래스와 ChatSession 클래스의 session 변수를 비교해서 탐색해 온다.
		Optional<ChatSession> data = users.stream().filter(x -> x.getSession() == userSession).findFirst();
		// 데이터가 있으면
		if (data.isPresent()) {
			// 리턴
			return data.get();
		}
		// 없으면 null
		return null;
	}

	// 세션 만들기
	private ChatSession createSession(ChatMessage msg, Session userSession) {
		// 먼저 기존 세션에 있는지 확인
		ChatSession session = getSession(userSession);
		// 세션이 없으면
		if (session == null) {
			// 인스턴스 생성
			session = new ChatSession();
			// 세션 저장
			session.setSession(userSession);
			// user 리스트에 추가
			users.add(session);
		}
		// id 저장
		session.setId(msg.getId());
		// session 리턴
		return session;
	}

	// WebSocket이 접속될 때 호출되는 함수
	@OnOpen
	public void handleOpen(Session userSession) {
	}

	// 브라우저로부터 메시지가 오면 호출되는 함수
	@OnMessage
	public void handleMessage(String message, Session userSession) {
		// 메시지가 JSON 타임으로 오는데 ChatMessage 클래스로 변환
		ChatMessage msg = gson.fromJson(message, ChatMessage.class);
		// State가 0이라면 초기 접속
		if (msg.getState() == 0) {
			// 세션 만들기
			createSession(msg, userSession);
			try {
				// 파일로부터 채팅 내용을 읽어와서 보내기
				userSession.getBasicRemote().sendText(readFile());
			} catch (Throwable e) {
				// 에러가 발생할 경우.
				e.printStackTrace();
			}
			// State가 1이라면 일반 메시지
		} else if (msg.getState() == 1) {
			// 세션 확인 하기
			if (getSession(userSession) != null) {
				// 메시지 보내기
				sendMessage(msg.getId(), msg.getValue());
				// 파일에 저장하기
				saveFile(msg.getId(), msg.getValue());
			}
		}
	}

	// 채팅 내용을 파일로 부터 읽어온다.
	private String readFile() {
		// d드라이브의 chat 폴더의 chat 파일
		File file = new File("C:\\chat\\chat.txt");
		// 파일 있는지 검사
		if (!file.exists()) {
			return "";
		}
		// 파일을 읽어온다.
		try (FileInputStream stream = new FileInputStream(file)) {
			byte[] byteArr = IOUtils.toByteArray(stream);
			return new String(byteArr);
		} catch (Throwable e) {
			e.printStackTrace();
			return "";
		}
	}

	// 파일를 저장하는 함수
	private void saveFile(String id, String message) {
		// 메시지 내용
		String msg = id + "] " + message + "\n";
		// 파일을 저장한다.
		try (FileOutputStream stream = new FileOutputStream("C:\\chat\\chat.txt", true)) {
			stream.write(msg.getBytes("UTF-8"));
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	// 메시지 보내기는 함수
	private void sendMessage(String id, String message) {
		// 메시지 내용
		String sendMessage = id + "] " + message + "\n";
		for (ChatSession user : users) {
			try {
				// 메시지 전송
				user.getSession().getBasicRemote().sendText(sendMessage);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

	// WebSocket이 닫기면 호출되는 함수
	@OnClose
	public void handleClose(Session userSession) {
		// session으로 users에서 찾는다.
		Optional<ChatSession> session = users.stream().filter(x -> x.getSession() == userSession).findFirst();
		// 있으면 삭제
		if (session.isPresent()) {
			users.remove(session.get());
		}
	}
}
