package wyf.hxl;
import java.awt.*;import java.awt.event.*;
import javax.swing.*;import javax.swing.event.*;
import javax.swing.tree.*;import javax.xml.parsers.*;
import org.w3c.dom.*;import java.io.*;
import java.net.*;
public class Root extends JFrame{
	DefaultMutableTreeNode[] dmtn={//�����ڵ�����
	new DefaultMutableTreeNode(new NodeValue("�����ó���޹�˾POSϵͳ")),
	new DefaultMutableTreeNode(new NodeValue("������Ϣ")),new DefaultMutableTreeNode(new NodeValue("ҵ����")),
	new DefaultMutableTreeNode(new NodeValue("ҵ��ͳ��")),new DefaultMutableTreeNode(new NodeValue("ϵͳά��")),
	new DefaultMutableTreeNode(new NodeValue("�˳�ϵͳ")),new DefaultMutableTreeNode(new NodeValue("��Ʒ����")),
	new DefaultMutableTreeNode(new NodeValue("�ͻ�����")),new DefaultMutableTreeNode(new NodeValue("��Ӧ������")),
	new DefaultMutableTreeNode(new NodeValue("�ɹ����")),new DefaultMutableTreeNode(new NodeValue("��Ʒ����")),
	new DefaultMutableTreeNode(new NodeValue("�������䶯")),	new DefaultMutableTreeNode(new NodeValue("���ͳ��")),
	new DefaultMutableTreeNode(new NodeValue("�����Ϣ")),new DefaultMutableTreeNode(new NodeValue("����ѯ")),
	new DefaultMutableTreeNode(new NodeValue("������Ϣ")),	new DefaultMutableTreeNode(new NodeValue("���۲�ѯ"))};	
    DefaultTreeModel dtm=new DefaultTreeModel(dmtn[0]);//������ģ�ͣ�ָ�����ڵ�Ϊ"ѧ������ϵͳ    
    JTree jt=new JTree(dtm);//��������dtm��ģ�͵�JTree����  
    JScrollPane jsp=new JScrollPane(jt);//��JTree��ӽ���������
    private JSplitPane jsplr=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true); 
    private JPanel jp=new JPanel();//����JPanel����
	private JLabel jlRoot=new JLabel();
	private NewManager mag;
    public static String mgNo;
	CardLayout cl=new CardLayout();//��ȡ��Ƭ���ֹ���������
    public Root(String mgNo){
    	this.mgNo=mgNo;
    	mag=new NewManager(mgNo);
    	this.initJp();//���ó�ʼ���ڵ�ķ���
    	jt.addTreeSelectionListener(new TreeSelectionListener(){//���ڲ�����ʾ���ĸ�ѡ��ڵ�
				public void valueChanged(TreeSelectionEvent e){
					DefaultMutableTreeNode cdmtn=
							(DefaultMutableTreeNode)e.getPath().getLastPathComponent();
					NodeValue cnv=(NodeValue)cdmtn.getUserObject();
					if(cnv.value.equals("�����ó���޹�˾POSϵͳ")){cl.show(jp,"root");}//��ʾ������
					else if(cnv.value.equals("��Ʒ����")){cl.show(jp,"pt");}
					else if(cnv.value.equals("�ͻ�����")){cl.show(jp,"cm");}//��ʾ"�ͻ�����"����
					else if(cnv.value.equals("��Ӧ������")){cl.show(jp,"fd");}
					else if(cnv.value.equals("�����Ϣ")){cl.show(jp,"st");}//��ʾ"�����Ϣ"����
					else if(cnv.value.equals("����ѯ")){cl.show(jp,"sst");}
					else if(cnv.value.equals("������Ϣ")){cl.show(jp,"se");}//��ʾ"������Ϣ"����
					else if(cnv.value.equals("���۲�ѯ")){cl.show(jp,"sse");}
					else if(cnv.value.equals("�������䶯")){cl.show(jp,"os");}//��ʾ"�������䶯"����
					else if(cnv.value.equals("���ͳ��")){cl.show(jp,"ssta");}
					else if(cnv.value.equals("ϵͳά��")){cl.show(jp,"mag");}//��ʾ"ϵͳά��"����
					else if(cnv.value.equals("�����޸�")){cl.show(jp,"up");}
					else if(cnv.value.equals("�˳�ϵͳ")){//��ѡ��˵���ʾ�Ƿ��˳�ϵͳ
						int i=JOptionPane.showConfirmDialog(Root.this,"�Ƿ��˳�ϵͳ?",
																"��Ϣ",JOptionPane.YES_NO_OPTION);
						if(i==JOptionPane.YES_OPTION){System.exit(0);}						
					}									
				}
			});
	    for(int i=1;i<6;i++){dmtn[0].add(dmtn[i]);}//��"POS��Ϣ����ϵͳ"�ڵ�����ӽڵ�
	    for(int i=6;i<9;i++){dmtn[1].add(dmtn[i]);}//��"������Ϣ"�ڵ�����ӽڵ�
	    for(int i=9;i<12;i++){dmtn[2].add(dmtn[i]);}//��"ҵ����"�ڵ�����ӽڵ�
	    dmtn[3].add(dmtn[12]);//��"ҵ��ͳ��"�ڵ�����ӽڵ�
	    for(int i=13;i<=14;i++){dmtn[9].add(dmtn[i]);}//��"�ɹ����"�ڵ�����ӽڵ�
	    for(int i=15;i<17;i++){dmtn[10].add(dmtn[i]);}//��"��Ʒ����"�ڵ�����ӽڵ�
	    //���ø����нڵ��ǿɱ༭��
		jt.setEditable(false);
		//���������Ĺ���������ӽ�����
		this.add(jsplr);
		//���������Ĺ���������ӽ���ߵ��Ӵ���
		jsplr.setLeftComponent(jt);
		//Ϊjp���ô�Сλ�ò���ӽ��ұߵ��Ӵ���
		jp.setBounds(200,50,900,800);
		jsplr.setRightComponent(jp);
        //���÷ָ����ĳ�ʼλ��
        jsplr.setDividerLocation(300);
        //���÷ָ����Ŀ��
        jsplr.setDividerSize(4); 
 //       jlRoot.setFont(new Font("Courier",Font.PLAIN,30));
		jlRoot.setHorizontalAlignment(JLabel.CENTER);
		jlRoot.setVerticalAlignment(JLabel.CENTER);
		
		Image icon = Toolkit.getDefaultToolkit().getImage("POS.jpg");
		ImageIcon ic = new ImageIcon(icon);
		jlRoot.setIcon(ic);
		
		
		//���ô���Ĺرն��������⣬��С��λ�ü��ɼ���
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//�Ա����logoͼƬ���г�ʼ��	
 		Image image=new ImageIcon("ico.gif").getImage();
 		//Image icon=Toolkit.getDefaultToolkit().getImage("/ico.gif");  
 		this.setIconImage(image);
		this.setTitle("�����ó���޹�˾POSϵͳ");
		//���ô����״γ��ֵĴ�С��λ��--�Զ�����
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX=screenSize.width/2;
		int centerY=screenSize.height/2;
		int w=500;//��������
		int h=400;//������߶�
		this.setBounds(centerX-w/2,centerY-h/2-100,w,h);//���ô����������Ļ����
		//����ȫ��
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);
		//������ʾ���ڵ�Ŀ���ͼ��
		jt.setShowsRootHandles(true);	
    }
    public void initJp(){//��ʼ�����ڵ�
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
class NodeValue{//�Զ���ĳ�ʼ�����ڵ�����ݶ������
	String value;	
	public NodeValue(String value){//������
		this.value=value;
	}
	public String getValue(){//ȡ�ýڵ��ֵ
		return this.value;
	}
	@Override
	public String toString(){//��д�ķ���
		return value;
	}
}