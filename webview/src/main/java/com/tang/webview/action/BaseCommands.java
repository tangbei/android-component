package com.tang.webview.action;

import android.content.Context;

import com.tang.base.utils.LogUtil;
import com.tang.webview.WebContent;
import com.tang.webview.interfaces.ResultBack;

import java.util.Map;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/6/23
 * Description: 基础通用的非UI指令处理
 */
public class BaseCommands extends Commands {

    public BaseCommands() {
        super();
        registerCommands();
    }

    @Override
    int getCommandLevel() {
        return WebContent.TYPE_NORMAL;
    }

    private void registerCommands() {
        registerCommandInterface(routerCommand);
    }

    private CommandInterface routerCommand = new CommandInterface() {
        @Override
        public String name() {
            return "newPage";
        }

        @Override
        public void exec(Context context, Map params, ResultBack resultBack) {
            String newUrl = params.get("url").toString();
            String title = params.get("title").toString();
            LogUtil.w("BaseCommands","newUrl:"+newUrl+",title:"+title);
        }
    };
}
