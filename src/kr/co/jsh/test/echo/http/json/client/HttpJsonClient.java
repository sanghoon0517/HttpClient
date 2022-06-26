package kr.co.jsh.test.echo.http.json.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class HttpJsonClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new HttpJsonClient().request();
	}
	
	public void request() {
		
		JSONObject json = new JSONObject();
		json.put("author", "jsh");
		json.put("data", "TEST DATA");
		
//		String target = "https://webhook.site/60e0b93d-4dd9-4021-a04d-77bfedd66590";
		String target = "http://localhost:8181/HttpEchoServer/main";
		
		try {
			URL url = new URL(target);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			
			con.setDoOutput(true);
			
			//POST방식으로 JSON데이터 전송
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));

			bw.write(json.toString());
			bw.flush();
			bw.close();
			
			//서버에서 보낸 응답 데이터 수신 받기
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			
			int statusCode = con.getResponseCode();
			if(statusCode == con.HTTP_OK) {
				System.out.println("정상 응답");
				String retMsg = in.readLine();
				System.out.println("응답 메시지 : "+retMsg);
				
				JSONParser parser = new JSONParser();
				JSONObject retJson = (JSONObject) parser.parse(retMsg);
				
				System.out.println("JSON parse를 한 응답 메시지 : "+retJson.toString());
			} else {
				System.out.println("에러 응답코드 : "+statusCode);
			}
			
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(ParseException e) {
			e.printStackTrace();
		}
	}

}
