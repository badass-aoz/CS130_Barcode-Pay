import java.util.*;


public class Customer {
    public static void main(String args[]){
	try {
	    UserServiceIntf usi = new UserServiceImpl();
	
	    while(true){
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter your username: ");
		String username = sc.nextLine();
		System.out.println("Enter your password: ");
		String password = sc.nextLine();
		int menu = 0;
		while(menu != 4){
		    System.out.println("Enter the menu: 1. Sign-up 2. Log-in 3.Get-random-number 4.Quit");
		    menu = sc.nextInt();
		    
		    switch(menu) {
		case 1: 
		    usi.sign_up(username, password); break;
		case 2:
		    usi.log_in(username, password); break;
		case 3: 			
		    int randNum = usi.get_rand_number();
		    System.out.println("Your random number(1-100) is " + randNum);
		    break;
		    }
		}
	    }
	}
	catch(Exception e){
	    System.out.println("Exception: " + e);
	}
    }
}



