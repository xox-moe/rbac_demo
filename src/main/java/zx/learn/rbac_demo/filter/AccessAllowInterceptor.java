package zx.learn.rbac_demo.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import zx.learn.rbac_demo.model.Resource;
import zx.learn.rbac_demo.model.User;
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

    public static Set<String> refreshPermissionList = new HashSet<String>();

    static {
        allowList.add("/css/");
        allowList.add("/js/");
        allowList.add("/static/");
        allowList.add("/fonts/");
        allowList.add("/login");
        allowList.add("/common/noPermission.html");
        allowList.add("/common/index.html");
        allowList.add("/logout");
        allowList.add("/error");

        refreshPermissionList.add("/resource/allocateResourceForRole");
        refreshPermissionList.add("/role/allocateRoleForUser");
    }

    @Override//在一个请求进入Controller层方法执行前执行这个方法
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //在这里可以对参数做一些预处理和做一些验证

        //上下文路径
        String ctxPath = request.getContextPath();
        // 请求的url
        String url = request.getRequestURI();
        // 相对路径
        String subUrl = url.substring(ctxPath.length());

        log.debug("对 请求路径： " + url + " 进行匹配，是否拦截");

        if (allowList.parallelStream().anyMatch(subUrl::startsWith)) {
            log.debug("因为 " + subUrl + " 在直接放行列表中，放行");
            return true;
        }

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            log.debug("用户为空，重定向到登录页面");
            response.sendRedirect("/login");
            return false;
        }

        //管理员直接放行，方便调试。
        if (user.getUserName().equals("admin"))
            return true;

        HashMap map = cache.getResourceByUserId(user.getUserId());
        Boolean ifPermit = hasPermission(subUrl, map);
        if (!ifPermit) {
            response.sendRedirect("/common/noPermission.html");
        } else {
            log.debug("根据系统判断，用户有访问 " + subUrl + " 的权限，放行");
        }
        return ifPermit;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {

        //在这里对当前用户进行权限刷新
        //上下文路径
        String ctxPath = request.getContextPath();
        // 请求的url
        String url = request.getRequestURI();
        // 相对路径
        String subUrl = url.substring(ctxPath.length());

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (refreshPermissionList.stream().anyMatch(subUrl::startsWith)) {
            cache.reloadResources(user.getUserId());
            log.debug("与刷新列表匹配 进行权限刷新");
        }

    }


    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        log.debug("视图渲染完成后才执行");
    }


    /**
     * 根据 URL 和 资源 进行判断 是否放行。
     * 前提： 不同的权限，前缀尽量不要重复， 前缀有包含关系  例如  /msg  包含 /msg/delete /msg/edit 等权限
     *
     * @param url URL
     * @param map 资源
     * @return
     */
    public Boolean hasPermission(String url, HashMap map) {

        log.debug("Resource匹配： 对 " + url + " 进行权限判断 是否进行拦截");
        List<Resource> resourceList = (List<Resource>) map.get("resourceList");
        log.debug("对" + url + "进行资源匹配扫描");
        return (resourceList.parallelStream().map(Resource::getUrl).anyMatch(url::startsWith));
    }

}
