import java.io.*;
import java.net.*;


public class Client_Communicator {

    String choiceOfMenu;

    public void connectToServer(){
	try{

	    BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));
	    Socket clientSocket = new Socket("108.168.239.90", 9800);
	    DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
	    BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	    
	    System.out.println(inFromServer.readLine());
	    
	    do {
		System.out.println("Enter the menu:");
		System.out.println("1.Create an account.");
		System.out.println("2.Add a credit card.");
		System.out.println("3.Update a credit card."); 
		System.out.println("4.Request a Barcode.") ;
		System.out.println("5.Decode a Barcode.(For cashier)") ;
		System.out.println("6.Leave the application.");    
		choiceOfMenu = inFromUser.readLine();
		outToServer.writeBytes(choiceOfMenu + "\n");
		System.out.println(inFromServer.readLine());
	    } while(!(choiceOfMenu.equals("6")));	    
	    


	    
	    clientSocket.close();






	} catch (IOException ex) {
	    ex.printStackTrace();
	}
    }



    public static void main(String[] args) {
	Client_Communicator client = new Client_Communicator();
	client.connectToServer();
    }
    

}
