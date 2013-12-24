package wyf.hxl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
public class Feeder extends JPanel implements ActionListener
{
	private JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	private JPanel jpt=new JPanel();
	DataBase db;
	String sql;
	String[] str1=new String[6];
	private JLabel[] jlArray=
	{
		new JLabel("供应商资料"),
		new JLabel("  供 应 商 ID"),
		new JLabel("  供 应 商 名"),
		new JLabel("  供应商地址"),
		new JLabel("  联  系  人"),
		new JLabel("  联 系 电 话"),
		new JLabel("  备 注 信 息")
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
	    new JButton("添加供应商"),
	    new JButton("删除供应商"),
	    new JButton("修改供应商"),
	    new JButton("查询供应商")
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
	public Feeder()
	{
		this.setLayout(new GridLayout(1,1));
		//设置面板的上部分为空布局管理器
		jpt.setLayout(null);
		//设置jspt中分割条的初始位置
		jsp.setDividerLocation(150);
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
			if(i==0)
			{
				jlArray[i].setBounds(5,5,100,20);
			}
			if(i>=0&&i<2)
			{
			    jlArray[i+1].setBounds(15,40+30*i,80,20);
			    jtxtArray[i].setBounds(95,40+30*i,150,20);
			    jtxtArray[i].addActionListener(this);
			}
			else if(i>=2&&i<4)
			{
				jlArray[i+1].setBounds(245,40+30*(i-2),80,20);
				jtxtArray[i].setBounds(325,40+30*(i-2),150,20);
				jtxtArray[i].addActionListener(this);
			}
			else if(i>=4&&i<6)
			{
				jlArray[i+1].setBounds(475,40+30*(i-4),80,20);
				jtxtArray[i].setBounds(555,40+30*(i-4),150,20);
				jtxtArray[i].addActionListener(this);	
			}
		}
		jpt.add(jlArray[6]);
		this.add(jsp);
		//设置下部子窗格
    	jsp.setBottomComponent(jspn);
		//将JButton添加进jpt
		for(int i=0;i<4;i++)
		{
			jpt.add(jbArray[i]);
			jbArray[i].setBounds(100+150*i,110,110,25);
			jbArray[i].addActionListener(this);
		}	
		//设置窗体的大小位置及可见性
		this.setBounds(5,5,700,600);
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==jtxtArray[0])
    	{
    		jtxtArray[1].requestFocus();
    	}
    	if(e.getSource()==jtxtArray[1])
    	{
    		jtxtArray[2].requestFocus();
    	}
    	if(e.getSource()==jtxtArray[2])
    	{
    		jtxtArray[3].requestFocus();
    	}
    	if(e.getSource()==jtxtArray[3])
    	{
    		jtxtArray[4].requestFocus();
    	}  
    	if(e.getSource()==jtxtArray[4])
    	{
    		jtxtArray[5].requestFocus();
    	} 
	    //当点击"添加信息"按钮将执行添加功能
		if(e.getSource()==jbArray[0])
		{
			this.insertFeeder();
			JOptionPane.showMessageDialog(this,	"添加成功！！！",
						        "消息",JOptionPane.INFORMATION_MESSAGE);
            return;
		}//当点击"删除信息"按钮将执行删除功能	
		if(e.getSource()==jbArray[1])
		{
			this.deleteFeeder();
			JOptionPane.showMessageDialog(this,	"删除成功！！！",
						        "消息",JOptionPane.INFORMATION_MESSAGE);
            return;
		}
		
		if(e.getSource()==jbArray[2])
		{
			this.updateFeeder();
			JOptionPane.showMessageDialog(this,	"修改成功！！！",
						        "消息",JOptionPane.INFORMATION_MESSAGE);
            return;
		}
		//当点击"查询经销商信息"按钮将执行修改功能
		if(e.getSource()==jbArray[3])
		{
		    this.selectFeeder();
		    JOptionPane.showMessageDialog(this,	"查询成功！！！",
						        "消息",JOptionPane.INFORMATION_MESSAGE);
            return;
		}
	}
		public void insertFeeder()
	{		
		for(int i=0;i<6;i++)
		{
			str1[i]=jtxtArray[i].getText().trim();		
		}
		if(!str1[0].equals("")&&!str1[1].equals("")&&!str1[2].equals("")&&!str1[3].equals("")
		   &&!str1[4].equals("")&&!str1[5].equals(""))
		{
            sql="insert into Feeder(FeederID,FeederName,Address,LinkMan,Tel,FeedRemark) values('"
			    +str1[0]+"','"+str1[1]+"','" + str1[2] + "','"+str1[3]+"','"+str1[4]+"','"+str1[5]+"')";
			db=new DataBase();
			db.updateDb(sql);
			Vector<String> v = new Vector<String>();
		    for(int i=0;i<=5;i++)
		    {		
				//将每列添加到临时数组v
				v.add(str1[i]);
				if(i<6)
				{
					jtxtArray[i].setText("");
				}	
		    }
		    data.add(v);
					
			//更新table	
			dtm.setDataVector(data,head);
			jt.updateUI();
			jt.repaint();
			return;
		}
		else
		{
			JOptionPane.showMessageDialog(this,	"经销商信息不能为空！！！",
						        "消息",JOptionPane.INFORMATION_MESSAGE);
            return;	
		}
	}
	public void deleteFeeder()
	{
		String cd=jtxtArray[0].getText().trim();
		if(cd.equals(""))
		{
			JOptionPane.showMessageDialog(this,	"经销商编号不能为空！！！",
						        "消息",JOptionPane.INFORMATION_MESSAGE);
			return;			
		}
		sql="select * from Feeder where FeederID='"+cd+"'";
		db=new DataBase();
		db.selectDb(sql);
		try
		{
			if(db.rs.next())
			{
		    	sql="delete from Feeder where FeederID='"+cd+"'";
		    	db=new DataBase();
	        	db.updateDb(sql);
	        	JOptionPane.showMessageDialog(this,	"成功删除该经销商信息记录！！",
							        "消息",JOptionPane.INFORMATION_MESSAGE);
	        	return;	
			}
			else
			{
				JOptionPane.showMessageDialog(this,	"没有该经销商信息！",
							        "消息",JOptionPane.INFORMATION_MESSAGE);
				return;			        
			}
		}
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }	
	}
	public void updateFeeder()
	{
		String str[]=new String[6];
		int row = jt.getSelectedRow();
		if(row>=0)
		{System.out.println(sql);
			for(int i=0;i<6;i++)
			{
				str[i]=jt.getValueAt(row,i).toString().trim();
			}
			sql="update Feeder set FeederName='"+str[1]+"',Address='"
			     +str[2]+"',LinkMan='"+str[3]+"',Tel='"+str[4]+"',FeedRemark='"
			     +str[5] +"' where FeederID='"+str[0]+"'";
		System.out.println(sql);	     
			db=new DataBase();
			db.updateDb(sql);
			JOptionPane.showMessageDialog(this,"修改成功！！",
			                                   "消息!!",JOptionPane.INFORMATION_MESSAGE);
			return;								
		}
		if(row==-1)
		{
			JOptionPane.showMessageDialog(this,"请点击'查找经销商信息',再在下部表格选中所改行,点击 '修改经销商信息' 按钮!",
			                                   "Warning!!",JOptionPane.INFORMATION_MESSAGE);
			return;
		}		
	}
	public void selectFeeder()
	{
		if(jtxtArray[0].getText().equals(""))
		{
        	JOptionPane.showMessageDialog(this,"输入不能为空，请重新输入！！！",
			                              "信息",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		//查询货品号文本中所输号是否存在于Feeder表中
       	sql="select * from Feeder where FeederID='"+jtxtArray[0].getText().trim()+"'";
        db=new DataBase();
		db.selectDb(sql);				   
	     try
		 {
		     int k=0;
			 Vector<Vector> vtemp = new Vector<Vector>();
			  while(db.rs.next())
			 {
			 	k++;
		       	Vector<String> v = new Vector<String>();
				//顺序达到所搜到的结果中的各项记录
				for(int i=1;i<=6;i++)
				{
					String str = db.rs.getString(i).trim();
					str = new String(str.getBytes("ISO-8859-1"),"gb2312");
					v.add(str);	
				}
				//更新结果框中的内容
				vtemp.add(v);
			   	
			 }
			  if(k==0)
		   	 {
			 	 //若Book表中没有该书号，则输出提示对话框
			 	 JOptionPane.showMessageDialog(this,	"没有您要查找的内容",
				                 "消息",JOptionPane.INFORMATION_MESSAGE);
			 }
			dtm.setDataVector(vtemp,head);
			jt.updateUI();
			jt.repaint();				 	
		 }
		 catch(Exception e)
		 {
		 	e.printStackTrace();
		 }	    
	}
	public static void main(String[]args)
	{
		new Feeder();
	}
}