package ChatRoom;




import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class addFriend {
	JFrame frame;
	JLabel ip_l;
	JLabel friendname_l;
	JLabel friendport_l;
	JTextField ip_t;
	JTextField friendname_t;
	JTextField friendport_t;
	JButton add_friend;
	JLabel friend_info;
	addFriend(Member member){
		//����frame
		frame=new JFrame("��Ӻ���");
		frame.setBounds(200,250,250,285);
		frame.setLayout(null);
		//���ñ�ǩͷ
		friend_info=new JLabel("�����������Ϣ",JLabel.CENTER);
		friend_info.setBounds(0,10,250,30);
		frame.add(friend_info);
		//����ip��ǩ
		ip_l=new JLabel("����IP��ַ:");
		ip_l.setBounds(10, 50, 75, 30);
		frame.add(ip_l);
		//����ip�ı���
		ip_t=new JTextField("127.0.0.1");
		ip_t.setBounds(80, 50, 145, 30);
		frame.add(ip_t);
		
		//���ú�������ǩ
		friendname_l=new JLabel("�����û���:");
		friendname_l.setBounds(10, 100, 75, 30);
		frame.add(friendname_l);
		//��д������
		friendname_t=new JTextField();
		friendname_t.setBounds(80, 100, 145, 30);
		frame.add(friendname_t);
		//���ú��Ѷ˿ڱ�ǩ
		
		friendport_l=new JLabel("���Ѷ˿ں�:");
		friendport_l.setBounds(10, 150,75, 30);
		frame.add(friendport_l);
		//��д���Ѷ˿�
		friendport_t=new JTextField("");
		friendport_t.setBounds(80, 150, 145, 30);
		frame.add(friendport_t);
	
		//������Ӽ�
		add_friend=new JButton("���");
		add_friend.setBounds(75, 200, 100, 30);
		frame.add(add_friend);
		frame.setResizable(false);
		frame.setVisible(true);
		
		add_friend.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent m){
				friend f=new friend(ip_t.getText(),friendname_t.getText(),friendport_t.getText());
				member.updata_data(f);
				member.friend_array.add(f);
				frame.dispose();
			}
		});
	}
}


