package wyf.hxl;
import javax.swing.*;import java.awt.*;
import java.awt.event.*;import javax.swing.table.*;
import java.sql.*;import java.util.*;
import java.util.Date;
public class SearchStock extends JPanel implements ActionListener{
	private JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT);//���÷ָ��
	private JPanel jpt=new JPanel();
	private JLabel jl=new JLabel("��ѯ����");
	DataBase db;
	String sql;
	private JRadioButton[] jrb=new JRadioButton[]{//���õ�ѡ��ť���ı�
		new JRadioButton("        ��ⵥ��",true),
		new JRadioButton("        �� �� ��"),
		new JRadioButton("        �������"),
		new JRadioButton("        ��Ʒ����")
	};
	private ButtonGroup bg=new ButtonGroup();//������ť��	
	private JButton jb= new JButton("��ѯ��Ϣ");//����JButton��ť���ı�
	Vector<String> head = new Vector<String>();
	{//��������
		head.add("��ⵥ��");head.add("��Ӧ����");
		head.add("��ƷID");head.add("��Ʒ��");
		head.add("��Ʒ���");head.add("������λ");
		head.add("�ο�����");head.add("��ⵥ��");
		head.add("���");head.add("�����");
	}
	private JTextField[] jtxtArray=new JTextField[]{//�����ı���
		new JTextField(),new JTextField(),
		new JTextField(),new JTextField()
	};	
	Vector<Vector> data=new Vector<Vector>();//���ñ��
    DefaultTableModel dtm=new DefaultTableModel(data,head);//�������ģ��  
	JTable jt=new JTable(dtm);//����Jtable����
	JScrollPane jspn=new JScrollPane(jt);//��JTable��װ����������
	public SearchStock(){
		this.setLayout(new GridLayout(1,1));	
		jpt.setLayout(null);//���������ϲ���Ϊ�ղ��ֹ�����
		jsp.setDividerLocation(135);//����jspt�зָ����ĳ�ʼλ��
		jsp.setDividerSize(4);//���÷ָ����Ŀ��
		jsp.setTopComponent(jpt);
		jsp.setBottomComponent(jspn);//�����²�����
		jpt.add(jl);
		jl.setBounds(5,5,100,20);
		for(int i=0;i<4;i++){
        	bg.add(jrb[i]);//����ѡ��ť��ӽ���ť��
	        jpt.add(jrb[i]);//����ѡ��ť��ӽ��ϲ�����
		    jpt.add(jtxtArray[i]);//���ı�����ӽ��ϲ�����
		    jrb[i].addActionListener(this);//Ϊ��ѡ��ťע�������
			if(i>=0&&i<2){//��ʼ����1�е�ѡ��ť���ı���Ϊ�ı���ע�������
			    jrb[i].setBounds(15,30+30*i,120,20);
			    jtxtArray[i].setBounds(135,30+30*i,150,20);
			    jtxtArray[i].addActionListener(this);
			}
			else if(i>=2&&i<4){//��ʼ����2�е�ѡ��ť���ı���Ϊ�ı���ע�������
				jrb[i].setBounds(285,30+30*(i-2),120,20);
				jtxtArray[i].setBounds(405,30+30*(i-2),150,20);
				jtxtArray[i].addActionListener(this);
			}
		}
		this.add(jsp);	
    	jsp.setBottomComponent(jspn);//�����²��Ӵ���
		jpt.add(jb);//��JButton��ӽ�jpt
		jb.setBounds(205,100,150,20);
		jb.addActionListener(this);	//���ü�����		
		this.setBounds(5,5,600,500);//���ô���Ĵ�Сλ��
		this.setVisible(true);//���ô���Ŀɼ���
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==jrb[0]){//�¼�ԴΪ"��ⵥ��"��ť
			jtxtArray[0].requestFocus();
		}
		if(e.getSource()==jrb[1]){//�¼�ԴΪ"������"��ť
			jtxtArray[1].requestFocus();
		}
		if(e.getSource()==jrb[2]){//�¼�ԴΪ"�������"��ť
			jtxtArray[2].requestFocus();
		} 
		if(e.getSource()==jrb[3]){//�¼�ԴΪ"��Ʒ����"��ť
			jtxtArray[3].requestFocus();
		} 
		if(e.getSource()==jb){//�����"��ѯ��Ϣ"��ť��ִ�в�ѯ����
	        if(jrb[0].isSelected()){//����ⵥ�Ų�ѯ
	        	String str=jtxtArray[0].getText().trim();
	        	sql="select * from Stock where StockID='"+str+"'";
	        	db=new DataBase();
		        db.selectDb(sql);//����select����
			    this.table();
	        }
	        if(jrb[1].isSelected()){//�������̲�ѯ
	        	String str=jtxtArray[1].getText().trim();
	        	sql="select * from Stock where FeederID='"+str+"'";
	        	db=new DataBase();
		        db.selectDb(sql);//����select����
			    this.table();
	        }
	        if(jrb[2].isSelected()){//��������ڲ�ѯ
	        	String str0=jtxtArray[2].getText().trim();
	        	sql="select * from Stock where Stackdate='"+str0+"'";
	            db=new DataBase();
			    db.selectDb(sql);//����select����
			        this.table();
	        }
	        if(jrb[3].isSelected()){//����Ʒ���Ʋ�ѯ
	        	String str=jtxtArray[3].getText().trim();
	        	sql="select * from Stock where ProductName='"+str+"'";
	        	db=new DataBase();
		        db.selectDb(sql);//����select����
			    this.table();
	        }
		}
	}
	public void table(){
		try
		{
			if(db.rs.next()){
				Vector <String> v=new Vector <String>();
		        for(int i=0;i<10;i++){//Ϊ���������ת�벢������ӽ���ʱ����
		            String str1=new String(db.rs.getString(i+1).getBytes("ISO-8859-1"),"gb2312");
		        	v.add(str1);
		        }
		         data.add(v);//����Ϣ��ӽ����
				dtm.setDataVector(data,head);//����table	
				jt.updateUI();
				jt.repaint();
				JOptionPane.showMessageDialog(this,"���Ѿ��ɹ���ѯ�û�����Ϣ����","��ʾ",
			             JOptionPane.INFORMATION_MESSAGE);//��ѯ�ɹ���ʾ
			    for(int i=0;i<4;i++){//����ı���
			     	 jtxtArray[3].setText("");
			    }
				return;
			}
			else{//��ѯʧ����ʾ
	        	JOptionPane.showMessageDialog(this,"������ѯ�Ļ�����Ϣ�����ڣ���","��ʾ",
			     JOptionPane.INFORMATION_MESSAGE);
				return;
	        }
		}
		catch(Exception ep){ep.printStackTrace();}//�����쳣
	}
	public static void main(String[]args)
	{
		new SearchStock();
	}
}