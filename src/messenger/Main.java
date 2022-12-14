package messenger;

import com.mysql.cj.util.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Main {
    String date="20221214";
    public static void main(String[] args){
        //검색 기능 방식
       /* DBconnection db=new DBconnection();
        String[] userID=new String[100];
        userID=db.userSearch("다");
        for(int i = 0; !StringUtils.isNullOrEmpty(userID[i]); i++) {
            System.out.println(i+1+"."+userID[i]);
        }*/
        publicUrl pubURL=new publicUrl();
        pubURL.lookUpWeather();
        new Login();

        ChatServerMain chat_server = new ChatServerMain();
        chat_server.main(args);

        //System.out.println(db.countFriend("아이디"));
       // new databaseConnection();
    }
   /*public static void main(String[] args) {

       //JSON 데이터
       String jsonInfo = "{\"books\":[{\"genre\":\"소설\",\"price\":\"100\",\"name\":\"사람은 무엇으로 사는가?\",\"writer\":\"톨스토이\",\"publisher\":\"톨스토이 출판사\"},{\"genre\":\"소설\",\"price\":\"300\",\"name\":\"홍길동전\",\"writer\":\"허균\",\"publisher\":\"허균 출판사\"},{\"genre\":\"소설\",\"price\":\"900\",\"name\":\"레미제라블\",\"writer\":\"빅토르 위고\",\"publisher\":\"빅토르 위고 출판사\"}],\"persons\":[{\"nickname\":\"남궁민수\",\"age\":\"25\",\"name\":\"송강호\",\"gender\":\"남자\"},{\"nickname\":\"예니콜\",\"age\":\"21\",\"name\":\"전지현\",\"gender\":\"여자\"}]}";


        *//*{
            "books": [
                {
                    "genre": "소설",
                    "price": "100",
                    "name": "사람은 무엇으로 사는가?",
                    "writer": "톨스토이",
                    "publisher": "톨스토이 출판사"
                },
                {
                    "genre": "소설",
                    "price": "300",
                    "name": "홍길동전",
                    "writer": "허균",
                    "publisher": "허균 출판사"
                },
                {
                    "genre": "소설",
                    "price": "900",
                    "name": "레미제라블",
                    "writer": "빅토르 위고",
                    "publisher": "빅토르 위고 출판사"
                }
            ],
            "persons": [
                {
                    "nickname": "남궁민수",
                    "age": "25",
                    "name": "송강호",
                    "gender": "남자"
                },
                {
                    "nickname": "예니콜",
                    "age": "21",
                    "name": "전지현",
                    "gender": "여자"
                }
            ]
        }*//*


       try {

           JSONParser jsonParser = new JSONParser();

           //JSON데이터를 넣어 JSON Object 로 만들어 준다.
           JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonInfo);

           //books의 배열을 추출
           JSONArray bookInfoArray = (JSONArray) jsonObject.get("books");

           System.out.println("* BOOKS *");

           for(int i=0; i<bookInfoArray.size(); i++){

               System.out.println("=BOOK_"+i+" ===========================================");

               //배열 안에 있는것도 JSON형식 이기 때문에 JSON Object 로 추출
               JSONObject bookObject = (JSONObject) bookInfoArray.get(i);

               //JSON name으로 추출
               System.out.println("bookInfo: name==>"+bookObject.get("name"));
               System.out.println("bookInfo: writer==>"+bookObject.get("writer"));
               System.out.println("bookInfo: price==>"+bookObject.get("price"));
               System.out.println("bookInfo: genre==>"+bookObject.get("genre"));
               System.out.println("bookInfo: publisher==>"+bookObject.get("publisher"));

           }

           JSONArray personInfoArray = (JSONArray) jsonObject.get("persons");

           System.out.println("\r\n* PERSONS *");

           for(int i=0; i<personInfoArray.size(); i++){

               System.out.println("=PERSON_"+i+" ===========================================");
               JSONObject personObject = (JSONObject) personInfoArray.get(i);
               System.out.println("personInfo: name==>"+personObject.get("name"));
               System.out.println("personInfo: age==>"+personObject.get("age"));
               System.out.println("personInfo: gender==>"+personObject.get("gender"));
               System.out.println("personInfo: nickname==>"+personObject.get("nickname"));

           }

       } catch (Exception e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }

   }*/

}
