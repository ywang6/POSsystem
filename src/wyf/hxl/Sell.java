package wyf.hxl;
import javax.swing.*;import java.awt.*;
import java.awt.event.*;import javax.swing.table.*;
import java.sql.*;import java.util.*;
import java.util.Date;
public class Sell extends JPanel implements ActionListener{
	private JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	private JPanel jpt=new JPanel();
	String[] str1=new String[10];
    DataBase db;
	String sql;
	private JLabel[] jlArray={
		new JLabel("���۵���"),new JLabel("�ͻ�����"),
		new JLabel("��  Ʒ  ID"),new JLabel("��  Ʒ  ��"),
		new JLabel("��Ʒ���"),new JLabel("������λ"),
	    new JLabel("��Ʒ����"),new JLabel("��Ʒ����"),
	    new JLabel("   ��     ��"),new JLabel("��������")
	};
	private JLabel jl=new JLabel("������Ϣ");
	private JTextField[] jtxtArray=new JTextField[]{
		new JTextField(),new JTextField(),
		new JTextField(),new JTextField(),
		new JTextField(),new JTextField(),
		new JTextField(),new JTextField(),
		new JTextField(),new JTextField()
	};
	private JButton[] jbArray={//����JButton��ť���ı�
	    new JButton("���۴���Ʒ"),
	    new JButton("���")
	};
	Vector<String> head = new Vector<String>();
	{//��������
		head.add("���۵���");head.add("�ͻ�����");
		head.add("��ƷID");head.add("��Ʒ��");
		head.add("��Ʒ���");head.add("������λ");
		head.add("��Ʒ����");head.add("��Ʒ����");
		head.add("�տ�");head.add("��������");
	}
	Vector<Vector> data=new Vector<Vector>(); 
    DefaultTableModel dtm=new DefaultTableModel(data,head);//�������ģ��  
	JTable jt=new JTable(dtm);//����Jtable��	
	JScrollPane jspn=new JScrollPane(jt);//��JTable��װ����������
	public Sell(){
		this.setLayout(new GridLayout(1,1));
		//���������ϲ���Ϊ�ղ��ֹ�����
		jpt.setLayout(null);
		//����jspt�зָ����ĳ�ʼλ��
		jsp.setDividerLocation(160);
		//���÷ָ����Ŀ��
		jsp.setDividerSize(4);
		jsp.setTopComponent(jpt);
		jsp.setBottomComponent(jspn);
		for(int i=0;i<10;i++)
		{
			
			jpt.add(jtxtArray[i]);
			jpt.add(jlArray[i]);
			if(i<5)
			{
			    jlArray[i].setBounds(15+i*140,35,60,20);
			    jtxtArray[i].setBounds(75+i*140,35,80,20);
			    jtxtArray[i].addActionListener(this);
			}
			else 
			{
				jlArray[i].setBounds(15+(i-5)*140,75,60,20);
				jtxtArray[i].setBounds(75+(i-5)*140,75,80,20);
				jtxtArray[i].addActionListener(this);
			}
		}
		jpt.add(jl);
		jl.setBounds(5,5,100,25);
		this.add(jsp);
		//�����²��Ӵ���
    	jsp.setBottomComponent(jspn);
		//��JButton��ӽ�jpt,���ü�����
		for(int i=0;i<2;i++)
		{
			jpt.add(jbArray[i]);
			jbArray[i].setBounds(250+150*i,115,120,25);
			jbArray[i].addActionListener(this);
		}		
		//���ô���Ĵ�Сλ�ü��ɼ���
		this.setBounds(5,5,600,500);
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==jtxtArray[0]){//�¼�ԴΪ"���۵���"�ı��� 
    		jtxtArray[1].requestFocus();
    	}
    	if(e.getSource()==jtxtArray[1]){//�¼�ԴΪ"�ͻ�����"�ı��� 
    		jtxtArray[2].requestFocus();
    	}
    	if(e.getSource()==jtxtArray[2]){//�¼�ԴΪ"��ƷID"�ı��� 
    		jtxtArray[3].requestFocus();
    	}
    	if(e.getSource()==jtxtArray[3]){//�¼�ԴΪ"��Ʒ��"�ı��� 
    		jtxtArray[4].requestFocus();
    	}
    	if(e.getSource()==jtxtArray[4]){//�¼�ԴΪ"��Ʒ���"�ı��� 
    		jtxtArray[5].requestFocus();
    	}   
    	if(e.getSource()==jtxtArray[5]){//�¼�ԴΪ"������λ"�ı��� 
    		jtxtArray[6].requestFocus();
    	}
    	if(e.getSource()==jtxtArray[6]){//�¼�ԴΪ"��Ʒ����"�ı��� 
    		jtxtArray[7].requestFocus();
    	} 
    	if(e.getSource()==jtxtArray[7]){//�¼�ԴΪ"��Ʒ����"�ı��� 
    		jtxtArray[8].requestFocus();
    	}
    	if(e.getSource()==jtxtArray[8]){//�¼�ԴΪ"�տ�"�ı��� 
    		jtxtArray[9].requestFocus();
    	}   
		if(e.getSource()==jbArray[0]){//�����"���۴���Ʒ"��ť��ִ����ӹ���
			this.insertSell();
		}
	   
		if(e.getSource()==jbArray[1]){ //�����"���"��ť�ǽ�ִ������ı��򣬲���ʾ�����»��﹦��	
			for(int i=0;i<10;i++){//����ı���
				jtxtArray[i].setText("");
			}
			JOptionPane.showMessageDialog(this,"�������»�����Ϣ����","��ʾ",
				     JOptionPane.INFORMATION_MESSAGE);//��ʾ�����»���
			return;
		}
	}
	public void insertSell(){
		for(int i=0;i<10;i++){//�õ��ı�����������Ϣ
				    str1[i]=jtxtArray[i].getText().trim();
		} 
		if(str1[0].equals("")&&str1[1].equals("")&&str1[2].equals("")&&str1[3].equals("")
		        &&str1[4].equals("")&&str1[5].equals("")&&str1[6].equals("")&&str1[7].equals("")
		        &&str1[8].equals("")&&str1[9].equals("")){//������Ϊ�ս�����ʾ
			JOptionPane.showMessageDialog(this,"�����������Ϣ����","��ʾ",
				     JOptionPane.INFORMATION_MESSAGE);
			return;
		}   
		sql="select ProductID from Stock where ProductID='"+str1[2]+"'";//��Stock���������Ƿ��и���Ʒ
		db=new DataBase();
		db.selectDb(sql);
		try{		
			if(db.rs.next()){//��Customer���������ͻ���
				sql="select CustomerName from Customer where CustomerNAme='"+str1[1]+"'";
		        db=new DataBase();
	        	db.selectDb(sql);
	        	boolean flag = db.rs.next();//�ò���ֵ���������Ƿ�Ϊ��
	        	db.rs.close();
	        	if(flag){//����������Ϊ�գ�ִ�в������
	        	    if(!str1[0].equals("")&&!str1[1].equals("")&&!str1[2].equals("")&&!str1[3].equals("")
			        &&!str1[4].equals("")&&!str1[5].equals("")&&!str1[6].equals("")&&!str1[7].equals("")
			        &&!str1[8].equals("")&&!str1[9].equals("")){
					    sql="insert into Sell(SellID,CustomerName,ProductID,ProductName,Spec,Unit,"
					        +"Quantity, UnitPrice,Payment,SellDate) values('"
					        +str1[0]+"','"+str1[1]+"','" + str1[2]
					        +"','"+str1[3]+"','"+str1[4]+"','"+str1[5]+"','"+str1[6]+"','"+str1[7]
					        +"','"+str1[8]+"','"+str1[9]+"')";//ִ�в���
						db=new DataBase();
						db.updateDb(sql);
						Vector<String> v = new Vector<String>();
					    for(int i=0;i<=9;i++){//��ÿ����ӵ���ʱ����v
							v.add(str1[i]);	
					    }
					    data.add(v);//��������ӽ������
						dtm.setDataVector(data,head);//����table	
						jt.updateUI();
						jt.repaint();
						JOptionPane.showMessageDialog(this,"���Ѿ��ɹ����۸û�����Ϣ����","��ʾ",
					             JOptionPane.INFORMATION_MESSAGE);//�ɹ�������ʾ
					    sql="select Quantity from Stock where ProductID='"+str1[2]+"'";//��ѯ���и���Ʒ������
				        db=new DataBase();
			        	db.selectDb(sql);
			        	db.rs.next();
					    int i=Integer.parseInt(db.rs.getString(1))-Integer.parseInt(str1[6]);//�Ը���Ʒ��������ѯ��ֵ
					    String in=Integer.toString(i);//
					    sql="update Stock set Quantity='"+in
			               +"' where ProductID='"+str1[2]+"'";//���¿��и���Ʒ������
						db=new DataBase();
						db.updateDb(sql);
						return;
		    	    }	
			    }
			    else{//�����Ϊ����ʾ
					JOptionPane.showMessageDialog(this,"�û���ȱ������","��ʾ",
					     JOptionPane.INFORMATION_MESSAGE);
			    	return;
				}		
	     	}
			 else{//����������ʾ
					JOptionPane.showMessageDialog(this,"�û���ȱ������","��ʾ",
					     JOptionPane.INFORMATION_MESSAGE);
			    	return;
			}			
	    }
	    catch(Exception e){e.printStackTrace();}//�����쳣    
	}
	public static void main(String[]args)
	{
		new Sell();
	}
}