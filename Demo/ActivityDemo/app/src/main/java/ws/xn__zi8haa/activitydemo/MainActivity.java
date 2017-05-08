package ws.xn__zi8haa.activitydemo;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends Activity {

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
}
