package ChatRoom;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;

public class MainFrame {
	int userport;
	String username;
	JFrame frame;
	JPanel panel;
	JPanel add_panel;
	JButton friend_but;
	JButton group_but;
	JButton new_friend_but;
	JButton new_group_but;
	JScrollPane scroll;
	int num=0;
	volatile HashMap<String,friendDialogFrame> open_dialog;
	DatagramSocket datagramsocket;
	MainFrame(Member member){
		username=member.username;
		userport=member.userport;
		open_dialog=new HashMap<String,friendDialogFrame>();
		
		try{
			datagramsocket=new DatagramSocket(userport);
		}catch(Exception e){
			System.out.println(e);
		}
		//����frame
		frame=new JFrame("�û�"+username+"������");
		frame.setLayout(null);
		frame.setBounds(100, 200, 500, 500);
		//����panel
		panel=new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, 500, 40);
		//���ú��Ѱ�ť
		friend_but=new JButton("�����б�");
		friend_but.setBounds(0, 1, 125, 40);
		panel.add(friend_but);
		//���ú��Ѱ�ť
		group_but=new JButton("Ⱥ���б�");
		group_but.setBounds(125, 1, 125, 40);
		panel.add(group_but);
		//���ú��Ѱ�ť
		new_friend_but=new JButton("��Ӻ���");
		new_friend_but.setBounds(250, 1, 125, 40);
		panel.add(new_friend_but);
		//���ú��Ѱ�ť
		new_group_but=new JButton("����Ⱥ��");
		new_group_but.setBounds(375, 1, 124, 40);
		panel.add(new_group_but);
		frame.add(panel);
		//����add_panel
		add_panel=new JPanel();
		add_panel.setLayout(null);
		scroll=new JScrollPane(add_panel);
		scroll.setBounds(0, 40, 500, 760);
		scroll.setBackground(new Color(0,0,0));
		frame.add(scroll);
		//init_panel_g(member.group_array);
		init_panel_f(member.friend_array);	
		frame.setVisible(true);
		frame.setResizable(false);
		
		//���Ѱ�ť
		friend_but.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent arg0) {
				num=0;
				add_panel.removeAll();
				init_panel_f(member.friend_array);
				add_panel.repaint();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
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
		
		//Ⱥ�İ�ť
		group_but.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				num=0;
				add_panel.removeAll();
				init_panel_g(member.group_array);
				//frame.revalidate(); 
				add_panel.repaint();
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
		//��Ӻ��Ѱ�ť
	    new_friend_but.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent m){
				new addFriend(member);
			}
		});
	    //���Ⱥ�鰴ť
	    new_group_but.addMouseListener(new MouseAdapter(){
	    	public void mouseClicked(MouseEvent m){
	    		new addGroup(member);
			}
	    });
	    byte receive_pak[]=new byte[1000];
		DatagramPacket receive_packet=new DatagramPacket(receive_pak,1000);
	    while(true){
	    	try{	
	    		
	    		//������Ϣ
				datagramsocket.receive(receive_packet);
				byte[] receive_message=receive_packet.getData();
				String rece_msg=new String(receive_message,0,receive_packet.getLength());
				deal_msg(rece_msg);
				
			}catch(Exception e){
				System.out.println("error happen in the receiver "+username+e);
			}
	    }
	
	}
	synchronized void deal_msg(String rece_msg){
		String msg[]=rece_msg.split("@");
		System.out.println(msg[2]);
		if(open_dialog.containsKey(msg[0])){
			open_dialog.get(msg[0]).text_area.setText(open_dialog.get(msg[0]).text_area.getText()+msg[0]+":"+msg[2]+"\n");
			System.out.println(msg[2]);
		}else{
			System.out.println("?????");
		}
	}
	/*
	 * �ú�����Ϣ����ʼ��add_panel
	 */
	void init_panel_f(ArrayList<friend> f_r){
		for(friend f : f_r){
			draw_panel(f);
		}
		
	}
	/*
	 * ��Ⱥ����Ϣ����ʼ��add_panel
	 */
	void init_panel_g(ArrayList<group> g_r){
		for(group g : g_r){
			draw_panel(g);
		}
	}
	
	//����һ�����ѵ���Ϣ����panel����ʾ��һ�����ѿؼ�
	void draw_panel(friend f){
		//���ѵ�IP��ַ
		JLabel address=new JLabel("             ����IP��ַ:"+f.friend_inetAddress);
		address.setBounds(0, 40*num, 200, 40);
		address.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		add_panel.add(address);
		//���ѵĶ˿ں�
		JLabel name=new JLabel("            �����û���:"+f.friend_name);
		name.setBounds(200, 40*num, 175, 40);
		name.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		add_panel.add(name);
		//��������찴��
		JButton enter_but=new JButton("��ʼ˽��");
		enter_but.setBounds(375, 40*num, 124, 40);
		add_panel.add(enter_but);
		num++;
		add_panel.revalidate(); 
		enter_but.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent m){
				open_dialog.put(f.friend_name, new friendDialogFrame(f,username,enter_but,datagramsocket));
				//enter_but.setEnabled(false);
			}
		});
	}
	//����һ��Ⱥ�����Ϣ����panel����ʾ��һ��Ⱥ��ؼ�
	void draw_panel(group g){
		//���ѵ�IP��ַ
		JLabel address=new JLabel("             Ⱥ��IP��ַ"+g.group_inetAddress);
		address.setBounds(0, 40*num, 200, 40);
		address.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		add_panel.add(address);
		//���ѵĶ˿ں�
		JLabel name=new JLabel("            Ⱥ��������:"+g.group_name);
		name.setBounds(200, 40*num, 175, 40);
		name.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		add_panel.add(name);
		//��������찴��
		JButton enter_but=new JButton("����Ⱥ��");
		enter_but.setBounds(375, 40*num, 125, 40);
		add_panel.add(enter_but);
		num++;
		add_panel.revalidate();
		enter_but.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent m){
				if(enter_but.isEnabled()){
					new groupDialogFrame(g,username,enter_but);
					//enter_but.setEnabled(false);
				}
			}
		});
	}
	

}
