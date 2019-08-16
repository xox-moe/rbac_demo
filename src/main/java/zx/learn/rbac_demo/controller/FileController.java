package zx.learn.rbac_demo.controller;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import zx.learn.rbac_demo.entity.ReturnBean;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: zx
 * Date: 2019/8/16
 * Time: 13:19
 * Description:
 */
@Controller
@Slf4j
@RequestMapping("file")
public class FileController {


    @Value("${file.upload.path}")
    String filePath;


    //    使用 HTTPServletRequest 作为参数
    @RequestMapping("uploadFile")
    @ResponseBody
    public ReturnBean testFileUpdate(HttpServletRequest request) {
        boolean flag = false;
        MultipartHttpServletRequest mreq = null;

        if (request instanceof MultipartHttpServletRequest) {
            mreq = (MultipartHttpServletRequest) request;
        } else {
            return ReturnBean.getFailed("失败");
        }
        MultipartFile mFile = mreq.getFile("file");
        assert mFile != null;
        String fileName = mFile.getName();
        File file = new File(fileName);
        try {
//            保存文件
            mFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
            return ReturnBean.getFailed("失败");

        }
        return ReturnBean.getSuccess("成功", file.getPath(), 0);

    }

    @RequestMapping("uploadMultipartFile")
    @ResponseBody
    public ReturnBean uploadMultipartFile(@RequestParam("file") MultipartFile file, Integer userId) {

        ModelAndView mv = new ModelAndView();
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        String endFix = fileName.substring(fileName.lastIndexOf('.'), fileName.length());
        String uuid = UUID.randomUUID().toString();
        fileName = uuid + endFix;
        String path = filePath+"head/";
        File dest = new File(path, fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(new File(path + File.separator + fileName));
        } catch (IOException e) {
            e.printStackTrace();
            mv.addObject("error", "上传失败");
            mv.setViewName("error");
            return ReturnBean.getFailed("失败");
        }
        String resultFilePath = "/images/head/" + fileName;
        return ReturnBean.getSuccess("成功", resultFilePath, 0);
    }


}
