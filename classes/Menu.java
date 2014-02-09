import java.io.*;

public class Menu {
    	private String DELIMITER = "`";

    
    public int order(String choice, Hash_Generator hashGen) throws IOException {
	String[] args = choice.split(DELIMITER);
	System.out.println(args[0]+args[1]+args[2]);

	Authenticator auth = new Authenticator();

	//TO DO: auth.isValid should check the args[1](account name) and args[2](password).
	//       auth.isValid returns:
	//       0: account and password is correct and inside the DB.
	//       1: account is in DB, but password is not correct.
	//       2: account does not exist in DB.
	int valid_num = auth.isValid(args[1], args[2]);
	
	    int caseNum = Integer.parseInt(args[0]);
	
	
	    switch(caseNum){
	    case 1:
		// When valid_num is equal to 2, it means that account is not exists.
		if (valid_num == 2) {

		    // args[0] = "2"                                                                                                                                                                              
		    // args[1] = "accountName"                                                                                                                                                                    
		    // args[2] = "password"         

		    // TODO: Store the account and password to DB. 

		    return 0;
		    
		}
		else
		    return -valid_num;
		
	    case 2:
		if ( valid_num == 0 ) {
		CreditCard c = new CreditCard(args);

		// args[0] = "2"
		// args[1] = "accountName"
		// args[2] = "password"
		// args[3] = "creditCardType"
		// args[4] = "creditCardNumber"
		// args[5] = "expirationMonth"
		// args[6] = "expirationYear"
		// args[7] = "cardVerificationNumber(CSV)"
		// args[8] = "cardHolderName"

		//TODO: Store the credit card object to DB

		
		return 0;

		}
		else
		    return -valid_num;
		
	    case 3:
		System.out.println("Update a Barcode function has not been implemented!");
		return 0;

	    case 4:
		if ( valid_num == 0 ) {

  
		    CreditCard cc4 = new CreditCard();
 		   
		    // args[0] = "4"
		    // args[1] = "accountName"
		    // args[2] = "password"
		  
		    // TO DO:
		    // Using given account name and password, create a credit card object from DB.
		    return hashGen.getRandomNumber(cc4);
		    

		    
		} else {
		    // for incorrect password / 
		    return -valid_num;
		} 		



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
    
    


	    

	    
      
