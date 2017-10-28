import java.net.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;



import java.io.*;

public class DummyClient {
	private Socket socket1;
	private Socket socket2;
	private Socket socket3;
	private Socket socket;
	private ServerSocket server;
	private	ObjectInputStream in;
	private ObjectOutputStream out;
	StringTokenizer token;
	private final String mapper_ip1="127.0.0.1";
	private final String mapper_ip2="127.0.0.1";
	private final String mapper_ip3="127.0.0.1";
	int counter =0;
	Info a=null; //mapper1
	Info b=null; // mapper 2
	Info c=null; //mapper 3
	
	
	public  DummyClient(String location, Timestamp date1, Timestamp date2){
		StringTokenizer token = new StringTokenizer(location, ",");
		double firstx = Double.parseDouble(token.nextToken().trim());
		double firsty = Double.parseDouble(token.nextToken().trim());
		double secondx = Double.parseDouble(token.nextToken().trim());
		double secondy = Double.parseDouble(token.nextToken().trim());
		double differencex = Math.abs((firstx - secondx))/3.0;
	
		
		
		try {
			
			socket1 = new Socket(mapper_ip1, 4001);
			socket2 = new Socket(mapper_ip2, 4002);
			socket3 = new Socket(mapper_ip3, 4003);	

			//a = new Info(firstx, firsty, (firstx + differencex), secondy, date1, date2,socket1.getInetAddress().getHostAddress(), 3);
			out = new ObjectOutputStream(socket1.getOutputStream());
			out.writeObject(a);
			out.flush();
			
			
			b = new Info(firstx + differencex, firsty, (firstx + (2 *differencex)), secondy, date1, date2,socket2.getInetAddress().getHostAddress(), 3);
			out = new ObjectOutputStream(socket2.getOutputStream());
			out.writeObject(b);
			out.flush();
			
			
			c = new Info(firstx + (2 *differencex), firsty, (firstx + (3*differencex)), secondy, date1, date2,socket3.getInetAddress().getHostAddress(), 3);
			out = new ObjectOutputStream(socket3.getOutputStream());
			out.writeObject(c);
			out.flush();
			
			server = new ServerSocket(4005);
			socket = server.accept();
			
			in = new ObjectInputStream(socket.getInputStream());
			
			@SuppressWarnings("unchecked")
			ArrayList<CheckIn> finalList = (ArrayList<CheckIn>)in.readObject();
			finalList.stream().forEach(s -> System.out.println(s));
		
		} catch (IOException e) {
			System.err.println("Cannot connect to mappers!");
			
			
			
		} catch (ClassNotFoundException e) {
			System.err.println("Wrong Packet Received!!");
		}finally{
			try{
				out.close();
				in.close();
				socket1.close();
				socket2.close();
				socket3.close();
				socket.close();
				server.close();
			}catch(IOException e){
				System.err.println("Cannot Close connections!");
			}
		}	
	}

	public static void main(String[] args) throws NumberFormatException, SQLException{
		String down="2012-2-23 4:34:03";
		String up="2016-2-23 4:34:03";
		
		Timestamp x1=new Timestamp(65756);
		Timestamp x2=new Timestamp(0435345);
		x1=Timestamp.valueOf(down);
		x2=Timestamp.valueOf(up);
	    
		new DummyClient("40.560, -73.9, 40.88, -73.78",x1,x2);
	}
	
	

}

