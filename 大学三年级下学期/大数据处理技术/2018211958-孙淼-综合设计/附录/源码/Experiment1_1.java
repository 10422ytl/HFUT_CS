package my_project;//����1�δʲ���

import java.io.*;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
public class Experiment1_1 {
	public static void main(String args[]){
		Map<String,Integer> sword=new HashMap<String,Integer>();//����һ�����еĵ���map
		Map<String,Integer> dword=new HashMap<String,Integer>();//����һ�����е�˫��map
		try{
			File fp=new File("D:\\��ѧ��ҵ\\3 ��\\Courses\\1 �����ݴ�����\\1��20��ʵ�顪���ۺ���Ʊ��� 30%\\�½��ļ���\\��HadoopȨ��ָ�ϣ������ݵĴ洢�����_��4��_ ������");
			InputStreamReader read=new InputStreamReader(new FileInputStream(fp),"GBK");//��GBK��ʽ��Ci.txt
			BufferedReader bfread=new BufferedReader(read);
			String LText=bfread.readLine();//LtextΪÿ��
			while(LText!=null){
				if(LText.length()<=5){//��ȡ����������������ת��������Ĵ����ݣ��������ִ��������ᳬ��5���֣�
						LText=bfread.readLine();
						continue;
				}else{//��ȡ������
					int len=LText.length();//�ʳ���Ϊlen
					for(int i=0;i<len;i++){//�����������޳��Ǻ����ַ�
						String str=LText.substring(i,i+1);//substring() ����������ȡ�ַ����н�������ָ���±�֮����ַ�
						int t=Pattern.compile("[\u4E00-\u9FA5]*").split(str).length;   //�ж��ַ������Ƿ�ȫΪ���ģ�����ǣ���ָ�����ַ�������������һ���ַ������飬 split()��������ƥ�������������ʽ������ַ���
			            if(t!=0)//ֻ��ȡ�����ַ�
			            	continue;
			            else if(sword.containsKey(str)){//�洢����
							int tem=sword.get(LText.substring(i,i+1))+1;//�����Ѿ��������1
							sword.put(LText.substring(i,i+1), tem);
						}else{
							sword.put(LText.substring(i,i+1), 1);//���ֲ��������½���1
						}
					}                          
					
					
					for(int i=1;i<LText.length();i++){
						String str=LText.substring(i-1,i+1);//ÿ����ȡ�����ַ�
						int t=Pattern.compile("[\u4E00-\u9FA5]*").split(str).length;
			            if(t!=0)
			            	continue;
						if(dword.containsKey(LText.substring(i-1,i+1))){
							int tem=dword.get(LText.substring(i-1,i+1))+1;//˫���Ѵ������1
							dword.put(LText.substring(i-1,i+1), tem);
						}else{
							dword.put(LText.substring(i-1,i+1), 1);//˫�ֲ���������1
						}
					}                          //ͳ��˫�ִ���
					
					for(int i=2;i<LText.length();i++){
						String str=LText.substring(i-2,i+1);
						int t=Pattern.compile("[\u4E00-\u9FA5]*").split(str).length;
			            if(t!=0)
			            	continue;
						if(tword.containsKey(LText.substring(i-2,i+1))){
							int tem=tword.get(LText.substring(i-2,i+1))+1;
							tword.put(LText.substring(i-2,i+1), tem);
						}else{
							tword.put(LText.substring(i-2,i+1), 1);
						}
					}   
					
					LText=bfread.readLine();
				}
				
			}
			List<Map.Entry<String, Integer>> slist = new ArrayList<Map.Entry<String, Integer>>(sword.entrySet());  //�����д��list
	        Collections.sort(slist, new Comparator<Map.Entry<String, Integer>>() {  
				public int compare(java.util.Map.Entry<String, Integer> o1, java.util.Map.Entry<String, Integer> o2) {
					return o2.getValue().compareTo(o1.getValue());
				}
	        });
	        
			List<Map.Entry<String, Integer>> dlist = new ArrayList<Map.Entry<String, Integer>>(dword.entrySet());  
	        Collections.sort(dlist, new Comparator<Map.Entry<String, Integer>>() {  
				public int compare(java.util.Map.Entry<String, Integer> o1, java.util.Map.Entry<String, Integer> o2) {
					return o2.getValue().compareTo(o1.getValue());
				}
	        });
	        
	        List<Map.Entry<String, Integer>> tlist = new ArrayList<Map.Entry<String, Integer>>(tword.entrySet());  
	        Collections.sort(tlist, new Comparator<Map.Entry<String, Integer>>() {  
				public int compare(java.util.Map.Entry<String, Integer> o1, java.util.Map.Entry<String, Integer> o2) {
					return o2.getValue().compareTo(o1.getValue());
				}
	        }); 
	        
			bfread.close();//��listд���ļ���ȥ
			read.close();
			BufferedWriter bwrite=new BufferedWriter(new FileWriter("D:\\��ѧ��ҵ\\3 ��\\Courses\\1 �����ݴ�����\\1��20��ʵ�顪���ۺ���Ʊ��� 30%\\�½��ļ���\\single_word.txt"));
			
			for(Map.Entry<String,Integer> key:slist){
				bwrite.write(key.getKey()+":"+key.getValue()+"\r\n");
			}
			bwrite.close();
			bwrite=new BufferedWriter(new FileWriter("D:\\��ѧ��ҵ\\3 ��\\Courses\\1 �����ݴ�����\\1��20��ʵ�顪���ۺ���Ʊ��� 30%\\�½��ļ���\\double_word.txt"));
			
			for(Map.Entry<String,Integer> key:dlist){
				bwrite.write(key.getKey()+":"+key.getValue()+"\r\n");
			}
			bwrite.close();
			
			bwrite=new BufferedWriter(new FileWriter("D:\\��ѧ��ҵ\\3 ��\\Courses\\1 �����ݴ�����\\1��20��ʵ�顪���ۺ���Ʊ��� 30%\\�½��ļ���\\treble_word.txt"));
			for(Map.Entry<String,Integer>key:tlist){
				bwrite.write(key.getKey()+":"+key.getValue()+"\r\n");
			}
			bwrite.close();
			
			System.out.println("�������н����������ļ�");
			
		}catch(Exception e){
			System.out.print(e);
		}
	}
}
