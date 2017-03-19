# SpringAnimation
AndroidSpringAnimation
**前言**
下午在掘金上看到了Android终于出了弹簧动画，马不停蹄的撸起代码,研究一下这个小家伙。毕竟ios的弹簧效果已经出来很久了。话不多说，先上效果图。

**效果图**

![这里写图片描述](http://img.blog.csdn.net/20170319174321885?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMTI3NzgyMQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

**配置环境**

1.![需要25的tools](http://img.blog.csdn.net/20170319173505120?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMTI3NzgyMQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

```
 compile 'com.android.support:support-dynamic-animation:25.3.0'
```

**主要代码**

在代码中可以看到new 了一个SpringAnimation，get到Spring后，就可以对这个Spring（管理弹簧动画的实例，控制着弹簧的劲度系数，阻尼系数以及最终位置）；其中Stiffness代表劲度系数，DampingRatio代表阻尼系数，FinalPosition代表最终位置；假设最终位置一定，弹簧在运动到最终位置只受到弹力和阻尼力的作用，就可以算出弹簧的加速度，从而得到位置，形成弹簧效果。

最终位置一般默认为View的初始位置，一般只要设置劲度系数和阻尼系数就好了。f = -kx，其中f就代表弹力，k就代表劲度系数；至于阻尼系数，需要涉及到微积分，有兴趣的可以去了解下计算公式，我是理解为缓冲系数。

只要简单设置下阻尼和劲度系数就可以得到上图的效果啦，其中SpringAnimation对Z轴也有支持，不过只能在Api大于21的系统上使用。
```
 if (box.getTranslationX() != 0) {
    animationX = new SpringAnimation(box, SpringAnimation.TRANSLATION_X, 0);
    animationX.getSpring().setStiffness(getStiffness());
    animationX.getSpring().setDampingRatio(getDamping());
    animationX.getSpring().setFinalPosition(getFinalPositionX());
    animationX.setStartVelocity(velocityTracker.getXVelocity());
    animationX.start();
 }
 
 if (box.getTranslationY() != 0) {
    animationY = new SpringAnimation(box, SpringAnimation.TRANSLATION_Y, 0);
    animationY.getSpring().setStiffness(getStiffness());
    animationY.getSpring().setDampingRatio(getDamping());
//  animationY.getSpring().setFinalPosition(getFinalPositionY());
    animationY.setStartVelocity(velocityTracker.getYVelocity());
    animationY.start();
 }
 velocityTracker.clear();
```

**例子**
具体的项目我已经放到我的github了，有需要的可以看一下。


**参考文章**

 1. [阻尼系数](https://zh.wikipedia.org/wiki/%E9%98%BB%E5%B0%BC)
 2. [Android 新推出基于物理的动画库，完全诠释什么叫做弹簧效果](https://juejin.im/post/58ccd4fca22b9d00642849bf?utm_source=gold_browser_extension)
 3. [源码Gist](https://gist.github.com/nickbutcher/7fdce476aaa589680cdd626d78e3149d#file-build-gradle-L7)



