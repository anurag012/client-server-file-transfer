package comNet;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URLEncoder;

/**
 * @ Anurag Garg *
 */
public class SocketClient {

    
    public static void main(String arg[]) throws IOException{
        //Creating a SocketClient object
    		BufferedReader bin = null;
        try {
        	String char1;
			System.out.print("Please Enter request method: ");
			BufferedReader method = new BufferedReader(new InputStreamReader(System.in));
			String requestmethod = method.readLine();
			System.out.println("please enter the filename:");
			
			//The stream textentered is an input stream to the program which is attached to the standard input i.e,,keyboard
			BufferedReader textentered = new BufferedReader(new InputStreamReader(System.in));
			char1= textentered.readLine();
        	String request = null;
        	
        	//Establish a connection with server on a port
        	Socket client = new Socket("localhost",1010);
        	
        	//Check the request method for get and post and take appropriate action
        	if(requestmethod.equalsIgnoreCase("Post")){
        	String encode = URLEncoder.encode(char1, "UTF-8");
        	request = requestmethod+" "+encode+" http/1.1"+"\r\n\r\n";
        	} else if(requestmethod.equalsIgnoreCase("Get")) {
        		request =requestmethod+" "+ char1+" http/1.1"+"\r\n\r\n";
        	}
        	
        	//send the request through the output stream
        	DataOutputStream send = new DataOutputStream(client.getOutputStream());
        	send.writeUTF(request);
        	
        	//get the response from server through input stream
        	bin = new BufferedReader(new InputStreamReader(client.getInputStream()));
        	String response; 
        	while((response =bin.readLine())!=null ){
        		System.out.println(response);
        	}
        	
        } catch (Exception e) {
             e.printStackTrace();
        }
    }
}
