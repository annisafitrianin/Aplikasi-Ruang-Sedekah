package ap.annisafitriani.ruangsedekah.Model;

import java.io.Serializable;

/**
 * Created by Hp on 5/3/2018.
 */

public class Member {

    public String username, email, nohp, password, userId;

    public Member(String email, String password, String username, String nohp, String userId) {

        this.email = email;
        this.password = password;
        this.username = username;
        this.nohp = nohp;
        this.userId = userId;
    }
    public Member(){

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}