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
		
		outToClient.println("Hello, you are client #" + clientNumber + ".");		
		Menu m = new Menu();
		
		while((choice= inFromClient.readLine()) != null){
		    response = m.order(choice, hashGen);
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
