import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class dbConnect {
	private Connection connect;
	private Statement statement;
	private ResultSet result;
	String query;		
	double x;
	ArrayList<CheckIn> list;
	private CheckIn checkin;
    private String dburl;


	public dbConnect(double firstx, double secondx, double firsty, double secondy){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(dburl);
			statement = connect.createStatement();
			query = "select * from checkins where "
					+ "latitude >= " + firstx + " and latitude < " + secondx + " and longitude >= " + firsty + " and longitude < " + secondy;
			result = statement.executeQuery(query);
			result.first();
			list = new ArrayList<CheckIn>();
			while(result.next()){
				checkin = new CheckIn(result.getLong(1), result.getInt(2), result.getString(3), result.getString(4), result.getString(5), result.getInt(6), result.getDouble(7), result.getDouble(8), result.getTimestamp(9), result.getString(10));
				list.add(checkin);
			}
			if(list.size() == 0){
				list.add(new CheckIn(0, 0, "" , "", "", 0 , 0, 0, null, ""));
			}
		}catch(ClassNotFoundException e){
			System.err.println("Driver Not Found!!!");
		} catch (SQLException e) {
			System.err.println("Cannot Conect to database");
		}
	}
	
	public dbConnect(){
		
	}
	
	public boolean insert(String name,String point,String url){
		String query;
		String poi = null,category = null,idname=null;
		boolean flag=false;
		double lon=0;
		double lat=0;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(dburl);
			statement = connect.createStatement();
			query="select max(id),max(user) from checkins";
			result=statement.executeQuery(query);
			result.first();
			long id=result.getLong(1);
			int user=result.getInt(2);
			id++;
			user++;
			query="select poi,poi_category,poi_category_id,longitude,latitude from checkins where poi_name="+"'"+name+"';";
			result=statement.executeQuery(query);
			result.first();
			while(result.next()){
				poi=result.getString(1);
				category=result.getString(2);
				idname=result.getString(3);
				lon=result.getDouble(4);
				lat=result.getDouble(5);
				flag=true;
			}
			
			StringTokenizer token = new StringTokenizer(point, ",");
			double latitude=Double.parseDouble(token.nextToken().trim());
			double longtitude=Double.parseDouble(token.nextToken().trim());
			query="select now() as date";
			result=statement.executeQuery(query);
			result.first();
			Timestamp x=result.getTimestamp(1);
			if (flag==false){
				query="insert into checkins values('"+id+"','" + user + "','aaa','"+name+"',null,null,'"+latitude+"','"+longtitude+"','"+x+"','"+url+"');";
			}else{
				query="insert into checkins values('"+id+"','" + user + "',"+ "'"+poi+"'"+",'"+name+"',"+ "'"+category+"'" +",'"+idname+"',"+"'"+lat+"','"+lon+"','"+x+"','"+url+"');";
			}
			System.out.println(query);
			statement.executeUpdate(query);
			return true;
			
		}catch(ClassNotFoundException e){
			System.err.println("Driver Not Found!!!");
			return false;
		} catch (SQLException e) {
			System.err.println("Cannot Connect to database");
			e.getLocalizedMessage();
			System.out.println(e);
			return false;
		}
	}
	
	
	public ArrayList<CheckIn> getList(){
		return list;
	}
}
