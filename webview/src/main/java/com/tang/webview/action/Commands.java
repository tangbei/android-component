package com.tang.webview.action;

import java.util.HashMap;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/6/23
 * Description: java类作用描述
 */
public abstract class Commands {

    private HashMap<String,CommandInterface> commands;

    abstract int getCommandLevel();

    public Commands(){
        commands = new HashMap<>();
    }

    public HashMap<String, CommandInterface> getCommands() {
        return commands;
    }

    /**
     * 注册指令
     * @param commandInterface
     */
    protected void registerCommandInterface(CommandInterface commandInterface){
        commands.put(commandInterface.name(),commandInterface);
    }
}
