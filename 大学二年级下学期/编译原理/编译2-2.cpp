#include<iostream>
  
#include <string>

using namespace std;
const int MaxLen = 20;//��ʼ��ջ��󳤶�
const int Length = 20;//��ʼ��������󳤶�

char Vn[5] = { 'E','G','T','S','F' };//���ս������
char Vt[8] = { 'i','(',')','+','-','*','/','#' };//�ս������

char ch, X;//ch���ڶ�ȡ��ǰ�ַ���X���ڻ�ȡջ��Ԫ��
char strToken[Length];//�洢���������ʽ������洢

struct LL//
{
	char*c;
};
LL E[8] = { "TG","TG","error","error","error","error","error","error" };
LL G[8] = { "error","error","null","+TG","-TG","error","error","null" };
LL T[8] = { "FS","FS","error","error","error","error","error","error" };
LL S[8] = { "error","error","null","null","null","*FS","/FS","null" };
LL F[8] = { "i","(E)","error","error","error","error","error","error" };

class stack//ջ�Ĺ��켰��ʼ��
{
public:
	stack();
	bool empty()const;//�жϿ�
	bool full()const;//�ж���
	bool get_top(char &c)const;//ȡջ��Ԫ��
	bool push(const char c);//��ջ
	bool pop();//��ջ
	void out();//���ջ��Ԫ��
	~stack() {};//��������
private:
	int count;//ջ����
	char data[MaxLen];//ջ��Ԫ��
};

stack::stack() {
	count = 0;
}

bool stack::empty() const {
	if (count == 0)
		return 1;
	return 0;
}

bool stack::full() const {
	if (count == MaxLen)
		return 1;
	return 0;
}

bool stack::get_top(char &c)const {
	if (empty())
		return 1;
	else {
		c =data[count - 1];
		return 0;
	}
}

bool stack::push(const char c) {
	if (full())
		return 1;
	else {
		data[count++] = c;//count�ȱ�����data Ȼ��++
		return 0;
	}
}

bool stack::pop() {
	if (empty())
		return 1;
	count--;
	return 0;
}

void stack::out() {
	for (int i = 0; i < count; i++)
		cout << data[i];
	cout << '\t';
}

int length(char *c) {
	int l = 0;
	for (int i = 0; c[i] != '\0'; i++)//��='0'�����е��ַ�����β������
		l++;
	return l;
}


void print(int i, char *c) {
	for (int j = i; j < Length; j++)
		cout << c[j];
	cout << '\t'; //����Ʊ��ֻ���һ�ΰ�
}

void run() {
	bool flag = true;//ѭ������?
	int step = 0, point = 0;//���裬ָ��
	int len;//����
	cout << "���Ҫ�����ķ��Ŵ���" << endl;
	cin >> strToken;
	ch = strToken[point++];
	stack s;
	s.push('#');
	s.push('E');
	s.get_top(X);
	cout << "����\t" << "����ջ\t" << "ʣ�����봮\t\t" << "���ò���ʽ\t" << "����" << endl;
	cout << step++ << '\t';
	s.out();
	print(point - 1, strToken);
	cout << '\t' << "��ʼ��" << endl;
	while (flag) {
		if ((X == Vt[0]) || (X == Vt[1]) || (X == Vt[2]) || (X == Vt[3]) || (X == Vt[4]) || (X == Vt[5]) || (X == Vt[6])) {//�ս��ƥ��
			if (X == ch) {
				s.pop();
				s.get_top(X);
				ch = strToken[point++];
				cout << step++ << '\t';
				s.out();
				print(point - 1, strToken);
				cout << '\t' << "\tGETNEXT(l)" << endl;
			}
			else {
				flag = false;
			}
		}
		else if (X == '#') {//�����ɹ���ֹͣ����	
			if (X == ch) {
				cout << step++ << '\t';
				s.out();
				print(point - 1, strToken);
				cout << '\t'<< X << "->" << ch << '\t'  << "\t����" << endl;
				s.pop();
				flag = false;
			}
			else {
				flag = false;
			}
		}
		else if (X == Vn[0]) {
			for (int i = 0; i < 8; i++)
				if (ch == Vt[i]) {
					if (E[i].c=="error") {
						flag = false;
					}
					else {
						s.pop();
						len = length(E[i].c) - 1;
						for (int j = len; j >= 0; j--)
							s.push(E[i].c[j]);
						cout << step++ << '\t';
						s.out();
						print(point - 1, strToken);
						cout << X << "->" << E[i].c << '\t' << "\tPOP,PUSH(";
						for (int j = len; j >= 0; j--)
							cout << E[i].c[j];
						cout << ")" << endl;
						s.get_top(X);
					}
				}
		}
		else if (X == Vn[1]) {
			for (int i = 0; i < 8; i++)
				if (ch == Vt[i]) {
					if (strcmp(G[i].c, "null") == 0) {
						s.pop();
						cout << step++ << '\t';
						s.out();
						print(point - 1, strToken);
						cout << X << "->" << "��" << '\t' << "\tPOP" << endl;
						s.get_top(X);
					}
					else if (strcmp(G[i].c, "error") == 0) {
						flag = false;
					}
					else {
						s.pop();
						len = length(G[i].c) - 1;
						for (int j = len; j >= 0; j--)
							s.push(G[i].c[j]);
						cout << step++ << '\t';
						s.out();
						print(point - 1, strToken);
						cout << X << "->" << G[i].c << '\t' << "\tPOP.PUSH(";
						for (int j = len; j >= 0; j--)
							cout << G[i].c[j];
						cout << ")" << endl;
						s.get_top(X);
					}
				}
		}
		else if (X == Vn[2]) {
			for (int i = 0; i < 8; i++)
				if (ch == Vt[i]) {
					if (strcmp(T[i].c, "error") == 0) {
						flag = false;
					}
					else {
						s.pop();
						len = length(T[i].c) - 1;
						for (int j = len; j >= 0; j--)
							s.push(T[i].c[j]);
						cout << step++ << '\t';
						s.out();
						print(point - 1, strToken);
						cout << X << "->" << T[i].c << '\t' << "\tPOP.PUSH(";
						for (int j = len; j >= 0; j--)
							cout << T[i].c[j];
						cout << ")" << endl;
						s.get_top(X);
					}
				}
		}
		else if (X == Vn[3]) {
			for (int i = 0; i < 8; i++)
				if (ch == Vt[i]) {
					if (strcmp(S[i].c, "null") == 0) {
						s.pop();
						cout << step++ << '\t';
						s.out();
						print(point - 1, strToken);
						cout << X << "->" << "��" << '\t' << "\tPOP" << endl;
						s.get_top(X);
					}
					else if (strcmp(S[i].c, "error") == 0) {
						flag = false;
					}
					else {
						s.pop();
						len = length(S[i].c) - 1;
						for (int j = len; j >= 0; j--)
							s.push(S[i].c[j]);
						cout << step++ << '\t';
						s.out();
						print(point - 1, strToken);
						cout << X << "->" << S[i].c << '\t' << "\tPOP.PUSH(";
						for (int j = len; j >= 0; j--)
							cout << S[i].c[j];
						cout << ")" << endl;
						s.get_top(X);
					}
				}
		}
		else if (X == Vn[4]) {
			for (int i = 0; i < 7; i++)
				if (ch == Vt[i]) {
					if (strcmp(F[i].c, "error") == 0) {
						flag = false;
					}
					else {
						s.pop();
						len = length(F[i].c) - 1;
						for (int j = len; j >= 0; j--)
							s.push(F[i].c[j]);
						cout << step++ << '\t';
						s.out();
						print(point - 1, strToken);
						cout << X << "->" << F[i].c << '\t' << "\tPOP,PUSH(";
						for (int j = len; j >= 0; j--)
							cout << F[i].c[j];
						cout << ")" << endl;
						s.get_top(X);
					}
				}
		}
		else {//������
			flag = false;
		}
	}
}
int main() {
	run();
	system("pause");
	return 0;
}