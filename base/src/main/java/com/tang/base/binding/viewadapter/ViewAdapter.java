package com.tang.base.binding.viewadapter;

import android.view.View;

import androidx.databinding.BindingAdapter;

import com.jakewharton.rxbinding2.view.RxView;
import com.tang.base.binding.command.BindingCommand;
import com.tang.base.binding.command.BindingConsumer;
import java.util.concurrent.TimeUnit;
import io.reactivex.functions.Consumer;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/5/27
 * Description: java类作用描述
 */
public class ViewAdapter {

    /**
     * 防重复点击间隔(单位：毫秒)
     */
    public static final int CLICK_INTERVAL = 2000;

    /**
     * 自定义view点击事件属性
     * @param view 当前view
     * @param consumer 被观察者
     * @param isThrottleFirst 默认为false,就是防重复点击，如果致为true，则是正常选中
     */
    @BindingAdapter(value = {"onClickCommand","isThrottleFirst"}, requireAll = false)
    public static void onClickCommand(View view, final BindingConsumer consumer, boolean isThrottleFirst){
        if (isThrottleFirst){
            RxView.clicks(view).subscribe(consumer);
        }else {
            RxView.clicks(view).throttleFirst(CLICK_INTERVAL, TimeUnit.MILLISECONDS).subscribe(consumer);
        }
    }

    /**
     * 自定义view点击事件属性
     * 防重复提交
     * @param view
     * @param clickCommand
     */
    @BindingAdapter(value = {"onClickDelayedCommand"})
    public static void onClickDelayedCommand(View view, final BindingCommand clickCommand){
        RxView.clicks(view).throttleFirst(CLICK_INTERVAL, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        if (null != clickCommand){
                            clickCommand.execute();
                        }
                    }
                });
    }

    @BindingAdapter({"onFocusChangeCommand"})
    public static void onFocusChangeCommand(View view, final BindingConsumer<Boolean> consumer){
        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (null != consumer){
                    try {
                        consumer.accept(hasFocus);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
