package ChatRoom;

import java.net.InetAddress;
import java.net.UnknownHostException;

//Ⱥ�ĵ�������ַ�Լ��˿�
public class group{
	InetAddress group_inetAddress;
	String group_name;
	group(String address,String name){
		try {
			group_inetAddress=InetAddress.getByName(address);
			group_name=name;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}