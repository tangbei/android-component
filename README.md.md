component  
================

android组件化方案，采用`mvvm` `retrofit` `okhttp` `jdk8` `dataBinding` `androidx` `ARouter`方式实现，采用比较流行的技术框架和方案进行整合，实现合理的开发架构，解决项目中的代码耦合，代码臃肿、保证多人开发的情况下，项目质量等问题。
------
##  一、基本实现
该项目整合了`屏幕适配`、`网络请求框架`、`mvvm`、`多进程WebView`等功能，目的是满足大多数app开发的基本需求，使用一套代码对移动端进行开发。该项目还在努力维护中，后面会陆续添加一些新功能。

## 二、项目结构
项目中使用阿里的[ARouter](https://github.com/alibaba/ARouter)进行桥接，目的使各模块间解耦。同时利用gradle编译，做到各个模块在调试阶段可作为独立的app进行编译。框架结构如下：
![项目结构图](https://github.com/tangbei/android-component/blob/master/readme_img/ic_component_process.png)
通过上面的框架结构图可知，该项目分为应用层。
