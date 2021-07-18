#include "Smoothing_5.h"



Smoothing_5::Smoothing_5()
{
}


Smoothing_5::~Smoothing_5()
{
}

void Smoothing_5::Smoothing_5_processing(){
	int oper[5][5];//ƽ������ n = 3
	memset(oper, 0, sizeof(oper));//ȫ����ʼ��Ϊ0
	for (int i = 0; i < img.rows; i++) {//�� i = 1 �п�ʼ
		for (int j = 0; j < img.cols; j++) {//i = 1 ɨ������ j �� 
			int temp = 0;//���������µ��������ص�ֵ
			for (int k = 0; k < 5; k++) {//�����һ������ʱ�����ӿ�ʼɨ�������ų����Ե��
				for (int l = 0; l < 5; l++) {
					if ((i - 2 + k < 0) || (j - 2 + l < 0) || (i - 2 + k >= img.rows) || (j - 2 + l >= img.cols))//������λ�ڱ�Ե����ʱ���ų�ͼ���в����ڵĵ�
						continue;
					temp += (oper[k][l] + 1) *img.at<Vec3b>(i - 2 + k, j - 2 + l)[0];
				}
			}
			temp /= (5 * 5);//֮���Էź�������Ϊ1 / 9 ��Ӱ��
							//������������dstimg�������ص����ͨ��ֵ
			for (int m = 0; m < 3; m++) {
				img_temp.at<Vec3b>(i, j)[m] = temp;
			}
		}
	}
}
