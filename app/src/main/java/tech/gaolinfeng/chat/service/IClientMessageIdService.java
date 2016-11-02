package tech.gaolinfeng.chat.service;

/**
 * Created by gaolf on 16/10/24.
 * 纪录客户端发给Server的消息ID, 以及查询ID对应的消息纪录是否已经存在
 * 暂时不考虑int存不下消息id的情况, int可以存21亿以内的整数, 如果用户每天发1万条消息, 需要210000天, 也就是575年才会用完,
 * 在int存不下之前, 数据库应该首先会爆磁盘
 */
public interface IClientMessageIdService {
    /**
     * 检查id是否已经存在, 已经存在说明该消息已经被处理过了, 不应该再次处理, 直接告知客户端已处理即可
     */
    boolean checkIdExist(int id, int userId);

    /**
     * 添加id纪录, 该调用应该与处理对应消息的service调用处于同一个事务中, 目前这是由
     * {@link tech.gaolinfeng.chat.controller.ws.ChatMessageController}保证的,
     * 如果Service调用失败, 回滚Service相关操作的同时, 也应该将该id纪录回滚, 避免纪录了id但是实际上并没有处理对应的消息
     */
    void addId(int id, int userId);

    /**
     * 获取当前最大的消息id, 下次客户端发送的消息应该至少是这个id+1
     * @param userId 要发送消息的用户id
     * @return 当前最大的消息id
     */
    int getCurrentMessageId(int userId);
}
