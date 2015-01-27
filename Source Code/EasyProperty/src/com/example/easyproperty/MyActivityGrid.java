package com.example.easyproperty;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class MyActivityGrid extends Activity implements AbsListView.OnScrollListener {
    List products;
    GridView gvProducts = null;
    ProductListAdapterWithCache adapterProducts;


    private boolean lvBusy = false;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_grid);

        // populate data
        products = new ArrayList();
        products.add(new Product("Kitchen","http://easyproperty.tk.hostinghood.com/images/house.jpe"));
        products.add(new Product("Outer View","http://easyproperty.tk.hostinghood.com/images/resize.jpe"));
        products.add(new Product("Bedroom","http://easyproperty.tk.hostinghood.com/images/bedroom.jpg"));
        products.add(new Product("Lounge","http://easyproperty.tk.hostinghood.com/images/lounge.jpg"));
        products.add(new Product("Stairway","http://easyproperty.tk.hostinghood.com/images/stairway.jpg"));
        products.add(new Product("Garden","http://easyproperty.tk.hostinghood.com/images/garden.jpg"));
        //
        gvProducts = (GridView) findViewById( R.id.grid_products);
        adapterProducts = new ProductListAdapterWithCache(this, products);
        gvProducts.setAdapter(adapterProducts);

    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                lvBusy = false;
                adapterProducts.notifyDataSetChanged();
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                lvBusy = true;
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                lvBusy = true;
                break;
        }
    }


    public boolean isLvBusy(){
        return lvBusy;
    }




}
