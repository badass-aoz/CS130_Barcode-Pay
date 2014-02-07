import java.util.*;
import java.io.*;

public class MenuLocal {

    public BufferedReader input;
    public PrintWriter output;

    // Constructor
    public MenuLocal(BufferedReader inFromClient, PrintWriter outToClient){
	this.input = inFromClient;
	this.output = outToClient;
    }


    
    public void display (){
	this.output.println("Enter the menu: 1)Create an account. 2)Add a credit card. 3)Update a credit card. 4) Request a Barcode. 5) Decode a Barcode.(For cashier) 6) Leave the application.");
    }
    
    
    public void order (int choiceOfMenu) {
	
	switch(choiceOfMenu){
	case 1:
	    this.output.println("Create an account function has not been implemented!");
	    break;
	case 2:
	    this.output.println("Add a credit card function has not been implemented!");
	    
	    break;
	case 3:
	    this.output.println("Update a Barcode function has not been implemented!");
	    break;
	case 4:
	    this.output.println("Request a Barcode function has not been implemented!");
	    break;
	case 5:
		this.output.println("Decode a Barcode function has not been implemented!");
		break;
	default:
	    this.output.println("Invalid Number!");
	    break;
	}
    }

    public static void main(String args[]){
	BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter output = new PrintWriter(new OutputStreamWriter(System.out));
	MenuLocal m = new MenuLocal(input, output);
	//	m.display();
	//m.order(1);


    }
}
