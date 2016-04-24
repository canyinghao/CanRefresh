package com.canyinghao.canrefresh;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Scroller;

/**
 * Copyright 2016 canyinghao
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class CanRefreshLayout extends ViewGroup {

    public static String TAG = CanRefreshLayout.class.getSimpleName();

    //  默认刷新时间
    private static final int DEFAULT_DURATION = 300;

    //  自动刷新时间
    private static final int DEFAULT_AUTO_DURATION = 100;
    //    默认摩擦系数
    private static final float DEFAULT_FRICTION = 0.5f;
    //   刷新完成时，默认平滑滚动单位距离  除CLASSIC外有效
    private static final int DEFAULT_SMOOTH_LENGTH = 50;
    //   刷新完成时，默认平滑滚动单位时间 除CLASSIC外有效
    private static final int DEFAULT_SMOOTH_DURATION = 3;


    //  通过触摸判断滑动方向
    private static byte NO_SCROLL = 0;
    private static byte NO_SCROLL_UP = 1;
    private static byte NO_SCROLL_DOWN = 2;

    // 风格
    public static final byte CLASSIC = 0;
    public static final byte UPPER = 1;
    public static final byte LOWER = 2;
    public static final byte MID = 3;


    //  头部
    protected View mHeaderView;
    //  底部
    protected View mFooterView;
    //    内容
    protected View mContentView;
    //    头部高度
    protected int mHeaderHeight;
    //    底部高度
    protected int mFooterHeight;

    private boolean isSetHeaderHeight;
    private boolean isSetFooterHeight;
//    是否在刷新中
    private boolean isHeaderRefreshing;
    private boolean isFooterRefreshing;


    //    摩擦系数
    private float mFriction = DEFAULT_FRICTION;
    //    是否可下拉
    private boolean mRefreshEnabled = true;
    //    是否可上拉
    private boolean mLoadMoreEnabled = true;
    //   下拉监听
    protected OnRefreshListener mOnRefreshListener;
    //    上拉监听
    protected OnLoadMoreListener mOnLoadMoreListener;
    //  下拉风格
    private int mHeadStyle = CLASSIC;
    //    上拉风格
    private int mFootStyle = CLASSIC;

    //    刷新时间
    private int mDuration = DEFAULT_DURATION;
    //    平滑滚动单位距离  除CLASSIC外有效
    private int mSmoothLength = DEFAULT_SMOOTH_LENGTH;
    //    平滑滚动单位时间 除CLASSIC外有效
    private int mSmoothDuration = DEFAULT_SMOOTH_DURATION;


    //  不可滑动view的滑动方向
    private int isUpOrDown = NO_SCROLL;
    //  判断y轴方向的存储值
    float directionX;
    //   判断x轴方向存储值
    float directionY;
    //   下拉偏移
    private int mHeadOffY;
    //    上拉偏移
    private int mFootOffY;
    //    内容偏移
    private int mContentOffY;
    //  最后一次触摸的位置
    private float lastY;
    //  偏移
    private int currentOffSetY;
    //  触摸移动的位置
    private int offsetSum;
    //    触摸移动的位置之和
    private int scrollSum;
    //  一个缓存值
    private int tempY;


    private Scroller mScroller = new Scroller(getContext());


    public CanRefreshLayout(Context context) {
        this(context, null);
    }

    public CanRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CanRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CanRefreshLayout, defStyleAttr, 0);

        try {
            final int N = a.getIndexCount();
            for (int i = 0; i < N; i++) {
                int attr = a.getIndex(i);
                if (attr == R.styleable.CanRefreshLayout_can_enabled_up) {
                    setRefreshEnabled(a.getBoolean(attr, true));

                } else if (attr == R.styleable.CanRefreshLayout_can_enabled_down) {
                    setLoadMoreEnabled(a.getBoolean(attr, true));

                } else if (attr == R.styleable.CanRefreshLayout_can_style_up) {
                    mHeadStyle = a.getInt(attr, CLASSIC);

                } else if (attr == R.styleable.CanRefreshLayout_can_style_down) {
                    mFootStyle = a.getInt(attr, CLASSIC);

                } else if (attr == R.styleable.CanRefreshLayout_can_friction) {

                    setFriction(a.getFloat(attr, DEFAULT_FRICTION));

                } else if (attr == R.styleable.CanRefreshLayout_can_duration) {

                    mDuration = a.getInt(attr, DEFAULT_DURATION);

                } else if (attr == R.styleable.CanRefreshLayout_can_smooth_duration) {

                    mSmoothDuration = a.getInt(attr, DEFAULT_SMOOTH_DURATION);

                } else if (attr == R.styleable.CanRefreshLayout_can_smooth_length) {

                    mSmoothLength = a.getInt(attr, DEFAULT_SMOOTH_LENGTH);

                }
            }


        } finally {
            a.recycle();
        }


    }


    /**
     * 自己设置高度时不使用自动获取高度
     * @param mHeaderHeight
     */
    public void setHeaderHeight(int mHeaderHeight) {
        this.mHeaderHeight = mHeaderHeight;
        isSetHeaderHeight = true;
    }
    /**
     * 自己设置高度时不使用自动获取高度
     * @param mFooterHeight
     */
    public void setFooterHeight(int mFooterHeight) {
        this.mFooterHeight = mFooterHeight;
        isSetFooterHeight = true;
    }

    /**
     * 设置是否可下拉刷新
     *
     * @param enable
     */
    public void setRefreshEnabled(boolean enable) {
        this.mRefreshEnabled = enable;
    }


    /**
     * 设置是否可上拉加载
     *
     * @param enable
     */
    public void setLoadMoreEnabled(boolean enable) {
        this.mLoadMoreEnabled = enable;
    }


    /**
     * 设置刷新监听
     *
     * @param mOnRefreshListener
     */
    public void setOnRefreshListener(@NonNull OnRefreshListener mOnRefreshListener) {
        this.mOnRefreshListener = mOnRefreshListener;
    }


    /**
     * 设置加载更多监听
     *
     * @param mOnLoadMoreListener
     */
    public void setOnLoadMoreListener(@NonNull OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }


    /**
     * 设置摩擦系数
     *
     * @param mFriction
     */
    public void setFriction(@FloatRange(from = 0.0, to = 1.0) float mFriction) {

        this.mFriction = mFriction;

    }

    /**
     * 设置默认刷新时间
     *
     * @param mDuration
     */
    public void setDuration(int mDuration) {
        this.mDuration = mDuration;
    }

    /**
     * 设置滑动单位距离
     *
     * @param mSmoothLength
     */
    public void setSmoothLength(int mSmoothLength) {
        this.mSmoothLength = mSmoothLength;
    }

    /**
     * 设置滑动单位时间
     *
     * @param mSmoothDuration
     */
    public void setSmoothDuration(int mSmoothDuration) {
        this.mSmoothDuration = mSmoothDuration;
    }


    /**
     * 设置风格
     *
     * @param headStyle
     * @param footStyle
     */
    public void setStyle(@IntRange(from = 0, to = 3) int headStyle, @IntRange(from = 0, to = 3) int footStyle) {

        this.mHeadStyle = headStyle;
        this.mFootStyle = footStyle;


        if (mHeadStyle == LOWER
                || mHeadStyle == MID) {


            mContentView.bringToFront();

        }

        if (mFootStyle == LOWER
                || mFootStyle == MID) {

            mContentView.bringToFront();

        }


        if (mHeaderView != null && mHeadStyle == CLASSIC
                || mHeadStyle == UPPER) {

            mHeaderView.bringToFront();


        }

        if (mFooterView != null && mFootStyle == CLASSIC
                || mFootStyle == UPPER) {

            mFooterView.bringToFront();

        }


    }


    /**
     * 通过id得到相应的view
     */
    @Override
    protected void onFinishInflate() {

        final int childCount = getChildCount();

        if (childCount > 0) {
            mHeaderView = findViewById(R.id.can_refresh_header);
            mContentView = findViewById(R.id.can_content_view);
            mFooterView = findViewById(R.id.can_refresh_footer);

        }

        if (mContentView == null) {
            throw new IllegalStateException("error");
        }

        if (mHeaderView != null && !(mHeaderView instanceof CanRefresh)) {

            throw new IllegalStateException("error");
        }
        if (mFooterView != null && !(mFooterView instanceof CanRefresh)) {

            throw new IllegalStateException("error");
        }

        if (mHeaderView != null) {

            getHeaderInterface().setIsHeaderOrFooter(true);
        }

        if (mFooterView != null) {

            getFooterInterface().setIsHeaderOrFooter(false);
        }


        super.onFinishInflate();

        setStyle(mHeadStyle, mFootStyle);

    }


    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

        childLayout();

    }


    /**
     * 设置上拉下拉中间view的位置
     */
    private void childLayout() {

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();

        if (mHeaderView != null) {
            MarginLayoutParams lp = (MarginLayoutParams) mHeaderView.getLayoutParams();
            final int left = paddingLeft + lp.leftMargin;
            final int top = paddingTop + lp.topMargin - mHeaderHeight + mHeadOffY;
            final int right = left + mHeaderView.getMeasuredWidth();
            final int bottom = top + mHeaderView.getMeasuredHeight();


            mHeaderView.layout(left, top, right, bottom);

        }

        if (mFooterView != null) {
            MarginLayoutParams lp = (MarginLayoutParams) mFooterView.getLayoutParams();
            final int left = paddingLeft + lp.leftMargin;
            final int top = getMeasuredHeight() + paddingTop + lp.topMargin - mFootOffY;
            final int right = left + mFooterView.getMeasuredWidth();
            final int bottom = top + mFooterView.getMeasuredHeight();
            mFooterView.layout(left, top, right, bottom);

        }
        if (mContentView != null) {

            MarginLayoutParams lp = (MarginLayoutParams) mContentView.getLayoutParams();
            final int left = paddingLeft + lp.leftMargin;
            final int top = paddingTop + lp.topMargin + mContentOffY;
            final int right = left + mContentView.getMeasuredWidth();
            final int bottom = top + mContentView.getMeasuredHeight();

            mContentView.layout(left, top, right, bottom);
        }

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        if (mHeaderView != null) {
            measureChildWithMargins(mHeaderView, widthMeasureSpec, 0, heightMeasureSpec, 0);
            MarginLayoutParams lp = (MarginLayoutParams) mHeaderView.getLayoutParams();

            if (!isSetHeaderHeight) {
                mHeaderHeight = mHeaderView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            }


        }

        if (mFooterView != null) {
            measureChildWithMargins(mFooterView, widthMeasureSpec, 0, heightMeasureSpec, 0);
            MarginLayoutParams lp = (MarginLayoutParams) mFooterView.getLayoutParams();

            if (!isSetFooterHeight) {
                mFooterHeight = mFooterView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            }


        }
        if (mContentView != null) {


            measureChildWithMargins(mContentView, widthMeasureSpec, 0, heightMeasureSpec, 0);
        }


    }


    /**
     * 能否刷新
     *
     * @return
     */
    private boolean canRefresh() {


        return !isHeaderRefreshing&&mRefreshEnabled && mHeaderView != null && !canChildScrollUp();
    }

    /**
     * 能否加载更多
     *
     * @return
     */
    private boolean canLoadMore() {


        return !isFooterRefreshing&&mLoadMoreEnabled && mFooterView != null && !canChildScrollDown();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {


        return super.dispatchTouchEvent(e);


    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {


        switch (e.getAction()) {

            case MotionEvent.ACTION_DOWN:

                directionY = e.getY();
                directionX = e.getX();

                break;

            case MotionEvent.ACTION_MOVE:

                if (directionY <= 0 || directionX <= 0) {

                    break;
                }

                float eventY = e.getY();
                float eventX = e.getX();

                float offY = eventY - directionY;
                float offX = eventX - directionX;

                directionY = eventY;
                directionX = eventX;


                boolean moved = Math.abs(offY) > Math.abs(offX);


                if (offY > 0 && moved && canRefresh()) {
                    isUpOrDown = NO_SCROLL_UP;
                } else if (offY < 0 && moved && canLoadMore()) {

                    isUpOrDown = NO_SCROLL_DOWN;
                } else {
                    isUpOrDown = NO_SCROLL;
                }


                if (isUpOrDown == NO_SCROLL_DOWN || isUpOrDown == NO_SCROLL_UP) {

                    return true;
                }


                break;


        }


        return super.onInterceptTouchEvent(e);

    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {

//     当是不可滑动的view里进入
        if (!canChildScrollDown() && !canChildScrollUp()) {


            if (isUpOrDown == NO_SCROLL_UP) {
                if (canRefresh()) {

                    return touch(e, true);

                }
            } else if (isUpOrDown == NO_SCROLL_DOWN) {
                if (canLoadMore()) {

                    return touch(e, false);
                }

            } else {


                switch (e.getAction()) {

                    case MotionEvent.ACTION_DOWN:

                        directionY = e.getY();
                        directionX = e.getX();

                        break;

                    case MotionEvent.ACTION_MOVE:
                        if (directionY <= 0 || directionX <= 0) {

                            break;
                        }

                        float eventY = e.getY();
                        float eventX = e.getX();

                        float offY = eventY - directionY;
                        float offX = eventX - directionX;

                        directionY = eventY;
                        directionX = eventX;


                        boolean moved = Math.abs(offY) > Math.abs(offX);


                        if (offY > 0 && moved && canRefresh()) {
                            isUpOrDown = NO_SCROLL_UP;
                        } else if (offY < 0 && moved && canLoadMore()) {

                            isUpOrDown = NO_SCROLL_DOWN;
                        } else {
                            isUpOrDown = NO_SCROLL;
                        }


                        break;


                }

                return true;

            }


        } else {

            if (canRefresh()) {
                return touch(e, true);

            } else if (canLoadMore()) {


                return touch(e, false);
            }
        }


        return super.onTouchEvent(e);
    }


    /**
     * 触摸滑动处理
     *
     * @param e
     * @param isHead
     * @return
     */
    private boolean touch(MotionEvent e, boolean isHead) {

        switch (e.getAction()) {


            case MotionEvent.ACTION_DOWN:

                lastY = e.getY();
                return true;

            case MotionEvent.ACTION_MOVE:

                if (lastY > 0) {
                    currentOffSetY = (int) (e.getY() - lastY);
                    offsetSum += currentOffSetY;
                }
                lastY = e.getY();

                boolean isCanMove;
                if (isHead) {
                    isCanMove = offsetSum > 0;
                } else {
                    isCanMove = offsetSum < 0;
                }


                if (isCanMove) {

                    float ratio = getRatio();

                    if (ratio < 0) {
                        ratio = 0;
                    }

                    int scrollNum = -((int) (currentOffSetY * ratio));

                    scrollSum += scrollNum;


                    if (isHead) {

                        smoothMove(true, true, scrollNum, scrollSum);


                        if (Math.abs(scrollSum) > mHeaderHeight) {

                            getHeaderInterface().onPrepare();
                        }

                        getHeaderInterface().onPositionChange(Math.abs(scrollSum) / (float) mHeaderHeight);
                    } else {

                        smoothMove(false, true, scrollNum, scrollSum);


                        if (Math.abs(scrollSum) > mFooterHeight) {

                            getFooterInterface().onPrepare();
                        }


                        getFooterInterface().onPositionChange(Math.abs(scrollSum) / (float) mFooterHeight);

                    }


                }


                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:

                if (Math.abs(scrollSum) > 3) {


                    if (isHead) {
                        if (Math.abs(scrollSum) > mHeaderHeight) {


                            smoothMove(true, false, -mHeaderHeight, mHeaderHeight);
                            getHeaderInterface().onRelease();
                            refreshing();
                        } else {

                            smoothMove(true, false, 0, 0);
                        }

                    } else {

                        if (Math.abs(scrollSum) > mFooterHeight) {


                            smoothMove(false, false, mContentView.getMeasuredHeight() - getMeasuredHeight() + mFooterHeight, mFooterHeight);
                            getFooterInterface().onRelease();
                            loadingMore();
                        } else {

                            smoothMove(false, false, mContentView.getMeasuredHeight() - getMeasuredHeight(), 0);

                        }

                    }


                }


                resetParameter();

                break;


        }


        return super.onTouchEvent(e);


    }


    /**
     * 滑动距离越大比率越小，越难拖动
     *
     * @return
     */
    private float getRatio() {

        return 1 - (Math.abs(offsetSum) / (float) getMeasuredHeight()) - 0.3f * mFriction;

    }


    /**
     * 重置参数
     */
    private void resetParameter() {

        directionX = 0;
        directionY = 0;
        isUpOrDown = NO_SCROLL;
        lastY = 0;
        offsetSum = 0;
        scrollSum = 0;

    }


    /**
     * * 滚动布局的方法
     *
     * @param isHeader
     * @param isMove      手指在移动还是已经抬起
     * @param moveScrollY
     * @param moveY
     */
    private void smoothMove(boolean isHeader, boolean isMove, int moveScrollY, int moveY) {

        moveY = Math.abs(moveY);
        if (isHeader) {

            if (mHeadStyle == CLASSIC) {

                if (isMove) {
                    smoothScrollBy(0, moveScrollY);
                } else {

                    smoothScrollTo(0, moveScrollY);
                }
            } else {

                layoutMove(isHeader, isMove, moveY);
            }


        } else {

            if (mFootStyle == CLASSIC) {


                if (isMove) {
                    smoothScrollBy(0, moveScrollY);
                } else {

                    smoothScrollTo(0, moveScrollY);
                }
            } else {


                layoutMove(isHeader, isMove, moveY);
            }

        }


    }

    /**
     * 调用此方法滚动到目标位置
     *
     * @param fx
     * @param fy
     */
    public void smoothScrollTo(int fx, int fy) {
        int dx = fx - mScroller.getFinalX();
        int dy = fy - mScroller.getFinalY();
        smoothScrollBy(dx, dy);
    }

    /**
     * 调用此方法设置滚动的相对偏移
     *
     * @param dx
     * @param dy
     */
    public void smoothScrollBy(int dx, int dy) {


        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);
        invalidate();


    }


    /**
     * 通过设置偏移滚动到目标位置
     *
     * @param isHeader
     * @param isMove
     * @param moveY
     */
    private void layoutMove(boolean isHeader, boolean isMove, int moveY) {

        if (isMove) {


            if (isHeader) {

                if (mHeadStyle == UPPER) {


                    mHeadOffY = moveY;


                } else if (mHeadStyle == LOWER) {


                    mHeadOffY = mHeaderHeight;
                    mContentOffY = moveY;


                } else if (mHeadStyle == MID) {


                    int offY = moveY / 2 + mHeaderHeight / 2;


                    mHeadOffY = offY;
                    mContentOffY = moveY;


                }

            } else {

                if (mFootStyle == UPPER) {


                    mFootOffY = moveY;


                } else if (mFootStyle == LOWER) {


                    mFootOffY = mFooterHeight;
                    mContentOffY = -moveY;


                } else if (mFootStyle == MID) {


                    int offY = moveY / 2 + mFooterHeight / 2;


                    mFootOffY = offY;
                    mContentOffY = -moveY;


                }

            }


        } else {


            if (isHeader) {
                layoutMoveSmooth(isHeader, moveY, mHeaderHeight);

            } else {

                layoutMoveSmooth(isHeader, moveY, mFooterHeight);
            }


        }

        requestLayout();
    }

    private void layoutMoveSmooth(boolean isHeader, int moveY, int mHeight) {

        if (moveY == mHeight) {

            tempY = Math.abs(scrollSum);
            layoutSmoothMove(isHeader, moveY);

        } else if (moveY == 0) {

            tempY = mHeight;
            layoutSmoothMove(isHeader, moveY);


        }

    }


    private void layoutSmoothMove(final boolean isHeader, final int moveY) {

        tempY -= mSmoothLength;

        if (tempY <= moveY) {

            layoutMove(isHeader, true, moveY);
            return;
        }


        layoutMove(isHeader, true, tempY);


        postDelayed(new Runnable() {
            @Override
            public void run() {
                layoutSmoothMove(isHeader, moveY);
            }
        }, mSmoothDuration);


    }


    /**
     * 刷新完成
     */
    public void refreshComplete() {

        postDelayed(new Runnable() {
            @Override
            public void run() {
                smoothMove(true, false, 0, 0);
                isHeaderRefreshing =false;
                getHeaderInterface().onComplete();
                getHeaderInterface().onReset();
            }
        }, mDuration);


    }

    /**
     * 加载更多完成
     */
    public void loadMoreComplete() {

        postDelayed(new Runnable() {
            @Override
            public void run() {
                smoothMove(false, false, mContentView.getMeasuredHeight() - getMeasuredHeight(), 0);
                isFooterRefreshing= false;
                getFooterInterface().onComplete();
                getFooterInterface().onReset();
            }
        }, mDuration);

    }

    /**
     * 自动刷新
     */
    public void autoRefresh() {

        if (mHeaderView != null) {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    smoothMove(true, false, -mHeaderHeight, -mHeaderHeight);
                    getHeaderInterface().onRelease();
                    refreshing();
                }
            }, DEFAULT_AUTO_DURATION);


        }


    }


    private void refreshing() {
        isHeaderRefreshing = true;
        if (mOnRefreshListener != null) {

            mOnRefreshListener.onRefresh();
        }

    }

    private void loadingMore() {
        isFooterRefreshing =true;
        if (mOnLoadMoreListener != null) {

            mOnLoadMoreListener.onLoadMore();
        }

    }


    @Override
    public void computeScroll() {

        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }

        super.computeScroll();
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p != null && p instanceof LayoutParams;
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }


    public static class LayoutParams extends MarginLayoutParams {

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        @SuppressWarnings({"unused"})
        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }


    private CanRefresh getHeaderInterface() {
        return (CanRefresh) mHeaderView;
    }

    private CanRefresh getFooterInterface() {
        return (CanRefresh) mFooterView;
    }


    /**
     * 是否能下拉
     *
     * @return
     */
    protected boolean canChildScrollUp() {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (mContentView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mContentView;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(mContentView, -1) || mContentView.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mContentView, -1);
        }
    }


    /**
     * 是否能上拉
     *
     * @return
     */
    protected boolean canChildScrollDown() {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (mContentView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mContentView;
                return absListView.getChildCount() > 0
                        && (absListView.getLastVisiblePosition() < absListView.getChildCount() - 1
                        || absListView.getChildAt(absListView.getChildCount() - 1).getBottom() > absListView.getPaddingBottom());
            } else {
                return ViewCompat.canScrollVertically(mContentView, 1) || mContentView.getScrollY() < 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mContentView, 1);
        }
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public interface OnRefreshListener {
        void onRefresh();
    }
}
