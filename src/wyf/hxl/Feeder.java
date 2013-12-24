package wyf.hxl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
public class Feeder extends JPanel implements ActionListener
{
	private JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	private JPanel jpt=new JPanel();
	DataBase db;
	String sql;
	String[] str1=new String[6];
	private JLabel[] jlArray=
	{
		new JLabel("��Ӧ������"),
		new JLabel("  �� Ӧ �� ID"),
		new JLabel("  �� Ӧ �� ��"),
		new JLabel("  ��Ӧ�̵�ַ"),
		new JLabel("  ��  ϵ  ��"),
		new JLabel("  �� ϵ �� ��"),
		new JLabel("  �� ע �� Ϣ")
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
	    new JButton("��ӹ�Ӧ��"),
	    new JButton("ɾ����Ӧ��"),
	    new JButton("�޸Ĺ�Ӧ��"),
	    new JButton("��ѯ��Ӧ��")
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
	public Feeder()
	{
		this.setLayout(new GridLayout(1,1));
		//���������ϲ���Ϊ�ղ��ֹ�����
		jpt.setLayout(null);
		//����jspt�зָ����ĳ�ʼλ��
		jsp.setDividerLocation(150);
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
			if(i==0)
			{
				jlArray[i].setBounds(5,5,100,20);
			}
			if(i>=0&&i<2)
			{
			    jlArray[i+1].setBounds(15,40+30*i,80,20);
			    jtxtArray[i].setBounds(95,40+30*i,150,20);
			    jtxtArray[i].addActionListener(this);
			}
			else if(i>=2&&i<4)
			{
				jlArray[i+1].setBounds(245,40+30*(i-2),80,20);
				jtxtArray[i].setBounds(325,40+30*(i-2),150,20);
				jtxtArray[i].addActionListener(this);
			}
			else if(i>=4&&i<6)
			{
				jlArray[i+1].setBounds(475,40+30*(i-4),80,20);
				jtxtArray[i].setBounds(555,40+30*(i-4),150,20);
				jtxtArray[i].addActionListener(this);	
			}
		}
		jpt.add(jlArray[6]);
		this.add(jsp);
		//�����²��Ӵ���
    	jsp.setBottomComponent(jspn);
		//��JButton��ӽ�jpt
		for(int i=0;i<4;i++)
		{
			jpt.add(jbArray[i]);
			jbArray[i].setBounds(100+150*i,110,110,25);
			jbArray[i].addActionListener(this);
		}	
		//���ô���Ĵ�Сλ�ü��ɼ���
		this.setBounds(5,5,700,600);
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==jtxtArray[0])
    	{
    		jtxtArray[1].requestFocus();
    	}
    	if(e.getSource()==jtxtArray[1])
    	{
    		jtxtArray[2].requestFocus();
    	}
    	if(e.getSource()==jtxtArray[2])
    	{
    		jtxtArray[3].requestFocus();
    	}
    	if(e.getSource()==jtxtArray[3])
    	{
    		jtxtArray[4].requestFocus();
    	}  
    	if(e.getSource()==jtxtArray[4])
    	{
    		jtxtArray[5].requestFocus();
    	} 
	    //�����"�����Ϣ"��ť��ִ����ӹ���
		if(e.getSource()==jbArray[0])
		{
			this.insertFeeder();
			JOptionPane.showMessageDialog(this,	"��ӳɹ�������",
						        "��Ϣ",JOptionPane.INFORMATION_MESSAGE);
            return;
		}//�����"ɾ����Ϣ"��ť��ִ��ɾ������	
		if(e.getSource()==jbArray[1])
		{
			this.deleteFeeder();
			JOptionPane.showMessageDialog(this,	"ɾ���ɹ�������",
						        "��Ϣ",JOptionPane.INFORMATION_MESSAGE);
            return;
		}
		
		if(e.getSource()==jbArray[2])
		{
			this.updateFeeder();
			JOptionPane.showMessageDialog(this,	"�޸ĳɹ�������",
						        "��Ϣ",JOptionPane.INFORMATION_MESSAGE);
            return;
		}
		//�����"��ѯ��������Ϣ"��ť��ִ���޸Ĺ���
		if(e.getSource()==jbArray[3])
		{
		    this.selectFeeder();
		    JOptionPane.showMessageDialog(this,	"��ѯ�ɹ�������",
						        "��Ϣ",JOptionPane.INFORMATION_MESSAGE);
            return;
		}
	}
		public void insertFeeder()
	{		
		for(int i=0;i<6;i++)
		{
			str1[i]=jtxtArray[i].getText().trim();		
		}
		if(!str1[0].equals("")&&!str1[1].equals("")&&!str1[2].equals("")&&!str1[3].equals("")
		   &&!str1[4].equals("")&&!str1[5].equals(""))
		{
            sql="insert into Feeder(FeederID,FeederName,Address,LinkMan,Tel,FeedRemark) values('"
			    +str1[0]+"','"+str1[1]+"','" + str1[2] + "','"+str1[3]+"','"+str1[4]+"','"+str1[5]+"')";
			db=new DataBase();
			db.updateDb(sql);
			Vector<String> v = new Vector<String>();
		    for(int i=0;i<=5;i++)
		    {		
				//��ÿ����ӵ���ʱ����v
				v.add(str1[i]);
				if(i<6)
				{
					jtxtArray[i].setText("");
				}	
		    }
		    data.add(v);
					
			//����table	
			dtm.setDataVector(data,head);
			jt.updateUI();
			jt.repaint();
			return;
		}
		else
		{
			JOptionPane.showMessageDialog(this,	"��������Ϣ����Ϊ�գ�����",
						        "��Ϣ",JOptionPane.INFORMATION_MESSAGE);
            return;	
		}
	}
	public void deleteFeeder()
	{
		String cd=jtxtArray[0].getText().trim();
		if(cd.equals(""))
		{
			JOptionPane.showMessageDialog(this,	"�����̱�Ų���Ϊ�գ�����",
						        "��Ϣ",JOptionPane.INFORMATION_MESSAGE);
			return;			
		}
		sql="select * from Feeder where FeederID='"+cd+"'";
		db=new DataBase();
		db.selectDb(sql);
		try
		{
			if(db.rs.next())
			{
		    	sql="delete from Feeder where FeederID='"+cd+"'";
		    	db=new DataBase();
	        	db.updateDb(sql);
	        	JOptionPane.showMessageDialog(this,	"�ɹ�ɾ���þ�������Ϣ��¼����",
							        "��Ϣ",JOptionPane.INFORMATION_MESSAGE);
	        	return;	
			}
			else
			{
				JOptionPane.showMessageDialog(this,	"û�иþ�������Ϣ��",
							        "��Ϣ",JOptionPane.INFORMATION_MESSAGE);
				return;			        
			}
		}
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }	
	}
	public void updateFeeder()
	{
		String str[]=new String[6];
		int row = jt.getSelectedRow();
		if(row>=0)
		{System.out.println(sql);
			for(int i=0;i<6;i++)
			{
				str[i]=jt.getValueAt(row,i).toString().trim();
			}
			sql="update Feeder set FeederName='"+str[1]+"',Address='"
			     +str[2]+"',LinkMan='"+str[3]+"',Tel='"+str[4]+"',FeedRemark='"
			     +str[5] +"' where FeederID='"+str[0]+"'";
		System.out.println(sql);	     
			db=new DataBase();
			db.updateDb(sql);
			JOptionPane.showMessageDialog(this,"�޸ĳɹ�����",
			                                   "��Ϣ!!",JOptionPane.INFORMATION_MESSAGE);
			return;								
		}
		if(row==-1)
		{
			JOptionPane.showMessageDialog(this,"����'���Ҿ�������Ϣ',�����²����ѡ��������,��� '�޸ľ�������Ϣ' ��ť!",
			                                   "Warning!!",JOptionPane.INFORMATION_MESSAGE);
			return;
		}		
	}
	public void selectFeeder()
	{
		if(jtxtArray[0].getText().equals(""))
		{
        	JOptionPane.showMessageDialog(this,"���벻��Ϊ�գ����������룡����",
			                              "��Ϣ",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		//��ѯ��Ʒ���ı���������Ƿ������Feeder����
       	sql="select * from Feeder where FeederID='"+jtxtArray[0].getText().trim()+"'";
        db=new DataBase();
		db.selectDb(sql);				   
	     try
		 {
		     int k=0;
			 Vector<Vector> vtemp = new Vector<Vector>();
			  while(db.rs.next())
			 {
			 	k++;
		       	Vector<String> v = new Vector<String>();
				//˳��ﵽ���ѵ��Ľ���еĸ����¼
				for(int i=1;i<=6;i++)
				{
					String str = db.rs.getString(i).trim();
					str = new String(str.getBytes("ISO-8859-1"),"gb2312");
					v.add(str);	
				}
				//���½�����е�����
				vtemp.add(v);
			   	
			 }
			  if(k==0)
		   	 {
			 	 //��Book����û�и���ţ��������ʾ�Ի���
			 	 JOptionPane.showMessageDialog(this,	"û����Ҫ���ҵ�����",
				                 "��Ϣ",JOptionPane.INFORMATION_MESSAGE);
			 }
			dtm.setDataVector(vtemp,head);
			jt.updateUI();
			jt.repaint();				 	
		 }
		 catch(Exception e)
		 {
		 	e.printStackTrace();
		 }	    
	}
	public static void main(String[]args)
	{
		new Feeder();
	}
}