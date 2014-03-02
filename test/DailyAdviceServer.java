import java.io.*;
import java.net.*;

public class DailyAdviceServer {

    String[] adviceList = { "Take smaller bites" };

    public void go() {
	try {
	    ServerSocket serverSock = new ServerSocket(4242);

	    while(true) {
		Socket sock = serverSock.accept();

		PrintWriter w = new PrintWriter(sock.getOutputStream());
		String advice = getAdvice();
		w.println(advice);
		w.close();
		System.out.println(advice);
	    }
	} catch (IOException ex) {
	    ex.printStackTrace();
	}
    }
    
    private String getAdvice() {
	return adviceList[0];
    }

    public static void main(String[] args) {
	DailyAdviceServer server = new DailyAdviceServer();
	server.go();
    }
}
