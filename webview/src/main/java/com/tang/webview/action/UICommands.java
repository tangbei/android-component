package com.tang.webview.action;

import android.content.Context;
import android.widget.Toast;

import com.tang.webview.WebContent;
import com.tang.webview.interfaces.ResultBack;

import java.util.Map;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/6/23
 * Description: java类作用描述
 */
public class UICommands extends Commands {

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
    }

    /**
     * 显示{@link android.widget.Toast} 信息
     */
    private CommandInterface showToastCommand = new CommandInterface() {
        @Override
        public String name() {
            return "showToast";
        }

        @Override
        public void exec(Context context, Map params, ResultBack resultBack) {
            Toast.makeText(context,String.valueOf(params.get("message")),Toast.LENGTH_SHORT).show();
        }
    };


}
