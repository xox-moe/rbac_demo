package zx.learn.rbac_demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zx.learn.rbac_demo.dao.HeaderMapper;
import zx.learn.rbac_demo.model.Image;
import zx.learn.rbac_demo.service.HeaderService;

import java.util.List;

@Service
public class HeaderServiceImpl implements HeaderService {

    @Autowired
    HeaderMapper mapper;

    @Override
    public Integer saveImage(String imgUrl) {
        Image image = new Image();
        image.setImgUrl(imgUrl);
        mapper.addImg(image);
        return image.getId();

    }

    @Override
    public Boolean saveUserHeader(Integer userId, Integer imgId) {
        mapper.disableAllHeaderForUser(userId);
        return mapper.addUserHeader(userId, imgId);
    }

    @Override
    public Boolean selectThisImg(Integer userId,Integer imgId) {
        mapper.disableAllHeaderForUser(userId);
        return mapper.selectUserHeader(userId, imgId);
    }

    @Override
    public String getUrlById(Integer imgId) {
        return mapper.getUrlById(imgId);
    }

    @Override
    public List<Image> listHeadByUserId(Integer userId) {
        return mapper.listHeadByUserId(userId);
    }

}
