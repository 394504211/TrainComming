package view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidtest.traincomming.R;

/**
 * Created by Administrator on 2017/3/29.
 */
public class QueryFragment extends Fragment {
    private static final String TAG = "QueryActivity";
    private FragmentManager fm;
    private LinearLayout ll_background;
    private Query_have qhFragment;
    private Query_nhave qnhFragment;
    public static final int HAVE_FRAGMENT = 1;
    public static final int NOTHAVE_FRAGMENT = 2;
    public int currentFragmentType = -1;
    private View view;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view=inflater.inflate(R.layout.query_fragment,container,false);
        initData();
        return view;
    }

    private void initData() {
        Button btn_nhave = (Button) view.findViewById(R.id.btn_nhave);
        Button btn_have = (Button) view.findViewById(R.id.btn_have);
        ll_background=(LinearLayout) view.findViewById(R.id.ll_background) ;
        btn_nhave.setOnClickListener(onClicker);
        btn_have.setOnClickListener(onClicker);
        loadFragment(NOTHAVE_FRAGMENT);
    }

    private void switchFragment(int type) {
        switch (type) {
            case HAVE_FRAGMENT:
                loadFragment(HAVE_FRAGMENT);
                break;
            case NOTHAVE_FRAGMENT:
                loadFragment(NOTHAVE_FRAGMENT);
                break;
        }

    }

    private void loadFragment(int type) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (type == NOTHAVE_FRAGMENT) {
            if (qnhFragment == null) {
                qnhFragment = new Query_nhave();
                transaction.add(R.id.fl_content, qnhFragment, "nothave");
            } else {
                transaction.remove(qnhFragment);
                qnhFragment = new Query_nhave();
                transaction.add(R.id.fl_content, qnhFragment, "nothave");
            }
            if (qhFragment != null) {
                transaction.hide(qhFragment);
            }
            currentFragmentType = NOTHAVE_FRAGMENT;
        } else {
            if (qhFragment == null) {
                qhFragment = new Query_have();
                transaction.add(R.id.fl_content, qhFragment, "have");
            } else {
                transaction.remove(qhFragment);
                qhFragment = new Query_have();
                transaction.add(R.id.fl_content, qhFragment, "have");
            }
            if (qnhFragment != null) {
                transaction.hide(qnhFragment);
            }
            currentFragmentType = HAVE_FRAGMENT;
        }
        transaction.commitAllowingStateLoss();
    }

    // private void setStatusBarColor() {
    // Window window = getWindow();
    // WindowManager.LayoutParams params = getWindow().getAttributes();
    // params.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
    // window.setAttributes(params);
    // SystemBarTintManager tintManager = new SystemBarTintManager(this);
    // tintManager.setStatusBarTintEnabled(true);
    // tintManager.setTintColor(getResources().getColor(R.color.tab));
    // }

    private View.OnClickListener onClicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_have:
                    ll_background.setBackgroundResource(R.mipmap.tab_have_03);
                    switchFragment(HAVE_FRAGMENT);

                    break;
                case R.id.btn_nhave:
                    ll_background.setBackgroundResource(R.mipmap.tab_nhave_03);
                    switchFragment(NOTHAVE_FRAGMENT);
                    break;
            }
        }
    };

}
