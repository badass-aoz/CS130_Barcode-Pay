import java.io.*;
import java.net.*;


public class Menu {
    	private String DELIMITER = "`";

    
    public int order(String choice, Hash_Generator hashGen, Socket socket) throws IOException {
	String[] args = choice.split(DELIMITER);
	System.out.println(args[0]+args[1]+args[2]+args[3]+args[4]+args[5]);

	Authenticator auth = new Authenticator();

	//TO DO: auth.isValid should check the args[1](account name) and args[2](password).
	//       auth.isValid returns:
	//       0: account and password is correct and inside the DB.
	//       1: account is in DB, but password is not correct.
	//       2: account does not exist in DB.
	int valid_num = auth.isValid(args[1], args[2], args[3]);
	
	    int caseNum = Integer.parseInt(args[0]);
	
	
	    switch(caseNum){
	    case 1:
		// When valid_num is equal to 2, it means that account is not exists.
		
		// args[0] = "5"                                                                                                                                                                              
		    // args[1] = "accountName"                                                                                                                                                                    
		    // args[2] = "password"         

		    // args[3] = "phone number"

		    // args[4] = "fileSize"
		    
		    // args[5] = "fileName"


		    // TODO: Store the account and password to DB. 


		// After validation, read the stream to get the file.
		if(valid_num == 0) {
		    
		    byte[] mybytearray = new byte[10485760];
		    InputStream is = socket.getInputStream();
		    FileOutputStream fos = new FileOutputStream("/home/peter/CS130_Barcode-Pay/photos/" + args[1]+".jpg");
		    BufferedOutputStream bos = new BufferedOutputStream(fos);
		    int bytesRead = is.read(mybytearray, 0, mybytearray.length);
		    bos.write(mybytearray, 0, bytesRead);
		    bos.close();
		    //		    socket.close();
		    
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
		System.out.println("In case4");
		if ( valid_num == 0 ) {

  
		    CreditCard cc4 = new CreditCard();
		    cc4.setCreditCardType("visa");
		    System.out.println(cc4.getCreditCardType());
		    // args[0] = "4"
		    // args[1] = "accountName"
		    // args[2] = "password"
		    System.out.println("In case4 if");
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
    
    


	    

	    
      
