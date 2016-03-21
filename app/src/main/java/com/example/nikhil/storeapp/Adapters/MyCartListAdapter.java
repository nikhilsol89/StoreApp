package com.example.nikhil.storeapp.Adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nikhil.storeapp.R;
import com.example.nikhil.storeapp.listeners.RecyclerViewListItemClickListener;
import com.example.nikhil.storeapp.model.ItemDetailsModel;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 * Created by Nikhil on 3/20/2016.
 */
public class MyCartListAdapter extends RecyclerView.Adapter<MyCartListAdapter.ViewHolderPatternImplementerClass>{

    View view;
    Context context;
    ArrayList<ItemDetailsModel> modelArrayListt;
    RecyclerViewListItemClickListener listener;
    int position=-1;
    boolean removeButtonVisibility=false;

    public MyCartListAdapter(Context con,ArrayList<ItemDetailsModel> model,RecyclerViewListItemClickListener recyclerViewListItemClickListener,boolean removeButtonvisibility){
        this.context = con;
        this.modelArrayListt = model;
        this.listener = recyclerViewListItemClickListener;
        this.removeButtonVisibility = removeButtonvisibility;
    }

    @Override
    public ViewHolderPatternImplementerClass onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(this.context).inflate(R.layout.cart_list_cell_view, null, false);
        ViewHolderPatternImplementerClass holder;
        if (view.getTag() == null) {
            holder = new ViewHolderPatternImplementerClass(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolderPatternImplementerClass) view.getTag();
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolderPatternImplementerClass holder, int position) {
        holder.listItemName.setText(modelArrayListt.get(position).getItemName());
        holder.listItemCategoryText.setText(modelArrayListt.get(position).getItemCategory());
        holder.listItemQuantity.setText("Quantity:"+modelArrayListt.get(position).getQuantity()+"");

        ByteArrayInputStream imageStream = new ByteArrayInputStream(modelArrayListt.get(position).getImageByteArray());
        holder.listItemImage.setImageBitmap(BitmapFactory.decodeStream(imageStream));

        holder.position = position;
    }

    @Override
    public int getItemCount() {
        return this.modelArrayListt.size();
    }
    public class ViewHolderPatternImplementerClass extends RecyclerView.ViewHolder {

        int position=-1;
        Button removeBUtton;
        ImageView listItemImage;
        TextView listItemName, listItemCategoryText, listItemQuantity;

        public ViewHolderPatternImplementerClass(View itemView) {
            super(itemView);
            removeBUtton = (Button) itemView.findViewById(R.id.listItemRemoveBUtton);
            listItemName = (TextView) itemView.findViewById(R.id.listItemNameTextView);
            listItemQuantity = (TextView) itemView.findViewById(R.id.listItemQuantityTextView);
            listItemCategoryText = (TextView) itemView.findViewById(R.id.listItemCategoryTextView);
            listItemImage = (ImageView) itemView.findViewById(R.id.listItemImageView);
            if(removeButtonVisibility==false){
                removeBUtton.setVisibility(View.GONE);
                listItemQuantity.setVisibility(View.GONE);
            }

            removeBUtton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyCartListAdapter.this.listener.onItemCLickedListener(ViewHolderPatternImplementerClass.this.position,true);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MyCartListAdapter.this.listener.onItemCLickedListener(ViewHolderPatternImplementerClass.this.position,false);
                }
            });
        }
    }
}
