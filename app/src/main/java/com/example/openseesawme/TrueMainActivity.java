package com.example.openseesawme;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

public class TrueMainActivity extends AppCompatActivity {
    Toolbar myToolbar; //툴바.

    ViewPager viewPager; //viewpager
    TrueMainDoorlockIndexAdapter adapter; //
    List<TData> doorlocks; //

    String resultDoorlockIndexname; //가져온 도어락 전체 출력
    String[] doorlockIndex; //도어락인덱스
    String[] doorlockName; //도어락이름

    Button btnfp;
    ImageView lock;

    //지문 인텐트
    //String fingerComplete = "fingeryet";

    //<!--ble스캔-->---------------시작----------------------
    BluetoothAdapter mBluetoothAdapter;
    BluetoothLeScanner mBluetoothLeScanner;
    BluetoothLeAdvertiser mBluetoothLeAdvertiser;
    private static final int PERMISSIONS = 100;
    //Vector<Beacon> beacon;
    ScanSettings.Builder mScanSettings;
    List<ScanFilter> scanFilters;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.KOREAN);
    //추가
    private String mDeviceAddress = "6C:C3:74:F3:CB:E4";
    String scanComplete="scanyet";
    //    Boolean fComplete= true;
    Boolean sComplete = true;
    String scanDeviceAddress; //스캔한 비콘의 맥주소
    //

//<!--ble스캔-->--------------끝-----------------------

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //인텐트 받기(MainActivity.java로부터)
        Intent intent = getIntent();
        Boolean keeplogin = intent.getBooleanExtra("keeplog",false);
        String user_id = intent.getStringExtra("userID");
        Toast.makeText(getApplicationContext(),keeplogin + "   " + user_id, Toast.LENGTH_LONG).show();

        //user_id가 null이 아닌 경우 SharedPreferences 설정
        if(user_id != null){
            //SharedPreferences 값 설정
            SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("keeplog",keeplogin);
            editor.putString("userID",user_id);
            editor.commit();
        }

        //사용자 id 글로벌 변수 test
        //Toast.makeText(getApplicationContext(), Dglobal.getLoginID().toString(),Toast.LENGTH_LONG).show();

        // 추가된 소스, Toolbar를 생성한다.
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //추가된 소스코드, Toolbar의 왼쪽에 버튼을 추가하고 버튼의 아이콘을 바꾼다.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_key2);

        //기본 타이틀 보여줄지 말지 설정. 안보여준다.
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //여기까지 툴바

        //지문인식 intent -------------
        btnfp = findViewById(R.id.btnfp); //지문버튼
        btnfp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Fingerprint.class);
                //intent.putExtra("done",false);
                //startActivityForResult(intent,0);
                //intent.putExtra("done",true);
                startActivityForResult(intent,0);
            }
        });
        lock = findViewById(R.id.lock);
        //지문인식 intent -------------

        // viewPager -------------
        viewPager = findViewById(R.id.viewPager);
        doorlocks = new ArrayList<>();
        adapter = new TrueMainDoorlockIndexAdapter(doorlocks, this);

        doorlockListAdd(); ////for문으로 불러온 도어락 개수만큼 add
        doorlocks.add(new TData("nodata", "추가하세요", doorlocks.size(),
                "colorlast")); //도어락 등록

        viewPager.setAdapter(adapter);
        viewPager.setPadding(60, 0, 60, 0);
        //viewPager -------------

        //ble스캔-------------------------
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
        mBluetoothLeAdvertiser = mBluetoothAdapter.getBluetoothLeAdvertiser();
        //beacon = new Vector<>();
        mScanSettings = new ScanSettings.Builder();
        mScanSettings.setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY);
        ScanSettings scanSettings = mScanSettings.build();

        scanFilters = new Vector<>();
        ScanFilter.Builder scanFilter = new ScanFilter.Builder();
        scanFilter.setDeviceAddress("6C:C3:74:F3:CB:E4"); //ex) 00:00:00:00:00:00
        ScanFilter scan = scanFilter.build();
        scanFilters.add(scan);
        mBluetoothLeScanner.startScan(scanFilters, scanSettings, mScanCallback);

        //ble스캔--------------------------
    }//onCreate end


    //1. 여기부터 툴바관련. 추가된 소스, ToolBar에 menu.xml을 인플레이트함
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    //추가된 소스, ToolBar에 추가된 항목의 select 이벤트를 처리하는 함수
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(getApplicationContext(), myguestkey.class);
                startActivity(intent);
                return true;
            case R.id.action_settings2 :
                Intent intent2 = new Intent(getApplicationContext(), DoorlockList.class);
                startActivity(intent2);
                return true;
            case R.id.action_settings3 :
                Intent intent3 = new Intent(getApplicationContext(), UserMypage.class);
                startActivity(intent3);
                return true;
            case R.id.action_settings4 :
                Intent intent4 = new Intent(getApplicationContext(), Setting.class);
                startActivity(intent4);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
    //1. 여기까지 툴바관련

    //지문인텐트 받아오기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Toast.makeText(getApplicationContext(), "지문인증이 완료되었습니다", Toast.LENGTH_SHORT).show();

            //여기에서 원 이미지(원안에 자물쇠)바꾸기 ?
            lock.setImageResource(R.drawable.openlock);

            //버튼 숨기기
            btnfp.setVisibility(Button.INVISIBLE);

            //지문성공시 변수값 done으로 변경
//            fingerComplete = "done"; /*String형*/
//            Log.d("fingerComplete값",fingerComplete);


        }
    }

    //ble스캔-------------------시작----------------
    ScanCallback mScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            try {
                ScanRecord scanRecord = result.getScanRecord();
                //Log.d("getTxPowerLevel()", scanRecord.getTxPowerLevel() + "");
                Log.d("onScanResult()", result.getDevice().getAddress() + "\n" + result.getRssi() + "\n" + result.getDevice().getName()
                        + "\n" + result.getDevice().getBondState() + "\n" + result.getDevice().getType());


                final ScanResult scanResult = result;
                scanDeviceAddress = scanResult.getDevice().getAddress(); //final 지워도 되나..?
                //스레드 시작
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if(mDeviceAddress.equals(scanResult.getDevice().getAddress())) {
                                    scanComplete = "done";
                                    Log.d("scanComplete값",scanComplete);
                                    //Log.d("fingerComplete값",fingerComplete);
                                    //함수 하나 만들어서 여기에 함수 호출? 아니면 밖에서 값 저장해서 사용?
                                }else{
                                    Log.d("scan","Undetectable");
                                }
                                //-------------------

                            }
                        });
                        ///////////////////////
                    }
                }).start();
                //스레드 종료
                //*Complete 변수값들이 done이면
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
            Log.d("onBatchScanResults", results.size() + "");
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            Log.d("onScanFailed()", errorCode + "");
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBluetoothLeScanner.stopScan(mScanCallback);
    }
    //ble스캔------끝----------------------------

    //지속적인 Complete 변수 판단을 위한 스레드 추가------

    Handler shandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            beaconfinThread();
        }
    };
    @Override
    protected void onStart() {
        super.onStart();
        Thread cThread = new Thread(new Runnable() {
            public void run() {
                while (sComplete) {
                    try {
                        shandler.sendMessage(shandler.obtainMessage());
                        Thread.sleep(1000);
                    } catch (Throwable t) {
                    }
                }
            }
        });
        cThread.start();
    }

    private void beaconfinThread() {
        //비콘을 스캔하면
        if(scanComplete == "done"){
            //지문인증화면 띄워주기-------------
            try {
                ///////////////////////////////////////////////////////
                String s_id = Dglobal.getLoginID();
                String result2 = new BeaconSigActivity().execute(s_id, scanDeviceAddress).get();


                //비콘신호를 받으면 beaconSig.jsp를 부른다.
//                String result4 = new BeaconActivity().execute(scanDeviceAddress).get();

                Intent intent = new Intent(getApplicationContext(), Fingerprint.class);
                intent.putExtra("bea_id",scanDeviceAddress);
                startActivityForResult(intent,0);


                sComplete = false;

            }catch (Exception e){}
            //----------------------
        }else{ }
    }

    //도어락리스트 불러와서 배열에 저장-------------------------
    public void doorlockListAdd() {
        try {
            resultDoorlockIndexname = new TrueMainDoorlockIndexActivity().execute(Dglobal.getLoginID()).get();//
            Log.d("값", "resultDoorlockIndexname :" + resultDoorlockIndexname);

            try {//가져와서, split, 배열에 넣기
                String row[] = resultDoorlockIndexname.split("spl");
                int rowLen = row.length;
                doorlockIndex = new String[rowLen];
                doorlockName = new String[rowLen];

                for (int j = 0; j < row.length; j++) {
                    String row2[] = row[j].split(",");
                    doorlockIndex[j] = row2[0];
                    doorlockName[j] = row2[1];
                }

                for (int i = 0; i < doorlockIndex.length; i++) {
                    // 각 List의 값들을 TData 객체에 set 해줍니다.
                    doorlocks.add(new TData(doorlockIndex[i], doorlockName[i], i,
                            String.valueOf(i%3)));

//                    Log.d("값", "doorlockIndex :" + doorlockIndex[i] + "\n" +
//                            "doorlockName :" + doorlockName[i] + "\n" +
//                            "cardIndex :" + i + "\n" +
//                            "bgcolor :" + doorlocks.get(i).getcBgcolor() + "\n");
                }
            } catch (NullPointerException e){
                btnfp.setVisibility(Button.INVISIBLE);
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}