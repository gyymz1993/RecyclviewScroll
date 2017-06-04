package com.example.yang.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("NewApi")
public class MainActivity1 extends AppCompatActivity implements OnClickListener,RecyclerAdapter.OnItemClickListener {
	private RecyclerView mRecyclerView;
	private RecyclerAdapter mAdapter;
	private LinearLayoutManager mLinearLayoutManager;
	private int n = 0;
	List<String> list = new ArrayList<String>();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main1);

		initData();
		initView();
	}

	private void initData() {
		for (int i = 0; i < 25; i++) {
			list.add("item");
		}
	}

	@SuppressWarnings("deprecation")
	private void initView() {
		mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);


		mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
		mAdapter = new RecyclerAdapter(list);
		mRecyclerView.setLayoutManager(mLinearLayoutManager);
		mRecyclerView.setAdapter(mAdapter);
		mAdapter.addOnItemClickListener(this);
		mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
	        //用来标记是否正在向最后一个滑动
	        boolean isSlidingToLast = false;
	 
	        @Override
	        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
	            super.onScrollStateChanged(recyclerView, newState);
	            LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
	            int firstVisible = mLinearLayoutManager.findFirstVisibleItemPosition();
	            int lastVisible = mLinearLayoutManager.findLastVisibleItemPosition();
	        	if (firstVisible <= 0) {


	    		} else if (lastVisible >= mAdapter.getItemCount()-1) {

	    		} else {

	    		}
	        }
	 
	        @Override
	        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
	            super.onScrolled(recyclerView, dx, dy);
	            //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
	            if (dx > 0) {
	                //大于0表示正在向右滚动
	                isSlidingToLast = true;
	            } else {
	                //小于等于0表示停止或向左滚动
	                isSlidingToLast = false;
	            }
	        }
	    });
	}
	
	@SuppressLint("LongLogTag")
	@Override
	public void onItemClick(View view, int position) {
		int currentPistion=list.size()/2;
		Log.e("currentPistion",currentPistion+"");
		Log.e("position",position+"");
		//n=position;
		n = mLinearLayoutManager.findFirstVisibleItemPosition();
		int m = mLinearLayoutManager.findLastVisibleItemPosition();
		move(position);
//		Log.e("当前点击位置 position",position+"");
//		Log.e("开始位置 n",n+"");
//		Log.e("结束显示位置m",m+"");

//		if (position == mAdapter.getItemCount()) {
//			move(position);
//			Log.e("position == mAdapter.getItemCount()","-------position"+position);
//		} else if (position<currentPistion){
//			position = position + 1;
//			move(position);
//			Log.e("position<currentPistion","-------position"+position);
//		}else if(position>currentPistion){
//			position = position + 1;
//			move(position);
//			Log.e("position>currentPistion","-------position"+position);
//		}else if(position==0){
//			move(0);
//		}else {
//			move(currentPistion);
//			Log.e("currentPistion","-------position"+currentPistion);
//		}


//		if (position > currentPistion) {
//			n = n + 3;
//			move(n);
//		}else {
//			n = n -3;
//			move(n);
//		}



//		mAdapter.notifyDataSetChanged();
		Toast.makeText(this, "点击了" + position, Toast.LENGTH_SHORT).show();
	}

	private void move(int n) {
		mRecyclerView.stopScroll();
		//mRecyclerView.smoothScrollToPosition(n);
		smoothMoveToPosition(n);
		//smoothMoveToPosition(n);
	}

	@SuppressLint("LongLogTag")
	private void smoothMoveToPosition(int n) {
		int firstItem = mLinearLayoutManager.findFirstVisibleItemPosition();//0
		int lastItem = mLinearLayoutManager.findLastVisibleItemPosition();  //3

		if (n==firstItem&&n!=0){
			mRecyclerView.smoothScrollToPosition(n-1);
		}if (n==lastItem&&n<mAdapter.getItemCount()){
			mRecyclerView.smoothScrollToPosition(n+1);
		}

	}

	@Override
	public void onClick(View v)
	{

	}
}
