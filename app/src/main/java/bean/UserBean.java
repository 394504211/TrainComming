package bean;

import java.io.Serializable;

import multitype.viewItem.Item;

/**
 * Created by Administrator on 2017/4/8.
 */
public class UserBean extends BaseResponse implements Serializable {
    private int id;
    private String name;
    private String avatar;
    private String large_avatar;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLarge_avatar() {
        return large_avatar;
    }

    public void setLarge_avatar(String large_avatar) {
        this.large_avatar = large_avatar;
    }
}
