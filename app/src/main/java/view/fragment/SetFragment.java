package view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.androidtest.traincomming.R;

import view.activity.setaboutActivity;
import view.activity.setadviceActivity;
import view.activity.setinfoActivity;

/**
 * Created by Administrator on 2017/3/29.
 */
public class SetFragment extends BaseFragment {
    private RelativeLayout rl_info;
    private RelativeLayout rl_check;
    private RelativeLayout rl_about;
    private RelativeLayout rl_feedback;
    @Override
    protected int getLayoutId() {
        return R.layout.setting_fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        rl_info=(RelativeLayout)view.findViewById(R.id.rl_set_info);
        rl_check=(RelativeLayout)view.findViewById(R.id.rl_set_check);
        rl_about=(RelativeLayout)view.findViewById(R.id.rl_set_about);
        rl_feedback=(RelativeLayout)view.findViewById(R.id.rl_set_feedback);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        rl_info.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getActivity(),setinfoActivity.class);
                startActivity(intent);
            }
        });

        rl_check.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Toast.makeText(getContext(),"当前已是最新版本", Toast.LENGTH_LONG).show();
            }
        });

        rl_about.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getActivity(),setaboutActivity.class);
                startActivity(intent);
            }
        });

        rl_feedback.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getActivity(),setadviceActivity.class);
                startActivity(intent);
            }
        });
    }
}
