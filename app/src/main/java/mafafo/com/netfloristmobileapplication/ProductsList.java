package mafafo.com.netfloristmobileapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import mafafo.com.netfloristmobileapplication.Interface.ItemClickListener;
import mafafo.com.netfloristmobileapplication.Model.Products;
import mafafo.com.netfloristmobileapplication.ViewHolder.ProductViewHolder;

public class ProductsList extends AppCompatActivity {

    //Declarations
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference productList;
    String categoryId="";
    FirebaseRecyclerAdapter<Products,ProductViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        //firebase
        database = FirebaseDatabase.getInstance();
        productList = database.getReference("Product");

        recyclerView = (RecyclerView)findViewById(R.id.recycler_cake);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //get Intent here
        if(getIntent() !=null)
            categoryId = getIntent().getStringExtra("CategoryId");
        if(!categoryId.isEmpty() && categoryId !=null){
            loadListCake(categoryId);
        }

    }
    private void loadListCake(String categoryId) {
        adapter = new FirebaseRecyclerAdapter<Products, ProductViewHolder>(Products.class,R.layout.cake_item,ProductViewHolder.class,productList.orderByChild("MenuId").equalTo(categoryId)) {
            @Override
            protected void populateViewHolder(ProductViewHolder viewHolder, Products model, final int position) {
            viewHolder.cake_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.cake_image);

                final Products local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int postion, boolean isLongClick) {
                        //Stary new activity
                        Intent productDetail = new Intent(ProductsList.this, ProductDetail.class);
                        productDetail.putExtra("ProductId",adapter.getRef(position).getKey());// sends product id to new activity
                        startActivity(productDetail);
                    }
                });
            }
        };
        //set Adapter
        Log.d("TAG",""+adapter.getItemCount());
        recyclerView.setAdapter(adapter);
    }
}
