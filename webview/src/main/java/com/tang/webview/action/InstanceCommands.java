package com.tang.webview.action;

import android.content.Context;

import com.tang.webview.WebContent;
import com.tang.webview.interfaces.ResultBack;

import java.util.Map;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/6/23
 * Description: java类作用描述
 */
public class InstanceCommands extends Commands {

    public InstanceCommands() {
        super();
        registerCommands();
    }

    @Override
    int getCommandLevel() {
        return WebContent.TYPE_INTERFACE;
    }

    private void registerCommands() {
        registerCommandInterface(dataProviderCommand);
    }

    private CommandInterface dataProviderCommand = new CommandInterface() {
        @Override
        public String name() {
            return "appDataProvider";
        }

        @Override
        public void exec(Context context, Map params, ResultBack resultBack) {

        }
    };
}
