package bageviewdemo.yang.com.bageviewdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jauker.widget.BadgeView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private ViewPager myViewPager;
    private FragmentPagerAdapter myFragmentAdapter;
    private List<Fragment> mDatas;

    private TextView mChatTv;
    private TextView mFriendTv;
    private TextView mContactTv;

    private BadgeView mbadgeView;

    private LinearLayout mChatLinearLayout;
    private ImageView mTabline;
    private int mScreen1_3;

    private int mCurrentPageIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

//        初始化文字下标
        initTabLine();
//        初始化界面
        initView();
    }

    private void initTabLine() {
//        初始化文字下标
        mTabline=(ImageView) findViewById(R.id.id_iv_tabline);
//        获取屏幕的高度和宽带
        Display display=getWindow().getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics=new DisplayMetrics();
        display.getMetrics(outMetrics);
        mScreen1_3=outMetrics.widthPixels/3;
        ViewGroup.LayoutParams lp=mTabline.getLayoutParams();
        lp.width=mScreen1_3;
        mTabline.setLayoutParams(lp);
    }

    private void initView() {
        myViewPager=(ViewPager)findViewById(R.id.id_viewPager);
        mChatTv= (TextView) findViewById(R.id.id_tv_chat);
        mFriendTv=(TextView)findViewById(R.id.id_tv_friend);
        mContactTv=(TextView)findViewById(R.id.id_tv_contact);

        mChatLinearLayout=(LinearLayout) findViewById(R.id.id_ll_chat);


        mDatas=new ArrayList<Fragment>();
        ChatMainTabFragement tab1=new ChatMainTabFragement();
        FriendMainTabFragement tab2=new FriendMainTabFragement();
        ContactMainTabFragement tab3=new ContactMainTabFragement();

        mDatas.add(tab1);
        mDatas.add(tab2);
        mDatas.add(tab3);

        myFragmentAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mDatas.get(position);
            }

            @Override
            public int getCount() {
                return mDatas.size();
            }
        };
        myViewPager.setAdapter(myFragmentAdapter);
        myViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LinearLayout.LayoutParams lp= (LinearLayout.LayoutParams) mTabline.getLayoutParams();
                if(mCurrentPageIndex==0&&position==0){//0页->1页
                    lp.leftMargin= (int) (positionOffset*mScreen1_3+mCurrentPageIndex*mScreen1_3);
                }
                else if(mCurrentPageIndex==1&&position==0){//1->0
                    lp.leftMargin= (int) (mCurrentPageIndex*mScreen1_3+(positionOffset-1)*mScreen1_3);
                }
                else if(mCurrentPageIndex==1&&position==1){//1->2
                    lp.leftMargin= (int) (mCurrentPageIndex*mScreen1_3+positionOffset*mScreen1_3);
                }
                else if(mCurrentPageIndex==2&&position==1){//2->1
                    lp.leftMargin= (int) (mCurrentPageIndex*mScreen1_3+(positionOffset-1)*mScreen1_3);
                }

                mTabline.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {
//                初始化三个选项卡字体颜色
                resetTextView();
                switch (position){
                    case 0:
                        if (mbadgeView!=null){
                            mChatLinearLayout.removeView(mbadgeView);
                        }
                        mbadgeView=new BadgeView(MainActivity.this);
                        mbadgeView.setBadgeCount(7);
                        mChatLinearLayout.addView(mbadgeView);
                        mChatTv.setTextColor(Color.parseColor("#008000"));
                        break;
                    case 1:
                        mFriendTv.setTextColor(Color.parseColor("#008000"));

                        break;
                    case 2:
                        mContactTv.setTextColor(Color.parseColor("#008000"));

                        break;
                }

                mCurrentPageIndex=position;

            }



            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
//    重新设定textview的颜色
    private void resetTextView() {
        mChatTv.setTextColor(Color.BLACK);
        mFriendTv.setTextColor(Color.BLACK);
        mContactTv.setTextColor(Color.BLACK);


    }
}
