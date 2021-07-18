package gui;

import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Client {
	public static void main(String args[]) {
		new WindowClient();
	}
}

class WindowClient extends JFrame implements Runnable,ActionListener {
	JButton button1,button2;
	JTextField ipArea;
	JTextField portArea;
	JTextArea mainArea;
 	JTextField sendArea;

	Socket socket=null;					//
	DataInputStream in=null;
	DataOutputStream out=null;
	Thread thread;

	WindowClient() {
		super("�ͻ���");
		
		Container contain = getContentPane();		//�½����������ò���
		contain.setLayout(new BorderLayout());

		JPanel ipportPanel = new JPanel();		//�½�ipport����岢���ò���
		
		ipportPanel.add(new JLabel("Server IP:"));	//����������Server IP:��ǩ

		ipArea = new JTextField(15);			//����������ip�����ı�
		ipArea.setText("127.0.0.1");
		ipportPanel.add(ipArea);

		ipportPanel.add(new JLabel("Server Port:"));	//����������Server Port:��ǩ

		portArea =new JTextField(8);			//����������port�����ı�
		ipportPanel.add(portArea);			

		button1 = new JButton("Connect");		//����������connect��ť
		ipportPanel.add(button1);			

		JPanel sayPanel = new JPanel();		//�½�sayPanel����岢���ò���
		sayPanel.setLayout(new BorderLayout());	

		sayPanel.add(new JLabel("Say:"),BorderLayout.WEST);//����������Say:��ǩ

		sendArea = new JTextField(25);
		sayPanel.add(sendArea, BorderLayout.CENTER);	//����������sendArea�����ı�

		JButton button2 = new JButton("Say");		//����������Say��ť
		sayPanel.add(button2, BorderLayout.EAST);
		
		mainArea = new JTextArea(10,30);		//�½����ı���

		contain.add(ipportPanel, BorderLayout.NORTH);	//��������������
		contain.add(mainArea, BorderLayout.CENTER);	//������������ı���
		contain.add(sayPanel, BorderLayout.SOUTH);	//��������������

		socket=new Socket();			//
		button1.setEnabled(false);			//������
        		button2.setEnabled(true);
		button1.addActionListener(this);
		button2.addActionListener(this);
		thread =new Thread(this);
		
		setSize(500, 300);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
public void actionPerformed(ActionEvent e) {          
        if(e.getSource()==button1) {
            
            try {
                if(!socket.isConnected()) {              //�ж��Ƿ�����
                    show.append("Connect to server��\n");
                    InetAddress address=InetAddress.getByName(ipArea.getText());
                    InetSocketAddress socketAddress=new InetSocketAddress(address,Integer.parseInt(portArea.getText()));
                    socket.connect(socketAddress);
                    in=new DataInputStream(socket.getInputStream());
                    out=new DataOutputStream(socket.getOutputStream());
                    if(!thread.isAlive())
                        thread=new Thread(this);
                    thread.start();
                }
            }
            catch(IOException ee) {
                System.out.println(ee);
                socket=new Socket();
            }
        }
        if(e.getSource()==button2) {
            String s_out=sendArea.getText();
            mainArea.append("�ͻ��ˣ�"+s_out+"\n");
            try {
                out.writeUTF(s_out);			//���ַ�����UTF��ʽд�������
            }
            catch(IOException e1) {}
        }
    }
    
    public void run() {
        String s_in=null;
        while(true) {
            try {
                s_in=in.readUTF();
                mainArea.append("��������"+s_in+"\n");
                System.out.println();
            }
            catch(IOException e) {
                mainArea.setText("��������Ͽ�"+e);
                socket=new Socket();
                break;
            }
        }
    }
}