import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;

public class CheckIn implements Serializable {
	private static final long  serialVersionUID = -743857438754387543L;
	private long checkid;
	private int userid;
	private String poi;
	private String poiname;
	private String poicat;
	private int poicatid;
	private double x;
	private double y;
	private Timestamp time;
	private String photo;
	private long freq;
	private ArrayList<String> urls = new ArrayList<String>();
	private String Ip;
	private int counter=0;

	public String getIp() {
		return Ip;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public void setIp(String ip) {
		Ip = ip;
	}

	public CheckIn(long a, int b, String p, String name, String catgory, int id, double xi, double yi, Timestamp dat, String ph){
		checkid = a;
		userid = b;
		poi = p; 
		poiname = name;
		poicat = catgory; 
		poicatid = id;
		x = xi;
		y = yi;
		time = dat;
		if(!ph.equalsIgnoreCase("Not exists"))setPhoto(ph);
	}
	
	public CheckIn(String poin, String POI, long Freq, String url,double x,double y){		
		poiname  = poin;
		poi = POI;
		freq = Freq;
		urls.add(url);
		this.x=x;
		this.y=y;
	}

	public long getCheckid() {
		return checkid;
	}

	public int getUserid() {
		return userid;
	}

	public String getPoi() {
		return poi;
	}

	public String getPoiname() {
		return poiname;
	}

	public String getPoicat() {
		return poicat;
	}

	public int getPoicatid() {
		return poicatid;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setCheckid(long checkid) {
		this.checkid = checkid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public void setPoi(String poi) {
		this.poi = poi;
	}

	public void setPoiname(String poiname) {
		this.poiname = poiname;
	}

	public void setPoicat(String poicat) {
		this.poicat = poicat;
	}

	public void setPoicatid(int poicatid) {
		this.poicatid = poicatid;
	}

	public void setX(long x) {
		this.x = x;
	}

	public void setY(long y) {
		this.y = y;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	
	public String getPhoto() {
		return photo;
	}

	public long getFreq() {
		return freq;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	public CheckIn incrFreq(String url){
		this.freq++;
		synchronized(this){
		if(url != null)
			this.urls.add(url);
		}
		return this;
		
	}
	
	public void setFreq(){
		freq=0;
	}
	
	public void setFrequency(long a){
		freq+=a;
	}
	public ArrayList<String> getUrls() {
		return urls;
	}

	public void setUrls(ArrayList<String> urls) {
		this.urls = urls;
	}

	public void addUrl(String url){
		this.urls.add(url);
	}
	
	public boolean find(String url){
		boolean t=true;
		for(int i=0; i<this.urls.size(); i++){
			if(urls!=null){
				if(urls.get(i).equals(url) && !(urls.get(i).equals("Not exists")))
					return false;
			}
		}
		return t;
	}
	
	
	public String toString(){
		return "Poi is " + poi + "\nPoi Name is " + poiname + "\nFrequency of check is " + freq + "\nAnd this CheckIn has " + (urls.size()-1) + " photos\n";
	}
}
