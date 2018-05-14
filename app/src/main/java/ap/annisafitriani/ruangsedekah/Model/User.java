package ap.annisafitriani.ruangsedekah.Model;

/**
 * Created by Hp on 5/3/2018.
 */

public class User {


    private String username, email, nohp, password;

    public User (String email, String password, String  username, String nohp){

        this.email = email;
        this.password = password;
        this.username = username;
        this.nohp = nohp;

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
}