#include<iostream>
#include<string>
#include<map>
#include<vector>
#include<stack>
#include<set>
#include<cstring>
using namespace std;
map<char, int>getnum;		//����map getnum
char get_char[100];         //��ö�Ӧ�ַ����ս���ͷ��ս����������
vector<string>proce;		//�洢����ʽ�Ҳ�
int table[100][100];		 //Ԥ��������ά����
int num = 0; int numvt = 0;     //numvt���ս�����ϣ�0�ǡ�#����numvtΪ�ս����λ�������
string first[100];
string follow[200];

void readin()
{
	memset(table, -1, sizeof(table));	//���ö�ά����table
	getnum['#'] = 0;	//��#��ʼ���������ս����map
	get_char[0] = '#';
	cout << "�������ս������" << endl;
	char x;
	do
	{
		cin >> x;
		getnum[x] = ++num;	//��һ������ķ��ս��������Ϊ++num����Ϊ1�����ս����map
		get_char[num] = x;
	} while (cin.peek() != '\n');
	numvt = ++num;		//�ս����ʼ��map
	getnum['@'] = numvt;        //����
	get_char[num] = ('@');
	cout << "��������ս������" << endl;
	do
	{
		cin >> x;
		getnum[x] = ++num;	//�ս����map
		get_char[num] = x;
	} while (cin.peek() != '\n');
	cout << "�������в���ʽ�������á�@����ʾ��,��end����:" << endl;
	string pro;
	while (cin >> pro&&pro != "end")
	{
		string ss;//ss���ڴ洢����ʽ����ʱ������proceѹ�����ʽ
		ss += pro[0];//�Ƚ�����ʽ�󲿽���ss(proce)
		for (int i = 3; i<pro.size(); i++)//i=3��������->
		{
			if (pro[i] == '|')
			{
				proce.push_back(ss);
				ss.clear(); 
				ss += pro[0];
			}
			else
			{
				ss += pro[i];
			}
		}
		proce.push_back(ss);
	}
}

void set_union(string &a, string b)  //ȡa,b������ֵ��a
{
	set<char>se;//set�����ظ�Ԫ��
	for (int i = 0; i<a.size(); i++)
		se.insert(a[i]);
	for (int i = 0; i<b.size(); i++)
		se.insert(b[i]);
	string ans;
	set<char>::iterator it;
	for (it = se.begin(); it != se.end(); it++)
		ans += *it;
	a = ans;
}

string get_f(int vn, int & has_0)     //�������ʽ�Ҳ���һ���ַ�Ϊ���ս������÷��ս����First�������÷��ս���ķ�@first���������ʽ�󲿵�first��
{
	if (vn == numvt)	//����ս��Ϊ����
		has_0 = 1;
	if (vn<numvt)	//����ս����Ϊ���֣����ر���
		return first[vn];
	string ans;
	for (int i = 0; i<proce.size(); i++)
	{
		if (getnum[proce[i][0]] == vn)
			ans += get_f(getnum[proce[i][1]], has_0);//�ݹ鴦��X->Y��Y->a��Y->@
	}
	return  ans;
}

void getfirst()
{
	for (int i = 1; i <= numvt; i++)     //���A->����A->@�������@����firstA
	{
		first[i] += ('0' + i);
	}
	for (int j = 0; j<proce.size(); j++)    //ɨ�����в���ʽ��A->X1X2X3...Xk
	{
		int k = 0; int has_0 = 0;        //kɨ��ò���ʽ
		do {
			has_0 = 0;
			k++;
			if (k == proce[j].size())  //���Ѿ��������ʽ�����Ҳ��ķ��ս�������Ҳ���First�������пմ�������@�����󲿵�First��
			{
				first[getnum[proce[j][0]]] += ('0' + numvt);
				break;
			}                    
			set_union(first[getnum[proce[j][0]]], get_f(getnum[proce[j][k]], has_0));//�����first(Yj)�е����з�@Ԫ�ض�����first(x)��
		} while (has_0);  //���޷��Ƴ�����Ϊֹ��ֹͣ�����ò���ʽ��������һ������ʽ
	}
}

void getfollow()
{
	set_union(follow[getnum[proce[0][0]]], "0");  //����ӡ�#�����ķ���ʼ���ŵ�follow����
	for (int j = 0; j<proce.size(); j++)       //����ɨ���в���ʽ
	{
		for (int jj = 1; jj<proce[j].size(); jj++)   //����ɨ���в���ʽ
		{
			if (getnum[proce[j][jj]] <= numvt)continue;  //�������ս��������
			int k = jj; int has_0;
			do
			{
				has_0 = 0;
				k++;
				if (k == proce[j].size())   //���Ѿ��������ʽĩβ
				{
					set_union(follow[getnum[proce[j][jj]]], follow[getnum[proce[j][0]]]);//�����A->��B����followA���뵽followB
					break;
				}
				set_union(follow[getnum[proce[j][jj]]], get_f(getnum[proce[j][k]], has_0));//�����A->��B��...,��first��\@����followB
			} while (has_0);
		}
	}
}

void gettable()          //����Ԥ�������A->���������[A,first(��)]   A->@,�����[A,follow(A)]
{
	for (int i = 0; i<proce.size(); i++)   //ɨ���в���ʽ
	{
		if (proce[i][1] == '@')     //ֱ���Ƴ����ֵģ�A->@,�����[A,follow(A)]
		{
			string flw = follow[getnum[proce[i][0]]];
			for (int k = 0; k<flw.size(); k++)
			{
				table[getnum[proce[i][0]]][flw[k] - '0'] = i;
			}
		}
		string temps = first[getnum[proce[i][1]]];	//����first��
		for (int j = 0; j<temps.size(); j++)               
		{

			if (temps[j] != ('0' + numvt))	//���A->���Ҧ���Ϊ@
			{
				table[getnum[proce[i][0]]][temps[j] - '0'] = i;//�����ʽ����[A,first(��)] 
			}
			else                               //�п��ֵģ�����Ħ���follow��
			{
				string flw = follow[getnum[proce[i][1]]];
				for (int k = 0; k<flw.size(); k++)
				{
					table[getnum[proce[i][0]]][flw[k] - '0'] = i;//�����ʽ����[A,follow(��)] 
				}
			}
		}
	}
}

string get_proce(int i)  //���proce��ÿ����Ӧ�Ĳ���ʽ��
{
	if (i<0)return " ";    //�޸ò���ʽ
	string ans;
	ans += proce[i][0];
	ans += "->";
	for (int j = 1; j<proce[i].size(); j++)
		ans += proce[i][j];
	return ans;
}

void print_first()
{
	cout << "first������:" << endl;
	for (int i = 1; i <= num; i++)
	{
		cout << "first [" << get_char[i] << "]: ";
		for (int j = 0; j<first[i].size(); j++)
			cout << get_char[first[i][j] - '0'] << " ";
		cout << endl;
	}
	cout << endl;
}

void print_follow()
{
	cout << "follow�����£�" << endl;
	for (int i = numvt + 1; i <= num; i++)
	{
		cout << "follow [" << get_char[i] << "]: ";
		for (int j = 0; j<follow[i].size(); j++)
			cout << get_char[follow[i][j] - '0'] << " ";
		cout << endl;
	}
	cout << endl;
}

void print_table()
{
	cout << "Ԥ����������£�" << endl;
	for (int i = 0; i<numvt; i++)
		cout << '\t' << get_char[i];
	cout << endl;
	for (int i = numvt + 1; i <= num; i++)
	{
		cout << get_char[i];
		for (int j = 0; j<numvt; j++)
		{
			cout << '\t' << get_proce(table[i][j]);
		}
		cout << endl;
	}
	cout << endl;
}

string word;
bool analyze()       //�ܿأ�������word�ĺϷ��ԣ����Ϸ���������в���ʽ��
{
	stack<char>sta;
	sta.push('#'); sta.push(proce[0][0]);
	int i = 0;
	while (!sta.empty())
	{
		int cur = sta.top();
		sta.pop();
		if (cur == word[i])       //���ս�����ƽ�
		{
			i++;
		}
		else  if (cur == '#')   //�ɹ�������
		{
			return 1;
		}
		else  if (table[getnum[cur]][getnum[word[i]]] != -1) //���
		{

			int k = table[getnum[cur]][getnum[word[i]]];
			cout << proce[k][0] << "->";
			for (int j = 1; j<proce[k].size(); j++)
				cout << proce[k][j];
			cout << endl;
			for (int j = proce[k].size() - 1; j>0; j--)  //������ջ
			{
				if (proce[k][j] != '@')
					sta.push(proce[k][j]);
			}
		}
		else      //ʧ�ܣ�
		{
			return 0;
		}
	}
	return 1;
}

int main()
{
	readin();
	getfirst();
	getfollow();
	getfollow();
	gettable();
	print_first();
	print_follow();
	print_table();

	cout << "�������֣�" << endl;
	cin >> word;
	if (analyze())
		cout << "succeed!������Ч�����ò���ʽ���ϡ�" << endl;
	else   cout << "error!" << endl;

	cin.get();
	cin.get();
	return 0;
}
