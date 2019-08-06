package zx.learn.rbac_demo.controller;


import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import zx.learn.rbac_demo.entity.Message;
import zx.learn.rbac_demo.entity.ReturnBean;
import zx.learn.rbac_demo.entity.User;
import zx.learn.rbac_demo.service.MessageService;

import java.time.LocalDateTime;

@Controller
@RequestMapping("message")
@SessionAttributes(names = {"user"})
public class MessageController {

    @Autowired
    MessageService messageService;


    /**
     * 根据用户的请求新增留言
     *
     * @param title  标题
     * @param detail 正文详情
     * @param user   用户
     * @return 回到 home 页面
     */
    @RequestMapping("addMessage")
    public String addMessage(String title, String detail, @SessionAttribute User user) {
        Message message = new Message();
        message.setTitle(title);
        message.setDetail(detail);
        message.setCreateTime(LocalDateTime.now());
        message.setEditTime(LocalDateTime.now());
        message.setUserId(user.getUserId());
        messageService.addMessage(message);
        return "redirect:/messageList";
    }

    @RequestMapping("deleteMessage")
    public String deleteMessage(Integer id) {
        messageService.deleteMessageById(id);
        return "redirect:/messageList";
    }

//    @RequestMapping("updateMessage")
//    public String updateMessage(Message message) {
//        messageService.updateMessage(message);
//        return "home";
//    }

    @RequestMapping("editMessagePage")
    public String editMessagePage(Model model,Integer id){
        Message message = messageService.getMessageById(id);
        model.addAttribute("message", message);
        model.addAttribute("edit", true);
        return "newMessage";
    }

    @RequestMapping("editMessage")
    public String editMessage(Model model, Message message){
        message.setCreateTime(null);
        message.setEditTime(LocalDateTime.now());
        messageService.updateMessage(message);
        return "redirect:/messageList";
    }

    @RequestMapping("listMessageByUserName")
    @ResponseBody
    public ReturnBean listMessageByUserName(@RequestParam(required = false, defaultValue = "") String userName,
                                            @SessionAttribute User user,
                                            @RequestParam(required = false,defaultValue = "1") Integer page,
                                            @RequestParam(required = false,defaultValue = "5") Integer limit) {

        Page<Message> messageList = messageService.listMessageByUserName(userName.trim(), page, limit);
        return ReturnBean.getSuccess("success",messageList,messageList.getTotal());
    }


}
