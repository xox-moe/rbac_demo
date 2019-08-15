package zx.learn.rbac_demo.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageRowBounds;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zx.learn.rbac_demo.entity.SysLog;
import zx.learn.rbac_demo.mapper.SysLogMapper;
import zx.learn.rbac_demo.service.SysLogService;

@Service
@Slf4j
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    SysLogMapper mapper;


    @Override
    public void addLog(SysLog log) {
        mapper.insert(log);
    }

    @Override
    public void deleteLog(Integer logId) {
        mapper.deleteByPrimaryKey(logId);
    }

    @Override
    public Page<SysLog> listSysLog(Integer page, Integer limit,String str) {
        PageRowBounds pageRowBounds = new PageRowBounds((page - 1) * limit, limit);
        Page<SysLog> messagePage = mapper.listSysLog(pageRowBounds,str);
        PageInfo info = new PageInfo<>(messagePage.getResult());
        log.info(info.toString());
        return messagePage;
    }
}
