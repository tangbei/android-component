/**
 * Copyright (C), 2019-2020
 * FileName: IEnvironment
 * Author: tangbei
 * Date: 2020/4/8 5:00 PM
 * Description:
 * History:
 */
package com.tang.component.network.environment;

/**
 * @ClassName: IEnvironment
 * @Description: 切换网络请求环境接口
 * @Author: tangbei
 * @Date: 2020/4/8 5:00 PM
 */
public interface IEnvironment {

    /**
     * 生产环境
     * @return
     */
    String getProduce();

    /**
     * 测试环境
     * @return
     */
    String getTest();
}