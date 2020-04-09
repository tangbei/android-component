package com.tang.component.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.tang.component.R;
import com.tang.component.databinding.FragmentHomeBinding;
import com.tang.component.network.base.CommonNetWorkApi;
import com.tang.component.network.beans.BaseResponse;
import com.tang.component.network.environment.EnvironmentActivity;
import com.tang.component.network.observer.BaseObserver;
import com.tang.component.service.ApiService;
import com.tang.component.service.TestBean;

import java.util.List;

/**
 * 描述:
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2020/3/17.
 */
public class HomeFragment extends Fragment {

    private FragmentHomeBinding homeBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homeBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false);
        return homeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeBinding.btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonNetWorkApi.getService(ApiService.class).getIndex()
                        .compose(CommonNetWorkApi.getTransformer(
                                new BaseObserver<BaseResponse<List<TestBean>>>()
                        ));
            }
        });

        homeBinding.btnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeFragment.this.getActivity(), EnvironmentActivity.class));
            }
        });
    }
}
