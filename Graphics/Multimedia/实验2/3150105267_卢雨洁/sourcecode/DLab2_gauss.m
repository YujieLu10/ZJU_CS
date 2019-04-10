x = (1:100) + 50 * cos((1:100) * 2 * pi / 40);%长度为100的正弦信号
n = 5 * rand(1, 100);%高斯伪随机信号噪声
plot(x, 'r')%红颜色线为原信号
hold on
x = x + n;%随机生成包含一定高斯噪声的正弦随机信号
plot(x, 'g')%绿颜色线为带噪声的信号
plot(x)
x_dct = dct(x);%DCT变换
x_dct(21:100) = 0;%DCT去噪
xx = idct(x_dct);%DCT反变换
plot(xx, 'b')%蓝颜色线为利用DCT去噪的信号
hold off