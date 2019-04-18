package com.vjsm.sports.kaalai;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.net.Socket;
import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    ArrayList<Users> personNames = new ArrayList<Users>();
    private OnItemClickListener mListener;
    Context context;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public CustomAdapter(ArrayList<Users> personNames, Context context) {

        this.personNames = personNames;
        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
     //   holder.name.setText(personNames.get(position).getName());
        holder.place.setText("இடம்: "+personNames.get(position).getPlace());
        holder.district.setText("மாவட்டம்: "+personNames.get(position).getDistrict());
        holder.sdate.setText("தேதி: "+personNames.get(position).getStartdate());
       // holder.madtdate.setText(personNames.get(position).getMadtdate());
      //  holder.mantdate.setText(personNames.get(position).getMantdate());
      //  holder.phone.setText(personNames.get(position).getPhone());


        if (TextUtils.isEmpty(personNames.get(position).getImageurl())) {
            Log.d("printer", "String same");
            //Toast.makeText(context,"include if",Toast.LENGTH_SHORT).show();
            holder.imagev.setVisibility(View.VISIBLE);

            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/socialwork-a0eb5.appspot.com/o/noimage%2Fnoimage.jpg?alt=media&token=eb353e78-3cb1-4d29-bf9c-360c472b81c6").into(holder.imagev);
            //Picasso.get().load(R.drawable.noimage).into(holder.imagev);
        } else {

            holder.imagev.setVisibility(View.VISIBLE);
            Picasso.get().load(personNames.get(position).getImageurl()).into(holder.imagev);

        }

    }


    @Override
    public int getItemCount() {
        return personNames.size();
    }

    public void filterList(ArrayList<Users> names) {
        this.personNames = names;

        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, place, district, sdate, madtdate, mantdate, phone;
        ImageView imagev;
        RelativeLayout relativeLayout;
        View mview;

        public MyViewHolder(final View itemView) {
            super(itemView);
            mview = itemView;
          //  name = (TextView) mview.findViewById(R.id.names);
            place = (TextView) mview.findViewById(R.id.places);
            district = (TextView) mview.findViewById(R.id.districts);
            sdate = (TextView) mview.findViewById(R.id.stdate);
         //   madtdate = (TextView) mview.findViewById(R.id.madTdate);
         //   mantdate = (TextView) mview.findViewById(R.id.manTdate);
         //   phone = (TextView) mview.findViewById(R.id.phonenumber);
            imagev = (ImageView) mview.findViewById(R.id.image);

           // relativeLayout = (RelativeLayout) mview.findViewById(R.id.cliccc);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });

        }
        }
    }



