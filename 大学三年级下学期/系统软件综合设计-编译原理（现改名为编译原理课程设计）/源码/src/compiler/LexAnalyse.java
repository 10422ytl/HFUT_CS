package compiler;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * �ʷ�������
 * 
 * 
 */
public class LexAnalyse {

	ArrayList<Word> wordList = new ArrayList<Word>();//���ʱ�
	ArrayList<Error> errorList = new ArrayList<Error>();//������Ϣ�б�
	int wordCount = 0;// ͳ�Ƶ��ʸ���
	int errorCount = 0;// ͳ�ƴ������
	boolean noteFlag = false;// ����ע�ͱ�־
	boolean lexErrorFlag = false;// �ʷ����������־
	public LexAnalyse() {

	}

	public LexAnalyse(String str) {
		lexAnalyse(str);
	}
	
	/*���·�����
	1��Ϊ��ȷ����ʼλ ���ڼ���while
	2���ж�while�õ���word����Word���е���һ��type
	*/
	/**
	 * �����ַ� �ж��Ƿ�Ϊ����
	 * 
	 * @param 
	 * @return
	 */
	private static boolean isDigit(char ch) {
		boolean flag = false;
		if ('0' <= ch && ch <= '9')
			flag = true;
		return flag;
	}

	/**
	 * �ַ��� �ж��Ƿ�Ϊ����int
	 * 
	 * @param string
	 * @return
	 */
	private static boolean isInteger(String word) {
		int i;
		boolean flag = false;
		for (i = 0; i < word.length(); i++) {
			if (Character.isDigit(word.charAt(i))) {
				continue;
			} else {
				break;
			}
		}
		if (i == word.length()) {
			flag = true;
		}
		return flag;
	}

	/**
 	 * �ַ��� �ж��Ƿ���\char\
	 * @param 
	 * @return
	 */
	private static boolean isChar(String word) {
		boolean flag = false;
		int i = 0;
		char temp = word.charAt(i);
		if (temp == '\'') {
			for (i = 1; i < word.length(); i++) {
				temp = word.charAt(i);
				if (0 <= temp && temp <= 255)
					continue;
				else
					break;
			}
			if (i + 1 == word.length() && word.charAt(i) == '\'')
				flag = true;
		} else
			return flag;

		return flag;
	}

	/**
	 * �ַ� �ж��Ƿ�Ϊ��ĸ
	 * 
	 * @param ch
	 * @return
	 */
	private static boolean isLetter(char ch) {
		boolean flag = false;
		if (('a' <= ch && ch <= 'z') || ('A' <= ch && ch <= 'Z'))
			flag = true;
		return flag;
	}

	/**
	 * �ַ��� �ж��Ƿ�Ϊ�Ϸ���ʶ��ID
	 * 
	 * @param 
	 * @return
	 */
	private static boolean isID(String word) {
		boolean flag = false;
		int i = 0;
		if (Word.isKey(word))
			return flag;
		char temp = word.charAt(i);
		if (isLetter(temp) || temp == '_') {//��ĸ����_��ͷ
			for (i = 1; i < word.length(); i++) {
				temp = word.charAt(i);
				if (isLetter(temp) || temp == '_' || isDigit(temp))//ֻ������ĸ��_������
					continue;
				else
					break;
			}
			if (i >= word.length())
				flag = true;
		} else
			return flag;

		return flag;
	}

	/**
	 * �жϴʷ������Ƿ������
	 * 
	 */
	public boolean isFail() {
		return lexErrorFlag;
	}

	public void analyse(String str, int line) {//str���Ǵ�����հ׷��ŵ� ���д���
		int beginIndex;
		int endIndex;
		int index = 0;
		int length = str.length();
		Word word = null;
		Error error;
		// boolean flag=false;
		char temp;
		while (index < length) {//
			temp = str.charAt(index);
			if (!noteFlag) {//���Ƕ���ע��
				if (isLetter(temp) || temp == '_') {//�������ĸ����_��ͷ�����ж��ǲ��Ǳ�־��
					beginIndex = index; //��ʼΪ 0
					index++;
					// temp=str.charAt(index);
					while ((index < length)//whileѭ�� ֱ������&&�еĸ������
							&& (!Word.isBoundarySign(str.substring(index,index + 1)))//����ͷ����ĺ���� ��beginIndex��ʼȡ����endIndex��������0��ʼ�������в�����endIndexλ�õ��ַ�
							&& (!Word.isOperator(str.substring(index, index + 1)))
							&& (str.charAt(index) != ' ')
							&& (str.charAt(index) != '\t')
							&& (str.charAt(index) != '\r')
							&& (str.charAt(index) != '\n'))
					{
						index++;
					}
					endIndex = index;
					word = new Word();
					wordCount++;
					word.id = wordCount;
					word.line = line;//���д���
					word.value = str.substring(beginIndex, endIndex);//�Ѹո�while�õ��Ĵʴ���wordList
					if (Word.isKey(word.value)) {
						word.type = Word.KEY;
					} else if (isID(word.value)) {
						word.type = Word.IDENTIFIER;
					} else {//������������ǣ��Ǿ���һ���Ƿ���ʶ��
						word.type = Word.UNIDEF;
						word.flag = false;
						errorCount++;
						error = new Error(errorCount, "�Ƿ���ʶ��", word.line, word);
						errorList.add(error);
						lexErrorFlag = true;
					}
					index--;//

				} else if (isDigit(temp)) {//�����ʼλ������ �ж��ǲ���int����
					beginIndex = index;
					index++;
					while ((index < length)
							&& (!Word.isBoundarySign(str.substring(index,index + 1)))
							&& (!Word.isOperator(str.substring(index, index + 1)))
							&& (str.charAt(index) != ' ')
							&& (str.charAt(index) != '\t')
							&& (str.charAt(index) != '\r')
							&& (str.charAt(index) != '\n')) 
					{
						index++;
					}
					endIndex = index;
					word = new Word();
					wordCount++;//lexAnalyse�е�ȫ�ֱ���
					word.id = wordCount;
					word.line = line;
					word.value = str.substring(beginIndex, endIndex);
					if (isInteger(word.value)) {
						word.type = Word.INT_CONST;
					} else {
						word.type = Word.UNIDEF;
						word.flag = false;//Ӧ����������ʶ���Ǵ����
						errorCount++;
						error = new Error(errorCount, "�Ƿ���ʶ��", word.line, word);
						errorList.add(error);
						lexErrorFlag = true;
					}
					index--;
					
				} else if (String.valueOf(str.charAt(index)).equals("'")) {//����һ�����ַ���'ʱ �ַ�����
					//flag=true;
					beginIndex = index;
					index++;
					temp = str.charAt(index);
					while (index < length && (0 <= temp && temp <= 255)) {
						if (String.valueOf(str.charAt(index)).equals("'"))//ֱ������'
							break;
						index++;
						// temp=str.charAt(index);
					}
					if (index < length) {
						endIndex = index;
						word = new Word();
						wordCount++;
						word.id = wordCount;
						word.line = line;
						word.value = str.substring(beginIndex, endIndex);
						word.type = Word.CHAR_CONST;
						// flag=true;
						// word.flag=flag;
						index--;
					} else {
						endIndex = index;
						word = new Word();
						wordCount++;
						word.id = wordCount;
						word.line = line;
						word.value = str.substring(beginIndex, endIndex);
						word.type = Word.UNIDEF;
						word.flag = false;
						errorCount++;
						error = new Error(errorCount, "�Ƿ���ʶ��", word.line, word);
						errorList.add(error);
						lexErrorFlag = true;
						index--;
					}
				} else if (temp == '=') {//����һ���ַ��� = ʱ ����������һ�� ���� = ���� ==
					beginIndex = index;
					index++;
					if (index < length && str.charAt(index) == '=') {
						endIndex = index + 1;
						word = new Word();
						wordCount++;
						word.id = wordCount;
						word.line = line;
						word.value = str.substring(beginIndex, endIndex);
						word.type = Word.OPERATOR;
					} else {
						// endIndex=index;
						word = new Word();
						wordCount++;
						word.id = wordCount;
						word.line = line;
						word.value = str.substring(index - 1, index);
						word.type = Word.OPERATOR;
						index--;
					}
				} else if (temp == '!') {//����һ���ַ��� ! ����������һ������ ! ���� !=
					beginIndex = index;
					index++;
					if (index < length && str.charAt(index) == '=') {
						endIndex = index + 1;
						word = new Word();
						wordCount++;
						word.id = wordCount;
						word.line = line;
						word.value = str.substring(beginIndex, endIndex);
						word.type = Word.OPERATOR;
						index++;
					} else {
						// endIndex=index;
						word = new Word();
						wordCount++;
						word.id = wordCount;
						word.line = line;
						word.value = str.substring(index - 1, index);
						word.type = Word.OPERATOR;
						index--;
					}
				} else if (temp == '&') {//����һ���ַ��� & ����������һ������ & ���� &&
					beginIndex = index;
					index++;
					if (index < length && str.charAt(index) == '&') {
						endIndex = index + 1;
						word = new Word();
						wordCount++;
						word.id = wordCount;
						word.line = line;
						word.value = str.substring(beginIndex, endIndex);
						word.type = Word.OPERATOR;
					} else {
						// endIndex=index;
						word = new Word();
						wordCount++;
						word.id = wordCount;
						word.line = line;
						word.value = str.substring(index - 1, index);
						word.type = Word.OPERATOR;
						index--;
					}
				} else if (temp == '|') {//����һ���ַ��� | ����������һ������ | ���� ||
					beginIndex = index;
					index++;
					if (index < length && str.charAt(index) == '|') {
						endIndex = index + 1;
						word = new Word();
						wordCount++;
						word.id = wordCount;
						word.line = line;
						word.value = str.substring(beginIndex, endIndex);
						word.type = Word.OPERATOR;
					} else {
						// endIndex=index;
						word = new Word();
						wordCount++;
						word.id = wordCount;
						word.line = line;
						word.value = str.substring(index - 1, index);
						word.type = Word.OPERATOR;
						index--;
					}
				} else if (temp == '+') {//����һ���ַ��� + ����������һ������ + ���� ++
					beginIndex = index;
					index++;
					if (index < length && str.charAt(index) == '+') {
						endIndex = index + 1;
						word = new Word();
						wordCount++;
						word.id = wordCount;
						word.line = line;
						word.value = str.substring(beginIndex, endIndex);
						word.type = Word.OPERATOR;

					} else {
						// endIndex=index;
						word = new Word();
						wordCount++;
						word.id = wordCount;
						word.line = line;
						word.value = str.substring(index - 1, index);
						word.type = Word.OPERATOR;
						index--;
					}
				} else if (temp == '-') {//����һ���ַ��� - ����������һ������ - ���� --
					beginIndex = index;
					index++;
					if (index < length && str.charAt(index) == '-') {
						endIndex = index + 1;
						word = new Word();
						wordCount++;
						word.id = wordCount;
						word.line = line;
						word.value = str.substring(beginIndex, endIndex);
						word.type = Word.OPERATOR;
					} else {
						// endIndex=index;
						word = new Word();
						wordCount++;
						word.id = wordCount;
						word.line = line;
						word.value = str.substring(index - 1, index);
						word.type = Word.OPERATOR;
						index--;
					}
				} else if (temp == '/') {//����һ���ַ��� / ����������һ������ / ���� /* ����//
					index++;
					if (index < length && str.charAt(index) == '/')
						break;//����ע�;Ͳ����� ֱ��������һ�� ��Ϊ ����ע��ֻ������һ�е������� ����Ҳ����Ҫ����
					/*
					 * { index++; while(str.charAt(index)!='\n'){ index++; } }
					 */
					else if (index < length && str.charAt(index) == '*') {//����Ƕ���ע��
						noteFlag = true;
					} else {//ֻ�ǵ����ĳ���
						word = new Word();
						wordCount++;
						word.id = wordCount;
						word.line = line;
						word.value = str.substring(index - 1, index);
						word.type = Word.OPERATOR;
					}
					index--;
				} else {// ���Ǳ�ʶ�������ֳ������ַ�������

					switch (temp) {
					case ' ':
					case '\t':
					case '\r':
					case '\n':
						word = null;
						break;// ���˿հ��ַ�
					case '[':
					case ']':
					case '(':
					case ')':
					case '{':
					case '}':
					case ',':
					case '"':
					case '.':
					case ';':
						// case '+':
						// case '-':
					case '*':
						// case '/':
					case '%':
					case '>':
					case '<':
					case '?':
					case '#':
						word = new Word();
						word.id = ++wordCount;
						word.line = line;
						word.value = String.valueOf(temp);
						if (Word.isOperator(word.value))
							word.type = Word.OPERATOR;
						else if (Word.isBoundarySign(word.value))
							word.type = Word.BOUNDARYSIGN;
						else
							word.type = Word.END;
						break;
					default://û���ǵ��ķǷ���ʶ��
						word = new Word();
						wordCount++;
						word.id = wordCount;
						word.line = line;
						word.value = String.valueOf(temp);
						word.type = Word.UNIDEF;
						word.flag = false;
						errorCount++;
						error = new Error(errorCount, "�Ƿ���ʶ��", word.line, word);
						errorList.add(error);
						lexErrorFlag = true;
					}
				}
			} else {//���ǰ�淢����/* Ҳ���Ƕ���ע�� ��ôͨ��indexOf�ҵ�����ע�͵Ľ�β ��ע������
				int i = str.indexOf("*/");
				if (i != -1) {
					noteFlag = false;
					index = i + 2;
					continue;
				} else
					break;
			}
			if (word == null) {
				index++;
				continue;
			}
			wordList.add(word);
			index++;
		}
	}

	public ArrayList<Word> lexAnalyse(String str) {
		String buffer[];
		buffer = str.split("\n");
		int line = 1;
		for (int i = 0; i < buffer.length; i++) {
			analyse(buffer[i].trim(), line);
			line++;
		}
		if (!wordList.get(wordList.size() - 1).type.equals(Word.END)) {
			Word word = new Word(++wordCount, "#", Word.END, line++);
			wordList.add(word);
		}
		return wordList;
	}

	public ArrayList<Word> lexAnalyse1(String filePath) throws IOException {
		FileInputStream fis = new FileInputStream(filePath);
		BufferedInputStream bis = new BufferedInputStream(fis);
		InputStreamReader isr = new InputStreamReader(bis, "utf-8");
		BufferedReader inbr = new BufferedReader(isr);
		String str = "";
		int line = 1;
		while ((str = inbr.readLine()) != null) {
			// System.out.println(str);
			analyse(str.trim(), line);
			line++;
		}
		inbr.close();
		if (!wordList.get(wordList.size() - 1).type.equals(Word.END)) {
			Word word = new Word(++wordCount, "#", Word.END, line++);
			wordList.add(word);
		}
		return wordList;
	}

	public String outputWordList() throws IOException {//����֮�󣬷���һ���ļ��� ��InfoFrameʹ��
		File file = new File("./output/");
		if (!file.exists()) {
			file.mkdirs();
			file.createNewFile();// �������ļ������ھʹ�����
		}
		String path = file.getAbsolutePath();
		FileOutputStream fos = new FileOutputStream(path + "/wordList.txt");
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		OutputStreamWriter osw1 = new OutputStreamWriter(bos, "utf-8");
		PrintWriter pw1 = new PrintWriter(osw1);
		pw1.println("�������\t���ʵ�ֵ\t��������\t����������\t�����Ƿ�Ϸ�");//mainframe��ʾ��ֻ��compiler����ģ��ѵ�mainframe��backup�޹أ�
		Word word;
		for (int i = 0; i < wordList.size(); i++) {//����ȡ��wordList
			word = wordList.get(i);
			if(isInteger(word.value)) {//����һ��λ�� ʹ֮����
				pw1.println(word.id + "\t\t" + word.value + "\t\t" + word.type + "\t" + word.line + "\t\t" + word.flag);
			}
			else {
			pw1.println(word.id + "\t\t" + word.value + "\t\t" + word.type + "\t" + "\t" + word.line + "\t\t" + word.flag);
			}
		}
		if (lexErrorFlag) {
			Error error;
			pw1.println("������Ϣ���£�");

			pw1.println("�������\t������Ϣ\t���������� \t���󵥴�");
			for (int i = 0; i < errorList.size(); i++) {
				error = errorList.get(i);
				pw1.println(error.id + "\t" + error.info + "\t\t" + error.line
						+ "\t" + error.word.value);
			}
		} else {
			pw1.println("�ʷ�����ͨ����");
		}
		pw1.close();
		return path + "/wordList.txt";
	}

	

	public static void main(String[] args) throws IOException {
		LexAnalyse lex = new LexAnalyse();
		lex.lexAnalyse1("b.c");
		lex.outputWordList();
	}
}
