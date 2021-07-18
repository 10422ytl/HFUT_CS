#include "Smoothing_Gauss_3.h"



Smoothing_Gauss_3::Smoothing_Gauss_3()
{
}


Smoothing_Gauss_3::~Smoothing_Gauss_3()
{
}

void Smoothing_Gauss_3::Smoothing_Gauss_3_processing(){
	//����n = 3 ���� x = 1
	int oper[3][3] = { { 1,2,1 },{ 2,4,2 },{ 1,2,1 } };
	for (int i = 0; i < img.rows; i++) {//�� i = 1 �п�ʼ
		for (int j = 0; j < img.cols; j++) {//i = 1 ɨ������ j ��
			int temp = 0;//���������µ��������ص�ֵ	
			for (int k = 0; k < 3; k++) {//�����һ������ʱ�����ӿ�ʼɨ�������ų����Ե��
				for (int l = 0; l < 3; l++) {
					if ((i - 1 + k < 0) || (j - 1 + l < 0) || (i - 1 + k >= img.rows) || (j - 1 + l >= img.cols))//������λ�ڱ�Ե����ʱ,���䣬�ų�ͼ���в����ڵĵ�
						continue;
					temp += (oper[k][l]) * img.at<Vec3b>(i - 1 + k, j - 1 + l)[0];
				}
			}
			temp = temp / 16;
			if (temp > 255) {
				temp = 255;
			}
			//��������С��0���Ͱ�����Ϊ0
			if (temp < 0) {
				temp = 0;
			}
			for (int m = 0; m < 3; m++) {
				img_temp.at<Vec3b>(i, j)[m] = temp;
			}
		}
	}
}
