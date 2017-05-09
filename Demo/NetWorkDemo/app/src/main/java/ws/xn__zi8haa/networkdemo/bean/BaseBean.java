package ws.xn__zi8haa.networkdemo.bean;

/**
 * <pre>
 *     author : h.yw
 *     e-mail : rumengzhenxing@gmail.com
 *     time   : 2017/05/09
 *     desc   : xxxx描述
 *     version: 1.0
 * </pre>
 */

public class BaseBean {

    String key;
    String value;

    public BaseBean(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
