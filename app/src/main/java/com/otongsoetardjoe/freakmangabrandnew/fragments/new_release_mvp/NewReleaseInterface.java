package com.otongsoetardjoe.freakmangabrandnew.fragments.new_release_mvp;

import com.otongsoetardjoe.freakmangabrandnew.HenModel;

import java.util.List;

public interface NewReleaseInterface {
    void onGetDataSuccess(List<HenModel> henModelList, String hitStatus);

    void onGetDataFailed();
}
