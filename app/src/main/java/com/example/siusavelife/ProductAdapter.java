package com.example.siusavelife;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.content.pm.PackageManager.*;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {



    //this context we will use to inflate the layout
    private Context context;

    //we are storing all the products in a list
    private List<Product> productList;

    //getting the context and product list with constructor
    public ProductAdapter(Context context, List<Product> productList) {

        this.context = context;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //inflating and returning our view holder

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.user_info, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {

        //getting the product of the specified position

       final Product product = productList.get(position);

        //binding the data with the view holder views

        holder.textViewName.setText(product.getName());
        holder.textViewPhone.setText("Call : "+product.getPhone());
        holder.textViewBlood.setText(""+product.getBlood());
        holder.textViewCity.setText("City : "+product.getCity());
        holder.textViewAddress.setText("Donate Location : "+product.getAddress());
        holder.textViewStatus.setText("Status : "+product.getStatus());


        holder.textViewPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = product.getPhone();
                String dial = "tel:" + number;
                context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        });



        if (product.getStatus().equals("Can't Donate Now.")){
            holder.textViewStatus.setTextColor(Color.parseColor("#FFFF0000"));
        }
        else if (product.getStatus().equals("Ready For Donate.")){
            holder.textViewStatus.setTextColor(Color.parseColor("#FF03A50B"));
        }
       // holder.imageView.setImageDrawable(context.getResources().getDrawable(product.getImage()));

    }


    @Override
    public int getItemCount() {

        return productList.size();
    }



    class ProductViewHolder extends RecyclerView.ViewHolder{

        TextView textViewName, textViewPhone, textViewBlood,textViewCity,textViewAddress,textViewStatus;
        ImageView imageView;


        public ProductViewHolder(View itemView) {

            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewNameId);
            textViewPhone = itemView.findViewById(R.id.textViewPhoneId);
            textViewBlood = itemView.findViewById(R.id.textViewBloodId);
            textViewCity = itemView.findViewById(R.id.textViewCityIdd);
            textViewAddress = itemView.findViewById(R.id.textViewAddressId);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            imageView = itemView.findViewById(R.id.imageView);


        }


    }
}
