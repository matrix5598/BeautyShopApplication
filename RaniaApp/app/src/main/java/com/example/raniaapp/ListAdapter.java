package com.example.raniaapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListAdapter extends ArrayAdapter {

    private Activity mContext;
    List<Employee> TerminList;

    public ListAdapter(Activity mContext, List<Employee> TerminList){
        super(mContext, R.layout.list_item, TerminList);
        this.mContext = mContext;
        this.TerminList = TerminList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){

        LayoutInflater inflater = mContext.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.list_item, null, true);


        AutoCompleteTextView tvAutoCompleteTextView1 = listItemView.findViewById(R.id.autoCompleteTextView1);
        Employee employee = TerminList.get(position);

        tvAutoCompleteTextView1.setText(employee.getVon());
        tvAutoCompleteTextView1.setText(employee.getBis());
        tvAutoCompleteTextView1.setText(employee.getTag());
        tvAutoCompleteTextView1.setText(employee.getMonat());
        tvAutoCompleteTextView1.setText(employee.getJahr());

        return listItemView;


        //von, bis, tagesdatum, monatstag, jahresdatum



    }
}
