import java.io.*;

public class Menu {
    	private String DELIMITER = "`";

    
    public int order(String choice, Hash_Generator hashGen) throws IOException {
	String[] args = choice.split(DELIMITER);
	System.out.println(args[0]+args[1]+args[2]);
	// TO DO
	// needs some method to validate arguments
	Authenticator auth = new Authenticator();
	int valid_num = auth.isValid(args[1], args[2]);
	
	    int caseNum = Integer.parseInt(args[0]);
	//	while(caseNum != 6){
	
	
	
	    switch(caseNum){
	    case 1:
		// When valid_num is equal to 2, it means that account is not exists.
		if (valid_num == 2) {
		    //TODO: Store the account and password to DB. 
		    return 0;
		    
		}
		else
		    return -valid_num;
		
	    case 2:
		if ( valid_num == 0 ) {
		CreditCard c = new CreditCard(args);
		//TODO: Store the credit card object to DB

		
		return 0;

		}
		else
		    return -valid_num;
		
	    case 3:
		System.out.println("Update a Barcode function has not been implemented!");
		return 0;
	    /*
	      creditCard c = new CreditCard();
		  r.updateCreditCard(c, inFromClient, outToClient);
		*/
	    case 4:
		if ( valid_num == 0 ) {

		    // TO DO:
		    // fetch credit card information from DB and generate random number accordingly
		    
		    CreditCard cc4 = new CreditCard();
		    return hashGen.getRandomNumber(cc4);
		    

		    
		} else {
		    // for incorrect password / 
		    return -valid_num;
		} 		


		
		

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

	    case 5:
		System.out.println("Decode a Barcode function has not been implemented!");
		return 0;
	    
	    
	    default:
		System.out.println("Invalid Number!");
		return 0;
	    }

				    //this.order();
	    //}

	
    }
}
    
    


	    

	    
      
