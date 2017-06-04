package com.example.yang.myapplication;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private OnItemClickListener mOnItemClickListener;
	private List<Boolean> isClicks;//控件是否被点击,默认为false，如果被点击，改变值，控件根据值改变自身颜色
	private List<String> mDatas;
    
    public RecyclerAdapter(List<String> datas){
    	this.mDatas = datas;
		isClicks = new ArrayList<>();
		isClicks.add(0, true);//第一项设置默认点击
        for(int i = 1; i < mDatas.size(); i++){
            isClicks.add(false);
        }
    }
    
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_main_recycle_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
    	String string = mDatas.get(position);
    	holder.title.setText(string + position);
    	if (isClicks.get(position)) {
            holder.image.setScaleX(0.8f + (1.3f - 0.8f) );
            holder.image.setScaleY(0.8f + (1.3f - 0.8f) );
    		holder.title.setTextColor(Color.parseColor("#af0000"));
		}else{
            holder.image.setScaleX(1.3f + (0.8f - 1.3f) );
            holder.image.setScaleY(1.3f + (0.8f - 1.3f) );
			holder.title.setTextColor(Color.parseColor("#333333"));
		}

    	// 如果设置了回调，则设置点击事件
    	if (mOnItemClickListener != null) {
    		holder.itemView.setOnClickListener(new View.OnClickListener() {
    			@Override
    			public void onClick(View v) {
    				for(int i = 0; i <isClicks.size();i++){
    					isClicks.set(i,false);
    				}
    				isClicks.set(position,true);
    				notifyDataSetChanged();
    				int n = holder.getLayoutPosition();
    				mOnItemClickListener.onItemClick(v,n);
    			}
    		});
    	}
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private ImageView image;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_titlt);
            image = (ImageView) itemView.findViewById(R.id.imageView1);

        }
    }

    public void addOnItemClickListener(OnItemClickListener listener){
        mOnItemClickListener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
}
