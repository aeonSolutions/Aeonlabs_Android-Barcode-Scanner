package aeonlabs.barcodescanner.common.libraries.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import aeonlabs.barcodescanner.common.libraries.R;
import aeonlabs.common.libraries.PlugIns.BarCodeScanner.FragmentBarCodeScanner;
import aeonlabs.common.libraries.activities.FragmentBase;
import aeonlabs.common.libraries.activities.FragmentBaseObserver;


public class ViewPagerFragment extends FragmentBase implements FragmentBaseObserver {

    private Button btnScan ;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    public ViewPagerFragment(){
        TAG= "ViewPagerFragment";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_viewpager, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnScan = view.findViewById(R.id.btnScan);
        viewPager = view.findViewById(R.id.viewpager);

        setupViewPager(viewPager);
        viewPager.setCurrentItem(0);
    }

    @Override
    public void notifyObserversFragment(String... params) {
        ((FragmentBase) adapter.getCurrentFragment()).notifyObserversFragment("update");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new FragmentBarCodeScanner(), "Barcode Scanner");
        adapter.addFragment(new BarCodeListFragment(), "Scan Item");
        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }
        private Fragment mCurrentFragment;

        public Fragment getCurrentFragment() {
            return mCurrentFragment;
        }
        //...
        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            if (getCurrentFragment() != object) {
                mCurrentFragment = ((Fragment) object);
            }
            super.setPrimaryItem(container, position, object);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
