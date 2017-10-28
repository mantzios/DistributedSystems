import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class Reducer {

	private ServerSocket serverSocket;
	private Socket socket;
	private ObjectOutputStream out;
	private Socket socket2;
	private ConcurrentHashMap<String,ArrayList<ArrayList<CheckIn>>> map=new ConcurrentHashMap<String,ArrayList<ArrayList<CheckIn>>>() ;
	void openServer(int portnum){
		try{
			serverSocket = new ServerSocket(portnum , 10);
			while(true){
				socket = serverSocket.accept();
				Thread t=new Reducing(socket,map);
				t.start();
			}
			
			
			
			
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				serverSocket.close();
				socket.close();
				out.close();
				socket2.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args){
		new Reducer().openServer(4004);
	}
}
