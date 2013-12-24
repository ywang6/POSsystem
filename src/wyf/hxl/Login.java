package wyf.hxl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
public class Login extends JFrame implements ActionListener
{
	//创建JPanel对象
	private JPanel jp=new JPanel();
    //创建标签组
    private JLabel []jlArray={new JLabel("用户名"),new JLabel("密  码"),new JLabel("")};
    //创建单选按钮
    private JRadioButton[] jrb={new JRadioButton("经理",true),new JRadioButton("管理员")};
    private ButtonGroup bg=new ButtonGroup();
    //创建按钮数组
    private JButton[] jbArray={new JButton("登录"),new JButton("清空")};
    //创建文本框
    private JTextField jtxt=new JTextField("wyf"); 
    //创建密码框
    private JPasswordField jpassword=new JPasswordField("hxl");
    String sql;
	DataBase db;
    public Login()
    {
    	//设置JPanel的布局管理器
    	jp.setLayout(null);
    	//对标签与按钮控件循环处理	
    	for(int i=0;i<2;i++)
        {
        	//设置标签与按钮的大小和位置
        	jlArray[i].setBounds(70,20+i*50,80,26);
        	
        	//将标签和按钮添加进JPanel容器中
        	jp.add(jlArray[i]);
        }
        for(int i=0;i<2;i++)
        {
        	jrb[i].setBounds(100+i*120,130,100,26);
        	jp.add(jrb[i]);
        	bg.add(jrb[i]);
        	jrb[i].addActionListener(this);
        	jbArray[i].setBounds(80+i*120,180,100,26);
        	jp.add(jbArray[i]);	
        	//为按钮注册动作事件监听器
        	jbArray[i].addActionListener(this);       
        }
        //设置文本框的大小位置
        jtxt.setBounds(130,20,180,30);
        //将文本框添加进JPanel容器
        jp.add(jtxt);
        //为文本框注册事件监听器
        jtxt.addActionListener(this);
        //设置密码框的大小位置
        jpassword.setBounds(130,70,180,30);
        //将密码框添加进JPanel容器
        jp.add(jpassword);
        //设置密码框的 回显字符
        jpassword.setEchoChar('*');
        //为密码框注册监听器
        jpassword.addActionListener(this);
        //设置用于显示登录状态的标签的大小位置，并将其添加进JPanel容器
        jlArray[2].setBounds(10,280,300,30);
        jp.add(jlArray[2]);
        this.add(jp);
         //对标题和logo图片进行初始化	
 		Image image=new ImageIcon("ico.gif").getImage();  
 		this.setIconImage(image);
        //设置窗体的 大小位置及可见性
        this.setTitle("登录");
        this.setResizable(false);
        this.setBounds(100,100,400,300);
        this.setVisible(true);
    }
    //实现ActionListener接口中的方法
    public void actionPerformed(ActionEvent e)
    {
    	String mgno=jtxt.getText().trim();
        if(e.getSource()==jtxt)
    	{
    		//切换输入焦点到密码框
    		jpassword.requestFocus();
    	}
    	else if(e.getSource()==jbArray[1])
    	{//事件源为清空按钮
    	    //清空所有信息
    		jlArray[2].setText("");
    		jtxt.setText("");
    		jpassword.setText("");
    		//将输入焦点设置到文本框
    		jtxt.requestFocus();
    	}
    	else if(e.getSource()==jbArray[0])
    	{//事件源为管理员登录按钮
    	    //判断用户名和密码是否匹配
    	    if(jrb[0].isSelected())
    	    {
	    	    this.denglu();
    	    }
    	    if(jrb[1].isSelected())
    	    {
    	    	this.denglu();
    	    }
	    //关闭数据库链接
		db.dbClose();   
    	}
    	
    }
    public void denglu()
    {
    	String mgno=jtxt.getText().trim();
	    if(jtxt.getText().trim().equals(""))
		{
			JOptionPane.showMessageDialog(this,"用户名不能为空！！！","信息",JOptionPane.INFORMATION_MESSAGE);
	    	return;
		}
		if(jpassword.getText().trim().equals(""))
		{
			JOptionPane.showMessageDialog(this,"用户密码不能为空！！！","信息",JOptionPane.INFORMATION_MESSAGE);
	    	return;
		}
    	sql="select mgNo,password from manager where mgNo='"+mgno+"'";
    	
        db=new DataBase();
		db.selectDb(sql);
		try
		{
			String mgNo="";
			String password="";
			while(db.rs.next())
			{
				mgNo=db.rs.getString(1).trim();
		        password=db.rs.getString(2).trim();					
			}

	        if(jtxt.getText().trim().equals(mgNo)&&
    		String.valueOf(jpassword.getPassword()).equals(password))
    		{//登录成功
    			jlArray[2].setText("恭喜您，登录成功！！！");
    			new Root(mgNo);
    			this.dispose();   			
    		}
    		else
    		{//登录失败
    			jlArray[2].setText("对不起，登录失败！！！");    
    		}
		}
	    catch(Exception e1)
	    {
	    	e1.printStackTrace();
	    }
    }
    public static void main(String[]args)
    {
    	new Login();
    }
}