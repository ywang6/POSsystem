package wyf.hxl;
import java.awt.*;import java.awt.event.*;
import javax.swing.*;import javax.swing.event.*;
import javax.swing.tree.*;import javax.xml.parsers.*;
import org.w3c.dom.*;import java.io.*;
import java.net.*;
public class Root extends JFrame{
	DefaultMutableTreeNode[] dmtn={//创建节点数组
	new DefaultMutableTreeNode(new NodeValue("瑞捷商贸有限公司POS系统")),
	new DefaultMutableTreeNode(new NodeValue("基本信息")),new DefaultMutableTreeNode(new NodeValue("业务处理")),
	new DefaultMutableTreeNode(new NodeValue("业务统计")),new DefaultMutableTreeNode(new NodeValue("系统维护")),
	new DefaultMutableTreeNode(new NodeValue("退出系统")),new DefaultMutableTreeNode(new NodeValue("商品资料")),
	new DefaultMutableTreeNode(new NodeValue("客户资料")),new DefaultMutableTreeNode(new NodeValue("供应商资料")),
	new DefaultMutableTreeNode(new NodeValue("采购入库")),new DefaultMutableTreeNode(new NodeValue("商品销售")),
	new DefaultMutableTreeNode(new NodeValue("其他库存变动")),	new DefaultMutableTreeNode(new NodeValue("库存统计")),
	new DefaultMutableTreeNode(new NodeValue("入库信息")),new DefaultMutableTreeNode(new NodeValue("入库查询")),
	new DefaultMutableTreeNode(new NodeValue("销售信息")),	new DefaultMutableTreeNode(new NodeValue("销售查询"))};	
    DefaultTreeModel dtm=new DefaultTreeModel(dmtn[0]);//创建树模型，指定根节点为"学生管理系统    
    JTree jt=new JTree(dtm);//创建包含dtm树模型的JTree对象  
    JScrollPane jsp=new JScrollPane(jt);//将JTree添加进滚动窗体
    private JSplitPane jsplr=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true); 
    private JPanel jp=new JPanel();//创建JPanel对象
	private JLabel jlRoot=new JLabel();
	private NewManager mag;
    public static String mgNo;
	CardLayout cl=new CardLayout();//获取卡片布局管理器引用
    public Root(String mgNo){
    	this.mgNo=mgNo;
    	mag=new NewManager(mgNo);
    	this.initJp();//调用初始化节点的方法
    	jt.addTreeSelectionListener(new TreeSelectionListener(){//用内部类显示树的各选择节点
				public void valueChanged(TreeSelectionEvent e){
					DefaultMutableTreeNode cdmtn=
							(DefaultMutableTreeNode)e.getPath().getLastPathComponent();
					NodeValue cnv=(NodeValue)cdmtn.getUserObject();
					if(cnv.value.equals("瑞捷商贸有限公司POS系统")){cl.show(jp,"root");}//显示主界面
					else if(cnv.value.equals("商品资料")){cl.show(jp,"pt");}
					else if(cnv.value.equals("客户资料")){cl.show(jp,"cm");}//显示"客户资料"界面
					else if(cnv.value.equals("供应商资料")){cl.show(jp,"fd");}
					else if(cnv.value.equals("入库信息")){cl.show(jp,"st");}//显示"入库信息"界面
					else if(cnv.value.equals("入库查询")){cl.show(jp,"sst");}
					else if(cnv.value.equals("销售信息")){cl.show(jp,"se");}//显示"销售信息"界面
					else if(cnv.value.equals("销售查询")){cl.show(jp,"sse");}
					else if(cnv.value.equals("其他库存变动")){cl.show(jp,"os");}//显示"其他库存变动"界面
					else if(cnv.value.equals("库存统计")){cl.show(jp,"ssta");}
					else if(cnv.value.equals("系统维护")){cl.show(jp,"mag");}//显示"系统维护"界面
					else if(cnv.value.equals("密码修改")){cl.show(jp,"up");}
					else if(cnv.value.equals("退出系统")){//用选择菜单提示是否退出系统
						int i=JOptionPane.showConfirmDialog(Root.this,"是否退出系统?",
																"消息",JOptionPane.YES_NO_OPTION);
						if(i==JOptionPane.YES_OPTION){System.exit(0);}						
					}									
				}
			});
	    for(int i=1;i<6;i++){dmtn[0].add(dmtn[i]);}//给"POS信息管理系统"节点添加子节点
	    for(int i=6;i<9;i++){dmtn[1].add(dmtn[i]);}//给"基本信息"节点添加子节点
	    for(int i=9;i<12;i++){dmtn[2].add(dmtn[i]);}//给"业务处理"节点添加子节点
	    dmtn[3].add(dmtn[12]);//给"业务统计"节点添加子节点
	    for(int i=13;i<=14;i++){dmtn[9].add(dmtn[i]);}//给"采购入库"节点添加子节点
	    for(int i=15;i<17;i++){dmtn[10].add(dmtn[i]);}//给"商品销售"节点添加子节点
	    //设置该树中节点是可编辑的
		jt.setEditable(false);
		//将包含树的滚动窗口添加进窗体
		this.add(jsplr);
		//将包含树的滚动窗口添加进左边的子窗口
		jsplr.setLeftComponent(jt);
		//为jp设置大小位置并添加进右边的子窗口
		jp.setBounds(200,50,900,800);
		jsplr.setRightComponent(jp);
        //设置分隔条的初始位置
        jsplr.setDividerLocation(300);
        //设置分隔条的宽度
        jsplr.setDividerSize(4); 
 //       jlRoot.setFont(new Font("Courier",Font.PLAIN,30));
		jlRoot.setHorizontalAlignment(JLabel.CENTER);
		jlRoot.setVerticalAlignment(JLabel.CENTER);
		
		Image icon = Toolkit.getDefaultToolkit().getImage("POS.jpg");
		ImageIcon ic = new ImageIcon(icon);
		jlRoot.setIcon(ic);
		
		
		//设置窗体的关闭动作，标题，大小，位置及可见性
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//对标题和logo图片进行初始化	
 		Image image=new ImageIcon("ico.gif").getImage();
 		//Image icon=Toolkit.getDefaultToolkit().getImage("/ico.gif");  
 		this.setIconImage(image);
		this.setTitle("瑞捷商贸有限公司POS系统");
		//设置窗体首次出现的大小和位置--自动居中
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX=screenSize.width/2;
		int centerY=screenSize.height/2;
		int w=500;//本窗体宽度
		int h=400;//本窗体高度
		this.setBounds(centerX-w/2,centerY-h/2-100,w,h);//设置窗体出现在屏幕中央
		//窗体全屏
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);
		//设置显示根节点的控制图标
		jt.setShowsRootHandles(true);	
    }
    public void initJp(){//初始化各节点
    	jp.setLayout(cl);
    	jp.add(jlRoot,"root");
    	jp.add(new Product(),"pt");
    	jp.add(new Customer(),"cm");
    	jp.add(new Feeder(),"fd");
    	jp.add(new Stock(),"st");
    	jp.add(new SearchStock(),"sst");
    	jp.add(new Sell(),"se");
    	jp.add(new SearchSell(),"sse");
    	jp.add(new OtherStorage(),"os");
    	jp.add(new StockStatistic(),"ssta");
    	jp.add(this.mag,"mag");
    }
    public static void main(String[]args)
    {
    	new Root(mgNo);
    }
}
class NodeValue{//自定义的初始化树节点的数据对象的类
	String value;	
	public NodeValue(String value){//构造器
		this.value=value;
	}
	public String getValue(){//取得节点的值
		return this.value;
	}
	@Override
	public String toString(){//重写的方法
		return value;
	}
}