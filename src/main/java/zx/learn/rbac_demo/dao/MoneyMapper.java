package zx.learn.rbac_demo.dao;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageRowBounds;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import zx.learn.rbac_demo.model.Record;

@Mapper
public interface MoneyMapper {


//    public boolean recharge(int userId, double amount);

    public boolean addRecord(Record record);

    public boolean changeUserBalance(@Param("userId") int userId, @Param("amount") double amount, @Param("ifAdd") boolean ifAdd);

    public Page<Record> listUserTransferRecord(@Param("userId") int userId, PageRowBounds pageRowBounds);

    int getAvailableBalance(@Param("fromId") int fromId);

}
