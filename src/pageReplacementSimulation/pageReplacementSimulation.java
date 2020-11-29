package pageReplacementSimulation;

import java.util.*;

public class pageReplacementSimulation
{
	//是否查看详细页面置换过程的标志位 
    public static int BiaoZhiWei;
    
    //页面大小,每个页面可包含ZhiLingNumPerPage条指令
    public static int ZhiLingNumPerPage;

    //存放该程序依次执行的指令的有序地址序列
    public static int[] ZhiLingSequence = null;

    //存放将有序指令地址序列转换成(经过合并相邻页号)的有序页号序列
    public static int[] PagesSequence = null;

    //指定分配给该程序的页框数
    public static int YeKuangNum;


    public static void main(String[] args)
    {

        int count = 1;
        Scanner scan = new Scanner(System.in);

        System.out.println("\t\t——————————————————————欢迎使用页面置换模拟系统！——————————————————————————\n");

        while (true)
        {
            System.out.println("—— —— —— ——第 " + count + " 个程序的页面置换模拟过程—— —— —— ——\n");
            System.out.println("进入系统请输入1，退出系统请输入-1");

            int caoZuoShu = scan.nextInt();

            if(caoZuoShu == -1) break;  //输入为-1 跳出循环程序

            int zhiLingNum = 400;  //指令数量

            ZhiLingSequence = getZhiLingSequence(zhiLingNum);
            System.out.println("系统随机生成的400条指令地址序列如下：");
            showZhiLingSequence(ZhiLingSequence); //输出随机生成的指令
            System.out.println();

            //每1k存放10条指令
            ZhiLingNumPerPage = 10;
            PagesSequence = zhiLingToPagesSequence(ZhiLingSequence, ZhiLingNumPerPage); //指令分页函数
            System.out.println("该指令地址序列对应的页号序列（已经过相邻页号合并）如下：");
            showPagesSequence(PagesSequence); //输出全部页号
            System.out.println();
            System.out.println("实际总共使用到的页号个数为：" + PagesSequence.length);
            System.out.println();

            YeKuangNum = 4;		//页框数分配从4开始
            while(YeKuangNum <41)		//页框数分配到40结束
            {
            	System.out.println("现在页框数位："+YeKuangNum);
            	 System.out.println("执行FIFO算法");
                 FIFO(PagesSequence, YeKuangNum);	//调用FIFO
                 System.out.println("***************************************************************");
                 System.out.println("执行LRU算法");
                 LRU(PagesSequence, YeKuangNum);	//调用LRU
                 System.out.println("***************************************************************");
                 System.out.println("执行OPT算法");
                 OPT(PagesSequence, YeKuangNum);	//调用OPT
                 System.out.println("***************************************************************");
                 YeKuangNum+=1;
                 System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
           
            } 
            //展示页面置换算法详细过程
            System.out.println("输入1查询详细的页面置换过程，输入其他数字则退出！");
            BiaoZhiWei = scan.nextInt();
            if(BiaoZhiWei==1) {
           	 	System.out.println("请输入此次页面置换要分配的页框数量（4~40）：");
            	YeKuangNum= scan.nextInt();
            	System.out.println("请输入需要模拟的页面置换算法标号：（请输入1 / 2 / 3    1：FIFO， 2：LRU， 3：OPT)");
                int flag = scan.nextInt();
                switch (flag)
                {
                    case 1:
                        FIFO(PagesSequence, YeKuangNum);
                        break;
                    case 2:
                        LRU(PagesSequence, YeKuangNum);
                        break;
                    case 3:
                        OPT(PagesSequence, YeKuangNum);
                        break;
                    default:
                        System.out.println("您的输入有误！");
                }
            	BiaoZhiWei=0; //重新置零 以免第二次循环发生死循环
            }
            else
            	System.out.println("您已选择不看详细页面置换过程！");
            
            System.out.println("\n\n");
            count++;
        }
        System.out.println("\n~~~~~~~~~~您已成功退出系统！~~~~~~~~~~");
    }

    
    // 随机 生成400地址指令的函数 
    public static int[] getZhiLingSequence(int zhiLingNum)
    {
        int[] zhiLingSequence = new int[zhiLingNum];	//定义同等大小的数组

        int count = 0;

        while (count < zhiLingNum)
        {
        	//生成随机指令地址
        	int randomAddress1 = 0 + (int) (Math.random() * 199);   //0 最大到199
        	zhiLingSequence[count] = randomAddress1;	
            randomAddress1++;	
            zhiLingSequence[++count] = randomAddress1;	

            int randomAddress2 = 200 + (int) (Math.random() * 199);  //200~399
            zhiLingSequence[++count] = randomAddress2; 
            randomAddress2++;
            zhiLingSequence[++count] = randomAddress2;	
            
            count++;
        }
        return zhiLingSequence; //生成的地址指令队列
    }

    //打印全部400条地址指令的函数
    public static void showZhiLingSequence(int[] zhiLingSequence)
    {
        for (int i = 0; i < zhiLingSequence.length; i++)
        {
            System.out.printf("%5s", zhiLingSequence[i]);

            if ((i + 1) % 20 == 0)
            {
                System.out.println();
            }
        }

        System.out.println();
    }

    //把指令地址队列变成页号队列
    public static int[] zhiLingToPagesSequence(int[] zhiLingSequence, int zhiLingNumPerPage)
    {
        ArrayList<Integer> pagesList = new ArrayList<Integer>();

        int temp = -1;
        //页号
        int pageIndex;

        for (int i = 0; i < zhiLingSequence.length; i++)
        {
            pageIndex = zhiLingSequence[i] / zhiLingNumPerPage;

            //将相邻的页号合并
            if (pageIndex != temp)
            {
                pagesList.add(pageIndex);
                temp = pageIndex;
            }
        }

        //有序页号序列经合并之后长度最长不超过指令的有序地址序列长度
        int[] pagesSequence = new int[pagesList.size()];

        for (int i = 0; i < pagesList.size(); i++)
        {
            pagesSequence[i] = pagesList.get(i);
        }

        return pagesSequence;
    }

//    打印所有页号队列的函数
    public static void showPagesSequence(int[] pagesSequence)
    {
        for (int i = 0; i < pagesSequence.length; i++)
        {
            System.out.printf("%5s", pagesSequence[i]);

            if ((i + 1) % 20 == 0)
            {
                System.out.println();
            }
        }

        System.out.println();
    }


    public static void FIFO(int[] pagesSequence, int yeKuangNum)
    {
        //执行页号序列期间内存块的状态
        int[][] yeKuangState = new int[pagesSequence.length][yeKuangNum];

        //该指针指向将要被置换的内存块的位置（下标位置）
        int cur = 0;

        //执行每个页号时内存块序列的状态
        int[] tempState = new int[yeKuangNum];

        //记录缺页情况， 1表示缺页，0表示不缺页
        int[] isLackOfPage = new int[pagesSequence.length];
        Arrays.fill(isLackOfPage, 0, pagesSequence.length, 0);

        //缺页次数
        int lackTimes = 0;

        //开始时，内存块状态都为空闲（-1表示）
        Arrays.fill(tempState, 0, yeKuangNum, -1);

        for (int i = 0; i < pagesSequence.length; i++)
        {
            //如果缺页
            if (findKey(tempState, 0, yeKuangNum - 1, pagesSequence[i]) == -1)
            {
                isLackOfPage[i] = 1;
                lackTimes++;
                tempState[cur] = pagesSequence[i];

                //指针向右移动超过memoryBlocksNum时，重置其指向开始的内存块位置0
                if (cur + 1 > yeKuangNum - 1)
                {
                    cur = 0;
                }
                else
                {
                    cur++;
                }
            }
            
            //保存当前内存块序列的状态
            System.arraycopy(tempState, 0, yeKuangState[i], 0, yeKuangNum);
        }
        if (BiaoZhiWei==1) {
        	showYeKuangState(yeKuangState, pagesSequence, yeKuangNum, isLackOfPage, lackTimes);
        }
        
        //命中率
        double lackOfPagesRate = lackTimes * 1.0 / pagesSequence.length;

        System.out.println("\n该程序的页号序列长度为：" + pagesSequence.length + ", 执行该算法后，缺页次数为：" + lackTimes + ", 命中率为：" + (1-lackOfPagesRate) * 100 + "%"+", 缺页率为：" + lackOfPagesRate * 100 + "%");

    }

    public static void LRU(int[] pagesSequence, int yeKuangNum)
    {
        //维护一个最近使用的内存块集合
        LRULinkedHashMap<String, Integer> recentVisitedBlocks = new LRULinkedHashMap<String, Integer>(yeKuangNum);

        //执行页号序列期间内存块的状态
        int[][] yeKuangState = new int[pagesSequence.length][yeKuangNum];

        //该指针指向将要被置换的内存块的位置（下标位置）
        int cur = 0;

        //执行每个页号时内存块序列的状态
        int[] tempState = new int[yeKuangNum];

        //记录缺页情况， 1表示缺页，0表示不缺页
        int[] isLackOfPage = new int[pagesSequence.length];
        Arrays.fill(isLackOfPage, 0, pagesSequence.length, 0);

        //缺页次数
        int lackTimes = 0;

        //开始时，内存块状态都为空闲（-1表示）
        Arrays.fill(tempState, 0, yeKuangNum, -1);

        for (int i = 0; i < pagesSequence.length; i++)
        {
            //如果缺页
            if(findKey(tempState, 0, yeKuangNum - 1, pagesSequence[i]) == -1)  //页框内找不到下一个页  则视为缺页
            {
                isLackOfPage[i] = 1;
                lackTimes++;

                //如果内存块还有剩余
                if(tempState[yeKuangNum - 1] == -1)
                {
                    tempState[cur] = pagesSequence[i];
                    recentVisitedBlocks.put(String.valueOf(pagesSequence[i]), pagesSequence[i]);
                    cur++;
                }
                //如果内存块都已被使用
                else
                {
                    //找到当前内存块序列中最近最少使用的内存块，并将其置换
                    cur = findKey(tempState, 0, yeKuangNum - 1, recentVisitedBlocks.getHead());
                    tempState[cur] = pagesSequence[i];
                    recentVisitedBlocks.put(String.valueOf(pagesSequence[i]), pagesSequence[i]);
                }
            }
            //如果不缺页
            else
            {
                //将这里被使用的pageSequence[i]在最近使用的内存块集合中的原先位置调整到最近被访问的位置
                recentVisitedBlocks.get(String.valueOf(pagesSequence[i]));
            }

            //保存当前内存块序列的状态
            System.arraycopy(tempState, 0, yeKuangState[i], 0, yeKuangNum);
        }
        if (BiaoZhiWei==1) {
        	showYeKuangState(yeKuangState, pagesSequence, yeKuangNum, isLackOfPage, lackTimes);
        }
        
    
        //命中率
        double lackOfPagesRate = lackTimes * 1.0 / pagesSequence.length;

        System.out.println("\n该程序的页号序列长度为：" + pagesSequence.length + ", 执行该算法后，缺页次数为：" + lackTimes + ", 命中率为：" + (1-lackOfPagesRate) * 100 + "%"+", 缺页率为：" + lackOfPagesRate * 100 + "%");
    }

    public static void OPT(int[] pagesSequence, int yeKuangNum)
    {
        //执行页号序列期间内存块的状态
        int[][] yeKuangState = new int[pagesSequence.length][yeKuangNum];

        //该指针指向将要被置换的内存块的位置（下标位置）
        int cur = 0;

        //执行每个页号时内存块序列的状态
        int[] tempState = new int[yeKuangNum];

        //记录缺页情况， 1表示缺页，0表示不缺页
        int[] isLackOfPage = new int[pagesSequence.length];
        Arrays.fill(isLackOfPage, 0, pagesSequence.length, 0);

        //缺页次数
        int lackTimes = 0;

        //开始时，内存块状态都为空闲（-1表示）
        Arrays.fill(tempState, 0, yeKuangNum, -1);

        for (int i = 0; i < pagesSequence.length; i++)
        {
            //如果缺页
            if(findKey(tempState, 0, yeKuangNum - 1, pagesSequence[i]) == -1)
            {
                isLackOfPage[i] = 1;
                lackTimes++;

                //如果页框还有剩余
                if(tempState[yeKuangNum - 1] == -1)
                {
                    tempState[cur] = pagesSequence[i];
                    cur++;
                }
                //如果内存块都已被使用
                else
                {
                    int maxDist = 0;

                    for(int j = 0; j < yeKuangNum; j++)
                    {
                        //找出当前内存块序列中的内存块tempState[j]在将来会被访问到的（第一个）位置
                        int loc = findKey(pagesSequence, i + 1, pagesSequence.length - 1, tempState[j]);

                        //如果将来该内存块都不再被使用了
                        if (loc == -1)
                        {
                            cur = j;
                            break;
                        }
                        //找出当前内存块序列中的所有内存块在将来会被访问到的最远位置，设为maxLoc
                        else
                        {
                            if(maxDist < loc)
                            {
                                maxDist = loc;
                                cur = j;
                            }
                        }
                    }

                    tempState[cur] = pagesSequence[i];
                }
            }

            //保存当前内存块序列的状态
            System.arraycopy(tempState, 0, yeKuangState[i], 0, yeKuangNum);
        }

        if (BiaoZhiWei==1) {
        	showYeKuangState(yeKuangState, pagesSequence, yeKuangNum, isLackOfPage, lackTimes);
        }
        
        //命中率
        double lackOfPagesRate = lackTimes * 1.0 / pagesSequence.length;

        System.out.println("\n该程序的页号序列长度为：" + pagesSequence.length + ", 执行该算法后，缺页次数为：" + lackTimes + ", 命中率为：" + (1-lackOfPagesRate) * 100 + "%"+", 缺页率为：" + lackOfPagesRate * 100 + "%");
    }


    //返回key在arr中第一次出现的位置,start和end为数组下标, 找不到则返回-1
    public static int findKey(int[] arr, int start, int end, int key)
    {
        for (int i = start; i <= end; i++)
        {
            if (arr[i] == key)
            {
                return i;
            }
        }

        return -1;
    }
    
    public static void showYeKuangState(int[][] yeKuangState, int[] pagesSequence, int yeKuangNum, int[] isLackofPage, int lackTimes)
    {
        String[] pagesDescription = {"不缺页", "缺页"};

        int pagesSequenceLength = pagesSequence.length;

        for (int i = 0; i < pagesSequenceLength; i++)
        {
            System.out.print("当前访问页号：" + pagesSequence[i]);
            System.out.print("\t\t");
            System.out.print("当前页框为：");
            
            for (int k = 0; k < yeKuangNum; k++)
            {
                //如果当前页框还没被使用，置为空
                if (yeKuangState[i][k] == -1)
                {
                    System.out.printf("%5s", " ");
                }
                else
                {
                    System.out.printf("%5s", yeKuangState[i][k]);
                }
            }
            System.out.print("\t\t");
            System.out.println("  缺页情况：" + pagesDescription[isLackofPage[i]]);

        }
    }

    
}


//LRU算法的辅助存储类
class LRULinkedHashMap<K, V> extends LinkedHashMap<K, V>
{
    //最大内存块数（容量）
    private int maxYeKuangNum;

    //设置默认负载因子
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    public LRULinkedHashMap(int maxCapacity)
    {
        //设置accessOrder为true，保证了LinkedHashMap底层实现的双向链表是按照访问的先后顺序排序
        super(maxCapacity, DEFAULT_LOAD_FACTOR, true);
        this.maxYeKuangNum = maxCapacity;
    }

    //得到最近最少被访问的元素
    public V getHead()
    {
        return (V) this.values().toArray()[0];
    }

    //移除多余的最近最少被访问的元素
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest)
    {
        return size() > maxYeKuangNum;
    }
}
