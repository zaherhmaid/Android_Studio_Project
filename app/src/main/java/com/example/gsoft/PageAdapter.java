package com.example.gsoft;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PageAdapter extends FragmentStatePagerAdapter {
    int mNoOfTabs;

    public PageAdapter(FragmentManager fm, int NumberOfTabs) {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ActiviteFragment activiteFragment = new ActiviteFragment();
                return activiteFragment;

            case 1:
                PrixFragment prixFragment = new PrixFragment();
                return prixFragment;
            case 2:
                StockFragment stockFragment = new StockFragment();
                return stockFragment;
            default:

                return null;
        }

    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}


