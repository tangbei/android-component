package com.tang.webview.progressbar;

/**
 * Author: tang
 * E-mail: itangbei@sina.com
 * Date: 2020/6/18
 * Description: 控制{@link WebProgressBar}的{@link android.os.Handler}
 * 是{@link ProgressBarInterface}的代理类
 */
public class IndicatorHandler {

    private ProgressBarInterface barInterface;

    public static IndicatorHandler getInstance(){
        return new IndicatorHandler();
    }

    public IndicatorHandler infactProgressView(ProgressBarInterface barInterface){
        this.barInterface = barInterface;
        return this;
    }

    public void show(){
        if (null != barInterface){
            barInterface.show();
        }
    }

    public void hide(){
        if (null != barInterface){
            barInterface.hide();
        }
    }

    public void reset(){
        if (null != barInterface){
            barInterface.reset();
        }
    }

    public void setProgressBar(int newProgress){
        if (null != barInterface){
            barInterface.setProgress(newProgress);
        }
    }

    public void setProgress(int progress){
        if (progress == 0){
            reset();
        }else if (progress >= 0 && progress <= 10){
            show();
        }else if (progress > 10 && progress <= 90){
            setProgressBar(progress);
        }else {
            setProgressBar(progress);
            hide();
        }
    }

}
