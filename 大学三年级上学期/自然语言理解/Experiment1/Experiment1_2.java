package my_project;//����1���Ų���

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Experiment1_2{//���ڴ��������
    public static Boolean Is_label(String p){//�������жϺ���
	    if(p.equals("��")||p.equals("��")||p.equals("��")||p.equals("��")||p.equals("��")||p.equals("��"))
		    return true;
		return false;
	}
	public static void main(String args[]){
		HashMap<String,Integer> sword=new HashMap<String,Integer>();
		HashMap<String,Integer> dword=new HashMap<String,Integer>();
		Pattern pat=Pattern.compile("(([\u4E00-\u9FA5]|��|��|��|��|��|��)*)(/)");
		String text="";
		try{
			BufferedReader bfread=new BufferedReader(new FileReader("E:\\eclipse-workspace\\my_project\\src\\my_project\\txt\\news.txt"));
		    text=bfread.readLine();
		    String Text="B";//text��ʼ��ΪB����Ϊ����ngram��˫����Ҫ
		    while(text!=null){
		       Matcher mat=pat.matcher(text);
		    	while(mat.find()){
		    		if(Is_label(mat.group(1))){//����Ǳ�㣬����EB����ʾǰ��Ͽ�
		    			Text+="EB";
		    		}else{
		    		    Text+=mat.group(1);//���򣬾ͽ��û��ֶεĺ����ַ�����Text
		    		}
		    	}
		    	Text+="EE";//������EE�����ڱ�֤���һ��˫���ǣ��ַ���+E
		    	
		    	for(int i=0;i<Text.length()-1;i++){
		    		
		   			if(sword.containsKey(Text.substring(i,i+1))){//�Ե��ֵ���ȡ
		   				sword.put(Text.substring(i,i+1), sword.get(Text.substring(i,i+1))+1);//����ⵥ���Ѿ��У����1
		    		}else{
		    			sword.put(Text.substring(i,i+1), 1);//û�У�����1
		    		}
		    		
		    		if(Text.substring(i, i+2).equals("BB")||Text.substring(i, i+2).equals("EE")||Text.substring(i, i+2).equals("BE")||Text.substring(i, i+2).equals("EB"));
		    		else{
		    			if(dword.containsKey(Text.substring(i,i+2))){//�Է��ַ�˫�ֵ���ȡ
		    				dword.put(Text.substring(i,i+2), dword.get(Text.substring(i,i+2))+1);//����У����1
		    			}else{
		    				dword.put(Text.substring(i,i+2), 1);//û�У�����1
		    			}
		    		}
		    	}
		    	Text="B";
		    	text=bfread.readLine();
		    }
		    System.out.println("�������н����������ļ�");
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
			bfread.close();
		
			BufferedWriter bwrite=new BufferedWriter(new FileWriter("E:\\eclipse-workspace\\my_project\\src\\my_project\\txt\\sword_news.txt"));//��listд���ļ���ȥ

			for(Map.Entry<String,Integer> key:slist){
				bwrite.write(key.getKey()+":"+key.getValue()+"\r\n");
			}
			bwrite.close();
			bwrite=new BufferedWriter(new FileWriter("E:\\eclipse-workspace\\my_project\\src\\my_project\\txt\\dword_news.txt"));
			for(Map.Entry<String,Integer> key:dlist){
				bwrite.write(key.getKey()+":"+key.getValue()+"\r\n");
			}
			bwrite.close();
		}catch(Exception e){
			System.out.println(e);
		}
	}
}
