package wyf.hxl;
import javax.swing.*;import java.awt.*;
import java.awt.event.*;import javax.swing.table.*;
import java.sql.*;import java.util.*;
import java.util.Date;
public class Product extends JPanel implements ActionListener{
	private JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT);//设置本面板为网格布局
	private JPanel jpt=new JPanel();
	String[]str1=new String[8];
	String sql;//声明sql字符串
	DataBase db;
	private JLabel[] jlArray={//创建标签
		new JLabel("  商  品  ID"),new JLabel("  商品名称"),
		new JLabel("  商品规格"),new JLabel("  计量单位"),
		new JLabel("  参考进价"),new JLabel("  参考售价"),
	    new JLabel("  库存下限"),new JLabel("  库存上限"),
	    new JLabel("商品资料"), 
	};
	private JTextField[] jtxtArray=new JTextField[]{//创建文本框
		new JTextField(),new JTextField(),
		new JTextField(),new JTextField(),
		new JTextField(),new JTextField(),
		new JTextField(),new JTextField()
	};
	private JButton[] jbArray={//创建JButton按钮并设置其文本内容
	    new JButton("添加商品"),new JButton("删除商品"),
	    new JButton("修改商品"),new JButton("查询商品")
	};
	Vector<String> head = new Vector<String>();//创建标题
		{
		head.add("商品ID");head.add("商品名称");
		head.add("商品规格");head.add("计量单位");
		head.add("参考进价");head.add("参考售价");
		head.add("库存下限");head.add("库存上限");
	}
	Vector<Vector> data=new Vector<Vector>();//在下部子窗口中设置表格
    DefaultTableModel dtm=new DefaultTableModel(data,head);//创建表格模型
	JTable jt=new JTable(dtm);//创建Jtable对象
	JScrollPane jspn=new JScrollPane(jt);//将JTable封装到滚动窗格
	public Product()
	{
		this.setLayout(new GridLayout(1,1));
		jpt.setLayout(null);//设置面板的上部分为空布局管理器	
		jsp.setDividerLocation(170);//设置jspt中分割条的初始位置
		jsp.setDividerSize(4);//设置分隔条的宽度
		jsp.setTopComponent(jpt);
		jsp.setBottomComponent(jspn);
		jpt.add(jlArray[8]);//将商品资料标签添加进上部的面板
		jlArray[8].setBounds(5,5,100,20);//设置货品资料标签的大小位置
		for(int i=0;i<8;i++){
			jpt.add(jlArray[i]);
			jpt.add(jtxtArray[i]);
			if(i<4){//设置第一行标签和文本框的大小位置
			    jlArray[i].setBounds(15+i*200,40,80,20);
			    jtxtArray[i].setBounds(95+i*200,40,120,20);
			    jtxtArray[i].addActionListener(this);//为文本框注册事件监听器
			}
			else {//设置第二行标签和文本框的大小位置
				jlArray[i].setBounds(15+(i-4)*200,80,80,20);
				jtxtArray[i].setBounds(95+(i-4)*200,80,120,20);
				jtxtArray[i].addActionListener(this);//为文本框注册事件监听器
			}
		}
		this.add(jsp);
    	jsp.setBottomComponent(jspn);//将滚动窗格添加到面板下部子窗体
		for(int i=0;i<4;i++){
			jpt.add(jbArray[i]);//将JButton添加进jpt
			jbArray[i].setBounds(120+150*i,130,100,25);
		}
		for(int i=0;i<4;i++){//为按钮设置监听器
			jbArray[i].addActionListener(this);
		}		
		//设置窗体的大小位置及可见性
		this.setBounds(5,5,1000,600);
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==jtxtArray[0]){
			jtxtArray[1].requestFocus();
		}
		if(e.getSource()==jtxtArray[1]){
			jtxtArray[2].requestFocus();
		}
		if(e.getSource()==jtxtArray[2]){
			jtxtArray[3].requestFocus();
		}
		if(e.getSource()==jtxtArray[3]){
			jtxtArray[4].requestFocus();
		}
		if(e.getSource()==jtxtArray[4]){
			jtxtArray[5].requestFocus();
		}   
		if(e.getSource()==jtxtArray[5]){
			jtxtArray[6].requestFocus();
		}
		if(e.getSource()==jtxtArray[6]){
			jtxtArray[7].requestFocus();
		}   
	    //当点击"添加信息"按钮是将执行添加功能
		if(e.getSource()==jbArray[0]){
			this.insertProduct();
			for(int i=0;i<8;i++){
				jtxtArray[i].setText("");
			}
		}
		//当点击"删除信息"按钮是将执行删除功能	
		if(e.getSource()==jbArray[1]){
		    this.deleteProduct();
		    jtxtArray[0].setText("");
		}
		
		if(e.getSource()==jbArray[2]){
		    this.updateProduct();
		    jtxtArray[0].setText("");
		}
		//当点击"查询商品"按钮是将执行修改功能
		if(e.getSource()==jbArray[3]){
		    this.searchProduct();
		    jtxtArray[0].setText("");
		    
		}
	}
	public void insertProduct(){		
		for(int i=0;i<8;i++){//对变量进行声明
			str1[i]=jtxtArray[i].getText().trim();		
		}
		if(!str1[0].equals("")&&!str1[1].equals("")&&!str1[2].equals("")&&!str1[3].equals("")
					&&!str1[4].equals("")&&!str1[5].equals("")&&!str1[6].equals("")&&!str1[7].equals("")){//根据条件进行商品信息插入
	        sql="insert into Product(ProductID,ProductName,Spec,Unit,RFStockPrice,RFSellPrice,"
			    +"Min_sto,Max_sto) values('"+str1[0]+"','"+str1[1]+"','" + str1[2] + "','"+str1[3]+"','"+
			            str1[4]+"','"+str1[5]+"','"+str1[6]+"','"+str1[7]+"')";
			db=new DataBase();
			db.updateDb(sql);//调用数据库更新方法
			Vector<String> v = new Vector<String>();
		    for(int i=0;i<=7;i++){//将每列添加到临时数组v
				v.add(str1[i]);
				if(i<8){jtxtArray[i].setText("");}	
		    }
		    data.add(v);//将数据添加到表格中		
			dtm.setDataVector(data,head);//更新table	
			jt.updateUI();
			jt.repaint();//添加成功提示
			JOptionPane.showMessageDialog(this,	"添加新商品成功！！",
						        "消息",JOptionPane.INFORMATION_MESSAGE);
	        return;
		}
		else{//商品输入信息为空提示
			JOptionPane.showMessageDialog(this,	"商品信息不能为空！！！",
						        "消息",JOptionPane.INFORMATION_MESSAGE);
	        return;	
		}
	}
	public void deleteProduct(){
		String pd=jtxtArray[0].getText().trim();//对商品ID文本框输入字符串进行声明
		if(pd.equals("")){//商品ID文本框输入为空进行提示
			JOptionPane.showMessageDialog(this,	"商品号不能为空！！！",
						        "消息",JOptionPane.INFORMATION_MESSAGE);
			return;			
		}
		sql="select * from Product where ProductID='"+pd+"'";//查询商品ID文本框指定商品
		db=new DataBase();
		db.selectDb(sql);
		try{
			if(db.rs.next()){//如果取得结果集，将其删除
		    	sql="delete from Product where ProductID='"+pd+"'";//执行删除
		    	db=new DataBase();
	        	db.updateDb(sql);
	        	JOptionPane.showMessageDialog(this,	"成功删除该商品信息记录！！",
							        "消息",JOptionPane.INFORMATION_MESSAGE);//删除成功提示
	        	return;	
			}
			else{//搜索商品ID文本框制定商品信息失败进行提示
				JOptionPane.showMessageDialog(this,	"没有该商品！",
							        "消息",JOptionPane.INFORMATION_MESSAGE);
				return;			        
			}
		}
	    catch(Exception e){e.printStackTrace();}//捕获异常	
	}
	public void updateProduct(){
		String str[]=new String[8];
		int row = jt.getSelectedRow();//选中表格中的一行信息
		if(row>=0){
			for(int i=0;i<8;i++){//声明所选行的各列数据
				str[i]=jt.getValueAt(row,i).toString().trim();
			}
			sql="update Product set ProductName='"+str[1]+"',Spec='"
			     +str[2]+"',Unit='"+str[3]+"',RFStockPrice='"+str[4]+"',RFSellPrice='"+str[5]
			     +"',Min_sto='"+str[6]+"',Max_sto='"+str[7]+"' where ProductID='"+str[0]+"'";//执行更新操作	     
			db=new DataBase();
			db.updateDb(sql);//调用数据库更新方法
			JOptionPane.showMessageDialog(this,"修改成功！！",
			                    "消息!!",JOptionPane.INFORMATION_MESSAGE);//修改成功提示
			return;								
		}
		if(row==-1){//错误操作提示
			JOptionPane.showMessageDialog(this,"请点击'查找'按钮,然后选中下部的表格里所改行,再点击'修改信息'按钮!",
			                                   "Warning!!",JOptionPane.INFORMATION_MESSAGE);
			return;
		}		
	}
	public void searchProduct(){
		if(jtxtArray[0].getText().equals("")){//必需信息输入为空提示
	        JOptionPane.showMessageDialog(this,"输入不能为空，请重新输入！！！",
			                              "信息",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		//查询货品号文本中所输号是否存在于Product表中
	   	sql="select * from Product where ProductID='"+jtxtArray[0].getText().trim()+"'";
	    db=new DataBase();
		db.selectDb(sql);//调用操纵数据库的select方法				   
	    try{
		    int k=0;
			Vector<Vector> vtemp = new Vector<Vector>();
			while(db.rs.next()){//获得结果集
			 	k++;
		       	Vector<String> v = new Vector<String>();
				for(int i=1;i<=8;i++){//顺序达到所搜到的结果中的各项记录
					String str = db.rs.getString(i).trim();
					str = new String(str.getBytes("ISO-8859-1"),"gb2312");//对取得的数据转码
					v.add(str);	
				}
				vtemp.add(v);//更新结果框中的内容
			 }
			  if(k==0){//若Book表中没有该书号，则弹出提示对话框
			 	 JOptionPane.showMessageDialog(this,"没有您要查找的内容",
				                 "消息",JOptionPane.INFORMATION_MESSAGE);
			 }
			dtm.setDataVector(vtemp,head);//更新表格
			jt.updateUI();
			jt.repaint();				 	
		 }
		 catch(Exception e){e.printStackTrace();}	    
	}
	public static void main(String[]args)
	{
		new Product();
	}
}