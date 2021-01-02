package com.example.recyclerviewtest;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {
  private List<Fruit>mFruits;

  public FruitAdapter(List<Fruit> fruits) {
    mFruits = fruits;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_item,parent,false);
    final ViewHolder holder = new ViewHolder(view);
    holder.fruitView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        int position = holder.getAbsoluteAdapterPosition();
        Fruit fruit = mFruits.get(position);
        Toast.makeText(v.getContext(),fruit.getName(),Toast.LENGTH_SHORT).show();
      }
    });

    holder.fruitImage.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        int position = holder.getAbsoluteAdapterPosition();
        Fruit fruit = mFruits.get(position);
        Toast.makeText(v.getContext(),"you Clicked Image"+fruit.getName(),Toast.LENGTH_SHORT).show();
      }
    });
    return holder;
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Fruit fruit = mFruits.get(position);
    holder.fruitName.setText(fruit.getName());
    holder.fruitImage.setImageResource(fruit.getImageId());
  }

  @Override
  public int getItemCount() {
    return mFruits.size();
  }

  static class ViewHolder extends RecyclerView.ViewHolder {
    ImageView fruitImage;
    TextView fruitName;
    View fruitView;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      fruitView = itemView;
      fruitImage = (ImageView)itemView.findViewById(R.id.fruit_image);
      fruitName = (TextView)itemView.findViewById(R.id.fruit_name);
    }
  }
}
