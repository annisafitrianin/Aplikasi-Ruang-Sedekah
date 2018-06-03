package ap.annisafitriani.ruangsedekah.Model;

/**
 * Created by Hp on 6/3/2018.
 */

public class Member {

    public String username, email, nohp, password, userId;

    public Member (String email, String password, String username, String nohp, String userId) {

        this.email = email;
        this.password = password;
        this.username = username;
        this.nohp = nohp;
        this.userId = userId;
    }
    public Member(){

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getNohp() {
        return nohp;
    }

    public String getPassword() {
        return password;
    }

    public String getUserId() {
        return userId;
    }
}
