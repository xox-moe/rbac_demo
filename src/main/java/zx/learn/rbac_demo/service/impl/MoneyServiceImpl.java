package zx.learn.rbac_demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zx.learn.rbac_demo.dao.MoneyMapper;
import zx.learn.rbac_demo.model.Record;
import zx.learn.rbac_demo.service.MoneyService;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: zx
 * Date: 2019/8/20
 * Time: 15:33
 * Description:
 */

@Service
@Transactional
public class MoneyServiceImpl implements MoneyService {

    @Autowired
    MoneyMapper mapper;


    @Override
    @Transactional
    public Boolean recharge(int userId, double amount) {
        Record record = new Record();
        record.setFormId(0);
        record.setToId(userId);
        record.setAmount(amount);
        record.setTime(LocalDateTime.now());
        mapper.addRecord(record);
        return mapper.changeUserBalance(userId, amount, true);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean transfer(int fromId, int toId, double amount) throws Exception {

        Record record = new Record();
        record.setFormId(fromId);
        record.setToId(toId);
        record.setAmount(amount);
        record.setTime(LocalDateTime.now());


        int availableBalance = mapper.getAvailableBalance(fromId);

        if (amount > availableBalance) {
            throw new Exception("可用余额不够");
        }
        if (amount < 0) {
            throw new Exception("打赏金额异常");
        }

        mapper.addRecord(record);
        mapper.changeUserBalance(fromId, amount, false);

        Random random = new Random();
        if (random.nextInt(2) == 0) {
            throw new Exception("随机的转账失败");
        }

        return mapper.changeUserBalance(toId, amount, true);
    }
}
