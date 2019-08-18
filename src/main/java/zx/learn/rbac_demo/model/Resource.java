package zx.learn.rbac_demo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Resource {

    /**
     * 资源ID
     */
    Integer resourceId;

    /**
     * 资源名称
     */
    String resourceName;
    /**
     * 父级资源ID
     */
    Integer parentId;
    /**
     * 资源类型
     * 有页面 查看 编辑 删除
     */
    String type;
    /**
     * 资源 URL
     */
    String url;
    /**
     * 权限，这个字段没想好怎么用，
     * 权限做成父子层级 例如 user:add user:delete user:update user:*  setting:resource:add ??
     * 还是怎么使用，，用这个来做权限在系统内部的标识？？？
     * 以此解耦 权限URL和具体的权限的问题 那么使用id来确认不是一样的么。为什么需要这个字段？？
     * 暂时留着，看看以后有啥用
     * 一级权限名：二级权限名 @ 用户组 ？？？
     *
     * 想到用法了，查看 编辑权限 以用户的小组作为区分，每个权限加上小组名，如果为all，则小组名使用*代替，
     * 每个用户默认拥有自己小组的查看，编辑权限；拥有其他小组的查看权限。
     */
    String permission;

}
