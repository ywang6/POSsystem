package wyf.hxl;
import javax.swing.*;
import java.sql.*;
public class DataBase{
	Connection con=null;//����Connection����
	Statement stat;
	ResultSet rs;
	int count;
	public DataBase(){
		try{//����MySQL�������࣬���������ݿ�����
			Class.forName("org.gjt.mm.mysql.Driver");	
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","");
		 	stat=con.createStatement();//����Statement����
		}
		catch(Exception e){e.printStackTrace();}
	}
	public void selectDb(String sql){//����select����
		try{
			sql=new String(sql.getBytes(),"ISO-8859-1");//ת��
			rs=stat.executeQuery(sql);
		}
		catch(Exception ie){ie.printStackTrace();}
	}	
	public int updateDb(String sql){//����update����                  
		try{
			sql=new String(sql.getBytes(),"ISO-8859-1");//ת��
			count=stat.executeUpdate(sql);
			con.close();
		}
		catch(Exception ie){ie.printStackTrace();}
		return count;		
	}
	public void dbClose(){//����close����		
		try{con.close();}
		catch(Exception e){e.printStackTrace();}	
	}	
	public int[] batchSql(String[] sql){
		int[] im= new int[sql.length];
		try{
			con.setAutoCommit(false);//�����Զ��ύ����ʼһ������
			for(int i=0;i<sql.length;i++){
				sql[i] = new String(sql[i].getBytes("gb2312"),"ISO-8859-1");
				stat.addBatch(sql[i]);//��SQL�����ӵ���������
			}
			im=stat.executeBatch();//ִ�е�ǰ�������е�SQL���
			con.commit();//�������ύ
			con.setAutoCommit(true);//�ָ��Զ��ύ
		}
		catch(Exception e){e.printStackTrace();}
		return im;
	}	   	
}