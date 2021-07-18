package my_project;
//�������
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import java.util.Scanner;

public class Experiment2 {
	public static void main(String args[]){
		Map<String,String> map=new HashMap<String,String>();//����map
		ArrayList<String> sword=new ArrayList<String>();//���춯̬����
		ArrayList<String> dword=new ArrayList<String>();
		Pattern pattern=Pattern.compile("([\u4E00-\u9FA5]*)(.)");
		Matcher mat;
		Random random=new Random();
		int m,clen=100;
		Boolean exist[]=new Boolean[clen];
		for(int i=0;i<clen;i++){
			exist[i]=false;
		}
		try{
			String title="";
			String sw,dw;
			BufferedReader bsread=new BufferedReader(new FileReader("E:\\eclipse-workspace\\my_project\\src\\my_project\\txt\\sword.txt"));//��ȡʵ��1���ɵ��ļ�
			BufferedReader bdread=new BufferedReader(new FileReader("E:\\eclipse-workspace\\my_project\\src\\my_project\\txt\\dword.txt"));	
			BufferedReader bfread=new BufferedReader(new FileReader(new File("E:\\eclipse-workspace\\my_project\\src\\my_project\\txt\\Ci.txt")));
			BufferedWriter bfwrite=new BufferedWriter(new FileWriter(new File("E:\\eclipse-workspace\\my_project\\src\\my_project\\txt\\Struct.txt")));
			String text=bfread.readLine();
			String sent="";
			String struc="";
			
			sw=bsread.readLine();//������sw��dw���ļ��ж�ȡ���붯̬����sword��dword��ȥ
			while(sw!=null){
				sword.add(sw);
				sw=bsread.readLine();
			}
			dw=bdread.readLine();
			while(dw!=null){
				dword.add(dw);
				dw=bdread.readLine();
			}
			bsread.close();
			bdread.close();
			
			while(text != null){//
				if(text.length()<2){//ȥ��һЩ����
					text=bfread.readLine();
					continue;
				}else if(text.length()<6){//����6���ж�Ϊ���⣬��������������
					title=text;
				}else{
					if(map.containsKey(title)){//������и�title����text����������һ��
						text=bfread.readLine();
						continue;
					}else{
						mat=pattern.matcher(text);//ȥ�����еķǺ����ַ�
						while(mat.find()){
							sent=mat.group(1);//ȡ�ֶκ�ĵ�һ��
							int len=sent.length();//��ȡ�öγ���
							while(len>0){
								if(len>3){//�����7���ַ����򻮷�Ϊ223�������5���ַ����򻮷�Ϊ23���Դ�����
									struc+=2;//struc�����ַ���'2'
									len-=2;//����-2
								}else{
									struc+=len;//struc�����ַ���Ϊ'�öγ���'����7�����ʱ����3
									len=0;//�öγ�����0
								}
							}
							struc+=mat.group(2);//��ʼ����ֶκ�ĵڶ���
						}
					    map.put(title, struc);//���õ��ĸ�ʽ����map
					    bfwrite.write(title+":"+struc+"\r\n");
					    struc="";
					}
					
				}

				text=bfread.readLine();//��ȡ
			}
			bfwrite.close();
			bfread.close();

			System.out.println("�뻻�������������ɵ��δ����ͣ���������ʽ��");//���ݴ��������ɴ�
		}catch(Exception e){
			System.out.println(e);
		}
		Scanner input=new Scanner(System.in);
		String title=input.nextLine();//������Ĵ���������title
		input.close();
		String text;
		if(map.containsKey(title)){
			text=map.get(title);//ȡ����Ӧ�ĸ�ʽ
			String output=title+"\n";//�Ƚ�����Ĵ�����д�����
			for(int i=0;i<text.length();i++){//�ʵ�������text���������������Ӵʲ�������
				m=random.nextInt(clen);//�������0-100������
			    while(exist[m])
			    	m=random.nextInt(clen);
			    exist[m]=true;//text�ĸ�ʽ��223����
				if(text.charAt(i)=='1'){//charAt()����ָ������λ�õ�charֵ
					output+=sword.get(m).split(":")[0];//��Ҫһ���ַ�����һ������
				}else if(text.charAt(i)=='2'){
					output+=dword.get(m).split(":")[0];//��Ҫ�����ַ�����һ��˫��
				}else if(text.charAt(i)=='3'){
					output+=sword.get(m).split(":")[0];//��Ҫ�����ַ�����һ˫һ��
					output+=dword.get(m).split(":")[0];
				}else{
					output+=(text.charAt(i)+"\n");	
				}
			}
			System.out.println(output);
		}else{
			System.out.println("�����������Ѵ��ڵĴ�����");
		}
	}
}
