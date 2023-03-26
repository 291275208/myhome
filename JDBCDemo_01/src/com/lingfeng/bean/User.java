package com.lingfeng.bean;

/**
 * Description:
 *
 * @Author: 领风
 * @Create: 2023/2/11 -  21:28
 * @Version: V1.0
 */
public class User {

    private int user_id;
    private String account;
    private String password;
    private String nickname;

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }

    public User(int user_id, String account, String password, String nickname) {
        this.user_id = user_id;
        this.account = account;
        this.password = password;
        this.nickname = nickname;
    }

    public User() {
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
