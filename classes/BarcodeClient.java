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
	    Socket clientSocket = new Socket("108.168.239.90", 9800);
	    DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
	    BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

	    System.out.println(inFromServer.readLine());

	    String serverSays = inFromServer.readLine();

	    String clientSays;


	    while(serverSays != null){
		System.out.println(serverSays);
		clientSays = inFromUser.readLine();
		outToServer.writeBytes(clientSays + '\n');
		serverSays = inFromServer.readLine();
	    }
	    





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
