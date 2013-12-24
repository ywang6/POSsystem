package wyf.hxl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
public class NewManager extends JPanel implements ActionListener
{
	JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	private JPanel jpt=new JPanel();
	String[] str1=new String[3];
	String mgNo;
	String sql;
	DataBase db;
	private JLabel[] jlArray=new JLabel[]
	{
		new JLabel("     管  理  员  名"),
		new JLabel("         权       限"),
	      new JLabel("         密      码")   
	};
	private JTextField[] jtxtArray=new JTextField[]
	{
		new JTextField(),
		new JTextField(),
		new JTextField()
	};
	private JButton[] jbArray=new JButton[]
	{
		new JButton("添加管理员"),
		new JButton("删除管理员"),
		new JButton("修改信息"),
		new JButton("查找信息")
	};
	//创建标题
	Vector<String> head=new Vector<String>();
	{
		head.add("管理员名");
		head.add("权限");	
		head.add("密码");	
	}
	//在下部子窗口中设置表格
	Vector<Vector> data=new Vector<Vector>();
    //创建表格模型
    DefaultTableModel dtm=new DefaultTableModel(data,head);
    //创建Jtable对象
	JTable jt=new JTable(dtm);
	//将JTable封装到滚动窗格
	JScrollPane jspn=new JScrollPane(jt);
	public NewManager(String mgNo)
	{
		this.setLayout(new GridLayout(1,1));
		this.mgNo=mgNo;
		//设置面板的上部分为空布局管理器
		jpt.setLayout(null);
		//设置jspt中分割条的初始位置
		jsp.setDividerLocation(110);
		//设置分隔条的宽度
		jsp.setDividerSize(4);
		jsp.setTopComponent(jpt);
		jsp.setBottomComponent(jspn);
		for(int i=0;i<3;i++)
		{
			jpt.add(jtxtArray[i]);
		}
		for(int i=0;i<3;i++)
		{
			jpt.add(jlArray[i]);	
		    jlArray[i].setBounds(15+i*250,20,100,25);
		    jtxtArray[i].setBounds(115+i*250,20,150,25);
		    jtxtArray[i].addActionListener(this);
		}
		this.add(jsp);
		//设置下部子窗格
    	jsp.setBottomComponent(jspn);
    	//将JButton添加进jpt,设置监听器
		for(int i=0;i<4;i++)
		{
			jpt.add(jbArray[i]);
			jbArray[i].setBounds(100+150*i,65,100,25);
			jbArray[i].addActionListener(this);
		}
	    //设置窗体的大小位置及可见性
		this.setBounds(5,5,600,500);
		this.setVisible(true);
	}
	
	public void setFlag(boolean b)
	{
		jbArray[0].setEnabled(b);
		jbArray[1].setEnabled(b);		
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
    	
    	sql="select permitted from manager where mgNo='"+mgNo+"'";
    	db=new DataBase();
		db.selectDb(sql);
		String string="";
		try
		{
			while(db.rs.next())
			{
				string=db.rs.getString(1).trim();				
			}
			int p=0;
			if(string.equals("1"))
			{ p++;
				String jtxt=jtxtArray[0].getText().trim();
				if(jtxt.equals(""))
				{
					JOptionPane.showMessageDialog(this,	"请输入管理员名！！！",
						        "消息",JOptionPane.INFORMATION_MESSAGE);
				    return;
				}
				if(e.getSource()==jbArray[0])
				{
					this.insertManager();
				}
				if(e.getSource()==jbArray[1])
				{
					this.deleteManager();
				}
				if(e.getSource()==jbArray[2])
				{
					this.updateManager();
				}
				if(e.getSource()==jbArray[3])
				{
					this.selectManager();
				}
			}
			if(p==0)
			{
				jtxtArray[0].requestDefaultFocus();
				String jtxt=jtxtArray[0].getText().trim();
				if(jtxt.equals(""))
				{
					JOptionPane.showMessageDialog(this,	"请输入管理员名！！！",
						        "消息",JOptionPane.INFORMATION_MESSAGE);
				    return;
				}
				else if(jtxt.equals(mgNo))
				{
					if(e.getSource()==jbArray[3])
					{//查询管理员名文本框中输入内容是否存在于manager表中
				       	sql="select * from manager where mgNo="+Integer.parseInt(jtxtArray[0].getText().trim());
				        db=new DataBase();
						db.selectDb(sql);
						SearchSell ss=new SearchSell();
					    ss.xianshi();
					}
					if(e.getSource()==jbArray[2])
					{
						String str[]=new String[3];
						int row = jt.getSelectedRow();
						if(row>=0)
						{
							for(int i=0;i<3;i++)
							{
								str[i]=jt.getValueAt(row,i).toString();
							}
							sql="update manager set password='"
							     +str[2]+"' where mgNo="+Integer.parseInt(str[0].trim());
							     
							db=new DataBase();
							int i=db.updateDb(sql);
							if(i==1)
							{
								JOptionPane.showMessageDialog(this,"修改成功！！",
								                                   "消息!!",JOptionPane.INFORMATION_MESSAGE);
								return;											
							}
							else
							{
								JOptionPane.showMessageDialog(this,"请重新操作！！",
	                                   "Warning!!",JOptionPane.INFORMATION_MESSAGE);
								return;											
							}
						}
						else
						{
							JOptionPane.showMessageDialog(this,"请重新操作！！",
                                   "Warning!!",JOptionPane.INFORMATION_MESSAGE);
							return;								
						}	
					}
				}
				else
				{
					JOptionPane.showMessageDialog(this,	"你不可以查看别人的信息,请重新输入！！！",
				        "消息",JOptionPane.INFORMATION_MESSAGE);
				    return;					
				}
			
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		//关闭数据库链接
		db.dbClose();
    }
    public void insertManager()
    {
    		if((jtxtArray[0]!=null)&&(jtxtArray[1]!=null)&&(jtxtArray[2]!=null))
		{
			for(int i=0;i<3;i++)
			{
			    str1[i]=jtxtArray[i].getText().trim();		
			}
			sql="insert into manager(mgNo,Permitted,password) values('"+str1[0]+"','"+str1[1]+"','"
			 +str1[2]+"')";
			db=new DataBase();
			db.updateDb(sql);
			Vector<String> v = new Vector<String>();
		    for(int i=0;i<=2;i++)
		    {		
				//将每列添加到临时数组v
				v.add(str1[i]);
				if(i<3)
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
			JOptionPane.showMessageDialog(this,	"学生信息不能为空！！！",
						        "消息",JOptionPane.INFORMATION_MESSAGE);
						        return;	
		}
    }
    public void deleteManager()
    {
    	String mgNo=jtxtArray[0].getText().trim();
		if(mgNo.equals(""))
		{
			JOptionPane.showMessageDialog(this,	"学号不能为空！！！",
						        "消息",JOptionPane.INFORMATION_MESSAGE);
			return;			
		}
		sql="select permitted from manager where mgNo="+Integer.parseInt(mgNo);
		db=new DataBase();
		db.selectDb(sql);
		String str="";
		try
		{
			while(db.rs.next())
			{
				str=db.rs.getString(1).trim();
			}
		
			if(str.equals("1"))
			{
		    	
	        	JOptionPane.showMessageDialog(this,	"不能删除超级管理员信息记录！！",
							        "警告",JOptionPane.INFORMATION_MESSAGE);
	        	return;	
			}
			else
			{
				sql="delete from manager where mgNo="+Integer.parseInt(mgNo);
		    	db=new DataBase();
	        	db.updateDb(sql);
				//若Record表中有该学号，则输出提示对话框
				JOptionPane.showMessageDialog(this,	"成功删除该管理员信息！！",
							        "消息",JOptionPane.INFORMATION_MESSAGE);
			}
		}
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }	
    }
    public void updateManager()
    {
    	String str[]=new String[3];
		int row = jt.getSelectedRow();
		if(row>=0)
		{
			for(int i=0;i<3;i++)
			{
				str[i]=jt.getValueAt(row,i).toString();
			}
			sql="update manager set mgNo='"+str[0]+"',permitted='"+str[1]+"',password='"
			     +str[2]+"' where mgNo="+Integer.parseInt(str[0].trim());
			     
			db=new DataBase();
			db.updateDb(sql);
			JOptionPane.showMessageDialog(this,"修改成功！！",
			                                   "消息!!",JOptionPane.INFORMATION_MESSAGE);
			return;								
		}
		if(row==-1)
		{
			JOptionPane.showMessageDialog(this,"请点击 '查找' 按钮, 然后在下部的表格里更改,再选中所改行,再点击 '修改信息' 按钮！！",
			                                   "Warning!!",JOptionPane.INFORMATION_MESSAGE);
			return;
		}		
    }
    public void selectManager()
    {
    	if(jtxtArray[0].getText().equals(""))
		{
			JOptionPane.showMessageDialog(this,"输入不能为空，请重新输入！！！",
			                              "信息",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		//查询管理员名文本框中输入内容是否存在于manager表中
       	sql="select * from manager where mgNo="+Integer.parseInt(jtxtArray[0].getText().trim());
        db=new DataBase();
		db.selectDb(sql);				   
		SearchSell ss=new SearchSell();
	    ss.xianshi();	    	
    }
    public void manager(){
		//查询管理员名文本框中输入内容是否存在于manager表中
       	sql="select * from manager where mgNo="+Integer.parseInt(jtxtArray[0].getText().trim());
        db=new DataBase();
		db.selectDb(sql);
		SearchSell ss=new SearchSell();
	    ss.xianshi();
	    
				    	
    }
    public static void main(String[]args)
    {
    	new NewManager("wyf");
    }
}