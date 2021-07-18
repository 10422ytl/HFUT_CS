#include <iostream>
using namespace std;
const int MaxLen = 20;
const int Length = 20;
char ch, Y;
char strToken[Length];

bool flag = true;
int point = 0, step = 1;


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
	void outl();
	~stack() {};//��������
private:
	int count;//ջ����
	char data[MaxLen];//ջ��Ԫ��
};

stack l, r;//lΪ����ջ��rΪ״̬ջ

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
		c = data[count - 1];
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
	for (int i = 0; i<count; i++)
		cout << data[i];
	cout << '\t';
}

void stack::outl() {
	for (int i = 0; i<count; i++)
		cout << int(data[i]);
	cout << '\t';
}
void print(int i, char *c) {
	for (int j = i; j<Length; j++)
		cout << c[j];
	cout << '\t';
}

void Goto(int i, char c) {
	if (i == 0) {
		if (c == 'E') {
			r.push(1);
			cout << ",GOTO(0,E)=1 ��ջ" << endl;
		}
		else if (c == 'T') {
			r.push(2);
			cout << ",GOTO(0,T)=2 ��ջ" << endl;
		}
		else if (c == 'F') {
			r.push(3);
			cout << ",GOTO(0,F)=3 ��ջ" << endl;
		}
		else
			flag = false;
	}
	else if (i == 4) {
		if (c == 'E') {
			r.push(8);
			cout << ",GOTO(4,E)=8 ��ջ" << endl;
		}
		else if (c == 'T') {
			r.push(2);
			cout << ",GOTO(4,T)=2 ��ջ" << endl;
		}
		else if (c == 'F') {
			r.push(3);
			cout << ",GOTO(4,F)=3 ��ջ" << endl;
		}
		else
			flag = false;
	}
	else if (i == 6) {
		if (c == 'T') {
			r.push(9);
			cout << ",GOTO(6,T)=9 ��ջ" << endl;
		}
		else if (c == 'F') {
			r.push(3);
			cout << ",GOTO(6,F)=3 ��ջ" << endl;
		}
		else
			flag = false;
	}
	else if (i == 7) {
		if (c == 'F') {
			r.push(10);
			cout << ",GOTO(7,F)=10 ��ջ" << endl;
		}
		else
			flag = false;
	}
	else
		flag = false;
}

void Action0() {
	if (ch == 'i') {
		cout << step++ << '\t';
		r.outl();
		l.out();
		print(point - 1, strToken);
		cout << "ACTION[0,i]=S5,״̬5��ջ" << endl;
		r.push(5);
		l.push(ch);
		ch = strToken[point++];
	}
	else if (ch == '(') {
		cout << step++ << '\t';
		r.outl();
		l.out();
		print(point - 1, strToken);
		cout << "ACTION[0,(]=S4,״̬4��ջ" << endl;
		r.push(4);
		l.push(ch);
		ch = strToken[point++];
	}
	else
		flag = false;
}

void Action1() {
	if (ch == '+') {
		cout << step++ << '\t';
		r.outl();
		l.out();
		print(point - 1, strToken);
		cout << "ACTION[1,+]=S6,״̬6��ջ" << endl;
		r.push(6);
		l.push(ch);
		ch = strToken[point++];
	}
	else if (ch == '#') {
		flag = false;
		cout << step++ << '\t';
		r.outl();
		l.out();
		print(point - 1, strToken);
		cout << "Acc:�����ɹ�" << endl;
	}
	else
		flag = false;
}

void Action2() {
	if (ch == '*') {
		cout << step++ << '\t';
		r.outl();
		l.out();
		print(point - 1, strToken);
		cout << "ACTION[2,*]=S7,״̬7��ջ" << endl;
		r.push(7);
		l.push(ch);
		ch = strToken[point++];
	}
	else if ((ch == '+') || (ch == ')') || (ch == '#')) {
		cout << step++ << '\t';
		r.outl();
		l.out();
		l.pop();
		l.push('E');
		print(point - 1, strToken);
		cout << "r2:E->T��Լ";
		r.pop();
		r.get_top(Y);
		Goto(int(Y), 'E');
	}
	else
		flag = false;
}

void Action3() {
	if ((ch == '+') || (ch == '*') || (ch == ')') || (ch == '#')) {
		cout << step++ << '\t';
		r.outl();
		l.out();
		l.pop();
		l.push('T');
		print(point - 1, strToken);
		cout << "r4:T->F��Լ";
		r.pop();
		r.get_top(Y);
		Goto(int(Y), 'T');
	}
	else
		flag = false;
}

void Action4_6_7(int x) {
	if (ch == 'i') {
		cout << step++ << '\t';
		r.outl();
		l.out();
		print(point - 1, strToken);
		cout << "ACTION[";
		cout << x << ",i]=S5,״̬5��ջ" << endl;//???
		r.push(5);
		l.push(ch);
		ch = strToken[point++];
	}
	else if (ch == '(') {
		cout << step++ << '\t';
		r.outl();
		l.out();
		print(point - 1, strToken);
		cout << "ACTION[";
		cout << x << ",(]=S4,״̬4��ջ" << endl;
		r.push(4);
		l.push(ch);
		ch = strToken[point++];
	}
	else
		flag = false;
}

void Action5() {
	if ((ch == '+') || (ch == '*') || (ch == ')') || (ch == '#')) {
		cout << step++ << '\t';
		r.outl();
		l.out();
		l.pop();
		l.push('F');
		print(point - 1, strToken);
		cout << "r6:F->i��Լ";
		r.pop();
		r.get_top(Y);
		Goto(int(Y), 'F');
	}
	else
		flag = false;
}

void Action8() {
	if (ch == '+') {
		cout << step++ << '\t';
		r.outl();
		l.out();
		print(point - 1, strToken);
		cout << "ACTION[8,+]=S6,״̬6��ջ" << endl;
		r.push(6);
		l.push(ch);
		ch = strToken[point++];
	}
	else if (ch == ')') {
		cout << step++ << '\t';
		r.outl();
		l.out();
		print(point - 1, strToken);
		cout << "ACTION[8,)]=S11,״̬11��ջ" << endl;
		r.push(11);
		l.push(ch);
		ch = strToken[point++];
	}
	else
		flag = false;
}

void Action9() {
	if (ch == '*') {
		cout << step++ << '\t';
		r.outl();
		l.out();
		print(point - 1, strToken);
		cout << "ACTION[9,*]=S7,״̬7��ջ" << endl;
		r.push(7);
		l.push(ch);
		ch = strToken[point++];
	}
	else if ((ch == '+') || (ch == ')') || (ch == '#')) {
		cout << step++ << '\t';
		r.outl();
		l.out();
		l.pop();
		l.pop();
		l.pop();
		l.push('E');
		print(point - 1, strToken);
		cout << "r1:E->E+T��Լ";
		r.pop();
		r.pop();
		r.pop();
		r.get_top(Y);
		Goto(int(Y), 'E');
	}
	else
		flag = false;
}

void Action10() {
	if ((ch == '+') || (ch == '*') || (ch == ')') || (ch == '#')) {
		cout << step++ << '\t';
		r.outl();
		l.out();
		l.pop();
		l.pop();
		l.pop();
		l.push('T');
		print(point - 1, strToken);
		cout << "r3:T->T*F��Լ";
		r.pop();
		r.pop();
		r.pop();
		r.get_top(Y);
		Goto(int(Y), 'T');
	}
	else
		flag = false;
}

void Action11() {
	if ((ch == '+') || (ch == '*') || (ch == ')') || (ch == '#')) {
		cout << step++ << '\t';
		r.outl();
		l.out();
		l.pop();
		l.pop();
		l.pop();
		l.push('F');
		print(point - 1, strToken);
		cout << "r5:F->(E)��Լ";
		r.pop();
		r.pop();
		r.pop();
		r.get_top(Y);
		Goto(int(Y), 'F');
	}
	else
		flag = false;
}

void run() {
	cout << "����������ַ���" << endl;
	cin >> strToken;
	cout << "����\t" << "״̬ջ\t" << "����ջ\t" << "���봮\t\t" << "����˵��" << endl;
	ch = strToken[point++];
	l.push('#');
	r.push(0);
	r.get_top(Y);
	while (flag) {
		if (int(Y) == 0)
			Action0();
		else if (int(Y) == 1)
			Action1();
		else if (int(Y) == 2)
			Action2();
		else if (int(Y) == 3)
			Action3();
		else if ((int(Y) == 4) || (int(Y) == 6) || (int(Y) == 7))
			Action4_6_7(int(Y));
		else if (int(Y) == 5)
			Action5();
		else if (int(Y) == 8)
			Action8();
		else if (int(Y) == 9)
			Action9();
		else if (int(Y) == 10)
			Action10();
		else if (int(Y) == 11)
			Action11();
		else
			flag = false;
		r.get_top(Y);
	}
}
int main() {
	run();
	cin.get();
	cin.get();
	return 0;
}

