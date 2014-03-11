//import /root/CreditCard;
//import /root/DBManager;
//import /root/DBOperation;
//import /root/DBOperation1;
//import /root/TestSignOn;


public class Authenticator {

    public int isValid(String account, String password, String phoneNumber){
		// TO DO:
		// Check DB if the account and password pair is valid.
		// return value 0: success. username, passwords, and phoneNumber combination is inside of the DB
		// return value 1: failure. At least one element does not match.
                          //if (account!= null && password!=null && phoneNumber!=null)
		//	return 0;
		//return 0;

             int result = 2;
             DBOperation db = new DBOperation();
             result = db.searchAccount(account,password,phoneNumber);
             return result; // result: 0: correct username,password,phone number
                            //         1: wrong password,or wrong phone number
                            //         2: Account do not exist		
    }

    public int isValid (String account,String phoneNumber){
    	//if(account!=null && phoneNumber!=null)
    	//	return 0;
    	//return 0;
              int result = 2;
             DBOperation db = new DBOperation();
             result = db.searchAccountNP(account,phoneNumber);
             return result; // result: 0: correct username,phone number
                            //         1: username do not match phone number
                            //         2: Account do not exist		

    }
    


}
