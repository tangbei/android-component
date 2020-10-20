---


---

<h1 id="component">component</h1>
<h2 id="android组件化方案，采用mvvm-retrofit-okhttp-jdk8-databinding-androidx-arouter方式实现，采用比较流行的技术框架和方案进行整合，实现合理的开发架构，解决项目中的代码耦合，代码臃肿、保证多人开发的情况下，项目质量等问题。">android组件化方案，采用<code>mvvm</code> <code>retrofit</code> <code>okhttp</code> <code>jdk8</code> <code>dataBinding</code> <code>androidx</code> <code>ARouter</code>方式实现，采用比较流行的技术框架和方案进行整合，实现合理的开发架构，解决项目中的代码耦合，代码臃肿、保证多人开发的情况下，项目质量等问题。</h2>
<h2 id="一、基本实现">一、基本实现</h2>
<p>该项目整合了<code>屏幕适配</code>、<code>网络请求框架</code>、<code>mvvm</code>、<code>多进程WebView</code>等功能，目的是满足大多数app开发的基本需求，使用一套代码对移动端进行开发。该项目还在努力维护中，后面会陆续添加一些新功能。</p>
<h2 id="二、项目结构">二、项目结构</h2>
<p>项目中使用阿里的<a href="https://github.com/alibaba/ARouter">ARouter</a>进行桥接，目的使各模块间解耦。同时利用gradle编译，做到各个模块在调试阶段可作为独立的app进行编译。框架结构如下：<br>
<img src="https://github.com/tangbei/android-component/blob/master/readme_img/ic_component_process.png" alt="项目结构图"><br>
通过上面的框架结构图可知，该项目分为应用层。</p>

