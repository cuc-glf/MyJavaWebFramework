package tech.gaolinfeng.base.entity;

/**
 * Created by gaolf on 16/10/11.
 */
public class UserRole {

    private int id;
    private String role;
    /**
     * foreign key referencing {@link User#id}
     */
    private int userId;

    public UserRole() {

    }

    public UserRole(String role, int userId) {
        this.role = role;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", role='" + role + '\'' +
                ", userId=" + userId +
                '}';
    }
}
