package zx.learn.rbac_demo.service;

import zx.learn.rbac_demo.model.Image;

import java.util.List;

public interface HeaderService {

//    public String save

    /**
     * 保存图片 返回 ID
     * @param imgUrl 图片
     * @return 图片表 ID
     */
    public Integer saveImage(String imgUrl);

    /**
     * 建立图像与用户的关联
     * @param userId 用户ID
     * @param imgId  图像ID
     * @return 是否成功
     */
    public Boolean saveUserHeader(Integer userId,Integer imgId);

    /**
     * 通过图像ID选择头像
     * @param imgId 图像ID
     * @return 是否成功
     */
    public Boolean selectThisImg(Integer userId,Integer imgId);

    public String getUrlById(Integer imgId);

    List<Image> listHeadByUserId(Integer userId);

    void deleteHeader(Integer imgId);
}
