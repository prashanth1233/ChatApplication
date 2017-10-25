package com.example.prasanth.chatapp.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prasanth.chatapp.R;
import com.example.prasanth.chatapp.activities.ChatUIActivity;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.users.model.QBUser;

import java.util.ArrayList;


public class AllUsersUIAdapter extends RecyclerView.Adapter<AllUsersUIAdapter.ViewHolder> {

    private Context context;
    private ArrayList<QBUser> usersList=new ArrayList<>();
    private View.OnClickListener onClickListener;


    public AllUsersUIAdapter(Context context, ArrayList<QBUser> usersList, View.OnClickListener onClickListener) {
        this.context=context;
        this.usersList=usersList;
        this.onClickListener=onClickListener;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(context).inflate(R.layout.all_users_adapter_xml,null,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        QBUser user=usersList.get(position);
        holder.userName.setText(usersList.get(position).getLogin());
        holder.userName.setTag(user);
    }

    @Override
    public int getItemCount() {
        return usersList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView userImage;
        TextView userName;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(onClickListener);
            userName=(TextView)itemView.findViewById(R.id.userName);
        }
    }
}
