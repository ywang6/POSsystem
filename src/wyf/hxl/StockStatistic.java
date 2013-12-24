package wyf.hxl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
public class StockStatistic extends JPanel implements ActionListener{
	private JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT);//设置分割方向
	private JPanel jpt=new JPanel();
	DataBase db;
	private JLabel[] jlArray={//设置标签
		new JLabel("库存查询"),
	    new JLabel("查询结果")
	};
	private JTextField jtxt=new JTextField();	
	private JButton[] jbArray={//设置JButton按钮的文本
	    new JButton("查询"),
	    new JButton("短线商品"),
	    new JButton("超限商品")
	};
	private JRadioButton[] jrb={new JRadioButton("              商  品  名",true),
	new JRadioButton(" 全 部 库 存 信 息")};//设置单选按钮
    private ButtonGroup bg=new ButtonGroup();	
	Vector<String> head = new Vector<String>();
	{//创建标题
		head.add("入库单ID");head.add("供货商名称");
		head.add("商品ID");head.add("商品名称");
		head.add("商品规格");head.add("计量单位");
		head.add("入库数量");head.add("入库单价");
		head.add("金额");head.add("入库日期");
	}
	Vector<Vector> data=new Vector<Vector>();
    DefaultTableModel dtm=new DefaultTableModel(data,head);//创建表格模型
	JTable jt=new JTable(dtm);//创建Jtable对象
	JScrollPane jspn=new JScrollPane(jt);//将JTable封装到滚动窗格
	public StockStatistic(){
		this.setLayout(new GridLayout(1,1));//将面板设为网格布局	
		jpt.setLayout(null);//设置面板的上部分为空布局管理器
		jsp.setDividerLocation(135);//设置jspt中分割条的初始位置
		jsp.setDividerSize(4);//设置分隔条的宽度
		jsp.setTopComponent(jpt);
		jsp.setBottomComponent(jspn);
	    for(int i=0;i<3;i++){//对按钮进行初始化并添加监听器
	    	jpt.add(jbArray[i]);
	    	jbArray[i].setBounds(65+i*135,70,115,25);
	    	jbArray[i].addActionListener(this);
	    }
	    for(int i=0;i<2;i++){
        	jrb[i].setBounds(20+i*350,30,150,20);
        	jpt.add(jrb[i]);//将单选按钮添加进上部窗体
        	bg.add(jrb[i]);//把单选按钮添加进按钮组
        	jrb[i].addActionListener(this);//为单选按钮添加监听器
            jpt.add(jlArray[i]);  
        }	
		jlArray[0].setBounds(5,5,80,20);//设置标签的大小位置
		jtxt.setBounds(170,30,150,20);
		jlArray[1].setBounds(5,115,100,20);	
		jpt.add(jtxt);	
		this.add(jsp);//将分割窗体添加进面板	
    	jsp.setBottomComponent(jspn);//设置下部子窗格	
		//设置窗体的大小位置及可见性
		this.setBounds(5,5,600,500);
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==jrb[0]){//文本框将得到鼠标焦点
	    	jtxt.requestFocus();			
		}
		if(e.getSource()==jbArray[0])
		{//当点击"查询"按钮将执行查询功能
			this.searchProduct();
		}
		if(e.getSource()==jbArray[1])
		{//当点击"短线商品"按钮将执行查询短线商品
			this.littleProduct();
		}
		if(e.getSource()==jbArray[2])
		{//当点击"超限商品"按钮将执行查询超限商品
			this.manyProduct();
		}
	}
	public void searchProduct(){
		String s=jtxt.getText().trim();//得到文本框输入内容
		if(jrb[0].isSelected()){//当"商品名"单选按钮被选中按其进行查询
			String sql="select * from Stock where ProductName='"+s+"'";
			DataBase db=new DataBase();
			db.selectDb(sql);
			this.table();//更新表格
		}
		if(jrb[1].isSelected()){//当"全部库存信息"单选按钮被选中将库存信息全部搜索出
			String sql="select * from Stock";
			DataBase db=new DataBase();
			db.selectDb(sql);
		    this.table();//信息显示
		}
	}
	public void littleProduct(){//查询短线商品信息
		String sql="select Stock.StockID,Stock.FeederID,Stock.ProductID,"+
		"Stock.ProductName,Stock.Spec,Stock.Unit,Stock.Quantity,Stock.UnitPrice,"+
		"Stock.Payment,Stock.Stackdate from Stock,Product"+
		" where Stock.ProductID=Product.ProductID and Stock.Quantity<=Product.Min_sto";
		DataBase db=new DataBase();
		db.selectDb(sql);
		this.table();//更新表格
	}
	public void manyProduct(){//查询超储商品信息
		String sql="select Stock.StockID,Stock.FeederID,Stock.ProductID,"+
		"Stock.ProductName,Stock.Spec,Stock.Unit,Stock.Quantity,Stock.UnitPrice,"+
		"Stock.Payment,Stock.Stackdate from Stock,Product"+
		" where Stock.ProductID=Product.ProductID and Stock.Quantity>=Product.Max_sto";
		DataBase db=new DataBase();
		db.selectDb(sql);
		this.table();//更新表格
	}
	public void table(){
		try{
			 Vector <Vector> v=new Vector<Vector>();
		     while(db.rs.next()){//得到结果集
		       	 Vector <String> vtemp = new Vector<String>();
			     for(int i=0;i<10;i++){//对得到的信息进行转码    
			         String str1=new String(db.rs.getString(i+1).getBytes("ISO-8859-1"),"gb2312");
			         vtemp.add(str1);//将从结果集得到的信息添加到临时数组
			     }
			     v.add(vtemp);//将临时数组中的信息添加到表格							
		     }
		     if(v.size()==0){//结果集为空的提示
		     	JOptionPane.showMessageDialog(this,"您所查询的货物信息不存在！！","提示",
			     JOptionPane.INFORMATION_MESSAGE);
			    return;
		     } 	
			 dtm.setDataVector(v,head);//更新table
			 jt.updateUI();
		     jt.repaint();
		     JOptionPane.showMessageDialog(this,"您已经成功查询该货物信息！！","提示",
					     JOptionPane.INFORMATION_MESSAGE);//查询成功提示
			 jtxt.setText("");//清空文本框
			 return;        
		 }
		 catch(Exception ep){ep.printStackTrace();}//捕获异常
	}
	public static void main(String[]args)
	{
		new StockStatistic();
	}
}