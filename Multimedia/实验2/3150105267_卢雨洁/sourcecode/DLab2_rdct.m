function result = my_idct(N)%一维离散余弦反变换
length = N%长度为N
my_dct = DLab2_dct(length)%通过已实现的变换函数得到DCT变换序列
reverse_dct = idct(my_dct)%利用matlab自带反变换函数验证
for i = 0:length-1%遍历计算每一项
    if i == 0%根据公示计算每一项的cu系数
        cu = sqrt(1/length);
    else
        cu = sqrt(2/length);
    end
    sum = 0;%初始各项和为0
    for j = 0:length-1
        sum = sum + my_dct(j + 1) * cos(pi * (j + 0.5) * i / length);%各项和按照公式计算
    end
    my_rdct(i + 1) = cu * sum;%记录每一项的值
end

my_rdct%显示反变换后的序列与原信号作比较
error = my_rdct - reverse_dct%输出与原信号的差异