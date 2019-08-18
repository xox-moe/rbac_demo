package zx.learn.rbac_demo.dao;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageRowBounds;
import org.apache.ibatis.annotations.Mapper;
import zx.learn.rbac_demo.model.SysLog;

/**
 * SysLogMapper继承基类
 */
@Mapper
public interface SysLogMapper extends MyBatisBaseDao<SysLog, Integer> {

    Page<SysLog> listSysLog(PageRowBounds pageRowBounds,String str);

}