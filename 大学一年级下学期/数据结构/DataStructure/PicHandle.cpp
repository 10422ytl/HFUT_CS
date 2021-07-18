#include <iostream>
#include <highgui.hpp>
#include <core.hpp>
//#include <cv.hpp>
#include <cstring>

using namespace std;
using namespace cv;

class PicHandle {
public:
	Mat img;
	Mat dstimg;// = img.clone();//�洢��
	PicHandle();//�޲ι��캯��
	~PicHandle();
	void colorToGray(Mat img);//��ɫͼ��ת��Ϊ�Ҷ�ͼ��
	void avrSmooth();//����ƽ����
	void Sharpen();//������
						  //void smooth_5();//ƽ��5x5
						  //void smooth_3();//ƽ��3x3
						  //void midSmooth(int n);//��ֵ�˲���
	
	void colorToGray_Max(Mat img);
	void colorToGray_Best(Mat img);
	void Smooth_5();
	void midSmooth();

	void Sharpen_5();
	void Sharpen_3();
	int max(int a,int b){
	(a > b) || (a = b);
	return a;
	}

	//����Ϊѡ�����ֵĺ���

	//void roberts();//roberts����

	void prewitt_x();//ˮƽprewitt
	void prewitt_y();//��ֱprewitt
};

PicHandle::PicHandle()
{
}

PicHandle::~PicHandle() 
{
}
/*
* img.at<Vec3b>(i, j)[0]�������Ƕ���ͼƬ��i��j����ĵ�0��ͨ����ֵ
*/
void PicHandle::colorToGray(Mat img) {
	for (int i = 0; i < img.rows; i++) {
		for (int j = 0; j < img.cols; j++) {
			int sum = img.at<Vec3b>(i, j)[0] + img.at<Vec3b>(i, j)[1] + img.at<Vec3b>(i, j)[2];
			sum /= 3;
			for (int k = 0; k < 3; k++) {
				img.at<Vec3b>(i, j)[k] = sum;
			}
		}
	}
}

void PicHandle::colorToGray_Max(Mat img) {
	for (int i = 0; i < img.rows; i++) {
		for (int j = 0; j < img.cols; j++) {

			
				//return maxnum;//�������ֵ�±�
			int sum = max(max(img.at<Vec3b>(i, j)[0] , img.at<Vec3b>(i, j)[1] ), img.at<Vec3b>(i, j)[2]);
			//sum /= 3;
			for (int k = 0; k < 3; k++) {
				img.at<Vec3b>(i, j)[k] = sum;
			}
		}
	}
}

void PicHandle::colorToGray_Best(Mat img) {
		for (int i = 0; i < img.rows; i++) {
			for (int j = 0; j < img.cols; j++) {
				int sum = img.at<Vec3b>(i, j)[0]*0.30 + img.at<Vec3b>(i, j)[1]*0.59 + img.at<Vec3b>(i, j)[2]*0.11;
				for (int k = 0; k < 3; k++) {
					img.at<Vec3b>(i, j)[k] = sum;
				}
			}
		}
	}

void PicHandle::avrSmooth() {
	const int n = 3;//ע��n��Ϊ����
	int x = 1;//n = 3ʱx = 1
	int oper[3][3];//ƽ������ n = 3
	memset(oper, 0, sizeof(oper));//ȫ����ʼ��Ϊ0
	for (int i = 0; i < img.rows; i++) {//�� i = 1 �п�ʼ
		for (int j = 0; j < img.cols; j++) {//i = 1 ɨ������ j �� 
			int temp = 0;//���������µ��������ص�ֵ

						 //�����һ������ʱ�����ӿ�ʼɨ�������ų����Ե��
			for (int k = 0; k < n; k++) {
				for (int l = 0; l < n; l++)
				{
					//������λ�ڱ�Ե����ʱ���ų�ͼ���в����ڵĵ�
					if ((i - x + k < 0) || (j - x + l < 0) || (i - x + k >= img.rows) || (j - x + l >= img.cols))
						//continueʹֱ��
						continue;
					//
					temp += (oper[k][l] + 1) *img.at<Vec3b>(i - x + k, j - x + l)[0];

				}
			}

			temp /= (n*n);
			//������������dstimg�������ص����ͨ��ֵ
			for (int m = 0; m < 3; m++) {
				img.at<Vec3b>(i, j)[m] = temp;
			}
		}
	}
}

void PicHandle::Sharpen() {
	for (int i = 0; i < img.rows; i++) {
		for (int j = 0; j < img.cols; j++) {
			int temp = 0;//���������µ��������ص�ֵ
			int oper[3][3] = { { -1,-1,-1 },{ -1,9,-1 },{ -1,-1,-1 } };//��
			for (int k = 0; k < 3; k++) {
				for (int l = 0; l < 3; l++) {
					//������λ�ڱ�Ե����ʱ���ų�ͼ���в����ڵĵ�
					if ((i - 1 + k < 0) || (j - 1 + l < 0) || (i - 1 + k >= img.rows) || (j - 1 + l >= img.cols))
						continue;
					temp += oper[k][l] * img.at<Vec3b>(i - 1 + k, j - 1 + l)[0];
				}
			}
			//������������255���Ͱ�����Ϊ255
			if (temp > 255) {
				temp = 255;
			}
			//��������С��0���Ͱ�����Ϊ0
			if (temp < 0) {
				temp = 0;
			}
			//�������������������ص����ͨ��ֵ
			for (int m = 0; m < 3; m++) {
				img.at<Vec3b>(i, j)[m] = temp;
			}
		}
	}
}

void PicHandle::Sharpen_5() {
	for (int i = 0; i < img.rows; i++) {
		for (int j = 0; j < img.cols; j++) {
			int temp = 0;//���������µ��������ص�ֵ
			int oper[5][5] = { { -1,-1,-1,-1,-1 },{ -1,-1,-1,-1,-1 },{ -1,-1,9,-1 ,-1},{ -1,-1,-1,-1,-1 },{ -1,-1,-1,-1,-1 } };//��
			for (int k = 0; k < 5; k++) {
				for (int l = 0; l < 5; l++) {
					//������λ�ڱ�Ե����ʱ���ų�ͼ���в����ڵĵ�
					if ((i - 2 + k < 0) || (j - 2 + l < 0) || (i - 2 + k >= img.rows) || (j - 2 + l >= img.cols))
						continue;
					temp += oper[k][l] * img.at<Vec3b>(i - 2 + k, j - 2 + l)[0];
				}
			}
			//������������255���Ͱ�����Ϊ255
			if (temp > 255) {
				temp = 255;
			}
			//��������С��0���Ͱ�����Ϊ0
			if (temp < 0) {
				temp = 0;
			}
			//�������������������ص����ͨ��ֵ
			for (int m = 0; m < 3; m++) {
				dstimg.at<Vec3b>(i, j)[m] = temp;
			}
		}
	}
}

void PicHandle::Sharpen_3(){
	for (int i = 0; i < img.rows; i++) {
		for (int j = 0; j < img.cols; j++) {
			int temp = 0;//���������µ��������ص�ֵ
			int oper[3][3] = { { -2,-2,-2 },{ -2,18,-2},{ -2 ,-2 ,-2 } };//��
			for (int k = 0; k < 3; k++) {
				for (int l = 0; l < 3; l++) {
					//������λ�ڱ�Ե����ʱ���ų�ͼ���в����ڵĵ�
					if ((i - 1 + k < 0) || (j - 1 + l < 0) || (i - 1 + k >= img.rows) || (j - 1 + l >= img.cols))
						continue;
					temp += oper[k][l] * img.at<Vec3b>(i - 1 + k, j - 1 + l)[0];
				}
			}
			//������������255���Ͱ�����Ϊ255
			if (temp > 255) {
				temp = 255;
			}
			//��������С��0���Ͱ�����Ϊ0
			if (temp < 0) {
				temp = 0;
			}
			//�������������������ص����ͨ��ֵ
			for (int m = 0; m < 3; m++) {
				dstimg.at<Vec3b>(i, j)[m] = temp;
			}
		}
	}
}

void PicHandle::Smooth_5() {
	const int n = 5;//ע��n��Ϊ����
	int x = 2;//n = 3ʱx = 1

	int oper[5][5];//ƽ������ n = 3
	memset(oper, 0, sizeof(oper));//ȫ����ʼ��Ϊ0
	for (int i = 0; i < img.rows; i++) {//�� i = 1 �п�ʼ
		for (int j = 0; j < img.cols; j++) {//i = 1 ɨ������ j �� 
			int temp = 0;//���������µ��������ص�ֵ

						 //�����һ������ʱ�����ӿ�ʼɨ�������ų����Ե��
			for (int k = 0; k < n; k++) {
				for (int l = 0; l < n; l++)
				{
					//������λ�ڱ�Ե����ʱ���ų�ͼ���в����ڵĵ�
					if ((i - x + k < 0) || (j - x + l < 0) || (i - x + k >= img.rows) || (j - x + l >= img.cols))
						//continueʹֱ��
						continue;
					//
					temp += (oper[k][l] + 1) *img.at<Vec3b>(i - x + k, j - x + l)[0];
				}
			}

			temp /= (n*n);
			//�������������������ص����ͨ��ֵ
			for (int m = 0; m < 3; m++) {
				dstimg.at<Vec3b>(i, j)[m] = temp;
			}
		}
	}
}

void PicHandle::midSmooth(){ 
int n = 3;
int oper[9];//�������ģ���Ӧ��9�����ص��ֵ
	memset(oper, 0, sizeof(oper));//��ʼ��Ϊ0
	int x = 1;
	for (int i = 0; i < img.rows; i++) {
		for (int j = 0; j < img.cols; j++) {
			int count = 0;//��ǹ��ж��ٸ��Ϸ���
			for (int k = 0; k < n; k++) {
				for (int l = 0; l < n; l++) {
					//������λ�ڱ�Ե����ʱ���ų�ͼ���в����ڵĵ�
					if ((i - x + k < 0) || (j - x + l < 0) || (i - x + k >= img.rows) || (j - x + l >= img.cols))
						continue;
					oper[count] = img.at<Vec3b>(i - x + k, j - x + l)[0];
					count++;
				}
			}
			//��temp�����е�����ֵ��С��������
			for (int p = 0; p < count; p++) {
				for (int q = count - 1; q > p; q--) {
					if (oper[q] < oper[q - 1]) {
						swap(oper[q], oper[q - 1]);
					}
				}
			}

			//��temp�������ֵ�����������ص����ͨ��
			for (int m = 0; m < 3; m++) {
				dstimg.at<Vec3b>(i, j)[m] = oper[count / 2 + 1];
			}
		}
	}
}

void PicHandle::prewitt_x() {
	for (int i = 0; i < img.rows; i++) {
		for (int j = 0; j < img.cols; j++) {
			int temp = 0;//���������µ��������ص�ֵ
			int oper[3][3] = { { -1,0,1 },{ -1,0,1 },{ -1,0,1 } };//prewitt_x
			for (int k = 0; k < 3; k++) {
				for (int l = 0; l < 3; l++) {
					//������λ�ڱ�Ե����ʱ���ų�ͼ���в����ڵĵ�
					if ((i - 1 + k < 0) || (j - 1 + l < 0) || (i - 1 + k >= img.rows) || (j - 1 + l >= img.cols))
						continue;
					temp += oper[k][l] * img.at<Vec3b>(i - 1 + k, j - 1 + l)[0];
				}
			}
			//������������255���Ͱ�����Ϊ255
			if (temp > 255) {
				temp = 255;
			}
			//��������С��0���Ͱ�����Ϊ0
			if (temp < 0) {
				temp = 0;
			}
			//�������������������ص����ͨ��ֵ
			for (int m = 0; m < 3; m++) {
				dstimg.at<Vec3b>(i, j)[m] = temp;
			}
		}
	}
}

void PicHandle::prewitt_y() {
	for (int i = 0; i < img.rows; i++) {
		for (int j = 0; j < img.cols; j++) {
			int temp = 0;//���������µ��������ص�ֵ
			int oper[3][3] = { { -1,-1,-1 },{ 0,0,0 },{ 1,1,1} };//
			for (int k = 0; k < 3; k++) {
				for (int l = 0; l < 3; l++) {
					//������λ�ڱ�Ե����ʱ���ų�ͼ���в����ڵĵ�
					if ((i - 1 + k < 0) || (j - 1 + l < 0) || (i - 1 + k >= img.rows) || (j - 1 + l >= img.cols))
						continue;
					temp += oper[k][l] * img.at<Vec3b>(i - 1 + k, j - 1 + l)[0];
				}
			}
			//������������255���Ͱ�����Ϊ255
			if (temp > 255) {
				temp = 255;
			}
			//��������С��0���Ͱ�����Ϊ0
			if (temp < 0) {
				temp = 0;
			}
			//�������������������ص����ͨ��ֵ
			for (int m = 0; m < 3; m++) {
				dstimg.at<Vec3b>(i, j)[m] = temp;
			}
		}
	}
}

/*void PicHandle::roberts()
{
	//cv::Mat dstImage = srcImage.clone();
	//int nRows = dstImage.rows;
	//int nCols = dstImage.cols;
	for (int i = 0; i < img.rows; i++)
	{
		for (int j = 0; j < img.cols; j++)
		{
			int t1 = (img.at<Vec3b>(i, j) - img.at<Vec3b>(i + 1, j + 1)) * (img.at<Vec3b>(i, j) - img.at<Vec3b>(i + 1, j + 1));
			int t2 = (img.at<Vec3b>(i + 1, j) - img.at<Vec3b>(i, j + 1)) * (img.at<Vec3b>(i + 1, j) - img.at<Vec3b>(i, j + 1));
			int temp = sqrt(t1 + t2);
			for (int m = 0; m < 3; m++) {
				img.at<Vec3b>(i, j)[m] = temp;
			}
		}
	}
	//return dstImage;
}*/

int main(int argc, char** argv)
{
	String filename = "D:\\��ѧ��ҵ\\3 ��\\�γ̿γ�\\���ݽṹ�γ����\\ģ��.jpg";
	String filename_Gray = "D:\\��ѧ��ҵ\\3 ��\\�γ̿γ�\\���ݽṹ�γ����\\ģ��_gray.jpg";
	String filename_Smooth = "D:\\��ѧ��ҵ\\3 ��\\�γ̿γ�\\���ݽṹ�γ����\\ģ��_smooth.jpg";
	String filename_Sharpen = "D:\\��ѧ��ҵ\\3 ��\\�γ̿γ�\\���ݽṹ�γ����\\ģ��_sharpen.jpg";

	String filename_Gray_Max = "D:\\��ѧ��ҵ\\3 ��\\�γ̿γ�\\���ݽṹ�γ����\\ģ��_gray_Max.jpg";
	String filename_Gray_Best = "D:\\��ѧ��ҵ\\3 ��\\�γ̿γ�\\���ݽṹ�γ����\\ģ��_gray_Best.jpg";

	String filename_smooth_5 = "D:\\��ѧ��ҵ\\3 ��\\�γ̿γ�\\���ݽṹ�γ����\\ģ��_smooth_5.jpg";
	String filename_midSmooth = "D:\\��ѧ��ҵ\\3 ��\\�γ̿γ�\\���ݽṹ�γ����\\ģ��_smooth_mid.jpg";

	String filename_Sharpen_5 = "D:\\��ѧ��ҵ\\3 ��\\�γ̿γ�\\���ݽṹ�γ����\\ģ��_sharpen_5.jpg";
	String filename_Sharpen_3 = "D:\\��ѧ��ҵ\\3 ��\\�γ̿γ�\\���ݽṹ�γ����\\ģ��_sharpen_3.jpg";


	String filename_prewitt_x = "D:\\��ѧ��ҵ\\3 ��\\�γ̿γ�\\���ݽṹ�γ����\\ģ��_prewitt_x.jpg";
	String filename_prewitt_y = "D:\\��ѧ��ҵ\\3 ��\\�γ̿γ�\\���ݽṹ�γ����\\ģ��_prewitt_y.jpg";
	//�Ҷ�
	PicHandle Gray;
	Gray.img = imread(filename, IMREAD_COLOR); // Read the file
	Gray.colorToGray(Gray.img);
	imwrite(filename_Gray, Gray.img);//

	//ƽ��
	PicHandle Smooth;
	Smooth.img =  imread(filename, IMREAD_COLOR); // Read the file
	Smooth.colorToGray(Smooth.img);
	Smooth.colorToGray(Smooth.dstimg);
	Smooth.avrSmooth();
	imwrite(filename_Smooth, Smooth.dstimg);//
	

	//��
	PicHandle Sharpen;
	Sharpen.img = imread(filename, IMREAD_COLOR); // Read the file
	Sharpen.colorToGray(Sharpen.img);//�Ҷ�
	Sharpen.Sharpen();//�񻯲���
	imwrite(filename_Sharpen, Sharpen.dstimg);//

	//���ֵ�Ҷ�
	PicHandle Gray_Max;
	Gray_Max.img = imread(filename, IMREAD_COLOR); // Read the file
	Gray_Max.colorToGray_Max(Gray_Max.img);
	imwrite(filename_Gray_Max, Gray_Max.dstimg);//

    //��Ȩƽ���Ҷ�
	PicHandle Gray_Best;
	Gray_Best.img = imread(filename, IMREAD_COLOR); // Read the file
	Gray_Best.colorToGray_Best(Gray_Best.img);
	imwrite(filename_Gray_Best, Gray_Best.dstimg);//

	//5*5ƽ��
	PicHandle smooth_5;
	smooth_5.img = imread(filename, IMREAD_COLOR); // Read the file
	smooth_5.colorToGray(smooth_5.img);//�Ҷ�
	smooth_5.Smooth_5();//5*5ƽ��
	imwrite(filename_smooth_5, smooth_5.dstimg);//
	
	//��ֵƽ��
	PicHandle midSmooth;
	midSmooth.img = imread(filename, IMREAD_COLOR); // Read the file
	midSmooth.colorToGray(midSmooth.img);//�Ҷ�
	midSmooth.midSmooth();//5*5ƽ��
	imwrite(filename_midSmooth, midSmooth.dstimg);//

    //5*5��
	PicHandle Sharpen_5;
	Sharpen_5.img = imread(filename, IMREAD_COLOR); // Read the file
	Sharpen_5.colorToGray(Sharpen_5.img);//�Ҷ�
	Sharpen_5.Sharpen_5();//�񻯲���
	imwrite(filename_Sharpen_5, Sharpen_5.dstimg);//

	//�������ڲ�ֵ�ӱ����3*3��
	PicHandle Sharpen_3;
	Sharpen_3.img = imread(filename, IMREAD_COLOR); // Read the file
	Sharpen_3.colorToGray(Sharpen_3.img);//�Ҷ�
	Sharpen_3.Sharpen_3();//�񻯲���
	imwrite(filename_Sharpen_3, Sharpen_3.dstimg);//

	//����Ϊѡ��������ش���

	//������˹����ģ��
	
	PicHandle prewitt_x;
	prewitt_x.img = imread(filename, IMREAD_COLOR); // Read the file
	prewitt_x.colorToGray(prewitt_x.img);//�Ҷ�
	prewitt_x.prewitt_x();//�񻯲���
	imwrite(filename_prewitt_x, prewitt_x.dstimg);//

	//��ɢ������˹����ģ��
	
	PicHandle prewitt_y;
	prewitt_y.img = imread(filename, IMREAD_COLOR); // Read the file
	prewitt_y.colorToGray(prewitt_y.img);//�Ҷ�
	prewitt_y.prewitt_y();//�񻯲���
	imwrite(filename_prewitt_y, prewitt_y.dstimg);//
	
	waitKey(10000); // Wait for a keystroke in the window
	return 0;
}


	