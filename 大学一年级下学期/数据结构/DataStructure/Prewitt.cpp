#include "Prewitt.h"



Prewitt::Prewitt()
{
}


Prewitt::~Prewitt()
{
}

void Prewitt::Prewitt_processing(){
	int oper_y[3][3] = { { -1,0,1 },{ -1,0,1 },{ -1,0,1 } };
	for (int i = 0; i < img.rows; i++) {//�� i = 1 �п�ʼ
		for (int j = 0; j < img.cols; j++) {//i = 1 ɨ������ j ��
			int temp = 0;//���������µ��������ص�ֵ	
			for (int k = 0; k < 3; k++) {//�����һ������ʱ�����ӿ�ʼɨ�������ų����Ե��
				for (int l = 0; l < 3; l++) {
					if ((i - 1 + k < 0) || (j - 1 + l < 0) || (i - 1 + k >= img.rows) || (j - 1 + l >= img.cols))//������λ�ڱ�Ե����ʱ,���䣬�ų�ͼ���в����ڵĵ�
						continue;
					temp += (oper_y[k][l]) * img.at<Vec3b>(i - 1 + k, j - 1 + l)[0];
				}
			}
			if (temp > 255) {
				temp = 255;
			}
			//��������С��0���Ͱ�����Ϊ0
			if (temp < 0) {
				temp = 0;
			}
			for (int m = 0; m < 3; m++) {
				img_y.at<Vec3b>(i, j)[m] = temp;
			}
		}
	}

		int oper_x[3][3] = { { 1,1,1 },{0,0,0 },{ -1,-1,-1 } };
		for (int i = 0; i < img.rows; i++) {//�� i = 1 �п�ʼ
			for (int j = 0; j < img.cols; j++) {//i = 1 ɨ������ j ��
				int temp = 0;//���������µ��������ص�ֵ	
				for (int k = 0; k < 3; k++) {//�����һ������ʱ�����ӿ�ʼɨ�������ų����Ե��
					for (int l = 0; l < 3; l++) {
						if ((i - 1 + k < 0) || (j - 1 + l < 0) || (i - 1 + k >= img.rows) || (j - 1 + l >= img.cols))//������λ�ڱ�Ե����ʱ,���䣬�ų�ͼ���в����ڵĵ�
							continue;
						temp += (oper_x[k][l]) * img.at<Vec3b>(i - 1 + k, j - 1 + l)[0];
					}
				}
				if (temp > 255) {
					temp = 255;
				}
				//��������С��0���Ͱ�����Ϊ0
				if (temp < 0) {
					temp = 0;
				}
				for (int m = 0; m < 3; m++) {
					img_x.at<Vec3b>(i, j)[m] = temp;
				}
			}
		}

		for (int i = 0; i < img.rows; i++) {//�� i = 1 �п�ʼ
			for (int j = 0; j < img.cols; j++) {//i = 1 ɨ������ j ��
				for (int m = 0; m < 3; m++) {
					img_temp.at<Vec3b>(i, j)[m] = max(img_x.at<Vec3b>(i, j)[0], img_y.at<Vec3b>(i, j)[0]);
				}
			}
		}
	}