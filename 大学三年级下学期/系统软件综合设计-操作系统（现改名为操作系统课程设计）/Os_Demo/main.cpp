#include <stdlib.h> 

#include<stdio.h>
#include<windows.h>

/*
ͷ�ļ�
*/
#include"D:/OS_EXPL/Os_Kernal/Os_Kernal/Resource_Allocation_Chart.h"

/*
���þ�̬���ӿ�
*/
#pragma comment (lib, "D:/OS_EXPL/Os_Kernal/x64/Debug/Os_Kernal.lib")

int main() {
	/*
	����
	struct Resourece_Allocation_Chart chart =
	{
	{ 1,2,3,4,5,6 } ,

	{
	{ 1,0,0,0,0,0 },
	{ 0,2,0,0,0,0 },
	{ 0,0,3,0,0,0 },
	{ 0,0,0,4,0,0 },
	{ 0,0,0,0,5,0 },
	{0,0,0,0,0,6}
	},
	{
	{ 0,1,0,0,0,0 },
	{ 0,0,1,0,0,0 },
	{ 0,0,0,1,0,0 },
	{ 0,0,0,0,1,0 },
	{ 0,0,0,0,0,1 },
	{ 1,0,0,0,0,0 }
	}
	};
	*/
	struct Resourece_Allocation_Chart chart;

	/*
	c�����н��ṹ�����ָ����Ϊ�����Ĳ���ʵ�ֶԽṹ���Ա���޸�!
	����������Ƶ�������
	1����ʼ��һ������״̬����ѡ ��Ϊ���滹��һ�ζ�ȡ�ļ��ĳ�ʼ�� ���Ը�����Σ�
	2�����ļ���Ԥ�����Դ����ͼ���ݽṹ ��ȡ���ṹ���У���ѡ ��Ϊǰ�滹��һ�ε��ú����ĳ�ʼ�� ����ȡ����Σ�
	3��������Դ->���̵ķ����
	4�����ӽ���->��Դ�������
	5��ɾ����Դ��㣨ɾ������������������ص����б���Ϣ��
	6��ɾ�����̽�㣨ɾ������������������ص����б���Ϣ��
	7��ɾ����Դ->���̵ķ����
	8��ɾ������->��Դ�������
	9������ṹ�������������Ϣ����Ϊ���飬�ں��в����У�
	10���ѽṹ���е����ݽṹ��Ϣ д�뵽�ļ���
	*/

	/*
	��ʼ������init() �ó�ʼ���������õĳ�ʼ�������һ������״̬
	*/
	init(&chart);

	/*
	���ļ��е����ݽṹ�����ṹ����
	*/
	//Chart_Read_From_File(&chart);

	/*
	������������ȫ����������ʵ�� ���ܱ�֤��ȫ��ȷ
	*/

	//Add_Edge_Resource_To_Process(&chart, 1, 1);
	
	//Delete_Resource_Node(&chart, 2);
	//Delete_Process_Node(&chart, 1);
	//printf("��ʼɾ����Դ6������6��һ����Դ");
	//Delete_Allocation_Edge(&chart, 6, 6);
	//printf("ɾ�����");
	//Delete_Allocation_Edge(&chart, 1, 1);
	//Delete_Request_Edge(&chart, 2, 3);
	//Add_Edge_Process_To_Resource(&chart, 2, 3);
	printf("��ʼ�������");
	Judge_DeadLock(&chart);
	printf("������");
	/*
	��Դ������ڵ���Դ��
	*/
	printf("����main.cpp\n");
	printf("��Դ������ڵ���Դ��\n");
	for (int i = 0; i < Resource_Node_Num; i++) {
		printf("%d ", chart.Resource_Node_Array[i]);
		if (i == Process_Node_Num - 1)
			printf("\n");
	}
	/*
	�����Դ->����ͼ
	*/
	printf("��Դ->����ͼ\n");
	for (int i = 0; i < Process_Node_Num; i++) {
		for (int j = 0; j < Resource_Node_Num; j++) {
			printf("%d ", chart.Resource_To_Process_Allocation_Edge[i][j]);
			if (j == Process_Node_Num - 1)
				printf("\n");
		}
	}
	/*
	�������->��Դͼ
	*/
	printf("����->��Դͼ\n");
	for (int i = 0; i < Resource_Node_Num; i++) {
		for (int j = 0; j < Process_Node_Num; j++) {
			printf("%d ", chart.Process_To_Resource_Request_Edge[i][j]);
			if (j == Process_Node_Num - 1)
				printf("\n");
		}
	}

	/*
	�ѽṹ���е����ݽṹд���ļ���
	*/
	Chart_Write_To_File(&chart);

	system("pause");
}