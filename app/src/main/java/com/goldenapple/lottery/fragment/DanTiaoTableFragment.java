package com.goldenapple.lottery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TableLayout;


import com.goldenapple.lottery.R;
import com.goldenapple.lottery.app.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class DanTiaoTableFragment extends BaseFragment {

//    @BindView(R.id.radioGroup)
//    RadioGroup radioGroup;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tablayout)
    TabLayout mTabLayout;



//    private int [] ids = new int[]{ R.id.radioButton1, R.id.radioButton2, R.id.radioButton3,
//        R.id.radioButton4 };

    private List<String> mTitleList = new ArrayList<>();//页卡标题集合

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflateView(inflater, container, "单挑说明", R.layout.dan_tiao,true,true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTitleList.add("时时彩、P3P5");
        mTitleList.add("11选5");
//        mTitleList.add("快三");
//        mTitleList.add("3D值");
        mTitleList.add("快乐十分");
        mTitleList.add("快乐十二");
        mTitleList.add("其他");

        for (int i=0;i<mTitleList.size();i++){
            mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(i)));//添加tab选项
        }

//        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                //tab被选的时候回调
//                viewPager.setCurrentItem(tab.getPosition(),true);
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//                //tab未被选择的时候回调
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//                //tab重新选择的时候回调
//            }
//        });
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        MyAdapter mAdapter= new MyAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来。
        mTabLayout.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器

        selectPage(0);
    }

//    @Override
//    public void onCheckedChanged(RadioGroup group, int checkedId) {
//        for (int index = 0; index < mTitleList.size(); index++) {
//            if (mTitleList[index] == checkedId) {
//                selectPage(index);
//                return;
//            }
//        }
//    }

//    @Override
//    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//    }
//
//    @Override
//    public void onPageSelected(int position) {
//        selectPage(position);
//    }
//
//    @Override
//    public void onPageScrollStateChanged(int state) {
//
//    }

    private void selectPage(int position) {
//        radioGroup.check(radioGroup.getChildAt(position).getId());
        viewPager.setCurrentItem(position, true);
    }

    private class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            DanTiaoDetailFragment fragment = (DanTiaoDetailFragment) Fragment.instantiate(getActivity(), DanTiaoDetailFragment.class.getName());
            fragment.setType(position);
            return fragment;
        }

        @Override
        public int getCount() {
            return mTitleList.size();
        }

        //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);
        }
    }
}
