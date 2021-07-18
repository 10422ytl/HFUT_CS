package ChatRoom;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class groupDialogFrame{
	int port_num=8888;
	String username;
	String groupname;
	InetAddress inetaddress;
	MulticastSocket multicastsocket;
	
	groupDialogFrame(group g,String user_name,JButton enter_but){
		groupname=g.group_name;
		username=user_name;
		try{
			/*
			 * ���øó�Ա����IP��ַΪ224.0.0.1���飬ͬʱ����8888�Ŷ˿�;
			 */
			multicastsocket=new MulticastSocket(port_num);
			inetaddress=g.group_inetAddress;
			multicastsocket.joinGroup(inetaddress);
		}catch(Exception e){
			System.out.println(e);
		}
		dialog_frame(enter_but);
	}
	void dialog_frame(JButton enter_but){
		//�������촰��
		JFrame frame=new JFrame("Ⱥ��");
		frame.setBounds(500,300,500,500);
		frame.setLayout(null);
		//����������ǩ
		JLabel label=new JLabel("");
		label.setBounds(0, 0, 520, 5);
		frame.add(label);
		//���������
		JTextArea text_area=new JTextArea();
		text_area.setBounds(0, 0, 520, 280);
		frame.add(text_area);
		//���������ǩ
		JLabel input_l=new JLabel("  �����");
		input_l.setBounds(0,295,510,15);
		frame.add(input_l);
		//���������
		JTextArea input_t=new JTextArea();
		input_t.setBounds(0, 320, 510, 103);
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
		//Ⱥ��Ⱥ�ķ��Ͱ�ť
		send_but.addMouseListener(new MouseListener(){

			@Override
			//������Ͱ�ťʱ�����û����Լ�������е��ı���Ϊ�����鲥��Ⱥ��Ա��Ȼ��������е��ı����
			public void mouseClicked(MouseEvent arg0) {
				byte[] send_message;
				DatagramPacket send_packet;
				try{			
					send_message=(username+":"+input_t.getText()).getBytes();
					send_packet=new DatagramPacket(send_message,send_message.length,inetaddress,port_num);// miao
					multicastsocket.send(send_packet);
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
		//�����̣߳�������Ⱥ���Ƿ����˷�����Ϣ������У���ȡ����ͬʱ��ʾ����Ϣ����
		Thread receive_thread=new Thread(){
			public void run(){
				byte receive_msg[]=new byte[1000];
				DatagramPacket receive_packet=new DatagramPacket(receive_msg,1000);
				while(true){
					try{
						multicastsocket.receive(receive_packet);
						byte[] receive_message=receive_packet.getData();
						text_area.setText(text_area.getText()+new String(receive_message,0,receive_packet.getLength())+"\n");
					}catch(Exception e){
						System.out.println("error happen in the receiver "+username);
					}
				}
			}
		};
		receive_thread.start();
	}
}
