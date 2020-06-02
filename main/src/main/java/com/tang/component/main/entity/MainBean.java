package com.tang.component.main.entity;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/5/22
 * Description: java类作用描述
 */
public class MainBean {

    private boolean isTabHome;
    private boolean isTabNews;
    private boolean isTabFind;
    private boolean isTabLive;
    private boolean isTabMine;

    public boolean isTabHome() {
        return isTabHome;
    }

    public void setTabHome(boolean tabHome) {
        isTabHome = tabHome;
    }

    public boolean isTabNews() {
        return isTabNews;
    }

    public void setTabNews(boolean tabNews) {
        isTabNews = tabNews;
    }

    public boolean isTabFind() {
        return isTabFind;
    }

    public void setTabFind(boolean tabFind) {
        isTabFind = tabFind;
    }

    public boolean isTabLive() {
        return isTabLive;
    }

    public void setTabLive(boolean tabLive) {
        isTabLive = tabLive;
    }

    public boolean isTabMine() {
        return isTabMine;
    }

    public void setTabMine(boolean tabMine) {
        isTabMine = tabMine;
    }
}
