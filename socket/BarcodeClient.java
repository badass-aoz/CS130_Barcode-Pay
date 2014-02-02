import java.io.*;
import java.net.*;
//import java.util.*;

public class BarcodeClient {

    String userName;
    String passWord;
    String loginInfo;

    public void connectToServer(){
	try{

	    BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));
	    Socket clientSocket = new Socket("108.168.239.90", 9898);
	    DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
	    BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

	    System.out.println(inFromServer.readLine());

	    System.out.println("Username: ");
	    userName = inFromUser.readLine();
	    outToServer.writeBytes(userName + '\n');

	    System.out.println("Password: ");
	    passWord = inFromUser.readLine();
	    outToServer.writeBytes(passWord + '\n');

	    loginInfo = inFromServer.readLine();
	    System.out.println(loginInfo);
 	    clientSocket.close();
	} catch (IOException ex) {
	    ex.printStackTrace();
	}
    }



    public static void main(String[] args) {
	BarcodeClient client = new BarcodeClient();
	client.connectToServer();
    }
    

}
