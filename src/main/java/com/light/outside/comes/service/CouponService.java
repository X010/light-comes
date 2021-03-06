package com.light.outside.comes.service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.light.outside.comes.model.*;
import com.light.outside.comes.mybatis.mapper.PersistentDao;
import com.light.outside.comes.qbkl.model.UserModel;
import com.light.outside.comes.utils.CONST;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by b3st9u on 16/11/15.
 */
@Service
public class CouponService {
    @Autowired
    private PersistentDao persistentDao;
    @Autowired
    private RaffleService raffleService;

    /**
     * 转让优惠券
     *
     * @param cardno
     * @param userModel
     * @param couponRecordId
     * @return
     */
    public int transferCoupon(long couponId, String cardno, UserModel userModel, long couponRecordId) {
        int status = 0;
        CouponModel couponModel = persistentDao.getCouponById(couponId);
        CouponRecordModel couponRecordModel = persistentDao.getCouponRecordById(couponRecordId);
        if (couponRecordModel != null && couponModel != null) {
            if (couponRecordModel.getStatus() == CONST.COUPON_STATUS_NOTUSED) {
                CouponUsedRecord couponUsedRecord = new CouponUsedRecord();
                couponUsedRecord.setCoupon_record_id(couponRecordModel.getId());
                couponUsedRecord.setUid(userModel.getId());
                couponUsedRecord.setCardno(cardno);
                couponUsedRecord.setUsed_time(new Date());
                couponUsedRecord.setSource_uid(couponRecordModel.getUid());
                couponUsedRecord.setSource_phone(couponRecordModel.getPhone());
                couponUsedRecord.setTo_phone(userModel.getPhone());
                couponUsedRecord.setCoupon_title(couponRecordModel.getTitle());
                couponUsedRecord.setStatus(1);
                couponUsedRecord.setPrice(couponRecordModel.getPrice());
                int count = persistentDao.addCouponUsedRecord(couponUsedRecord);
                if (count > 0) {
                    status = 1;
                    //修改为已使用状态
                    //persistentDao.editCouponRecordStatusByCardno(cardno, CONST.COUPON_STATUS_USED);
                    //转让给其他用户
                    //persistentDao.editCouponRecordStatusByUser(couponRecordId, CONST.COUPON_STATUS_NOTUSED, userModel.getId(), userModel.getPhone());
                    raffleService.sendCoupon(couponModel,couponRecordModel,userModel.getId(),userModel.getPhone());
                }
            } else {
                status = -1;
            }
        } else {
            status = -2;
        }
        return status;
    }

    public int changeCoupon(String cardno, long couponRecordId) {
        int status = 0;
        CouponRecordModel couponRecordModel = persistentDao.getCouponRecordById(couponRecordId);
        if (couponRecordModel != null) {
            if (couponRecordModel.getStatus() == CONST.COUPON_STATUS_NOTUSED) {
                CouponUsedRecord couponUsedRecord = new CouponUsedRecord();
                couponUsedRecord.setCoupon_record_id(couponRecordModel.getId());
                couponUsedRecord.setUid(0);
                couponUsedRecord.setCardno(cardno);
                couponUsedRecord.setUsed_time(new Date());
                couponUsedRecord.setSource_uid(couponRecordModel.getUid());
                couponUsedRecord.setSource_phone(couponRecordModel.getPhone());
                couponUsedRecord.setTo_phone("0");
                couponUsedRecord.setCoupon_title(couponRecordModel.getTitle());
                couponUsedRecord.setStatus(1);
                couponUsedRecord.setPrice(couponRecordModel.getPrice());
                int count = persistentDao.addCouponUsedRecord(couponUsedRecord);
                if (count > 0) {
                    status = 1;
                    //修改为已使用状态
                    persistentDao.editCouponRecordStatusByCardno(cardno, CONST.COUPON_STATUS_USED);
                }
            } else {
                status = -1;
            }
        } else {
            status = -2;
        }
        return status;
    }

    /**
     * 根据手机号码获取转让过来信息
     *
     * @param phone
     * @return
     */
    public List<CouponUsedRecord> getCouponUsedRecordByPhone(String phone, int status) {
        Preconditions.checkNotNull(phone);
        Preconditions.checkNotNull(status > 0);

        return this.persistentDao.getCouponeUsedRecordByPhone(phone, status);
    }

    /**
     * 对该优惠劵进行结算
     *
     * @param ids
     * @return
     */
    public List<CouponUsedRecord> balanceCoupon(List<Long> ids, String phone) {
        Preconditions.checkNotNull(ids);

        List<CouponUsedRecord> couponUsedRecords = Lists.newArrayList();

        float totalPrice = 0;
        for (long id : ids) {
            CouponUsedRecord couponUsedRecord = this.persistentDao.getCouponUsedRecordById(id);

            if (couponUsedRecord != null && couponUsedRecord.getStatus() == CONST.COUPON_B_INIT) {
                //对CouponUsedRecord的状态进行更新
                couponUsedRecord.setStatus(CONST.COUPON_B_OVER);
                totalPrice += couponUsedRecord.getPrice();
                couponUsedRecords.add(couponUsedRecord);
            }
        }


        if (totalPrice > 0) {
            CouponBill couponBill = new CouponBill();
            couponBill.setCreate_time(new Date());
            couponBill.setTotal_price(totalPrice);
            couponBill.setPhone(phone);

            long bill_id = this.persistentDao.addCouponBill(couponBill);

            if (bill_id > 0) {
                for (CouponUsedRecord couponUsedRecord : couponUsedRecords) {
                    couponUsedRecord.setBill_id(bill_id);
                    this.persistentDao.updateCouponeUsedRecord(couponUsedRecord);
                }
            }
        }

        return couponUsedRecords;
    }


    public List<CouponUsedRecord> getCouponBalanceDetailByBillid(long id) {
        return this.persistentDao.getCouponUsedByBillid(id);
    }

    public PageResult<CouponBill> getCouponBill(PageModel pageModel) {
        Preconditions.checkNotNull(pageModel);

        int total = this.persistentDao.couponBillTotal();

        PageResult<CouponBill> couponBillPageResult = new PageResult<CouponBill>();
        List<CouponBill> couponBills = this.persistentDao.getCouponBill(pageModel.getStart(), pageModel.getSize());

        couponBillPageResult.setData(couponBills);
        couponBillPageResult.setPageModel(pageModel);
        couponBillPageResult.setTotal(total);
        return couponBillPageResult;
    }

    /**
     * 查询一个未绑定的优惠券
     *
     * @param cid
     * @return
     */
    public CouponRecordModel getCouponBlanceByCouponId(long cid) {
        return this.persistentDao.getCouponRecorByCid(cid);
    }

    /**
     * 获取优惠券信息
     *
     * @param cid
     * @return
     */
    public CouponModel getCouponByCouponId(long cid) {
        return this.persistentDao.getCouponById(cid);
    }


    /**
     * 修改过期状态
     */
    public void changeCouponRecordStatus() {
        List<CouponModel> list = this.persistentDao.queryExpirationCoupon();
        if (list != null && list.size() > 0) {
            for (CouponModel couponModel : list) {
                this.persistentDao.editCouponRecordStatus(couponModel.getId(), CONST.RAFFLE_STATUS_EXPIRATION);
            }
        }
    }
}
