package com.dp2003.zkfingerprint;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dp2003.zkfingerprint.Bean.GetSearchResultRequest;
import com.dp2003.zkfingerprint.Bean.GetSearchResultResponse;
import com.dp2003.zkfingerprint.Bean.LoginRequest;


import com.dp2003.zkfingerprint.Bean.LoginResponse;
import com.dp2003.zkfingerprint.Bean.Record;
import com.dp2003.zkfingerprint.Bean.SearchReaderRequest;
import com.dp2003.zkfingerprint.Bean.SearchReaderResponse;
import com.dp2003.zkfingerprint.LibraryClient.ErrorCode;
import com.zkteco.android.biometric.core.device.ParameterHelper;
import com.zkteco.android.biometric.core.device.TransportType;
import com.zkteco.android.biometric.core.utils.LogHelper;
import com.zkteco.android.biometric.core.utils.ToolUtils;
import com.zkteco.android.biometric.module.fingerprintreader.FingerprintCaptureListener;
import com.zkteco.android.biometric.module.fingerprintreader.FingerprintSensor;
import com.zkteco.android.biometric.module.fingerprintreader.FingprintFactory;
import com.zkteco.android.biometric.module.fingerprintreader.ZKFingerService;
import com.zkteco.android.biometric.module.fingerprintreader.exception.FingerprintException;
import com.zkteco.zkfinger.FingerprintService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;


import com.google.gson.*;

import okhttp3.OkHttpClient;
import okhttp3.*;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public static final MediaType MEDIA_TYPE_JSON
            = MediaType.parse("application/json; charset=utf-8");

    final String baseUrl = "http://192.168.1.100/dp2library/xe/rest/";
    String username = "supervisor";
    String password = "";

    public static final int VID = 6997;
    public static final int PID = 288;
    private boolean bStart = false;
    private boolean isRegister = false;
    private int uid = 1;
    private byte[][] regtempArray = new byte[3][2048];
    private int enrollIdx = 0;
    private byte[] lastRegTemp = new byte[2048];


    private TextView textView = null;
    private TextView progress = null;
    private ImageView imageView = null;
    private Button buttonBegin = null;
    private Button buttonStop = null;
    private Button buttonEnroll= null;
    private Button buttonIdentify = null;

    // private TextToSpeech tts = null;

    private FingerprintSensor fingerprintSensor = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
/*
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS){
                    int ret = tts.setLanguage(Locale.CHINESE);
                    if (ret != TextToSpeech.LANG_COUNTRY_AVAILABLE
                            && ret != TextToSpeech.LANG_AVAILABLE){
                        Toast.makeText(MainActivity.this, "TTS 暂时不支持这种语言的朗读。",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
*/
        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);

        buttonBegin = findViewById(R.id.begin);

        buttonStop = findViewById(R.id.stop);
        buttonStop.setEnabled(false);

        buttonEnroll = findViewById(R.id.enroll);
        buttonEnroll.setEnabled(false);

        buttonIdentify = findViewById(R.id.identify);
        buttonIdentify.setEnabled(false);

        startFingerprintSensor();

        // client = new OkHttpClient();
    }

/*
    @Override
    protected void onDestroy() {
        if (tts !=null){
            tts.shutdown();
        }
        super.onDestroy();
    }
*/

    private void startFingerprintSensor(){
        LogHelper.setLevel(Log.ASSERT);

        // Map<String,Integer> fingerprintParams = new HashMap<>();
        Map fingerprintParams = new HashMap();
        fingerprintParams.put(ParameterHelper.PARAM_KEY_VID,VID);
        fingerprintParams.put(ParameterHelper.PARAM_KEY_PID,PID);

        fingerprintSensor = FingprintFactory.createFingerprintSensor(this, TransportType.USB, fingerprintParams);
        // fingerprintSensor.getDeviceTag();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onButtonBegin(View view) throws FingerprintException{
        try {
            if(bStart) return;
            fingerprintSensor.open(0);
            final FingerprintCaptureListener listener = new FingerprintCaptureListener() {
                @Override
                public void captureOK(final byte[] fpImage) {
                    final int width = fingerprintSensor.getImageWidth();
                    final int height = fingerprintSensor.getImageHeight();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (null != fpImage){
                                ToolUtils.outputHexString(fpImage);
                                LogHelper.i("width=" + width + "\nheight=" + height);
                                Bitmap bitmapFp = ToolUtils.renderCroppedGreyScaleBitmap(fpImage, width, height);
                                imageView.setImageBitmap(bitmapFp);
                            }
                        }
                    });
                }

                @Override
                public void captureError(FingerprintException e) {
                    final FingerprintException exp = e;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LogHelper.d("captureError errno=" + exp.getErrorCode() + ",Internal error code=" + exp.getInternalErrorCode() + ",message=" + exp.getMessage());
                        }
                    });
                }

                @Override
                public void extractOK(final byte[] fpTemplate) {
                    final byte[] buffer = fpTemplate;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(isRegister){
                                byte[] bufids = new byte[256];
                                int ret = ZKFingerService.identify(buffer,bufids,55,1);
                                if(ret > 0){
                                    String strRes[] = new String(bufids).split("\t");
                                    textView.setText("The finger already enroll by " + strRes[0] + ",cancel enroll");
                                    isRegister = false;
                                    enrollIdx = 0;
                                    return;
                                }

                                if (enrollIdx > 0 && ZKFingerService.verify(regtempArray[enrollIdx - 1],buffer) <= 0){
                                    textView.setText("Please press the same finger 3 items for the enrollment");
                                    return;
                                }

                                System.arraycopy(buffer,0, regtempArray[enrollIdx],0,2048);
                                enrollIdx++;
                                if (enrollIdx == 3){
                                    byte[] regTemp = new byte[2048];
                                    if (0 < (ret = ZKFingerService.merge(regtempArray[0], regtempArray[1], regtempArray[2],regTemp))){
                                        ZKFingerService.save(regTemp,"test" + uid++);

                                        System.arraycopy(regTemp,0,lastRegTemp,0,ret);
                                        String strBase64 = Base64.encodeToString(regTemp,0,ret,Base64.NO_WRAP);

                                        byte[] deRegTemp = Base64.decode(strBase64,Base64.NO_WRAP);
                                        textView.setText("Enroll success,uid:" + uid + ",count:" + ZKFingerService.count());
                                        // tts.speak("登记成功。",TextToSpeech.QUEUE_ADD,null);
                                    }else {
                                        textView.setText("Enroll fail.");
                                        // tts.speak("登记失败。",TextToSpeech.QUEUE_ADD,null);
                                    }
                                    isRegister = false;
                                }else {
                                    textView.setText("You need to press the " + (3 - enrollIdx) + " time fingerprint.");
                                    // tts.speak("请再按一次。",TextToSpeech.QUEUE_ADD,null);
                                }
                            }else {
                                byte[] bufids = new byte[256];
                                int ret = ZKFingerService.identify(buffer,bufids,55,1);
                                if (ret > 0){
                                    String strRes[] = new String(bufids).split("\t");
                                    textView.setText("Identify success,user id:" + strRes[0] + ",score:" + strRes[1]);
                                }else {
                                    textView.setText("Identify fail.");
                                    // tts.speak("认证失败",TextToSpeech.QUEUE_ADD,null);
                                }
                            }
                        }
                    });
                }

                @Override
                public void extractError(final int i) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText("Extract fail,error code:" + i);
                        }
                    });
                }
            };

            fingerprintSensor.setFingerprintCaptureListener(0,listener);
            fingerprintSensor.startCapture(0);
            bStart = true;
            byte[] version = new byte[128];
            FingerprintService.setParameter(1, 30);
            FingerprintService.version(version);
            textView.setText("Start capture success.version: " + new String(version));

            buttonBegin.setEnabled(false);

            buttonStop.setEnabled(true);
            buttonEnroll.setEnabled(true);
            buttonIdentify.setEnabled(true);


        }catch (FingerprintException e) {
            textView.setText("Begin capture fail.\nError code:"+ e.getErrorCode()
                    + "\nError message:" + e.getMessage() + "\nInternal Error code:" + e.getInternalErrorCode());
        }
    }

    public void onButtonStop(View view) throws FingerprintException{
        try {
            if (bStart){
                fingerprintSensor.stopCapture(0);
                bStart = false;
                fingerprintSensor.close(0);
                buttonBegin.setEnabled(true);

                buttonStop.setEnabled(false);
                buttonEnroll.setEnabled(false);
                buttonIdentify.setEnabled(false);
                textView.setText("Stop capture success.");
            }else {
                textView.setText("Already stop.");
            }

        }catch (FingerprintException e){
            textView.setText("Stop fail,errno:" + e.getErrorCode() + "\nmessage:" + e.getMessage());
        }
    }

    public void onButtonEnroll(View view){
        if (bStart){
            isRegister = true;
            enrollIdx = 0;
            textView.setText("You need to press the 3 time fingerprint.");
            /*
            tts.speak("你需要按 3 次。",
                    TextToSpeech.QUEUE_ADD,
                    null);
            */
        }
        else {
            textView.setText("Please begin capture first.");
        }
    }

    public void onButtonVerify(View view){
        if (bStart){
            isRegister = false;
            enrollIdx = 0;
        }else {
            textView.setText("Please begin capture first.");
        }
    }

    public void onLogin(View view) {
        login();
    }

    public void onSearchReader(View view){
        searchReader();
    }

    public void onLoadingFinger(View view){
        getSearchResult();
    }

    OkHttpClient client = null;
    public void login(){

        client = new OkHttpClient.Builder().cookieJar(new CookieJar() {
            private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();
            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                cookieStore.put(HttpUrl.parse(url.host()),cookies);
//                for(Cookie cookie:cookies){
//                    Log.i(TAG,"cookie Name:"+cookie.name());
//                    Log.i(TAG,"cookie Path:"+cookie.path());
//                }
//                Log.i(TAG,"saveFromResponse url = " + url.toString());
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(HttpUrl.parse(url.host()));
//                Log.i(TAG,"loadForRequest url = " + url.toString());
//                if(cookies == null){
//                    Log.i(TAG,"没加载到cookie");
//                }
                return cookies != null ? cookies : new ArrayList<Cookie>();
            }
        }).build();

        final LoginRequest loginRequest = new LoginRequest();
        loginRequest.setStrUserName(username);
        loginRequest.setStrPassword(password);
        loginRequest.setStrParameters("type=worker,client=mobileApp|0.01");
        final Gson gson = new Gson();
        String json = gson.toJson(loginRequest,LoginRequest.class);
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
        Request request = new Request.Builder()
                .post(body)
                .url(baseUrl+"login")
                .build();
        try{
            // Response response = client.newCall(request).execute();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, "login: ", e);
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    int code = response.code();
                    Log.i(TAG,"response.code() = " + code);
                    if (code == 200){
                        String ss = response.body().string();
                        Log.i(TAG,"response.body().string() = " + ss);
                        final LoginResponse loginResponse = gson.fromJson(ss, LoginResponse.class);
                        if (loginResponse.getLoginResult().getErrorCode() == ErrorCode.NO_ERROR){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    textView.setText("登录成功，欢迎：" + loginResponse.getStrOutputUserName());
                                }
                            });
                        }
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    int searchMax = -1;
    public void searchReader(){
        final SearchReaderRequest searchReaderRequest = new SearchReaderRequest();
        // searchReaderRequest.setStrReaderDbNames("望湖小学");
        searchReaderRequest.setStrResultSetName("<全部>");
        searchReaderRequest.setnPerMax(searchMax);
        searchReaderRequest.setStrFrom("指纹时间戳");
        searchReaderRequest.setStrLang("zh");
        searchReaderRequest.setStrMatchStyle("left");
        searchReaderRequest.setStrResultSetName("fingers");
        searchReaderRequest.setStrOutputStyle("");
        searchReaderRequest.setStrQueryWord("");

        final Gson gson = new Gson();
        String json = gson.toJson(searchReaderRequest,SearchReaderRequest.class);
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
        Request request = new Request.Builder()
                .url(baseUrl+"SearchReader")
                .post(body)
                .build();
        try{
            final ProgressDialog pd = ProgressDialog.show(this,"请稍候","正在检索读者...");

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, "searchReader: ", e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    int code = response.code();
                    if (code == 200) {
                        String result = response.body().string();
                        Log.i(TAG,"SearchReader:" + result);

                        SearchReaderResponse searchReaderResponse = gson.fromJson(result,SearchReaderResponse.class);
                        lTotal = searchReaderResponse.getSearchReaderResult().getValue();
                        pd.dismiss();
                        int errorCode = searchReaderResponse.getSearchReaderResult().getErrorCode();
                        Log.i(TAG,"errorCode = " + errorCode);
                        String ErrorInfo = searchReaderResponse.getSearchReaderResult().getErrorInfo();
                        Log.i(TAG,"ErrorInfo = " + ErrorInfo);
                        if (errorCode == ErrorCode.NO_ERROR){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    textView.setText("检索成功，共检索到：" + lTotal + " 条记录");
                                }
                            });
                        }
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG, "searchReader: error", e);
        }
    }

    long lTotal = 0;
    private long getRecordCount = 0;
    private ProgressDialog mProgressDialog;
    public void getSearchResult(){
        getRecordCount = 0;
        long lStart = 0;
        long lPerCount = Math.min(100, lTotal);

        mProgressDialog = new ProgressDialog(MainActivity.this);
        mProgressDialog.setTitle("请稍候");
        mProgressDialog.setMessage("正在获取指纹...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setMax((int) lTotal);
        mProgressDialog.show();

        while (lStart < lTotal && lPerCount > 0){
            ansyPostGetSearchResult(lStart, lPerCount);
            lStart += lPerCount;
            Log.i(TAG, "getSearchResult: " + lStart + " / " + lTotal);
        }
    }


    public void ansyPostGetSearchResult(final long lStart,long lCount){
        GetSearchResultRequest getSearchResultRequest = new GetSearchResultRequest();
        getSearchResultRequest.setStrBrowseInfoStyle("id,cols,format:cfgs/browse_fingerprint");
        getSearchResultRequest.setStrResultSetName("fingers");
        getSearchResultRequest.setStrLang("zh");
        getSearchResultRequest.setlStart(lStart);
        getSearchResultRequest.setlCount(lCount);
        final Gson gson = new Gson();
        String json = gson.toJson(getSearchResultRequest,GetSearchResultRequest.class);
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
        final Request request = new Request.Builder()
                .url(baseUrl + "GetSearchResult")
                .post(body)
                .build();
        try{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        lock.lock();
                        Response response = client.newCall(request).execute();
                        if (response.isSuccessful()){
                            String result = response.body().string();
                            GetSearchResultResponse getSearchResultResponse = gson.fromJson(result,GetSearchResultResponse.class);
                            if (getSearchResultResponse.getGetSearchResultResult().getErrorCode() != 0) {
                                Log.i(TAG, "postGetSearchResult: " + getSearchResultResponse.getGetSearchResultResult().getErrorInfo());
                                return;
                            }

                            final Record[] records = getSearchResultResponse.getSearchresults();
                            if (records == null || records.length <= 0)
                                return;

                            getRecordCount += records.length;

                            Message msg = Message.obtain();
                            msg.what = 0;
                            msg.arg1 = (int) getRecordCount;
                            // msg.arg2 = total;
                            _handler.sendMessage(msg);

                            Log.i(TAG, "postGetSearchResult: lStart / getRecordCount / lTotal = "+ lStart + " / " + getRecordCount + " / " + lTotal);
                            for (Record record : records){
                                String[] cols = record.getCols();
                                if (cols.length < 3)
                                    continue;
                                String barcode = cols[1];
                                String fingerBase64 = cols[2];
                                if (TextUtils.isEmpty(barcode) || TextUtils.isEmpty(fingerBase64))
                                    continue;
                                byte[] deRegTemp = Base64.decode(fingerBase64,Base64.NO_WRAP);
                                int ret = ZKFingerService.save(deRegTemp,barcode);
                                // Log.i(TAG,"ZKFingerService.save() return = " + ret);
                            }

                            if (getRecordCount >= lTotal)
                                _handler.sendEmptyMessage(1);
                        }
                    } catch (IOException e){
                        Log.e(TAG, "run: ", e);
                    }
                    finally {
                        lock.unlock();
                    }
                }
            }).start();
        }catch (Exception ex){
            Log.e(TAG, "postGetSearchResult: ", ex);
        }
    }

    private final static ReentrantLock lock = new ReentrantLock();

    public void postGetSearchResult(final long lStart, long lCount){
        final GetSearchResultRequest getSearchResultRequest = new GetSearchResultRequest();
        getSearchResultRequest.setStrBrowseInfoStyle("id,cols,format:cfgs/browse_fingerprint");
        getSearchResultRequest.setStrResultSetName("fingers");
        getSearchResultRequest.setStrLang("zh");
        getSearchResultRequest.setlStart(lStart);
        getSearchResultRequest.setlCount(lCount);
        final Gson gson = new Gson();
        String json = gson.toJson(getSearchResultRequest,GetSearchResultRequest.class);
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
        Request request = new Request.Builder()
                .url(baseUrl + "GetSearchResult")
                .post(body)
                .build();
        try{
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, "postGetSearchResult: ", e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String result = response.body().string();
                    // Log.i(TAG,result);

                    GetSearchResultResponse getSearchResultResponse = gson.fromJson(result,GetSearchResultResponse.class);
                    if (getSearchResultResponse.getGetSearchResultResult().getErrorCode() != 0) {
                        Log.i(TAG, "postGetSearchResult: " + getSearchResultResponse.getGetSearchResultResult().getErrorInfo());
                        return;
                    }

                    final Record[] records = getSearchResultResponse.getSearchresults();
                    if (records == null || records.length <= 0)
                        return;

                    getRecordCount += records.length;

                    Message msg = Message.obtain();
                    msg.what = 0;
                    msg.arg1 = (int) getRecordCount;
                    // msg.arg2 = total;
                    _handler.sendMessage(msg);

                    Log.i(TAG, "postGetSearchResult:  lStart / getRecordCount / lTotal = " + lStart + " / " + getRecordCount + " / " + lTotal);
                    for (Record record : records){
                        String[] cols = record.getCols();
                        if (cols.length < 3)
                            continue;
                        String barcode = cols[1];
                        String fingerBase64 = cols[2];
                        if (TextUtils.isEmpty(barcode) || TextUtils.isEmpty(fingerBase64))
                            continue;
                        byte[] deRegTemp = Base64.decode(fingerBase64, Base64.NO_WRAP);
                        int ret = ZKFingerService.save(deRegTemp, barcode);
                        // Log.i(TAG,"ZKFingerService.save() return = " + ret);
                    }

                    if (getRecordCount >= lTotal)
                        _handler.sendEmptyMessage(1);

                }
            });
        } catch (Exception e){
            Log.e(TAG, "getSearchResult: error", e);
        }
    }

//    private Handler _handler = new Handler(new Handler.Callback(){
//      @Override
//      public void handleMessage(Message msg){
//          if (msg.what == 0){
//              // progress.setText(msg.arg1 + " / " + msg.arg2);
//              mProgressDialog.show();
//              mProgressDialog.setProgress(msg.arg1);
//          } else if (msg.what == 1){
//              post(mCloseDialog);
//          }
//      };
//    };

    private Handler _handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
          if (msg.what == 0){
              // progress.setText(msg.arg1 + " / " + msg.arg2);
//              mProgressDialog.show();
              mProgressDialog.setProgress(msg.arg1);
          } else if (msg.what == 1){
              if (mProgressDialog.isShowing()){
                  mProgressDialog.dismiss();
              }
          }
            return false;
        }
    });

//    private Runnable mCloseDialog = new Runnable() {
//        @Override
//        public void run() {
//            if (mProgressDialog.isShowing()){
//                mProgressDialog.dismiss();
//            }
//        }
//    };
}
