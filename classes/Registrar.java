import java.io.*;


public class Registrar {
    

    public CreditCard createCreditCard(BufferedReader inFromClient, PrintWriter outToClient) throws IOException{



	CreditCard card = new CreditCard();
	outToClient.println("Enter the credit card type:");
	String creditCardType = inFromClient.readLine();
	System.out.println("card type recived: " + creditCardType);
	card.setCreditCardType(creditCardType);
	

	outToClient.println("Enter the credit card number:");
	String creditCardNumber = inFromClient.readLine();
	System.out.println("card number recived: " + creditCardNumber);
	card.setCreditCardNumber(creditCardNumber);


	outToClient.println("Enter the expiration month:");
	String expirationMonth = inFromClient.readLine();
	System.out.println("exp month recived: " + expirationMonth);
	card.setExpirationMonth(expirationMonth);
	

	outToClient.println("Enter the expiration year:");
        String expirationYear = inFromClient.readLine();
	System.out.println("exp year recived: " + expirationYear);
	card.setExpirationYear(expirationYear);


	outToClient.println("Enter the 3-digit card verification number:");
        String cardVerificationNumber = inFromClient.readLine();
	System.out.println("CSV recived: " + cardVerificationNumber);
	card.setCardVerificationNumber(cardVerificationNumber);



	outToClient.println("Your " + card.getCreditCardType() + " has been successfully created.");
	



	return card;
    }



}
