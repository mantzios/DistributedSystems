import java.io.Serializable;
import java.sql.Timestamp;

public class Info implements Serializable {
	private double x1;
	private double y1;
	private double x2;
	private double y2;
	private static final long  serialVersionUID = -733857438754387543L;
	private Timestamp date1;
	private Timestamp date2;
	private String Ip;
	private int counter;
    private String name;
	private String point;
	private String url;
	private boolean isInsert=false;
	
	public boolean isInsert() {
	   return isInsert;
	}

	   
	
	public String getIp() {
		return Ip;
	}


	public void setIp(String ip) {
		Ip = ip;
	}


	public int getCounter() {
		return counter;
	}


	public void setCounter(int counter) {
		this.counter = counter;
	}
	
	public Info(){
		counter=0;
	}
	public Info(String name,String point,String url,boolean f){
	     this.name=name;
	     this.point=point;
	     this.url=url;
	     isInsert=f;
	}
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getPoint() {
		return point;
	}



	public void setPoint(String point) {
		this.point = point;
	}



	public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		this.url = url;
	}



	public Info(double xi1, double yi1, double xi2, double yi2,Timestamp Date1,Timestamp Date2,String ip,int c){
		x1 = xi1;
		y1 = yi1;
		x2 = xi2;
		y2 = yi2;
		date1 = Date1;
		date2 = Date2;
		Ip=ip;
		counter=c;
		
	}
	
	
	public Timestamp getDate1() {
		return date1;
	}


	public void setDate1(Timestamp date1) {
		this.date1 = date1;
	}


	public Timestamp getDate2() {
		return date2;
	}


	public void setDate2(Timestamp date2) {
		this.date2 = date2;
	}


	public double getX1() {
		return x1;
	}
	public double getY1() {
		return y1;
	}
	public void setX1(double x) {
		this.x1 = x;
	}
	public void setY1(double y) {
		this.y1 = y;
	}
	
	public double getX2() {
		return x2;
	}
	public double getY2() {
		return y2;
	}
	public void setX2(double x) {
		this.x2 = x;
	}
	public void setY2(double y) {
		this.y2 = y;
	}
	
	
	
}
