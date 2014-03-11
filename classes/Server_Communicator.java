import java.io.*;
import java.net.*;

public class Server_Communicator {

    //    private static Hash_Generator hashGen = new Hash_Generator();
    private static Hash_Generator hashGen;

    public static class Worker extends Thread {
        private Socket socket;
        private int clientNumber;
    
	// Constructor
    public Worker(Socket socket, int clientNumber){
            this.socket = socket;
            this.clientNumber = clientNumber;
            log("New connection with client# " + clientNumber + " at " + socket);
	    //	    hashGen = hashGen;
        }
	
    public void run() {
	    //	    Registrar r = new Registrar();
	    
	    String choice;
	    int response;

            try {

 
		
		BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		// DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());
		PrintWriter outToClient = new PrintWriter(socket.getOutputStream(), true);		
		Menu m = new Menu();
		
		long startTime = 0;
		long elapsedTime = 0;
		if((choice= inFromClient.readLine()) != null && choice!=""){		    
		    if(choice.charAt(0) == '4'){
                        startTime = System.currentTimeMillis();
                    }
                    else if(choice.charAt(0) == '5'){
                        elapsedTime = System.currentTimeMillis()-startTime;
                        if(elapsedTime>6000){
                            System.out.println(elapsedTime);
                            System.out.println("Barcode has been expired. Request a barcode again");
                            //break;
                        }
                    }

		    System.out.println(choice);
		    response = m.order(choice, hashGen, socket);
            if (response<0)
                outToClient.println("ERROR");
            else
                outToClient.println(response);
		}
		
            } catch (IOException e) {
                log("Error handling client# " + clientNumber + ": " + e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    log("Couldn't close a socket, what's going on?");
                }
                log("Connection with client# " + clientNumber + " closed");
		this.interrupt();
		System.out.println("here!\n");
		}

        }


        private void log(String message) {
            System.out.println(message);
        }
    }

    
    public static void main(String[] args) throws Exception {
        System.out.println("The barcode server is running.");
        int clientNumber = 0;
        ServerSocket listener = new ServerSocket(9800);
        try {
            while (true) {
		hashGen = new Hash_Generator();
                new Worker(listener.accept(), clientNumber++).start();
		// if (hashGen.isEmpty()) {
		//     System.out.println("blah\n\n");
		// }
            }
        } finally {
            listener.close();
        }
    }
    
}
