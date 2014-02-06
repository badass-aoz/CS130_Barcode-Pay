import java.io.*;
import java.net.*;

public class BarcodeServer {
    

    private static class Worker extends Thread {

	
        private Socket socket;
        private int clientNumber;
	

        public Worker(Socket socket, int clientNumber) {
            this.socket = socket;
            this.clientNumber = clientNumber;
            log("New connection with client# " + clientNumber + " at " + socket);
        }
	
        public void run() {
	    
	    CreditCard c = new CreditCard();
	    Registrar r = new Registrar();
	    

            try {
		
		BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		// DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());
		PrintWriter outToClient = new PrintWriter(socket.getOutputStream(), true);

		outToClient.println("Hello, you are client #" + clientNumber + ".");
		c = r.createCreditCard(inFromClient, outToClient);
		outToClient.println("Your card number is: " + c.getCreditCardNumber());
		
		










		
            } catch (IOException e) {
                log("Error handling client# " + clientNumber + ": " + e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    log("Couldn't close a socket, what's going on?");
                }
                log("Connection with client# " + clientNumber + " closed");
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
            }
        } finally {
            listener.close();
        }
    }

}
