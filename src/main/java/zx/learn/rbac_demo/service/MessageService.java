package zx.learn.rbac_demo.service;

import com.github.pagehelper.Page;
import zx.learn.rbac_demo.model.Message;


public interface MessageService {


    public void addMessage(Message message);

    public void deleteMessageById(Integer messageId);

    public void updateMessage(Message message);

    public Page<Message> listMessageByUserName(String userName, Integer page, Integer limit);

    public Message getMessageById(Integer id);
}
