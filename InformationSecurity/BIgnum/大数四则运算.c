//
//  main.c
//  InfoSe_hw2
//
//  Created by C-TEN on 2017/3/24.
//  Copyright © 2017年 C-TEN. All rights reserved.
//



#include <stdio.h>
#include <string.h>
#include <stdlib.h>
//#define MAXINT 1000

char a[100],b[100];
int x[100],y[100],z[100],m[100];
int digit;
void big_data_add(int a[],int b[],int c[],int lena,int lenb);
void big_data_sub(int a[],int b[],int c[],int lena,int lenb);
void big_data_multi(int a[],int b[],int c[],int lena,int lenb);
void big_data_div(int a[],int b[],int lena,int lenb);




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

int max(int a , int b){
    if(a > b)
        return a;
    else
        return b;
}

int main(int argc, const char * argv[]) {
    int j,k,len,temp;
    printf("Input A operator B \n");
    printf("for example 3672 / 213\n");
    int A[1000];
    char ch;
    int B[1000];
    char tmp;
    int i = 0;
    while((tmp = getchar()) != ' ')
        A[i++] = tmp-'0';
    int lena = i;
    ch = getchar();
    getchar();
    i = 0;
    while((tmp = getchar())>= '0' && tmp <= '9')
        B[i++] = tmp-'0';
    int lenb = i;
    //getchar();
    int result[1000];
    switch(ch){
        case'+':
        {
            big_data_add(A,B,result,lena, lenb);
            printf("The result is : \n");
            i = 0;
            int lenr = max(lena, lenb);
            while(result[i]==0&&i<lenr)
                i++;
            for(;i<=lenr;i++){
                printf("%d",result[i]);
            }
            printf("\n");
            break;
        }
        case'-':
        {
            
            int flag = 0;
            if(lena < lenb)
                flag = 1;// a < b need reverse
            else if(lena == lenb){
                i = 0;
                while(i<lena && A[i] <= B[i]){
                    if( A[i] < B[i] )
                        flag = 1;
                    i++;
                }
            }
            if(flag){
                printf("The result is : \n");
                printf("-");
                big_data_sub(B,A,result,lenb,lena);
            }
            else{
                printf("The result is : \n");
                big_data_sub(A,B,result,lena,lenb);
            }
            i = 1;
            int lenr = max(lena, lenb);
            while(result[i]==0&&i<lenr)
                i++;
            for(;i<=lenr;i++){
                printf("%d",result[i]);
            }
            printf("\n");
            break;
        }
        case'*':{
            if(lena > lenb)
                big_data_multi(A,B,result,lena,lenb);
            else
                big_data_multi(B,A,result,lenb,lena);
            break;
        }//break;
        case'/':{
            big_data_div(A,B,lena,lenb);
                        //big_data_div(A,B,result,lena,lenb);
            break;
        }
        default : break;
    }
    
    //比较大小
    //    big_data_multi(A,B,result);
    //    big_data_div(A,B,result);
    return 0;
}





void big_data_sub(int a[],int b[],int c[],int lena,int lenb){
    int lenr = max(lena, lenb);
    int tmpa = a[lena-1];
    int tmpb = b[lenb-1];
    int i;
    int carry = 0;
    int tmpc = carry;
    //int flag = 0;
    for(i = lenr; i >= 0 ; i-- )
    {
        tmpa = a[lena-1];
        if(lenb-1<0)
            tmpb = 0;
        else
            tmpb = b[lenb-1];
        tmpc = carry ;
        if(lena - 1 < 0 && lenb - 1 <0){
            c[i] = tmpc;
        }

        else if(tmpa - tmpb - tmpc < 0){
            c[i] = tmpa - tmpb - tmpc + 10;
            carry = 1;
        }
        else{
            c[i] = tmpa - tmpb - tmpc;
            // printf("c[i] %d tmpa %d tmpb %d tmpc %d\n",c[i],tmpa,tmpb,tmpc);
            carry = 0;
            //break;
        }
        lena--;
        lenb--;
    }
    
}


void big_data_add(int a[],int b[],int c[],int lena ,int lenb){
    int lenr = max(lena, lenb);
    //printf("%s\n", );
    //printf("lena %d lenb %d\n",lena,lenb );
    int tmpa = a[lena-1];
    int tmpb = b[lenb-1];
    int i;
    int carry = 0;
    int tmpc = carry;
    for(i = lenr; i >= 0 ; i-- )
    {
        tmpa = a[lena-1];
        tmpb = b[lenb-1];
        tmpc = carry;
        if(lena - 1 < 0 && lenb - 1 <0){
            c[i] = tmpc;
        }
        else if (lena-1 < 0) {
            c[i] = tmpb + tmpc;
            carry = 0;
        }
        else if (lenb - 1 < 0){
            c[i] = tmpa + tmpc;
            carry = 0;
        }
        else if(tmpa + tmpb + tmpc> 9){
            c[i] = tmpa + tmpb + tmpc - 10;
            carry = 1;
        }
        else{
            c[i] = tmpa + tmpb + tmpc;
            carry = 0;
        }
        lena--;
        lenb--;
    }
}




void big_data_multi(int a[],int b[],int c[],int lena,int lenb){
    int lenr = lena + lenb + 1;
    int tmpa = a[lena-1];
    int tmpb = b[lenb-1];
    int i;
    int rowc[lenr+1];
    for(i = 0; i <= lenr;i++){
        rowc[i] = 0;
    }
    int tmp;
    int carry = 0;
    int tmpc = carry;
    
    //calculate the first row
    int rowa[100];
    int len = lena+1;
    int j = 0;
    int ai = len-1;
    int zero;
    for(i = lenb - 1 ; i >= 0 ; i--){
        ai = len-1+lenb-1-i;
        zero = lenb-1-i;
        while(zero > 0){
            // printf("zero %d \n", zero);
            // printf("%d \n", ai);
            rowa[ai--] = 0;
            zero--;
        }
        carry = 0;
        tmpb = b[i];
        lena = len - 1;
        while(ai >= 0){
            tmpa = a[lena-1];
            tmpc = carry;
            tmp = tmpa*tmpb + tmpc;
            //printf("ai %d tmp %d\n", ai,tmp);
            if(lena - 1 < 0){
                rowa[ai] = tmpc;
                carry = 0;
            }
            else if(tmp> 9){
                while(tmp > 9){
                    j++;
                    tmp = tmp - 10;
                }
                rowa[ai] = tmp;
                carry = j;
                j = 0;
            }
            else{
                rowa[ai] = tmp;
                carry = 0;
            }
            lena--;
            ai--;
        }
        
        //if(rowa[0]==0){
        // big_data_add(rowa+1,c,c,len-1+lenb-i-1-1,len-1+lenb-i-1);
        //  printf("c[0] %d c[1] %d c[2] %d \n",c[0],c[1],c[2] );
        // }
        // else
        big_data_add(rowa,c,c,len-1+lenb-i,len-1+lenb-i);
        
    }
    int m = len-1+lenb-i;
    printf("The result is : \n");
    i = 0;
    while(c[i]==0&&i<lenr)
        i++;
    for(;i<m;i++){
        printf("%d",c[i]);
    }
    printf("\n");
    
}
//
//void big_data_div(int a[],int b[],int c[],int lena,int lenb){
//
//}




void big_data_div(int a[],int b[],int lena,int lenb){
    int i, j, k, temp, len;
    for(i=lena-1,j=0;i>=0;i--)
        x[j++]=a[i];
    for(i=lenb-1,k=0;i>=0;i--)
        y[k++]=b[i];
    if(lena<lenb)
    {
        printf("The quotient is ：0\n");
        printf("The remainder is ：");
        for(i=0;i<lena;i++)
        {
            if(a[i])
                break;
        }
        for(i = 0;i<lena;i++)
            printf("%d",a[i]);
        printf("\n");
    }
    else
    {
        len=lena-lenb;
        for(i=lena-1;i>=0;i--)
        {
            if(i>=len)
                y[i]=y[i-len];
            else
                y[i]=0;
        }
        lenb=lena;
        digit=lena;
        for(j=0;j<=len;j++)
        {
            z[len-j]=0;
            while(((temp=judge(x,y,lena,lenb))>=0)&&digit>=k)
            {
                sub(x,y,lena,lenb);
                z[len-j]++;
                lena=digit;
                if(lena<lenb&&y[lenb-1]==0)
                    lenb=lena;
            }
            if(temp<0)
            {
                for(i=1;i<lenb;i++)
                    y[i-1]=y[i];
                y[i-1]=0;
                if(lena<lenb)
                    lenb--;
            }
        }
        printf("The quotient is ：");
        for(i=len;i>0;i--)
        {
            if(z[i])
                break;
        }
        for(;i>=0;i--)
            printf("%d",z[i]);
        printf("\n");
        printf("The remainder is ：");
        for(i=lena;i>0;i--)
        {
            if(x[i])
                break;
        }
        for(;i>=0;i--)
            printf("%d",x[i]);
        printf("\n");
    }
    

    
}



//{
//    int remainder[100];
//    int ri;
//    int tmp[1];
//    tmp[0] = 1;
//    int i;
//    int j = 0;
//    int flag = 0;//a>b
//    if(lena < lenb)
//        flag = 1;// a < b need reverse
//    else if(lena == lenb){
//        i = 0;
//        while(i<lena && a[i] <= b[i]){
//            if( a[i] < b[i] )
//                flag = 1;   //a<b
//            i++;
//        }
//    }
//
//    //
//    i = 0;
//    if(flag){   //a<b
//        for(int i = 0;i<lena;i++)
//            c[i] = a[i];
//        ri = 0;
//        for(i = 0;i<lena;i++)
//            remainder[i] = a[i];
//    }
//    else{   //a>b
//        long int ten = 10;
//        int t = 0,ti = 0;
//        while(!flag){
//            //printf("lena %d lenb %d\n",lena,lenb);
//            //printf("here\n");
//            t++;
//            if(t>ten-1){
//                ten = ten * 10;
//                ti++;
//            }
//            //printf("sub lena %d lenb %d\n",lena,lenb);
//            big_data_sub(a,b,a,lena,lenb);
//            i = 0;
//            while(a[i]==0&&i<=lena)
//                i++;
//            a = a+i;
//            //printf("a[0] %d a[1] %d a[2] %d ",a[0],a[1],a[2]);
//            lena = lena - i + 1;
//            big_data_add(c,tmp,c,ti+1,1);
//            j++;
//            
//            if(lena < lenb){
//                flag = 1;// a < b need reverse
//                //printf("lena < lenb\n");
//            }
//            else if(lena == lenb){
//                //printf("less than\n");
//                //printf("a[lena] %d lena %d\n",a[lena],lena);
//                i = 0;
//                //printf("a[]")
//                int tmpi;
//                tmpi = 0;
//                while(a[i+tmpi]==0&&(i+tmpi)<lena)
//                    tmpi++;
//                while(i<lena && a[i+tmpi] <= b[i]){
//                    
//                    //printf("i %d a[i+1] : %d b[i] : %d ",tmpi,a[i+tmpi],b[i]);
//                    if( a[i+tmpi] < b[i] )
//                        flag = 1;   //a<b
//                    i++;
//                }
//                
//            }
//            for(i = 0;i<lena;i++)
//                remainder[i] = a[i];
//            
//        }
//        
//        printf("The quotient : %d\n",j);
//        printf("The remainder is : ");
//        if (lena==0) {
//            printf("0");
//        }
//        for(i = 0;i<lena;i++)
//            printf("%d",remainder[i]);
//        printf("\n");
//    }
//    
//}
//























