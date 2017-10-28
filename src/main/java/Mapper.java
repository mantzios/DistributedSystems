import java.io.IOException;
import java.net.*;
public class Mapper {
	private ServerSocket serverSocket = null;
	private Socket socket = null;;
	public static void main(String args[]) {
		System.out.println(args[0]);
		new Mapper().openServer(Integer.parseInt(args[0]));
	}
	
	void openServer(int portnum){
		try{
			serverSocket = new ServerSocket(portnum , 10);
			while(true){
				socket = serverSocket.accept();
				System.out.println("Perimenw");
				Thread t = new Waiting(socket);
				t.start();
				
			}
			
			
			
			
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				serverSocket.close();
				socket.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	
}
