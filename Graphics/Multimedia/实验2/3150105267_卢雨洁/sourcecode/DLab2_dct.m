function result = my_dct(N)%һά��ɢ���ұ任������ΪN
seq = rand(1, N);%����ΪN������in
length = N;%����ΪN
seq%��ʾseq������
for i = 0:length-1  %��������cuϵ����ʹ��DCT�任�����Ϊ��������
    if i == 0   %���ݹ�ʾ����cuϵ����ֵ
        cu = sqrt(1/length);
    else
        cu = sqrt(2/length);
    end
    sum = 0;%��ʼΪ0
    for j = 0:length-1%�����ۼ�
        sum = sum + seq(j + 1) * cos(pi * (j + 0.5) * i / length);%���չ�ʽ��������
    end
    result(i + 1) = cu * sum;%����任�����е�ÿһ���ֵ
end
result%���my_dct��DCT�任�����verify_result���Ƚ�
verify_result = dct(seq)%����matlab�Դ���dct������seq�������任
