package nguyenpham.com.musicstar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Quang Nguyen on 8/15/2017.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                PersonalFragment personalFragment = new PersonalFragment();
                return personalFragment;

            default: return null;
        }

    }

    @Override
    public int getCount() {
        return 2;
    }
}
