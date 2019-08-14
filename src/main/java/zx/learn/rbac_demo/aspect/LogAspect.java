package zx.learn.rbac_demo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import zx.learn.rbac_demo.annotation.SysLogs;
import zx.learn.rbac_demo.entity.SysLog;
import zx.learn.rbac_demo.entity.User;
import zx.learn.rbac_demo.service.SysLogService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: zx
 * Date: 2019/8/14
 * Time: 11:15
 * Description:
 */

@Aspect
@Slf4j
public class LogAspect {


    @Autowired
    SysLogService logService;

    @Pointcut("execution(* zx.learn.rbac_demo.controller.*.*(..))")
    public void logPoint() {
    }

    @Pointcut("@annotation(zx.learn.rbac_demo.annotation.SysLogs)")
    public void logAn() {
    }


//    @Before("") 之前
//    @After("")  返回或者抛出异常之后 执行
//    @AfterReturning("") 返回之后
//    @AfterThrowing("")  抛出异常之后

//    @Before("logPoint()")
//    public void beforeExecute(JoinPoint joinPoint){
//        log.info("执行了切点前置方法");
//        log.info(joinPoint.toLongString());
//
//        return;
//    }
//
//    @After("logPoint()")
//    public void afterExecute(){
//
//    }


    @Around("logAn()")
    public Object aroundExecuteAn(ProceedingJoinPoint pjp) {
        SysLog sysLog = new SysLog();

        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        sysLog.setMethodName(method.getName());
        if (method.isAnnotationPresent(SysLogs.class)) {
            //获取方法上注解中表明的权限
            SysLogs sysLogs = method.getAnnotation(SysLogs.class);
            log.debug("操作名称：" + sysLogs.name());
            log.debug("操作类型：" + sysLogs.type());
            sysLog.setActionName(sysLogs.name());
            sysLog.setActionType(sysLogs.type());
        }
        log.info("环绕前置");
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        sysLog.setIp(getClientIp(attr.getRequest()));
        sysLog.setUrl(attr.getRequest().getRequestURL().toString());
        HttpSession session = attr.getRequest().getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                log.debug("Session 中的user" + user.toString());
                log.debug("用户ID：" + user.getUserId());
                log.debug("用户名：" + user.getUserName());
                sysLog.setUserId(user.getUserId());
                sysLog.setUserName(user.getUserName());
            }
        } else {
            sysLog.setUserId(-1);
            sysLog.setUserName("无用户");
        }
        Object[] args = pjp.getArgs();
        if (args.length > 0) {
            sysLog.setArgs(Arrays.toString(args).length() > 200 ? "数据过长，不记录" : Arrays.toString(args));
        }
        for (Object arg : args) {
            try {
                log.debug("参数：" + (arg == null ? "" : arg.toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.info(pjp.toLongString());
        try {
            long startTime = System.currentTimeMillis();
            Object ret = pjp.proceed();
            long endTime = System.currentTimeMillis();
            log.debug("耗时(毫秒)： " + (endTime - startTime));
            log.debug("返回类型： " + ret.getClass().toString());
            log.debug("返回值： " + ret.toString());
            log.debug("环绕后置");

            sysLog.setTimeUse((int) (endTime - startTime));
            sysLog.setReturnResult(ret.toString().length() > 200 ? "数据过长，不记录" : ret.toString());
            sysLog.setCreateDate(LocalDateTime.now());

            log.debug(sysLog.toString());
            sysLog.setIfSuccess(true);
            logService.addLog(sysLog);
            return ret;
        } catch (Throwable throwable) {
            log.info("环绕抛出异常");
            sysLog.setIfSuccess(false);
            logService.addLog(sysLog);
            throwable.printStackTrace();
            log.info(sysLog.toString());
        }
        return "error";
    }


    public static SysLog newSysLog(HttpServletRequest request) {


        return null;
    }


    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || "".equals(ip.trim()) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknow".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if ("0.0.0.0".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip) || "localhost".equals(ip) || "127.0.0.1".equals(ip)) {
            ip = "127.0.0.1";
        }
        return ip;
    }

}
