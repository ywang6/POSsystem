package wyf.hxl;
import javax.swing.*;import java.awt.*;
import java.awt.event.*;import javax.swing.table.*;
import java.sql.*;import java.util.*;
import java.util.Date;
public class Sell extends JPanel implements ActionListener{
	private JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	private JPanel jpt=new JPanel();
	String[] str1=new String[10];
    DataBase db;
	String sql;
	private JLabel[] jlArray={
		new JLabel("销售单号"),new JLabel("客户名称"),
		new JLabel("商  品  ID"),new JLabel("商  品  名"),
		new JLabel("商品规格"),new JLabel("计量单位"),
	    new JLabel("商品数量"),new JLabel("商品单价"),
	    new JLabel("   收     款"),new JLabel("销售日期")
	};
	private JLabel jl=new JLabel("销售信息");
	private JTextField[] jtxtArray=new JTextField[]{
		new JTextField(),new JTextField(),
		new JTextField(),new JTextField(),
		new JTextField(),new JTextField(),
		new JTextField(),new JTextField(),
		new JTextField(),new JTextField()
	};
	private JButton[] jbArray={//设置JButton按钮的文本
	    new JButton("销售此商品"),
	    new JButton("清空")
	};
	Vector<String> head = new Vector<String>();
	{//创建标题
		head.add("销售单号");head.add("客户名称");
		head.add("商品ID");head.add("商品名");
		head.add("商品规格");head.add("计量单位");
		head.add("商品数量");head.add("商品单价");
		head.add("收款");head.add("销售日期");
	}
	Vector<Vector> data=new Vector<Vector>(); 
    DefaultTableModel dtm=new DefaultTableModel(data,head);//创建表格模型  
	JTable jt=new JTable(dtm);//创建Jtable对	
	JScrollPane jspn=new JScrollPane(jt);//将JTable封装到滚动窗格
	public Sell(){
		this.setLayout(new GridLayout(1,1));
		//设置面板的上部分为空布局管理器
		jpt.setLayout(null);
		//设置jspt中分割条的初始位置
		jsp.setDividerLocation(160);
		//设置分隔条的宽度
		jsp.setDividerSize(4);
		jsp.setTopComponent(jpt);
		jsp.setBottomComponent(jspn);
		for(int i=0;i<10;i++)
		{
			
			jpt.add(jtxtArray[i]);
			jpt.add(jlArray[i]);
			if(i<5)
			{
			    jlArray[i].setBounds(15+i*140,35,60,20);
			    jtxtArray[i].setBounds(75+i*140,35,80,20);
			    jtxtArray[i].addActionListener(this);
			}
			else 
			{
				jlArray[i].setBounds(15+(i-5)*140,75,60,20);
				jtxtArray[i].setBounds(75+(i-5)*140,75,80,20);
				jtxtArray[i].addActionListener(this);
			}
		}
		jpt.add(jl);
		jl.setBounds(5,5,100,25);
		this.add(jsp);
		//设置下部子窗格
    	jsp.setBottomComponent(jspn);
		//将JButton添加进jpt,设置监听器
		for(int i=0;i<2;i++)
		{
			jpt.add(jbArray[i]);
			jbArray[i].setBounds(250+150*i,115,120,25);
			jbArray[i].addActionListener(this);
		}		
		//设置窗体的大小位置及可见性
		this.setBounds(5,5,600,500);
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==jtxtArray[0]){//事件源为"销售单号"文本框 
    		jtxtArray[1].requestFocus();
    	}
    	if(e.getSource()==jtxtArray[1]){//事件源为"客户名称"文本框 
    		jtxtArray[2].requestFocus();
    	}
    	if(e.getSource()==jtxtArray[2]){//事件源为"商品ID"文本框 
    		jtxtArray[3].requestFocus();
    	}
    	if(e.getSource()==jtxtArray[3]){//事件源为"商品名"文本框 
    		jtxtArray[4].requestFocus();
    	}
    	if(e.getSource()==jtxtArray[4]){//事件源为"商品规格"文本框 
    		jtxtArray[5].requestFocus();
    	}   
    	if(e.getSource()==jtxtArray[5]){//事件源为"计量单位"文本框 
    		jtxtArray[6].requestFocus();
    	}
    	if(e.getSource()==jtxtArray[6]){//事件源为"商品数量"文本框 
    		jtxtArray[7].requestFocus();
    	} 
    	if(e.getSource()==jtxtArray[7]){//事件源为"商品单价"文本框 
    		jtxtArray[8].requestFocus();
    	}
    	if(e.getSource()==jtxtArray[8]){//事件源为"收款"文本框 
    		jtxtArray[9].requestFocus();
    	}   
		if(e.getSource()==jbArray[0]){//当点击"销售此商品"按钮将执行添加功能
			this.insertSell();
		}
	   
		if(e.getSource()==jbArray[1]){ //当点击"清空"按钮是将执行清空文本框，并提示输入新货物功能	
			for(int i=0;i<10;i++){//清空文本框
				jtxtArray[i].setText("");
			}
			JOptionPane.showMessageDialog(this,"请输入新货物信息！！","提示",
				     JOptionPane.INFORMATION_MESSAGE);//提示输入新货物
			return;
		}
	}
	public void insertSell(){
		for(int i=0;i<10;i++){//得到文本框所输入信息
				    str1[i]=jtxtArray[i].getText().trim();
		} 
		if(str1[0].equals("")&&str1[1].equals("")&&str1[2].equals("")&&str1[3].equals("")
		        &&str1[4].equals("")&&str1[5].equals("")&&str1[6].equals("")&&str1[7].equals("")
		        &&str1[8].equals("")&&str1[9].equals("")){//当输入为空进行提示
			JOptionPane.showMessageDialog(this,"请输入货物信息！！","提示",
				     JOptionPane.INFORMATION_MESSAGE);
			return;
		}   
		sql="select ProductID from Stock where ProductID='"+str1[2]+"'";//从Stock表中搜索是否有该商品
		db=new DataBase();
		db.selectDb(sql);
		try{		
			if(db.rs.next()){//从Customer表中搜索客户名
				sql="select CustomerName from Customer where CustomerNAme='"+str1[1]+"'";
		        db=new DataBase();
	        	db.selectDb(sql);
	        	boolean flag = db.rs.next();//用布尔值代替结果集是否为空
	        	db.rs.close();
	        	if(flag){//如果结果集不为空，执行插入操作
	        	    if(!str1[0].equals("")&&!str1[1].equals("")&&!str1[2].equals("")&&!str1[3].equals("")
			        &&!str1[4].equals("")&&!str1[5].equals("")&&!str1[6].equals("")&&!str1[7].equals("")
			        &&!str1[8].equals("")&&!str1[9].equals("")){
					    sql="insert into Sell(SellID,CustomerName,ProductID,ProductName,Spec,Unit,"
					        +"Quantity, UnitPrice,Payment,SellDate) values('"
					        +str1[0]+"','"+str1[1]+"','" + str1[2]
					        +"','"+str1[3]+"','"+str1[4]+"','"+str1[5]+"','"+str1[6]+"','"+str1[7]
					        +"','"+str1[8]+"','"+str1[9]+"')";//执行插入
						db=new DataBase();
						db.updateDb(sql);
						Vector<String> v = new Vector<String>();
					    for(int i=0;i<=9;i++){//将每列添加到临时数组v
							v.add(str1[i]);	
					    }
					    data.add(v);//将数据添加进表格中
						dtm.setDataVector(data,head);//更新table	
						jt.updateUI();
						jt.repaint();
						JOptionPane.showMessageDialog(this,"您已经成功销售该货物信息！！","提示",
					             JOptionPane.INFORMATION_MESSAGE);//成功销售提示
					    sql="select Quantity from Stock where ProductID='"+str1[2]+"'";//查询库中该商品的数量
				        db=new DataBase();
			        	db.selectDb(sql);
			        	db.rs.next();
					    int i=Integer.parseInt(db.rs.getString(1))-Integer.parseInt(str1[6]);//对该商品的数量查询赋值
					    String in=Integer.toString(i);//
					    sql="update Stock set Quantity='"+in
			               +"' where ProductID='"+str1[2]+"'";//更新库中该商品的数量
						db=new DataBase();
						db.updateDb(sql);
						return;
		    	    }	
			    }
			    else{//结果集为空提示
					JOptionPane.showMessageDialog(this,"该货物缺货！！","提示",
					     JOptionPane.INFORMATION_MESSAGE);
			    	return;
				}		
	     	}
			 else{//操作错误提示
					JOptionPane.showMessageDialog(this,"该货物缺货！！","提示",
					     JOptionPane.INFORMATION_MESSAGE);
			    	return;
			}			
	    }
	    catch(Exception e){e.printStackTrace();}//捕获异常    
	}
	public static void main(String[]args)
	{
		new Sell();
	}
}