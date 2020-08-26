package com.otongsoetardjoe.freakmangabrandnew.fragments.anime_fragments.neko_new_releases_mvp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.otongsoetardjoe.freakmangabrandnew.R;
import com.otongsoetardjoe.freakmangabrandnew.adapters.recycler_adapters.RecyclerNekoNewReleasesAdapter;
import com.otongsoetardjoe.freakmangabrandnew.databinding.FragmentNekoNewReleasesBinding;
import com.otongsoetardjoe.freakmangabrandnew.models.NekoModel;
import com.otongsoetardjoe.freakmangabrandnew.utils.Const;
import com.otongsoetardjoe.freakmangabrandnew.utils.EndlessRecyclerViewScrollListener;


import java.util.ArrayList;
import java.util.List;

public class NekoNewReleasesFragment extends Fragment implements NekoNewReleasesInterface {
    private FragmentNekoNewReleasesBinding mBinding;
    private NekoNewReleasesPresenter newReleasePresenter = new NekoNewReleasesPresenter(this);
    private Context mContext;
    private int pageCount = 1;
    private List<NekoModel> henModelArrayList = new ArrayList<>();
    private RecyclerNekoNewReleasesAdapter newReleasesAdapter;
    private ProgressDialog progressDialog;
    private boolean hitAPI = false;

    public NekoNewReleasesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!hitAPI) {
            hitAPI = true;
            getNekoData(pageCount++, "newPage");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void getNekoData(int pageCount, String hitStatus) {
        progressDialog.show();
        if (hitStatus.equalsIgnoreCase("swipeRefresh")) {
            if (this.pageCount <= 2) {
                Log.e("minusStatus", "Can't!");
            } else {
                this.pageCount--;
            }
        }
        newReleasePresenter.getNekoContent(String.format(Const.BASE_PAGE_NEKO_HEN, pageCount), hitStatus);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentNekoNewReleasesBinding.inflate(inflater, container, false);
        initUI();
        initEvent();
//        String nekoWatchSampleURL = "https://nekopoi.care/uncensored-inbo-the-sleazy-family-episode-3-subtitle-indonesia/";
//        String nekoHenURLDepan = "https://nekopoi.care/category/hentai/";
        return mBinding.getRoot();
    }

    private void initEvent() {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mBinding.recylerHen.getLayoutManager();
        mBinding.recylerHen.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int index, int totalItemsCount, RecyclerView view) {
                getNekoData(pageCount++, "newPage");
            }
        });
        mBinding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mBinding.swipeRefresh.setRefreshing(false);
                getNekoData(1, "swipeRefresh");
            }
        });
    }

    private void initUI() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Be patient please onii-chan, it just take less than a minute :3");


        mBinding.recylerHen.setHasFixedSize(true);
        newReleasesAdapter = new RecyclerNekoNewReleasesAdapter(getActivity(), henModelArrayList);
        mBinding.recylerHen.setAdapter(newReleasesAdapter);
    }

    @Override
    public void onGetDataSuccess(List<NekoModel> nekoModelList, String hitStatus) {
        getActivity().runOnUiThread(() -> {
            mBinding.recylerHen.setVisibility(View.VISIBLE);
            mBinding.linearError.setVisibility(View.GONE);
            if (hitStatus.equalsIgnoreCase("newPage")) {
                progressDialog.dismiss();
                henModelArrayList.addAll(nekoModelList);
                newReleasesAdapter.notifyDataSetChanged();
            } else if (hitStatus.equalsIgnoreCase("swipeRefresh")) {
                progressDialog.dismiss();
                if (henModelArrayList != null) {
                    henModelArrayList.clear();
                    henModelArrayList.addAll(nekoModelList);
                }
                newReleasesAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onGetDataFailed() {
        getActivity().runOnUiThread(() -> {
            progressDialog.dismiss();
            mBinding.recylerHen.setVisibility(View.GONE);
            Glide.with(mContext).asGif().load(R.raw.aquacry).into(mBinding.imageError);
            mBinding.linearError.setVisibility(View.VISIBLE);
        });
    }
}