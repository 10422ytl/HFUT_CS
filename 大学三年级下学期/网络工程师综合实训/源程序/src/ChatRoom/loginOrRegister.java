package ChatRoom;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class loginOrRegister{
	String username="";
	int userport;
	Socket socket=null;
	BufferedWriter out=null;
	volatile boolean login=false;
	public loginOrRegister(){
		login_reg();
	}
	void login_reg(){
		//��¼��� 
		JFrame frame=new JFrame("��¼ע��ҳ��");
		frame.setBounds(800,300,305,180);
		frame.setLayout(null);
		//�����û�����ǩ
		JLabel usr_name_l=new JLabel("�û���:");
		usr_name_l.setBounds(15, 0, 75, 50);
		frame.add(usr_name_l);
		//�����û����ı�
		JTextField usr_name_t=new JTextField();
		usr_name_t.setBounds(75, 10, 200, 30);
		frame.add(usr_name_t);
		//���������ǩ
		JLabel usr_pswd_l=new JLabel("����#�˿�:");
		usr_pswd_l.setBounds(10, 46, 75, 50);
		frame.add(usr_pswd_l);
		//���������ı�
		JTextField usr_pswd_t=new JTextField();
		usr_pswd_t.setBounds(75, 58, 200, 30);
		frame.add(usr_pswd_t);
		//��¼����
		JButton login_but=new JButton("��¼");
		login_but.setBounds(15, 106, 100, 30);
		frame.add(login_but);
		//����ע�ᰴ��
		JButton register_but=new JButton("ע��");
		register_but.setBounds(175, 106, 100, 30);
		frame.add(register_but);
		frame.setResizable(false);
		frame.setVisible(true);
		
		try{
			socket=new Socket("127.0.0.1",6666);
			out=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		}catch(Exception e){
			System.out.println("ע��error");
		}
		//���µ�½��ť
		login_but.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent m){
				try{
					//���û���½�������Ϣ������͵���������
					String msg="login@"+usr_name_t.getText()+"@"+usr_pswd_t.getText()+"\n";
					out.write(msg);
					out.flush();
					//���շ������˶���Ϣ�����ش�����Ϣ
					BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
					msg=in.readLine();
					String info[]=msg.split("@");
					if(info[0].equals("yes")){
						username=usr_name_t.getText();
						userport=Integer.valueOf(info[1]).intValue();
						out.close();
						in.close();
						frame.dispose();
						login=true;
					}else{
						usr_name_t.setText(info[2]);
					}
				}catch(Exception e){
					System.out.println(e);
				}
			}
			
		});
		//����ע�ᰴť
		register_but.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent m){
				try{//���û�ע���������Ϣ������͵���������
					String msg="register@"+usr_name_t.getText()+"@"+usr_pswd_t.getText()+"\n";
					out.write(msg);
					out.flush();
					//���շ������˶���Ϣ�����ش�����Ϣ
					BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
					msg=in.readLine();
					String info[]=msg.split("@");
					if(info[0].equals("yes")){
						username=usr_name_t.getText();
						userport=Integer.valueOf(info[1]).intValue();
						out.close();
						in.close();
						frame.dispose();
						//�������û������ݿ��ļ�
						createFile(username);
						login=true;
					}else{
						usr_name_t.setText(info[2]);
					}
				}catch(Exception e){
					System.out.println(e);
				}
			}
		});
	}
	
	//��ע������£����Զ������ļ�//userdata//username//friends.txt&groups.txt
	void createFile(String username){
		//path��ʾ���������ļ���·��
		String path = ".//userdata//"+username;
		File f = new File(path);
		if(!f.exists()){
			f.mkdirs();
		} 
		String fileName="friends.txt";
		File file = new File(f,fileName);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		fileName="groups.txt";//ֱ�����������filename�����¸�ֵ����
		file = new File(f,fileName);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
