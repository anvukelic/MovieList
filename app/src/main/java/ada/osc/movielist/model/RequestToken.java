package ada.osc.movielist.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by avukelic on 09-Jul-18.
 */
public class RequestToken {

    @Expose
    @SerializedName("request_token")
    private String token;

    public RequestToken() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
