package com.geekq.miaosha;

import java.util.ArrayList;
import java.util.List;

/**
 * mybatis xml 写法的配置测试类
 */
public class Test {
    public static List<Double> list = new ArrayList<Double>();

    public static void main(String[] args) {
        double[] keys = {1, 2,3};
        System.err.println(getNum(keys, 5));


//        举例：3 个元素：a, b, c。所以有2 ^ 3 = 8 个组合结果：所以i = 0, 1, 2,....7.对应应输出 a, b, ab, c...abc(注意a表示001，不是100.)
//
//        将i变成2进制：
//        i = 1 = 001
//    　　i = 2 = 010
//       i = 3 = 011
//
//　　　　(1) j = 0 (1) j = 0 (1) j = 0
//        　　移1位：1 << j == 001 1 << j == 001 1 << j == 001
//         　　和i = 001 相与，两个位都为1，返回1 与i无相同位 和i = 001 相与，两个位都为1，返回1
//          　　输出：a 输出a
//
//        (2) j = 1 (2) j = 1 (2) j = 1
//        再移一位：1 << j == 010 1 << j == 010                             　　1 << j == 010
//        与i = 001 相与。无相同1 和i相与，两个位都为1，返回1 和i相与，两个位都为1，返回1
//        输出b 输出b
//
//        (3) j = 2 3)j = 2 (3) j = 2
//        移一位 1 << j == 100 1 << j == 100
//        与i无相同位 与i无相同位 与i无相同位
//
//        所以i = 001, 只输出a.所以i = 010, 只输出b.所以011，输出ab
//
//                *************************************
//         *可见上面每一个数字i, 只会判断判断3次，因为只需要移三次位，二进制就遍历完了

    }

    static List<Double> getNum(double[] keys, double kill) {
        int n = keys.length;//数字长度
        System.out.println(n);
        int nbit = 1 << n; // 8
        System.out.println(nbit);
        double in;
        List<Double> list = new ArrayList<Double>();
        for (int i = 0; i < nbit; i++) {
            System.out.println("nbit======="+i);
            in = 0;
            list.clear();
            for (int j = 0; j < n; j++) {
                System.out.println("j======"+j);
                int tmp = 1 << j; // 由0到n右移位
                System.out.println("tmp===="+tmp);
                if ((tmp & i) != 0) { // 与运算，同为1时才会是1
                    System.out.println("keys[j]=========="+keys[j]);
                    in += keys[j];
                    list.add(keys[j]);
                }
            }
            if (in == kill){
                return list;
            }
        }
        return list;
    }
}