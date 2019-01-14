package com.goldenapple.lottery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goldenapple.lottery.R;
import com.goldenapple.lottery.app.BaseFragment;
import com.goldenapple.lottery.app.FragmentLauncher;

import butterknife.OnClick;

public class AGFragment  extends BaseFragment {
    private static final String TAG = AGFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ag, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @OnClick(R.id.enterbutton)
    public void enterButton(){
        FragmentLauncher.launch(getActivity(), GameAgFragment.class);
    }

}
