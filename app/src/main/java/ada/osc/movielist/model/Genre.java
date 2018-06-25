package ada.osc.movielist.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by avukelic on 25-Jun-18.
 */
public class Genre {
    @Expose
    @SerializedName("name")
    private String genre;

    public String getGenre() {
        return genre;
    }
}
