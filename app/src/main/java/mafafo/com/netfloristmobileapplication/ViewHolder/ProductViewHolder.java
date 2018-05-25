package mafafo.com.netfloristmobileapplication.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import mafafo.com.netfloristmobileapplication.Interface.ItemClickListener;
import mafafo.com.netfloristmobileapplication.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView cake_name;
    public ImageView cake_image;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ProductViewHolder(View itemView) {
        super(itemView);

        cake_name = (TextView)itemView.findViewById(R.id.cake_name);
        cake_image = (ImageView)itemView.findViewById(R.id.cake_image);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);

    }
}
