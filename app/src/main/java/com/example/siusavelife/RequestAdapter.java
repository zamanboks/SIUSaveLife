package com.example.siusavelife;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.util.List;

public class RequestAdapter extends  RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {

    //this context we will use to inflate the layout
    private Context context;

    //we are storing all the products in a list
    private List<Request> requestList;
    public RequestAdapter(Context context, List<Request> requestList) {

        this.context = context;
        this.requestList = requestList;
    }
    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating and returning our view holder

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.request_list, null);
        return new RequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {

        final Request request = requestList.get(position);

        //binding the data with the viewholder views

        holder.textViewName.setText(request.getFullname());
        holder.textViewCity.setText("City : "+request.getCity());
        holder.textViewDate.setText("Date : "+request.getDate());
        holder.textViewBeg.setText(request.getBag());
        holder.textViewAddress.setText("Donate Location : "+request.getHospital());
        holder.textViewWhy.setText("Why : "+request.getWhy());
        holder.textVieNumber.setText("Call : "+request.getNumber());


    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public class RequestViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewCity, textViewDate,textViewBeg,textViewAddress,textViewWhy,textVieNumber;

        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewNameIdd);
            textViewCity = itemView.findViewById(R.id.textViewCityIdd);
            textViewDate = itemView.findViewById(R.id.textViewDateIdd);
            textViewBeg = itemView.findViewById(R.id.textViewBagIdd);
            textViewAddress = itemView.findViewById(R.id.textViewAddressIdd);
            textViewWhy = itemView.findViewById(R.id.textViewWhyIdd);
            textVieNumber = itemView.findViewById(R.id.textViewNameIdd);

        }
    }
}
