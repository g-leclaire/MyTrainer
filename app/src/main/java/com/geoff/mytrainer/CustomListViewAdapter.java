package com.geoff.mytrainer;

import java.util.List;
import com.geoff.mytrainer.R;
import com.geoff.mytrainer.RowItem;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListViewAdapter extends ArrayAdapter<RowItem> {

    Context context;

    public CustomListViewAdapter(Context context, int resourceId,
                                 List<RowItem> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    // Private view holder class.
    private class ViewHolder {
        TextView txtTitle;
        TextView txtDesc;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.txtDesc = (TextView) convertView.findViewById(R.id.desc);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        RowItem rowItem = getItem(position);
        holder.txtDesc.setText(rowItem.getDesc());
        holder.txtTitle.setText((position + 1) + ". " + rowItem.getTitle());

        return convertView;
    }

    public void remove(int position){
        remove(getItem(position));
    }

    public void setRowItems(List<RowItem> rowItems){
        clear();
        addAll(rowItems);
    }
}
