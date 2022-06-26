package kr.co.jsh.test.echo.http.client;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpEchoClient {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		new HttpEchoClient().init();
	}
	
	public void init() throws Exception{
		URL url = new URL("http://localhost:8181/HttpEchoServer/main");
		
		HttpURLConnection con = (HttpURLConnection) url.openConnection(); //HTTP Connection
		
		System.out.println("서버의 HTTP 서버와 연결 성공");
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "text/plain");
		
		con.setConnectTimeout(30000);
		con.setReadTimeout(30000);
		con.setDoOutput(true);
		con.setDoInput(true);
		
		//서버에 데이터를 전송할 객체
		OutputStream out = con.getOutputStream();
		String data = "hello";
		System.out.println("서버에 보내는 데이터 : "+data);
		byte[] bytes = data.getBytes();
		out.write(bytes);
		out.flush();
		
		System.out.println("응답 코드 : "+ con.getResponseCode());
		
		//서버로부터 응답받은 데이터를 받는 객체
		InputStream in = con.getInputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		byte[] bytes2 = new byte[1024];
		int cnt;
		while((cnt = in.read(bytes2)) != -1) {
			baos.write(bytes2);
		}
		byte[] byteData = baos.toByteArray();
		String echoData = new String(byteData);
		System.out.println("서버에서 준 응답 데이터 : "+ echoData);
		System.out.println("서버로부터 받은 헤더의 응답값 : "+con.getHeaderFields());
		
		out.close();
		in.close();
	}

}
