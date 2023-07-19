# ComposeUIWidgets

日常积累的一些Compose UI小组件,可以方便以后复用,也欢迎您提交自己的一些UI组件

### 项目目录介绍

- **samples模块**:示例程序，演示各个组件的使用及效果
- **library模块**:UI组件的实现
  - **extensions**:Modifier扩展,使用方法和标准Modifier扩展方法一致
  - **ui**:单独实现的UI组件
  - **utils**:工具类

### 已有UI组件介绍(使用方法参考samples模块)


|          方法名           |                                效果                                | 参数                                                                                                                                                                                                                           | 贡献者 |
|:------------------------:|:-----------------------------------------------------------------:|:-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|:-----:|
|     **PressEffect**      |          ![pressEffect.gif](screenshots/pressEffect.gif)          | minScale:最小的缩放值(0f-1f)                                                                                                                                                                                                     |   -   |
|       **Rotation**       |             ![Rotation.gif](screenshots/Rotation.gif)             | speed 旋转速度,单位圈/秒                                                                                                                                                                                                         |   -   |
|    **ShakingEffect**     |        ![ShakingEffect.gif](screenshots/ShakingEffect.gif)        | animate:动画触发开关，此值改变后触发一次动画<br>maxDp:抖动的最大距离<br>duration:动画持续时间<br>direction:动画方向                                                                                                                       |   -   |
|    **ShinningEffect**    |       ![ShinningEffect.gif](screenshots/ShinningEffect.gif)       | shinningColor:闪光颜色，默认白色<br>shinningPercent:闪光宽度占比，默认30%，取值范围[0f-1f]<br>duration:一次动画持续时间 默认1500ms<br>delayMillis:两次动画时间间隔 默认300ms<br>skewAngle:闪光倾斜的角度，默认30度 取值范围[0f-360f],顺时针        |   -   |
| **StreamerBorderEffect** | ![StreamerBorderEffect.gif](screenshots/StreamerBorderEffect.gif) | borderWidth:边框宽度<br>borderShape:边框形状<br>colorList:颜色列表<br>colorStops:颜色分割列表<br>tileMode:重复模式                                                                                                                    |   -   |
|  **HeartBeatOfBorder**   |    ![HeartBeatOfBorder.gif](screenshots/HeartBeatOfBorder.gif)    | expandLength:动画向外扩展的长度<br>borderShape:边框形状<br>borderColor:边框颜色<br>borderWidth:边框宽度<br>duration:单次跳动持续时间<br>delayMillis:两次跳动之间的延迟时间<br>content:包裹内容                                               |   -   |
|  **heartBeatOfContent**  |   ![heartBeatOfContent.gif](screenshots/heartBeatOfContent.gif)   | scaleFactor:缩放因数，介于0-1之间<br>duration:单次跳动持续时间<br>delayMillis:两次跳动之间的时间间隔                                                                                                                                    |   -   |
|     **FlipTextView**     |         ![FlipTextView.gif](screenshots/FlipTextView.gif)         | fontSize:字号<br>from:原字符串<br>to:待动画跳转的字符串<br>backgroundColor:背景色<br>textColor:文本颜色<br>textTypeface:文本字体<br>dividerColor:中间分隔符颜色<br>dividerWidth:中间分隔符宽度，传入0dp时为无分割线                            |   -   |
|  **SwitchPageLayout**   |     ![SwitchPageLayout.gif](screenshots/SwitchPageLayout.gif)     | modifier<br>switchDirection:页面动画方向<br>autoSwitch:是否开启自动切换<br>delayMillis:切换时延，每个页面展示的时间<br>duration: 切换动画持续时长<br>onClick: 点击事件回调<br>content:每个页面的视图，有几个页面就在同一层级定义几个组件，参考Samples |   -   |
|    **LuminousBorder**    |         ![LuminousBorder](screenshots/LuminousBorder.png)         | shape:边框形状<br>borderColor:边框颜色<br>borderWidth:边框宽度<br>radius:blur半径                                                                                                                                                  |   -   |


### 效果一览

![screenshot1.gif](screenshots/screenshot1.gif)
![screenshot2.gif](screenshots/screenshot2.gif)
