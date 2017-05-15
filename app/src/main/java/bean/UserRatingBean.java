package bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/8.
 */
public class UserRatingBean implements Serializable {
    private int max;
    private String value;
    private int min;

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }
}
