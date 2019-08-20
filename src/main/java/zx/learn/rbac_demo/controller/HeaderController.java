package zx.learn.rbac_demo.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import zx.learn.rbac_demo.annotation.SysLogs;
import zx.learn.rbac_demo.model.Image;
import zx.learn.rbac_demo.model.ReturnBean;
import zx.learn.rbac_demo.model.User;
import zx.learn.rbac_demo.service.HeaderService;
import zx.learn.rbac_demo.service.UserService;
import zx.learn.rbac_demo.util.FileUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: zx
 * Date: 2019/8/16
 * Time: 13:19
 * Description:
 */
@Controller
@Slf4j
@RequestMapping("head")
public class HeaderController {

    @Value("${file.upload.path}")
    String rootPath;

    @Autowired
    FileUtils fileUtil;

    @Autowired
    HeaderService headerService;

    @Autowired
    UserService userService;

    @Value("${file.upload.relative}")
    private String fileRelativePath;

    @Autowired
    HttpSession session;


    //    使用 HTTPServletRequest 作为参数
//    @RequestMapping("uploadFile")
//    @ResponseBody
//    public ReturnBean testFileUpdate(HttpServletRequest request) {
//        boolean flag = false;
//
//        if (request instanceof MultipartHttpServletRequest) {
//             MultipartHttpServletRequest mreq = (MultipartHttpServletRequest) request;
//        } else {
//            return ReturnBean.getFailed("失败");
//        }
//        MultipartFile mFile = mreq.getFile("file");
//        assert mFile != null;
//        String fileName = mFile.getName();
//        File file = new File(fileName);
//        try {
////            保存文件
//            mFile.transferTo(file);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ReturnBean.getFailed("失败");
//
//        }
//        return ReturnBean.getSuccess("成功", file.getPath(), 0);
//
//    }

    @RequestMapping("uploadHeader")
    @SysLogs(name = "上传头像", type = "新增")
    public String uploadHeader(@RequestParam("file") MultipartFile file, @SessionAttribute("user") User user, HttpSession session) {

        ModelAndView mv = new ModelAndView();

        String path = "\\image\\" + fileUtil.saveFile(file, "head");
        Integer id = headerService.saveImage(path);
        headerService.saveUserHeader(user.getUserId(), id);
        reloadHeaderImg();

        return "user/myInfo";

    }


    @RequestMapping("historyHead")
    @SysLogs(name = "查看历史头像", type = "查询")
    public String historyHead(@SessionAttribute("userId") Integer userId, Model model) {
        List<Image> imageList = headerService.listHeadByUserId(userId);
        String oldImgUrl = ((User) session.getAttribute("user")).getHeaderUrl();

        imageList = imageList.parallelStream().filter(img -> !oldImgUrl.equals(img.getImgUrl())).collect(Collectors.toList());

        model.addAttribute("imgList", imageList);
        return "user/historyHead";
    }

    @RequestMapping("deleteHeader")
    @SysLogs(name = "删除头像", type = "删除")
    public String deleteHeader(@SessionAttribute("userId") Integer userId, Integer imgId, Model model) {

        List<Image> imageList = headerService.listHeadByUserId(userId);

        if (imageList.parallelStream().anyMatch(image -> image.getId().equals(imgId))) {
            headerService.deleteHeader(imgId);
        } else {
            model.addAttribute("error", "请删除自己的头像");
            return "common/error";
        }
        imageList = headerService.listHeadByUserId(userId);
        String oldImgUrl = ((User) session.getAttribute("user")).getHeaderUrl();
        imageList = imageList.parallelStream().filter(img -> !oldImgUrl.equals(img.getImgUrl())).collect(Collectors.toList());

        model.addAttribute("imgList", imageList);
        return "user/historyHead";
    }

    @RequestMapping("selectHeader")
    @SysLogs(name = "选择头像", type = "修改")
    public String selectHeaderByImgId(Integer imgId, @SessionAttribute("userId") Integer userId,Model model) {
        List<Image> imageList = headerService.listHeadByUserId(userId);
        boolean ifCouldChange = false;

        if (imageList.parallelStream().map(Image::getId).anyMatch(imgId::equals)) {
            ifCouldChange = true;
        }

        if (ifCouldChange) {
            headerService.selectThisImg(userId, imgId);
            reloadHeaderImg();
        } else {
            model.addAttribute("error", "请选择自己的头像");
            return "common/error";
        }

        return "user/myInfo";
    }

//
//    @RequestMapping("deleteImgById")
//    public ReturnBean deleteImgById(){
//        headerService.
//    }


    private void reloadHeaderImg() {

        User user = (User) session.getAttribute("user");
        user = userService.getUserInfoByUserName(user.getUserName());
        session.setAttribute("user", user);
    }
}
