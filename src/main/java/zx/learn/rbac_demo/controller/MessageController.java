package zx.learn.rbac_demo.controller;


import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import zx.learn.rbac_demo.annotation.SysLogs;
import zx.learn.rbac_demo.model.Message;
import zx.learn.rbac_demo.model.ReturnBean;
import zx.learn.rbac_demo.model.User;
import zx.learn.rbac_demo.service.MessageService;

import java.time.LocalDateTime;

@Controller
@RequestMapping("message")
@SessionAttributes(names = {"user"})
public class MessageController {

    @Autowired
    MessageService messageService;


    @RequestMapping("addMessage.html")
    public String newMessage() {
        return "message/newMessage";
    }


    /**
     * 根据用户的请求新增留言
     *
     * @param title  标题
     * @param detail 正文详情
     * @param user   用户
     * @return 回到 home 页面
     */
    @RequestMapping("addMessage")
    @SysLogs(name = "新增留言", type = "新增&跳转")
    public String addMessage(String title, String detail, @SessionAttribute User user) {
        Message message = new Message();
        message.setTitle(title);
        message.setDetail(detail);
        message.setCreateTime(LocalDateTime.now());
        message.setEditTime(LocalDateTime.now());
        message.setUserId(user.getUserId());
        messageService.addMessage(message);
        return "redirect:/message/messageListTest.html";
    }

    @RequestMapping("deleteMessage")
    @ResponseBody
    @SysLogs(name = "删除留言", type = "删除")
    public ReturnBean deleteMessage(Integer id) {
        messageService.deleteMessageById(id);
        return ReturnBean.getSuccess("删除成功");
    }


    @RequestMapping("editMessagePage.html")
    @SysLogs(name = "修改留言页面", type = "查询&跳转")
    public String editMessagePage(Model model, Integer id) {
        Message message = messageService.getMessageById(id);
        model.addAttribute("message", message);
        model.addAttribute("edit", true);
        return "message/newMessage";
    }

    @RequestMapping("editMessage")
    @SysLogs(name = "编辑留言", type = "更新&跳转")
    public String editMessage(Model model, Message message) {
        message.setCreateTime(null);
        message.setEditTime(LocalDateTime.now());
        messageService.updateMessage(message);
        return "redirect:/message/messageList";
    }

    @RequestMapping("listMessageByUserName")
    @ResponseBody
    @SysLogs(name = "查询用户留言", type = "查询")
    public ReturnBean listMessageByUserName(@RequestParam(required = false, defaultValue = "") String userName,
                                            @SessionAttribute User user,
                                            @RequestParam(required = false, defaultValue = "1") Integer page,
                                            @RequestParam(required = false, defaultValue = "5") Integer limit) {

        Page<Message> messageList = messageService.listMessageByUserName(userName.trim(), page, limit);
        return ReturnBean.getSuccess("success", messageList, messageList.getTotal());
    }


}
