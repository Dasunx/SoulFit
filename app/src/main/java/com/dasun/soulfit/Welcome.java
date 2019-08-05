package com.dasun.soulfit;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Welcome extends AppCompatActivity {
    private ViewPager mSlideView;
    private LinearLayout mDotLayer;

    private TextView[] mDots;
    private SliderAdapter sliderAdapter;

    private Button mNextBtn;
    private Button mBackBtn;

    private int mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        mSlideView = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayer = (LinearLayout) findViewById(R.id.dotsLayout);

        mNextBtn = (Button) findViewById(R.id.nextbtn);
        mBackBtn = (Button) findViewById(R.id.backbtn);

        sliderAdapter = new SliderAdapter(this);

        mSlideView.setAdapter(sliderAdapter);

        addDotIndicator(0);
        mSlideView.addOnPageChangeListener(viewListner);

        mNextBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if(mCurrentPage==(mDots.length-1)){
                    passLogin();
                }else {
                    mSlideView.setCurrentItem(mCurrentPage + 1);
                }
            }
        });

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlideView.setCurrentItem(mCurrentPage -1);
            }
        });
    }

    public void addDotIndicator(int position){
        mDots = new TextView[3];
        mDotLayer.removeAllViews();

        for(int i=0; i<mDots.length;i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.realgrey));

            mDotLayer.addView(mDots[i]);
        }

        if(mDots.length > 0 ){
            mDots[position].setTextColor(getResources().getColor(R.color.main_green));
        }
    }

    ViewPager.OnPageChangeListener viewListner =new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotIndicator(position);
            mCurrentPage = position;

            if(position == 0){
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(false);
                mBackBtn.setVisibility(View.INVISIBLE);

                mNextBtn.setText("Next");
                mBackBtn.setText(" ");
            }else if(position == (mDots.length -1) ){

                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                mBackBtn.setVisibility(View.VISIBLE);

                mNextBtn.setText("Finish");
                mBackBtn.setText("Back");
            }else{
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                mBackBtn.setVisibility(View.VISIBLE);

                mNextBtn.setText("Next");
                mBackBtn.setText("Back");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public void passLogin(){
        Intent myInt= new Intent(this,MainActivity.class);
        startActivity(myInt);

    }
}
