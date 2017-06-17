package view;

import bean.BaseResponse;

/**
 * Created by xlf on 2017/5/14.
 */
public interface IBaseView {
    void showDate(Object result);
    void loadFail(String msg);
    void showMessage(String msg);
}
