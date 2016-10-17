package com.light.outside.comes.service;

import com.light.outside.comes.model.Prize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by b3st9u on 16/10/17.
 */
@Service
public class RaffleArithmeticService {
        // 放大倍数
        private static final int mulriple = 1000000;

        public int pay(List<Prize> prizes) {
            int lastScope = 0;
            // 洗牌，打乱奖品次序
            Collections.shuffle(prizes);
            Map<Integer, int[]> prizeScopes = new HashMap<Integer, int[]>();
            Map<Integer, Integer> prizeQuantity = new HashMap<Integer, Integer>();
            for (Prize prize : prizes) {
                int prizeId = prize.getPrizeId();
                // 划分区间
                int currentScope = lastScope + prize.getProbability().multiply(new BigDecimal(mulriple)).intValue();
                prizeScopes.put(prizeId, new int[] { lastScope + 1, currentScope });
                prizeQuantity.put(prizeId, prize.getQuantity());

                lastScope = currentScope;
            }

            // 获取1-1000000之间的一个随机数
            int luckyNumber = new Random().nextInt(mulriple);
            int luckyPrizeId = 0;
            // 查找随机数所在的区间
            if ((null != prizeScopes) && !prizeScopes.isEmpty()) {
                Set<Map.Entry<Integer, int[]>> entrySets = prizeScopes.entrySet();
                for (Map.Entry<Integer, int[]> m : entrySets) {
                    int key = m.getKey();
                    if (luckyNumber >= m.getValue()[0] && luckyNumber <= m.getValue()[1] && prizeQuantity.get(key) > 0) {
                        luckyPrizeId = key;
                        break;
                    }
                }
            }

            if (luckyPrizeId > 0) {
                // 奖品库存减一
            }

            return luckyPrizeId;
        }
}
