package wyf.hxl;
import javax.swing.*;import java.awt.*;
import java.awt.event.*;import javax.swing.table.*;
import java.sql.*;import java.util.*;
import java.util.Date;import javax.swing.event.*;
public class Stock extends JPanel implements ActionListener{
	private JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT);//�ָ��Ϊ����
	private JPanel jpt=new JPanel();//�������
	String[] str1=new String[10];
    DataBase db;
	String sql;//����sql����
	private JLabel[] jlArray={//������ǩ���������ı�
		new JLabel("  ��ⵥ��"),new JLabel("  ��Ӧ����"),
		new JLabel("  ��  Ʒ  ID"),new JLabel("  ��  Ʒ  ��"),
		new JLabel("  ��Ʒ���"),new JLabel("  ������λ"),
	    new JLabel("  �������"),new JLabel("  ��ⵥ��"),
	    new JLabel("     ��    ��"),new JLabel("  �������")
	};
	private JLabel jl=new JLabel("�����Ϣ");//������ǩ
	private JTextField[] jtxtArray=new JTextField[]
	{//�����ı���
		new JTextField(),new JTextField(),
		new JTextField(),new JTextField(),
		new JTextField(),new JTextField(),
		new JTextField(),new JTextField(),
		new JTextField(),new JTextField()
	};	
	private JButton[] jbArray={//����JButton��ť
	    new JButton("��Ӵ���Ʒ���"),
	    new JButton("���")
	};
	Vector<String> head=new Vector<String>();
	{//��������
		head.add("��ⵥ��");head.add("��������");
		head.add("��ƷID");head.add("��Ʒ����");
		head.add("��Ʒ���");head.add("������λ");
		head.add("�������");head.add("��ⵥ��");
		head.add("���");head.add("���ʱ��");
	}	
	Vector<Vector> data=new Vector<Vector>();//���²��Ӵ��������ñ��
    DefaultTableModel dtm=new DefaultTableModel(data,head);//�������ģ��    
	JTable jt=new JTable(dtm);//����Jtable����	
	JScrollPane jspn=new JScrollPane(jt);//��JTable��װ����������
	public Stock(){
		this.setLayout(new GridLayout(1,1));		
		jpt.setLayout(null);//���������ϲ���Ϊ�ղ��ֹ�����	
		jsp.setDividerLocation(170);//����jspt�зָ����ĳ�ʼλ��	
		jsp.setDividerSize(4);//���÷ָ����Ŀ��
		jsp.setTopComponent(jpt);
		jsp.setBottomComponent(jspn);
		for(int i=0;i<10;i++){
			jpt.add(jtxtArray[i]);//���ı�����ӽ������ϲ�
			jpt.add(jlArray[i]);//����ǩ��ӽ������ϲ�
			if(i<5){//���õ�1�б�ǩ���ı���Ĵ�Сλ�ò�Ϊ�ı���ע�������
			    jlArray[i].setBounds(15+i*140,35,60,20);
			    jtxtArray[i].setBounds(75+i*140,35,80,20);
			    jtxtArray[i].addActionListener(this);
			}
			else {//���õ�2�б�ǩ���ı���Ĵ�Сλ�ò�Ϊ�ı���ע�������
				jlArray[i].setBounds(15+(i-5)*140,75,60,20);
				jtxtArray[i].setBounds(75+(i-5)*140,75,80,20);
				jtxtArray[i].addActionListener(this);
			}
		}
		jpt.add(jl);//��jl��ǩ��ӽ�����ϲ�
		jl.setBounds(5,5,100,25);
		this.add(jsp);//���ָ����ӽ�����	
    	jsp.setBottomComponent(jspn);//�����²��Ӵ���	
		for(int i=0;i<2;i++){
			jpt.add(jbArray[i]);//��JButton��ӽ�jpt
			jbArray[i].setBounds(240+150*i,125,130,25);//���ô�Сλ��
			jbArray[i].addActionListener(this);//ע�������
		}	
		this.setBounds(5,5,600,500);//���ô���Ĵ�Сλ��
		this.setVisible(true);//���ô���Ŀɼ���
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==jtxtArray[0]){//�¼�ԴΪ"��ⵥ��"�ı���"��������"�ı��򽫻����꽹��
			jtxtArray[1].requestFocus();
		}
		if(e.getSource()==jtxtArray[1]){//�¼�ԴΪ"��������"�ı���"��ƷID"�ı��򽫻����꽹��
			jtxtArray[2].requestFocus();
		}
		if(e.getSource()==jtxtArray[2]){//�¼�ԴΪ"��ƷID"�ı���"��Ʒ����"�ı��򽫻����꽹��
			jtxtArray[3].requestFocus();
		}
		if(e.getSource()==jtxtArray[3]){//�¼�ԴΪ"��Ʒ����"�ı���"��Ʒ���"�ı��򽫻����꽹��
			jtxtArray[4].requestFocus();
		}
		if(e.getSource()==jtxtArray[4]){//�¼�ԴΪ"��Ʒ���"�ı���"������λ"�ı��򽫻����꽹��
			jtxtArray[5].requestFocus();
		}   
		if(e.getSource()==jtxtArray[5]){//�¼�ԴΪ"������λ"�ı���"�������"�ı��򽫻����꽹��
			jtxtArray[6].requestFocus();
		}
		if(e.getSource()==jtxtArray[6]){//�¼�ԴΪ"�������"�ı���"��ⵥ��"�ı��򽫻����꽹��
			jtxtArray[7].requestFocus();
		} 
		if(e.getSource()==jtxtArray[7]){//�¼�ԴΪ"��ⵥ��"�ı���"���"�ı��򽫻����꽹��
			jtxtArray[8].requestFocus();
		}
		if(e.getSource()==jtxtArray[8]){//�¼�ԴΪ"���"�ı���"���ʱ��"�ı��򽫻����꽹��
			jtxtArray[9].requestFocus();
		}   
		if(e.getSource()==jbArray[0]){//�����"��Ӵ���Ʒ���"��ť�ǽ�ִ����ӹ���
			this.insertStock();
		}
		if(e.getSource()==jbArray[1]){//�����"���"��ť�ǽ�����ı��򣬲���ʾ�����»���	
			for(int i=0;i<10;i++){//ִ�����
				jtxtArray[i].setText("");
			}
			JOptionPane.showMessageDialog(this,"�������»�����Ϣ����","��ʾ",
				     JOptionPane.INFORMATION_MESSAGE);//��ʾ��������Ϣ
			return;
		}
	}
	public void insertStock(){
		for(int i=0;i<10;i++){//��ô��ı������������Ϣ
		    str1[i]=jtxtArray[i].getText().trim();
		} 
		if(str1[0].equals("")&&str1[1].equals("")&&str1[2].equals("")&&str1[3].equals("")
		        &&str1[4].equals("")&&str1[5].equals("")&&str1[6].equals("")
		        &&str1[7].equals("")&&str1[8].equals("")&&str1[9].equals("")){//�ı���Ϊ�ս�����ʾ
			JOptionPane.showMessageDialog(this,"�����������Ϣ����","��ʾ",
				     JOptionPane.INFORMATION_MESSAGE);
			return;
		}   
		sql="select ProductID from Product where ProductID='"+str1[2]+"'";//��ѯ����Ʒ
		db=new DataBase();
		db.selectDb(sql);
		try{		
			if(db.rs.next()){//�������Ϊ�ս��й�Ӧ�������������ѱ�����
				sql="select FeederName from Feeder where FeederName='"+str1[1]+"'";
		        db=new DataBase();
	        	db.selectDb(sql);
	        	if(db.rs.next()){//��������Ӧ����
	        	    if(!str1[0].equals("")&&!str1[1].equals("")&&!str1[2].equals("")&&!str1[3].equals("")
			        &&!str1[4].equals("")&&!str1[5].equals("")&&!str1[6].equals("")&&!str1[7].equals("")
			        &&!str1[8].equals("")&&!str1[9].equals("")){//����ı����������Ϊ������в������
					    sql="insert into Stock(StockID,FeederID,ProductID,ProductName,Spec,Unit,"
					        +"Quantity, UnitPrice,Payment,Stackdate) values('"
					        +str1[0]+"','"+str1[1]+"','" + str1[2]
					        +"','"+str1[3]+"','"+str1[4]+"','"+str1[5]+"','"+str1[6]+"','"+str1[7]
					        +"','"+str1[8]+"','"+str1[9]+"')";//ִ�в������
						db=new DataBase();
						db.updateDb(sql);
						Vector<String> v = new Vector<String>();
					    for(int i=0;i<=9;i++){//��ÿ����ӵ���ʱ����v
							v.add(str1[i]);	
					    }
					    data.add(v);//����ʱ�������Ϣ��ӽ����
						dtm.setDataVector(data,head);//����table	
						jt.updateUI();
						jt.repaint();
						JOptionPane.showMessageDialog(this,"���Ѿ��ɹ���Ӹû�����Ϣ����","��ʾ",
					     JOptionPane.INFORMATION_MESSAGE);//�ɹ������ʾ
						return;
		    	    }	
			    }
			    else{//����ʧ����ʾ
					JOptionPane.showMessageDialog(this,"����ʧ�ܣ������²�������","��ʾ",
					     JOptionPane.INFORMATION_MESSAGE);
			    	return;
				}		
	     	}
			 else{//����Ʒ�Ĺ�Ӧ�̲����ڽ�����ʾ
					JOptionPane.showMessageDialog(this,"����ӵĻ��ﱾ��˾�ݲ���Ҫ����","��ʾ",
					     JOptionPane.INFORMATION_MESSAGE);
			    	return;
				}			
	    }
	    catch(Exception e){e.printStackTrace();}// �����쳣   
	}
	public static void main(String[]args)
	{
		new Stock();
	}
}