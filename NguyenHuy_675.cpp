#include<bits/stdc++.h>

using namespace std;
struct SinhVien
{
	int maSV;
	char hoDem[30];
	char ten[30];
	char gioiTinh[30];
	int namSinh;
	double diemTK;
};

struct Node
{
	SinhVien infor;
	Node *next;
};

typedef Node *TRO;


//Tao moi danh sach rong
void create(TRO &L)
{
	L = NULL;
}

int empty(TRO &L)
{
	return L == NULL;
}

//Nhap thong tin cho sinh vien 
SinhVien taoSV(int maSV, const char hoDem[30], const char ten[30], const char gioiTinh[30], int namSinh, double diemTK)
{
	SinhVien sv;
	sv.maSV = maSV;
	strcpy(sv.hoDem, hoDem);
	strcpy(sv.ten, ten);
	strcpy(sv.gioiTinh, gioiTinh);
	sv.namSinh = namSinh;
	sv.diemTK = diemTK;
	return sv;
}

//In thong tin sv
void display(SinhVien sv)
{
	cout<<fixed;
	cout<<setw(20)<<left<<sv.maSV;
	cout<<setw(20)<<left<<sv.hoDem;
	cout<<setw(20)<<left<<sv.ten;
	cout<<setw(20)<<left<<sv.gioiTinh;
	cout<<setw(20)<<left<<sv.namSinh;
	cout<<setw(20)<<left<<setprecision(2)<<sv.diemTK;
	cout<<endl;
}

//hien thi danh sach
void showList(TRO L)
{
	TRO Q;
	if(!empty(L))
	{
		cout<<fixed;
		cout<<setw(20)<<left<<"MaSV";
		cout<<setw(20)<<left<<"HoDem";
		cout<<setw(20)<<left<<"Ten";
		cout<<setw(20)<<left<<"GioiTinh";
		cout<<setw(20)<<left<<"NamSinh";
		cout<<setw(20)<<left<<"DiemTK";
		cout<<endl;
		
		Q = L;
		while(Q != NULL)
		{
			display(Q->infor);
			
			Q = Q->next;
		}
	}
	else
	{
		cout<<"DANH SACH RONG!!!!"<<endl;
	}
}


//chen 1 nut dau danh sach
void addFirst(TRO &L, SinhVien sv)
{
	TRO P;
	P = new Node;
	P->infor = sv;
	P->next = L;
	L = P;
}

//chen 1 nut cuoi danh sach
void addLast(TRO &L, SinhVien sv)
{
	TRO P,Q;
	P = new Node;
	P->infor = sv;
	P->next = NULL;
	
	if(empty(L))
	{
		L = P;
	}
	else
	{
		Q = L;
		while(Q->next != NULL)
			Q = Q ->next;
		Q -> next = P;
	}
}

//chen 1 nut vao vi tri k trong danh sach
//1.Chen 1 nut vao sau 1 nut tro boi Q
void addAfterQ(TRO &L, TRO Q, SinhVien sv)
{
	TRO P;
	P = new Node;
	P->infor = sv;
	P->next = Q->next;
	Q->next = P;
}
//2.Bo sung phan tu moi vao vi tri K
void addAfterK(TRO &L, int k)
{
	SinhVien sv;
	if(k==1)
	{
		addFirst(L,taoSV(2001, "Chen K  ","Hau","Nam",2003,9.5));
	}
	else
	{
		//Tim den nut k-1, Q tro den nut nay
		TRO Q = L;
		int d = 1;
		while(d<k-1)
		{
			d++;
			Q = Q->next;
		}
		addAfterQ(L,Q,taoSV(2001, "Chen K ","Hau","Nam",2003,9.5));
	}
}


//tao danh sach moi
void create_List(TRO &L)
{
	//Chen dau danh sach
	addFirst(L, taoSV(2001, "Chen dau ","Huy","Nam",2003,9.5));
	addFirst(L, taoSV(2002, "Chen dau","Linh","Nu",2003,8.5));
	//Chen cuoi danh sach
	addLast(L, taoSV(2003, "Chen cuoi","Trong","Nam",2006,7.5));
	//Chen vi tri K
	addAfterK(L, 3);
	
}

//Xoa  nut dau tien trong ds
void removeFirst(TRO &L)
{
	TRO Q;
	Q = L;
	L = L->next;
	delete Q;
	showList(L);
}

//Xoa phan tu thu k trong danh sach
//1.Xoa phan tu dung sau nut tro boi M
void removeAfterQ(TRO &L, TRO M)
{
	TRO Q;
	Q = M->next;
	M->next = Q->next;
	delete Q;
}
//2.Xoa phan tu vi tri K
void removeAfterK(TRO &L, int k)
{
	SinhVien sv;
		//Tim den nut k-1, Q tro den nut nay
		TRO Q = L;
		int d = 1;
		while(d<k-1)
		{
			d++;
			Q = Q->next;
		}
		removeAfterQ(L,Q);
		showList(L);
}
//3.Xoa sv ho ten Huy trong danh sach
//3.1 xoa ten
void afterRemove(TRO &L, TRO T)
{
	TRO Q;
	if(T==L)
		L = T->next;
	else
	{
		Q = L;
		while(Q->next != T)
			Q = Q->next;
		Q->next = T->next;
	}
	delete T;
}

void removeName(TRO &L, const char *name)
{
	TRO Q = L;
	while(Q!=NULL && strcmp(Q->infor.ten,name)!=0)
		Q = Q->next;
	TRO T = Q;
	if(T == NULL)
	{
		cout<<"khong co sv ten Linh trong danh sach!"<<endl;
		return;
	}
	afterRemove(L,T);
	showList(L);
		
}

//Tim kiem sinh vien co diem tb cao nhat
//1.tim diem cao nhat
double findMaxDiem(TRO L)
{
	
	if(L==NULL)
	{
		cout<<"DANH SACH RONG!"<<endl;
		return -1;
	}
	double max = L->infor.diemTK;
	TRO Q = L;
	while(Q !=NULL)
	{
		if(Q->infor.diemTK > max)
		{
			max = Q->infor.diemTK;
		}
		Q=Q->next;
	}
		return max;
	
}
//2.Tim sinh vien diem cao nhat hien thi danh sach
void hienThiSVDiemMax(TRO L)
{
	if(L==NULL)
	{
		cout<<"Danh sach rong!"<<endl;
		return;	
	}
	TRO Q = L;
	while(Q!=NULL)
	{
		SinhVien sv = Q->infor;
		if(sv.diemTK == findMaxDiem(L))
		{
			display(sv);
		}
		Q=Q->next;
	}
}
//3.Tim sinh vien co Ten Huy
void hienThiSVName(TRO L, const char *name)
{
	if(L==NULL)
	{
		cout<<"Danh sach rong!"<<endl;
		return;	
	}
	TRO Q = L;
	bool found = false;
	while(Q!=NULL)
	{
		SinhVien sv = Q->infor;
		if(strcmp(sv.ten,name)==0)
		{
			found = true;
			display(sv);
		}
		Q=Q->next;
	}
	
	if(!found)
		cout<<"Khong tim thay sv ten: "<<name<<endl;
}


//Sap xep sinh vien 
//1.Sap xep theo diem tk
//1.Bubble sort
void bubbleSort(TRO &L)
{
	if(L==NULL || L->next == NULL)
		return;
	bool swapped;
	TRO ptr1;
	TRO lptr = NULL;//Phan tu chua sap xep
	do{
		swapped = false;
		ptr1 = L;
		while(ptr1->next != lptr)
		{
			
			if(ptr1->infor.diemTK > ptr1->next->infor.diemTK)
			{
				SinhVien temp = ptr1->infor;
				ptr1->infor = ptr1->next->infor;
				ptr1->next->infor = temp;
				
				swapped = true;
			}
			ptr1 = ptr1->next;
		}
		lptr = ptr1;
	}while(swapped);
}

void bubbleSortName(TRO &L)
{
	if(L==NULL || L->next == NULL)
		return;
	bool swapped;
	TRO ptr1;
	TRO lptr = NULL;//Phan tu chua sap xep
	do{
		swapped = false;
		ptr1 = L;
		while(ptr1->next != lptr)
		{
			
			if(strcmp(ptr1->infor.ten, ptr1->next->infor.ten)>0)
			{
				SinhVien temp = ptr1->infor;
				ptr1->infor = ptr1->next->infor;
				ptr1->next->infor = temp;
				
				swapped = true;
			}
			ptr1 = ptr1->next;
		}
		lptr = ptr1;
	}while(swapped);
}
//2.Select sort
void selectSort(TRO &L)
{
	if(L==NULL)
	{
		cout<<"Danh sach rong!"<<endl;
		return;
	}
	TRO i = L;
	while(i != NULL)
	{
		TRO minNode = i;
		TRO j = i->next;
		while(j != NULL)
		{
			if(j->infor.diemTK < minNode->infor.diemTK)
			{
				minNode = j;
			}
			j = j->next;
		}
		
		//Hoan doi du lieu giua nut hien tai va nut co gia tri min
		if(minNode != i)
		{
			SinhVien temp = i->infor;
			i->infor = minNode->infor;
			minNode->infor = temp;
		}
		
		i = i->next;	
	}
}




int main()
{
	TRO L;
	create(L);
	create_List(L);
	cout<<"DANH SACH DA TAO:"<<endl;
	showList(L);
//	cout<<"XOA NUT DAU TIEN TRONG DANH SACH: "<<endl;
//	removeFirst(L);
//	cout<<"XOA NUT K TRONG DANH SACH: "<<endl;
//	removeAfterK(L,3);
//	cout<<"XOA SV TEN HUY TRONG DANH SACH: "<<endl;
//	removeName(L, "Trong");
//	cout<<"DIEM TB CAO NHAT LOP: "<<findMaxDiem(L)<<endl;
//	cout<<"SV CO DIEM TB CAO NHAT LOP: "<<endl;
//	hienThiSVDiemMax(L);
//	cout<<"HIEN THI DS SV TEN LINH "<<endl;
//	hienThiSVName(L, "Linh");
//	bubbleSort(L);
//	selectSort(L);
	bubbleSortName(L);
	cout<<"DANH SACH DA SAP XEP:"<<endl;
	showList(L);

		
}












