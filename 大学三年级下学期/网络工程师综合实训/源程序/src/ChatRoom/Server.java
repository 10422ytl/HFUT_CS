package ChatRoom;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.*;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
public class Server {
	int port=6666;
	ServerSocket server_socket=null;
	//���ڱ����û���������;
	HashMap<String,String> user=null;
	boolean Is_login=false;
	public void run(){
		try{
			server_socket=new ServerSocket(port);
			user=new HashMap<String,String>();
			JFrame frame=new JFrame();
			
			frame.setVisible(true);
			frame.setSize(150, 200);
			frame.setLayout(null);
			JButton but=new JButton("�˳�");
			but.setBounds(0,0,160,160);
			frame.add(but);	
			frame.setResizable(false);
			but.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent m){
					System.exit(0);
				}	
			});
			while(true){
				Client client=new Client(server_socket.accept());
				client.start();
			}				
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	class Client extends Thread{
		Socket socket=null;
		Client(Socket socket){
			this.socket=socket;
		}
		public void run(){
			try{
				BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				BufferedWriter out=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				String login_reg;
				while(true){
					login_reg=in.readLine();
					//��½/ע��ģ�鴫������Ϣ��@�ֶ�
					String info[]=login_reg.split("@");
					//�ֶν����������
					if(info.length>3){
						out.write("no@no@�û���������Ƿ�\n");
						out.flush();
					//�ֶν��С������
					}else if(info.length<3){
						out.write("no@no@������������Ϣ\n");
						out.flush();
					//�ֶν����������
					}else{
						//������ע��ģ��ʱ
						if(info[0].equals("register")){
							//���û�ע��
							if(user.containsKey(info[1])){
								out.write("no@register@�û��Ѵ���\n");
								out.flush();
							//���û���һ��ע��
							}else{
								String port[]=info[2].split("#");
								out.write("yes@"+port[1]+"@��ӭ\n");
								out.flush();
								user.put(info[1],info[2]);	
							}
						//�����ǵ�½ģ��ʱ
						}else if(info[0].equals("login")){
							//���û���½
							if(user.containsKey(info[1])){
								if(user.get(info[1]).equals(info[2])){
									String port[]=info[2].split("#");
									out.write("yes@"+port[1]+"@��ӭ\n");
									out.flush();
							//���û��������ʱ
								}else{
									out.write("no@login@�������\n");
									out.flush();
									System.out.println("ע��ɹ�4");
								}
							//���������û�ֱ�ӵ�¼
							}else{
								out.write("no@login@�û�����������ע��\n");
								out.flush();
							}
						}
					}
				}
			}catch(Exception e){
				System.out.println(e);
			}
		}
	}
	
	public void info(){
		System.out.println("�������ѿ���");
	}
	public static void main(String args[]){
		Server server=new Server();
		server.info();
		server.run();
		
	}
}

