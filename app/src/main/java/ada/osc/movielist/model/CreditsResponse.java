package ada.osc.movielist.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by avukelic on 25-Jun-18.
 */
public class CreditsResponse {
    @Expose
    @SerializedName("cast")
    private List<Credit> credits;

    public List<Credit> getCredits() {
        return credits;
    }

    public void setCredits(List<Credit> credits) {
        this.credits = credits;
    }
}
