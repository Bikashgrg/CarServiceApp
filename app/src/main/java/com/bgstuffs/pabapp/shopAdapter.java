package com.bgstuffs.pabapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class shopAdapter extends RecyclerView.Adapter<shopAdapter.ProductViewHolder> {

    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<serviceCenteres> serviceCenteresList;

    public shopAdapter(Context mCtx, List<serviceCenteres> serviceCenteresList) {
        this.mCtx = mCtx;
        this.serviceCenteresList = serviceCenteresList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater layoutInflater = LayoutInflater.from(mCtx);
        View view = layoutInflater.inflate(R.layout.home_recycler,null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, final int position) {

        //getting the product of the specified position
        serviceCenteres services = serviceCenteresList.get(position);

        //binding the data with viewHolder views
        holder.textViewTitle.setText(services.getName());
        holder.textViewLocation.setText(services.getLocation());
        holder.textViewRating.setText(String.valueOf(services.getRating()));
        holder.textViewPrice.setText(String.valueOf(services.getPrice()));

        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(services.getImage()));

        holder.relativeLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent,intent1,intent2,intent3,intent4,intent5,intent6;
                Context context = v.getContext();
                switch (position){
                    case 0:
                        intent = new Intent(context,ServiceShop1.class);
                        context.startActivity(intent);
                        break;
                    case 1:
                        intent1 = new Intent(context,ServiceShop2.class);
                        context.startActivity(intent1);
                        break;
                    case 2:
                        intent2 = new Intent(context,ServiceShop3.class);
                        context.startActivity(intent2);
                        break;
                    case 3:
                        intent3 = new Intent(context,ServiceShop4.class);
                        context.startActivity(intent3);
                        break;
                    case 4:
                        intent4 = new Intent(context,Servicehop5.class);
                        context.startActivity(intent4);
                        break;
                    case 5:
                        intent5 = new Intent(context,ServiceShop6.class);
                        context.startActivity(intent5);
                        break;
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return serviceCenteresList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewLocation, textViewRating, textViewPrice;
        ImageView imageView;
        RelativeLayout relativeLay;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewLocation = itemView.findViewById(R.id.textViewShortDesc);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            imageView = itemView.findViewById(R.id.imageView);

            relativeLay = itemView.findViewById(R.id.relativeLayout);
        }
    }


}
