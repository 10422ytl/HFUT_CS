#include "Sharpening_5.h"



Sharpening_5::Sharpening_5()
{
}


Sharpening_5::~Sharpening_5()
{
}

void Sharpening_5::Sharpening_5_processing(){
	//����n = 3 ���� x = 1
	int oper[5][5] = { { -1,-1,-1,-1,-1 },{ -1,-1,-1,-1,-1 },{ -1,-1,21,-1,-1 },{ -1,-1,-1,-1,-1 },{ -1,-1,-1,-1,-1 } };
	for (int i = 0; i < img.rows; i++) {//�� i = 1 �п�ʼ
		for (int j = 0; j < img.cols; j++) {//i = 1 ɨ������ j ��
			int temp = 0;//���������µ��������ص�ֵ	
			for (int k = 0; k < 5; k++) {//�����һ������ʱ�����ӿ�ʼɨ�������ų����Ե��
				for (int l = 0; l < 5; l++) {
					if ((i - 2 + k < 0) || (j - 2 + l < 0) || (i - 2 + k >= img.rows) || (j - 2 + l >= img.cols))//������λ�ڱ�Ե����ʱ,���䣬�ų�ͼ���в����ڵĵ�
						continue;
					temp += (oper[k][l]) * img.at<Vec3b>(i - 2 + k, j - 2 + l)[0];
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
				img_temp.at<Vec3b>(i, j)[m] = temp;
			}
		}
	}
}
