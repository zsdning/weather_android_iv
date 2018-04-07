package com.iframe.ui.product.weather;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.creditcloud.event.response.CMSResponse;
import com.iframe.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BannerAdapter extends PagerAdapter {
	Context context;
	ArrayList<String> bannerList;
	ArrayList<View> views;
	ArrayList<CMSResponse> cmsLinkList;

	public BannerAdapter(Context context, ArrayList<String> bannerList) {
		this.context = context;
		this.bannerList = bannerList;
		initViews();
	}

	private void initViews() {
		views = new ArrayList<View>();
		for (String link : bannerList) {
			View view = LayoutInflater.from(context).inflate(R.layout.fragment_screen_slide_page, null);
			ImageView image = (ImageView) view.findViewById(R.id.pic);
			Picasso.with(context).load(link).into(image);
			views.add(view);
		}
	}

	public void setCMSLinkData(ArrayList<CMSResponse> list) {
		this.cmsLinkList = list;
	}

	@Override
	public int getCount() {
		return views.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(views.get(position));
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(views.get(position));
		View view = views.get(position);
		if (cmsLinkList !=null && cmsLinkList.size() > position) {
			final CMSResponse item = cmsLinkList.get(position);

			view.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (!TextUtils.isEmpty(item.getUrl())) {
						//HTMLPageActivity.startAc(activity, item.getUrl(), item.getTitle());
					}
				}
			});
		}
		return views.get(position);
	}

}
