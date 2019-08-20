package zx.learn.rbac_demo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import zx.learn.rbac_demo.model.Record;

import java.util.List;

@Mapper
public interface MoneyMapper {


//    public boolean recharge(int userId, double amount);

    public boolean addRecord(Record record);

    public boolean changeUserBalance(@Param("userId") int userId, @Param("amount") double amount, @Param("ifAdd") boolean ifAdd);

    public List<Record> listUserTransferRecord(int userId);

    int getAvailableBalance(@Param("fromId") int fromId);

}
