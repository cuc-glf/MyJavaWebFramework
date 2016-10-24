package tech.gaolinfeng.chat.entity;

/**
 * Created by gaolf on 16/10/24.
 */
public class ClientMessageId {

    private int id;
    private int userId;

    public ClientMessageId() {

    }

    public ClientMessageId(int id, int userId) {
        this.id = id;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
