#include <cstdio>
#include <cstdlib>
#include <cctype>
#include <cstring>

#define N 64        //最大字符数
#define M 200       //最大编码长度

int HuffmanWPL(int heap[]);                             /*模拟建立哈夫曼树，返回WPL*/
void PercolateDown(int heap[], int *Size, int parent);  /*从节点parent开始下滤*/
void BuildMinHeap(int heap[], int *Size);               /*通过传入的完全二叉树（数组）建立最小堆*/
int DeleteMin(int heap[], int *Size);                   /*删除堆中最小元素 */
void Insert(int minHeap[], int * Size, int weight);     /*向最小堆中插入权重为weight的节点*/
int CountWPL(int f[], char code[][M]);                  /*计算一种编码的WPL*/
bool IsPrefixcode(char code[][M]);                      /*判断某种编码是否为前缀码*/
bool IsPrefix(char *s1, char *s2);                      /*判断两个字符中某一个字符编码是否是另一个字符编码的前缀*/

int n;      //全局变量，字符的个数

int main()
{
    int caseNum = 0;
    while (1) {
        caseNum++;
        char ch, code[N][M];
        int frequency[N];
        scanf("%d", &n);
        if(n == 0)  // exit the input when read 0
            return 0;
        for (int i = 1; i <= n; i++) {
            while (ch = getchar()) {
                if (isalpha(ch) || isdigit(ch) || ch == '_') {    //如果是字符
                    scanf("%d", &frequency[i]);         //输出对应的访问次数
                    break;
                }
            }
        }
        int minWPL = HuffmanWPL(frequency);             //通过模拟哈夫曼树得到最小WPL

        int stusNum;                            //学生个数（编码种类）
        scanf("%d", &stusNum);
        printf("Case %d :\n", caseNum);
        for (int i = 0; i < stusNum; i++) {
            for (int j = 1; j <= n; j++) {
                while (ch = getchar()) {
                    if (isalpha(ch) || isdigit(ch) || ch == '_') {    //若为字符
                        scanf("%s", code[j]);                         //输入对应字符的编码
                        break;
                    }
                }
            }
            int thisWPL = CountWPL(frequency, code);
            if (thisWPL == minWPL && IsPrefixcode(code))    //若WPL为最小且为前缀码
                printf("Yes\n");
            else
                printf("No\n");
        }
    }
    return 0;
}

bool IsPrefixcode(char code[][M])   //TODO:考虑用字典树优化判断是否有前缀码
{
    for (int i = 1; i <= n; i++)    // O(n^2)
        for (int j = i+1; j <= n; j++)
            if (IsPrefix(code[i], code[j]))
                return false;
    return true;
}

bool IsPrefix(char *s1, char *s2)
{
    while (s1 && s2 && *s1 == *s2) {  //从编码首位向后遍历,当遍历到末端或两者不相等时退出循环
        s1++;
        s2++;
    }
    if (*s1 == '\0' || *s2 == '\0')   //若遍历到某个字符编码的末端
        return true;                  //则该字符是另一字符的前缀
    else
        return false;
}

int CountWPL(int f[], char code[][M])
{
    int WPL = 0;
    for (int i = 1; i <= n; i++)
        WPL += f[i] * strlen(code[i]);  //权重*编码长
    return WPL;
}

void PercolateDown(int heap[], int *Size, int parent)
{
    int temp = heap[parent];
    int i, child;

    for (i = parent; i*2 <= (*Size); i = child){
        child = 2 * i;
        if (child != (*Size) && heap[child+1] < heap[child])    //找到值更小的儿子
            child++;
        if (temp > heap[child])     //如果值比下一层的大
            heap[i] = heap[child];  //下滤
        else
            break;
    }
    heap[i] = temp;
}

void BuildMinHeap(int heap[], int *Size)
{
    for (int i = (*Size) / 2; i > 0; i--)   //从最后一个有儿子的节点开始
        PercolateDown(heap, Size, i);       //向前构造最小堆
}

int DeleteMin(int heap[], int *Size)
{
    int minElem = heap[1];          //最小堆根节点为最小值
    heap[1] = heap[*Size];          //将最小堆最后一个节点放到根节点处
    (*Size)--;                      //节点数减一
    PercolateDown(heap, Size, 1);   //从根节点开始下滤
    return minElem;
}

void Insert(int heap[], int * Size, int weight)
{
    int i;
    for (i = ++(*Size); i > 0 && heap[i/2] > weight; i /= 2)    //先将要插入的节点放最后
        heap[i] = heap[i/2];                                    //再上滤
    heap[i] = weight;
}

int HuffmanWPL(int heap[])
{
    int minHeap[N];
    int Size = 1;

    for (int i = 1; i < n; i++)
        minHeap[Size++] = heap[i];  //将构造最小堆的数组初始化
    minHeap[Size] = heap[n];
    BuildMinHeap(minHeap, &Size);

    int WPL = 0;
    for (int i = 1; i < n; i++) {
        int leftWeight = DeleteMin(minHeap, &Size);
        int rightWeight = DeleteMin(minHeap, &Size);
        int rootWeight = leftWeight + rightWeight;
        WPL += rootWeight;
        Insert(minHeap, &Size, rootWeight);
    }
    return WPL;
}