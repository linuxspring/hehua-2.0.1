package myshiro;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2017/7/28.
 * IntelliJ IDEA 2017 of gzcss
 */
public class UserDAO {
    private JdbcTemplate jt;
    public void setJt(JdbcTemplate jt) {
        this.jt = jt;
    }

    public Set<String> getRoles(String username){
        return new HashSet();
    }

    public Set<String> getPrimary(String username) {
        return new HashSet();
    }
}
