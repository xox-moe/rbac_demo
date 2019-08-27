package zx.learn.rbac_demo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import zx.learn.rbac_demo.annotation.SysLogs;
import zx.learn.rbac_demo.model.ReturnBean;
import zx.learn.rbac_demo.model.SysLog;
import zx.learn.rbac_demo.model.User;
import zx.learn.rbac_demo.service.SysLogService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.LocalDateTime;

import static java.util.concurrent.TimeUnit.NANOSECONDS;


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

    @Around("logAn()")
    public Object aroundExecuteAn(ProceedingJoinPoint pjp) {
        SysLog sysLog = new SysLog();

        //通过反射获取注解上的 操作名称 和 类型
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

//        通过request获取session 得到当前请求用户的ID
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        sysLog.setIp(getClientIp(attr.getRequest()));
        sysLog.setUrl(attr.getRequest().getRequestURL().toString());
        HttpSession session = attr.getRequest().getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                sysLog.setUserId(user.getUserId());
                sysLog.setUserName(user.getUserName());
            } else {
                sysLog.setUserId(-1);
                sysLog.setUserName("无用户");
            }
        } else {
            sysLog.setUserId(-1);
            sysLog.setUserName("无用户");
        }

        Object[] args = pjp.getArgs();

//        通过反射，JoinPoint 的接口，获取参数名称以及参数值，同时过滤 Model 和 Session 和 password 等内容
        Parameter[] parameters = method.getParameters();
        StringBuilder argsStr = new StringBuilder();
        for (int i = 0; i < parameters.length; i++) {
            if ((args[i] instanceof Model) || (args[i] instanceof HttpSession) || (args[i] instanceof HttpServletRequest || args[i] instanceof MultipartFile))
                continue;
            if (!(parameters[i].getName().contains("assword"))) {
//                argsStr.append(parameters[i].getName()).append(":").append(args[i] == null ? "null" : args[i].toString()).append(" , ");
                argsStr.append(parameters[i].getName()).append(":").append(args[i] == null ? "null" : args[i]).append(" , ");
            } else {
                argsStr.append(parameters[i].getName()).append(":").append("<Mask>").append("  ");
            }
        }

        log.info("args: " + argsStr.toString());
        String arsString = argsStr.toString();
        arsString = arsString.length() > 3 ? arsString.substring(0, arsString.length() - 2) : arsString;
        arsString = arsString.length() > 200 ? arsString.substring(0, 199) : arsString;
        sysLog.setArgs(arsString);
        log.info(pjp.toLongString());

//        执行连接点的函数，得到耗时 以及返回值 是否成功
        try {
            System.currentTimeMillis();
            long startTime = System.nanoTime();
            Object ret = pjp.proceed();
            long endTime = System.nanoTime();
            long timeUse = NANOSECONDS.toMillis(endTime - startTime);
            log.debug("耗时(毫秒)： " + timeUse);
            log.debug("返回类型： " + ret.getClass().toString());
            log.debug("返回值： " + ret.toString());
            log.debug("环绕后置");


            sysLog.setTimeUse(timeUse);
            sysLog.setReturnResult(ret.toString().length() > 200 ? "数据过长，不记录" : ret.toString());
            sysLog.setCreateDate(LocalDateTime.now());

            log.debug(sysLog.toString());
            if (ret instanceof ReturnBean) {
                sysLog.setIfSuccess(((ReturnBean) ret).status == 1);
            }
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
