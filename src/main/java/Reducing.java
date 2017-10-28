import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


public class Reducing extends Thread{
	private ConcurrentHashMap<String,ArrayList<ArrayList<CheckIn>>> a=new ConcurrentHashMap<String,ArrayList<ArrayList<CheckIn>>>();
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private ArrayList<CheckIn> lista;
	private Socket socket2;
	
	Reducing(Socket s,ConcurrentHashMap<String,ArrayList<ArrayList<CheckIn>>> b){
		socket=s;
		a=b;
		lista=new ArrayList<CheckIn>();
	}
	
	@SuppressWarnings("unchecked")
	public void run(){
		try{
			in=new ObjectInputStream(socket.getInputStream());
			if(in!=null){
			    lista=(ArrayList<CheckIn>) in.readObject();
			    if(lista==null) System.out.println("null arraylist");
			    reduce();
				
			}	
		}catch(IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.err.println("Wrong Info Object!!!");
		}finally{
			try{
				if (in!=null)
					in.close();
				
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	private void reduce(){
		synchronized(a){
		if(!a.containsKey(lista.get(0).getIp())){
			ArrayList<ArrayList<CheckIn>> temp=new ArrayList<ArrayList<CheckIn>>();
			temp.add(lista);
			a.put(lista.get(0).getIp(),temp);
		}
		
		else{
			ArrayList<ArrayList<CheckIn>> temp=a.remove(lista.get(0).getIp());
			temp.add(lista);
			a.put(lista.get(0).getIp(), temp);
		}
		if(a.get(lista.get(0).getIp()).size()==lista.get(0).getCounter()){ //ison me to counter
			ArrayList<ArrayList<CheckIn>> temp1=a.remove(lista.get(0).getIp());
			temp1.add(lista);
			ArrayList<CheckIn> send=new ArrayList<CheckIn>();
			for(int i=0; i<lista.get(0).getCounter(); i++){
				send.addAll(temp1.get(i));
			}	
				send=(ArrayList<CheckIn>) send.stream().sorted((s,q)->{
					if(s.getPoi()==q.getPoi())  s.setFrequency(q.getFreq());
					int b=Long.compare(q.getFreq(),s.getFreq());
					return b;
					}).limit(10).collect(Collectors.toList());
				
				SendToClient(send);
		}
		}
		
	}
	
	
	private void SendToClient(ArrayList<CheckIn> t){
		try {
			socket2=new Socket(t.get(0).getIp(), 4005);
			out = new ObjectOutputStream(socket2.getOutputStream());
			out.writeObject(t);
			System.out.println("reducer has sent the ArrayList");
			System.out.println(out);
			t.stream().forEach(s->System.out.println(s));
			out.flush();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		finally{
			try {
				socket2.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

