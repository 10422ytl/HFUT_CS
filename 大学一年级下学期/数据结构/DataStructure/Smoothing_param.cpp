#include "Smoothing_param.h"



Smoothing_param::Smoothing_param()
{
}


Smoothing_param::~Smoothing_param()
{
}

void Smoothing_param::Smoothing_param_processing(int n) {
	int * oper;
	oper = new int[n * n]() ;
	int x = (n - 1) / 2;
	for (int i = 0; i < img.rows; i++) {//�� i = 1 �п�ʼ
		for (int j = 0; j < img.cols; j++) {//i = 1 ɨ������ j �� 
			int temp = 0;//���������µ��������ص�ֵ
			for (int k = 0; k < n; k++) {//�����һ������ʱ�����ӿ�ʼɨ�������ų����Ե��
				for (int l = 0; l < n; l++) {
					if ((i - x + k < 0) || (j - x + l < 0) || (i - x + k >= img.rows) || (j - x + l >= img.cols))//������λ�ڱ�Ե����ʱ���ų�ͼ���в����ڵĵ�
						continue;
					temp += (oper[k * n + l] + 1) *img.at<Vec3b>(i - x + k, j - x + l)[0];
				}
			}
			temp /= (n * n);//֮���Էź�������Ϊ1 / 9 ��Ӱ��
							//������������dstimg�������ص����ͨ��ֵ
			for (int m = 0; m < 3; m++) {
				img_temp.at<Vec3b>(i, j)[m] = temp;
			}
		}
	}
	delete oper;
}