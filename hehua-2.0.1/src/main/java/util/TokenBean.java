package util;

/**
 * Created by Administrator on 2017/7/11.
 * IntelliJ IDEA 2017 of gzcss
 */
public class TokenBean {
    public String accessToken;
    public String key;
    public String signture;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSignture() {
        return signture;
    }

    public void setSignture(String signture) {
        this.signture = signture;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long timestamp;

}
