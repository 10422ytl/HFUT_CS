package inventory;//����˳���¼ӻ���  ������  ���������ȳ��ٺ���ࣩ ɾ����������δ�գ�����

import java.io.*;
import java.util.*;
	
	class Inventory {
		String itemNumber;
		int quantity;
		String supplier;
		String description;
		
		public Inventory(){
			super();
		}
		public Inventory(String itemNumber,int quantity,String supplier,String description) {//�����¼���������ţ�������������Ӧ�̱�ţ���������
			super();//�����о�super�����÷�
			this.itemNumber=itemNumber;		
			this.quantity=quantity;
			this.supplier=supplier;
			this.description=description;
		}	
		public Inventory(Add a) {//ʹ��Add������Inventory����
			itemNumber = a.itemNumber;
			quantity = 0;
			supplier = a.supplier;
			description = a.description;
		}
		public void plus(int i) {
			quantity+=i;
		}
		public void minus(int i) {
			quantity-=i;
		}
	}
	
	class TransAction {//����ҵ�񣬷�Ϊ���� �ջ� �ӻ� ɾ�� �˴����캯��ҲҪsuper()��
		String type;
		String itemNumber;	
		public TransAction() {
			super();
		}	
		public TransAction(String type,String itemNumber) {
			super();
			this.type=type;
			this.itemNumber=itemNumber;
		}		
	}
	
	class Order extends TransAction {//������������O�������ţ�������������Ӧ�̱��
		int quantity;
		String supplier;
		public Order() {
			super();
		}
		public Order(String type,String itemNumber,int quantity,String supplier) {
			super("O",itemNumber);
			this.quantity=quantity;
			this.supplier=supplier;
		}
		public void copy(Order tmp) {
			itemNumber=tmp.itemNumber;
			type="O";
			quantity=tmp.quantity;
			supplier=tmp.supplier;
		}		
	}
	
	class Recv extends TransAction{//�ջ���������R��������������Ӧ�̱��
		int quantity;
		public Recv(){
			super();
		};
		public Recv(String type,String itemNumber,int quantity) {
			super("R",itemNumber);
			this.quantity=quantity;
		}
	}
	
	class Add extends TransAction{//�ӻ���������A�������ţ�������������������
		String supplier;
		String description;	
		public Add() {
			super();
		}
		public Add(String type,String itemNumber,String supplier,String description) {
			super("A",itemNumber);
			this.supplier=supplier;
			this.description=description;		
		}
	}	
	
	class Delete extends TransAction{//ɾ����������D��������
		public Delete(String type, String itemNumber) {
			super("D", itemNumber);
		}
	} 
	
	class Error {//���������л����ţ�������������Ӧ�̱�ţ����ڴ���������С�δ���͵ķ�����¼�Ϳ��������0��ɾ����¼����������TransAction���޷���Error�������̳У��ʴ���δ���ü̳�
		String itemNumber;
		int quantity;
		String supplier;	
		public Error(String s, int i, String q) {
			itemNumber = s;
			quantity = i;
			supplier = q;
		}	
		public Error(Order o) {//ʹ��Order������Error����
			supplier = o.supplier;
			itemNumber = o.itemNumber;
			quantity = o.quantity;
		}
	}
	
public class Test{
	static Vector<Inventory> inventory = new Vector<Inventory>();//�嵥�����пɶ�ȡ����¼			��TransAction.txt���н���ҵ��
	static Vector<Order> order = new Vector<Order>();
	static Vector<Order> shipping = new Vector<Order>();//��¼������¼������ͬ������ϲ�
	static Vector<Recv> recv = new Vector<Recv>();
	static Vector<Add> add = new Vector<Add>();
	static Vector<Delete> delete = new Vector<Delete>();
	static Vector<Error> error = new Vector<Error>(); //��¼������Ϣ


	public static void main(String[] args) throws Exception {//�ļ�Inventory.txt  TransAction.txt  NewInventory.txt�����µĿ���¼�����У�  shipping.txt  Errors.txt
		readInventory("E:\\Inventory.txt");
		readTransAction("E:\\TransActions.txt");
		doAdd();
		doReceive();
		arrOrder();
		doOrder();
		doDelete();
		write("E:\\NewInventory.txt E:\\shipping.txt E:\\Errors.txt");
	}														//����˳���¼ӻ���  ������  ���������ȳ��ٺ���ࣩ ɾ����������δ�գ�����

	public static void readInventory(String filename)throws Exception{//�����ļ�"E:\\Inventory.txt"������������Ϣ����inventory
		BufferedReader br =new BufferedReader(new FileReader(filename));
		String line=null;
		while ((line=br.readLine())!=null) {
			String tmp[]=line.split("\t");
			int quantity=Integer.parseInt(tmp[1]);//Integer.parseInt(String)�����þ��ǽ�String�ַ���������ת��ΪInteger�������ݣ�����һЩ���ܱ�ת��Ϊ���͵��ַ�ʱ�����׳��쳣��
			inventory.add(new Inventory(tmp[0],quantity,tmp[2],tmp[3]));//
		}
	br.close();
	}
	
	public static void readTransAction(String filename) throws Exception {//�����ļ�"E:\\Transactions.txt"������������Ϣ����order��recv��add��delete
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line = null;
		while ((line = br.readLine()) != null) {
			String [] tmp = line.split("\t");
			if (tmp[0].equals("O")) {
				int quantity = Integer.parseInt(tmp[2]);
				order.add(new Order("O", tmp[1], quantity, tmp[3]));
			} else if (tmp[0].equals("R")) {
				int quantity = Integer.parseInt(tmp[2]);
				recv.add(new Recv("R", tmp[1], quantity));
			} else if (tmp[0].equals("A")) {
				add.add(new Add("A", tmp[1], tmp[2], tmp[3]));
			} else if (tmp[0].equals("D")) {
				delete.add(new Delete("D", tmp[1]));
			}
		}
		br.close();
	}
	
	public static void doAdd() {// 1.�¼ӻ��� 
		for (int i = 0; i < add.size(); i++) {
			Inventory tmp = new Inventory(add.get(i));
			inventory.add(tmp);
		}
	}
	
	public static void doReceive() {// 2.������
		for (int i = 0; i < recv.size(); i++) {
			for (int j = 0; j < inventory.size(); j++) {
				String s1, s2;
				s1 = new String(recv.get(i).itemNumber);
				s2 = new String(inventory.get(j).itemNumber);
				if (s1.equals(s2)) {
					(inventory.get(j)).plus((recv.get(i)).quantity);
				}
			}
		}
	} 
		
	public static void arrOrder() {//3.�����������մ��ٵ����������
		for (int i = 0; i < order.size(); i++) {
			for (int j = i + 1; j < order.size(); j++) {
				if ((order.get(i)).quantity > (order.get(j)).quantity) {
					Order tmp = order.get(i);
					order.set(i, order.get(j)); // set��������vector.set(i,p)��������Ϊi��Ԫ����Ϊp��
					order.set(j, tmp);
				}
			}
		}
	}
	
	public static void doOrder() {//4.������
		for(int i=0;i < order.size();i++) {
			for (int j = 0; j < inventory.size(); j++) {
				String s1,s2;
				s1=new String (order.get(i).itemNumber);
				s2=new String (inventory.get(j).itemNumber);
				if (s1.equals(s2)) {//ע�⣺������Ϊcustom����supplier����ֻ���ж�itemNumber�Ƿ���ͬ
					if(inventory.get(j).quantity>=(order.get(i)).quantity){
						(inventory.get(j)).minus((order.get(i)).quantity);
						shipping.add(order.get(i));//�˴���shipping
					}else {
						Error tmp=new Error(order.get(i));
						error.add(tmp);
					}						
				}
			}
		}
	}
	
	public static void doDelete() {//5.ɾ��������Ӽ���Ƿ����
		for(int i=0;i<delete.size();i++) {
			for (int j=0;j<inventory.size();j++) {
			String s1,s2;
			s1=new String (delete.get(i).itemNumber);
			s2=new String (inventory.get(j).itemNumber);
			if (s1.equals(s2)) {
				if ((inventory.get(j)).quantity==0) {
					inventory.remove(j);
				}else {
					error.add(new Error((inventory.get(j)).itemNumber,(inventory.get(j)).quantity,(inventory.get(j)).supplier));
					}
				}
			}
		}
	}
		
	public static void arrShipping() {//6.����ȷ�ĳ��������д���
		for (int i=0;i<shipping.size();i++) {
			for (int j=i+1;j<shipping.size();j++) {
				String s1,s2,s3,s4;
				s1=new String (shipping.get(i).itemNumber);
				s2=new String (shipping.get(j).itemNumber);
				s3=new String (shipping.get(i).supplier);
				s4=new String (shipping.get(j).supplier);
				if(s1.equals(s2)&&s3.equals(s4)) {
					Order tmp=new Order();
					tmp.copy(shipping.get(i));
					shipping.set(i,tmp);
					shipping.remove(j);
					j--;
				}
			}	
		}
		
	}
	
	public static void write(String filenames)throws Exception{
		String [] filename=filenames.split(" ");
		BufferedWriter ibw=new BufferedWriter(new FileWriter(filename[0]));
		BufferedWriter sbw=new BufferedWriter(new FileWriter(filename[1]));
		BufferedWriter ebw=new BufferedWriter(new FileWriter(filename[2]));
		for (int i=0;i<inventory.size();i++) {
			Inventory tmp=inventory.get(i);
			String s=tmp.itemNumber+"\t"+tmp.quantity+"\t"+tmp.supplier+"\t"+tmp.description;
			ibw.write(s);
			ibw.newLine();
		}
		for (int i=0;i<shipping.size();i++) {
			Order tmp=shipping.get(i);
			String s=tmp.itemNumber+"\t"+tmp.quantity+"\t"+tmp.supplier+"\t"+tmp.quantity;
			sbw.write(s);
			sbw.newLine();
		}
		for (int i=0;i<error.size();i++) {
			Error tmp=error.get(i);
			String s=tmp.itemNumber+"\t"+tmp.quantity+"\t"+tmp.supplier+"\t"+tmp.quantity ;
			ebw.write(s);
			ebw.newLine();
	}
		ibw.close();
		sbw.close();
		ebw.close();
	}
}
