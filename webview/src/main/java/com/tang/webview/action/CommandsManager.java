package com.tang.webview.action;

import android.content.Context;

import com.tang.webview.WebContent;
import com.tang.webview.interfaces.AidlError;
import com.tang.webview.interfaces.ResultBack;

import java.util.Map;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/6/23
 * Description: 指令集合管理类
 */
public class CommandsManager {

    private static CommandsManager instance;
    private UICommands uiCommands;
    private BaseCommands baseCommands;
    private InstanceCommands instanceCommands;

    private CommandsManager(){
        uiCommands = new UICommands();
        baseCommands = new BaseCommands();
        instanceCommands = new InstanceCommands();
    }

    public static CommandsManager getInstance() {
        if (null == instance) {
            synchronized (CommandsManager.class) {
                if (null == instance) {
                    instance = new CommandsManager();
                }
            }
        }
        return instance;
    }

    /**
     * 判断该指令是否已经注册
     */
    public boolean checkUICommand(int commandLevel,String cmd){
        return uiCommands.getCommands().get(cmd) != null;
    }

    /**
     * 只做展示ui的指令处理方法
     * 并交由{@link UICommands}处理
     * @param context 上下文对象
     * @param commandLevel 当前类型
     * @param cmd 指令
     * @param params 内容
     * @param resultBack 回调
     */
    public void findAndExecUICommand(Context context, int commandLevel, String cmd, Map params, ResultBack resultBack){
        if (uiCommands.getCommands().get(cmd) != null){
            uiCommands.getCommands().get(cmd).exec(context,params,resultBack);
        }
    }

    /**
     * 非ui指令的处理方法
     * @param context
     * @param commandLevel
     * @param cmd
     * @param params
     * @param resultBack
     */
    public void findAndExecNonUICommand(Context context,int commandLevel,String cmd, Map params, ResultBack resultBack){
        boolean methodFlag = false;
        switch (commandLevel){
            case WebContent.TYPE_NORMAL:
                if (null != baseCommands.getCommands().get(cmd)){
                    methodFlag = true;
                    baseCommands.getCommands().get(cmd).exec(context,params,resultBack);
                }
                if (null != instanceCommands.getCommands().get(cmd)){
                    resultBack.onResult(WebContent.FAILED,cmd,new AidlError(WebContent.ErrorCode.NO_AUTH,WebContent.ErrorMessage.NO_AUTH));
                }
                break;
            case WebContent.TYPE_INTERFACE:
                if (null != baseCommands.getCommands().get(cmd)){
                    methodFlag = true;
                    baseCommands.getCommands().get(cmd).exec(context,params,resultBack);
                }
                if (null != instanceCommands.getCommands().get(cmd)){
                    methodFlag = true;
                    instanceCommands.getCommands().get(cmd).exec(context,params,resultBack);
                }
                break;
            default:
                break;
        }
        if (!methodFlag){
            resultBack.onResult(WebContent.FAILED,cmd,new AidlError(WebContent.ErrorCode.NO_METHOD,WebContent.ErrorMessage.NO_METHOD));
        }
    }
}
