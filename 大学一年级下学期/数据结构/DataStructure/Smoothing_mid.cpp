#include "Smoothing_mid.h"



Smoothing_mid::Smoothing_mid()
{
}


Smoothing_mid::~Smoothing_mid()
{
}

void Smoothing_mid::Smoothing_mid_processing(){
	int oper[9];//�������ģ���Ӧ��9�����ص��ֵ
	memset(oper, 0, sizeof(oper));//��ʼ��Ϊ0
	for (int i = 0; i < img.rows; i++) {
		for (int j = 0; j < img.cols; j++) {
			int count = 0;//��ǹ��ж��ٸ��Ϸ���
			for (int k = 0; k < 3; k++) {
				for (int l = 0; l < 3; l++) {
					//������λ�ڱ�Ե����ʱ���ų�ͼ���в����ڵĵ�
					if ((i - 1 + k < 0) || (j - 1 + l < 0) || (i - 1 + k >= img.rows) || (j - 1 + l >= img.cols))
						continue;
					oper[count] = img.at<Vec3b>(i - 1 + k, j - 1 + l)[0];
					count++;
				}
			}
			//��temp�����е�����ֵ��С��������
			sort(oper, oper + count);
			//��temp�������ֵ�����������ص����ͨ��
			for (int m = 0; m < 3; m++) {
				img_temp.at<Vec3b>(i, j)[m] = oper[(count - 1) / 2];//�����λ��
			}
		}
	}
}
