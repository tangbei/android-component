package com.tang.base.model;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/5/26
 * Description: java类作用描述
 */
public interface IModel {

    /**
     * viewModel销毁时清除Model,与ViewModel共消亡。
     * model层同样不能持有长生命周期对象
     */
    void onCleared();

}
