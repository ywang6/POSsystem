package wyf.hxl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
public class Login extends JFrame implements ActionListener
{
	//����JPanel����
	private JPanel jp=new JPanel();
    //������ǩ��
    private JLabel []jlArray={new JLabel("�û���"),new JLabel("��  ��"),new JLabel("")};
    //������ѡ��ť
    private JRadioButton[] jrb={new JRadioButton("����",true),new JRadioButton("����Ա")};
    private ButtonGroup bg=new ButtonGroup();
    //������ť����
    private JButton[] jbArray={new JButton("��¼"),new JButton("���")};
    //�����ı���
    private JTextField jtxt=new JTextField("wyf"); 
    //���������
    private JPasswordField jpassword=new JPasswordField("hxl");
    String sql;
	DataBase db;
    public Login()
    {
    	//����JPanel�Ĳ��ֹ�����
    	jp.setLayout(null);
    	//�Ա�ǩ�밴ť�ؼ�ѭ������	
    	for(int i=0;i<2;i++)
        {
        	//���ñ�ǩ�밴ť�Ĵ�С��λ��
        	jlArray[i].setBounds(70,20+i*50,80,26);
        	
        	//����ǩ�Ͱ�ť��ӽ�JPanel������
        	jp.add(jlArray[i]);
        }
        for(int i=0;i<2;i++)
        {
        	jrb[i].setBounds(100+i*120,130,100,26);
        	jp.add(jrb[i]);
        	bg.add(jrb[i]);
        	jrb[i].addActionListener(this);
        	jbArray[i].setBounds(80+i*120,180,100,26);
        	jp.add(jbArray[i]);	
        	//Ϊ��ťע�ᶯ���¼�������
        	jbArray[i].addActionListener(this);       
        }
        //�����ı���Ĵ�Сλ��
        jtxt.setBounds(130,20,180,30);
        //���ı�����ӽ�JPanel����
        jp.add(jtxt);
        //Ϊ�ı���ע���¼�������
        jtxt.addActionListener(this);
        //���������Ĵ�Сλ��
        jpassword.setBounds(130,70,180,30);
        //���������ӽ�JPanel����
        jp.add(jpassword);
        //���������� �����ַ�
        jpassword.setEchoChar('*');
        //Ϊ�����ע�������
        jpassword.addActionListener(this);
        //����������ʾ��¼״̬�ı�ǩ�Ĵ�Сλ�ã���������ӽ�JPanel����
        jlArray[2].setBounds(10,280,300,30);
        jp.add(jlArray[2]);
        this.add(jp);
         //�Ա����logoͼƬ���г�ʼ��	
 		Image image=new ImageIcon("ico.gif").getImage();  
 		this.setIconImage(image);
        //���ô���� ��Сλ�ü��ɼ���
        this.setTitle("��¼");
        this.setResizable(false);
        this.setBounds(100,100,400,300);
        this.setVisible(true);
    }
    //ʵ��ActionListener�ӿ��еķ���
    public void actionPerformed(ActionEvent e)
    {
    	String mgno=jtxt.getText().trim();
        if(e.getSource()==jtxt)
    	{
    		//�л����뽹�㵽�����
    		jpassword.requestFocus();
    	}
    	else if(e.getSource()==jbArray[1])
    	{//�¼�ԴΪ��հ�ť
    	    //���������Ϣ
    		jlArray[2].setText("");
    		jtxt.setText("");
    		jpassword.setText("");
    		//�����뽹�����õ��ı���
    		jtxt.requestFocus();
    	}
    	else if(e.getSource()==jbArray[0])
    	{//�¼�ԴΪ����Ա��¼��ť
    	    //�ж��û����������Ƿ�ƥ��
    	    if(jrb[0].isSelected())
    	    {
	    	    this.denglu();
    	    }
    	    if(jrb[1].isSelected())
    	    {
    	    	this.denglu();
    	    }
	    //�ر����ݿ�����
		db.dbClose();   
    	}
    	
    }
    public void denglu()
    {
    	String mgno=jtxt.getText().trim();
	    if(jtxt.getText().trim().equals(""))
		{
			JOptionPane.showMessageDialog(this,"�û�������Ϊ�գ�����","��Ϣ",JOptionPane.INFORMATION_MESSAGE);
	    	return;
		}
		if(jpassword.getText().trim().equals(""))
		{
			JOptionPane.showMessageDialog(this,"�û����벻��Ϊ�գ�����","��Ϣ",JOptionPane.INFORMATION_MESSAGE);
	    	return;
		}
    	sql="select mgNo,password from manager where mgNo='"+mgno+"'";
    	
        db=new DataBase();
		db.selectDb(sql);
		try
		{
			String mgNo="";
			String password="";
			while(db.rs.next())
			{
				mgNo=db.rs.getString(1).trim();
		        password=db.rs.getString(2).trim();					
			}

	        if(jtxt.getText().trim().equals(mgNo)&&
    		String.valueOf(jpassword.getPassword()).equals(password))
    		{//��¼�ɹ�
    			jlArray[2].setText("��ϲ������¼�ɹ�������");
    			new Root(mgNo);
    			this.dispose();   			
    		}
    		else
    		{//��¼ʧ��
    			jlArray[2].setText("�Բ��𣬵�¼ʧ�ܣ�����");    
    		}
		}
	    catch(Exception e1)
	    {
	    	e1.printStackTrace();
	    }
    }
    public static void main(String[]args)
    {
    	new Login();
    }
}