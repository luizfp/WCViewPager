package nevet.me.wcviewpagersample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.Random;


public class SampleActivity extends AppCompatActivity implements
        ImageViewPagerAdapter.OnClickListener {

    private ViewPager viewPager;
    private TabLayout tabs;
    private IAdapter mAdapter;
    private int mTotalEnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(4);

        // demo tab selection without scrolling
        tabs = (TabLayout) findViewById(R.id.tablayout);
//        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition(), false);
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });

        initImageViewsAdapter();

        // Create global configuration and initialize ImageLoader with this config
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sample, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_textviews) {
            initTextViewsAdapter();
            return true;
        } else if (id == R.id.action_imageviews) {
            initImageViewsAdapter();
            return true;
        } else if (id == R.id.action_imageviews_async) {
            initAsyncImageViewsAdapter();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initImageViewsAdapter() {
        ArrayList<Integer> resArrayList = new ArrayList<Integer>();
        resArrayList.add(R.mipmap.ic_launcher);
        resArrayList.add(R.mipmap.ic_launcher);
        resArrayList.add(R.mipmap.ic_launcher);
        resArrayList.add(R.mipmap.ic_launcher);
        resArrayList.add(R.mipmap.ic_launcher);
        resArrayList.add(R.mipmap.ic_launcher);
        resArrayList.add(R.mipmap.ic_launcher);

        ImageViewPagerAdapter adapter = new ImageViewPagerAdapter(resArrayList, this);
        viewPager.setAdapter(adapter);
        mAdapter = adapter;
//        tabs.setupWithViewPager(viewPager);
    }

    private void initAsyncImageViewsAdapter() {
        ArrayList<String> asyncImagesArray = new ArrayList<>();
        asyncImagesArray.add("http://dummyimage.com/500x400/000/fff");
        asyncImagesArray.add("http://dummyimage.com/500x300/040/fff");
        asyncImagesArray.add("http://dummyimage.com/500x200/006/fff");
        asyncImagesArray.add("http://dummyimage.com/500x400/700/fff");
        asyncImagesArray.add("http://dummyimage.com/500x100/006/fff");

        AsyncImageViewPagerAdapter adapter = new AsyncImageViewPagerAdapter(asyncImagesArray);
        viewPager.setAdapter(adapter);
//        tabs.setupWithViewPager(viewPager);
    }

    private void initTextViewsAdapter() {
        ArrayList<String> stringArrayList = new ArrayList<String>();
        stringArrayList.add(getString(R.string.lorem_short));
        stringArrayList.add(getString(R.string.lorem_medium));
        stringArrayList.add(getString(R.string.lorem_long));
        stringArrayList.add(getString(R.string.lorem_medium));
        stringArrayList.add(getString(R.string.lorem_short));

        TextViewPagerAdapter adapter = new TextViewPagerAdapter(stringArrayList);
        viewPager.setAdapter(adapter);
//        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public void onClickAdapterItem() {
        if (mAdapter != null && mAdapter.enableNext()) {
            mTotalEnable++;
            viewPager.setCurrentItem(mTotalEnable, true);
            Toast.makeText(this, "Swipe next enabled", Toast.LENGTH_SHORT).show();
        }
    }


    public class TextViewPagerAdapter extends PagerAdapter {

        private final ArrayList<String> strings;

        public TextViewPagerAdapter(ArrayList<String> strings) {
            super();
            this.strings = strings;
        }

        @Override
        public int getCount() {
            return strings.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(container.getContext(), android.R.layout.test_list_item, null);
            ((TextView)view.findViewById(android.R.id.text1)).setText(strings.get(position));
            container.addView(view);

            // set Random background
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            view.setBackgroundColor(color);

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "View" + position;
        }
    }

    public class AsyncImageViewPagerAdapter extends PagerAdapter {

        private final ArrayList<String> images;

        public AsyncImageViewPagerAdapter(ArrayList<String> imageUrls) {
            this.images = imageUrls;
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            container.addView(view);
            ImageLoader.getInstance().displayImage(images.get(position), view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "View " + position;
        }
    }
}
