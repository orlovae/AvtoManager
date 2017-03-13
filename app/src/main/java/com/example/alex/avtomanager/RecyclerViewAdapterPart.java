package com.example.alex.avtomanager;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;


/**
 * Created by alex on 12.10.16.
 */

public class RecyclerViewAdapterPart extends RecyclerView.Adapter<RecyclerViewAdapterPart.ViewHolder>  {
    private ArrayList<Part> parts;
    private Part part;
    private Context context;

    public final static String PART = "com.example.alex.avtomanager.PART";

    public RecyclerViewAdapterPart(ArrayList<Part> parts, Context context) {
        this.parts = parts;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_view_item_layout_part,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int i) {
        holder.textViewNamePart.setText(parts.get(i).getNamePart());
        holder.textViewExist.setText(R.string.exist);
        holder.textViewAutodoc.setText(R.string.autodoc);
        holder.textViewEmex.setText(R.string.emex);
        holder.textViewExistPrice.setText(Float.toString(parts.get(i).getPricePartExist()));
        holder.textViewAutodocPrice.setText(Float.toString(parts.get(i).getPricePartAutodoc()));
        holder.textViewEmexPrice.setText(Float.toString(parts.get(i).getPricePartEmex()));
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePart(i);
            }
        });
        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPart(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return parts == null ? 0 : parts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNamePart, textViewExist, textViewAutodoc, textViewEmex, textViewExistPrice,
                textViewAutodocPrice, textViewEmexPrice;
        Button buttonEdit, buttonDelete;


        public ViewHolder(View itemView) {
            super(itemView);
            initViews(itemView);

        }

        private void initViews (View itemView){
            textViewNamePart = (TextView)itemView.findViewById(R.id.text_view_name_part);
            textViewExist = (TextView)itemView.findViewById(R.id.text_view_exist);
            textViewAutodoc = (TextView)itemView.findViewById(R.id.text_view_autodoc);
            textViewEmex = (TextView)itemView.findViewById(R.id.text_view_emex);
            textViewExistPrice = (TextView)itemView.findViewById(R.id.text_view_exist_price);
            textViewAutodocPrice = (TextView)itemView.findViewById(R.id.text_view_autodoc_price);
            textViewEmexPrice = (TextView)itemView.findViewById(R.id.text_view_emex_price);
            buttonEdit = (Button)itemView.findViewById(R.id.edit);
            buttonDelete = (Button)itemView.findViewById(R.id.delete);

        }
    }

    private void deletePart(int i){//Удаляем Part из RecyclerView
        parts.remove(i);
        notifyItemRemoved(i);
        notifyItemRangeRemoved(i, parts.size());
    }

    private void editPart(int i){
        part = parts.get(i);

        startLayoutGo(part);
    }

    private void startLayoutGo(Part part){
        String s1 = part.getNamePart();
        String s2 = part.getCatalogueNumber();
        boolean b1 = part.isPartTypeOfWork();
        float f1 = part.getPricePartExist();
        float f2 = part.getPricePartAutodoc();
        float f3 = part.getPricePartEmex();
        int id = part.getId();

        Intent intent = new Intent(context, LayoutPartEditEdit.class);
        intent.putExtra(PART, new Part(s1, s2, b1, f1, f2, f3, id));
        context.startActivity(intent);
    }

}
