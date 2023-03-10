package com.example.recipe.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe.Models.Equipment;
import com.example.recipe.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class InstructionsEquipmentAdapter extends RecyclerView.Adapter<InstructionsEquipmentViewHolder> {

    Context context;
    List<Equipment> list;

    public InstructionsEquipmentAdapter(Context context, List<Equipment> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public InstructionsEquipmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InstructionsEquipmentViewHolder(LayoutInflater.from(context).inflate(R.layout.list_instructions_step_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionsEquipmentViewHolder holder, int position) {
                holder.textView_instructions_step_item.setText(list.get(position).name);
                holder.textView_instructions_step_item.setSelected(true);
        Picasso.get().load("https://spoonacular.com/cdn/equipment_100x100/" + list.get(position).image).into(holder.imageView_instructions_items);
        }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class InstructionsEquipmentViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView_instructions_items;
    TextView textView_instructions_step_item;
    public InstructionsEquipmentViewHolder(@NonNull View itemView) {
        super(itemView);
        textView_instructions_step_item = itemView.findViewById(R.id.textView_instructions_step_item);
        imageView_instructions_items = itemView.findViewById(R.id.imageView_instructions_items);
    }
}