package ada.osc.movielist.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by avukelic on 25-Jun-18.
 */
public class Video {
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("key")
    private String key;
    @Expose
    @SerializedName("type")
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
