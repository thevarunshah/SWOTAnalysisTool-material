package com.thevarunshah.swotanalysistool.backend;

import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;


import com.thevarunshah.swotanalysistool.R;
import com.thevarunshah.swotanalysistool.SWOTScreen;

/**
 * Created by Pranav on 8/6/15.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<SWOTObject> mList;
    private AlphaAnimation clickEffect = new AlphaAnimation(1F, 0.5F);

    public RecyclerViewAdapter(Context contexts, ArrayList<SWOTObject> list) {
        this.mContext = contexts;
        this.mList = list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.list_item, parent, false);
        //itemView.startAnimation(clickEffect);
        return new ViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.titleTextView.setText(mList.get(position).getName());
        final int pos = position;
        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                //view.startAnimation(clickEffect);
                if(isLongClick){
                     final SWOTObject so = mList.get(position);
                    new AlertDialog.Builder(mContext)
                    .setIconAttribute(android.R.attr.alertDialogIcon)
                    .setTitle("Confirm Delete")
                    .setMessage("Are you sure you want to delete this SWOT?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            int id = so.getId();

                            removeItem(pos, id);
                            FileOutputStream fos = null;
                            ObjectOutputStream oos = null;
                            try {
                                fos = mContext.openFileOutput("swot_backup.ser", Context.MODE_PRIVATE);
                                oos = new ObjectOutputStream(fos);
                                oos.writeInt(Database.getId());
                                oos.writeObject(Database.getSWOTs());
                                notifyDataSetChanged();
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally{
                                try{
                                    oos.close();
                                    fos.close();
                                } catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();

                }
                else {
                    SWOTObject so = mList.get(position);
                    Bundle extra = new Bundle();
                    extra.putInt("objectId", so.getId());
                    Intent i = new Intent(mContext, SWOTScreen.class);
                    i.putExtra("bundle", extra);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(i);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener{
        private TextView titleTextView;
        private ItemClickListener clickListener;
        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView)itemView.findViewById(R.id.textView);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }
        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }
        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition(), false);
        }
        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view, getPosition(), true);
            return true;
        }
    }

    protected void removeItem(int pos, int id) {
        Database.getSWOTs().remove(id);
        mList.remove(pos);
        notifyItemRemoved(pos);
        notifyDataSetChanged();
    }
}
