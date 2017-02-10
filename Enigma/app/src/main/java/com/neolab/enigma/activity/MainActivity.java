package com.neolab.enigma.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.neolab.enigma.BuildConfig;
import com.neolab.enigma.R;
import com.neolab.enigma.activity.adapter.DrawerAdapter;
import com.neolab.enigma.dto.menu.MenuDto;
import com.neolab.enigma.util.EniLogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * MainActivity
 *
 * @author LongHV
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    /** Drawer layout */
    private DrawerLayout mDrawerLayout;
    /** ListView include menu item */
    private ListView mDrawerListView;
    /** Toolbar */
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        initDrawer();
    }

    /**
     * Setup toolbar
     */
    private void setupToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (null != getSupportActionBar()) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    /**
     * Initialize
     */
    private void initDrawer() {
        ImageView ivMenuDrawer = (ImageView) findViewById(R.id.ivMenuDrawer);
        ivMenuDrawer.setOnClickListener(this);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerListView = (ListView) findViewById(R.id.drawer_listView);
        final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headerView = inflater.inflate(R.layout.nav_header_main, null, false);
        DrawerAdapter adapter = new DrawerAdapter(this, getMenuItemDto());
        mDrawerListView.addHeaderView(headerView, null, true);
        mDrawerListView.setAdapter(adapter);
        mDrawerListView.setOnItemClickListener(onItemClickListener);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @SuppressLint("RtlHardcoded")
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    mDrawerLayout.closeDrawer(Gravity.RIGHT);
                } else {
                    mDrawerLayout.openDrawer(Gravity.RIGHT);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivMenuDrawer:
                if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    mDrawerLayout.closeDrawer(Gravity.RIGHT);
                } else {
                    mDrawerLayout.openDrawer(Gravity.RIGHT);
                }
                break;
            default:
                break;
        }
    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     */
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (BuildConfig.DEBUG) {
                EniLogUtil.d(getClass(), "[OnItemClickListener] position:" + position);
            }
            mDrawerLayout.closeDrawer(Gravity.RIGHT);
        }
    };

    /**
     * Get list menu
     *
     * @return List<MenuDto>
     */
    public List<MenuDto> getMenuItemDto() {
        List<MenuDto> menuDtoList = new ArrayList<>();
        MenuDto menuDto = new MenuDto();
        menuDto.id = 1;
        menuDto.icon = R.drawable.ic_top;
        menuDto.title = getString(R.string.item_top);
        menuDtoList.add(menuDto);

        menuDto = new MenuDto();
        menuDto.id = 2;
        menuDto.icon = R.drawable.ic_account;
        menuDto.title = getString(R.string.item_update_profile);
        menuDtoList.add(menuDto);

        menuDto = new MenuDto();
        menuDto.id = 3;
        menuDto.icon = R.drawable.ic_logout;
        menuDto.title = getString(R.string.item_logout);
        menuDtoList.add(menuDto);

        return menuDtoList;
    }
}

