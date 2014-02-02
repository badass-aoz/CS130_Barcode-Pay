public class UserServiceImpl implements UserServiceIntf{
   
    public boolean sign_up(String username, String password){
	System.out.println("sign_up function is currently under construction!");
	return false;
    }
    
    public boolean log_in(String username, String passwrod){
	System.out.println("log_in function function is currently under construction!");
	return false;
    }
    
    public int get_rand_number(){
	return (int)(Math.random() * 100 +1);
	
    }

}
