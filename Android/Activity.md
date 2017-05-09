# Android Activity
  `Activity是一个应用中的组件，它为用户提供一个可视的界面，方便用户操作，比如说拔打电话、照相、发邮件或者是浏览地图等。每个activity会提供一个可视的窗口，一般情况下这个窗口会覆盖整个屏幕，但在某此情况下也会出现一些比屏幕小的窗口飘浮在另外一个窗口上面。`
# Activity 管理
  - 创建Activity

    在 Android 中创建一个 Activity 是很简单的事情，编写一个继承自 android.app.Activity的 Java 类。
    ```Java
    public class MainActivity extends Activity {

      /**
       * 这个是必须实现的函数，在其中做初始化工作。
       * 同时必须在此函数中调用setContentView()函数的设置Activity的界面。
       * @param savedInstanceState
      */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        }
    }
    ```
  - 声明Activity

  在 AndroidManifest.xml声明。
  ```xml
  <activity android:name=".MainActivity"/>
  ```
  - 启动Activity

  [Intent说明](/android/Intent.md)
  ```Java
  Intent intent =new Intent(CurrentActivity.this,OtherActivity.class);     
startActivity(intent);
  ```
  - 结束Activity

  可以通过调用 Activity 的 finish() 方法来结束该 Activity。您还可以通过调用 finishActivity() 结束您之前启动的另一个 Activity。
  ```java
  finish();//结束当前Activity
  finishActivity(requestCode);//结束之前启动的另一个 Activity
  ```

---

# Activity 生命周期

  Activity整个生命周期的三种状态、七个重要方法。

  - 三种状态
    1. 运行中

      此 Activity 位于屏幕前台并具有用户焦点。

    2. 暂停

      另一个 Activity 位于屏幕前台并具有用户焦点，但此 Activity 仍可见。也就是说，有Activity或Toast、AlertDialog等弹出窗口显示在此 Activity 上方，并且该 Activity 部分透明或未覆盖整个屏幕。 暂停的 Activity 处于完全活动状态（Activity 对象保留在内存中，它保留了所有状态和成员信息，并与窗口管理器保持连接），但在内存极度不足的情况下，可能会被系统终止。

    3. 停止

      该 Activity 被另一个 Activity 完全遮盖（该 Activity 目前位于“后台”）。 已停止的 Activity 同样仍处于活动状态（Activity 对象保留在内存中，它保留了所有状态和成员信息，但未与窗口管理器连接）。 不过，它对用户不再可见，在他处需要内存时可能会被系统终止。

    4. 非活动(Google 开发者文档没有)

      Activity 尚未被启动、已经被手动终止，或已经被系统回收时处于非活动的状态，要手动终止Activity，可以在程序中调用"finish"方法。

  内存不足时，Dalvak 虚拟机会根据其内存回收规则来回收内存，优先级如下：
    1.  先回收与其他Activity 或Service/Intent Receiver 无关的进程。
    2. 不可见(处于Stopped状态的)Activity。
    3. Service进程(除非真的没有内存可用时会被销毁)。
    4. 非活动的可见的(Paused状态的)Activity。
    5. 当前正在运行（Active/Running状态的）Activity。


  - 七个重要方法

```Java

  /**
    * 这个是必须实现的函数，在其中做初始化工作。
    * 同时必须在此函数中调用setContentView()函数的设置Activity的界面。
    * 当Activity第一次被实例化的时候系统会调用,且整个生命周期只调用1次这个方法。
    * 系统向此方法传递一个 Bundle 对象，其中包含 Activity 的上一状态，不过前提是捕获了该状态。
    * 始终后接 onStart()
    * @param savedInstanceState
    */
   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
   }

   /**
    * 在 Activity 已停止并即将再次启动前调用。
    * 始终后接 onStart()
    */
   @Override
   protected void onRestart() {
       super.onRestart();
   }


   /**
    *在 Activity 即将对用户可见之前调用。
    *如果 Activity 转入前台，则后接 onResume().
    * 如果 Activity 转入隐藏状态，则后接 onStop()。
    */
   @Override
   protected void onStart() {
       super.onStart();
   }


   /**
    * 在 Activity 即将开始与用户进行交互之前调用。
    * 此时，Activity 处于 Activity 堆栈的顶层，并具有用户输入焦点。
    * 始终后接 onPause()。
    */
   @Override
   protected void onResume() {
       super.onResume();
   }

   /**
    * 当系统即将开始继续另一个 Activity 时调用。
    * 此方法通常用于确认对持久性数据的未保存更改、停止动画以及其他可能消耗 CPU 的内容，诸如此类。 它应该非常迅速地执行所需操作，因为它返回后，下一个 Activity 才能继续执行。
    * 如果 Activity 返回前台，则后接 onResume()，如果 Activity 转入对用户不可见状态，则后接 onStop()。
    */
   @Override
   protected void onPause() {
       super.onPause();
   }

   /**
    * 在 Activity 对用户不再可见时调用。
    * 如果 Activity 被销毁，或另一个 Activity（一个现有 Activity 或新 Activity）继续执行并将其覆盖，就可能发生这种情况。
    * 如果 Activity 恢复与用户的交互，则后接 onRestart()，如果 Activity 被销毁，则后接 onDestroy()。
    */
   @Override
   protected void onStop() {
       super.onStop();
   }

   /**
    * 在 Activity 被销毁前调用。
    * 这是 Activity 将收到的最后调用。
    * 当 Activity 结束（有人对 Activity 调用了 finish()），或系统为节省空间而暂时销毁该 Activity 实例时，可能会调用它。
    * 可以通过 isFinishing() 方法区分这两种情形。
    */
   @Override
   protected void onDestroy() {
       super.onDestroy();
   }

```


# Activity缓存方法


# 任务和返回栈

# 参考
  - [Google开发者文档](https://developer.android.com/guide/components/activities.html)

  - Activity生命周期图

    ![Activity Lifecycle](/img/activity_lifecycle.png)


# Demo
  - [Activity](/demo/ActivityDemo/app/src/main/java/ws/xn__zi8haa/activitydemo)
