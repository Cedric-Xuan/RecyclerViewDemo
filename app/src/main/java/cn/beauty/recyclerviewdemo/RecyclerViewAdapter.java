package cn.beauty.recyclerviewdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import cn.beauty.recyclerviewdemo.model.ItemInfo;
import cn.beauty.recyclerviewdemo.view.VisualChangesView;
import cn.beauty.recyclerviewdemo.widget.PullDownRefreshRecyclerView.IViewHolder;
import cn.beauty.recyclerviewdemo.widget.ShareData;

/**
 * Created by Simon Meng on 2018/4/23.
 * Guangzhou Beauty Information Technology Co.,Ltd
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<IViewHolder>{
    private Context mContext;
    private List<ItemInfo> mInfoList;

    public RecyclerViewAdapter(Context context, List<ItemInfo> itemInfoList) {
        mContext = context;
        mInfoList = itemInfoList;
    }

    @NonNull
    @Override
    public IViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VisualChangesView view = new VisualChangesView(parent.getContext());
        view.setScaleType(ImageView.ScaleType.MATRIX);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ShareData.m_screenWidth, ShareData.PxToDpi_xxhdpi(450));
        view.setLayoutParams(params);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IViewHolder holder, int position) {
        ItemInfo itemInfo = mInfoList.get(position);
        final VisualChangesView visualChangesView =(VisualChangesView) holder.itemView;
        if (position == 0) {
            visualChangesView.setType(VisualChangesView.HEAD);
        } else {
            visualChangesView.setType(VisualChangesView.NORMAL);
        }


        Glide.with(mContext).load(itemInfo.getImageUri()).placeholder(R.mipmap.ic_launcher).listener(new RequestListener<Integer, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, Integer model, Target<GlideDrawable> target, boolean isFirstResource) {
                Log.i("exception", e.getMessage() + "");
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, Integer model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                visualChangesView.setUpImage(resource);
                return false;
            }
        }).into(visualChangesView);
    }

    @Override
    public int getItemCount() {
        return mInfoList != null ? mInfoList.size() : 0;
    }

    private static class ViewHolder extends IViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
