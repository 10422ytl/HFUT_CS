package ChatRoom;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class addGroup {
	JFrame frame;
	JLabel ip_l;
	JLabel groupname_l;
	JTextField ip_t;
	JTextField groupname_t;
	JButton add_group;
	JLabel group_info;
	addGroup(Member member){
		//����frame
		frame=new JFrame("����Ⱥ��");
		frame.setBounds(200,200,250,235);
		frame.setLayout(null);
		//���ñ�ǩͷ
		group_info=new JLabel("������Ⱥ����Ϣ",JLabel.CENTER);
		group_info.setBounds(0,10,250,30);
		frame.add(group_info);
		
		//����ip��ǩ
		ip_l=new JLabel("Ⱥ��IP��ַ:");
		ip_l.setBounds(10, 50, 75, 30);
		frame.add(ip_l);
		//����ip�ı���
		ip_t=new JTextField("224.0.0.1");
		ip_t.setBounds(80, 50, 145, 30);
		frame.add(ip_t);
		
		//���ú�������ǩ
		groupname_l=new JLabel("  Ⱥ������:");
		groupname_l.setBounds(10, 100, 75, 30);
		frame.add(groupname_l);
		//��д������
		groupname_t=new JTextField();
		groupname_t.setBounds(80, 100, 145, 30);
		frame.add(groupname_t);
		
		//������Ӽ�
		add_group=new JButton("����");
		add_group.setBounds(75,150, 100, 30);
		frame.add(add_group);
		frame.setResizable(false);
		frame.setVisible(true);
		
		add_group.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent m){
				group g=new group(ip_t.getText(),groupname_t.getText());
				member.updata_data(g);
				member.group_array.add(g);
				frame.dispose();
			}
		});
	}
}

