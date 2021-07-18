package ChatRoom;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
public class Member{
	String username="";
	ArrayList<friend> friend_array;
	ArrayList<group> group_array;
	int userport;
	public Member(){
		friend_array=new ArrayList<friend>();
		group_array=new ArrayList<group>();
	}
	
	//����member�������ؼ���������
	void load_data(){
		try{
			//�����ݿ��ڵĺ�����Ϣ����friend_array��
			File fp=new File(".\\userdata\\"+username+"\\friends.txt");
			InputStreamReader read=new InputStreamReader(new FileInputStream(fp),"GBK");
			BufferedReader bfread=new BufferedReader(read);
			String text=bfread.readLine();
			while(text!=null){
				String friend_info[]=text.split("@");
				friend_array.add(new friend(friend_info[0],friend_info[1],friend_info[2]));
				text=bfread.readLine();
			}
			bfread.close();
			//�����ݿ��ڵ�Ⱥ����Ϣ���ص�group_array��
			fp=new File(".\\userdata\\"+username+"\\groups.txt");
			read=new InputStreamReader(new FileInputStream(fp),"GBK");
			bfread=new BufferedReader(read);
			text=bfread.readLine();
			while(text!=null){
				String group_info[]=text.split("@");
				group_array.add(new group(group_info[0],group_info[1]));
				text=bfread.readLine();
			}
			bfread.close();
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	//���û������һ������ʱ����������Ϣ���µ����ݿ��ڸ��û����ݿ�ĺ����ļ�friends.txt��
	void updata_data(friend f){
		try {
			BufferedWriter bwrite=new BufferedWriter(new FileWriter(".\\userdata\\"+username+"\\friends.txt",true));
			bwrite.write((f.friend_inetAddress+"@"+f.friend_name+"@"+f.friend_port).substring(1));
			bwrite.newLine();
			bwrite.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	//���û������һ��Ⱥ��ʱ����Ⱥ����Ϣ���µ����ݿ��ڸ��û����ݿ��Ⱥ���ļ�groups.txt��
	void updata_data(group g){
		try {
			BufferedWriter bwrite=new BufferedWriter(new FileWriter(".\\userdata\\"+username+"\\groups.txt",true));
			bwrite.write((g.group_inetAddress+"@"+g.group_name).substring(1));
			bwrite.newLine();
			bwrite.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String args[]){
		Member member=new Member();
		loginOrRegister login_or_register=new loginOrRegister();
		
		//���û�гɹ���¼������Ӧ
		while(!login_or_register.login){}
		
		//�ɹ�ֱ�ӽ�����ҳ��
		member.username=login_or_register.username;
		member.userport=login_or_register.userport;
		member.load_data();
		new MainFrame(member);
	}
}
