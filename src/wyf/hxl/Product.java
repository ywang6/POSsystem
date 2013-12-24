package wyf.hxl;
import javax.swing.*;import java.awt.*;
import java.awt.event.*;import javax.swing.table.*;
import java.sql.*;import java.util.*;
import java.util.Date;
public class Product extends JPanel implements ActionListener{
	private JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT);//���ñ����Ϊ���񲼾�
	private JPanel jpt=new JPanel();
	String[]str1=new String[8];
	String sql;//����sql�ַ���
	DataBase db;
	private JLabel[] jlArray={//������ǩ
		new JLabel("  ��  Ʒ  ID"),new JLabel("  ��Ʒ����"),
		new JLabel("  ��Ʒ���"),new JLabel("  ������λ"),
		new JLabel("  �ο�����"),new JLabel("  �ο��ۼ�"),
	    new JLabel("  �������"),new JLabel("  �������"),
	    new JLabel("��Ʒ����"), 
	};
	private JTextField[] jtxtArray=new JTextField[]{//�����ı���
		new JTextField(),new JTextField(),
		new JTextField(),new JTextField(),
		new JTextField(),new JTextField(),
		new JTextField(),new JTextField()
	};
	private JButton[] jbArray={//����JButton��ť���������ı�����
	    new JButton("�����Ʒ"),new JButton("ɾ����Ʒ"),
	    new JButton("�޸���Ʒ"),new JButton("��ѯ��Ʒ")
	};
	Vector<String> head = new Vector<String>();//��������
		{
		head.add("��ƷID");head.add("��Ʒ����");
		head.add("��Ʒ���");head.add("������λ");
		head.add("�ο�����");head.add("�ο��ۼ�");
		head.add("�������");head.add("�������");
	}
	Vector<Vector> data=new Vector<Vector>();//���²��Ӵ��������ñ��
    DefaultTableModel dtm=new DefaultTableModel(data,head);//�������ģ��
	JTable jt=new JTable(dtm);//����Jtable����
	JScrollPane jspn=new JScrollPane(jt);//��JTable��װ����������
	public Product()
	{
		this.setLayout(new GridLayout(1,1));
		jpt.setLayout(null);//���������ϲ���Ϊ�ղ��ֹ�����	
		jsp.setDividerLocation(170);//����jspt�зָ����ĳ�ʼλ��
		jsp.setDividerSize(4);//���÷ָ����Ŀ��
		jsp.setTopComponent(jpt);
		jsp.setBottomComponent(jspn);
		jpt.add(jlArray[8]);//����Ʒ���ϱ�ǩ��ӽ��ϲ������
		jlArray[8].setBounds(5,5,100,20);//���û�Ʒ���ϱ�ǩ�Ĵ�Сλ��
		for(int i=0;i<8;i++){
			jpt.add(jlArray[i]);
			jpt.add(jtxtArray[i]);
			if(i<4){//���õ�һ�б�ǩ���ı���Ĵ�Сλ��
			    jlArray[i].setBounds(15+i*200,40,80,20);
			    jtxtArray[i].setBounds(95+i*200,40,120,20);
			    jtxtArray[i].addActionListener(this);//Ϊ�ı���ע���¼�������
			}
			else {//���õڶ��б�ǩ���ı���Ĵ�Сλ��
				jlArray[i].setBounds(15+(i-4)*200,80,80,20);
				jtxtArray[i].setBounds(95+(i-4)*200,80,120,20);
				jtxtArray[i].addActionListener(this);//Ϊ�ı���ע���¼�������
			}
		}
		this.add(jsp);
    	jsp.setBottomComponent(jspn);//������������ӵ�����²��Ӵ���
		for(int i=0;i<4;i++){
			jpt.add(jbArray[i]);//��JButton��ӽ�jpt
			jbArray[i].setBounds(120+150*i,130,100,25);
		}
		for(int i=0;i<4;i++){//Ϊ��ť���ü�����
			jbArray[i].addActionListener(this);
		}		
		//���ô���Ĵ�Сλ�ü��ɼ���
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
	    //�����"�����Ϣ"��ť�ǽ�ִ����ӹ���
		if(e.getSource()==jbArray[0]){
			this.insertProduct();
			for(int i=0;i<8;i++){
				jtxtArray[i].setText("");
			}
		}
		//�����"ɾ����Ϣ"��ť�ǽ�ִ��ɾ������	
		if(e.getSource()==jbArray[1]){
		    this.deleteProduct();
		    jtxtArray[0].setText("");
		}
		
		if(e.getSource()==jbArray[2]){
		    this.updateProduct();
		    jtxtArray[0].setText("");
		}
		//�����"��ѯ��Ʒ"��ť�ǽ�ִ���޸Ĺ���
		if(e.getSource()==jbArray[3]){
		    this.searchProduct();
		    jtxtArray[0].setText("");
		    
		}
	}
	public void insertProduct(){		
		for(int i=0;i<8;i++){//�Ա�����������
			str1[i]=jtxtArray[i].getText().trim();		
		}
		if(!str1[0].equals("")&&!str1[1].equals("")&&!str1[2].equals("")&&!str1[3].equals("")
					&&!str1[4].equals("")&&!str1[5].equals("")&&!str1[6].equals("")&&!str1[7].equals("")){//��������������Ʒ��Ϣ����
	        sql="insert into Product(ProductID,ProductName,Spec,Unit,RFStockPrice,RFSellPrice,"
			    +"Min_sto,Max_sto) values('"+str1[0]+"','"+str1[1]+"','" + str1[2] + "','"+str1[3]+"','"+
			            str1[4]+"','"+str1[5]+"','"+str1[6]+"','"+str1[7]+"')";
			db=new DataBase();
			db.updateDb(sql);//�������ݿ���·���
			Vector<String> v = new Vector<String>();
		    for(int i=0;i<=7;i++){//��ÿ����ӵ���ʱ����v
				v.add(str1[i]);
				if(i<8){jtxtArray[i].setText("");}	
		    }
		    data.add(v);//��������ӵ������		
			dtm.setDataVector(data,head);//����table	
			jt.updateUI();
			jt.repaint();//��ӳɹ���ʾ
			JOptionPane.showMessageDialog(this,	"�������Ʒ�ɹ�����",
						        "��Ϣ",JOptionPane.INFORMATION_MESSAGE);
	        return;
		}
		else{//��Ʒ������ϢΪ����ʾ
			JOptionPane.showMessageDialog(this,	"��Ʒ��Ϣ����Ϊ�գ�����",
						        "��Ϣ",JOptionPane.INFORMATION_MESSAGE);
	        return;	
		}
	}
	public void deleteProduct(){
		String pd=jtxtArray[0].getText().trim();//����ƷID�ı��������ַ�����������
		if(pd.equals("")){//��ƷID�ı�������Ϊ�ս�����ʾ
			JOptionPane.showMessageDialog(this,	"��Ʒ�Ų���Ϊ�գ�����",
						        "��Ϣ",JOptionPane.INFORMATION_MESSAGE);
			return;			
		}
		sql="select * from Product where ProductID='"+pd+"'";//��ѯ��ƷID�ı���ָ����Ʒ
		db=new DataBase();
		db.selectDb(sql);
		try{
			if(db.rs.next()){//���ȡ�ý����������ɾ��
		    	sql="delete from Product where ProductID='"+pd+"'";//ִ��ɾ��
		    	db=new DataBase();
	        	db.updateDb(sql);
	        	JOptionPane.showMessageDialog(this,	"�ɹ�ɾ������Ʒ��Ϣ��¼����",
							        "��Ϣ",JOptionPane.INFORMATION_MESSAGE);//ɾ���ɹ���ʾ
	        	return;	
			}
			else{//������ƷID�ı����ƶ���Ʒ��Ϣʧ�ܽ�����ʾ
				JOptionPane.showMessageDialog(this,	"û�и���Ʒ��",
							        "��Ϣ",JOptionPane.INFORMATION_MESSAGE);
				return;			        
			}
		}
	    catch(Exception e){e.printStackTrace();}//�����쳣	
	}
	public void updateProduct(){
		String str[]=new String[8];
		int row = jt.getSelectedRow();//ѡ�б���е�һ����Ϣ
		if(row>=0){
			for(int i=0;i<8;i++){//������ѡ�еĸ�������
				str[i]=jt.getValueAt(row,i).toString().trim();
			}
			sql="update Product set ProductName='"+str[1]+"',Spec='"
			     +str[2]+"',Unit='"+str[3]+"',RFStockPrice='"+str[4]+"',RFSellPrice='"+str[5]
			     +"',Min_sto='"+str[6]+"',Max_sto='"+str[7]+"' where ProductID='"+str[0]+"'";//ִ�и��²���	     
			db=new DataBase();
			db.updateDb(sql);//�������ݿ���·���
			JOptionPane.showMessageDialog(this,"�޸ĳɹ�����",
			                    "��Ϣ!!",JOptionPane.INFORMATION_MESSAGE);//�޸ĳɹ���ʾ
			return;								
		}
		if(row==-1){//���������ʾ
			JOptionPane.showMessageDialog(this,"����'����'��ť,Ȼ��ѡ���²��ı����������,�ٵ��'�޸���Ϣ'��ť!",
			                                   "Warning!!",JOptionPane.INFORMATION_MESSAGE);
			return;
		}		
	}
	public void searchProduct(){
		if(jtxtArray[0].getText().equals("")){//������Ϣ����Ϊ����ʾ
	        JOptionPane.showMessageDialog(this,"���벻��Ϊ�գ����������룡����",
			                              "��Ϣ",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		//��ѯ��Ʒ���ı���������Ƿ������Product����
	   	sql="select * from Product where ProductID='"+jtxtArray[0].getText().trim()+"'";
	    db=new DataBase();
		db.selectDb(sql);//���ò������ݿ��select����				   
	    try{
		    int k=0;
			Vector<Vector> vtemp = new Vector<Vector>();
			while(db.rs.next()){//��ý����
			 	k++;
		       	Vector<String> v = new Vector<String>();
				for(int i=1;i<=8;i++){//˳��ﵽ���ѵ��Ľ���еĸ����¼
					String str = db.rs.getString(i).trim();
					str = new String(str.getBytes("ISO-8859-1"),"gb2312");//��ȡ�õ�����ת��
					v.add(str);	
				}
				vtemp.add(v);//���½�����е�����
			 }
			  if(k==0){//��Book����û�и���ţ��򵯳���ʾ�Ի���
			 	 JOptionPane.showMessageDialog(this,"û����Ҫ���ҵ�����",
				                 "��Ϣ",JOptionPane.INFORMATION_MESSAGE);
			 }
			dtm.setDataVector(vtemp,head);//���±��
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