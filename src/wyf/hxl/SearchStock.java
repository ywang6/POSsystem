package wyf.hxl;
import javax.swing.*;import java.awt.*;
import java.awt.event.*;import javax.swing.table.*;
import java.sql.*;import java.util.*;
import java.util.Date;
public class SearchStock extends JPanel implements ActionListener{
	private JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT);//设置分割方向
	private JPanel jpt=new JPanel();
	private JLabel jl=new JLabel("查询条件");
	DataBase db;
	String sql;
	private JRadioButton[] jrb=new JRadioButton[]{//设置单选按钮的文本
		new JRadioButton("        入库单号",true),
		new JRadioButton("        供 货 商"),
		new JRadioButton("        入库日期"),
		new JRadioButton("        商品名称")
	};
	private ButtonGroup bg=new ButtonGroup();//创建按钮组	
	private JButton jb= new JButton("查询信息");//设置JButton按钮的文本
	Vector<String> head = new Vector<String>();
	{//创建标题
		head.add("入库单号");head.add("供应商名");
		head.add("货品ID");head.add("货品名");
		head.add("货品规格");head.add("计量单位");
		head.add("参考进价");head.add("入库单价");
		head.add("金额");head.add("入库规格");
	}
	private JTextField[] jtxtArray=new JTextField[]{//声明文本框
		new JTextField(),new JTextField(),
		new JTextField(),new JTextField()
	};	
	Vector<Vector> data=new Vector<Vector>();//设置表格
    DefaultTableModel dtm=new DefaultTableModel(data,head);//创建表格模型  
	JTable jt=new JTable(dtm);//创建Jtable对象
	JScrollPane jspn=new JScrollPane(jt);//将JTable封装到滚动窗格
	public SearchStock(){
		this.setLayout(new GridLayout(1,1));	
		jpt.setLayout(null);//设置面板的上部分为空布局管理器
		jsp.setDividerLocation(135);//设置jspt中分割条的初始位置
		jsp.setDividerSize(4);//设置分隔条的宽度
		jsp.setTopComponent(jpt);
		jsp.setBottomComponent(jspn);//设置下部窗体
		jpt.add(jl);
		jl.setBounds(5,5,100,20);
		for(int i=0;i<4;i++){
        	bg.add(jrb[i]);//将单选按钮添加进按钮组
	        jpt.add(jrb[i]);//将单选按钮添加进上部窗体
		    jpt.add(jtxtArray[i]);//将文本框添加进上部窗体
		    jrb[i].addActionListener(this);//为单选按钮注册监听器
			if(i>=0&&i<2){//初始化第1列单选按钮及文本框并为文本框注册监听器
			    jrb[i].setBounds(15,30+30*i,120,20);
			    jtxtArray[i].setBounds(135,30+30*i,150,20);
			    jtxtArray[i].addActionListener(this);
			}
			else if(i>=2&&i<4){//初始化第2列单选按钮及文本框并为文本框注册监听器
				jrb[i].setBounds(285,30+30*(i-2),120,20);
				jtxtArray[i].setBounds(405,30+30*(i-2),150,20);
				jtxtArray[i].addActionListener(this);
			}
		}
		this.add(jsp);	
    	jsp.setBottomComponent(jspn);//设置下部子窗格
		jpt.add(jb);//将JButton添加进jpt
		jb.setBounds(205,100,150,20);
		jb.addActionListener(this);	//设置监听器		
		this.setBounds(5,5,600,500);//设置窗体的大小位置
		this.setVisible(true);//设置窗体的可见性
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==jrb[0]){//事件源为"入库单号"按钮
			jtxtArray[0].requestFocus();
		}
		if(e.getSource()==jrb[1]){//事件源为"供货商"按钮
			jtxtArray[1].requestFocus();
		}
		if(e.getSource()==jrb[2]){//事件源为"入库日期"按钮
			jtxtArray[2].requestFocus();
		} 
		if(e.getSource()==jrb[3]){//事件源为"商品名称"按钮
			jtxtArray[3].requestFocus();
		} 
		if(e.getSource()==jb){//当点击"查询信息"按钮将执行查询功能
	        if(jrb[0].isSelected()){//按入库单号查询
	        	String str=jtxtArray[0].getText().trim();
	        	sql="select * from Stock where StockID='"+str+"'";
	        	db=new DataBase();
		        db.selectDb(sql);//调用select方法
			    this.table();
	        }
	        if(jrb[1].isSelected()){//按供货商查询
	        	String str=jtxtArray[1].getText().trim();
	        	sql="select * from Stock where FeederID='"+str+"'";
	        	db=new DataBase();
		        db.selectDb(sql);//调用select方法
			    this.table();
	        }
	        if(jrb[2].isSelected()){//按入库日期查询
	        	String str0=jtxtArray[2].getText().trim();
	        	sql="select * from Stock where Stackdate='"+str0+"'";
	            db=new DataBase();
			    db.selectDb(sql);//调用select方法
			        this.table();
	        }
	        if(jrb[3].isSelected()){//按商品名称查询
	        	String str=jtxtArray[3].getText().trim();
	        	sql="select * from Stock where ProductName='"+str+"'";
	        	db=new DataBase();
		        db.selectDb(sql);//调用select方法
			    this.table();
	        }
		}
	}
	public void table(){
		try
		{
			if(db.rs.next()){
				Vector <String> v=new Vector <String>();
		        for(int i=0;i<10;i++){//为结果集数据转码并将其添加进临时数组
		            String str1=new String(db.rs.getString(i+1).getBytes("ISO-8859-1"),"gb2312");
		        	v.add(str1);
		        }
		         data.add(v);//将信息添加进表格
				dtm.setDataVector(data,head);//更新table	
				jt.updateUI();
				jt.repaint();
				JOptionPane.showMessageDialog(this,"您已经成功查询该货物信息！！","提示",
			             JOptionPane.INFORMATION_MESSAGE);//查询成功提示
			    for(int i=0;i<4;i++){//清空文本框
			     	 jtxtArray[3].setText("");
			    }
				return;
			}
			else{//查询失败提示
	        	JOptionPane.showMessageDialog(this,"您所查询的货物信息不存在！！","提示",
			     JOptionPane.INFORMATION_MESSAGE);
				return;
	        }
		}
		catch(Exception ep){ep.printStackTrace();}//捕获异常
	}
	public static void main(String[]args)
	{
		new SearchStock();
	}
}