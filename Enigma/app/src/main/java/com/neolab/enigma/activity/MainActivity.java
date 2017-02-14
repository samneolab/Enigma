package com.neolab.enigma.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.neolab.enigma.BuildConfig;
import com.neolab.enigma.EniConstant;
import com.neolab.enigma.R;
import com.neolab.enigma.activity.adapter.DrawerAdapter;
import com.neolab.enigma.dto.ToolbarTypeDto;
import com.neolab.enigma.dto.menu.MenuDto;
import com.neolab.enigma.fragment.BaseFragment.OnBaseFragmentListener;
import com.neolab.enigma.fragment.top.TopFragment;
import com.neolab.enigma.preference.EncryptionPreference;
import com.neolab.enigma.util.EniLogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * MainActivity
 *
 * @author LongHV
 */
public class MainActivity extends BaseActivity implements View.OnClickListener, OnBaseFragmentListener {

    /**
     * Drawer layout
     */
    private DrawerLayout mDrawerLayout;
    /**
     * ListView include menu item
     */
    private ListView mDrawerListView;
    /**
     * Toolbar
     */
    private Toolbar mToolbar;
    private View mBackTextView;
    private View mLogoImageView;
    private View mMenuDrawerImageView;
    private TextView mTitleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        initDrawer();
        initEvent();
        addFragment(new TopFragment(), false);
    }

    /**
     * Setup toolbar
     */
    private void setupToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitleTextView = (TextView) findViewById(R.id.toolbar_title_textView);
        mBackTextView = findViewById(R.id.toolbar_back_textView);
        mLogoImageView = findViewById(R.id.toolbar_logo_imageView);
        setSupportActionBar(mToolbar);
        if (null != getSupportActionBar()) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    /**
     * Initialize
     */
    private void initDrawer() {
        mMenuDrawerImageView = findViewById(R.id.toolbar_menu_drawer_imageView);
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

    /**
     * Listener event
     */
    private void initEvent() {
        mMenuDrawerImageView.setOnClickListener(this);
        mBackTextView.setOnClickListener(this);
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
            case R.id.toolbar_menu_drawer_imageView:
                if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    mDrawerLayout.closeDrawer(Gravity.RIGHT);
                } else {
                    mDrawerLayout.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.toolbar_back_textView:
                onBackPressed();
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
            switch (position) {
                case EniConstant.MenuItem.TOP_ITEM:
                    break;
                case EniConstant.MenuItem.USER_INFORMATION:
                    break;
                case EniConstant.MenuItem.LOGOUT:
                    logout();
                    break;
                default:
                    mDrawerLayout.closeDrawer(Gravity.RIGHT);
                    break;
            }
        }
    };

    /**
     * The method is used to logout application
     */
    private void logout() {
        EncryptionPreference encryptionPreference = new EncryptionPreference(getApplicationContext());
        encryptionPreference.token = null;
        encryptionPreference.userId = null;
        encryptionPreference.isUserLogin = false;
        encryptionPreference.write();
        finish();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.animation_slide_bottom_to_up, 0);
    }

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

    @Override
    public void onToolbarListener(ToolbarTypeDto toolbarTypeDto) {
        mLogoImageView.setVisibility(View.GONE);
        mMenuDrawerImageView.setVisibility(View.GONE);
        mBackTextView.setVisibility(View.GONE);
        mTitleTextView.setVisibility(View.GONE);
        switch (toolbarTypeDto.type) {
            case EniConstant.ToolbarType.HOME:
                mLogoImageView.setVisibility(View.VISIBLE);
                mMenuDrawerImageView.setVisibility(View.VISIBLE);
                break;
            case EniConstant.ToolbarType.DETAIL_DISPLAY_DRAWER:
                mBackTextView.setVisibility(View.VISIBLE);
                mLogoImageView.setVisibility(View.VISIBLE);
                mMenuDrawerImageView.setVisibility(View.VISIBLE);
                break;
            case EniConstant.ToolbarType.DETAIL_NOT_DISPLAY_DRAWER:
                mBackTextView.setVisibility(View.VISIBLE);
                mTitleTextView.setVisibility(View.VISIBLE);
                mTitleTextView.setText(toolbarTypeDto.title);
                break;
        }
    }

}

