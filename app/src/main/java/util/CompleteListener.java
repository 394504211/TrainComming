package util;

import bean.BaseResponse;

/**
 * Created by xlf on 2017/5/10.
 */
public interface CompleteListener {
    void onCompleted(Object result);
    void onFailed(BaseResponse msg);
}
