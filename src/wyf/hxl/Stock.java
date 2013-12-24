package wyf.hxl;
import javax.swing.*;import java.awt.*;
import java.awt.event.*;import javax.swing.table.*;
import java.sql.*;import java.util.*;
import java.util.Date;import javax.swing.event.*;
public class Stock extends JPanel implements ActionListener{
	private JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT);//分割方向为上下
	private JPanel jpt=new JPanel();//声明面板
	String[] str1=new String[10];
    DataBase db;
	String sql;//声明sql变量
	private JLabel[] jlArray={//声明标签并创建其文本
		new JLabel("  入库单号"),new JLabel("  供应商名"),
		new JLabel("  货  品  ID"),new JLabel("  货  品  名"),
		new JLabel("  货品规格"),new JLabel("  计量单位"),
	    new JLabel("  入库数量"),new JLabel("  入库单价"),
	    new JLabel("     金    额"),new JLabel("  入库日期")
	};
	private JLabel jl=new JLabel("入库信息");//声明标签
	private JTextField[] jtxtArray=new JTextField[]
	{//声明文本框
		new JTextField(),new JTextField(),
		new JTextField(),new JTextField(),
		new JTextField(),new JTextField(),
		new JTextField(),new JTextField(),
		new JTextField(),new JTextField()
	};	
	private JButton[] jbArray={//设置JButton按钮
	    new JButton("添加此商品入库"),
	    new JButton("清空")
	};
	Vector<String> head=new Vector<String>();
	{//创建标题
		head.add("入库单号");head.add("供货商名");
		head.add("货品ID");head.add("货品名称");
		head.add("货品规格");head.add("计量单位");
		head.add("入库数量");head.add("入库单价");
		head.add("金额");head.add("入库时间");
	}	
	Vector<Vector> data=new Vector<Vector>();//在下部子窗口中设置表格
    DefaultTableModel dtm=new DefaultTableModel(data,head);//创建表格模型    
	JTable jt=new JTable(dtm);//创建Jtable对象	
	JScrollPane jspn=new JScrollPane(jt);//将JTable封装到滚动窗格
	public Stock(){
		this.setLayout(new GridLayout(1,1));		
		jpt.setLayout(null);//设置面板的上部分为空布局管理器	
		jsp.setDividerLocation(170);//设置jspt中分割条的初始位置	
		jsp.setDividerSize(4);//设置分隔条的宽度
		jsp.setTopComponent(jpt);
		jsp.setBottomComponent(jspn);
		for(int i=0;i<10;i++){
			jpt.add(jtxtArray[i]);//将文本框添加进窗体上部
			jpt.add(jlArray[i]);//将标签添加进窗体上部
			if(i<5){//设置第1行标签和文本框的大小位置并为文本框注册监听器
			    jlArray[i].setBounds(15+i*140,35,60,20);
			    jtxtArray[i].setBounds(75+i*140,35,80,20);
			    jtxtArray[i].addActionListener(this);
			}
			else {//设置第2行标签和文本框的大小位置并为文本框注册监听器
				jlArray[i].setBounds(15+(i-5)*140,75,60,20);
				jtxtArray[i].setBounds(75+(i-5)*140,75,80,20);
				jtxtArray[i].addActionListener(this);
			}
		}
		jpt.add(jl);//将jl标签添加进面板上部
		jl.setBounds(5,5,100,25);
		this.add(jsp);//将分割窗体添加进窗体	
    	jsp.setBottomComponent(jspn);//设置下部子窗格	
		for(int i=0;i<2;i++){
			jpt.add(jbArray[i]);//将JButton添加进jpt
			jbArray[i].setBounds(240+150*i,125,130,25);//设置大小位置
			jbArray[i].addActionListener(this);//注册监听器
		}	
		this.setBounds(5,5,600,500);//设置窗体的大小位置
		this.setVisible(true);//设置窗体的可见性
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==jtxtArray[0]){//事件源为"入库单号"文本框"供货商名"文本框将获得鼠标焦点
			jtxtArray[1].requestFocus();
		}
		if(e.getSource()==jtxtArray[1]){//事件源为"供货商名"文本框"货品ID"文本框将获得鼠标焦点
			jtxtArray[2].requestFocus();
		}
		if(e.getSource()==jtxtArray[2]){//事件源为"货品ID"文本框"货品名称"文本框将获得鼠标焦点
			jtxtArray[3].requestFocus();
		}
		if(e.getSource()==jtxtArray[3]){//事件源为"货品名称"文本框"货品规格"文本框将获得鼠标焦点
			jtxtArray[4].requestFocus();
		}
		if(e.getSource()==jtxtArray[4]){//事件源为"货品规格"文本框"计量单位"文本框将获得鼠标焦点
			jtxtArray[5].requestFocus();
		}   
		if(e.getSource()==jtxtArray[5]){//事件源为"计量单位"文本框"入库数量"文本框将获得鼠标焦点
			jtxtArray[6].requestFocus();
		}
		if(e.getSource()==jtxtArray[6]){//事件源为"入库数量"文本框"入库单价"文本框将获得鼠标焦点
			jtxtArray[7].requestFocus();
		} 
		if(e.getSource()==jtxtArray[7]){//事件源为"入库单价"文本框"金额"文本框将获得鼠标焦点
			jtxtArray[8].requestFocus();
		}
		if(e.getSource()==jtxtArray[8]){//事件源为"金额"文本框"入库时间"文本框将获得鼠标焦点
			jtxtArray[9].requestFocus();
		}   
		if(e.getSource()==jbArray[0]){//当点击"添加此商品入库"按钮是将执行添加功能
			this.insertStock();
		}
		if(e.getSource()==jbArray[1]){//当点击"清空"按钮是将清空文本框，并提示输入新货物	
			for(int i=0;i<10;i++){//执行清空
				jtxtArray[i].setText("");
			}
			JOptionPane.showMessageDialog(this,"请输入新货物信息！！","提示",
				     JOptionPane.INFORMATION_MESSAGE);//提示输入新信息
			return;
		}
	}
	public void insertStock(){
		for(int i=0;i<10;i++){//获得从文本框所输入的信息
		    str1[i]=jtxtArray[i].getText().trim();
		} 
		if(str1[0].equals("")&&str1[1].equals("")&&str1[2].equals("")&&str1[3].equals("")
		        &&str1[4].equals("")&&str1[5].equals("")&&str1[6].equals("")
		        &&str1[7].equals("")&&str1[8].equals("")&&str1[9].equals("")){//文本框为空进行提示
			JOptionPane.showMessageDialog(this,"请输入货物信息！！","提示",
				     JOptionPane.INFORMATION_MESSAGE);
			return;
		}   
		sql="select ProductID from Product where ProductID='"+str1[2]+"'";//查询该商品
		db=new DataBase();
		db.selectDb(sql);
		try{		
			if(db.rs.next()){//结果集不为空进行供应商名的搜索，已备后用
				sql="select FeederName from Feeder where FeederName='"+str1[1]+"'";
		        db=new DataBase();
	        	db.selectDb(sql);
	        	if(db.rs.next()){//搜索到供应商名
	        	    if(!str1[0].equals("")&&!str1[1].equals("")&&!str1[2].equals("")&&!str1[3].equals("")
			        &&!str1[4].equals("")&&!str1[5].equals("")&&!str1[6].equals("")&&!str1[7].equals("")
			        &&!str1[8].equals("")&&!str1[9].equals("")){//如果文本框输入均不为空则进行插入操作
					    sql="insert into Stock(StockID,FeederID,ProductID,ProductName,Spec,Unit,"
					        +"Quantity, UnitPrice,Payment,Stackdate) values('"
					        +str1[0]+"','"+str1[1]+"','" + str1[2]
					        +"','"+str1[3]+"','"+str1[4]+"','"+str1[5]+"','"+str1[6]+"','"+str1[7]
					        +"','"+str1[8]+"','"+str1[9]+"')";//执行插入操作
						db=new DataBase();
						db.updateDb(sql);
						Vector<String> v = new Vector<String>();
					    for(int i=0;i<=9;i++){//将每列添加到临时数组v
							v.add(str1[i]);	
					    }
					    data.add(v);//将临时数组的信息添加进表格
						dtm.setDataVector(data,head);//更新table	
						jt.updateUI();
						jt.repaint();
						JOptionPane.showMessageDialog(this,"您已经成功添加该货物信息！！","提示",
					     JOptionPane.INFORMATION_MESSAGE);//成功添加提示
						return;
		    	    }	
			    }
			    else{//操作失败提示
					JOptionPane.showMessageDialog(this,"操作失败，请重新操作！！","提示",
					     JOptionPane.INFORMATION_MESSAGE);
			    	return;
				}		
	     	}
			 else{//该商品的供应商不存在进行提示
					JOptionPane.showMessageDialog(this,"您添加的货物本公司暂不需要！！","提示",
					     JOptionPane.INFORMATION_MESSAGE);
			    	return;
				}			
	    }
	    catch(Exception e){e.printStackTrace();}// 捕获异常   
	}
	public static void main(String[]args)
	{
		new Stock();
	}
}