package wyf.hxl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
public class OtherStorage extends JPanel implements ActionListener{
	DataBase db;
	String sql;//声明sql变量
	private JLabel[] jlArray={//声明标签，并为其指定文本
		new JLabel("对方名称"),
		new JLabel("对方ID"),
		new JLabel("变动日期"),
		new JLabel("货品名称"),
		new JLabel("变动名称"),
		new JLabel("变动数量"),
		new JLabel("库存变动信息")
	};
	private JTextField[] jtxtArray=new JTextField[]{//声明文本框数组
		new JTextField(),
		new JTextField(),
		new JTextField(),
		new JTextField(),
		new JTextField(),
		new JTextField()
	};
	private JButton jb=new JButton("确定保存");//设置JButton按钮的文本
	public OtherStorage(){
		this.setLayout(null);//设置面板的布局管理器为空布局管理器
		for(int i=0;i<6;i++){
			this.add(jlArray[i]);//将标签添加到窗体
			this.add(jtxtArray[i]);//将文本框添加到窗体
			jtxtArray[i].addActionListener(this);//为文本框添加事件监听器 
			if(i<3){//设置第一列标签及文本框的大小位置
				jlArray[i].setBounds(50,30+i*30,80,20);
		        jtxtArray[i].setBounds(130,30+i*30,120,20);
			}
			else{//设置第二列标签及文本框的大小位置
				jlArray[i].setBounds(255,30+(i-3)*30,80,20);
		        jtxtArray[i].setBounds(335,30+(i-3)*30,120,20);
			}
		}
		this.add(jlArray[6]);//把标签添加到窗体
		jlArray[6].setBounds(5,5,120,20);//设置标签的大小位置		
		this.add(jb);//将JButton添加进jpt
		jb.setBounds(200,120,140,25);
		jb.addActionListener(this);//设置监听器;
		//设置窗体的大小位置及可见性
		this.setBounds(5,5,500,300);
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==jtxtArray[0]){//事件源为"对方名称"文本框鼠标焦点将跳转到"对方ID"文本框
			jtxtArray[1].requestFocus();
		}
		if(e.getSource()==jtxtArray[1]){//事件源为"对方ID"文本框鼠标焦点将跳转到"变动日期"文本框
			jtxtArray[2].requestFocus();
		}
		if(e.getSource()==jtxtArray[2]){//事件源为"变动日期"文本框鼠标焦点将跳转到"货品名称"文本框
			jtxtArray[3].requestFocus();
		}
		if(e.getSource()==jtxtArray[3]){//事件源为"货品名称"文本框鼠标焦点将跳转到"变动名称"文本框
			jtxtArray[4].requestFocus();
		}
		if(e.getSource()==jtxtArray[4]){//事件源为"变动名称"文本框鼠标焦点将跳转到"变动数量"文本框
			jtxtArray[5].requestFocus();
		} 
		if(e.getSource()==jb){//当点击"添加信息"按钮是将执行添加功能
			String[] str=new String[6];
			for(int i=0;i<6;i++){//获得文本框输入的内容
				str[i]=jtxtArray[i].getText().trim();
			}
			if(str[0].equals("")&&str[1].equals("")&&str[2].equals("")&&str[3].equals("")&&
			   str[4].equals("")&&str[5].equals("")){//文本框输入为空进行提示
			   	JOptionPane.showConfirmDialog(this,"请将以下六条记录填写完整,再进行更改!!",
								           "提示",JOptionPane.INFORMATION_MESSAGE);
								return;   
			}
			try{
				sql="select Quantity from Sell where ProductName='"+str[3]+"'";//从销售信息表中查询商品数量
				db=new DataBase();
				db.selectDb(sql);
				boolean flag=db.rs.next();//将结果集是否为空定义为布尔型变量
				String str0=db.rs.getString(1);//取得结果集的第一条信息
		    	db.rs.close();
				if(flag){//当结果集不为空
					int i=Integer.parseInt(str0)-Integer.parseInt(str[5]);//计算变动后该商品的数量
					String in=Integer.toString(i);
					sql="select Quantity from Stock where ProductName='"+str[3]+"'";//搜索该商品的库存数量
				    DataBase db=new DataBase();
				    db.selectDb(sql);
					if(db.rs.next()){//获得该商品的库存数量 
					 	String str2=db.rs.getString(1).trim();
					    int l=Integer.parseInt(str2)+Integer.parseInt(str[5]);//重新计算该商品的库存数
					    String ln=Integer.toString(l);                   
						if(str[4].equals("客户退货")){//如果是客户退货的情况
							if(i==0){//当库存为0，执行事务处理
								String[] sql=new String[2];	
								sql[0]="delete from Sell where ProductName='"+str[3]+"'";
								sql[1]="update Stock set Quantity='"+ln+"' where ProductName='"+str[3]+"'";
							    db=new DataBase();
								db.batchSql(sql);
							}
							if(i>0){//当库存>0，执行事务处理
								String[] sql=new String[2];	
								sql[0]="update Sell set Quantity='"+in+"' where ProductName='"+str[3]+"'";
								sql[1]="update Stock set Quantity='"+ln+"' where ProductName='"+str[3]+"'";
							    db=new DataBase();
								db.batchSql(sql);
							}
							if(i<0){//当库存<0，提示错误信息
								JOptionPane.showConfirmDialog(this,"错误,您没有购买这么多该货物,请核实后再退货!!",
								           "提示",JOptionPane.INFORMATION_MESSAGE);
								return;
							}
						}
						else if(str[4].equals("向供销商退货")){//当本商场需要向供应商退货的情况
							if(i>=0){//当库存>0则更新库存数量
								sql="update Stock set Quantity='"+ln+"' where ProductName='"+str[3]+"'";	
							}
						    else{//当库存数<0,进行错误提示
						    	JOptionPane.showConfirmDialog(this,"错误,库房没有这么多该货物,请核实后再退货!!",
								           "提示",JOptionPane.INFORMATION_MESSAGE);
								return;
						    }
						}
					}
				}
			}
			catch(Exception eee){eee.printStackTrace();}//捕获异常	
		}
	}
	public static void main(String[]args)
	{
		new OtherStorage();
	}
}