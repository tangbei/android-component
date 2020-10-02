/**
 * 自定义web和js交互对象
 */
var webNativeBridge = {};
/**
 * 设置系统
 */
webNativeBridge.os = {};
/**
 * 获取ios类型
 */
webNativeBridge.os.isIOS = /iOS|iPhone|iPad|iPod/i.test(navigator.userAgent);
/**
 * 获取Android类型
 */
webNativeBridge.os.isAndroid = !webNativeBridge.os.isIOS;

/**
 * 事件监听
 */
webNativeBridge.addEventListener = window.addEventListener;

/**
 * object转换
 * @param {*} obj 
 */
webNativeBridge.stringify = function(obj){
    var type = typeof obj;
    if (type == "object"){
        return JSON.stringify(obj);
    }else {
        return obj;
    }
};


webNativeBridge.nativecallback = function(obj){
    if(webNativeBridge.os.isIOS){
        return webNativeBridge.stringify(obj.data);
    }else if(window.webNativeBridge.os.isAndroid){
        window.webview.post(obj.callback,webNativeBridge.stringify(obj));
    }
};

/**
 * http请求调取
 * @param {*} envcmd 
 * @param {*} options 请求头配置参数
 */
webNativeBridge.http = function(envcmd,options){
    var params = {
        url: options.url,
        type: options.type || "get",
        timeout: options.timeout || 60000, // 60 second
        data: options.data,
        contentType: options.contentType,
        responseType: options.responseType,
        headers: options.headers
    };//请求头参数
    var response = {
        envcmd: envcmd,
        params: params,
        callbacks: {
            success: options.success,
            complete: options.complete,
            beforeSend: options.beforeSend,
            error: options.error
        }
    }
    response.callbacks.beforeSend ? response.callbacks.beforeSend() : ""
    webNativeBridge.postActionCallback(envcmd,params,function(params,response){
        if (para.success){
            response.callbacks.success ? response.callbacks.success(params.data) : "";
        }else{
            response.callbacks.error ? response.callbacks.error(null,params.errorReason) : "";
        }
        response.callbacks.complete ? response.callbacks.complete() : "";
    },response);
}

webNativeBridge.webNativeBridgeApi = function(options){
    return webNativeBridge.http("webNativeBridgeapi",options)
}
webNativeBridge.studioApi = function(options){
    return webNativeBridge.http("studioapi",options)
}

/**
 * url参数格式化
 * @param {*} query 
 */
webNativeBridge.parseQuery = function(query){
    var reg = /([^=&\s]+)[=\s]*([^=&\s]*)/g;
    var obj = {};
    while(reg.exec(query)){
        obj[RegExp.$1] = RegExp.$2;
    }
    return obj;
}

/**
 * 通过a标签解析url
 * @param {*} url 
 */
webNativeBridge.parseUrl = function(url){
    //获取a标签
    var a = document.createElement('a');
    a.href = url;
    var querys = webNativeBridge.parseQuery((a.search || "").replace("?",""));
    return { protocol:a.protocol || "",host:a.host || "",querys:querys,path:a.pathname || "" }
}

/**
 * 新页面跳转
 * @param {*} paras 
 */
webNativeBridge.newPage = function(params){
    var url = params.url;
    if (!url){
        return;
    }
    var urlinfo = webNativeBridge.parseUrl(url);
    //获取协议头
    if(urlinfo.protocol == "dajia:"){
        var openPageParas = {
            object_title: params.title,
            object_type: urlinfo.querys.objectType,
            object_info: urlinfo.querys
        }
        var messageParas = {
            title: params.title,
            content: "",
            action: "OPEN_APP_INTERNAL_PAGE",
            extra: openPageParas,
            needCloseSelf: paras.needCloseSelf,
            url: params.url
        }
        webNativeBridge.post("newPage",messageParas);
    }else{
        webNativeBridge.post("newPage",paras);
    }
}


/**
 * 跳转至新的web页面
 */
webNativeBridge.newWebPage = function(url, title, needCloseSelf) {
    needCloseSelf = !!needCloseSelf ? needCloseSelf : false;
    try {
        webNativeBridge.newPage({
            title: title,
            url: url,
            needCloseSelf: needCloseSelf
        });
    } catch (e) {
        document.location = url;
    }
};

/**
 * 回调名
 * 时间戳+随机数生成，唯一性
 */
webNativeBridge.callbackName = function(){
    return "webNativeBridge_api_callback_" + (new Date()).getTime() + "_" + Math.floor(Math.random() * 10000);
};

/**
 * 存储当前回调名
 */
webNativeBridge.callbackStorage = {};

/**
 * 添加回调
 * @param {*} name 回调名，由时间戳和随机数生成、且唯一
 * @param {*} func 回调方法
 * @param {*} userData 用户信息
 */
webNativeBridge.addCallback = function(name,func,userData){
    //添加之前，先删除原有的存储
    delete webNativeBridge.callbackStorage[name];
    //存储回调信息
    webNativeBridge.callbackStorage[name] = {callback: func, userData: userData};
};

/**
 * 回调方法
 * 此方法暴露给native调用
 * @param {*} params 
 */
webNativeBridge.callback = function(params){
    //获取最近存储的回调名
    var callbackObject = webNativeBridge.callbackStorage[params.callbackName];
    //判断回调名是否存在
    if (callbackObject !== undefined){
        if (callbackObject.userData !== undefined){
            callbackObject.userData.callbackData = params;
        }
        //判断回调方法是否存在
        if(callbackObject.callback != undefined){
            //执行回调方法
            var ret = callbackObject.callback(params,callbackObject.userData);
            if(ret === false){
                return;
            }
            //删除存储的回调
            delete webNativeBridge.callbackStorage[params.callbackName];
        }
    }
};

/**
 * js向webView添加一个提示
 * @param {*} msg 提示内容
 */
webNativeBridge.toast = function(msg) {
    webNativeBridge.post("toast",{message:msg})
}

/**
 * js向webView添加一个弹窗
 * @param {*} content 内容
 * @param {*} callback 回调方法
 */
webNativeBridge.dialog = function(content,callback){
    webNativeBridge.dialogSubmit("",content,"确定",callback);
}

/**
 * js向webView添加一个弹窗
 * @param {*} title 标题
 * @param {*} content 内容
 * @param {*} confirm 确定
 * @param {*} callback 回调方法
 */
webNativeBridge.dialogSubmit = function(title,content,confirm,callback){
    webNativeBridge.dialogAdjust(title,content,confirm,"",callback);
}

/**
 * js向webView添加一个弹窗
 * @param {*} title 标题-如果为空，则native使用默认标题
 * @param {*} content 内容-不能为空
 * @param {*} confirm 确定-不能为空，可更改显示名称
 * @param {*} cancel 取消-如果为空，则只有确定按钮，可更改显示名称
 * @param {*} callback 回调方法，为null，则不写
 */
webNativeBridge.dialogAdjust = function(title,content,confirm,cancel,callback) {
    //获取回调名
    var callbackName = webNativeBridge.callbackName();
    if (null != callback) {
        //添加回调方法
        webNativeBridge.addCallback(callbackName,callback);
    }
    var dialogMap = {
        title: title ? title : "",
        content: content,
        confirm: confirm ? confirm : "",
        cancel: cancel ? cancel : "",
        callbackName: callbackName,
    };
    webNativeBridge.post("dialog",dialogMap);
}

/**
 * 向webView发送指令
 * @param {*} action 指令名
 * @param {*} params 参数
 */
webNativeBridge.postAction = function(action,params){
    window.webview.post(action,params);
};

/**
 * 向webView发送指令并回调
 * @param {*} action 指令名
 * @param {*} params 参数
 * @param {*} callback 回调方法
 */
webNativeBridge.postActionCallback = function(action,params,callback){
    var callbackname = webNativeBridge.callbackname();
    webNativeBridge.addCallback(callbackname,callback);
    window.webview.post(action,params);
};

/**
 * 自定义事件触发机制
 * @param {*} params 参数
 */
webNativeBridge.dispatchEvent = function(params){
    if (!params) {
        params = {"name":"webviewLoadComplete"};
    }
    var event = {};
    try {
        //设置自定义事件名称
        event = new CustomEvent(params.name);
        //设置自定义事件的参数
        event.params = params.params;
    } catch(e) {
        event = document.createEvent("HTMLEvents");
        event.initEvent(params.name, false, false);
    }
    window.dispatchEvent(event);
};

/**
 * 执行带回调的方法
 * @param {*} cmd 
 * @param {*} para 
 * @param {*} callback 
 * @param {*} ud 
 */
webNativeBridge.postWithCallback = function(cmd,para,callback,ud){
    var callbackname = webNativeBridge.callbackname();
    webNativeBridge.addCallback(callbackname,callback,ud);
    if(webNativeBridge.os.isIOS){
        var message = {};
        message.meta  = {
            cmd:cmd,
            callback:callbackname
        };
        message.para = para;
        window.webview.post(message);
    }else if(window.webNativeBridge.os.isAndroid){
        para.callback = callbackname;
        window.webview.post(cmd,JSON.stringify(para));
    }
};

/**
 * js向webView传递数据
 * 'post'是前后端的约定
 * @param {*} action 指令
 * @param {*} params 内容
 */
webNativeBridge.post = function(action,params){
    if(webNativeBridge.os.isIOS){
        // var message = {};
        // message.meta = {
        //     action:action
        // };
        // message.params = params || {};
        window.webview.post(params);
    }else if(window.webNativeBridge.os.isAndroid){
        window.webview.post(action,JSON.stringify(params));
    }
};

window.webNativeBridge = webNativeBridge;
