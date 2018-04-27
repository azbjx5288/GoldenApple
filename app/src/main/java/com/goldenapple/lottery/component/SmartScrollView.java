package com.goldenapple.lottery.component;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;

public class SmartScrollView extends ScrollView {

    private static final int ANIMATION_SCREEN_SET_DURATION_MILLIS = 500;
    // 必须轻扫以显示页面更改的屏幕分数（1 / x）
    private static final int FRACTION_OF_SCREEN_WIDTH_FOR_SWIPE = 4;
    private static final int INVALID_SCREEN = -1;
    /**
     *   * 刷卡速度（以每秒钟密度无关的像素数）强制刷卡
     *   * 下一个/上一个屏幕。 在init中调整到mDensityAdjustedSnapVelocity。
     *   
     */
    private static final int SNAP_VELOCITY_DIP_PER_SECOND = 600;
    // 参数getVelocity单位给像素每秒（1 =像素每毫秒）。
    private static final int VELOCITY_UNIT_PIXELS_PER_SECOND = 1000;

    private static final int TOUCH_STATE_REST = 0;
    //    private static final int TOUCH_STATE_HORIZONTAL_SCROLLING = 1;
    private static final int TOUCH_STATE_VERTICAL_SCROLLING = -1;
    private int mCurrentScreen;
    private int mDensityAdjustedSnapVelocity;
    private boolean mFirstLayout = true;
    private float mLastMotionX;
    private float mLastMotionY;
    //private OnScreenSwitchListener mOnScreenSwitchListener;
    private int mMaximumVelocity;
    private int mNextScreen = INVALID_SCREEN;
    private Scroller mScroller;
    private ExpandableLayout layout;
    private int mTouchSlop;
    private int mTouchState = TOUCH_STATE_REST;
    private VelocityTracker mVelocityTracker;
    private int mLastSeenLayoutWidth = -1;

    public SmartScrollView(Context context) {
        super(context);
        init();
    }

    public SmartScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public SmartScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setHeadView(ExpandableLayout layout) {
        this.layout = layout;
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
        // 用像素计算密度相关的捕捉速度
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        mDensityAdjustedSnapVelocity = (int) (displayMetrics.density * SNAP_VELOCITY_DIP_PER_SECOND);
        final ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mTouchSlop = configuration.getScaledTouchSlop();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
    }

    @Override
    public boolean onInterceptTouchEvent(final MotionEvent ev) {
        final int action = ev.getAction();
        boolean intercept = false;
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                if (mTouchState == TOUCH_STATE_VERTICAL_SCROLLING) {
                    /**
                     * 我们已经开始了一个水平滚动; 设置拦截为真，所以我们可以
                     * 在onTouchEvent中获取所有触摸事件的其余部分。
                     */
                    intercept = true;
                } else { // 还没有收到一个滚动事件; 检查一个。
                    /**
                     * 如果我们检测到水平滚动事件，请开始窃取触摸事件（标记
                     * 作为滚动）。 否则，看看我们是否有垂直滚动事件 - 如果是的话，让
                     * 孩子们处理它，并且直到动议结束时才再次拦截
                     * 完成。
                     */
                    final float x = ev.getX();
                    final int xDiff = (int) Math.abs(x - mLastMotionX);
                    final float y = ev.getY();
                    final int yDiff = (int) Math.abs(y - mLastMotionY);
                    Log.e("yMoved","yMoved->"+yDiff+"<------>mTouchSlop->"+mTouchSlop);
                    if (yDiff > mTouchSlop) {
                        if (yDiff > xDiff) {
                            mTouchState = TOUCH_STATE_VERTICAL_SCROLLING;
                        }
                        mLastMotionY = y;
                        if (layout != null ) {
                            if (!layout.isExpanded()) {
                                Log.e("MultiScroll-->", "展开");
                                layout.expand();
                            }
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                // 释放拖动。
                mTouchState = TOUCH_STATE_REST;
                intercept = false;
                break;
            case MotionEvent.ACTION_DOWN:
                /**
                 * 没有动作，但注册坐标，以便我们可以检查拦截
                 * 下一个MOVE事件。
                 */
                //Log.i("ViewPager-->", "Action_Down");
                mTouchState = TOUCH_STATE_REST;
                mLastMotionY = ev.getY();
                mLastMotionX = ev.getX();
                break;
            default:
                break;
        }

        Log.i("MultiScroll-->", intercept + "");
        return intercept;
    }
}