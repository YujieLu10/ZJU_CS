function result = my_dct(N)%一维离散余弦变换，长度为N
seq = rand(1, N);%长度为N的序列in
length = N;%长度为N
seq%显示seq的内容
for i = 0:length-1  %遍历计算cu系数，使得DCT变换矩阵成为正交矩阵
    if i == 0   %根据公示设置cu系数的值
        cu = sqrt(1/length);
    else
        cu = sqrt(2/length);
    end
    sum = 0;%初始为0
    for j = 0:length-1%进行累加
        sum = sum + seq(j + 1) * cos(pi * (j + 0.5) * i / length);%按照公式计算各项和
    end
    result(i + 1) = cu * sum;%计算变换后序列的每一项的值
end
result%输出my_dct的DCT变换结果与verify_result作比较
verify_result = dct(seq)%利用matlab自带的dct函数对seq序列作变换
