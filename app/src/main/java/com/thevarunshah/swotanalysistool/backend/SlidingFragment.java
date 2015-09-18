//package com.thevarunshah.swotanalysistool.backend;
//
//import com.thevarunshah.swotanalysistool.R;
//import com.thevarunshah.swotanalysistool.R.id;
//import com.thevarunshah.swotanalysistool.R.layout;
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.view.ViewPager;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//public class SlidingFragment extends Fragment {
//
//	private SlidingTabLayout mSlidingTabLayout;
//
//	private ViewPager mViewPager;
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		return inflater.inflate(R.layout.swot_fragment, container, false);
//	}
//
//	@Override
//	public void onViewCreated(View view, Bundle savedInstanceState) {
//		setUpPager(view);
//		setUpTabColor();
//	}
//	void setUpPager(View view){
//		mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
//		mViewPager.setAdapter(new TabsPagerAdapter(getActivity()));
//		mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
//		mSlidingTabLayout.setViewPager(mViewPager);
//	}
//	void setUpTabColor(){
//		mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
//			@Override
//			public int getIndicatorColor(int position) {
//				return SlidingFragment.this.getResources().getColor(android.R.color.black);
//			}
//			@Override
//			public int getDividerColor(int position) {
//				return SlidingFragment.this.getResources().getColor(android.R.color.black);
//			}
//		});
//	}
//
//}
