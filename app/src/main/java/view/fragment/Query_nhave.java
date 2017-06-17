package view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidtest.traincomming.R;

import adapter.StationAdapter;
import bean.TrainBean;
import view.activity.ticketsActivity;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import java.util.Calendar;

/**
 * Created by Administrator on 2017/3/30.
 */
public class Query_nhave extends BaseFragment implements OnDateSetListener {
    public static final String DATEPICKER_TAG = "datepicker";
    private Button dateButton;
    private Button submit;
    AutoCompleteTextView acTextViewfrom;
    AutoCompleteTextView acTextViewto;
    ImageView imExchange;


    @Override
    protected int getLayoutId() {
        return R.layout.query_nhave_fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        acTextViewfrom = (AutoCompleteTextView) view.findViewById(R.id.actv_from);
        acTextViewto = (AutoCompleteTextView) view.findViewById(R.id.actv_to);
        submit=(Button)view.findViewById(R.id.btn_query) ;
        StationAdapter adapter = new StationAdapter(view.getContext());
        acTextViewfrom.setAdapter(adapter);
        acTextViewfrom.setThreshold(1);
        acTextViewfrom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String station = ((StationAdapter) parent.getAdapter()).getFilteredList().get(position);
                acTextViewfrom.setText(station);
            }
        });
        acTextViewto.setAdapter(adapter);
        acTextViewto.setThreshold(1);
        acTextViewto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String station = ((StationAdapter) parent.getAdapter()).getFilteredList().get(position);
                acTextViewto.setText(station);
            }
        });

        imExchange=(ImageView)view.findViewById(R.id.img_query_change);
        imExchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp=acTextViewfrom.getText().toString();
                acTextViewfrom.setText(acTextViewto.getText().toString());
                acTextViewto.setText(temp);
            }
        });

        final Calendar calendar = Calendar.getInstance();
        dateButton = (Button) view.findViewById(R.id.dateButton);
        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);
        dateButton.setText(calendar.get(Calendar.YEAR)+"-"+
                String.format("%02d",(calendar.get(Calendar.MONTH)+1))+"-"+String.format("%02d",calendar.get(Calendar.DAY_OF_MONTH)));
        dateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                datePickerDialog.setVibrate(false);
                datePickerDialog.setYearRange(calendar.get(Calendar.YEAR), calendar.get(Calendar.YEAR) + 1);
                datePickerDialog.setCloseOnSingleTapDay(false);
                datePickerDialog.show(getActivity().getSupportFragmentManager(), DATEPICKER_TAG);
            }
        });

        if (savedInstanceState != null) {
            DatePickerDialog dpd = (DatePickerDialog) getActivity().getSupportFragmentManager().findFragmentByTag(DATEPICKER_TAG);
            if (dpd != null) {
                dpd.setOnDateSetListener(this);
            }
        }
    }

    @Override
    protected void initEvent() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrainBean trainBean=new TrainBean();
                if(acTextViewfrom.getText().toString().isEmpty()||acTextViewto.getText().toString().isEmpty()){
                    Toast.makeText(v.getContext(), getResources().getString(R.string.querynull), Toast.LENGTH_SHORT).show();
                    return;
                }
                trainBean.setDate(dateButton.getText().toString());
                trainBean.setFrom(acTextViewfrom.getText().toString());
                trainBean.setTo(acTextViewto.getText().toString());
                Intent intent=new Intent(v.getContext(), ticketsActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("info", trainBean);
                intent.putExtras(b);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        dateButton.setText(year + "-" + String.format("%02d",(month + 1)) + "-" + String.format("%02d",day));

    }

    @Override
    protected void initData() {
    }
}
