package comNet;

import java.io.BufferedReader;
import java.net.URLDecoder;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.StringTokenizer;

public class HttpRequest implements Runnable {
	final static String crlf = "\r\n";
	Socket client;

	public HttpRequest(Socket client) {
		// TODO Auto-generated constructor stub
		this.client=client;
	}

	@Override
	public void run() {
		try{
			processRequest();
		} catch(Exception e){
			System.out.println(e);
		}
		
	}

	private void processRequest() throws Exception {
		InputStreamReader in = new InputStreamReader(client.getInputStream());
		DataOutputStream os = new DataOutputStream(client.getOutputStream());
		DataInputStream din = new DataInputStream(client.getInputStream());
		String requestline = din.readUTF();
		System.out.println(requestline); 
		BufferedReader br = new BufferedReader(in);
	
		String filename = null;
		
		StringTokenizer token = new StringTokenizer(requestline);
		System.out.println("tokenize");
		//token.nextToken();
		if(token.nextToken().equalsIgnoreCase("Post")){
		filename = token.nextToken();
		filename = URLDecoder.decode(filename,"UTF-8");
		//File f = new File(filename);
		} else if(token.nextToken().equalsIgnoreCase("Get")) {
			filename = token.nextToken();
		}
		FileInputStream fis = null;
		boolean fileExists = true;
		try {
			fis = new FileInputStream(filename);
		} catch(FileNotFoundException e){
			System.out.println("error here");
			fileExists = false;
		}
		
		String statusLine = null;
		String contentType = null;
		String entityBody = null;
		if(fileExists){
			statusLine = "Http/1.1 200 OK";
			contentType = "content-type:"+contentType(filename)+crlf;
		}
		else {
			statusLine = "Http/1.1 404 File Not Found";
			contentType = "content-type: Unknown";
			entityBody = "<HTML>"+ "<HEAD><TITLE> Not Found </TITLE></HEAD>"+
							"<BODY> Not Found</BODY></HTML>";
		}
		
		os.writeBytes(statusLine+crlf);
		os.writeBytes(contentType);
		os.writeBytes(crlf);
		System.out.println(statusLine+""+contentType);
		
		if (fileExists){
			System.out.println(filename);
			sendBytes(fis,os);
			fis.close();
		} else {
			os.writeBytes(entityBody);
		}
		
		os.close();
		br.close();
		client.close();
			}

	private void sendBytes(FileInputStream fis, DataOutputStream os)throws Exception {
		byte[] buffer = new byte[1024];
		int bytes = 0;
		
		while((bytes = fis.read(buffer))!= -1){
			os.write(buffer,0,bytes);
		}
		//os.write(buffer,0,buffer.length);
		os.flush();
		
		
	}

	private static String contentType(String filename) {
		if(filename.endsWith(".htm")|| filename.endsWith(".html")){
		return "text/html";
		}
		if(filename.endsWith(".jpg"))
			return "image/jpg";
		if(filename.endsWith(".gif"))
			return "image.gif";
		return "application/octet-stream";
	}

}
