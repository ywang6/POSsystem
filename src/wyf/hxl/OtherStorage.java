package wyf.hxl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
public class OtherStorage extends JPanel implements ActionListener{
	DataBase db;
	String sql;//����sql����
	private JLabel[] jlArray={//������ǩ����Ϊ��ָ���ı�
		new JLabel("�Է�����"),
		new JLabel("�Է�ID"),
		new JLabel("�䶯����"),
		new JLabel("��Ʒ����"),
		new JLabel("�䶯����"),
		new JLabel("�䶯����"),
		new JLabel("���䶯��Ϣ")
	};
	private JTextField[] jtxtArray=new JTextField[]{//�����ı�������
		new JTextField(),
		new JTextField(),
		new JTextField(),
		new JTextField(),
		new JTextField(),
		new JTextField()
	};
	private JButton jb=new JButton("ȷ������");//����JButton��ť���ı�
	public OtherStorage(){
		this.setLayout(null);//�������Ĳ��ֹ�����Ϊ�ղ��ֹ�����
		for(int i=0;i<6;i++){
			this.add(jlArray[i]);//����ǩ��ӵ�����
			this.add(jtxtArray[i]);//���ı�����ӵ�����
			jtxtArray[i].addActionListener(this);//Ϊ�ı�������¼������� 
			if(i<3){//���õ�һ�б�ǩ���ı���Ĵ�Сλ��
				jlArray[i].setBounds(50,30+i*30,80,20);
		        jtxtArray[i].setBounds(130,30+i*30,120,20);
			}
			else{//���õڶ��б�ǩ���ı���Ĵ�Сλ��
				jlArray[i].setBounds(255,30+(i-3)*30,80,20);
		        jtxtArray[i].setBounds(335,30+(i-3)*30,120,20);
			}
		}
		this.add(jlArray[6]);//�ѱ�ǩ��ӵ�����
		jlArray[6].setBounds(5,5,120,20);//���ñ�ǩ�Ĵ�Сλ��		
		this.add(jb);//��JButton��ӽ�jpt
		jb.setBounds(200,120,140,25);
		jb.addActionListener(this);//���ü�����;
		//���ô���Ĵ�Сλ�ü��ɼ���
		this.setBounds(5,5,500,300);
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==jtxtArray[0]){//�¼�ԴΪ"�Է�����"�ı�����꽹�㽫��ת��"�Է�ID"�ı���
			jtxtArray[1].requestFocus();
		}
		if(e.getSource()==jtxtArray[1]){//�¼�ԴΪ"�Է�ID"�ı�����꽹�㽫��ת��"�䶯����"�ı���
			jtxtArray[2].requestFocus();
		}
		if(e.getSource()==jtxtArray[2]){//�¼�ԴΪ"�䶯����"�ı�����꽹�㽫��ת��"��Ʒ����"�ı���
			jtxtArray[3].requestFocus();
		}
		if(e.getSource()==jtxtArray[3]){//�¼�ԴΪ"��Ʒ����"�ı�����꽹�㽫��ת��"�䶯����"�ı���
			jtxtArray[4].requestFocus();
		}
		if(e.getSource()==jtxtArray[4]){//�¼�ԴΪ"�䶯����"�ı�����꽹�㽫��ת��"�䶯����"�ı���
			jtxtArray[5].requestFocus();
		} 
		if(e.getSource()==jb){//�����"�����Ϣ"��ť�ǽ�ִ����ӹ���
			String[] str=new String[6];
			for(int i=0;i<6;i++){//����ı������������
				str[i]=jtxtArray[i].getText().trim();
			}
			if(str[0].equals("")&&str[1].equals("")&&str[2].equals("")&&str[3].equals("")&&
			   str[4].equals("")&&str[5].equals("")){//�ı�������Ϊ�ս�����ʾ
			   	JOptionPane.showConfirmDialog(this,"�뽫����������¼��д����,�ٽ��и���!!",
								           "��ʾ",JOptionPane.INFORMATION_MESSAGE);
								return;   
			}
			try{
				sql="select Quantity from Sell where ProductName='"+str[3]+"'";//��������Ϣ���в�ѯ��Ʒ����
				db=new DataBase();
				db.selectDb(sql);
				boolean flag=db.rs.next();//��������Ƿ�Ϊ�ն���Ϊ�����ͱ���
				String str0=db.rs.getString(1);//ȡ�ý�����ĵ�һ����Ϣ
		    	db.rs.close();
				if(flag){//���������Ϊ��
					int i=Integer.parseInt(str0)-Integer.parseInt(str[5]);//����䶯�����Ʒ������
					String in=Integer.toString(i);
					sql="select Quantity from Stock where ProductName='"+str[3]+"'";//��������Ʒ�Ŀ������
				    DataBase db=new DataBase();
				    db.selectDb(sql);
					if(db.rs.next()){//��ø���Ʒ�Ŀ������ 
					 	String str2=db.rs.getString(1).trim();
					    int l=Integer.parseInt(str2)+Integer.parseInt(str[5]);//���¼������Ʒ�Ŀ����
					    String ln=Integer.toString(l);                   
						if(str[4].equals("�ͻ��˻�")){//����ǿͻ��˻������
							if(i==0){//�����Ϊ0��ִ��������
								String[] sql=new String[2];	
								sql[0]="delete from Sell where ProductName='"+str[3]+"'";
								sql[1]="update Stock set Quantity='"+ln+"' where ProductName='"+str[3]+"'";
							    db=new DataBase();
								db.batchSql(sql);
							}
							if(i>0){//�����>0��ִ��������
								String[] sql=new String[2];	
								sql[0]="update Sell set Quantity='"+in+"' where ProductName='"+str[3]+"'";
								sql[1]="update Stock set Quantity='"+ln+"' where ProductName='"+str[3]+"'";
							    db=new DataBase();
								db.batchSql(sql);
							}
							if(i<0){//�����<0����ʾ������Ϣ
								JOptionPane.showConfirmDialog(this,"����,��û�й�����ô��û���,���ʵ�����˻�!!",
								           "��ʾ",JOptionPane.INFORMATION_MESSAGE);
								return;
							}
						}
						else if(str[4].equals("�������˻�")){//�����̳���Ҫ��Ӧ���˻������
							if(i>=0){//�����>0����¿������
								sql="update Stock set Quantity='"+ln+"' where ProductName='"+str[3]+"'";	
							}
						    else{//�������<0,���д�����ʾ
						    	JOptionPane.showConfirmDialog(this,"����,�ⷿû����ô��û���,���ʵ�����˻�!!",
								           "��ʾ",JOptionPane.INFORMATION_MESSAGE);
								return;
						    }
						}
					}
				}
			}
			catch(Exception eee){eee.printStackTrace();}//�����쳣	
		}
	}
	public static void main(String[]args)
	{
		new OtherStorage();
	}
}