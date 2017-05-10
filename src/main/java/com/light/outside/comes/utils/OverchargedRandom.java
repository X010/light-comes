package com.light.outside.comes.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by b3st9u on 17/4/18.
 */
public class OverchargedRandom {
    /**
     * 1.总金额不能超过200*100 单位是分
     * 2.每个红包都要有钱，最低不能低于1分，最大金额不能超过200*100
     */
    private static final double MINMONEY = 1;
    private static final double MAXMONEY = 200 * 100;
    /**
     * 这里为了避免某一个红包占用大量资金，我们需要设定非最后一个红包的最大金额，我们把他设置为红包金额平均值的N倍；
     */
    private static final double TIMES = 2.1;

    /**
     * 拆分红包
     *
     * @param money ：红包总金额
     * @param count ：个数
     * @return
     */
    public List<Double> splitRedPackets(double money, int count) {
        //红包 合法性校验
        if (!isRight(money, count)) {
            return null;
        }
        //红包列表
        List<Double> list = new ArrayList<Double>();
        //每个红包最大的金额为平均金额的Times 倍
        double max = (money * TIMES / count);
        max = max > MAXMONEY ? MAXMONEY : max;
        //分配红包
        for (int i = 0; i < count; i++) {
            double one = randomRedPacket(money, MINMONEY, max, count - i);
            list.add(one);
            money -= one;
        }
        return list;
    }

    /**
     * 随机分配一个红包
     *
     * @param money
     * @param minS  :最小金额
     * @param maxS  ：最大金额(每个红包的默认Times倍最大值)
     * @param count
     * @return
     */
    public static double randomRedPacket(double money, double minS, double maxS, int count) {
        DecimalFormat df = new DecimalFormat("#.00");
        //若是只有一个，直接返回红包
        if (count == 1) {
            return Double.parseDouble(df.format(money));
        }
        //若是最小金额红包 == 最大金额红包， 直接返回最小金额红包
        if (minS == maxS) {
            return Double.parseDouble(df.format(minS));
        }
        //校验 最大值 max 要是比money 金额高的话？ 去 money 金额
        double max = maxS > money ? money : maxS;
        //随机一个红包 = 随机一个数* (金额-最小)+最小
        Random random = new Random();
        double one = random.nextDouble() * (max - minS) + minS;
        //剩下的金额
        double moneyOther = money - one;
        //校验这种随机方案是否可行，不合法的话，就要重新分配方案
        if(moneyOther<=0){//剩余金额小于0直接返回0
            return 0;
        }
        if (isRight(moneyOther, count - 1)) {
            return Double.parseDouble(df.format(one));
        } else {
            //重新分配
            double avg = moneyOther / (count - 1);
            //本次红包过大，导致下次的红包过小；如果红包过大，下次就随机一个小值到本次红包金额的一个红包
            if (avg < MINMONEY) {
                //递归调用，修改红包最大金额
                return randomRedPacket(money, minS, one, count);
            } else if (avg > MAXMONEY) {
                //递归调用，修改红包最小金额
                return randomRedPacket(money, one, maxS, count);
            }
        }
        return Double.parseDouble(df.format(one));
    }

    /**
     * 红包 合法性校验
     *
     * @param money
     * @param count
     * @return
     */
    private static boolean isRight(double money, int count) {
        double avg = money / count;
        //小于最小金额
        if (avg < MINMONEY) {
            return false;
            //大于最大金额
        } else if (avg > MAXMONEY) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        //随机一个188.88  5个红包
        OverchargedRandom dd = new OverchargedRandom();
        //单位是分
        System.out.println(dd.splitRedPackets(0, 10));
    }

}
