package wyf.hxl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
public class SearchSell extends JPanel implements ActionListener
{
	private JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	private JPanel jpt=new JPanel();
	DataBase db;
	String sql;
	private JLabel jl=new JLabel("��ѯ����");
	private JRadioButton[] jrb=new JRadioButton[]
	{
		new JRadioButton("        ���۵���",true),
		new JRadioButton("        ��       ��"),
		new JRadioButton("        ��������"),
		new JRadioButton("        ��Ʒ����")
	};
	private ButtonGroup bg=new ButtonGroup();
	//����JButton��ť���ı�
	private JButton jb= new JButton("��ѯ��Ϣ");
	Vector<String> head = new Vector<String>();
	{//��������
		head.add("���۵���");
		head.add("�ͻ�����");
		head.add("��ƷID");
		head.add("��Ʒ��");
		head.add("��Ʒ���");
		head.add("������λ");
		head.add("��Ʒ����");
		head.add("��Ʒ����");
		head.add("�տ�");
		head.add("��������");
	}
	private JTextField[] jtxtArray=new JTextField[]
	{//�����ı���
		new JTextField(),
		new JTextField(),
		new JTextField(),
		new JTextField()
	};
	//���²��Ӵ��������ñ��
	Vector<Vector> data=new Vector<Vector>();
    //�������ģ��
    DefaultTableModel dtm=new DefaultTableModel(data,head);
    //����Jtable����
	JTable jt=new JTable(dtm);
	//��JTable��װ����������
	JScrollPane jspn=new JScrollPane(jt);
	public SearchSell()
	{
		this.setLayout(new GridLayout(1,1));
		//���������ϲ���Ϊ�ղ��ֹ�����
		jpt.setLayout(null);
		//����jspt�зָ����ĳ�ʼλ��
		jsp.setDividerLocation(140);
		//���÷ָ����Ŀ��
		jsp.setDividerSize(4);
		jsp.setTopComponent(jpt);
		jsp.setBottomComponent(jspn);
		jpt.add(jl);
		jl.setBounds(5,5,100,20);
		for(int i=0;i<4;i++)
		{
			bg.add(jrb[i]);//����ѡ��ť��ӽ���ť��
			jpt.add(jrb[i]);//����ѡ��ť��ӵ��ϲ�����
		    jpt.add(jtxtArray[i]);
		    jrb[i].addActionListener(this);//Ϊ�ı�����Ӽ�����
			if(i>=0&&i<2)
			{//��ʼ����1�е�ѡ��ť���ı��򣬲�Ϊ�ı�����Ӽ�����
			    jrb[i].setBounds(15,30+30*i,120,20);
			    jtxtArray[i].setBounds(135,30+30*i,150,20);
			    jtxtArray[i].addActionListener(this);
			}
			else if(i>=2&&i<4)
			{//��ʼ����2�е�ѡ��ť���ı��򣬲�Ϊ�ı�����Ӽ�����
				jrb[i].setBounds(285,30+30*(i-2),120,20);
				jtxtArray[i].setBounds(405,30+30*(i-2),150,20);
				jtxtArray[i].addActionListener(this);
			}
		}
			
		this.add(jsp);
		//�����²��Ӵ���
    	jsp.setBottomComponent(jspn);
		//��JButton��ӽ�jpt�����ü�����
		jpt.add(jb);
		jb.setBounds(200,100,140,25);
		jb.addActionListener(this);		
		//���ô���Ĵ�Сλ�ü��ɼ���
		this.setBounds(5,5,600,500);
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==jrb[0]){//��ѡ��"���۵���"��ѡ��ť����꽹�㵽����Ӧ�ı���
			jtxtArray[0].requestFocus();
		}
		if(e.getSource()==jrb[1]){//��ѡ��"�ͻ�"��ѡ��ť����꽹�㵽����Ӧ�ı���
			jtxtArray[1].requestFocus();
		}
		if(e.getSource()==jrb[2]){//��ѡ��"��������"��ѡ��ť����꽹�㵽����Ӧ�ı���
			jtxtArray[2].requestFocus();
		} 
		if(e.getSource()==jrb[3]){//��ѡ��"��Ʒ����"��ѡ��ť����꽹�㵽����Ӧ�ı���
			jtxtArray[3].requestFocus();
		}  
		if(e.getSource()==jb){//�����"��ѯ��Ϣ"��ť��ִ�в�ѯ����
	        if(jrb[0].isSelected()){//�¼�ԴΪ"���۵���"��ѡ��ť
	        	String str=jtxtArray[0].getText().trim();
	        	sql="select * from Sell where SellID='"+str+"'";//�����۵��Ų�ѯ
	        	db=new DataBase();
		        db.selectDb(sql);
			    this.xianshi();//������ʾ����
	        }
	        if(jrb[1].isSelected()){//"�ͻ�"��ѡ��ť
	        	String str=jtxtArray[1].getText().trim();
	        	sql="select * from Customer where CustomerID='"+str+"'";//���ͻ���ѯ
	        	db=new DataBase();
		        db.selectDb(sql);
			    this.xianshi();//������ʾ����
	        }
	        if(jrb[2].isSelected()){//"��������"��ѡ��ť
	        	String str0=jtxtArray[2].getText().trim();
	        	sql="select * from Sell where SellDate='"+str0+"'";//���������ڲ�ѯ
	            db=new DataBase();
			    db.selectDb(sql);
	            this.xianshi();//������ʾ����
	        }
	        if(jrb[3].isSelected()){//"��Ʒ����"��ѡ��ť
	        	String str=jtxtArray[3].getText().trim();
	        	sql="select * from Sell where ProductName='"+str+"'";//����Ʒ���Ʋ�ѯ
	        	db=new DataBase();
		        db.selectDb(sql);
			    this.xianshi();//������ʾ����
	        }
		}
	}
	public void xianshi(){
	      try{
		       if(db.rs.next()){//��ý����		        	
		        	Vector <String> v=new Vector <String>();
			        for(int i=0;i<10;i++){//�Եõ������ݽ���ת��
			            String str1=new String(db.rs.getString(i+1).getBytes("ISO-8859-1"),"gb2312");
			        	v.add(str1);//�Ѵӽ������õ�������ӵ���ʱ����
			        }
			        data.add(v);//����ʱ�����е�������ӽ����							
					dtm.setDataVector(data,head);//����table	
					jt.updateUI();
					jt.repaint();
					JOptionPane.showMessageDialog(this,"���Ѿ��ɹ���ѯ����Ʒ������Ϣ����","��ʾ",
				            JOptionPane.INFORMATION_MESSAGE);//��ѯ�ɹ���ʾ
				    for(int i=0;i<4;i++){//����ı���
				     	jtxtArray[i].setText("");
				    }
	                return;
		        }
		        else{//��ѯʧ����ʾ
		        	JOptionPane.showMessageDialog(this,"������ѯ����Ʒ������Ϣ�����ڣ���","��ʾ",
				     JOptionPane.INFORMATION_MESSAGE);
					return;
		        }
		  }
	      catch(Exception ep){ep.printStackTrace();}//�����쳣
	}
	public static void main(String[]args)
	{
		new SearchSell();
	}
}