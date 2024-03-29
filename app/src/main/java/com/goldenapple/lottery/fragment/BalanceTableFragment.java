package com.goldenapple.lottery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.goldenapple.lottery.R;
import com.goldenapple.lottery.app.BaseFragment;

import butterknife.BindView;

/**
 * 资金明细table页
 * Created by Alashi on 2016/3/17.
 */
public class BalanceTableFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {

    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    private int [] ids = new int[]{ R.id.radioButton1, R.id.radioButton2, R.id.radioButton3,
        R.id.radioButton4, R.id.radioButton5 };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflateView(inflater, container, "资金明细", R.layout.balance,true,true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        radioGroup.setOnCheckedChangeListener(this);
        viewPager.setAdapter(new MyAdapter(getActivity().getSupportFragmentManager()));
        viewPager.setOnPageChangeListener(this);
        selectPage(0);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        for (int index = 0; index < ids.length; index++) {
            if (ids[index] == checkedId) {
                selectPage(index);
                return;
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        selectPage(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void selectPage(int position) {
        radioGroup.check(radioGroup.getChildAt(position).getId());
        viewPager.setCurrentItem(position, true);
    }

    private class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            BalanceDetailsFragment fragment = (BalanceDetailsFragment) Fragment.instantiate(getActivity(), BalanceDetailsFragment.class.getName());
            fragment.setOrderType(position);
            return fragment;
        }

        @Override
        public int getCount() {
            return 5;
        }
    }
}
