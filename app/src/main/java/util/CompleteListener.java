package util;

import bean.BaseBean;

/**
 * Created by xlf on 2017/5/10.
 */
public interface CompleteListener {
    void onComplected(Object result);

    void onFailed(BaseBean msg);
}
