package com.user.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.user.app.R;
import com.user.app.model.MonthModel;
import com.user.app.view.MyJournal;
import com.zarinpal.libs.cardviwepager.BaseCardViewPagerItem;


public class MonthAdapter extends BaseCardViewPagerItem<MonthModel> {

    Context context;
    @Override
    public int getLayout() {
        return R.layout.item_month;
    }

    public MonthAdapter(Context context) {
        this.context=context;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return super.getCardViewAt(position);
    }

    @Override
    public void bindView(final View view, final MonthModel item) {

        ViewGroup layoutRoot = view.findViewById(R.id.layout_root);
        TextView txtTitle = view.findViewById(R.id.txt_title);
        TextView txtCount = view.findViewById(R.id.txt_count);

        layoutRoot.setBackgroundColor(item.getBgColor());
        txtTitle.setText(item.getMonth());
        String count=item.getEntryCount()+" Entry";
        txtCount.setText(count);

        layoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, item.getMonth(), Toast.LENGTH_SHORT).show();


                Intent intent=new Intent(context, MyJournal.class);
                intent.putExtra("month",item.getMonth());
                intent.putExtra("monthShort",item.getMonthShort());
                context.startActivity(intent);
            }
        });
    }



}