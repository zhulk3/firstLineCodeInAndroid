package com.example.materialdesign;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {
  private Context mContext;
  private List<Fruit>mFruitList;


  public FruitAdapter(List<Fruit> fruitList) {
    mFruitList = fruitList;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    if(mContext==null){
      mContext = parent.getContext();
    }
    View view = LayoutInflater.from(mContext).inflate(R.layout.fruit_item,parent,false);
    final ViewHolder holder = new ViewHolder(view);
    holder.mCardView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        int position = holder.getAdapterPosition();
        Fruit  fruit = mFruitList.get(position);
        Intent intent = new Intent(mContext,FruitActivity.class);
        intent.putExtra(FruitActivity.FRUIT_NAME,fruit.getName());
        intent.putExtra(FruitActivity.FRUIT_IMAGE_ID,fruit.getImage());
        mContext.startActivity(intent);
      }
    });
    return holder;
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Fruit fruit = mFruitList.get(position);
    holder.mTextView.setText(fruit.getName());
    Glide.with(mContext).load(fruit.getImage()).into(holder.mImageView);
  }



  @Override
  public int getItemCount() {
    return mFruitList.size();
  }

  static class ViewHolder extends RecyclerView.ViewHolder{
    CardView mCardView;
    ImageView mImageView;
    TextView mTextView;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      mCardView = (CardView)itemView;
      mImageView = itemView.findViewById(R.id.fruit_image);
      mTextView = itemView.findViewById(R.id.fruit_name);
    }
  }
}
