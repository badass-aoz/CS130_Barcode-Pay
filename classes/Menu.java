
//import /root/CreditCard;
//import /root/DBManager;
//import /root/DBOperation;
//import /root/DBOperation1;
//import /root/TestSignOn;

import java.io.*;
import java.net.*;


public class Menu {
	private String DELIMITER = "`";
	public static final int SUCCESS = 1;
	public static final int FAILURE = -1;
	
	public int order(String choice, Hash_Generator hashGen, Socket socket) throws IOException {
		String[] args = choice.split(DELIMITER);
		Authenticator auth;
		try {
		    auth = new Authenticator();
		} catch (Exception e) {
		    System.out.println("blah Authenticator\n");
		    return FAILURE;
		}
		int caseNum = Integer.parseInt(args[0]);
		int valid_num=0;

		if(caseNum != 4){
		    System.out.println("isvalid called");
			valid_num = auth.isValid(args[1],args[2],args[3]);
		}
			else
			valid_num = auth.isValid(args[1],args[2]); 
		

		switch(caseNum){
			/*
			Sign up
			args[1] = username'
			args[2] = password
			args[3] = phoneNumber
			args[4] = pictureSize
			*/


			// To be changed after Ping's done with authentication
			case 1:	

			    System.out.println("validnumber:" + valid_num);
			if(valid_num == 2) {

                                      // Ping: here is the code for sign up,it will insert a new record to DB with above 4 parameters
                                      int result = 0; //result: 0: failed, 1:successful
                                      DBOperation DBO;
				      try {
					 DBO = new DBOperation();
					 result = DBO.openAccount(args[4],args[1],args[2],args[3]);
				      } catch (Exception e) {
					  System.out.println("blah, createAccount!\n");
					  return FAILURE;
				      }

                                      // Ping: end


				// create a image file
				String path = "/home/peter/CS130_Barcode-Pay/photos/" + args[1]+".jpg";
				File f = new File (path);
				f.createNewFile();

		    	byte[] mybytearray = new byte[10485760];
		    	InputStream is = socket.getInputStream();
		    	FileOutputStream fos = new FileOutputStream("/home/peter/CS130_Barcode-Pay/photos/" + args[1]+".jpg");
		    	BufferedOutputStream bos = new BufferedOutputStream(fos);

		    	int numRead = 0;
		    	int totalNumRead = 0;
		    	int totalBytes = Integer.parseInt(args[4]);
		    	while ( (totalNumRead < totalBytes) &&(numRead = is.read(mybytearray) ) > 0) {
          			bos.write(mybytearray, 0, numRead);
          			totalNumRead +=numRead;
     			}
		   	
		   		bos.close();
		   		return SUCCESS;
			}
			else
		    	return FAILURE;

			/*
			Login
			args[1] = username
			args[2] = password
			args[3] = phoneNumber
			*/
                                       //Ping: not sure what to do with sign in
                                       //      are you going to use authenticator here?
                                       //Ping: end
			
			case 2:
			    if(valid_num == 0){
				//				int result = 0; // result: 0: no credit card is linked to this account
				//         1: credit card exist.
				//				DBOperation1 DBO1 = new DBOperation1();
				//				result = DBO1.searchCreditCard(args[1],args[2],args[3]);
				// int flag = 0;
				// if( result == 0){
				//     String[] temp = {"","","",args[4],args[5],args[6],args[7],args[8],args[9]};
				//     CreditCard cc = new CreditCard(temp);
                                //             flag = DBO1.linkCreditCard (args[1],cc); // flag: 0: failed, 1: successful
				// }
				//Ping: end
				
				
				return SUCCESS;
			    }
			    else{
				return FAILURE;
			    }
			    
			/*
			  Link Credit Card
			  args[1] = username
			  args[2] = password
			  args[3] = phoneNumber
			  args[4] = "creditCardType"
			  args[5] = "creditCardNumber"
			  args[6] = "expirationMonth"
			  args[7] = "expirationYear"
			  args[8] = "cardVerificationNumber(CSV)"
			  args[9] = "cardHolderName"
			*/
			    
			    //Ping: code for link credit card
                                        
			    
                            
			    
		case 3:
		    //TODO:
		    return FAILURE;
		    
		    /*
		      Request Barcode
		      args[1] = username
		      ar
		      args[3] = phoneNumber
			*/
		case 4:
		    if(valid_num == 0){
			CreditCard cc4 = new CreditCard();
			cc4.setCreditCardType("visa");
		    	String path = "/home/peter/CS130_Barcode-Pay/photos/" + args[1]+".jpg";
		    	FileInputStream fis = null;
		    	OutputStream os = null;
		    	ByteArrayOutputStream bos = new ByteArrayOutputStream();



		    	try{
		    		fis = new FileInputStream(path);
		    		os = socket.getOutputStream();
		    	}catch (Exception e){
		    		if(fis!=null)
		    			fis.close();
		    		if (os!=null)
		    			os.close();
		    		return FAILURE;
		    	}

		    	PrintWriter out = new PrintWriter(os,true);
		    	byte[] buf = new byte[1024];
		    	
		    	try{
		    		for (int readNum; (readNum = fis.read(buf))!=-1;){
		    			bos.write(buf,0,readNum);
		    		}
		    	}catch(Exception e){
		    		System.out.println("Error in reading file");
		    	}

		    	byte[] bytes = bos.toByteArray();


		    	//int totalNumBytes = bytes.length;

		    	out.println(hashGen.getRandomNumber(cc4));
		    	//out.println(((Integer)totalNumBytes).toString());
		    	os.write(bytes);
		    	os.flush();

		    	return SUCCESS;
			}
			else
				return FAILURE;
		}
		return FAILURE;
	}
}
