package com.example.shin.disposisi.FileKadis;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapterKadis extends FragmentPagerAdapter {

    private final List<Fragment> ListFragment = new ArrayList<>();
    private final List<String> ListJudul = new ArrayList<>();

    public ViewPagerAdapterKadis(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ListFragment.get(position);
    }

    @Override
    public int getCount() {
        return ListJudul.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return ListJudul.get(position);
    }

    public void TambahFragmentKadis(Fragment fragment, String judul){
        ListFragment.add(fragment);
        ListJudul.add(judul);
    }


}
