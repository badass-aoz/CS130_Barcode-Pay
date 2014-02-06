public class CreditCard {

    public void setCreditCardType(String creditCardType){
	this.pCreditCardType = creditCardType;
    }
    public void setCreditCardNumber(String creditCardNumber){
	this.pCreditCardNumber = creditCardNumber;
    }
    public void setExpirationMonth(String expirationMonth){
	this.pExpirationMonth = expirationMonth;
    }
    public void setExpirationYear(String expirationYear){
	this.pExpirationYear = expirationYear;
    }
	
    public void setCardVerificationNumber(String cardVerificationNumber){
	this.pCardVerificationNumber = cardVerificationNumber;
    }


    public String getCreditCardType(){
	return this.pCreditCardType;
    }
    public String getCreditCardNumber(){
	return this.pCreditCardNumber;
    }
    public String getExpirationMonth(){
	return this.pExpirationMonth;
    }
    public String getExpirationYear(){
	return this.pExpirationYear;
    }
    public String getCardVerificationNumber(){
	return this.pCardVerificationNumber;
    }


   
    
    private String pCreditCardType;
    private String pCreditCardNumber;
    private String pExpirationMonth;
    private String pExpirationYear;
    private String pCardVerificationNumber;
}
