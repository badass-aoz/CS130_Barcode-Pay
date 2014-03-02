public interface UserServiceIntf{
    public boolean sign_up(String username, String password);
    public boolean log_in(String username, String passwrod);
    public int get_rand_number();
}
