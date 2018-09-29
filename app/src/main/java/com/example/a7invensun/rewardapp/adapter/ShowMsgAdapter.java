package com.example.a7invensun.rewardapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a7invensun.rewardapp.R;
import com.example.a7invensun.rewardapp.model.DeliveryMessages;

import java.util.List;

/**
 * Created by 7invensun on 2018/9/28.
 */

public class ShowMsgAdapter extends BaseAdapter{
    private Context mContext;
    private List<DeliveryMessages.Message> mList;
    public ShowMsgAdapter(Context context,List<DeliveryMessages.Message> dataList) {
        mContext = context;
        mList = dataList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_showcourier_lv, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tvShowtimeShowcourier.setText(mList.get(position).getTime());
        holder.tvShowcontextShowcourier.setText(mList.get(position).getContext());
        holder.tvShowcontextShowcourier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                particularContext(mList.get(position).getTime(),mList.get(position).getContext());
            }
        });
        return view;
    }

    private void particularContext(String time, String context) {
        AlertDialog dialog = new AlertDialog.Builder(mContext)
                .setTitle("详细内容--"+time)//设置对话框的标题
                .setMessage(context)//设置对话框的内容
                //设置对话框的按钮
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

    private class ViewHolder {
        private TextView tvShowtimeShowcourier;
        private TextView tvShowcontextShowcourier;

        private ViewHolder(View view) {
            tvShowtimeShowcourier = view.findViewById(R.id.tv_showtime_showcourier);
            tvShowcontextShowcourier = view.findViewById(R.id.tv_showcontext_showcourier);
        }
    }
}
