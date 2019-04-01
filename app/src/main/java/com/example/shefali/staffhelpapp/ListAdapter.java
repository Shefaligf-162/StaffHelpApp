package com.example.shefali.staffhelpapp;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends ArrayAdapter {
    List list=new ArrayList<>();
    public ListAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
    static class LayoutHandler{
        TextView name,date,contact_no,type;
    }

    @Override
    public void add(Object object){
        super.add(object);
        list.add(object);

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row=convertView;
        LayoutHandler lh;
        if(row==null){
            LayoutInflater li=(LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=li.inflate(R.layout.row_layout,parent,false);
            lh=new LayoutHandler();
            lh.name=(TextView)row.findViewById(R.id.name_my);
            lh.contact_no=(TextView)row.findViewById(R.id.contact_my);
            lh.date=(TextView)row.findViewById(R.id.date_my);
            lh.type=(TextView)row.findViewById(R.id.type_my);
            row.setTag(lh);
        }
        else{
            lh=(LayoutHandler)row.getTag();
        }
        DataProvider dp1=(DataProvider)this.getItem(position);
        lh.name.setText(dp1.getName());
        lh.date.setText(dp1.getDate());
        lh.type.setText(dp1.getType());
        lh.contact_no.setText(dp1.getContact_no());
        return row;
    }
}
