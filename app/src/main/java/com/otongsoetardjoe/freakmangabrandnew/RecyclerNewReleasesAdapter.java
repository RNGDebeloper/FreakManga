package com.otongsoetardjoe.freakmangabrandnew;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.otongsoetardjoe.freakmangabrandnew.databinding.HenListItemBinding;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class RecyclerNewReleasesAdapter extends RecyclerView.Adapter<RecyclerNewReleasesAdapter.ViewHolder> {
    private Context context;
    private List<HenModel> henModelList;

    public RecyclerNewReleasesAdapter(Context context, List<HenModel> henModelList) {
        this.context = context;
        this.henModelList = henModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        HenListItemBinding itemListBinding = HenListItemBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(itemListBinding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        populateList(holder, position);
    }

    private void populateList(ViewHolder holder, int position) {
        holder.itemListBinding.textTitle.setText(henModelList.get(position).getMangaTitle());
        try {
            Glide.with(context)
                    .asDrawable()
                    .load(new URL(henModelList.get(position).getMangaThumbURL()))
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
//                Toast.makeText(context, henModelList.get(position).getMangaURL().substring(henModelList.get(position).getMangaURL().length() - 7), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context.getApplicationContext(), SearchNuclearActivity.class);
                intent.putExtra("nuclearCode", henModelList.get(position).getMangaURL().substring(henModelList.get(position).getMangaURL().length() - 7));
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return henModelList == null ? 0 : henModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private HenListItemBinding itemListBinding;

        public ViewHolder(final HenListItemBinding itemViewList) {
            super(itemViewList.getRoot());
            this.itemListBinding = itemViewList;
        }
    }

}
