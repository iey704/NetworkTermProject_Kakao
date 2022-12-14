package messenger;
/* Java 1.8 샘플 코드 */

//import android.util.Log;

import org.json.simple.JSONObject;

import java.io.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;

public class publicUrl {
    public String jsonString=null;
  /* public publicUrl(){
       try {
           StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst"); *//*URL*//*
           urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=nqHsGXHz4hJ07cb%2B6w5PzAkFr4PDIomIzHhkW4kpYvlgW03oVaCqH63g6Qxr7v3ulRM8PUCLKnFvRy0iZmJLXw%3D%3D"); *//*Service Key*//*
           urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); *//*페이지번호*//*
           urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10000", "UTF-8")); *//*한 페이지 결과 수*//*
           urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode("XML", "UTF-8")); *//*요청자료형식(XML/JSON) Default: XML*//*
           urlBuilder.append("&" + URLEncoder.encode("base_date", "UTF-8") + "=" + URLEncoder.encode("20221212", "UTF-8")); *//*‘21년 6월 28일 발표*//*
           urlBuilder.append("&" + URLEncoder.encode("base_time", "UTF-8") + "=" + URLEncoder.encode("0600", "UTF-8")); *//*06시 발표(정시단위) *//*
           urlBuilder.append("&" + URLEncoder.encode("nx", "UTF-8") + "=" + URLEncoder.encode("55", "UTF-8")); *//*예보지점의 X 좌표값*//*
           urlBuilder.append("&" + URLEncoder.encode("ny", "UTF-8") + "=" + URLEncoder.encode("127", "UTF-8")); *//*예보지점의 Y 좌표값*//*
           URL url = new URL(urlBuilder.toString());
           HttpURLConnection conn = (HttpURLConnection) url.openConnection();
           conn.setRequestMethod("GET");
           conn.setRequestProperty("Content-type", "application/json");
           System.out.println("Response code: " + conn.getResponseCode());
           BufferedReader rd;
           if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
               rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
           } else {
               rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
           }
           StringBuilder sb = new StringBuilder();
           String line;

           while ((line = rd.readLine()) != null) {
               sb.append(line);
           }
           rd.close();
           conn.disconnect();
           System.out.println(sb.toString());
           jsonString=sb.toString();

       }
       catch (Exception e){
           e.printStackTrace();
       }
      *//* JSONParser jsonParser = new JSONParser();
       try{
           Object result=jsonParser.parse(jsonString);
           if (result instanceof JSONObject) {
               JSONObject jsonObject = (JSONObject)result;
               System.out.println("jsonObiect:"+jsonObject);
               // .. JSON 객체 처리
           }
           else if (result instanceof JSONArray) {
               JSONArray jsonArray = (JSONArray)result;
               System.out.println("jsonArray:"+jsonArray);
       }

    } catch (ParseException e) {
           throw new RuntimeException(e);
       }*//*

   }*/
  File file = new File("public.txt");


    private String nx="55";
    private String ny="127";
    private String baseDate="20221214";
    private String baseTime="0500";
    private String type="json";
    public void lookUpWeather() {
        try {
            FileWriter fw = new FileWriter(file);
            String apiUrl = "nqHsGXHz4hJ07cb%2B6w5PzAkFr4PDIomIzHhkW4kpYvlgW03oVaCqH63g6Qxr7v3ulRM8PUCLKnFvRy0iZmJLXw%3D%3D";
            String serviceKey = "nqHsGXHz4hJ07cb%2B6w5PzAkFr4PDIomIzHhkW4kpYvlgW03oVaCqH63g6Qxr7v3ulRM8PUCLKnFvRy0iZmJLXw%3D%3D";
            StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst");
            urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey);
            urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("base_date", "UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("base_time", "UTF-8") + "=" + URLEncoder.encode(baseTime, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("nx", "UTF-8") + "=" + URLEncoder.encode(nx, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("ny", "UTF-8") + "=" + URLEncoder.encode(ny, "UTF-8"));
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            System.out.println("Response code: " + conn.getResponseCode());
            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }

            rd.close();
            conn.disconnect();
            String result = sb.toString();

            JSONParser jsonParser= new JSONParser();
            JSONObject jObject = (JSONObject)jsonParser.parse(result);
            JSONObject parse_response=(JSONObject)  jObject.get("response");
            JSONObject parse_body = (JSONObject) parse_response.get("body");
            JSONObject parse_items = (JSONObject) parse_body.get("items");
            JSONArray parse_item = (JSONArray) parse_items.get("item");
            System.out.println("parse_item:"+parse_item.size());
            String category;
            JSONObject weather;
            String day="";
            String time="";
            Object fcstValue;
            Object fcstDate;
            Object fcstTime;


                for (int i = 0; i < parse_item.size(); i++) {
                    //System.out.println("(In for)parse_item:"+parse_item.size());
                    weather = (JSONObject) parse_item.get(i);
                    //System.out.println("weather:"+weather.toJSONString());
                    fcstValue = weather.get("fcstValue");
                    fcstDate = weather.get("fcstDate");
                    fcstTime = weather.get("fcstTime");
                    category = (String) weather.get("category");
                    //System.out.println("fcstValue:" + String.valueOf(fcstValue));
                    //System.out.println("fcstValue:"+fcstValue.toString()+"fcstTime:"+fcstDate.toString()+"fcstTime:"+fcstTime.toString());
                if(!day.equals(fcstDate.toString())) {
                    day=fcstDate.toString();
                }
                if(!time.equals(fcstTime.toString())) {
                    time=fcstTime.toString();
                   // System.out.println(day+"  "+time);
                    if(time.equals("1600")&&day.equals("20221215"))
                    fw.write(day+" "+time+"\n");
                }
                   /* System.out.print("\tcategory : " + category);
                    System.out.print(", fcst_Value : " + fcstValue);
                    System.out.print(", fcstDate : " + fcstDate);
                    System.out.println(", fcstTime : " + fcstTime);*/
                    if(day.equals("20221215")&&time.equals("1600")&& (category.equals("PTY")||category.equals("SKY")||category.equals("TMP"))) {
                        fw.write( category + "\n");
                        fw.write( fcstValue + "\n");
                        //fw.write(", fcstDate : " + fcstDate + "\n");
                       // fw.write(", fcstTime : " + fcstTime + "\n");
                    }
                }
                fw.flush();
                fw.close();
           /* JSONParser jsonparser= new JSONParser();
            JSONObject jobject = new JSONObject();
            Object ob=jsonparser.parse(result);
            //System.out.println("ob:"+ob);
             if (ob instanceof JSONObject) {
                JSONObject jsonObject = (JSONObject)ob;
                System.out.println("jsonObiect:"+jsonObject);

                // .. JSON 객체 처리

            }
            else if (ob instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray)ob;
                System.out.println("jsonArray:"+jsonArray);
            }*/


        /*// response 키를 가지고 데이터를 파싱
        JSONObject jsonObj_1 = new JSONObject(result);
        String response = jsonObj_1.getString("response");

        // response 로 부터 body 찾기
        JSONObject jsonObj_2 = new JSONObject(response);
        String body = jsonObj_2.getString("body");

        // body 로 부터 items 찾기
        JSONObject jsonObj_3 = new JSONObject(body);
        String items = jsonObj_3.getString("items");
        Log.i("ITEMS",items);

        // items로 부터 itemlist 를 받기
        JSONObject jsonObj_4 = new JSONObject(items);
        JSONArray jsonArray = jsonObj_4.getJSONArray("item");

        for(int i=0;i<jsonArray.length();i++){
            jsonObj_4 = jsonArray.getJSONObject(i);
            String fcstValue = jsonObj_4.getString("fcstValue");
            String category = jsonObj_4.getString("category");

            if(category.equals("SKY")){
                weather = "현재 날씨는 ";
                if(fcstValue.equals("1")) {
                    weather += "맑은 상태로";
                }else if(fcstValue.equals("2")) {
                    weather += "비가 오는 상태로 ";
                }else if(fcstValue.equals("3")) {
                    weather += "구름이 많은 상태로 ";
                }else if(fcstValue.equals("4")) {
                    weather += "흐린 상태로 ";
                }
            }

            if(category.equals("T3H") || category.equals("T1H")){
                tmperature = "기온은 "+fcstValue+"℃ 입니다.";
            }

            Log.i("WEATER_TAG",weather + tmperature);*/
        }
        catch (Exception e){
            e.printStackTrace();
        }
        }

    }

