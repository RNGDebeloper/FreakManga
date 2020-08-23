package com.otongsoetardjoe.freakmangabrandnew.adapters.recycler_adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.otongsoetardjoe.freakmangabrandnew.R;
import com.otongsoetardjoe.freakmangabrandnew.activities.TryWebViewActivity;
import com.otongsoetardjoe.freakmangabrandnew.activities.search_or_read_mvp.SearchNuclearActivity;
import com.otongsoetardjoe.freakmangabrandnew.databinding.HenListItemBinding;
import com.otongsoetardjoe.freakmangabrandnew.databinding.ItemListNekoBinding;
import com.otongsoetardjoe.freakmangabrandnew.models.HenModel;
import com.otongsoetardjoe.freakmangabrandnew.models.NekoModel;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class RecyclerNekoNewReleasesAdapter extends RecyclerView.Adapter<RecyclerNekoNewReleasesAdapter.ViewHolder> {
    private Context context;
    private List<NekoModel> henModelList;

    public RecyclerNekoNewReleasesAdapter(Context context, List<NekoModel> henModelList) {
        this.context = context;
        this.henModelList = henModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ItemListNekoBinding itemListBinding = ItemListNekoBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(itemListBinding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        populateList(holder, position);
    }

    private void populateList(ViewHolder holder, int position) {
        holder.itemListBinding.textTitle.setText(henModelList.get(position).getNekoTitle());
        try {
            Glide.with(context)
                    .asDrawable()
                    .load(new URL(henModelList.get(position).getNekoThumbURL()))
                    .apply(
                            new RequestOptions()
                                    .transform(new RoundedCorners(20))
                                    .timeout(30000)
                    )
                    .error(context.getResources().getDrawable(R.drawable.error))
                    .placeholder(context.getResources().getDrawable(R.drawable.imageplaceholder))
                    .into(holder.itemListBinding.imageThumb);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        holder.itemListBinding.relativeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), TryWebViewActivity.class);
                intent.putExtra("watchNekoURL", henModelList.get(position).getNekoURL());
                intent.putExtra("watchNekoTitle", henModelList.get(position).getNekoTitle());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return henModelList == null ? 0 : henModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemListNekoBinding itemListBinding;

        public ViewHolder(final ItemListNekoBinding itemViewList) {
            super(itemViewList.getRoot());
            this.itemListBinding = itemViewList;
        }
    }

}
