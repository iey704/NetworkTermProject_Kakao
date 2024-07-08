<img width="553" alt="image" src="https://github.com/iey704/NetworkTermProject_Kakao/assets/105503671/154dc5a1-eda6-4ed4-b253-5fa4a5af2253"># Kakaotalk Clone Coding

## 01. Database Used
<img width="382" alt="image" src="https://github.com/iey704/NetworkTermProject_Kakao/assets/105503671/bb8a429c-a091-4de3-9f12-64b09bb85ed0">

## 02. Register/Login
<img width="593" alt="image" src="https://github.com/iey704/NetworkTermProject_Kakao/assets/105503671/b1711c86-2c11-4468-a9b1-254587f09442">
<br/>
[Login Core Part]
![image](https://github.com/iey704/NetworkTermProject_Kakao/assets/105503671/7c2472b8-4538-4fc3-9540-94e3bb29d48f)
• DB에 정보가 저장된 경우에만 사용 가능
• 아이디 혹은 비밀번호를 입력하지 않은 경우 입력을 요구하는 창이 뜸
<br/>
[Register Core Part]
![image](https://github.com/iey704/NetworkTermProject_Kakao/assets/105503671/1dbabaa7-f773-4078-80ea-bb9056633ab6)
• DB에 같은 아이디가 존재하는지 확인하는 과정을 거침
• 개인 정보를 모두 입력하지 않은 경우 입력을 요구하는 창이 뜸
![image](https://github.com/iey704/NetworkTermProject_Kakao/assets/105503671/c2715d30-ccf8-4bd3-b9f1-18caf1f4e82d)
• DB에 정보를 저장함

## 03. User Management
<img width="558" alt="image" src="https://github.com/iey704/NetworkTermProject_Kakao/assets/105503671/fc35d0d7-8527-470b-9c3b-cfc52aa0aeb0">
• JTree 구조를 사용하여, 친구 리스트 내관계-친구 조직도 형성
• 친구 추가 아이콘을 클릭하여, 친구 아이디를 입력한 후 친구 추가 
• 검색 아이콘을 클릭하여, 아이디 검색 가능
<img width="531" alt="image" src="https://github.com/iey704/NetworkTermProject_Kakao/assets/105503671/02391957-1b59-4235-813a-eb02abefc668">

## 04. Chatting
<img width="553" alt="image" src="https://github.com/iey704/NetworkTermProject_Kakao/assets/105503671/7df47e86-0d1e-41a5-8ecc-8d76c1fe8848">
• 친구를 클릭하면 메세지를 보낼 수 있는 항목이 뜸. 해당 항목을 클릭하면 채팅을 진행할건지 팝업창이 뜸.
• 거절하면 홈페이지로 이동
<img width="533" alt="image" src="https://github.com/iey704/NetworkTermProject_Kakao/assets/105503671/96905ccb-9c97-42d7-9ac0-4bdaa1bddbb6">
• 수락하면 채팅방으로 이동

<소켓을 이용한 입출력 데이터>
서버 소켓 스레드 개체 저장합니다.
1. 소켓 연결 대기
2. 소켓 연결 허용
   입력 시 클라이언트가 호출되고 클라이언트 책임 스레드가 목록에 저장됩니다.
   대화 내용을 모두에게 전달됩니다.

## 05. File Send
![image](https://github.com/iey704/NetworkTermProject_Kakao/assets/105503671/e5e7cecb-5d4c-4972-a47f-f25626dcb7e2)
