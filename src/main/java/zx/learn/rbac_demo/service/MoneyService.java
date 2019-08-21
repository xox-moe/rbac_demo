package zx.learn.rbac_demo.service;

import com.github.pagehelper.Page;
import com.sun.org.apache.xpath.internal.operations.Bool;
import zx.learn.rbac_demo.model.Record;

import java.util.List;

public interface MoneyService {


    /**
     * 给用户 充值
     * @param userId 用户ID
     * @param amount 金额
     * @return 是否成功
     */
    public Boolean recharge(int userId, double amount);

    /**
     * 转账
     *
     * 需要 50% 的成功率，体现出事务
     * @param fromId 来自
     * @param toId   去向
     * @param amount 金额
     * @return 是否成功
     */
    public Boolean transfer(int fromId, int toId, double amount) throws Exception;

    Page<Record> listUserTransferRecord(Integer userId, Integer page, Integer limit);


//    public List<Record>


}
