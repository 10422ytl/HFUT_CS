#include "Smoothing.h"



Smoothing::Smoothing()
{
}


Smoothing::~Smoothing()
{
}

void Smoothing::Smoothing_processing(){
	int oper[3][3];//ƽ������ n = 3
	memset(oper, 0, sizeof(oper));//ȫ����ʼ��Ϊ0
	for (int i = 0; i < img.rows; i++) {//�� i = 1 �п�ʼ
		for (int j = 0; j < img.cols; j++) {//i = 1 ɨ������ j �� 
			int temp = 0;//���������µ��������ص�ֵ
			for (int k = 0; k < 3; k++) {//�����һ������ʱ�����ӿ�ʼɨ�������ų����Ե��
				for (int l = 0; l < 3; l++){
					if ((i - 1 + k < 0) || (j - 1 + l < 0) || (i - 1 + k >= img.rows) || (j - 1 + l >= img.cols))//������λ�ڱ�Ե����ʱ���ų�ͼ���в����ڵĵ�
						continue;
					temp += (oper[k][l] + 1) *img.at<Vec3b>(i - 1 + k, j - 1 + l)[0];
				}
			}
			temp /= (3 * 3);//֮���Էź�������Ϊ1 / 9 ��Ӱ��
			//������������dstimg�������ص����ͨ��ֵ
			for (int m = 0; m < 3; m++) {
				img_temp.at<Vec3b>(i, j)[m] = temp;
			}
		}
	}
}
