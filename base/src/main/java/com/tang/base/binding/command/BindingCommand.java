package com.tang.base.binding.command;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/5/27
 * Description: java类作用描述
 */
public class BindingCommand<T> {

    private BindingAction mAction;

    public BindingCommand(BindingAction action) {
        this.mAction = action;
    }

    /**
     * 执行BindingAction命令
     */
    public void execute(){
        if (null != mAction){
            mAction.call();
        }
    }
}
