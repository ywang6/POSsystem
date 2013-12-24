package wyf.hxl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
public class StockStatistic extends JPanel implements ActionListener{
	private JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT);//���÷ָ��
	private JPanel jpt=new JPanel();
	DataBase db;
	private JLabel[] jlArray={//���ñ�ǩ
		new JLabel("����ѯ"),
	    new JLabel("��ѯ���")
	};
	private JTextField jtxt=new JTextField();	
	private JButton[] jbArray={//����JButton��ť���ı�
	    new JButton("��ѯ"),
	    new JButton("������Ʒ"),
	    new JButton("������Ʒ")
	};
	private JRadioButton[] jrb={new JRadioButton("              ��  Ʒ  ��",true),
	new JRadioButton(" ȫ �� �� �� �� Ϣ")};//���õ�ѡ��ť
    private ButtonGroup bg=new ButtonGroup();	
	Vector<String> head = new Vector<String>();
	{//��������
		head.add("��ⵥID");head.add("����������");
		head.add("��ƷID");head.add("��Ʒ����");
		head.add("��Ʒ���");head.add("������λ");
		head.add("�������");head.add("��ⵥ��");
		head.add("���");head.add("�������");
	}
	Vector<Vector> data=new Vector<Vector>();
    DefaultTableModel dtm=new DefaultTableModel(data,head);//�������ģ��
	JTable jt=new JTable(dtm);//����Jtable����
	JScrollPane jspn=new JScrollPane(jt);//��JTable��װ����������
	public StockStatistic(){
		this.setLayout(new GridLayout(1,1));//�������Ϊ���񲼾�	
		jpt.setLayout(null);//���������ϲ���Ϊ�ղ��ֹ�����
		jsp.setDividerLocation(135);//����jspt�зָ����ĳ�ʼλ��
		jsp.setDividerSize(4);//���÷ָ����Ŀ��
		jsp.setTopComponent(jpt);
		jsp.setBottomComponent(jspn);
	    for(int i=0;i<3;i++){//�԰�ť���г�ʼ������Ӽ�����
	    	jpt.add(jbArray[i]);
	    	jbArray[i].setBounds(65+i*135,70,115,25);
	    	jbArray[i].addActionListener(this);
	    }
	    for(int i=0;i<2;i++){
        	jrb[i].setBounds(20+i*350,30,150,20);
        	jpt.add(jrb[i]);//����ѡ��ť��ӽ��ϲ�����
        	bg.add(jrb[i]);//�ѵ�ѡ��ť��ӽ���ť��
        	jrb[i].addActionListener(this);//Ϊ��ѡ��ť��Ӽ�����
            jpt.add(jlArray[i]);  
        }	
		jlArray[0].setBounds(5,5,80,20);//���ñ�ǩ�Ĵ�Сλ��
		jtxt.setBounds(170,30,150,20);
		jlArray[1].setBounds(5,115,100,20);	
		jpt.add(jtxt);	
		this.add(jsp);//���ָ����ӽ����	
    	jsp.setBottomComponent(jspn);//�����²��Ӵ���	
		//���ô���Ĵ�Сλ�ü��ɼ���
		this.setBounds(5,5,600,500);
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==jrb[0]){//�ı��򽫵õ���꽹��
	    	jtxt.requestFocus();			
		}
		if(e.getSource()==jbArray[0])
		{//�����"��ѯ"��ť��ִ�в�ѯ����
			this.searchProduct();
		}
		if(e.getSource()==jbArray[1])
		{//�����"������Ʒ"��ť��ִ�в�ѯ������Ʒ
			this.littleProduct();
		}
		if(e.getSource()==jbArray[2])
		{//�����"������Ʒ"��ť��ִ�в�ѯ������Ʒ
			this.manyProduct();
		}
	}
	public void searchProduct(){
		String s=jtxt.getText().trim();//�õ��ı�����������
		if(jrb[0].isSelected()){//��"��Ʒ��"��ѡ��ť��ѡ�а�����в�ѯ
			String sql="select * from Stock where ProductName='"+s+"'";
			DataBase db=new DataBase();
			db.selectDb(sql);
			this.table();//���±��
		}
		if(jrb[1].isSelected()){//��"ȫ�������Ϣ"��ѡ��ť��ѡ�н������Ϣȫ��������
			String sql="select * from Stock";
			DataBase db=new DataBase();
			db.selectDb(sql);
		    this.table();//��Ϣ��ʾ
		}
	}
	public void littleProduct(){//��ѯ������Ʒ��Ϣ
		String sql="select Stock.StockID,Stock.FeederID,Stock.ProductID,"+
		"Stock.ProductName,Stock.Spec,Stock.Unit,Stock.Quantity,Stock.UnitPrice,"+
		"Stock.Payment,Stock.Stackdate from Stock,Product"+
		" where Stock.ProductID=Product.ProductID and Stock.Quantity<=Product.Min_sto";
		DataBase db=new DataBase();
		db.selectDb(sql);
		this.table();//���±��
	}
	public void manyProduct(){//��ѯ������Ʒ��Ϣ
		String sql="select Stock.StockID,Stock.FeederID,Stock.ProductID,"+
		"Stock.ProductName,Stock.Spec,Stock.Unit,Stock.Quantity,Stock.UnitPrice,"+
		"Stock.Payment,Stock.Stackdate from Stock,Product"+
		" where Stock.ProductID=Product.ProductID and Stock.Quantity>=Product.Max_sto";
		DataBase db=new DataBase();
		db.selectDb(sql);
		this.table();//���±��
	}
	public void table(){
		try{
			 Vector <Vector> v=new Vector<Vector>();
		     while(db.rs.next()){//�õ������
		       	 Vector <String> vtemp = new Vector<String>();
			     for(int i=0;i<10;i++){//�Եõ�����Ϣ����ת��    
			         String str1=new String(db.rs.getString(i+1).getBytes("ISO-8859-1"),"gb2312");
			         vtemp.add(str1);//���ӽ�����õ�����Ϣ��ӵ���ʱ����
			     }
			     v.add(vtemp);//����ʱ�����е���Ϣ��ӵ����							
		     }
		     if(v.size()==0){//�����Ϊ�յ���ʾ
		     	JOptionPane.showMessageDialog(this,"������ѯ�Ļ�����Ϣ�����ڣ���","��ʾ",
			     JOptionPane.INFORMATION_MESSAGE);
			    return;
		     } 	
			 dtm.setDataVector(v,head);//����table
			 jt.updateUI();
		     jt.repaint();
		     JOptionPane.showMessageDialog(this,"���Ѿ��ɹ���ѯ�û�����Ϣ����","��ʾ",
					     JOptionPane.INFORMATION_MESSAGE);//��ѯ�ɹ���ʾ
			 jtxt.setText("");//����ı���
			 return;        
		 }
		 catch(Exception ep){ep.printStackTrace();}//�����쳣
	}
	public static void main(String[]args)
	{
		new StockStatistic();
	}
}