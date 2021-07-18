#include "Roberts.h"



Roberts::Roberts()
{
}


Roberts::~Roberts() {
	
}

void Roberts::Roberts_processing(){
	int oper_y[2][2] = { { 0,-1 },{ 1,0 } };
	for (int i = 0; i < img.rows; i++) {//�� i = 1 �п�ʼ
		for (int j = 0; j < img.cols; j++) {//i = 1 ɨ������ j ��
			int temp = 0;//���������µ��������ص�ֵ	
			for (int k = 0; k < 2; k++) {//�����һ������ʱ�����ӿ�ʼɨ�������ų����Ե��
				for (int l = 0; l < 2; l++) {
					if ((i + k < 0) || (j + l < 0) || (i + k >= img.rows) || (j + l >= img.cols))//������λ�ڱ�Ե����ʱ,���䣬�ų�ͼ���в����ڵĵ�
						continue;
					temp += (oper_y[k][l]) * img.at<Vec3b>(i + k, j + l)[0];
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

	int oper_x[2][2] = { { -1,0 },{ 0,1 } };
	for (int i = 0; i < img.rows; i++) {//�� i = 1 �п�ʼ
		for (int j = 0; j < img.cols; j++) {//i = 1 ɨ������ j ��
			int temp = 0;//���������µ��������ص�ֵ	
			for (int k = 0; k < 2; k++) {//�����һ������ʱ�����ӿ�ʼɨ�������ų����Ե��
				for (int l = 0; l < 2; l++) {
					if ((i + k < 0) || (j + l < 0) || (i + k >= img.rows) || (j + l >= img.cols))//������λ�ڱ�Ե����ʱ,���䣬�ų�ͼ���в����ڵĵ�
						continue;
					temp += (oper_x[k][l]) * img.at<Vec3b>(i + k, j + l)[0];
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
