#include <iostream>
#include <highgui.hpp>
#include <core.hpp>
#include <cstring>
#include "Rgb2Gray.h"
#include "Rgb2Gray_avr.h"
#include "Rgb2Gray_max.h"
#include "Rgb2Gray_weighting.h"
#include "Smoothing.h"
#include "Sharpening.h"
#include "Smoothing_5.h"
#include "Sharpening_5.h"
#include "Sharpening_double.h"

#include "Rgb2Gray_param.h"
#include "Sharpening_param.h"
#include "Smoothing_param.h"

#include "Smoothing_mid.h"
#include "Smoothing_Gauss_3.h"
#include "Smoothing_Gauss_5.h"

#include "Prewitt.h"
#include "Sobel.h"
#include "Roberts.h"

using namespace std;
using namespace cv;
int main(int argc, char** argv)
{
	String filename = "D:\\��ѧ��ҵ\\3 ��\\�γ̿γ�\\���ݽṹ�γ����\\Lenna.jpg";

	//ƽ��ֵ�Ҷ�ͼ
	String filename_Rgb2Gray_avr = "D:\\��ѧ��ҵ\\3 ��\\�γ̿γ�\\���ݽṹ�γ����\\Rgb2Gray_avr.jpg";
	Rgb2Gray_avr rgb2Gray_avr;//������Ķ���
	rgb2Gray_avr.img = imread(filename, IMREAD_COLOR);//�����img���Զ�ȡ��ֵ
	rgb2Gray_avr.Rgb2Gray_avr_processing();//��img������ƽ��ֵ�Ҷ�ֵ
	imwrite(filename_Rgb2Gray_avr, rgb2Gray_avr.img);//��img���� ��"D:\\��ѧ��ҵ\\3 ��\\�γ̿γ�\\���ݽṹ�γ����\\Lenna_Rgb2Gray_avr.jpg"�д���


	//���ֵ�õ��Ҷ�
	String filename_Rgb2Gray_max = "D:\\��ѧ��ҵ\\3 ��\\�γ̿γ�\\���ݽṹ�γ����\\Rgb2Gray_max.jpg";
	Rgb2Gray_max rgb2Gray_max;
	rgb2Gray_max.img = imread(filename, IMREAD_COLOR);
	rgb2Gray_max.Rgb2Gray_max_processing();
	imwrite(filename_Rgb2Gray_max, rgb2Gray_max.img);

	//��Ȩ���õ��Ҷ�
	String filename_Rgb2Gray_weighting = "D:\\��ѧ��ҵ\\3 ��\\�γ̿γ�\\���ݽṹ�γ����\\Rgb2Gray_weighting.jpg";
	Rgb2Gray_weighting rgb2Gray_weighting;
	rgb2Gray_weighting.img = imread(filename, IMREAD_COLOR);
	rgb2Gray_weighting.Rgb2Gray_weighting_processing();
	imwrite(filename_Rgb2Gray_weighting, rgb2Gray_weighting.img);

	/*
	���ǵ���Ȩ�Ҷȷ��õ��Ľ���ȽϺ� ���Ǻ�����ʹ�ü�Ȩ���õ��ĻҶ�ͼ���к���ʵ��
	*/

	/*
	ƽ��
	1/9 1/9 1/9
	1/9 1/9 1/9
	1/9 1/9 1/9
	*/
	String filename_Smoothing = "D:\\��ѧ��ҵ\\3 ��\\�γ̿γ�\\���ݽṹ�γ����\\Smoothing.jpg";
	Smoothing smoothing;
	smoothing.img = imread(filename_Rgb2Gray_weighting, IMREAD_COLOR);
	smoothing.img_temp = imread(filename_Rgb2Gray_weighting, IMREAD_COLOR);
	smoothing.Smoothing_processing();
	imwrite(filename_Smoothing, smoothing.img_temp);

	/*
	��
	-1 -1 -1
	-1  9 -1
	-1 -1 -1
	*/
	String filename_Sharpening = "D:\\��ѧ��ҵ\\3 ��\\�γ̿γ�\\���ݽṹ�γ����\\Sharpening.jpg";
	Sharpening sharpening;
	sharpening.img = imread(filename_Rgb2Gray_weighting, IMREAD_COLOR);
	sharpening.img_temp = imread(filename_Rgb2Gray_weighting, IMREAD_COLOR);
	sharpening.Sharpening_processing();
	imwrite(filename_Sharpening, sharpening.img_temp);

	/*
	ƽ�� 5
	1/25 1/25 1/25 1/25 1/25
	1/25 1/25 1/25 1/25 1/25
	1/25 1/25 1/25 1/25 1/25
	1/25 1/25 1/25 1/25 1/25
	1/25 1/25 1/25 1/25 1/25
	*/
	String filename_Smoothing_5 = "D:\\��ѧ��ҵ\\3 ��\\�γ̿γ�\\���ݽṹ�γ����\\Smoothing_5.jpg";
	Smoothing_5 smoothing_5;
	smoothing_5.img = imread(filename_Rgb2Gray_weighting, IMREAD_COLOR);
	smoothing_5.img_temp = imread(filename_Rgb2Gray_weighting, IMREAD_COLOR);
	smoothing_5.Smoothing_5_processing();
	imwrite(filename_Smoothing_5, smoothing_5.img_temp);

	/*
	�� 5 ������ 23 ʱ��Ч�����
	-1 -1 -1 -1 -1
	-1 -1 -1 -1 -1
	-1 -1 23 -1 -1
	-1 -1 -1 -1 -1
	-1 -1 -1 -1 -1
	*/
	String filename_Sharpening_5 = "D:\\��ѧ��ҵ\\3 ��\\�γ̿γ�\\���ݽṹ�γ����\\Sharpening_5.jpg";
	Sharpening_5 sharpening_5;
	sharpening_5.img = imread(filename_Rgb2Gray_weighting, IMREAD_COLOR);
	sharpening_5.img_temp = imread(filename_Rgb2Gray_weighting, IMREAD_COLOR);
	sharpening_5.Sharpening_5_processing();
	imwrite(filename_Sharpening_5, sharpening_5.img_temp);

	/*
	�� double
	-2 -2 -2
	-2 18 -2
	-2 -2 -2
	*/
	String filename_Sharpening_double = "D:\\��ѧ��ҵ\\3 ��\\�γ̿γ�\\���ݽṹ�γ����\\Sharpening_double.jpg";
	Sharpening_double sharpening_double;
	sharpening_double.img = imread(filename_Rgb2Gray_weighting, IMREAD_COLOR);
	sharpening_double.img_temp = imread(filename_Rgb2Gray_weighting, IMREAD_COLOR);
	sharpening_double.Sharpening_double_processing();
	imwrite(filename_Sharpening_double, sharpening_double.img_temp);

	//�����Ҷ� 
	String filename_Rgb2Gray_param = "D:\\��ѧ��ҵ\\3 ��\\�γ̿γ�\\���ݽṹ�γ����\\Rgb2Gray_param.jpg";
	Rgb2Gray_param rgb2Gray_param;
	rgb2Gray_param.img = imread(filename, IMREAD_COLOR);
	rgb2Gray_param.Rgb2Gray_param_processing("max");//�˴�������ѡ��avr max weight valid_input��
	imwrite(filename_Rgb2Gray_param, rgb2Gray_param.img);

	//������
	String filename_Sharpening_param = "D:\\��ѧ��ҵ\\3 ��\\�γ̿γ�\\���ݽṹ�γ����\\Sharpening_param.jpg";
	Sharpening_param sharpening_param;
	sharpening_param.img = imread(filename_Rgb2Gray_weighting, IMREAD_COLOR);
	sharpening_param.img_temp = imread(filename_Rgb2Gray_weighting, IMREAD_COLOR);
	int oper[3][3] = { { -1,-1,-1 },{ -1,9,-1 },{ -1,-1,-1 } };
	sharpening_param.Sharpening_param_processing(oper);
	imwrite(filename_Sharpening_param, sharpening_param.img_temp);

	//����ƽ��
	String filename_Smoothing_param = "D:\\��ѧ��ҵ\\3 ��\\�γ̿γ�\\���ݽṹ�γ����\\Smoothing_param.jpg";
	Smoothing_param smoothing_param;
	smoothing_param.img = imread(filename_Rgb2Gray_weighting, IMREAD_COLOR);
	smoothing_param.img_temp = imread(filename_Rgb2Gray_weighting, IMREAD_COLOR);
	smoothing_param.Smoothing_param_processing(3);
	imwrite(filename_Smoothing_param, smoothing_param.img_temp);

	//����Ϊѡ�����ִ���
	//��ֵƽ��
	String filename_Smoothing_mid = "D:\\��ѧ��ҵ\\3 ��\\�γ̿γ�\\���ݽṹ�γ����\\Smoothing_mid.jpg";
	Smoothing_mid smoothing_mid;
	smoothing_mid.img = imread(filename_Rgb2Gray_weighting, IMREAD_COLOR);
	smoothing_mid.img_temp = imread(filename_Rgb2Gray_weighting, IMREAD_COLOR);
	smoothing_mid.Smoothing_mid_processing();
	imwrite(filename_Smoothing_mid, smoothing_mid.img_temp);

	//Gauss_3ƽ��
	String filename_Smoothing_Gauss_3 = "D:\\��ѧ��ҵ\\3 ��\\�γ̿γ�\\���ݽṹ�γ����\\Smoothing_Gauss_3.jpg";
	Smoothing_Gauss_3 smoothing_Gauss_3;
	smoothing_Gauss_3.img = imread(filename_Rgb2Gray_weighting, IMREAD_COLOR);
	smoothing_Gauss_3.img_temp = imread(filename_Rgb2Gray_weighting, IMREAD_COLOR);
	smoothing_Gauss_3.Smoothing_Gauss_3_processing();
	imwrite(filename_Smoothing_Gauss_3, smoothing_Gauss_3.img_temp);

	//Gauss_5ƽ��
	String filename_Smoothing_Gauss_5 = "D:\\��ѧ��ҵ\\3 ��\\�γ̿γ�\\���ݽṹ�γ����\\Smoothing_Gauss_5.jpg";
	Smoothing_Gauss_5 smoothing_Gauss_5;
	smoothing_Gauss_5.img = imread(filename_Rgb2Gray_weighting, IMREAD_COLOR);
	smoothing_Gauss_5.img_temp = imread(filename_Rgb2Gray_weighting, IMREAD_COLOR);
	smoothing_Gauss_5.Smoothing_Gauss_5_processing();
	imwrite(filename_Smoothing_Gauss_5, smoothing_Gauss_5.img_temp);

	//prewitt
	String filename_Prewitt = "D:\\��ѧ��ҵ\\3 ��\\�γ̿γ�\\���ݽṹ�γ����\\Prewitt.jpg";
	String filename_Prewitt_x = "D:\\��ѧ��ҵ\\3 ��\\�γ̿γ�\\���ݽṹ�γ����\\Prewitt_x.jpg";
	String filename_Prewitt_y = "D:\\��ѧ��ҵ\\3 ��\\�γ̿γ�\\���ݽṹ�γ����\\Prewitt_y.jpg";
	Prewitt prewitt;
	prewitt.img = imread(filename_Rgb2Gray_weighting, IMREAD_COLOR);
	prewitt.img_temp = imread(filename_Rgb2Gray_weighting, IMREAD_COLOR);
	prewitt.img_x = imread(filename_Rgb2Gray_weighting, IMREAD_COLOR);
	prewitt.img_y = imread(filename_Rgb2Gray_weighting, IMREAD_COLOR);
	prewitt.Prewitt_processing();
	imwrite(filename_Prewitt, prewitt.img_temp);
	imwrite(filename_Prewitt_x, prewitt.img_x);
	imwrite(filename_Prewitt_y, prewitt.img_y);


	//sobel
	String filename_Sobel = "D:\\��ѧ��ҵ\\3 ��\\�γ̿γ�\\���ݽṹ�γ����\\Sobel.jpg";
	String filename_Sobel_x = "D:\\��ѧ��ҵ\\3 ��\\�γ̿γ�\\���ݽṹ�γ����\\Sobel_x.jpg";
	String filename_Sobel_y = "D:\\��ѧ��ҵ\\3 ��\\�γ̿γ�\\���ݽṹ�γ����\\Sobel_y.jpg";
	Sobel sobel;
	sobel.img = imread(filename_Rgb2Gray_weighting, IMREAD_COLOR);
	sobel.img_temp = imread(filename_Rgb2Gray_weighting, IMREAD_COLOR);
	sobel.img_x = imread(filename_Rgb2Gray_weighting, IMREAD_COLOR);
	sobel.img_y = imread(filename_Rgb2Gray_weighting, IMREAD_COLOR);
	sobel.Sobel_processing();
	imwrite(filename_Sobel, sobel.img_temp);
	imwrite(filename_Sobel_x, sobel.img_x);
	imwrite(filename_Sobel_y, sobel.img_y);

	//roberts
	String filename_Roberts = "D:\\��ѧ��ҵ\\3 ��\\�γ̿γ�\\���ݽṹ�γ����\\Roberts.jpg";
	String filename_Roberts_x = "D:\\��ѧ��ҵ\\3 ��\\�γ̿γ�\\���ݽṹ�γ����\\Roberts_x.jpg";
	String filename_Roberts_y = "D:\\��ѧ��ҵ\\3 ��\\�γ̿γ�\\���ݽṹ�γ����\\Roberts_y.jpg";
	Roberts roberts;
	roberts.img = imread(filename_Rgb2Gray_weighting, IMREAD_COLOR);
	roberts.img_temp = imread(filename_Rgb2Gray_weighting, IMREAD_COLOR);
	roberts.img_x = imread(filename_Rgb2Gray_weighting, IMREAD_COLOR);
	roberts.img_y = imread(filename_Rgb2Gray_weighting, IMREAD_COLOR);
	roberts.Roberts_processing();
	imwrite(filename_Roberts, roberts.img_temp);
	imwrite(filename_Roberts_x, roberts.img_x);
	imwrite(filename_Roberts_y, roberts.img_y);
	waitKey(10000); // Wait for a keystroke in the window
	return 0;
}
