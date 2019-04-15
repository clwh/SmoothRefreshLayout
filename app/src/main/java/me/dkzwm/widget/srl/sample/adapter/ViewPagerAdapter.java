package me.dkzwm.widget.srl.sample.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by dkzwm on 2017/6/2.
 *
 * @author dkzwm
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mViewPagerFragments;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        mViewPagerFragments = list;
    }

    public  void addFragment(Fragment fragment) {
        mViewPagerFragments.add(fragment);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return mViewPagerFragments.get(position);
    }

    @Override
    public int getCount() {
        return mViewPagerFragments.size();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
