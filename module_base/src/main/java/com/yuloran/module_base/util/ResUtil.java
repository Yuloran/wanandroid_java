package com.yuloran.module_base.util;

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;

import com.yuloran.lib_core.init.EnvService;
import com.yuloran.lib_core.utils.Logger;
import com.yuloran.module_base.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * [资源管理器]
 * <p>
 * Author: Yuloran
 * Date Added: 2018/12/14 18:00
 *
 * @since 1.0.0
 */
public final class ResUtil
{
    /** 微信公众号 */
    public static final byte OFFICIAL_ACCOUNT = 0;
    /** 推荐 */
    public static final byte RECOMMEND = 1;
    /** 知识体系 */
    public static final byte KNOWLEDGE_STRUCTURE = 2;
    /** 项目 */
    public static final byte PROJECT = 3;
    /** 导航 */
    public static final byte NAVIGATION = 4;

    private static final List<MainPage> MAIN_PAGES = new ArrayList<>(5);

    static
    {
        MAIN_PAGES.add(new MainPage(OFFICIAL_ACCOUNT, R.string.tab_official_account));
        MAIN_PAGES.add(new MainPage(RECOMMEND, R.string.tab_recommend));
        MAIN_PAGES.add(new MainPage(KNOWLEDGE_STRUCTURE, R.string.tab_knowledge_structure));
        MAIN_PAGES.add(new MainPage(PROJECT, R.string.tab_project));
        MAIN_PAGES.add(new MainPage(NAVIGATION, R.string.tab_navigation));
    }

    private static final String TAG = "ResUtil";

    private static Resources sResources = EnvService.getInstance().getApplicationContext().getResources();

    private ResUtil()
    {
    }

    /**
     * 获取主页所有pages:<br />
     * <ol>
     * <li>{@link #OFFICIAL_ACCOUNT}
     * <li>{@link #RECOMMEND}
     * <li>{@link #KNOWLEDGE_STRUCTURE}
     * <li>{@link #PROJECT}
     * <li>{@link #NAVIGATION}
     * </ol>
     *
     * @return 主页所有tabs
     */
    public static List<MainPage> getMainPages()
    {
        return MAIN_PAGES;
    }

    @NonNull
    public static List<String> getStringArray(int id)
    {
        try
        {
            String[] stringArray = sResources.getStringArray(id);
            // 注意：int、char等基本类型不能直接使用asList()转换
            return Arrays.asList(stringArray);
        } catch (Resources.NotFoundException e)
        {
            Logger.error(TAG, "getStringArray: not found with id " + id);
        }
        return Collections.emptyList();
    }

    @NonNull
    public static String getString(int id)
    {
        try
        {
            return sResources.getString(id);
        } catch (Resources.NotFoundException e)
        {
            Logger.error(TAG, "getString: not found with id " + id);
        }
        return "";
    }

    /** 主页类型 */
    public static class MainPage implements Parcelable
    {
        /**
         * one of {@link #OFFICIAL_ACCOUNT}, {@link #RECOMMEND}, {@link #KNOWLEDGE_STRUCTURE}, {@link #PROJECT},
         * {@link #NAVIGATION}
         */
        private byte type;

        private int titleResId;

        private MainPage(byte type, int titleResId)
        {
            this.type = type;
            this.titleResId = titleResId;
        }

        public byte getType()
        {
            return type;
        }

        @NonNull
        public String getTitle()
        {
            return getString(titleResId);
        }

        @Override
        public int describeContents()
        {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags)
        {
            dest.writeByte(this.type);
            dest.writeInt(this.titleResId);
        }

        private MainPage(Parcel in)
        {
            this.type = in.readByte();
            this.titleResId = in.readInt();
        }

        public static final Parcelable.Creator<MainPage> CREATOR = new Parcelable.Creator<MainPage>()
        {
            @Override
            public MainPage createFromParcel(Parcel source)
            {
                return new MainPage(source);
            }

            @Override
            public MainPage[] newArray(int size)
            {
                return new MainPage[size];
            }
        };
    }
}
