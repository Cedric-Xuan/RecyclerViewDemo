package cn.beauty.recyclerviewdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.beauty.recyclerviewdemo.model.ItemInfo;
import cn.beauty.recyclerviewdemo.widget.PullDownRefreshRecyclerView.IRecyclerView;
import cn.beauty.recyclerviewdemo.widget.ShareData;
import cn.beauty.recyclerviewdemo.widget.VisualRateChangeHelper;
import cn.beauty.recyclerviewdemo.widget.footer.LoadMoreFooterView;
import cn.beauty.recyclerviewdemo.widget.header.ClassicRefreshHeaderView;

public class MainActivity extends AppCompatActivity {
    private Context mContext;

    private FrameLayout mUiContainer;
    private VisualRateChangeHelper mVisualRateChangeHelper;
    private IRecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;

    private ClassicRefreshHeaderView mHeaderView;
    private LoadMoreFooterView mLoadMoreFooterView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mUiContainer = new FrameLayout(mContext);
        ShareData.InitData(this);
        this.setContentView(mUiContainer);
        initView();
    }

    private void initView() {
        FrameLayout.LayoutParams fl;

        mVisualRateChangeHelper = new VisualRateChangeHelper(mContext);

        mRecyclerView = new IRecyclerView(mContext);
        mRecyclerView.setLayoutManager(mVisualRateChangeHelper.getLayoutManager());
        mRecyclerView.setRefreshEnabled(true);
        mRecyclerView.setLoadMoreEnabled(true);
        mRecyclerView.setOnRefreshListener(mOnRefreshListener);
        mRecyclerView.setOnLoadMoreListener(mOnLoadMoreListener);
        fl = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        mRecyclerView.setLayoutParams(fl);
        mUiContainer.addView(mRecyclerView);
        mRecyclerView.addOnScrollListener(mVisualRateChangeHelper.getScrollListener());

        // 初始化测试数据

        // 添加头部
        mHeaderView = new ClassicRefreshHeaderView(mContext);
        fl = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, ShareData.PxToDpi_xxhdpi(200));
        mHeaderView.setLayoutParams(fl);
        mRecyclerView.setRefreshHeaderView(mHeaderView);

        // 添加尾部
        mLoadMoreFooterView = new LoadMoreFooterView(mContext);
        fl = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, ShareData.PxToDpi_xxhdpi(200));
        mLoadMoreFooterView.setLayoutParams(fl);
        mRecyclerView.setLoadMoreFooterView(mLoadMoreFooterView);

        initTestData();
        mAdapter = new RecyclerViewAdapter(mContext, mTestData);
        mRecyclerView.setIAdapter(mAdapter);
    }



    private List<ItemInfo> mTestData = new ArrayList<>();
    private void initTestData() {
        int drawable1 =  R.drawable.scene1;
        int drawable2 =  R.drawable.scene2;
        int drawable3 =  R.drawable.scene4;

        for (int i = 0; i < 10; i++) {
            ItemInfo itemInfo;
            if (i % 2 == 0) {
                itemInfo = new ItemInfo(drawable2);
            } else if (i % 3 == 0) {
                itemInfo = new ItemInfo(drawable3);
            } else {
                itemInfo = new ItemInfo(drawable1);
            }
            mTestData.add(itemInfo);
        }
    }


    private IRecyclerView.OnRefreshListener mOnRefreshListener = new IRecyclerView.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mRecyclerView.setRefreshing(true);
            mLoadMoreFooterView.setStatus(LoadMoreFooterView.Status.GONE);
            mRecyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mContext, "刷新完成", Toast.LENGTH_SHORT).show();
                    mRecyclerView.setRefreshing(false);
                }
            }, 1000);
        }
    };

    private IRecyclerView.OnLoadMoreListener mOnLoadMoreListener = new IRecyclerView.OnLoadMoreListener() {
        @Override
        public void onLoadMore() {
            if (mLoadMoreFooterView.canLoadMore()) {
                mLoadMoreFooterView.setStatus(LoadMoreFooterView.Status.LOADING);
                loadMoreResult();
            }
        }
    };

    private void loadMoreResult() {
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, "没有更多数据了", Toast.LENGTH_SHORT).show();
                mLoadMoreFooterView.setStatus(LoadMoreFooterView.Status.THE_END);
            }
        }, 1000);
    }




}
