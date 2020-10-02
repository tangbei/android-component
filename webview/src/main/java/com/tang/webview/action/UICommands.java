package com.tang.webview.action;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tang.base.utils.LogUtil;
import com.tang.webview.WebContent;
import com.tang.webview.interfaces.ResultBack;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/6/23
 * Description: java类作用描述
 */
public class UICommands extends Commands {

    private final String DIALOG_TITLE = "title";
    private final String DIALOG_CONTENT = "content";
    private final String DIALOG_CONFIRM = "confirm";
    private final String DIALOG_CANCEL = "cancel";
    private final String DIALOG_CALLBACK_NAME = "callbackName";

    public UICommands() {
        super();
        registerCommands();
    }

    @Override
    int getCommandLevel() {
        return WebContent.TYPE_UI;
    }

    /**
     * 添加指令方法
     */
    private void registerCommands() {
        registerCommandInterface(showToastCommand);
        registerCommandInterface(showDialogCommand);
    }

    /**
     * 显示{@link android.widget.Toast} 信息
     */
    private CommandInterface showToastCommand = new CommandInterface() {
        @Override
        public String name() {
            return WebContent.Instruct.TOAST;
        }

        @Override
        public void exec(Context context, Map params, ResultBack resultBack) {
            Toast.makeText(context,String.valueOf(params.get("message")),Toast.LENGTH_SHORT).show();
        }
    };

    private CommandInterface showDialogCommand = new CommandInterface() {
        @Override
        public String name() {
            return WebContent.Instruct.DIALOG;
        }

        @Override
        public void exec(Context context, Map params, ResultBack resultBack) {
            LogUtil.d("我是dialog:"+new Gson().toJson(params));
            if (null != params){
                String title = (String) params.get(DIALOG_TITLE);
                String content = (String) params.get(DIALOG_CONTENT);
                String confirm = (String) params.get(DIALOG_CONFIRM);
                String cancel = (String) params.get(DIALOG_CANCEL);
                String callbackName = (String) params.get(DIALOG_CALLBACK_NAME);
                if (!TextUtils.isEmpty(content)){
                    AlertDialog dialog = new AlertDialog.Builder(context)
                            .setTitle(TextUtils.isEmpty(title) ? "提示" : title)
                            .setMessage(content)
                            .create();
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.setButton(DialogInterface.BUTTON_POSITIVE, TextUtils.isEmpty(confirm) ? "确定" : confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            resultBack.onResult(WebContent.SUCCESS,name(),getWebCallback("confirm","",callbackName));
                        }
                    });
                    dialog.setButton(DialogInterface.BUTTON_NEGATIVE, TextUtils.isEmpty(cancel) ? "取消" : cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.show();
                }else {
                    Toast.makeText(context,"弹窗内容不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    private Map<String, String> getWebCallback(String action,String content,String callbackName) {
        Map<String,String> map = new HashMap<>();
        map.put(WebContent.NATIVE2WEB_ACTION,action);
        map.put(WebContent.NATIVE2WEB_CONTENT,content);
        map.put(WebContent.NATIVE2WEB_CALLBACK,callbackName);
        return map;
    }


}
