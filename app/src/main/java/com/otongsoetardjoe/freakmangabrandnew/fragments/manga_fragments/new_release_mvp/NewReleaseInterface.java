package com.otongsoetardjoe.freakmangabrandnew.fragments.manga_fragments.new_release_mvp;

import com.otongsoetardjoe.freakmangabrandnew.models.HenModel;

import java.util.List;

public interface NewReleaseInterface {
    void onGetDataSuccess(List<HenModel> henModelList, String hitStatus);

    void onGetDataFailed();
}
