function result = my_idct(N)%һά��ɢ���ҷ��任
length = N%����ΪN
my_dct = DLab2_dct(length)%ͨ����ʵ�ֵı任�����õ�DCT�任����
reverse_dct = idct(my_dct)%����matlab�Դ����任������֤
for i = 0:length-1%��������ÿһ��
    if i == 0%���ݹ�ʾ����ÿһ���cuϵ��
        cu = sqrt(1/length);
    else
        cu = sqrt(2/length);
    end
    sum = 0;%��ʼ�����Ϊ0
    for j = 0:length-1
        sum = sum + my_dct(j + 1) * cos(pi * (j + 0.5) * i / length);%����Ͱ��չ�ʽ����
    end
    my_rdct(i + 1) = cu * sum;%��¼ÿһ���ֵ
end

my_rdct%��ʾ���任���������ԭ�ź����Ƚ�
error = my_rdct - reverse_dct%�����ԭ�źŵĲ���