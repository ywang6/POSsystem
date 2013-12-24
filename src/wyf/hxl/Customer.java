package wyf.hxl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
public class Customer extends JPanel implements ActionListener
{
	private JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	private JPanel jpt=new JPanel();
	String[] str1=new String[6];
	DataBase db;
	String sql;
	private JLabel[] jlArray=
	{
		new JLabel("客户资料"),
		new JLabel("  客  户  ID"),
		new JLabel("  客  户  名"),
		new JLabel("  客户地址"),
		new JLabel("  联  系  人"),
		new JLabel("  联系电话"),
		new JLabel("  备注信息")
	};
	private JTextField[] jtxtArray=new JTextField[]
	{
		new JTextField(),
		new JTextField(),
		new JTextField(),
		new JTextField(),		
		new JTextField(),
		new JTextField()
	};
	//设置JButton按钮的文本
	private JButton[] jbArray=
	{
	    new JButton("添加客户"),
	    new JButton("删除客户"),
	    new JButton("修改客户"),
	    new JButton("查询客户")
	};
	//创建标题
	Vector<String> head = new Vector<String>();
	{
		head.add("客户ID");
		head.add("客户名");
		head.add("客户地址");
		head.add("联系人");
		head.add("联系电话");
		head.add("备注信息");
	}
	//在下部子窗口中设置表格
	Vector<Vector> data=new Vector<Vector>();
    //创建表格模型
    DefaultTableModel dtm=new DefaultTableModel(data,head);
    //创建Jtable对象
	JTable jt=new JTable(dtm);
	//将JTable封装到滚动窗格
	JScrollPane jspn=new JScrollPane(jt);
	public Customer()
	{
		this.setLayout(new GridLayout(1,1));
		//设置面板的上部分为空布局管理器
		jpt.setLayout(null);
		//设置jspt中分割条的初始位置
		jsp.setDividerLocation(155);
		//设置分隔条的宽度
		jsp.setDividerSize(4);
		jsp.setTopComponent(jpt);
		jsp.setBottomComponent(jspn);
		for(int i=0;i<6;i++)
		{
			jpt.add(jtxtArray[i]);
		}
		for(int i=0;i<6;i++)
		{
			jpt.add(jlArray[i]);
			jpt.add(jlArray[6]);
			if(i==0)
			{
				jlArray[i].setBounds(5,5,100,20);
			}
			if(i>=0&&i<2)
			{
			    jlArray[i+1].setBounds(10,40+30*i,80,20);
			    jtxtArray[i].setBounds(90,40+30*i,150,20);
			    jtxtArray[i].addActionListener(this);
			}
			else if(i>=2&&i<4)
			{
				jlArray[i+1].setBounds(240,40+30*(i-2),80,20);
				jtxtArray[i].setBounds(320,40+30*(i-2),150,20);
				jtxtArray[i].addActionListener(this);
			}
			else if(i>=4&&i<6)
			{
				jlArray[i+1].setBounds(470,40+30*(i-4),80,20);
				jtxtArray[i].setBounds(550,40+30*(i-4),150,20);
				jtxtArray[i].addActionListener(this);	
			}
		}
		this.add(jsp);
		//设置下部子窗格
    	jsp.setBottomComponent(jspn);
		//将JButton添加进jpt
		for(int i=0;i<4;i++)
		{
			jpt.add(jbArray[i]);
			jbArray[i].setBounds(100+150*i,110,100,25);
		}
		//设置监听器
		for(int i=0;i<4;i++)
		{
			jbArray[i].addActionListener(this);
		}		
		//设置窗体的大小位置及可见性
		this.setBounds(5,5,600,500);
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==jtxtArray[0]){//事件源为"客户ID"文本框
			jtxtArray[1].requestFocus();
		}
		if(e.getSource()==jtxtArray[1]){//事件源为"客户名"文本框
			jtxtArray[2].requestFocus();
		}
		if(e.getSource()==jtxtArray[2]){//事件源为"客户地址"文本框
			jtxtArray[3].requestFocus();
		}
		if(e.getSource()==jtxtArray[3]){//事件源为"联系人"文本框
			jtxtArray[4].requestFocus();
		}  
		if(e.getSource()==jtxtArray[4]){//事件源为"联系电话"文本框
			jtxtArray[5].requestFocus();
		}    
		if(e.getSource()==jbArray[0]){//当点击"添加信息"按钮将执行添加功能
			this.insertCustomer();
			JOptionPane.showMessageDialog(this,	"您已成功添加！！！",
					"消息",JOptionPane.INFORMATION_MESSAGE);//添加成功提示
	        return;	
		}
		if(e.getSource()==jbArray[1]){//当点击"删除信息"按将执行删除功能	
			this.deleteCustomer();
			JOptionPane.showMessageDialog(this,	"您已成功删除！！！",
					"消息",JOptionPane.INFORMATION_MESSAGE);//删除成功提示
	        return;
		}	
		if(e.getSource()==jbArray[2]){//当点击"修改客户信息"按钮将执行修改功能
		    this.updateCustomer();
		    JOptionPane.showMessageDialog(this,	"您已修改成功！！！",
					 "消息",JOptionPane.INFORMATION_MESSAGE);//修改成功提示
	        return;
		}	
		if(e.getSource()==jbArray[3]){//当点击"查询客户信息"按钮将执行查询功能
		    this.selectCustomer();
		}
	}
	public void insertCustomer(){		
		for(int i=0;i<6;i++){//获得文本框中输入的信息
			str1[i]=jtxtArray[i].getText().trim();		
		}
		if(!str1[0].equals("")&&!str1[1].equals("")&&!str1[2].equals("")&&!str1[3].equals("")
		&&!str1[4].equals("")&&!str1[5].equals("")){//如果文本框均不为空执行插入功能
	        sql="insert into Customer(CustomerID,CustomerName,Address,LinkMan,"
	                +"Tel,CustomerRemark) values('"+str1[0]+"','"+str1[1]+"','" 
	                + str1[2] + "','"+str1[3]+"','"+str1[4]+"','"+str1[5]+"')";
			db=new DataBase();
			db.updateDb(sql);//调用数据库插入方法
			Vector<String> v = new Vector<String>();
		    for(int i=0;i<=5;i++){//将每列添加到临时数组v
				v.add(str1[i]);
				if(i<6){jtxtArray[i].setText("");}	
		    }
		    data.add(v);//将临时数组中信息添加到表格里		
			dtm.setDataVector(data,head);//更新table	
			jt.updateUI();
			jt.repaint();
			return;
		}
		else{//信息不为空提示
			JOptionPane.showMessageDialog(this,	"顾客信息不能为空！！！",
						        "消息",JOptionPane.INFORMATION_MESSAGE);
	        return;	
		}
	}
	public void deleteCustomer(){
		String cd=jtxtArray[0].getText().trim();
		if(cd.equals("")){//当客户ID文本框输入为空进行提示
			JOptionPane.showMessageDialog(this,	"顾客编号不能为空！！！",
						        "消息",JOptionPane.INFORMATION_MESSAGE);
			return;			
		}
		sql="select * from Customer where CustomerID='"+cd+"'";//在数据库搜索客户信息
		db=new DataBase();
		db.selectDb(sql);
		try{
			if(db.rs.next()){//获得结果集
		    	sql="delete from Customer where CustomerID='"+cd+"'";//执行删除功能
		    	db=new DataBase();
	        	db.updateDb(sql);
	        	JOptionPane.showMessageDialog(this,	"成功删除该顾客信息记录！！",
						 "消息",JOptionPane.INFORMATION_MESSAGE);//删除成功提示
	        	return;	
			}
			else{//结果集为空，提示
				JOptionPane.showMessageDialog(this,	"没有该顾客！",
							        "消息",JOptionPane.INFORMATION_MESSAGE);
				return;			        
			}
		}
	    catch(Exception e){e.printStackTrace();}	
	}
	public void updateCustomer(){
		String str[]=new String[6];
		int row=jt.getSelectedRow();//选择表格的一行
		if(row>=0){//选中表格中的一行
			for(int i=0;i<6;i++){//得到所选行的信息
				str[i]=jt.getValueAt(row,i).toString().trim();
			}
			sql="update Customer set CustomerName='"+str[1]+"',Address='"
			     +str[2]+"',LinkMan='"+str[3]+"',Tel='"+str[4]+"',CustomerRemark='"
			     +str[5] +"' where CustomerID='"+str[0]+"'";//	更新客户信息     
			db=new DataBase();
			db.updateDb(sql);//调用数据库的更新方法
			JOptionPane.showMessageDialog(this,"修改成功！！",
			          "消息!!",JOptionPane.INFORMATION_MESSAGE);//修改成功提示
			return;								
		}
		if(row==-1){//对用户错误操作的提示
			JOptionPane.showMessageDialog(this,"请单击'查找客户信息',再在表格选中所改行,单击 '修改客户信息' 按钮!",
			                                   "Warning!!",JOptionPane.INFORMATION_MESSAGE);
			return;
		}		
	}
	public void selectCustomer(){
		if(jtxtArray[0].getText().equals("")){//对输入为空的处理
	    	JOptionPane.showMessageDialog(this,"输入不能为空，请重新输入！！！",
			                              "信息",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		//验证客户ID文本框中所输的客户信息是否存在于Product表中
	   	sql="select * from Product where ProductID='"+jtxtArray[0].getText().trim()+"'";
	    db=new DataBase();
		db.selectDb(sql);				   
	     try{//对结果集进行try处理
		     int k=0;
			 Vector<Vector> vtemp = new Vector<Vector>();
			  while(db.rs.next()){//得到结果集
			 	k++;
		       	Vector<String> v = new Vector<String>();
				for(int i=1;i<=6;i++){//顺序达到所搜到的结果中的各项记录
					String str = db.rs.getString(i).trim();
					str = new String(str.getBytes("ISO-8859-1"),"gb2312");//转码
					v.add(str);	
				}
				vtemp.add(v);//更新结果框中的内容
			   	
			 }
			  if(k==0){//若Book表中没有该书号，则输出提示对话框
			 	 JOptionPane.showMessageDialog(this,	"没有您要查找的内容",
				                 "消息",JOptionPane.INFORMATION_MESSAGE);
			 }
			dtm.setDataVector(vtemp,head);//更新表格
			jt.updateUI();
			jt.repaint();				 	
		 }
		 catch(Exception e){e.printStackTrace();}// 捕获异常  
	}
	public static void main(String[]args)
	{
		new Customer();
	}
}