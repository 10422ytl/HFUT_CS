package ChatRoom;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
public class friendDialogFrame {
	int port_num;
	String username;
	String friendname;
	InetAddress inetaddress;
	private DatagramSocket datagramsocket;
	JTextArea text_area;
	
    public friendDialogFrame(friend f,String user_name,JButton enter_but,DatagramSocket datagramsocket){
		friendname=f.friend_name;
		this.port_num=f.friend_port;
		username=user_name;
		this.datagramsocket=datagramsocket;
		inetaddress=f.friend_inetAddress;
		dialog_frame(enter_but);
	}
	void dialog_frame(JButton enter_but){
		//�������촰��
		JFrame frame=new JFrame("��"+friendname+"��˽��ҳ��");
		frame.setBounds(500,300,500,500);
		frame.setLayout(null);
		//����������ǩ
		JLabel label=new JLabel("");
		label.setBounds(0, 0, 520, 5);
		frame.add(label);
		//���������
		text_area=new JTextArea();
		text_area.setBounds(0, 0, 520, 280);
		frame.add(text_area);
		//���������ǩ
		JLabel input_l=new JLabel("  �����");
		input_l.setBounds(0,295,510,15);//
		frame.add(input_l);
		//���������
		JTextArea input_t=new JTextArea();
		input_t.setBounds(0, 320, 510, 103);//
		frame.add(input_t);
		//�����˳���ť
		JButton exit_but=new JButton("�˳�");
		exit_but.setBounds(0,423,250,40);
		frame.add(exit_but);
		//���÷��Ͱ�ť
		JButton send_but=new JButton("����");
		send_but.setBounds(250,423,250,40);
		frame.add(send_but);

		frame.setVisible(true);
		frame.setResizable(false);
		
		//�˳���ť
		exit_but.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0){
				frame.dispose();
				enter_but.setEnabled(true);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		//����˽�ķ��Ͱ�ť
		send_but.addMouseListener(new MouseListener(){

			@Override
			//������Ͱ�ťʱ�����û����Լ�������е��ı���Ϊ���ݵ��������ѣ�Ȼ��������е��ı����
			public void mouseClicked(MouseEvent arg0) {
				byte[] send_message;
				DatagramPacket send_packet;
				try{			
					send_message=(username+"@"+friendname+"@"+input_t.getText()).getBytes();
					send_packet=new DatagramPacket(send_message,send_message.length,inetaddress,port_num);
					datagramsocket.send(send_packet);
					text_area.setText(text_area.getText()+username+":"+input_t.getText()+"\n");
					input_t.setText("");
				}catch(IOException e){
					System.out.println("error happen in the sender "+username);
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				send_but.setBackground(new Color(255,128,0));
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				send_but.setBackground(new Color(173,234,234));
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
    		
    	});
	}
}
