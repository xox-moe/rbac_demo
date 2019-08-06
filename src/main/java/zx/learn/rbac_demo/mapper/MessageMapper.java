package zx.learn.rbac_demo.mapper;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageRowBounds;
import org.apache.ibatis.annotations.Mapper;
import zx.learn.rbac_demo.entity.Message;

@Mapper
public interface MessageMapper {
    void addMessage(Message message);

    Page<Message> listMessageByUserName(PageRowBounds pageRowBounds, String userName);

    void updateMessage(Message message);

    void deleteMessageById(Integer messageId);

    Message getMessageById(Integer id);
}
