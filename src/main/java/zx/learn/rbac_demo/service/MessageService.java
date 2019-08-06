package zx.learn.rbac_demo.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageRowBounds;
import org.springframework.stereotype.Service;
import zx.learn.rbac_demo.entity.Message;


public interface MessageService {


    public void addMessage(Message message);

    public void deleteMessageById(Integer messageId);

    public void updateMessage(Message message);

    public Page<Message> listMessageByUserName(String userName, Integer page, Integer limit);

    public Message getMessageById(Integer id);
}
