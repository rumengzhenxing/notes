package ws.xn__zi8haa.networkdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import ws.xn__zi8haa.networkdemo.bean.BaseBean;

public class HttpURLConnectionActivity extends AppCompatActivity {

    public final static String TAG = new Object() {
        public String getClassName() {
            String clazzName = this.getClass().getName();
            return clazzName.substring(0, clazzName.lastIndexOf('$'));
        }
    }.getClassName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_client);
    }


    /**
     * 发送git请求
     * @param url
     * @throws IOException
     */
    public void sendGetRequest(String url) throws IOException {
        String line = null;
        String result = null;
        HttpURLConnection mHttpURLConnection=null;
        InputStreamReader mInputStreamReader=null;
        //使用HttpURLConnection 打开链接
        try{
            URL mUrl = new URL(url);
            mHttpURLConnection = (HttpURLConnection ) mUrl.openConnection();
            //读取响应的内容(流)
            mInputStreamReader = new InputStreamReader(mHttpURLConnection.getInputStream());
            BufferedReader mBufferedReader = new BufferedReader(mInputStreamReader );
            if ((line = mBufferedReader.readLine()) != null) {
                result = line + "\n";
            }
            Log.e("", "请求结果：" + result);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            mInputStreamReader.close();
            mHttpURLConnection.disconnect();
        }
    }


    /**
     *发送Post请求
     * @param url 请求地址
     * @param baselist 请求参数
     * @throws IOException
     */
    public void sendPostRequest(String url,List<BaseBean> baselist){
        String line = null;
        String result = null;
        HttpURLConnection mHttpURLConnection=null;
        try {
            URL mUrl = new URL(url);
            mHttpURLConnection = (HttpURLConnection) mUrl.openConnection();
            //设置读取超时为10秒
            mHttpURLConnection.setReadTimeout(10000);
            //设置链接超时为15秒
            mHttpURLConnection.setConnectTimeout(15000);
            //设置请求方式
            mHttpURLConnection.setRequestMethod("Post");
            //接收输入流
            mHttpURLConnection.setDoInput(true);
            //启动输出流，当需要传递参数时需要开启
            mHttpURLConnection.setDoOutput(true);
            //添加Header
            mHttpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            //将参数写入到输出流
            writeParams(mHttpURLConnection.getOutputStream(), baselist);

            //发起请求
            mHttpURLConnection.connect();
            BufferedReader mBufferedReader = new BufferedReader(new InputStreamReader(mHttpURLConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            while ((line = mBufferedReader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
            Log.d(TAG,result);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally{
            mHttpURLConnection.disconnect();
        }

    }


    /**
     * 将参数写入输出流
     * @param mOutputStream
     * @param paramsList
     * @throws IOException
     */
    private void writeParams(OutputStream mOutputStream, List<BaseBean> paramsList) throws IOException {
        StringBuilder paramStr = new StringBuilder();
        for (BaseBean pair : paramsList) {
            if (!TextUtils.isEmpty(paramStr)) {
                paramStr.append("&");
            }
            paramStr.append(URLEncoder.encode(pair.getKey(), "UTF-8"));
            paramStr.append("=");
            paramStr.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(mOutputStream, "UTF-8"));
        //将参数写入输出流
        writer.write(paramStr.toString());
        writer.flush();
        writer.close();
    }
}
