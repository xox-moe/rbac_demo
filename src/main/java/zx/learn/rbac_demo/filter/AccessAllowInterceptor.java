package zx.learn.rbac_demo.filter;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import zx.learn.rbac_demo.entity.Resource;
import zx.learn.rbac_demo.entity.User;
import zx.learn.rbac_demo.util.CacheSingleton;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: zx
 * Date: 2019/8/6
 * Time: 16:11
 * Description:
 */

@Slf4j
public class AccessAllowInterceptor implements HandlerInterceptor {

    @Autowired
    CacheSingleton cache;

    public static Set<String> allowList = new HashSet<String>();

    static {
        allowList.add("/login");
    }

    @Override//在一个请求进入Controller层方法执行前执行这个方法
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //在这里可以对参数做一些预处理和做一些验证

        //上下文路径
        String ctxPath = request.getContextPath();
        // 请求的url
        String url = request.getRequestURI();
        // 相对路径
        String subUrl = url.substring(ctxPath.length() + 1);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("/login");
            return false;
        }

        HashMap map = cache.getResourceByUserId(user.getUserId());
        Boolean ifPermit = hasPermission(subUrl, (List<Resource>) map.get("resourceList"));

        return ifPermit;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
        log.info("请求结束执行");
    }


    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

        log.info("视图渲染完成后才执行");

    }


    /**
     * 根据 URL 和 资源 进行判断 是否放行。
     *
     * @param url
     * @param resourceList
     * @return
     */
    public Boolean hasPermission(String url, List<Resource> resourceList) {
        return true;
    }

}
