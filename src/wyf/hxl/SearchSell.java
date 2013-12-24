package wyf.hxl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
public class SearchSell extends JPanel implements ActionListener
{
	private JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	private JPanel jpt=new JPanel();
	DataBase db;
	String sql;
	private JLabel jl=new JLabel("查询条件");
	private JRadioButton[] jrb=new JRadioButton[]
	{
		new JRadioButton("        销售单号",true),
		new JRadioButton("        客       户"),
		new JRadioButton("        销售日期"),
		new JRadioButton("        商品名称")
	};
	private ButtonGroup bg=new ButtonGroup();
	//设置JButton按钮的文本
	private JButton jb= new JButton("查询信息");
	Vector<String> head = new Vector<String>();
	{//创建标题
		head.add("销售单号");
		head.add("客户名称");
		head.add("商品ID");
		head.add("商品名");
		head.add("商品规格");
		head.add("计量单位");
		head.add("商品数量");
		head.add("商品单价");
		head.add("收款");
		head.add("销售日期");
	}
	private JTextField[] jtxtArray=new JTextField[]
	{//创建文本框
		new JTextField(),
		new JTextField(),
		new JTextField(),
		new JTextField()
	};
	//在下部子窗口中设置表格
	Vector<Vector> data=new Vector<Vector>();
    //创建表格模型
    DefaultTableModel dtm=new DefaultTableModel(data,head);
    //创建Jtable对象
	JTable jt=new JTable(dtm);
	//将JTable封装到滚动窗格
	JScrollPane jspn=new JScrollPane(jt);
	public SearchSell()
	{
		this.setLayout(new GridLayout(1,1));
		//设置面板的上部分为空布局管理器
		jpt.setLayout(null);
		//设置jspt中分割条的初始位置
		jsp.setDividerLocation(140);
		//设置分隔条的宽度
		jsp.setDividerSize(4);
		jsp.setTopComponent(jpt);
		jsp.setBottomComponent(jspn);
		jpt.add(jl);
		jl.setBounds(5,5,100,20);
		for(int i=0;i<4;i++)
		{
			bg.add(jrb[i]);//将单选按钮添加进按钮组
			jpt.add(jrb[i]);//将单选按钮添加到上部窗体
		    jpt.add(jtxtArray[i]);
		    jrb[i].addActionListener(this);//为文本框添加监听器
			if(i>=0&&i<2)
			{//初始化第1行单选按钮、文本框，并为文本框添加监听器
			    jrb[i].setBounds(15,30+30*i,120,20);
			    jtxtArray[i].setBounds(135,30+30*i,150,20);
			    jtxtArray[i].addActionListener(this);
			}
			else if(i>=2&&i<4)
			{//初始化第2行单选按钮、文本框，并为文本框添加监听器
				jrb[i].setBounds(285,30+30*(i-2),120,20);
				jtxtArray[i].setBounds(405,30+30*(i-2),150,20);
				jtxtArray[i].addActionListener(this);
			}
		}
			
		this.add(jsp);
		//设置下部子窗格
    	jsp.setBottomComponent(jspn);
		//将JButton添加进jpt，设置监听器
		jpt.add(jb);
		jb.setBounds(200,100,140,25);
		jb.addActionListener(this);		
		//设置窗体的大小位置及可见性
		this.setBounds(5,5,600,500);
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==jrb[0]){//当选择"销售单号"单选按钮，鼠标焦点到达相应文本框
			jtxtArray[0].requestFocus();
		}
		if(e.getSource()==jrb[1]){//当选择"客户"单选按钮，鼠标焦点到达相应文本框
			jtxtArray[1].requestFocus();
		}
		if(e.getSource()==jrb[2]){//当选择"销售日期"单选按钮，鼠标焦点到达相应文本框
			jtxtArray[2].requestFocus();
		} 
		if(e.getSource()==jrb[3]){//当选择"商品名称"单选按钮，鼠标焦点到达相应文本框
			jtxtArray[3].requestFocus();
		}  
		if(e.getSource()==jb){//当点击"查询信息"按钮将执行查询功能
	        if(jrb[0].isSelected()){//事件源为"销售单号"单选按钮
	        	String str=jtxtArray[0].getText().trim();
	        	sql="select * from Sell where SellID='"+str+"'";//按销售单号查询
	        	db=new DataBase();
		        db.selectDb(sql);
			    this.xianshi();//调用显示方法
	        }
	        if(jrb[1].isSelected()){//"客户"单选按钮
	        	String str=jtxtArray[1].getText().trim();
	        	sql="select * from Customer where CustomerID='"+str+"'";//按客户查询
	        	db=new DataBase();
		        db.selectDb(sql);
			    this.xianshi();//调用显示方法
	        }
	        if(jrb[2].isSelected()){//"销售日期"单选按钮
	        	String str0=jtxtArray[2].getText().trim();
	        	sql="select * from Sell where SellDate='"+str0+"'";//按销售日期查询
	            db=new DataBase();
			    db.selectDb(sql);
	            this.xianshi();//调用显示方法
	        }
	        if(jrb[3].isSelected()){//"商品名称"单选按钮
	        	String str=jtxtArray[3].getText().trim();
	        	sql="select * from Sell where ProductName='"+str+"'";//按商品名称查询
	        	db=new DataBase();
		        db.selectDb(sql);
			    this.xianshi();//调用显示方法
	        }
		}
	}
	public void xianshi(){
	      try{
		       if(db.rs.next()){//获得结果集		        	
		        	Vector <String> v=new Vector <String>();
			        for(int i=0;i<10;i++){//对得到的数据进行转码
			            String str1=new String(db.rs.getString(i+1).getBytes("ISO-8859-1"),"gb2312");
			        	v.add(str1);//把从结果集获得的数据添加到临时数组
			        }
			        data.add(v);//将临时数组中的数据添加进表格							
					dtm.setDataVector(data,head);//更新table	
					jt.updateUI();
					jt.repaint();
					JOptionPane.showMessageDialog(this,"您已经成功查询该商品销售信息！！","提示",
				            JOptionPane.INFORMATION_MESSAGE);//查询成功提示
				    for(int i=0;i<4;i++){//清空文本框
				     	jtxtArray[i].setText("");
				    }
	                return;
		        }
		        else{//查询失败提示
		        	JOptionPane.showMessageDialog(this,"您所查询的商品销售信息不存在！！","提示",
				     JOptionPane.INFORMATION_MESSAGE);
					return;
		        }
		  }
	      catch(Exception ep){ep.printStackTrace();}//捕获异常
	}
	public static void main(String[]args)
	{
		new SearchSell();
	}
}