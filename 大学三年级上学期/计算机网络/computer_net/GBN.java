package computer_net;

//import java.awt.List;//���������38��һֱ����:���� List ����ͨ�õģ�����ʹ�ò��� <ByteArrayOutputStream> ����������
import java.util.List;//�������ȷ��Ӧ�õ���İ�
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.LinkedList;

class GBN {
	//��ȡ��������InetAddress
	private InetAddress host;
	//Ŀ��˿ںͱ����˿�
	private int targetPort, ownPort;
	//���˴��Ĵ��ڴ�СΪ1����Ϊͣ��Э��
	private int WindowSize = 16;
	//�����ʱ�䣬������ʱ�䣬һ�����ݰ��ĳ�ʱʱ��
	private final int sendMaxTime = 2,receiveMaxTime = 4,endTime=5; 
	//��ʼbaseֵ
	private long base = 0;
	//����ģN����ģ�ⶪ����N��Lossgap
	private final int Lossgap = 100;
	//��GBN�г�Ա�������и�ֵA
	GBN(String host, int targetPort, int ownPort) throws UnknownHostException {
			//this�ؼ��ֵ������г�Ա����ownPort
			this.ownPort = ownPort;
			//ȷ��������IP��ַ
			this.host = InetAddress.getByName(host);
			//this�ؼ��ֵ������г�Ա����targetPort
			this.targetPort = targetPort;
	}
/**
  *������Ŀ�ĵ�ַ������������ع���
 */
//send����
void send(byte[] content) throws IOException {
	//
	int sendIndex = 0, length;
	//����������ݰ�����ֽ���
	final int MAX_LENGTH = 1024;
	//����UDP��DatagramSocket����ʾ���ܻ������ݱ����׽��֣��˴��Ƚ����׽���<DatagramSocket>
	DatagramSocket datagramSocket = new DatagramSocket(ownPort);
	//�������ڻ��棬������������ݽṹ����Ϊ���ݰ���Ҫ�����ʶ�����£���������ʵ���ش�
	List<ByteArrayOutputStream> datagramBuffer = new LinkedList<>(); 
	//ʵ����һ��Integer��Ķ���timer�������ö���ֵΪ0����ʱ����
	Integer timer = 0;
	//����������к�
	long sendSeq = base;
	do {
		//ֱ�����ڿ�ʼ����
		while (sendIndex < content.length && sendSeq < 256 && datagramBuffer.size()<WindowSize) {
			//�򻬴������м���byte������
			datagramBuffer.add(new ByteArrayOutputStream());
			//�����ݲ����ֽ���δ�����޶������ֵ���򲻱䣻����������ȡ�޶������ֵ
			length = content.length - sendIndex < MAX_LENGTH ? content.length - sendIndex : MAX_LENGTH;
			//��������֡
			ByteArrayOutputStream oneSend = new ByteArrayOutputStream();
			//��ʼ��byte����
			byte[] temp = new byte[1];
			//
			temp[0] = new Long(sendSeq).byteValue();
			//��temp����oneSendд���0��ʼ��һ�ֽڵ�����
			oneSend.write(temp, 0, 1);
			//��content����oneSendд���sendIndex��ʼ��length�ֽڵ�����
			oneSend.write(content, sendIndex, length);
			//DatagramPacket��ʾ������ݵ����ݱ����˾��ʾ��host��ַtargetPort�˿ڴ�����oneSend.size()���ȵ�oneSend.toByteArray()
			DatagramPacket datagramPacket = new DatagramPacket(oneSend.toByteArray(), oneSend.size(), host, targetPort);
			//��datagramSocket����datagramPacket
			datagramSocket.send(datagramPacket);
			//�򻬴�������sendSeq-base��λ��д��content�д�sendIndex��ʼ��length�ֽڵ�����
			datagramBuffer.get((int) (sendSeq - base)).write(content, sendIndex, length);
			//����length��ѭ����
			sendIndex += length;
			//����������ݰ����ΪsendSeq��
			System.out.println("send the datagram seq: " +sendSeq);
			//���+1��ѭ����
			sendSeq++;
		}
		//����ʱ����ʵ��1�뵹��ʱ
		datagramSocket.setSoTimeout(1000);
		//����һ������ACK�����ݰ�
		DatagramPacket receivePacket;
		try{//��base��ʼ����ACK receive ACKs for base
			while (true) {
				//1500�ֽڵ�recv�ֽ�����
				byte[] recv = new byte[1500];
				//���recv.length����recv�����ݱ�
				receivePacket = new DatagramPacket(recv, recv.length);
				//�׽��ֿ�ʼ�������ݰ��������յ�ʱ��receivePacket����Ϊ���յ�������
				datagramSocket.receive(receivePacket);
				//��11111111��λ�룬�����Ϊint�͸���ack����һ����Ϊ��ʵ�ֽ��շ������������
				int ack = (int) ((recv[0] & 0x0FF));
				//������յ��˵�һ�����ͼ������յڶ�������˳����ջ���
				if (ack == base) {
					base++;
					datagramBuffer.remove(0);
					break;
				}
			}
		//��ʱ����
		} catch (SocketTimeoutException e) {
			timer++;
		}
			//��������趨�ĳ�ʱʱ��
			if ( timer> this.sendMaxTime) { 
				// �ط�����û���յ�ACK����ʱ�������ݱ�
				for (int i = 0; i<datagramBuffer.size();i++) {
					ByteArrayOutputStream resender =  new ByteArrayOutputStream();
					byte[] temp = new byte[1];
					temp[0] = new Long(i + base).byteValue();
					resender.write(temp, 0, 1);
					resender.write(datagramBuffer.get(i).toByteArray(), 0, datagramBuffer.get(i).size());
					DatagramPacket datagramPacket = new DatagramPacket(resender.toByteArray(), resender.size(), host, targetPort);
					datagramSocket.send(datagramPacket);
					System.err.println("resend the datagram's seq: "+ (i + base));
			}
				//����timer
				timer = 0;
		//baseÿ��256���ͽ�base��sendSeq����
		if (base >= 256) {
			base = base - 256;
			sendSeq = sendSeq - 256;
			} 
		}
		//ֱ������ȫ���������
		}while (sendIndex < content.length || datagramBuffer.size() !=0) ;
		//�ر��׽���
		datagramSocket.close();
	}
	
	/**
	  * ��������Ŀ�ĵ�ַ����������ع���
	 */
	//receive
	ByteArrayOutputStream receive() throws IOException {
		//���� used to simulate datagram loss
		int count = 1,time=0; 
		long nowseq = 0;
		//�洢���յ� store the received content
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		//�������ݱ��ͷ���ACK��UDP�׽���  UDP socket to receive datagram and send ACKs		
		DatagramSocket datagramSocket = new DatagramSocket(ownPort); 
		//һ����ʱ�����ݱ� one temp datagram packet
		DatagramPacket receivePacket;
		//
		datagramSocket.setSoTimeout(endTime*1000);
		while (true) {
			//����һ�����ݱ����ҷ���ACK
			try {
				byte[] recv = new byte[1500];
				receivePacket = new DatagramPacket(recv, recv.length, host, targetPort);
				datagramSocket.receive(receivePacket);
			//��ģNΪ0ʱ����Ϊ����һ������
			if (count % Lossgap != 0) {
				long seq = recv[0] & 0x0FF;
				if (seq == nowseq) {
					//accept
					nowseq++;
					if (nowseq>=256)nowseq=0;
					//д����write to upper
					result.write(recv, 1, receivePacket.getLength()-1);
					//����ACK send ACK
						recv = new byte[1];
						recv[0] = new Long(seq).byteValue();
						receivePacket = new DatagramPacket(recv, recv. length, host, targetPort);
						datagramSocket.send(receivePacket);
						System.out.println("receive datagram seq: " + seq);
						datagramSocket.setSoTimeout(endTime*1000);
					}//else discard
					}
				}catch (SocketTimeoutException e) {
					break;
			}
			//datagramSocket.setSoTimeout(endTime);
			count++;
		}
		datagramSocket.close();
		return result;
	}
}