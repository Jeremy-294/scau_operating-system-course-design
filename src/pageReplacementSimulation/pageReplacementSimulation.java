package pageReplacementSimulation;

import java.util.*;

public class pageReplacementSimulation
{
	//�Ƿ�鿴��ϸҳ���û����̵ı�־λ 
    public static int BiaoZhiWei;
    
    //ҳ���С,ÿ��ҳ��ɰ���ZhiLingNumPerPage��ָ��
    public static int ZhiLingNumPerPage;

    //��Ÿó�������ִ�е�ָ��������ַ����
    public static int[] ZhiLingSequence = null;

    //��Ž�����ָ���ַ����ת����(�����ϲ�����ҳ��)������ҳ������
    public static int[] PagesSequence = null;

    //ָ��������ó����ҳ����
    public static int YeKuangNum;


    public static void main(String[] args)
    {

        int count = 1;
        Scanner scan = new Scanner(System.in);

        System.out.println("\t\t����������������������������������������������ӭʹ��ҳ���û�ģ��ϵͳ������������������������������������������������������\n");

        while (true)
        {
            System.out.println("���� ���� ���� ������ " + count + " �������ҳ���û�ģ����̡��� ���� ���� ����\n");
            System.out.println("����ϵͳ������1���˳�ϵͳ������-1");

            int caoZuoShu = scan.nextInt();

            if(caoZuoShu == -1) break;  //����Ϊ-1 ����ѭ������

            int zhiLingNum = 400;  //ָ������

            ZhiLingSequence = getZhiLingSequence(zhiLingNum);
            System.out.println("ϵͳ������ɵ�400��ָ���ַ�������£�");
            showZhiLingSequence(ZhiLingSequence); //���������ɵ�ָ��
            System.out.println();

            //ÿ1k���10��ָ��
            ZhiLingNumPerPage = 10;
            PagesSequence = zhiLingToPagesSequence(ZhiLingSequence, ZhiLingNumPerPage); //ָ���ҳ����
            System.out.println("��ָ���ַ���ж�Ӧ��ҳ�����У��Ѿ�������ҳ�źϲ������£�");
            showPagesSequence(PagesSequence); //���ȫ��ҳ��
            System.out.println();
            System.out.println("ʵ���ܹ�ʹ�õ���ҳ�Ÿ���Ϊ��" + PagesSequence.length);
            System.out.println();

            YeKuangNum = 4;		//ҳ���������4��ʼ
            while(YeKuangNum <41)		//ҳ�������䵽40����
            {
            	System.out.println("����ҳ����λ��"+YeKuangNum);
            	 System.out.println("ִ��FIFO�㷨");
                 FIFO(PagesSequence, YeKuangNum);	//����FIFO
                 System.out.println("***************************************************************");
                 System.out.println("ִ��LRU�㷨");
                 LRU(PagesSequence, YeKuangNum);	//����LRU
                 System.out.println("***************************************************************");
                 System.out.println("ִ��OPT�㷨");
                 OPT(PagesSequence, YeKuangNum);	//����OPT
                 System.out.println("***************************************************************");
                 YeKuangNum+=1;
                 System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
           
            } 
            //չʾҳ���û��㷨��ϸ����
            System.out.println("����1��ѯ��ϸ��ҳ���û����̣����������������˳���");
            BiaoZhiWei = scan.nextInt();
            if(BiaoZhiWei==1) {
           	 	System.out.println("������˴�ҳ���û�Ҫ�����ҳ��������4~40����");
            	YeKuangNum= scan.nextInt();
            	System.out.println("��������Ҫģ���ҳ���û��㷨��ţ���������1 / 2 / 3    1��FIFO�� 2��LRU�� 3��OPT)");
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
                        System.out.println("������������");
                }
            	BiaoZhiWei=0; //�������� ����ڶ���ѭ��������ѭ��
            }
            else
            	System.out.println("����ѡ�񲻿���ϸҳ���û����̣�");
            
            System.out.println("\n\n");
            count++;
        }
        System.out.println("\n~~~~~~~~~~���ѳɹ��˳�ϵͳ��~~~~~~~~~~");
    }

    
    // ��� ����400��ַָ��ĺ��� 
    public static int[] getZhiLingSequence(int zhiLingNum)
    {
        int[] zhiLingSequence = new int[zhiLingNum];	//����ͬ�ȴ�С������

        int count = 0;

        while (count < zhiLingNum)
        {
        	//�������ָ���ַ
        	int randomAddress1 = 0 + (int) (Math.random() * 199);   //0 ���199
        	zhiLingSequence[count] = randomAddress1;	
            randomAddress1++;	
            zhiLingSequence[++count] = randomAddress1;	

            int randomAddress2 = 200 + (int) (Math.random() * 199);  //200~399
            zhiLingSequence[++count] = randomAddress2; 
            randomAddress2++;
            zhiLingSequence[++count] = randomAddress2;	
            
            count++;
        }
        return zhiLingSequence; //���ɵĵ�ַָ�����
    }

    //��ӡȫ��400����ַָ��ĺ���
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

    //��ָ���ַ���б��ҳ�Ŷ���
    public static int[] zhiLingToPagesSequence(int[] zhiLingSequence, int zhiLingNumPerPage)
    {
        ArrayList<Integer> pagesList = new ArrayList<Integer>();

        int temp = -1;
        //ҳ��
        int pageIndex;

        for (int i = 0; i < zhiLingSequence.length; i++)
        {
            pageIndex = zhiLingSequence[i] / zhiLingNumPerPage;

            //�����ڵ�ҳ�źϲ�
            if (pageIndex != temp)
            {
                pagesList.add(pageIndex);
                temp = pageIndex;
            }
        }

        //����ҳ�����о��ϲ�֮�󳤶��������ָ��������ַ���г���
        int[] pagesSequence = new int[pagesList.size()];

        for (int i = 0; i < pagesList.size(); i++)
        {
            pagesSequence[i] = pagesList.get(i);
        }

        return pagesSequence;
    }

//    ��ӡ����ҳ�Ŷ��еĺ���
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
        //ִ��ҳ�������ڼ��ڴ���״̬
        int[][] yeKuangState = new int[pagesSequence.length][yeKuangNum];

        //��ָ��ָ��Ҫ���û����ڴ���λ�ã��±�λ�ã�
        int cur = 0;

        //ִ��ÿ��ҳ��ʱ�ڴ�����е�״̬
        int[] tempState = new int[yeKuangNum];

        //��¼ȱҳ����� 1��ʾȱҳ��0��ʾ��ȱҳ
        int[] isLackOfPage = new int[pagesSequence.length];
        Arrays.fill(isLackOfPage, 0, pagesSequence.length, 0);

        //ȱҳ����
        int lackTimes = 0;

        //��ʼʱ���ڴ��״̬��Ϊ���У�-1��ʾ��
        Arrays.fill(tempState, 0, yeKuangNum, -1);

        for (int i = 0; i < pagesSequence.length; i++)
        {
            //���ȱҳ
            if (findKey(tempState, 0, yeKuangNum - 1, pagesSequence[i]) == -1)
            {
                isLackOfPage[i] = 1;
                lackTimes++;
                tempState[cur] = pagesSequence[i];

                //ָ�������ƶ�����memoryBlocksNumʱ��������ָ��ʼ���ڴ��λ��0
                if (cur + 1 > yeKuangNum - 1)
                {
                    cur = 0;
                }
                else
                {
                    cur++;
                }
            }
            
            //���浱ǰ�ڴ�����е�״̬
            System.arraycopy(tempState, 0, yeKuangState[i], 0, yeKuangNum);
        }
        if (BiaoZhiWei==1) {
        	showYeKuangState(yeKuangState, pagesSequence, yeKuangNum, isLackOfPage, lackTimes);
        }
        
        //������
        double lackOfPagesRate = lackTimes * 1.0 / pagesSequence.length;

        System.out.println("\n�ó����ҳ�����г���Ϊ��" + pagesSequence.length + ", ִ�и��㷨��ȱҳ����Ϊ��" + lackTimes + ", ������Ϊ��" + (1-lackOfPagesRate) * 100 + "%"+", ȱҳ��Ϊ��" + lackOfPagesRate * 100 + "%");

    }

    public static void LRU(int[] pagesSequence, int yeKuangNum)
    {
        //ά��һ�����ʹ�õ��ڴ�鼯��
        LRULinkedHashMap<String, Integer> recentVisitedBlocks = new LRULinkedHashMap<String, Integer>(yeKuangNum);

        //ִ��ҳ�������ڼ��ڴ���״̬
        int[][] yeKuangState = new int[pagesSequence.length][yeKuangNum];

        //��ָ��ָ��Ҫ���û����ڴ���λ�ã��±�λ�ã�
        int cur = 0;

        //ִ��ÿ��ҳ��ʱ�ڴ�����е�״̬
        int[] tempState = new int[yeKuangNum];

        //��¼ȱҳ����� 1��ʾȱҳ��0��ʾ��ȱҳ
        int[] isLackOfPage = new int[pagesSequence.length];
        Arrays.fill(isLackOfPage, 0, pagesSequence.length, 0);

        //ȱҳ����
        int lackTimes = 0;

        //��ʼʱ���ڴ��״̬��Ϊ���У�-1��ʾ��
        Arrays.fill(tempState, 0, yeKuangNum, -1);

        for (int i = 0; i < pagesSequence.length; i++)
        {
            //���ȱҳ
            if(findKey(tempState, 0, yeKuangNum - 1, pagesSequence[i]) == -1)  //ҳ�����Ҳ�����һ��ҳ  ����Ϊȱҳ
            {
                isLackOfPage[i] = 1;
                lackTimes++;

                //����ڴ�黹��ʣ��
                if(tempState[yeKuangNum - 1] == -1)
                {
                    tempState[cur] = pagesSequence[i];
                    recentVisitedBlocks.put(String.valueOf(pagesSequence[i]), pagesSequence[i]);
                    cur++;
                }
                //����ڴ�鶼�ѱ�ʹ��
                else
                {
                    //�ҵ���ǰ�ڴ���������������ʹ�õ��ڴ�飬�������û�
                    cur = findKey(tempState, 0, yeKuangNum - 1, recentVisitedBlocks.getHead());
                    tempState[cur] = pagesSequence[i];
                    recentVisitedBlocks.put(String.valueOf(pagesSequence[i]), pagesSequence[i]);
                }
            }
            //�����ȱҳ
            else
            {
                //�����ﱻʹ�õ�pageSequence[i]�����ʹ�õ��ڴ�鼯���е�ԭ��λ�õ�������������ʵ�λ��
                recentVisitedBlocks.get(String.valueOf(pagesSequence[i]));
            }

            //���浱ǰ�ڴ�����е�״̬
            System.arraycopy(tempState, 0, yeKuangState[i], 0, yeKuangNum);
        }
        if (BiaoZhiWei==1) {
        	showYeKuangState(yeKuangState, pagesSequence, yeKuangNum, isLackOfPage, lackTimes);
        }
        
    
        //������
        double lackOfPagesRate = lackTimes * 1.0 / pagesSequence.length;

        System.out.println("\n�ó����ҳ�����г���Ϊ��" + pagesSequence.length + ", ִ�и��㷨��ȱҳ����Ϊ��" + lackTimes + ", ������Ϊ��" + (1-lackOfPagesRate) * 100 + "%"+", ȱҳ��Ϊ��" + lackOfPagesRate * 100 + "%");
    }

    public static void OPT(int[] pagesSequence, int yeKuangNum)
    {
        //ִ��ҳ�������ڼ��ڴ���״̬
        int[][] yeKuangState = new int[pagesSequence.length][yeKuangNum];

        //��ָ��ָ��Ҫ���û����ڴ���λ�ã��±�λ�ã�
        int cur = 0;

        //ִ��ÿ��ҳ��ʱ�ڴ�����е�״̬
        int[] tempState = new int[yeKuangNum];

        //��¼ȱҳ����� 1��ʾȱҳ��0��ʾ��ȱҳ
        int[] isLackOfPage = new int[pagesSequence.length];
        Arrays.fill(isLackOfPage, 0, pagesSequence.length, 0);

        //ȱҳ����
        int lackTimes = 0;

        //��ʼʱ���ڴ��״̬��Ϊ���У�-1��ʾ��
        Arrays.fill(tempState, 0, yeKuangNum, -1);

        for (int i = 0; i < pagesSequence.length; i++)
        {
            //���ȱҳ
            if(findKey(tempState, 0, yeKuangNum - 1, pagesSequence[i]) == -1)
            {
                isLackOfPage[i] = 1;
                lackTimes++;

                //���ҳ����ʣ��
                if(tempState[yeKuangNum - 1] == -1)
                {
                    tempState[cur] = pagesSequence[i];
                    cur++;
                }
                //����ڴ�鶼�ѱ�ʹ��
                else
                {
                    int maxDist = 0;

                    for(int j = 0; j < yeKuangNum; j++)
                    {
                        //�ҳ���ǰ�ڴ�������е��ڴ��tempState[j]�ڽ����ᱻ���ʵ��ģ���һ����λ��
                        int loc = findKey(pagesSequence, i + 1, pagesSequence.length - 1, tempState[j]);

                        //����������ڴ�鶼���ٱ�ʹ����
                        if (loc == -1)
                        {
                            cur = j;
                            break;
                        }
                        //�ҳ���ǰ�ڴ�������е������ڴ���ڽ����ᱻ���ʵ�����Զλ�ã���ΪmaxLoc
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

            //���浱ǰ�ڴ�����е�״̬
            System.arraycopy(tempState, 0, yeKuangState[i], 0, yeKuangNum);
        }

        if (BiaoZhiWei==1) {
        	showYeKuangState(yeKuangState, pagesSequence, yeKuangNum, isLackOfPage, lackTimes);
        }
        
        //������
        double lackOfPagesRate = lackTimes * 1.0 / pagesSequence.length;

        System.out.println("\n�ó����ҳ�����г���Ϊ��" + pagesSequence.length + ", ִ�и��㷨��ȱҳ����Ϊ��" + lackTimes + ", ������Ϊ��" + (1-lackOfPagesRate) * 100 + "%"+", ȱҳ��Ϊ��" + lackOfPagesRate * 100 + "%");
    }


    //����key��arr�е�һ�γ��ֵ�λ��,start��endΪ�����±�, �Ҳ����򷵻�-1
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
        String[] pagesDescription = {"��ȱҳ", "ȱҳ"};

        int pagesSequenceLength = pagesSequence.length;

        for (int i = 0; i < pagesSequenceLength; i++)
        {
            System.out.print("��ǰ����ҳ�ţ�" + pagesSequence[i]);
            System.out.print("\t\t");
            System.out.print("��ǰҳ��Ϊ��");
            
            for (int k = 0; k < yeKuangNum; k++)
            {
                //�����ǰҳ��û��ʹ�ã���Ϊ��
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
            System.out.println("  ȱҳ�����" + pagesDescription[isLackofPage[i]]);

        }
    }

    
}


//LRU�㷨�ĸ����洢��
class LRULinkedHashMap<K, V> extends LinkedHashMap<K, V>
{
    //����ڴ������������
    private int maxYeKuangNum;

    //����Ĭ�ϸ�������
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    public LRULinkedHashMap(int maxCapacity)
    {
        //����accessOrderΪtrue����֤��LinkedHashMap�ײ�ʵ�ֵ�˫�������ǰ��շ��ʵ��Ⱥ�˳������
        super(maxCapacity, DEFAULT_LOAD_FACTOR, true);
        this.maxYeKuangNum = maxCapacity;
    }

    //�õ�������ٱ����ʵ�Ԫ��
    public V getHead()
    {
        return (V) this.values().toArray()[0];
    }

    //�Ƴ������������ٱ����ʵ�Ԫ��
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest)
    {
        return size() > maxYeKuangNum;
    }
}
