package zx.learn.rbac_demo.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import zx.learn.rbac_demo.model.Image;

import java.util.List;

@Mapper
public interface HeaderMapper {

    int addImg(Image image);

    String getUrlById(@Param("imgId") Integer imgId);

    Boolean addUserHeader(@Param("userId") Integer userId, @Param("imgId") Integer imgId);

    void disableAllHeaderForUser(@Param("userId") Integer userId);

    Boolean selectUserHeader(@Param("userId") Integer userId, @Param("imgId") Integer imgId);

    List<Image> listHeadByUserId(Integer userId);

    void deleteHeader(Integer imgId);


    void deleteUserHeader(Integer imgId);
}
