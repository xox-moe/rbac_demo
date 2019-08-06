//package zx.learn.rbac_demo.filter;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.lang.Nullable;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//import zx.learn.rbac_demo.entity.User;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.util.ArrayList;
//import java.util.List;
//
//@Slf4j
//@Component
//public class LoginInterceptor implements HandlerInterceptor {
//
//    public static List<String> allowList;
//
//    static {
//        List<String> list = new ArrayList<>();
//        list.add("css/");
//        list.add("js/");
//        list.add("fonts/");
//        list.add("login");
//        list.add("error");
//        list.add("register");
//        list.add("passwordError");
////        list.add("");
//        allowList = list;
//    }
//
//    @Override//在一个请求进入Controller层方法执行前执行这个方法
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        //在这里可以对参数做一些预处理和做一些验证
//
//        //上下文路径
//        String ctxPath = request.getContextPath();
//        // 请求的url
//        String url = request.getRequestURI();
//        // 相对路径
//        String subUrl = url.substring(ctxPath.length() + 1);
//
//        log.info("对 请求路径： " + url + " 进行匹配，是否拦截");
//
//        if (subUrl.equals("")) {
//            log.info("放行 空字符串");
//            return true;
//        }
//
//        for (String str : allowList) {
//            if (subUrl.startsWith(str)) {
//                log.info("放行");
//                return true;
//            }
//        }
//
//        HttpSession session = request.getSession();
//        User user = (User) session.getAttribute("user");
//        if (user == null) {
//            log.info("拦截 session 中 session 为空，拦截掉，跳转login");
////            request.getRequestDispatcher("/login").forward(request, response);
//            response.sendRedirect("/login");
//            return false;
//        } else {
//            log.info("放行 session 中 session 不为空，放行，跳转login");
//            return true;
//        }
////        return true;//方法给予执行，就是允许controller的方法进行执行
//        //false 不允许，可以在这之前在reponse中编写返回的结果
//
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
//                           @Nullable ModelAndView modelAndView) throws Exception {
//        log.info("请求结束执行");
//    }
//
//
//    @Override
//    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
//
//        log.info("视图渲染完成后才执行");
//
//    }
//}
