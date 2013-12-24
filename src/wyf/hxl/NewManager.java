package wyf.hxl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
public class NewManager extends JPanel implements ActionListener
{
	JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	private JPanel jpt=new JPanel();
	String[] str1=new String[3];
	String mgNo;
	String sql;
	DataBase db;
	private JLabel[] jlArray=new JLabel[]
	{
		new JLabel("     ��  ��  Ա  ��"),
		new JLabel("         Ȩ       ��"),
	      new JLabel("         ��      ��")   
	};
	private JTextField[] jtxtArray=new JTextField[]
	{
		new JTextField(),
		new JTextField(),
		new JTextField()
	};
	private JButton[] jbArray=new JButton[]
	{
		new JButton("��ӹ���Ա"),
		new JButton("ɾ������Ա"),
		new JButton("�޸���Ϣ"),
		new JButton("������Ϣ")
	};
	//��������
	Vector<String> head=new Vector<String>();
	{
		head.add("����Ա��");
		head.add("Ȩ��");	
		head.add("����");	
	}
	//���²��Ӵ��������ñ��
	Vector<Vector> data=new Vector<Vector>();
    //�������ģ��
    DefaultTableModel dtm=new DefaultTableModel(data,head);
    //����Jtable����
	JTable jt=new JTable(dtm);
	//��JTable��װ����������
	JScrollPane jspn=new JScrollPane(jt);
	public NewManager(String mgNo)
	{
		this.setLayout(new GridLayout(1,1));
		this.mgNo=mgNo;
		//���������ϲ���Ϊ�ղ��ֹ�����
		jpt.setLayout(null);
		//����jspt�зָ����ĳ�ʼλ��
		jsp.setDividerLocation(110);
		//���÷ָ����Ŀ��
		jsp.setDividerSize(4);
		jsp.setTopComponent(jpt);
		jsp.setBottomComponent(jspn);
		for(int i=0;i<3;i++)
		{
			jpt.add(jtxtArray[i]);
		}
		for(int i=0;i<3;i++)
		{
			jpt.add(jlArray[i]);	
		    jlArray[i].setBounds(15+i*250,20,100,25);
		    jtxtArray[i].setBounds(115+i*250,20,150,25);
		    jtxtArray[i].addActionListener(this);
		}
		this.add(jsp);
		//�����²��Ӵ���
    	jsp.setBottomComponent(jspn);
    	//��JButton��ӽ�jpt,���ü�����
		for(int i=0;i<4;i++)
		{
			jpt.add(jbArray[i]);
			jbArray[i].setBounds(100+150*i,65,100,25);
			jbArray[i].addActionListener(this);
		}
	    //���ô���Ĵ�Сλ�ü��ɼ���
		this.setBounds(5,5,600,500);
		this.setVisible(true);
	}
	
	public void setFlag(boolean b)
	{
		jbArray[0].setEnabled(b);
		jbArray[1].setEnabled(b);		
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
    	
    	sql="select permitted from manager where mgNo='"+mgNo+"'";
    	db=new DataBase();
		db.selectDb(sql);
		String string="";
		try
		{
			while(db.rs.next())
			{
				string=db.rs.getString(1).trim();				
			}
			int p=0;
			if(string.equals("1"))
			{ p++;
				String jtxt=jtxtArray[0].getText().trim();
				if(jtxt.equals(""))
				{
					JOptionPane.showMessageDialog(this,	"���������Ա��������",
						        "��Ϣ",JOptionPane.INFORMATION_MESSAGE);
				    return;
				}
				if(e.getSource()==jbArray[0])
				{
					this.insertManager();
				}
				if(e.getSource()==jbArray[1])
				{
					this.deleteManager();
				}
				if(e.getSource()==jbArray[2])
				{
					this.updateManager();
				}
				if(e.getSource()==jbArray[3])
				{
					this.selectManager();
				}
			}
			if(p==0)
			{
				jtxtArray[0].requestDefaultFocus();
				String jtxt=jtxtArray[0].getText().trim();
				if(jtxt.equals(""))
				{
					JOptionPane.showMessageDialog(this,	"���������Ա��������",
						        "��Ϣ",JOptionPane.INFORMATION_MESSAGE);
				    return;
				}
				else if(jtxt.equals(mgNo))
				{
					if(e.getSource()==jbArray[3])
					{//��ѯ����Ա���ı��������������Ƿ������manager����
				       	sql="select * from manager where mgNo="+Integer.parseInt(jtxtArray[0].getText().trim());
				        db=new DataBase();
						db.selectDb(sql);
						SearchSell ss=new SearchSell();
					    ss.xianshi();
					}
					if(e.getSource()==jbArray[2])
					{
						String str[]=new String[3];
						int row = jt.getSelectedRow();
						if(row>=0)
						{
							for(int i=0;i<3;i++)
							{
								str[i]=jt.getValueAt(row,i).toString();
							}
							sql="update manager set password='"
							     +str[2]+"' where mgNo="+Integer.parseInt(str[0].trim());
							     
							db=new DataBase();
							int i=db.updateDb(sql);
							if(i==1)
							{
								JOptionPane.showMessageDialog(this,"�޸ĳɹ�����",
								                                   "��Ϣ!!",JOptionPane.INFORMATION_MESSAGE);
								return;											
							}
							else
							{
								JOptionPane.showMessageDialog(this,"�����²�������",
	                                   "Warning!!",JOptionPane.INFORMATION_MESSAGE);
								return;											
							}
						}
						else
						{
							JOptionPane.showMessageDialog(this,"�����²�������",
                                   "Warning!!",JOptionPane.INFORMATION_MESSAGE);
							return;								
						}	
					}
				}
				else
				{
					JOptionPane.showMessageDialog(this,	"�㲻���Բ鿴���˵���Ϣ,���������룡����",
				        "��Ϣ",JOptionPane.INFORMATION_MESSAGE);
				    return;					
				}
			
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		//�ر����ݿ�����
		db.dbClose();
    }
    public void insertManager()
    {
    		if((jtxtArray[0]!=null)&&(jtxtArray[1]!=null)&&(jtxtArray[2]!=null))
		{
			for(int i=0;i<3;i++)
			{
			    str1[i]=jtxtArray[i].getText().trim();		
			}
			sql="insert into manager(mgNo,Permitted,password) values('"+str1[0]+"','"+str1[1]+"','"
			 +str1[2]+"')";
			db=new DataBase();
			db.updateDb(sql);
			Vector<String> v = new Vector<String>();
		    for(int i=0;i<=2;i++)
		    {		
				//��ÿ����ӵ���ʱ����v
				v.add(str1[i]);
				if(i<3)
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
			JOptionPane.showMessageDialog(this,	"ѧ����Ϣ����Ϊ�գ�����",
						        "��Ϣ",JOptionPane.INFORMATION_MESSAGE);
						        return;	
		}
    }
    public void deleteManager()
    {
    	String mgNo=jtxtArray[0].getText().trim();
		if(mgNo.equals(""))
		{
			JOptionPane.showMessageDialog(this,	"ѧ�Ų���Ϊ�գ�����",
						        "��Ϣ",JOptionPane.INFORMATION_MESSAGE);
			return;			
		}
		sql="select permitted from manager where mgNo="+Integer.parseInt(mgNo);
		db=new DataBase();
		db.selectDb(sql);
		String str="";
		try
		{
			while(db.rs.next())
			{
				str=db.rs.getString(1).trim();
			}
		
			if(str.equals("1"))
			{
		    	
	        	JOptionPane.showMessageDialog(this,	"����ɾ����������Ա��Ϣ��¼����",
							        "����",JOptionPane.INFORMATION_MESSAGE);
	        	return;	
			}
			else
			{
				sql="delete from manager where mgNo="+Integer.parseInt(mgNo);
		    	db=new DataBase();
	        	db.updateDb(sql);
				//��Record�����и�ѧ�ţ��������ʾ�Ի���
				JOptionPane.showMessageDialog(this,	"�ɹ�ɾ���ù���Ա��Ϣ����",
							        "��Ϣ",JOptionPane.INFORMATION_MESSAGE);
			}
		}
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }	
    }
    public void updateManager()
    {
    	String str[]=new String[3];
		int row = jt.getSelectedRow();
		if(row>=0)
		{
			for(int i=0;i<3;i++)
			{
				str[i]=jt.getValueAt(row,i).toString();
			}
			sql="update manager set mgNo='"+str[0]+"',permitted='"+str[1]+"',password='"
			     +str[2]+"' where mgNo="+Integer.parseInt(str[0].trim());
			     
			db=new DataBase();
			db.updateDb(sql);
			JOptionPane.showMessageDialog(this,"�޸ĳɹ�����",
			                                   "��Ϣ!!",JOptionPane.INFORMATION_MESSAGE);
			return;								
		}
		if(row==-1)
		{
			JOptionPane.showMessageDialog(this,"���� '����' ��ť, Ȼ�����²��ı�������,��ѡ��������,�ٵ�� '�޸���Ϣ' ��ť����",
			                                   "Warning!!",JOptionPane.INFORMATION_MESSAGE);
			return;
		}		
    }
    public void selectManager()
    {
    	if(jtxtArray[0].getText().equals(""))
		{
			JOptionPane.showMessageDialog(this,"���벻��Ϊ�գ����������룡����",
			                              "��Ϣ",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		//��ѯ����Ա���ı��������������Ƿ������manager����
       	sql="select * from manager where mgNo="+Integer.parseInt(jtxtArray[0].getText().trim());
        db=new DataBase();
		db.selectDb(sql);				   
		SearchSell ss=new SearchSell();
	    ss.xianshi();	    	
    }
    public void manager(){
		//��ѯ����Ա���ı��������������Ƿ������manager����
       	sql="select * from manager where mgNo="+Integer.parseInt(jtxtArray[0].getText().trim());
        db=new DataBase();
		db.selectDb(sql);
		SearchSell ss=new SearchSell();
	    ss.xianshi();
	    
				    	
    }
    public static void main(String[]args)
    {
    	new NewManager("wyf");
    }
}