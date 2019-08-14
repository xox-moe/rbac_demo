package zx.learn.rbac_demo.mapper;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageRowBounds;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import zx.learn.rbac_demo.entity.SysLog;

/**
 * SysLogMapper继承基类
 */
@Mapper
public interface SysLogMapper extends MyBatisBaseDao<SysLog, Integer> {

    Page<SysLog> listSysLog(PageRowBounds pageRowBounds);

}