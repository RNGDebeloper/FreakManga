package com.otongsoetardjoe.freakmangabrandnew.fragments.anime_fragments.neko_new_releases_mvp;


import com.otongsoetardjoe.freakmangabrandnew.models.NekoModel;

import java.util.List;

public interface NekoNewReleasesInterface {
    void onGetDataSuccess(List<NekoModel> nekoModelList, String hitStatus);

    void onGetDataFailed();
}
