package com.example.alex.avtomanager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by alex on 23.10.16.
 */

public class RecyclerViewAdapterParameter extends RecyclerView.Adapter<RecyclerViewAdapterParameter.ViewHolder>  {
    private ArrayList<Parameter> parameters;
    private Context context;

    private boolean b;

    public RecyclerViewAdapterParameter(ArrayList<Parameter> parameters, Context context) {
        this.parameters = parameters;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_view_item_layout_parameter, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        holder.textViewNameParameter.setText(parameters.get(i).getNameParameter());

        b = MyApplication.getInstance().getParameters().get(i).isParameterTypeOfWork();
        String s = parameters.get(i).getParameterTypeOfWork(b);


        holder.textViewTypeOfWork.setText(s);

    }

    @Override
    public int getItemCount() {
        return parameters == null ? 0 : parameters.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNameParameter, textViewTypeOfWork;


        public ViewHolder(View itemView) {
            super(itemView);
            initViews(itemView);
        }

        private void initViews (View itemView){
            textViewNameParameter = (TextView)itemView.findViewById(R.id.text_view_name_parameter);
            textViewTypeOfWork = (TextView)itemView.findViewById(R.id.text_view_type_of_work);

        }
    }

}
