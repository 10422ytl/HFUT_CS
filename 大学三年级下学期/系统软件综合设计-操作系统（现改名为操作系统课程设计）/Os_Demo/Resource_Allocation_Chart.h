#pragma once
#ifndef RESOURCE_ALLOCATION_CHART
#define RESOURCE_ALLOCATION_CHART

#define	Resource_Node_Num 6	/*��Դ���������ֵ*/
#define Resource_Max_Num 6	/*ÿ����Դ���������Դ���������ֵ*/
#define Process_Node_Num 6	/*���̽������ֵ*/

typedef struct Resourece_Allocation_Chart
{
	int	Resource_Node_Array[Resource_Node_Num]; /*��Դ��������飬�����ڵ�ֵ�Ǹ���Դ�����Դ�������������ܳ���Resource_Max_Num*/
	int Resource_To_Process_Allocation_Edge[Resource_Max_Num][Process_Node_Num];  /*��Դ�����̵ķ���ߣ���������Դ�������ǽ���*/
	int Process_To_Resource_Request_Edge[Process_Node_Num][Resource_Node_Num];	  /*���̵���Դ�ķ���ߣ������ǽ��̣���������Դ*/

}Resourece_Allocation_Chart;

/*
ע�⣺����������д��ͷ�ļ��У����������pch.h��
��������Ҫʹ�õľ�̬���Դ�ļ���ͨ��#include����/pch.h��(����·�������·��)��
�Ϳ��Խ�����������������������������޷�ʶ�����ھ�̬����д�ĺ���
*/

#ifdef __cplusplus
extern "C"{
void init(Resourece_Allocation_Chart *chart);
void Read_From_File(Resourece_Allocation_Chart *chart);
void Add_Edge_Resource_To_Process(Resourece_Allocation_Chart *chart, int Resourece_index, int Process_index);
void Add_Edge_Process_To_Resource(Resourece_Allocation_Chart *chart, int Process_index, int Resource_index);
void Delete_Resource_Node(Resourece_Allocation_Chart*chart, int Resource_index);
void Delete_Process_Node(Resourece_Allocation_Chart*chart, int Process_index);
void Delete_Allocation_Edge(Resourece_Allocation_Chart *chart, int Resource_index, int Process_index);
void Delete_Request_Edge(Resourece_Allocation_Chart *chart, int Resource_index, int Process_index);
void Chart_Write_To_File(Resourece_Allocation_Chart *chart);
}
#endif
#endif