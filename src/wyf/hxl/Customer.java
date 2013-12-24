package wyf.hxl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
public class Customer extends JPanel implements ActionListener
{
	private JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	private JPanel jpt=new JPanel();
	String[] str1=new String[6];
	DataBase db;
	String sql;
	private JLabel[] jlArray=
	{
		new JLabel("�ͻ�����"),
		new JLabel("  ��  ��  ID"),
		new JLabel("  ��  ��  ��"),
		new JLabel("  �ͻ���ַ"),
		new JLabel("  ��  ϵ  ��"),
		new JLabel("  ��ϵ�绰"),
		new JLabel("  ��ע��Ϣ")
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
	//����JButton��ť���ı�
	private JButton[] jbArray=
	{
	    new JButton("��ӿͻ�"),
	    new JButton("ɾ���ͻ�"),
	    new JButton("�޸Ŀͻ�"),
	    new JButton("��ѯ�ͻ�")
	};
	//��������
	Vector<String> head = new Vector<String>();
	{
		head.add("�ͻ�ID");
		head.add("�ͻ���");
		head.add("�ͻ���ַ");
		head.add("��ϵ��");
		head.add("��ϵ�绰");
		head.add("��ע��Ϣ");
	}
	//���²��Ӵ��������ñ��
	Vector<Vector> data=new Vector<Vector>();
    //�������ģ��
    DefaultTableModel dtm=new DefaultTableModel(data,head);
    //����Jtable����
	JTable jt=new JTable(dtm);
	//��JTable��װ����������
	JScrollPane jspn=new JScrollPane(jt);
	public Customer()
	{
		this.setLayout(new GridLayout(1,1));
		//���������ϲ���Ϊ�ղ��ֹ�����
		jpt.setLayout(null);
		//����jspt�зָ����ĳ�ʼλ��
		jsp.setDividerLocation(155);
		//���÷ָ����Ŀ��
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
			jpt.add(jlArray[6]);
			if(i==0)
			{
				jlArray[i].setBounds(5,5,100,20);
			}
			if(i>=0&&i<2)
			{
			    jlArray[i+1].setBounds(10,40+30*i,80,20);
			    jtxtArray[i].setBounds(90,40+30*i,150,20);
			    jtxtArray[i].addActionListener(this);
			}
			else if(i>=2&&i<4)
			{
				jlArray[i+1].setBounds(240,40+30*(i-2),80,20);
				jtxtArray[i].setBounds(320,40+30*(i-2),150,20);
				jtxtArray[i].addActionListener(this);
			}
			else if(i>=4&&i<6)
			{
				jlArray[i+1].setBounds(470,40+30*(i-4),80,20);
				jtxtArray[i].setBounds(550,40+30*(i-4),150,20);
				jtxtArray[i].addActionListener(this);	
			}
		}
		this.add(jsp);
		//�����²��Ӵ���
    	jsp.setBottomComponent(jspn);
		//��JButton��ӽ�jpt
		for(int i=0;i<4;i++)
		{
			jpt.add(jbArray[i]);
			jbArray[i].setBounds(100+150*i,110,100,25);
		}
		//���ü�����
		for(int i=0;i<4;i++)
		{
			jbArray[i].addActionListener(this);
		}		
		//���ô���Ĵ�Сλ�ü��ɼ���
		this.setBounds(5,5,600,500);
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==jtxtArray[0]){//�¼�ԴΪ"�ͻ�ID"�ı���
			jtxtArray[1].requestFocus();
		}
		if(e.getSource()==jtxtArray[1]){//�¼�ԴΪ"�ͻ���"�ı���
			jtxtArray[2].requestFocus();
		}
		if(e.getSource()==jtxtArray[2]){//�¼�ԴΪ"�ͻ���ַ"�ı���
			jtxtArray[3].requestFocus();
		}
		if(e.getSource()==jtxtArray[3]){//�¼�ԴΪ"��ϵ��"�ı���
			jtxtArray[4].requestFocus();
		}  
		if(e.getSource()==jtxtArray[4]){//�¼�ԴΪ"��ϵ�绰"�ı���
			jtxtArray[5].requestFocus();
		}    
		if(e.getSource()==jbArray[0]){//�����"�����Ϣ"��ť��ִ����ӹ���
			this.insertCustomer();
			JOptionPane.showMessageDialog(this,	"���ѳɹ���ӣ�����",
					"��Ϣ",JOptionPane.INFORMATION_MESSAGE);//��ӳɹ���ʾ
	        return;	
		}
		if(e.getSource()==jbArray[1]){//�����"ɾ����Ϣ"����ִ��ɾ������	
			this.deleteCustomer();
			JOptionPane.showMessageDialog(this,	"���ѳɹ�ɾ��������",
					"��Ϣ",JOptionPane.INFORMATION_MESSAGE);//ɾ���ɹ���ʾ
	        return;
		}	
		if(e.getSource()==jbArray[2]){//�����"�޸Ŀͻ���Ϣ"��ť��ִ���޸Ĺ���
		    this.updateCustomer();
		    JOptionPane.showMessageDialog(this,	"�����޸ĳɹ�������",
					 "��Ϣ",JOptionPane.INFORMATION_MESSAGE);//�޸ĳɹ���ʾ
	        return;
		}	
		if(e.getSource()==jbArray[3]){//�����"��ѯ�ͻ���Ϣ"��ť��ִ�в�ѯ����
		    this.selectCustomer();
		}
	}
	public void insertCustomer(){		
		for(int i=0;i<6;i++){//����ı������������Ϣ
			str1[i]=jtxtArray[i].getText().trim();		
		}
		if(!str1[0].equals("")&&!str1[1].equals("")&&!str1[2].equals("")&&!str1[3].equals("")
		&&!str1[4].equals("")&&!str1[5].equals("")){//����ı������Ϊ��ִ�в��빦��
	        sql="insert into Customer(CustomerID,CustomerName,Address,LinkMan,"
	                +"Tel,CustomerRemark) values('"+str1[0]+"','"+str1[1]+"','" 
	                + str1[2] + "','"+str1[3]+"','"+str1[4]+"','"+str1[5]+"')";
			db=new DataBase();
			db.updateDb(sql);//�������ݿ���뷽��
			Vector<String> v = new Vector<String>();
		    for(int i=0;i<=5;i++){//��ÿ����ӵ���ʱ����v
				v.add(str1[i]);
				if(i<6){jtxtArray[i].setText("");}	
		    }
		    data.add(v);//����ʱ��������Ϣ��ӵ������		
			dtm.setDataVector(data,head);//����table	
			jt.updateUI();
			jt.repaint();
			return;
		}
		else{//��Ϣ��Ϊ����ʾ
			JOptionPane.showMessageDialog(this,	"�˿���Ϣ����Ϊ�գ�����",
						        "��Ϣ",JOptionPane.INFORMATION_MESSAGE);
	        return;	
		}
	}
	public void deleteCustomer(){
		String cd=jtxtArray[0].getText().trim();
		if(cd.equals("")){//���ͻ�ID�ı�������Ϊ�ս�����ʾ
			JOptionPane.showMessageDialog(this,	"�˿ͱ�Ų���Ϊ�գ�����",
						        "��Ϣ",JOptionPane.INFORMATION_MESSAGE);
			return;			
		}
		sql="select * from Customer where CustomerID='"+cd+"'";//�����ݿ������ͻ���Ϣ
		db=new DataBase();
		db.selectDb(sql);
		try{
			if(db.rs.next()){//��ý����
		    	sql="delete from Customer where CustomerID='"+cd+"'";//ִ��ɾ������
		    	db=new DataBase();
	        	db.updateDb(sql);
	        	JOptionPane.showMessageDialog(this,	"�ɹ�ɾ���ù˿���Ϣ��¼����",
						 "��Ϣ",JOptionPane.INFORMATION_MESSAGE);//ɾ���ɹ���ʾ
	        	return;	
			}
			else{//�����Ϊ�գ���ʾ
				JOptionPane.showMessageDialog(this,	"û�иù˿ͣ�",
							        "��Ϣ",JOptionPane.INFORMATION_MESSAGE);
				return;			        
			}
		}
	    catch(Exception e){e.printStackTrace();}	
	}
	public void updateCustomer(){
		String str[]=new String[6];
		int row=jt.getSelectedRow();//ѡ�����һ��
		if(row>=0){//ѡ�б���е�һ��
			for(int i=0;i<6;i++){//�õ���ѡ�е���Ϣ
				str[i]=jt.getValueAt(row,i).toString().trim();
			}
			sql="update Customer set CustomerName='"+str[1]+"',Address='"
			     +str[2]+"',LinkMan='"+str[3]+"',Tel='"+str[4]+"',CustomerRemark='"
			     +str[5] +"' where CustomerID='"+str[0]+"'";//	���¿ͻ���Ϣ     
			db=new DataBase();
			db.updateDb(sql);//�������ݿ�ĸ��·���
			JOptionPane.showMessageDialog(this,"�޸ĳɹ�����",
			          "��Ϣ!!",JOptionPane.INFORMATION_MESSAGE);//�޸ĳɹ���ʾ
			return;								
		}
		if(row==-1){//���û������������ʾ
			JOptionPane.showMessageDialog(this,"�뵥��'���ҿͻ���Ϣ',���ڱ��ѡ��������,���� '�޸Ŀͻ���Ϣ' ��ť!",
			                                   "Warning!!",JOptionPane.INFORMATION_MESSAGE);
			return;
		}		
	}
	public void selectCustomer(){
		if(jtxtArray[0].getText().equals("")){//������Ϊ�յĴ���
	    	JOptionPane.showMessageDialog(this,"���벻��Ϊ�գ����������룡����",
			                              "��Ϣ",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		//��֤�ͻ�ID�ı���������Ŀͻ���Ϣ�Ƿ������Product����
	   	sql="select * from Product where ProductID='"+jtxtArray[0].getText().trim()+"'";
	    db=new DataBase();
		db.selectDb(sql);				   
	     try{//�Խ��������try����
		     int k=0;
			 Vector<Vector> vtemp = new Vector<Vector>();
			  while(db.rs.next()){//�õ������
			 	k++;
		       	Vector<String> v = new Vector<String>();
				for(int i=1;i<=6;i++){//˳��ﵽ���ѵ��Ľ���еĸ����¼
					String str = db.rs.getString(i).trim();
					str = new String(str.getBytes("ISO-8859-1"),"gb2312");//ת��
					v.add(str);	
				}
				vtemp.add(v);//���½�����е�����
			   	
			 }
			  if(k==0){//��Book����û�и���ţ��������ʾ�Ի���
			 	 JOptionPane.showMessageDialog(this,	"û����Ҫ���ҵ�����",
				                 "��Ϣ",JOptionPane.INFORMATION_MESSAGE);
			 }
			dtm.setDataVector(vtemp,head);//���±��
			jt.updateUI();
			jt.repaint();				 	
		 }
		 catch(Exception e){e.printStackTrace();}// �����쳣  
	}
	public static void main(String[]args)
	{
		new Customer();
	}
}