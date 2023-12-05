package com.example.gsoft;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

class UtilisateursDataAdapter extends RecyclerView.Adapter<UtilisateursDataAdapter.PlayerViewHolder> {
    public List<ListUser> user;

    public class PlayerViewHolder extends RecyclerView.ViewHolder {
        private TextView nom, type;

        public PlayerViewHolder(View view) {
            super(view);
            nom = (TextView) view.findViewById(R.id.tvnom);
            type = (TextView) view.findViewById(R.id.tvtype);

        }
    }

    public UtilisateursDataAdapter(List<ListUser> users) {
        this.user = users;
    }

    @Override
    public PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listeutilisateur, parent, false);

        return new PlayerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PlayerViewHolder holder, int position) {
        ListUser listUser =user.get(position);
        holder.nom.setText(listUser.getNom());
        holder.type.setText(listUser.getType());

    }

    @Override
    public int getItemCount() {
        return user.size();
    }
}
