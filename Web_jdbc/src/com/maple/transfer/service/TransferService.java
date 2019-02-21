package com.maple.transfer.service;

import com.maple.transfer.dao.TransferDao;
import com.maple.transfer.utils.DataSourceUtils_C3P0;

import java.sql.SQLException;

/**
 * @author Maple
 * @date 2019.02.01 11:16
 */
public class TransferService {
    public boolean transfer(String outAccount, String inAccount, double money) {
        TransferDao dao = new TransferDao();
        //转账操作是否成功的标志位
        boolean isTransferSuccess = true;
        try {
            //开启事务
            DataSourceUtils_C3P0.startTransaction();
            //调用dao层的转出和转入方法
            dao.transferOut(outAccount, money);
            dao.transferIn(inAccount, money);
        } catch (Exception e) {
            try {
                //事务回滚
                DataSourceUtils_C3P0.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            isTransferSuccess = false;
            e.printStackTrace();
        } finally {
            try {
                //提交事务
                DataSourceUtils_C3P0.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isTransferSuccess;
    }
}
