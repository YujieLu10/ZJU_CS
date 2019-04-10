#include<stdio.h>
#include <stdlib.h>
#include<string.h>
#include<time.h>

#define MAX 1000
char a[1000],b[1000];
int x[1000],y[1000],z[1000],m[1000];
int ay[1000];
int ax[1000];
int digit;
int pro[1000000];
int num[1000];
int an[1000];
int aa[1000];
int ab[1000];
int at[1000];
int DHpow(int array[], int len , int n);
void DHmod(int len1, int len2, int a[], int b[]);
int judge(int x[],int y[],int len1,int len2)
{
    int i;
    if(len1<len2)
        return -1;
    else if(len1==len2)
    {
        for(i=len1-1;i>=0;i--)
        {
            if(x[i]==y[i])
                continue;
            if(x[i]>y[i])
                return 1;
            if(x[i]<y[i])
                return -1;
        }
        return 0;
    }
    else
        return -1;
}


void sub(int x[],int y[],int len1,int len2)
{
    int i;
    for(i=0;i<len1;i++)
    {
        if(x[i]<y[i])
        {
            x[i]=x[i]+10-y[i];
            x[i+1]--;
        }
        else
            x[i]=x[i]-y[i];
    }
    for(i=len1-1;i>=0;i--)
    {
        if(x[i])
        {
            digit=i+1;
            break;
        }
    }
}

void converse(int n,int cnt,int aa[]){
    int i = 0;
    int a = n;
    while(a) {
        aa[cnt-1-i] = a % 10 ; //long -> char
        a = a / 10;

        i++;
    }


}
void mul(int num[],int pro[],int len)
{
    int s[MAX*MAX]={0};
    int i,j;
    for(i=0;i<len;i++)
    {
        for(j=0;j<MAX*MAX;j++)
            s[i+j]=s[i+j]+num[i]*pro[j];
    }
    for(i=0;i<MAX*MAX;i++)
    {
        if(s[i]>=10)
            s[i+1]=s[i+1]+s[i]/10;
        pro[i]=s[i]%10;
    }
}
int main()
{
    int tn,a,b,t,i;
    int acnt = 0,bcnt = 0,tcnt = 0,ncnt = 0;
    int tmp;
    unsigned long x,y,k,l;
    printf("输入素数p的值：");
    scanf("%d",&tn);
    srand(time(0));
    a = (rand()%(tn-2)) + 2;
    

    printf("随机数n的值 ： %d\n",tn);
    tmp = tn;
    while(tmp){
        tmp = tmp/10;
        ncnt++;
    }
    converse(tn,ncnt,an);


    printf("随机数a的值 ： %d\n",a);
    tmp = a;
    while(tmp){
        tmp = tmp/10;
        acnt++;
    }
    converse(a,acnt,aa);

    b = (rand()%(tn-2)) + 2;
    printf("随机数b的值 ： %d\n",b);
    tmp = b;
    while(tmp){
        tmp = tmp/10;
        bcnt++;
    }
    converse(b,bcnt,ab);

    t = (rand()%(tn-2)) + 2;
    printf("原根t的值 ： %d\n",t);
    tmp = t;
    while(tmp){
        tmp = tmp/10;
        tcnt++;
    }
    converse(t,tcnt,at);

    int xcnt;
    int ycnt;
    int kcnt;
    int lcnt;
    
    int j;
    xcnt =  DHpow(at, tcnt, a);
    i = xcnt;
    for(j = 0;i>=0;i--){
        ax[j++] = pro[i];
        //printf("%d",pro[i]);
    }
   // printf("\n");
    printf("计算YA=a^XA mod p 发送给B:\n");
    DHmod(xcnt+1,ncnt,pro,an);
    
    
    ycnt = DHpow(at, tcnt, b);
    i = ycnt;
    for(j = 0;i>=0;i--){
        ay[j++] = pro[i];
       // printf("%d",pro[i]);
    }
   // printf("\n");
    printf("计算YB=a^XB mod p 发送给A:\n");
    DHmod(ycnt+1,ncnt,pro,an);
    

    


    
    
    kcnt = DHpow(ay, ycnt, a);
    i = kcnt;
//    for(;i>=0;i--){
//        //ay[i] = pro[i];
//        printf("%d",pro[i]);
//    }
//    printf("\n");
    printf("A计算密钥的方式是：K=(YB) ^XA modp:\n");
    DHmod(kcnt+1,ncnt,pro,an);
//
    
    lcnt = DHpow(ax, xcnt, b);
    i = lcnt;
//    for(;i>=0;i--){
//        //ay[i] = pro[i];
//        printf("%d",pro[i]);
//    }
//    printf("\n");

    printf("B计算密钥的方式是：K=(YA) ^XB modp:\n");
    DHmod(lcnt+1,ncnt,pro,an);

    return 0;
}



void DHmod(int len1,int len2,int a[],int b[])
{
    int i,j=0,k=0,temp;
    int len;
        for(i=len1-1,j=len1-1;i>=0;i--)
            x[j--]=a[i];
        for(i=len2-1,k=0;i>=0;i--)
            y[k++]=b[i];
        if(len1<len2)
        {
                    //printf("The quotient is ：0\n");
                    //printf("The remainder is ：");
                    for(i=0;i<len1;i++)
                    {
                        if(a[i])
                            break;
                    }
                    for(i = 0;i<len1;i++)
                        printf("%d",a[i]);
                    printf("\n");
        }
        else
        {
            len=len1-len2;
            for(i=len1-1;i>=0;i--)
            {
                if(i>=len)
                    y[i]=y[i-len];
                else
                    y[i]=0;
            }
            len2=len1;
            digit=len1;
            for(j=0;j<=len;j++)
            {
                z[len-j]=0;
                while(((temp=judge(x,y,len1,len2))>=0)&&digit>=k)
                {
                    sub(x,y,len1,len2);
                    z[len-j]++;
                    len1=digit;
                    if(len1<len2&&y[len2-1]==0)
                        len2=len1;
                }
                if(temp<0)
                {
                    for(i=1;i<len2;i++)
                        y[i-1]=y[i];
                    y[i-1]=0;
                    if(len1<len2)
                        len2--;
                }
            }
//            printf("商是：");
//            for(i=len;i>0;i--)//去掉前缀0
//            {
//                if(z[i])
//                    break;
//            }
//            for(;i>=0;i--)
//                printf("%d",z[i]);
//            printf("\n");
            //printf("余数是：");
            for(i=len1;i>0;i--)
            {
                if(x[i])
                    break;
            }
            for(;i>=0;i--)
                printf("%d",x[i]);
            printf("\n");
        }

}


int DHpow(int array[],int len , int n){
    int j,k,i;

    for(j=0,k=0,i=len-1;i>=0;i--)
    {
        num[j++]=array[i];
        pro[k++]=array[i];
    }
    n--;
    while(n)
    {
        mul(num,pro,len);
        n--;
    }
    for(i=MAX*MAX-1;i>0;i--)
        if(pro[i])
            break;

    return i;
    
}
