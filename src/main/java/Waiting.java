import java.io.*;
import java.net.*;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Waiting extends Thread{
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private ArrayList<CheckIn> arrayList=new ArrayList<CheckIn>();
	private Socket ergasia2;
	private Socket socket1;
	private String reducerIp = "127.0.0.1";
	private ArrayList<CheckIn> newList;
	
	
	public Waiting(Socket c){
		socket1=c;
	}

	public void run(){
		try{
			in=new ObjectInputStream(socket1.getInputStream());
			if(in!=null){
				Info a = (Info)in.readObject();	
				if(a.isInsert()==true){
					new dbConnect().insert(a.getName(),a.getPoint(),a.getUrl());
				}
				else{
					arrayList = new dbConnect(a.getX1(), a.getX2(), a.getY1(), a.getY2()).getList();
					map(a, arrayList);
				}
			}	
		}catch(IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.err.println("Wrong Info Object!!!");
		}
		
		finally{
			try{
				if (in!=null)
					in.close();
				
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	private void map(Info a, ArrayList<CheckIn> l) throws IOException{
		Timestamp down=a.getDate1();
		Timestamp up=a.getDate2();
		
		ConcurrentHashMap<String, CheckIn> hashmap = new ConcurrentHashMap<String, CheckIn>();
		if(l.size() > 1){
			l.stream().parallel().forEach(s -> {
			hashmap.put(s.getPoi(), hashmap.containsKey(s.getPoi()) ? hashmap.get(s.getPoi()).incrFreq(s.getPhoto()): new CheckIn(s.getPoiname(), s.getPoi(), 1, s.getPhoto(),s.getX(),s.getY()));
			}
			);
			newList = new ArrayList<CheckIn>(hashmap.values());
			newList = (ArrayList<CheckIn>)newList.stream().sorted((s,q)->Long.compare(q.getFreq(),s.getFreq())).limit(10).collect(Collectors.toList());
		}else{
			newList = l;
		}
		
		for(int i=0; i<newList.size(); i++){
			newList.get(i).setIp(a.getIp());
			newList.get(i).setCounter(a.getCounter());
		}
		
		System.out.println(newList.get(0).getIp());
		System.out.println();
		System.out.println();
		newList.stream().forEach(s->System.out.println(s));
		SendtoReducer(newList);
	}
	
	private void SendtoReducer(ArrayList<CheckIn> list){
		try{
		ergasia2 = new Socket(reducerIp  , 4004);
		out = new ObjectOutputStream(ergasia2.getOutputStream());
		out.writeObject(list);
		out.flush();
		}catch (IOException e){
			e.printStackTrace();
		}
		try{
			ergasia2.close();
			out.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	










}
