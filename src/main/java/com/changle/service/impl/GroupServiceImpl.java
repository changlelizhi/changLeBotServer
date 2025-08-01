package com.changle.service.impl;

import com.changle.bot.botenum.BotInfo;
import com.changle.bot.constant.ChangLeBotConstant;
import com.changle.bot.exception.BotBaseException;
import com.changle.bot.exception.BotError;
import com.changle.entity.Group;
import com.changle.mapper.GroupMapper;
import com.changle.service.DeleteMsgService;
import com.changle.service.GroupService;
import com.changle.service.SendMsgService;
import com.changle.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberAdministrator;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author : 长乐
 * @package : com.changle.service.impl
 * @project : changLeBotServer
 * @name : GroupServiceImpl
 * @Date : 2025/7/25
 * @Description :
 */
@Slf4j
@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private SendMsgService sendMsgService;

    @Autowired
    private DeleteMsgService deleteMsgService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateGroupDeleteStatus(String chatId, Integer groupStatusDelete, String botStatus) {
        groupMapper.updateGroupStatus(chatId, groupStatusDelete);
        stringRedisTemplate.opsForHash().putAll(chatId, Map.of(BotInfo.GROUP_STATUS.getInfo(), groupStatusDelete.toString(), BotInfo.BOT_STATUS.getInfo(), botStatus));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateGroupStatusNormal(String chatId, Integer groupStatusNormal, String botStatus) {
        groupMapper.updateGroupStatus(chatId, groupStatusNormal);
        stringRedisTemplate.opsForHash().putAll(chatId, Map.of(BotInfo.GROUP_STATUS.getInfo(), groupStatusNormal.toString(), BotInfo.BOT_STATUS.getInfo(), botStatus));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateGroupStatusDisable(String chatId, Integer groupStatusDisable, String botStatus) {
        groupMapper.updateGroupStatus(chatId, groupStatusDisable);
        stringRedisTemplate.opsForHash().putAll(chatId, Map.of(BotInfo.GROUP_STATUS.getInfo(), groupStatusDisable.toString(), BotInfo.BOT_STATUS.getInfo(), botStatus));
    }


    @Override
    public Integer queryGroupStatus(String chatId) {
        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries(chatId);
        String groupStatus = (String) entries.get(BotInfo.GROUP_STATUS.getInfo());
        if (StringUtils.isBlank(groupStatus)) {
            return ChangLeBotConstant.SQL_DEFAULT;
        }
        return Integer.parseInt(groupStatus);
    }

    /**
     * 机器人加入群组
     *
     * @param update
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void botJoinGroup(TelegramClient telegramClient, Update update) {
        String chatId = update.getMyChatMember().getChat().getId().toString();
        String chatName = update.getMyChatMember().getChat().getTitle();
        String chatType = update.getMyChatMember().getChat().getType();
        Integer groupStatus = queryGroupStatus(chatId);
        String text = "如果要使用本Bot，请设置bot为 <strong>管理员</strong>，并授予<strong>修改群组信息</strong> 和 <strong>删除消息</strong> 权限";
        try {
            if (ChangLeBotConstant.SQL_DEFAULT.equals(groupStatus)) {
                stringRedisTemplate.opsForHash().putAll(chatId, Map.of(BotInfo.GROUP_STATUS.getInfo(), ChangLeBotConstant.GROUP_STATUS_DISABLE.toString(),
                        BotInfo.BOT_STATUS.getInfo(), BotInfo.MEMBER.getInfo()));
                Group group = Group.builder().chatId(chatId).chatName(chatName).chatType(chatType).status(ChangLeBotConstant.GROUP_STATUS_DISABLE).createTime(LocalDateTime.now()).build();
                int insertGroup = groupMapper.insertGroup(group);
                if (insertGroup <= ChangLeBotConstant.SQL_DEFAULT) {
                    throw new RuntimeException("数据库插入失败");
                }
                sendMsgService.sendHtmlMsg(telegramClient, chatId, text);
                return;
            }
            if (groupStatus.equals(ChangLeBotConstant.GROUP_STATUS_DELETE) && botIsJoin(update)) {
                sendMsgService.sendHtmlMsg(telegramClient, chatId, text);
            }
        } catch (Exception e) {
            stringRedisTemplate.delete(chatId);
            throw new BotBaseException(BotError.BOT_JOIN_FAILED, e);
        }

    }

    /**
     * 机器人离开群组
     *
     * @param update
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void botLeaveGroup(Update update) {
        String chatId = update.getMyChatMember().getChat().getId().toString();
        try {
            stringRedisTemplate.opsForHash().putAll(chatId, Map.of(BotInfo.GROUP_STATUS.getInfo(), ChangLeBotConstant.GROUP_STATUS_DELETE.toString(),
                    BotInfo.BOT_STATUS.getInfo(), BotInfo.LEFT.getInfo()));
            groupMapper.updateGroupStatus(chatId, ChangLeBotConstant.GROUP_STATUS_DELETE);
        } catch (Exception e) {
            log.error("机器人离开群组异常", e);
            throw new BotBaseException(BotError.BOT_LEAVE_FAILED, e);
        }


    }

    /**
     * 用户加入群组
     *
     * @param update
     */
    @Override
    public void userJoinGroup(TelegramClient telegramClient, Update update) {
        String userName = UserUtils.combineUsername(update);
        String chatName = update.getMessage().getChat().getTitle();
        String chatId = update.getMessage().getChat().getId().toString();
        String userId = update.getMessage().getFrom().getId().toString();
        String text = String.format("欢迎用户:<a href=\"tg://user?id=%s\">%s</a> 加入 <b><u><i>%s</i></u></b>，爱你呦，宝贝💖", userId, userName, chatName);
        Message message = sendMsgService.sendHtmlMsg(telegramClient, chatId, text);
        if (message != null){
            deleteMsgService.deleteMsg(telegramClient, chatId, message);
        }
    }

    /**
     * 用户离开群组
     *
     * @param update
     */
    @Override
    public void userLeaveGroup(TelegramClient telegramClient, Update update) {
        User leftUser = update.getMessage().getLeftChatMember();
        String username = UserUtils.combineUsername(leftUser.getFirstName(), leftUser.getLastName());
        String text = String.format("用户:%s 离开了群组，再见啦",  username);
        Message message = sendMsgService.sendHtmlMsg(telegramClient, update.getMessage().getChat().getId().toString(), text);
        if (message != null){
            deleteMsgService.deleteMsg(telegramClient, update.getMessage().getChat().getId().toString(), message);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void botIsAdmin(TelegramClient telegramClient, Update update) {
        ChatMemberAdministrator administrator = (ChatMemberAdministrator) update.getMyChatMember().getNewChatMember();
        Boolean canChangeInfo = administrator.getCanChangeInfo();
        Boolean canDeleteMessages = administrator.getCanDeleteMessages();
        if (canChangeInfo && canDeleteMessages) {
            String chatId = update.getMyChatMember().getChat().getId().toString();
            try {
                //修改redis中存储的群组状态和状态
                stringRedisTemplate.opsForHash().putAll(chatId, Map.of(BotInfo.GROUP_STATUS.getInfo(), ChangLeBotConstant.GROUP_STATUS_NORMAL.toString(),
                        BotInfo.BOT_STATUS.getInfo(), BotInfo.BOT_IS_ADMIN.getInfo()));
                //修改MySQL中存储的群组状态
                groupMapper.updateGroupStatus(chatId, ChangLeBotConstant.GROUP_STATUS_NORMAL);
            } catch (Exception e) {
                throw new BotBaseException(BotError.BOT_SET_ADMIN_FAILED, e);
            }
        }
    }


    private static boolean botIsJoin(Update update) {
        return update.getMyChatMember().getNewChatMember().getStatus().equals(BotInfo.MEMBER.getInfo())
                && update.getMyChatMember().getNewChatMember().getUser().getIsBot()
                && update.getMyChatMember().getNewChatMember().getUser().getUserName().equals(BotInfo.BOT_NAME.getInfo());
    }
}
