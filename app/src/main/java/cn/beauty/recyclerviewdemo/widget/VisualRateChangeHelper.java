package cn.beauty.recyclerviewdemo.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.View;

import cn.beauty.recyclerviewdemo.view.VisualChangesView;

/**
 * Created by Simon Meng on 2018/4/24.
 * Guangzhou Beauty Information Technology Co.,Ltd
 */
public class VisualRateChangeHelper{
    private boolean mShouldLayout;
    private SparseIntArray mLocationDictionary = new SparseIntArray();
    private boolean mStartEffect;

    private Context mContext;

    public VisualRateChangeHelper(Context context) {
        mContext = context;
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return mLayoutManager;
    }

    public RecyclerView.OnScrollListener getScrollListener() {
        return mOnScrollListener;
    }


    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (!mShouldLayout) {
                mShouldLayout = true;
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (mStartEffect && mShouldLayout) {
                for (int i = 0; i < recyclerView.getChildCount(); i++) {
                    View currentView = recyclerView.getChildAt(i);
                    if (currentView instanceof VisualChangesView) {
                        VisualChangesView visualChangesView = (VisualChangesView) currentView;
                        int viewIndex = recyclerView.getLayoutManager().getPosition(visualChangesView);

                        // 修正linearLayoutManager没有完全回调，造成的某些view的位置数据缺失
                        if ((mLocationDictionary.indexOfKey(viewIndex) < 0)) {
                            int lastVisibleItemPosition = ((LinearLayoutManager)(recyclerView.getLayoutManager())).findLastVisibleItemPosition();
                            boolean isLastsOne = lastVisibleItemPosition == viewIndex;
                            if (isLastsOne) {
                                mLocationDictionary.put(viewIndex, visualChangesView.getTop());
                            }
                        }

                        int viewLocation = mLocationDictionary.get(viewIndex);

                        float rate = 0;
                        if (viewLocation == 0) {
                            if (visualChangesView.mType == VisualChangesView.HEAD) {
                                rate = 1;
                            } else if (visualChangesView.mType == VisualChangesView.NORMAL){
                                rate = 0;
                            }
                        } else{
                            rate = (viewLocation - (currentView.getTop())) / (viewLocation * 1.0f);
                        }

                        if (rate > 1) {
                            rate = 1;
                        }

                        if (rate < 0) {
                            rate = 0;
                        }

                        visualChangesView.mRate = rate;
                        visualChangesView.invalidate();
                    }
                }
            }
        }
    };


    private LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext) {
        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            super.onLayoutChildren(recycler, state);
            for (int i = 0; i < this.getChildCount(); i++) {
                View currentView = this.getChildAt(i);
                if (currentView instanceof VisualChangesView) {
                    VisualChangesView visualChangesView = (VisualChangesView) currentView;
                    int viewIndex = this.getPosition(visualChangesView);

//                      记录每个View最开始的顶部位置
                    if ((mLocationDictionary.indexOfKey(viewIndex) < 0)) {
                        mLocationDictionary.put(viewIndex, visualChangesView.getTop());
                        mStartEffect = true;
                    }
                }
            }
        }
    };





}
