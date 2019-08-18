package zx.learn.rbac_demo.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageRowBounds;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zx.learn.rbac_demo.model.Message;
import zx.learn.rbac_demo.dao.MessageMapper;
import zx.learn.rbac_demo.service.MessageService;

/**
 * Created with IntelliJ IDEA.
 * User: zx
 * Date: 2019/8/6
 * Time: 16:09
 * Description:
 */
@Slf4j
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageMapper messageMapper;

    @Override
    public void addMessage(Message message) {
        messageMapper.addMessage(message);
    }

    @Override
    public void deleteMessageById(Integer messageId) {
        messageMapper.deleteMessageById(messageId);
    }

    @Override
    public void updateMessage(Message message) {
        messageMapper.updateMessage(message);
    }

    @Override
    public Page<Message> listMessageByUserName(String userName, Integer page, Integer limit) {
        PageRowBounds pageRowBounds = new PageRowBounds((page - 1) * limit, limit);
        Page<Message> messagePage = messageMapper.listMessageByUserName(pageRowBounds, userName);
        PageInfo info = new PageInfo<>(messagePage.getResult());
        log.info(info.toString());
        return messagePage;
    }

    @Override
    public Message getMessageById(Integer id) {
        return messageMapper.getMessageById(id);
    }
}
