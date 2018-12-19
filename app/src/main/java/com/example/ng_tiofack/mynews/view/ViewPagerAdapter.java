package com.example.ng_tiofack.mynews.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by <VOTRE-NOM> on <DATE-DU-JOUR>.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentListTitles = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentListTitles.size();
    }

    @Override

    public CharSequence getPageTitle(int position) {
        return mFragmentListTitles.get(position);
    }

    public void AddFragment(Fragment fragment, String Title) {
        mFragmentList.add(fragment);
        mFragmentListTitles.add(Title);
    }
}
