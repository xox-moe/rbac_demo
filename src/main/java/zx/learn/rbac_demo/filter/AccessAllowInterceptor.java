package zx.learn.rbac_demo.filter;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import zx.learn.rbac_demo.entity.Group;
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
        allowList.add("/css/");
        allowList.add("/js/");
        allowList.add("/static/");
        allowList.add("/fonts/");
        allowList.add("/login");
        allowList.add("/noPermission.html");
        allowList.add("/logout");
        allowList.add("/error");
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

        log.info("对 请求路径： " + url + " 进行匹配，是否拦截");

        if (url.endsWith(".html")) {
            log.info("因为 " + subUrl + "以  .html  结尾，放行");
            return true;
        }

        for (String s : allowList) {
//            log.info("判断 " + url + " 与 " + s + " 的关系中。。 ");
            if (subUrl.startsWith(s)) {
                log.info("因为 " + subUrl + "以 " + s + " 开头，放行");
                return true;
            }
        }

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            log.info("用户为空，重定向到登录页面");
            response.sendRedirect("/login");
            return false;
        }
        //管理员直接放行，方便调试。
        if(user.getUserName().equals("admin"))
            return true;

        HashMap map = cache.getResourceByUserId(user.getUserId());
        Boolean ifPermit = hasPermission(subUrl, map);
        if (!ifPermit) {
            response.sendRedirect("/common/noPermission.html");
        }
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
     * 前提： 不同的权限，前缀尽量不要重复， 前缀有包含关系  例如  /msg  包含 /msg/delete /msg/edit 等权限
     * 需要使用用户组概念的权限需要以 "group" + group.getGroupId() 结尾 方便判断是否放行
     *
     * @param url URL
     * @param map 资源
     * @return
     */
    public Boolean hasPermission(String url, HashMap map) {

        log.info("Resource匹配： 对 " + url + " 进行权限判断 是否进行拦截");

        List<Resource> resourceList = (List<Resource>) map.get("resourceList");
//        对资源进行扫描？ 然后进行匹配??
        for (Resource resource : resourceList) {
            //首先，URL前缀要匹配 这里就涉及到编辑权限 与 用户组的关系。
            //如果是以 指定的为前缀 打算放行
            if (url.startsWith(resource.getUrl())) {
                //如果 不是 edit delete 等权限 ， 直接放行          到时候写个枚举类
                //这里使用 资源的 type 区分一下 ajax 请求 和 其他类型的请求，
//                if (!(resource.getType().equals("edit") || resource.getType().equals("delete"))) {
                    log.info("有权限，且权限类型不是修改类型，可访问，放行");
                    return true;
//                } else {
//                    //是 edit delete 要进行权限判断。 首先是用户是否有删除权限，再判断用户是不是该用户组。
//                    log.info("有权限，但是权限类型是修改了类型，进一步判断");
//                    List<Group> groupList = (List<Group>) map.get("groupList");
//                    //如果用户是该用户组的成员 就放行
//                    for (Group group : groupList) {
//                        if (url.endsWith("group" + group.getGroupId())) {
//                            log.info("有权限，但是权限类型是修改了类型，进一步判断 有修改权限，放行");
//                            return true;
//                        }
//                    }
//                }
            }
        }

        return false;
    }

}
