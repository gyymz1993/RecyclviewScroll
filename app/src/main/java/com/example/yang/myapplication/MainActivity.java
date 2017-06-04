package com.example.yang.myapplication;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends AppCompatActivity implements OnClickListener,RecyclerAdapter.OnItemClickListener {
	private RecyclerView mRecyclerView;
	private RecyclerAdapter mAdapter;
	private LinearLayoutManager mLinearLayoutManager;
	private int n = 0;
	private ImageView back;
	private ImageView enter;
	List<String> list = new ArrayList<String>();
	private RelativeLayout rl_news_enter;
	private RelativeLayout rl_news_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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
		rl_news_back = (RelativeLayout) findViewById(R.id.rl_news_back);
		rl_news_enter = (RelativeLayout) findViewById(R.id.rl_news_enter);
		rl_news_back.setOnClickListener(this);
		rl_news_enter.setOnClickListener(this);
		
		back = (ImageView) findViewById(R.id.img_news_back_pre);
		enter = (ImageView) findViewById(R.id.img_news_enter);
		enter.setImageResource(R.drawable.goenter);
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
	    			back.setImageResource(R.drawable.left_gray);
	    			enter.setImageResource(R.drawable.goenter);
	    		} else if (lastVisible >= mAdapter.getItemCount()-1) {
	    			back.setImageResource(R.drawable.back);
	    			enter.setImageResource(R.drawable.right_gray);
	    		} else {
	    			back.setImageResource(R.drawable.back);
	    			enter.setImageResource(R.drawable.goenter);
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
	
	@Override
	public void onItemClick(View view, int position) {
//		mAdapter.notifyDataSetChanged();
		Toast.makeText(this, "点击了" + position, Toast.LENGTH_SHORT).show();
	}

	private void move(int n) {
		if (n <= 0) {
			back.setImageResource(R.drawable.left_gray);
			enter.setImageResource(R.drawable.goenter);
		} else if (n >= mAdapter.getItemCount() - 1) {
			back.setImageResource(R.drawable.back);
			enter.setImageResource(R.drawable.right_gray);
		} else {
			back.setImageResource(R.drawable.back);
			enter.setImageResource(R.drawable.goenter);
		}
		mRecyclerView.stopScroll();
		smoothMoveToPosition(n);
	}

	private void smoothMoveToPosition(int n) {
		int firstItem = mLinearLayoutManager.findFirstVisibleItemPosition();
		int lastItem = mLinearLayoutManager.findLastVisibleItemPosition();

		if (n <= firstItem) {
			mRecyclerView.smoothScrollToPosition(n);
		} else if (n <= lastItem) {
			int top = mRecyclerView.getChildAt(n - firstItem).getLeft();
			mRecyclerView.smoothScrollBy(0, top);
		} else {
			mRecyclerView.smoothScrollToPosition(n);
		}
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.rl_news_back:
			n = mLinearLayoutManager.findFirstVisibleItemPosition();
			if (n > 0) {
				n = n - 1;
				move(n);
			}
			break;
		case R.id.rl_news_enter:
			n = mLinearLayoutManager.findLastVisibleItemPosition();
			if (n == mAdapter.getItemCount()) {
				move(n);
			} else {
				n = n + 1;
				move(n);
			}
			break;
		default:
			break;
		}
	}
}
