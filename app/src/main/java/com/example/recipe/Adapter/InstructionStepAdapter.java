package com.example.recipe.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipe.Models.Step;
import com.example.recipe.R;

import java.util.List;

public class InstructionStepAdapter  extends RecyclerView.Adapter<InstructionsStepViewHolder>{

    Context context;
    List<Step> list;

    public InstructionStepAdapter(Context context, List<Step> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public InstructionsStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InstructionsStepViewHolder(LayoutInflater.from(context).inflate(R.layout.list_instructions_steps,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionsStepViewHolder holder, int position) {

        holder.textView_instructions_stepnumber.setText(String.valueOf(list.get(position).number));
        holder.textView_instructions_steptitle.setText(list.get(position).step);



            holder.recyclerView_instructions_ingredients.setHasFixedSize(true);
            holder.recyclerView_instructions_ingredients.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            InstructionsIngredientsAdapter instructionsIngredientsAdapter = new InstructionsIngredientsAdapter(context, list.get(position).ingredients);
            holder.recyclerView_instructions_ingredients.setAdapter(instructionsIngredientsAdapter);


        holder.recyclerView_instructions_equipments.setHasFixedSize(true);
        holder.recyclerView_instructions_equipments.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        InstructionsEquipmentAdapter instructionsEquipmentAdapter = new InstructionsEquipmentAdapter(context, list.get(position).equipment);
        holder.recyclerView_instructions_equipments.setAdapter(instructionsEquipmentAdapter);
    }

    @Override
    public int getItemCount() {

        return list.size();
    }
}

class InstructionsStepViewHolder extends RecyclerView.ViewHolder {
    TextView textView_instructions_stepnumber, textView_instructions_steptitle;

    RecyclerView recyclerView_instructions_equipments, recyclerView_instructions_ingredients;
    public InstructionsStepViewHolder(@NonNull View itemView) {
        super(itemView);

        textView_instructions_stepnumber = itemView.findViewById(R.id.textView_instructions_stepsnumber);
        textView_instructions_steptitle = itemView.findViewById(R.id.textView_instructions_stepstitle);
        recyclerView_instructions_equipments = itemView.findViewById(R.id.recyclerView_instructions_equipments);
        recyclerView_instructions_ingredients = itemView.findViewById(R.id.recyclerView_instructions_ingredients);
    }
}