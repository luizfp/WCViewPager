package nevet.me.wcviewpagersample;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by luiz on 9/5/16.
 */
public class ImageViewPagerAdapter extends PagerAdapter implements IAdapter, View.OnClickListener {

    private final ArrayList<Integer> images;
    private int count = 1;
    private final OnClickListener mListener;

    public interface OnClickListener {
        void onClickAdapterItem();
    }

    public ImageViewPagerAdapter(ArrayList<Integer> imageResourceIds, OnClickListener listener) {
        this.images = imageResourceIds;
        mListener = listener;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public boolean enableNext() {
        if (count >= images.size())
            return false;

        count++;
        notifyDataSetChanged();

        return true;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView view = new ImageView(container.getContext());
        container.addView(view);
        view.setOnClickListener(this);
        view.setImageResource(images.get(position));

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public void onClick(View view) {
        mListener.onClickAdapterItem();
    }
}