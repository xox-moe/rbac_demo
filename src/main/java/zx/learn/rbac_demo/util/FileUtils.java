package zx.learn.rbac_demo.util;

import javafx.beans.value.ObservableNumberValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import zx.learn.rbac_demo.model.ReturnBean;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.UUID;


@Slf4j
@Component
public class FileUtils {

    @Autowired
    Environment environment ;



    private static String ROOT_PATH;

    private static FileUtils fileUtils;

//    @Value("${file.upload.path}")
//    public String rootPath;

    private FileUtils() {
        ROOT_PATH = "Z:\\Image\\";
//        assert ROOT_PATH != null;
        if (!ROOT_PATH.endsWith(File.separator)) {
            ROOT_PATH = ROOT_PATH + File.separator;
        }
    }

    public static FileUtils getInstance() {
        if (fileUtils == null) {
            fileUtils = new FileUtils();
        }
        return fileUtils;
    }

        public String saveFile (MultipartFile file, String folderName){

        String uuid = UUID.randomUUID().toString();
        String originalFileName = file.getOriginalFilename();
        assert originalFileName != null;
        String suffix = originalFileName.substring(originalFileName.lastIndexOf('.'));
        String filePath = ROOT_PATH + "\\" + folderName;
        String fileName = uuid + suffix;

        File dest = new File(filePath, fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(new File(filePath + File.separator + fileName));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return folderName + "\\" + fileName;
    }

    }
