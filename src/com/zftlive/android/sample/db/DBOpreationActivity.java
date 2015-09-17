package com.zftlive.android.sample.db;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import com.zftlive.android.R;
import com.zftlive.android.base.BaseActivity;
import com.zftlive.android.view.SwipeListView;

public class DBOpreationActivity extends BaseActivity {

	private SwipeListView mListView;
	private MyFavDataAdapter mAdapter;
	
	@Override
	public int bindLayout() {
		return R.layout.activity_db_fav_list;
	}

	@Override
	public void initView(View view) {
		mListView = (SwipeListView) findViewById(R.id.fav_listview);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
//				//没有显示删除时进行跳转
//				if(!mListView.isShowRight()){
//					Intent intent = new Intent(getActivity(),EssayDetailActivity.class);
//					intent.putExtra("url", ((Favorite)mAdapter.getItem(position)).getLinkURL());
//					getActivity().startActivity(intent);
//				}
			}
		});
	}

	@Override
	public void doBusiness(Context mContext) {

	}

	@Override
	public void resume() {

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
