#pragma once
#include <iostream>
#include <highgui.hpp>
#include <core.hpp>
#include <cstring>

using namespace std;
using namespace cv;
class Rgb2Gray
{
public:
	//����Ƶ������Ŀ�� ��Ҫ��Ϊ�����ṩһ��Mat img
	Mat img;
	Rgb2Gray();
	virtual ~Rgb2Gray();
};

