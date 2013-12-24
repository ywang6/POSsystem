package wyf.hxl;
import javax.swing.*;
import java.sql.*;
public class DataBase{
	Connection con=null;//声明Connection引用
	Statement stat;
	ResultSet rs;
	int count;
	public DataBase(){
		try{//加载MySQL的驱动类，并创建数据库连接
			Class.forName("org.gjt.mm.mysql.Driver");	
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","");
		 	stat=con.createStatement();//创建Statement对象
		}
		catch(Exception e){e.printStackTrace();}
	}
	public void selectDb(String sql){//声明select方法
		try{
			sql=new String(sql.getBytes(),"ISO-8859-1");//转码
			rs=stat.executeQuery(sql);
		}
		catch(Exception ie){ie.printStackTrace();}
	}	
	public int updateDb(String sql){//声明update方法                  
		try{
			sql=new String(sql.getBytes(),"ISO-8859-1");//转码
			count=stat.executeUpdate(sql);
			con.close();
		}
		catch(Exception ie){ie.printStackTrace();}
		return count;		
	}
	public void dbClose(){//声明close方法		
		try{con.close();}
		catch(Exception e){e.printStackTrace();}	
	}	
	public int[] batchSql(String[] sql){
		int[] im= new int[sql.length];
		try{
			con.setAutoCommit(false);//禁用自动提交，开始一个事务
			for(int i=0;i<sql.length;i++){
				sql[i] = new String(sql[i].getBytes("gb2312"),"ISO-8859-1");
				stat.addBatch(sql[i]);//将SQL语句添加到批处理中
			}
			im=stat.executeBatch();//执行当前批处理中的SQL语句
			con.commit();//将事务提交
			con.setAutoCommit(true);//恢复自动提交
		}
		catch(Exception e){e.printStackTrace();}
		return im;
	}	   	
}