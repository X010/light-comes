package com.light.outside.comes.service;

import com.light.outside.comes.model.CouponRecordModel;
import com.light.outside.comes.model.CouponUsedRecord;
import com.light.outside.comes.mybatis.mapper.PersistentDao;
import com.light.outside.comes.utils.CONST;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by b3st9u on 16/11/15.
 */
@Service
public class CouponService {
    @Autowired
    private PersistentDao persistentDao;

    /**
     * 转让优惠券
     * @param cardno
     * @param uid
     * @param couponRecordId
     * @return
     */
    public int transferCoupon(String cardno, long uid, long couponRecordId) {
        int status = 0;
        CouponRecordModel couponRecordModel = persistentDao.getCouponRecordById(couponRecordId);
        if (couponRecordModel != null) {
            if (couponRecordModel.getStatus() == CONST.COUPON_STATUS_NOTUSED) {
                //修改为已使用状态
                persistentDao.editCouponRecordStatusByCardno(cardno, CONST.COUPON_STATUS_USED);
                CouponUsedRecord couponUsedRecord = new CouponUsedRecord();
                couponUsedRecord.setUid(uid);
                couponUsedRecord.setCardno(cardno);
                couponUsedRecord.setUsed_time(new Date());
                couponUsedRecord.setSource_uid(couponRecordModel.getUid());
                int count = persistentDao.addCouponUsedRecord(couponUsedRecord);
                if (count > 0) {
                    status = 1;
                }
            } else {
                status = -1;
            }
        } else {
            status = -2;
        }
        return status;
    }


}