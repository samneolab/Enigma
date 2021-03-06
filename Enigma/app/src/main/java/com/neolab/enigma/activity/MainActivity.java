package com.neolab.enigma.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.neolab.enigma.BuildConfig;
import com.neolab.enigma.EniConstant;
import com.neolab.enigma.R;
import com.neolab.enigma.activity.adapter.DrawerAdapter;
import com.neolab.enigma.dto.HeaderDto;
import com.neolab.enigma.dto.menu.MenuDto;
import com.neolab.enigma.fragment.BaseFragment.OnBaseFragmentListener;
import com.neolab.enigma.fragment.other.TermOfServiceFragment;
import com.neolab.enigma.fragment.user.CompleteStopServiceFragment;
import com.neolab.enigma.fragment.user.UserUpdateInformationFragment;
import com.neolab.enigma.fragment.history.CompleteWithdrawPaymentFragment;
import com.neolab.enigma.fragment.payment.CompletePaymentFragment;
import com.neolab.enigma.fragment.top.TopFragment;
import com.neolab.enigma.util.EniEncryptionUtil;
import com.neolab.enigma.util.EniLogUtil;
import com.neolab.enigma.ws.ApiRequest;
import com.neolab.enigma.ws.core.ApiCallback;
import com.neolab.enigma.ws.core.ApiError;
import com.neolab.enigma.ws.respone.user.UserLogoutResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * MainActivity
 *
 * @author LongHV
 */
public class MainActivity extends BaseActivity implements View.OnClickListener, OnBaseFragmentListener {

    /** Drawer layout */
    private DrawerLayout mDrawerLayout;
    /** Toolbar */
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
        /* ListView include menu item */
        ListView drawerListView = (ListView) findViewById(R.id.drawer_listView);
        final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headerView = inflater.inflate(R.layout.nav_header_main, null, false);
        DrawerAdapter adapter = new DrawerAdapter(this, getMenuItemDto());
        drawerListView.addHeaderView(headerView, null, true);
        drawerListView.setAdapter(adapter);
        drawerListView.setOnItemClickListener(onItemClickListener);

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
            FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
            Fragment currentFragment = getVisibleFragment(fragmentManager);
            if (currentFragment instanceof TermOfServiceFragment
                    || currentFragment instanceof UserUpdateInformationFragment
                    || currentFragment instanceof CompletePaymentFragment
                    || currentFragment instanceof CompleteWithdrawPaymentFragment) {
                TopFragment topFragment = new TopFragment();
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.setCustomAnimations(R.anim.animation_fade_in_left_to_right, R.anim.animation_fade_out_left_to_right, 0, 0);
                transaction.replace(R.id.main_root_frameLayout, topFragment);
                transaction.commit();
                return;
            }
            if (currentFragment instanceof CompleteStopServiceFragment) {
                logout();
                return;
            } if (currentFragment instanceof TopFragment) {
                EniEncryptionUtil.resetDataForLogout(getApplicationContext());
                finish();
                return;
            }
            super.onBackPressed();
        }
    }

    /**
     * Get fragment is visible on activity
     *
     * @return current fragment visible
     */
    private Fragment getVisibleFragment(FragmentManager fragmentManager) {
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible())
                return fragment;
        }
        return null;
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
            mDrawerLayout.closeDrawer(Gravity.RIGHT);
            switch (position) {
                case EniConstant.MenuItem.TOP_ITEM:
                    addFragment(new TopFragment(), false);
                    break;
                case EniConstant.MenuItem.USER_INFORMATION:
                    addFragment(new UserUpdateInformationFragment(), false);
                    break;
                case EniConstant.MenuItem.TERM_OF_SERVICE:
                    addFragment(new TermOfServiceFragment(), false);
                    break;
                case EniConstant.MenuItem.LOGOUT:
                    logout();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * Call api to logout
     */
    public void logout() {
        eniShowLoading();
        ApiRequest.logout(new ApiCallback<UserLogoutResponse>() {
            @Override
            public void failure(RetrofitError retrofitError, ApiError apiError) {
                eniCancelNowLoading();
                if (apiError == null) {
                    return;
                }
                Toast.makeText(MainActivity.this, apiError.getError().getMessage(), Toast.LENGTH_SHORT).show();
                resetData();
            }

            @Override
            public void success(UserLogoutResponse userLogoutResponse, Response response) {
                eniCancelNowLoading();
                resetData();
            }
        });

    }

    /**
     * Reset data of user
     */
    private void resetData() {
        EniEncryptionUtil.resetDataForLogout(getApplicationContext());
        finish();
        startActivity(LoginActivity.class, R.anim.animation_fade_in_left_to_right, R.anim.animation_fade_out_left_to_right);
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
        menuDto.icon = R.drawable.ic_terms_of_service;
        menuDto.title = getString(R.string.item_term_of_service);
        menuDtoList.add(menuDto);

        menuDto = new MenuDto();
        menuDto.id = 4;
        menuDto.icon = R.drawable.ic_logout;
        menuDto.title = getString(R.string.item_logout);
        menuDtoList.add(menuDto);

        return menuDtoList;
    }

    @Override
    public void onHeaderListener(HeaderDto headerDto) {
        mLogoImageView.setVisibility(View.GONE);
        mMenuDrawerImageView.setVisibility(View.GONE);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mBackTextView.setVisibility(View.GONE);
        mTitleTextView.setVisibility(View.GONE);
        if (headerDto == null) {
            return;
        }
        switch (headerDto.type) {
            case EniConstant.ToolbarType.HOME:
                mLogoImageView.setVisibility(View.VISIBLE);
                mMenuDrawerImageView.setVisibility(View.VISIBLE);
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                break;
            case EniConstant.ToolbarType.DISPLAY_BACK_LOGO_DRAWER:
                mBackTextView.setVisibility(View.VISIBLE);
                mLogoImageView.setVisibility(View.VISIBLE);
                mMenuDrawerImageView.setVisibility(View.VISIBLE);
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                break;
            case EniConstant.ToolbarType.DISPLAY_BACK_TITLE:
                mBackTextView.setVisibility(View.VISIBLE);
                mTitleTextView.setVisibility(View.VISIBLE);
                mTitleTextView.setText(headerDto.title);
                break;
            case EniConstant.ToolbarType.DISPLAY_BACK_TITLE_DRAWER:
                mBackTextView.setVisibility(View.VISIBLE);
                mTitleTextView.setVisibility(View.VISIBLE);
                mTitleTextView.setText(headerDto.title);
                mMenuDrawerImageView.setVisibility(View.VISIBLE);
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                break;
            case EniConstant.ToolbarType.ONLY_TITLE:
                mTitleTextView.setVisibility(View.VISIBLE);
                mTitleTextView.setText(headerDto.title);
                break;
            case EniConstant.ToolbarType.ONLY_DRAWER_AND_TITLE:
                mMenuDrawerImageView.setVisibility(View.VISIBLE);
                mTitleTextView.setVisibility(View.VISIBLE);
                mTitleTextView.setText(headerDto.title);
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                break;
            default:
                break;
        }
    }

}

