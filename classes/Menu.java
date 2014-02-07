//import java.util.*;
import java.io.*;

public class Menu {


    
    public Boolean order(String choice) throws IOException {

	int caseNum = Integer.parseInt(choice);
	//	while(caseNum != 6){
	switch(caseNum){
	    case 1:
		System.out.println("Create a user account function has not been implemented!");
		/*		UserAccount a = new UserAccount();
				r.createAnAccount();
		*/
		return true;
	    case 2:/*
		     Registrar r = new Registrar();
		     CreditCard c = new CreditCard();
		     r.addCreditCard(c, inFromClient, outToClient);
		   */
		System.out.println("Add a credit card function has not been implemented!");
		
		return true;
	    case 3:
		System.out.println("Update a Barcode function has not been implemented!");
		return true;
	    /*
	      creditCard c = new CreditCard();
		  r.updateCreditCard(c, inFromClient, outToClient);
		*/
	    case 4:
	    /*	    Authenticator a = new Authenticator();
		    if(a.isValidAccount()){
			    CreditCard disposableCard = getCardFromDB(account, password);
			    Hash_Generator h;
			    int randomKey = h.randomKey();
			    h.table.put(randomKey, disposableCard);
			    outToClient.println(randomKey);
			    }
			    else{
			    outToClient.println("The account or password you entered is incorrect.");
			    this.display();
			    this.order();
			    }
	    */
		System.out.println("Request a Barcode function has not been implemented!");
		return true;
	    case 5:
		System.out.println("Decode a Barcode function has not been implemented!");
		return true;
	    
	    
	    default:
		System.out.println("Invalid Number!");
		return true;
	    }

				    //this.order();
	    //}   
	
    }
}
    
    


	    

	    
      
