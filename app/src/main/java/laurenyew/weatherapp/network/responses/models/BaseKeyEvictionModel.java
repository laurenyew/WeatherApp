package laurenyew.weatherapp.network.responses.models;

import java.util.Date;

/**
 * Created by laurenyew on 4/23/16.
 * <p/>
 * Base Eviction Model (used for eviction strategy)
 */
public class BaseKeyEvictionModel {

    //Used for eviction strategy (evict if 1 day off original received time)
    public Date evictionDate;
    public String key;

    public BaseKeyEvictionModel() {
        evictionDate = null;
        key = null;
    }

    public BaseKeyEvictionModel(String key) {
        super();
        this.key = key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        BaseKeyEvictionModel c = (BaseKeyEvictionModel) o;
        boolean isEqual = false;
        if (c != null) {
            isEqual = key.equals(c.key) && evictionDate.equals(c.evictionDate);
        }
        return isEqual;
    }

    @Override
    public String toString() {
        return "BaseKeyEvictionModel [response=" +
                key + "," + evictionDate + "]";
    }
}
