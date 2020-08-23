package com.otongsoetardjoe.freakmangabrandnew.activities.search_or_read_mvp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.otongsoetardjoe.freakmangabrandnew.utils.Const;
import com.otongsoetardjoe.freakmangabrandnew.adapters.recycler_adapters.RecyclerReadHenAdapter;
import com.otongsoetardjoe.freakmangabrandnew.databinding.ActivitySearchNuclearBinding;
import com.otongsoetardjoe.freakmangabrandnew.fragments.manga_fragments.new_release_mvp.NewReleasePresenter;

import java.util.ArrayList;
import java.util.List;

public class SearchNuclearActivity extends AppCompatActivity implements ReadHenInterface {
    private ActivitySearchNuclearBinding nuclearBinding;
    private NewReleasePresenter newReleasePresenter = new NewReleasePresenter(this);
    private List<String> contentList = new ArrayList<>();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nuclearBinding = ActivitySearchNuclearBinding.inflate(getLayoutInflater());
        setContentView(nuclearBinding.getRoot());
        progressDialog = new ProgressDialog(SearchNuclearActivity.this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Be patient please onii-chan, it just take less than a minute :3");
        nuclearBinding.recylerImages.setHasFixedSize(true);

        if (getIntent() != null) {
            String vidLink = getIntent().getStringExtra("vidLink");
            if (vidLink != null) {
                nuclearBinding.webViewTest.setVisibility(View.VISIBLE);
                nuclearBinding.webViewTest.getSettings().setJavaScriptEnabled(true);
                nuclearBinding.webViewTest.loadUrl(vidLink);

                nuclearBinding.editTextNuclear.setVisibility(View.GONE);
                nuclearBinding.linearError.setVisibility(View.GONE);
                nuclearBinding.buttonNuclearResult.setVisibility(View.GONE);
                nuclearBinding.recylerImages.setVisibility(View.GONE);
            } else {
                String nuclearCode = getIntent().getStringExtra("nuclearCode");
                if (nuclearCode != null && nuclearCode.contains("/")) {
                    progressDialog.show();
                    newReleasePresenter.getHenImage(Const.BASE_URL + "/g/" + nuclearCode, "");
                    nuclearBinding.editTextNuclear.setText(nuclearCode.replace("/", ""));
                }
            }
        }
        initEvent();
    }

    private void initEvent() {
        nuclearBinding.buttonNuclearResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nuclearBinding.buttonNuclearResult.setClickable(false);
                nuclearBinding.buttonNuclearResult.setEnabled(false);
                if (nuclearBinding.editTextNuclear.getText().toString().isEmpty() || nuclearBinding.editTextNuclear.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(SearchNuclearActivity.this, "Diisi dulu cuk kodenya", Toast.LENGTH_SHORT).show();
//                } else if (nuclearBinding.editTextNuclear.getText().toString().length() > 4) {
                } else if (nuclearBinding.editTextNuclear.getText().toString().length() > 6) {
                    Toast.makeText(SearchNuclearActivity.this, "Kurangi lagi cuk, max 6 karakter!", Toast.LENGTH_SHORT).show();
                } else {
//                    progressDialog.show();
                    newReleasePresenter.getHenImage(Const.BASE_URL + "/g/" + nuclearBinding.editTextNuclear.getText().toString() + "/", "");
//                    newReleasePresenter.getHenImage(Const.BASE_URL_HENCAFE + "/hc.fyi/" + nuclearBinding.editTextNuclear.getText().toString() + "/", "");
                }
            }
        });
    }

    @Override
    public void onGetImageSuccess(List<String> henModelList) {
        runOnUiThread(() -> {
            progressDialog.dismiss();
            nuclearBinding.buttonNuclearResult.setClickable(true);
            nuclearBinding.buttonNuclearResult.setEnabled(true);
            if (contentList != null) {
                contentList.clear();
            }
            nuclearBinding.recylerImages.setVisibility(View.VISIBLE);
            nuclearBinding.linearError.setVisibility(View.GONE);
            contentList = henModelList;
            nuclearBinding.recylerImages.setAdapter(new RecyclerReadHenAdapter(SearchNuclearActivity.this, contentList));
        });
    }

    @Override
    public void onGetDataFailed() {
        runOnUiThread(() -> {
            nuclearBinding.buttonNuclearResult.setClickable(true);
            nuclearBinding.buttonNuclearResult.setEnabled(true);
            progressDialog.dismiss();
            nuclearBinding.recylerImages.setVisibility(View.GONE);
            nuclearBinding.linearError.setVisibility(View.VISIBLE);
            Toast.makeText(SearchNuclearActivity.this, "Gagal!!", Toast.LENGTH_SHORT).show();
        });
    }
}
