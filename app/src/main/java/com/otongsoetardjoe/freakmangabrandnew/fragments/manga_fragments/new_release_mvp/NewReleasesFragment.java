package com.otongsoetardjoe.freakmangabrandnew.fragments.manga_fragments.new_release_mvp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.otongsoetardjoe.freakmangabrandnew.R;
import com.otongsoetardjoe.freakmangabrandnew.adapters.recycler_adapters.RecyclerNewReleasesAdapter;
import com.otongsoetardjoe.freakmangabrandnew.databinding.FragmentNewReleasesBinding;
import com.otongsoetardjoe.freakmangabrandnew.models.HenModel;
import com.otongsoetardjoe.freakmangabrandnew.utils.Const;
import com.otongsoetardjoe.freakmangabrandnew.utils.EndlessRecyclerViewScrollListener;


import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewReleasesFragment extends Fragment implements NewReleaseInterface {
    private FragmentNewReleasesBinding newReleasesBinding;
    private NewReleasePresenter newReleasePresenter = new NewReleasePresenter(this);
    private Context mContext;
    private int pageCount = 1;
    private List<HenModel> henModelArrayList = new ArrayList<>();
    private RecyclerNewReleasesAdapter newReleasesAdapter;
    private ProgressDialog progressDialog;
    private boolean hitAPI = false;

    public NewReleasesFragment() {
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
            getHenData(pageCount++, "newPage");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        newReleasesBinding = FragmentNewReleasesBinding.inflate(inflater, container, false);
        initUI();
        initEvent();
        return newReleasesBinding.getRoot();
    }

    private void initEvent() {
        GridLayoutManager gridLayoutManager = (GridLayoutManager) newReleasesBinding.recylerHen.getLayoutManager();
        newReleasesBinding.recylerHen.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int index, int totalItemsCount, RecyclerView view) {
                getHenData(pageCount++, "newPage");
            }
        });
        newReleasesBinding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                newReleasesBinding.swipeRefresh.setRefreshing(false);
                getHenData(1, "swipeRefresh");
            }
        });
    }

    private void getHenData(int pageCount, String hitStatus) {
        progressDialog.show();
        if (hitStatus.equalsIgnoreCase("swipeRefresh")) {
            if (this.pageCount <= 2) {
                Log.e("minusStatus", "Can't!");
            } else {
                this.pageCount--;
            }
        }
        newReleasePresenter.getHenContent(Const.BASE_URL + String.format(Const.BASE_PAGE_URL, String.valueOf(pageCount)), hitStatus);
    }


    private void initUI() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Be patient please onii-chan, it just take less than a minute :3");


        newReleasesBinding.recylerHen.setHasFixedSize(true);
        newReleasesAdapter = new RecyclerNewReleasesAdapter(getActivity(), henModelArrayList);
        newReleasesBinding.recylerHen.setAdapter(newReleasesAdapter);
    }

    @Override
    public void onGetDataSuccess(List<HenModel> henModelList, String hitStatus) {
        getActivity().runOnUiThread(() -> {
            newReleasesBinding.recylerHen.setVisibility(View.VISIBLE);
            newReleasesBinding.linearError.setVisibility(View.GONE);
            if (hitStatus.equalsIgnoreCase("newPage")) {
                progressDialog.dismiss();
                henModelArrayList.addAll(henModelList);
                newReleasesAdapter.notifyDataSetChanged();
            } else if (hitStatus.equalsIgnoreCase("swipeRefresh")) {
                progressDialog.dismiss();
                if (henModelArrayList != null) {
                    henModelArrayList.clear();
                    henModelArrayList.addAll(henModelList);
                }
                newReleasesAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onGetDataFailed() {
        getActivity().runOnUiThread(() -> {
            progressDialog.dismiss();
            newReleasesBinding.recylerHen.setVisibility(View.GONE);
            Glide.with(mContext).asGif().load(R.raw.aquacry).into(newReleasesBinding.imageError);
            newReleasesBinding.linearError.setVisibility(View.VISIBLE);
        });
    }
}
