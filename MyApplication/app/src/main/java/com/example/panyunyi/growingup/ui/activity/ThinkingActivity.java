package com.example.panyunyi.growingup.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.webkit.WebView;
import android.widget.TextView;

import com.example.panyunyi.growingup.Constant;
import com.example.panyunyi.growingup.ui.base.BaseActivity;
import com.example.panyunyi.growingup.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ThinkingActivity extends BaseActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    @BindView(R.id.text1)
    TextView textViewOfOne;
    @BindView(R.id.text3)
    TextView textViewOfThree;
    @BindView(R.id.text4)
    TextView textViewOfFour;
    @BindView(R.id.text2)
    TextView textViewOfTwo;

    @BindView(R.id.container)
    ViewPager mViewPager;

    private TextView[] view;
    private Context context;
    private int pos;// 记录当前到达的位置
    static int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thinking);
        ButterKnife.bind(this);






        textViewOfOne.setBackgroundColor(Color.parseColor("#808080"));
        view = new TextView[]{textViewOfOne, textViewOfTwo, textViewOfThree, textViewOfFour};
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pos=position;
                for (int i = 0; i < 4; i++) {
                    if (position != i) {
                        view[i].setBackgroundColor(Color.parseColor("#302833"));
                        view[i].setTextColor(Color.parseColor("#ffffff"));
                    } else {
                        view[i].setBackgroundColor(Color.parseColor("#808080"));
                        view[i].setTextColor(Color.parseColor("#ee7800"));

                    }

                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "开始进行预约", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent();
                intent.setClass(context, OrderActivity.class);
                intent.putExtra("className", pos);
                startActivity(intent);
            }
        });*/

    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_thinking, container, false);

            WebView webView = (WebView) rootView.findViewById(R.id.thinking_activity_webview);
            //解决乱码问题
            //webView.getSettings().setDefaultTextEncodingName("UTF-8");//设置默认为utf-8
            switch (count%4){
                case 0:
                    webView.loadUrl(Constant.API_URL+"/showThinkingActivity?num=1");
                    //webView.loadData(getResources().getString(R.string.activity_thinking1), "text/html; charset=UTF-8",null);
                    break;
                case 1:
                    webView.loadUrl(Constant.API_URL+"/showThinkingActivity?num=2");
                    //webView.loadData(getResources().getString(R.string.activity_thinking2), "text/html; charset=UTF-8",null);
                    break;
                case 2:
                    webView.loadUrl(Constant.API_URL+"/showThinkingActivity?num=3");
                    //webView.loadData(getResources().getString(R.string.activity_thinking3), "text/html; charset=UTF-8",null);
                    break;
                case 3:
                    webView.loadUrl(Constant.API_URL+"/showThinkingActivity?num=4");
                    //webView.loadData(getResources().getString(R.string.activity_thinking4), "text/html; charset=UTF-8",null);
                    break;

            }
            count++;
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
                case 3:
                    return "SECTION 4";
            }
            return null;
        }
        }
    @OnClick({R.id.text1,R.id.text2,R.id.text3,R.id.text4})
    void clickTitle(View v){
        switch (v.getId()){
            case R.id.text1:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.text2:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.text3:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.text4:
                mViewPager.setCurrentItem(3);
                break;

        }
    }
}
