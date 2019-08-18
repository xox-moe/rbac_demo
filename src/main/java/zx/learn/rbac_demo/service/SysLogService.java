package zx.learn.rbac_demo.service;

import com.github.pagehelper.Page;
import zx.learn.rbac_demo.model.SysLog;

public interface SysLogService {

    public void addLog(SysLog log);

    public void deleteLog(Integer logId);

    public Page<SysLog> listSysLog(Integer page, Integer limit, String str);

}
