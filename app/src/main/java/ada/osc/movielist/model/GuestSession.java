package ada.osc.movielist.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by avukelic on 09-Jul-18.
 */
public class GuestSession {
    @Expose
    @SerializedName("guest_session_id")
    private String session;

    public GuestSession() {
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
