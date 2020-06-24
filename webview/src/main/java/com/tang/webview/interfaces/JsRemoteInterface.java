package com.tang.webview.interfaces;

import android.content.Context;
import android.os.Handler;
import android.webkit.JavascriptInterface;

import com.tang.base.utils.LogUtil;


/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/6/17
 * Description: 和js远程交互接口类
 */
public class JsRemoteInterface {

    private final Context mContext;
    private AidlCommand mAidlCommand;
    private final Handler postHandler = new Handler();

    public JsRemoteInterface(Context context) {
        this.mContext = context;
    }

    /**
     * 与js交互的实现方法
     * 加上注解，解决在4.2之后的安全性问题
     * @param cmd js指令key
     * @param params key的参数
     */
    @JavascriptInterface
    public void post(final String cmd, final String params){
        LogUtil.d("我是和js的交互："+cmd+ " ," + params);
        postHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (null != mAidlCommand){
                        mAidlCommand.exec(mContext,cmd,params);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void setAidlCommand(AidlCommand aidlCommand) {
        this.mAidlCommand = aidlCommand;
    }

    /**
     * 拿到交互后的回调数据
     */
    public interface AidlCommand {
        void exec(Context context,String cmd,String params);
    }
}
