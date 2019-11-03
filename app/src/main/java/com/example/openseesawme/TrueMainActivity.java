package com.example.openseesawme;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
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

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TrueMainActivity extends AppCompatActivity implements BootstrapNotifier, BeaconConsumer {
    Toolbar myToolbar; //툴바.

    ViewPager viewPager; //viewpager
    TrueMainDoorlockIndexAdapter adapter; //
    List<TData> doorlocks; //

    String resultDoorlockIndexname; //가져온 도어락 전체 출력
    String[] doorlockIndex; //도어락인덱스
    String[] doorlockName; //도어락이름

    Button btnfp;
    ImageView lock;

    public static Boolean registernof = true;

    //ble스캔--------------------------------
    private static final int PERMISSIONS = 100;
    //추가
    private String mDeviceAddress = "6C:C3:74:F3:CB:E4";
    Boolean scanComplete=false; //스캔 완료 확인
    String scanDeviceAddress; //스캔한 비콘의 맥주소
    Boolean fingerComplete=false; //지문 완료 확인
    //

//ble스캔--------------------------------

    //foreground +scan --------------------
    private static final String TAG = "TrueMainActivity";
    private RegionBootstrap regionBootstrap;
    private BackgroundPowerSaver backgroundPowerSaver;
    private boolean haveDetectedBeaconsSinceBoot = false;
    private String cumulativeLog = "";

    private BeaconManager beaconManager;
    //foreground +scan --------------------

    //사용자가 등록한 도어락의 비콘s---
    String resultBeaconIds;
    String[] beaconId;
///---
    //앱 종료시
    private long pressedTime;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //foreground----------------------
        beaconManager = BeaconManager.getInstanceForApplication(this);
        //beaconManager = org.altbeacon.beacon.BeaconManager.getInstanceForApplication(this);

        // By default the AndroidBeaconLibrary will only find AltBeacons.  If you wish to make it
        // find a different type of beacon, you must specify the byte layout for that beacon's
        // advertisement with a line like below.  The example shows how to find a beacon with the
        // same byte layout as AltBeacon but with a beaconTypeCode of 0xaabb.  To find the proper
        // layout expression for other beacon types, do a web search for "setBeaconLayout"
        // including the quotes.
        //
        beaconManager.getBeaconParsers().clear();
//        beaconManager.getBeaconParsers().add(new BeaconParser().
//                setBeaconLayout("m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25")); //
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
        beaconManager.setDebug(true);

        // Uncomment the code below to use a foreground service to scan for beacons. This unlocks
        // the ability to continually scan for long periods of time in the background on Andorid 8+
        // in exchange for showing an icon at the top of the screen and a always-on notification to
        // communicate to users that your app is using resources in the background.
        //
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher_new);
        builder.setContentTitle("Open Seesawme - Scanning for Beacons");
        Intent fintent = new Intent(this, TrueMainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, fintent, PendingIntent.FLAG_UPDATE_CURRENT
        );
        builder.setContentIntent(pendingIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("My Notification Channel ID",
                    "My Notification Name", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("My Notification Channel Description");
            NotificationManager notificationManager = (NotificationManager) getSystemService(
                    Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
            builder.setChannelId(channel.getId());
        }
        try {
            beaconManager.enableForegroundServiceScanning(builder.build(), 456);

            beaconManager.setEnableScheduledScanJobs(false);
            beaconManager.setBackgroundScanPeriod(1100); //1100->1.1s
            //beaconManager.setBackgroundBetweenScanPeriod(10000l); //100001->10000ms, 10000 ms = 10.0 secs
            //beaconManager.setForegroundBetweenScanPeriod(10000l);

            beaconManager.setBackgroundScanPeriod(1100l);
            beaconManager.setForegroundScanPeriod(1100l);

        } catch (Exception ee){ }

        // For the above foreground scanning service to be useful, you need to disable
        // JobScheduler-based scans (used on Android 8+) and set a fast background scan
        // cycle that would otherwise be disallowed by the operating system.
        //
        Log.d(TAG, "setting up background monitoring for beacons and power saving");
        // wake up the app when a beacon is seen
        Region region = new Region("backgroundRegion",
                null, null, null);
        regionBootstrap = new RegionBootstrap(this, region);

        // simply constructing this class and holding a reference to it in your custom Application
        // class will automatically cause the BeaconLibrary to save battery whenever the application
        // is not visible.  This reduces bluetooth power usage by about 60%
        backgroundPowerSaver = new BackgroundPowerSaver(this);

        // If you wish to test beacon detection in the Android Emulator, you can use code like this:
        // BeaconManager.setBeaconSimulator(new TimedBeaconSimulator() );
        // ((TimedBeaconSimulator) BeaconManager.getBeaconSimulator()).createTimedSimulatedBeacons();
        //----------------------


        //인텐트 받기(MainActivity.java로부터)
        Intent intent = getIntent();
        Boolean keeplogin = intent.getBooleanExtra("keeplog",false);
        String user_id = intent.getStringExtra("userID");
        //Toast.makeText(getApplicationContext(),keeplogin + "   " + user_id, Toast.LENGTH_LONG).show();

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

        //beaconidlist(); //도어락의 beacon_info 가져오기

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

        //ble스캔 권한-------------------------
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS);
        //ble스캔 권한--------------------------

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
                finish();
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
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Toast.makeText(getApplicationContext(), "지문인증이 완료되었습니다", Toast.LENGTH_SHORT).show();

            //여기에서 원 이미지(원안에 자물쇠)바꾸기 ?
            //lock.setImageResource(R.drawable.openlock);
            //Caused by: java.lang.NullPointerException: Attempt to invoke virtual method 'void android.widget.ImageView.setImageResource(int)' on a null object reference
            //        at com.example.openseesawme.TrueMainActivity.onActivityResult(TrueMainActivity.java:320)
            //setImageResource 대신 이걸로 try
//            Picasso.with(getApplicationContext())
//                    .load("http://128.134.114.250:8080/doorlock/userImages/"+imageName) //imageName : 파일 이름
//                    .placeholder(R.drawable.person1)//이미지가 존재하지 않을 경우   경우 대체 이미지
//                    /*.resize(2000, 2000) // 이미지 크기를 재조정하고 싶을 경우*/
//                    .into(lock);

            //버튼 숨기기
            btnfp.setVisibility(Button.INVISIBLE);

            //지문성공시
            //try {
                //jsp로 보내는 코드------------------
                //String result2 = new FingerActivity().execute().get();
                fingerComplete = true;
            //}catch (Exception e){ }
        }
    }

    private void beaconfinThread() {//비콘을 스캔하면
            try {

                ///////////////////////////////////////////////////////
                String s_id = Dglobal.getLoginID();
                String result2 = new BeaconSigActivity().execute(s_id, scanDeviceAddress).get();
                Log.d("값", "scanDeviceAddress :" + scanDeviceAddress);


                if(result2.equals("true")) {
                    Toast.makeText(getApplicationContext(), "비콘이 스캔되었습니다.", Toast.LENGTH_SHORT).show();
                    if (fingerComplete == false) { //지문->비콘 을 위해서 추가, 지문 안했으면
                        Intent intent = new Intent(getApplicationContext(), Fingerprint.class); //지문인증화면
                        //intent.putExtra("bea_id", scanDeviceAddress);
                        startActivityForResult(intent, 0);
                    }
                }

            }catch (Exception e){}
            //----------------------
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

    //foreground-----------------
    public void disableMonitoring() {
        if (regionBootstrap != null) {
            regionBootstrap.disable();
            regionBootstrap = null;
        }
    }
    public void enableMonitoring() {
        Region region = new Region("backgroundRegion",
                null, null, null);
        regionBootstrap = new RegionBootstrap(this, region);
    }


    @Override
    public void didEnterRegion(Region arg0) {
        // In this example, this class sends a notification to the user whenever a Beacon
        // matching a Region (defined above) are first seen.
        Log.d(TAG, "did enter region.");
        if (!haveDetectedBeaconsSinceBoot) {
            Log.d(TAG, "auto launching MainActivity");

            // The very first time since boot that we detect an beacon, we launch the MainActivity
            //Intent intent = new Intent(this, MainActivity.class);
            // Important:  make sure to add android:launchMode="singleInstance" in the manifest
            // to keep multiple copies of this activity from getting created if the user has
            // already manually launched the app.
            //this.startActivity(intent);
            haveDetectedBeaconsSinceBoot = true;
        } else { }
    }

    @Override
    public void didExitRegion(Region region) {
        logToDisplay("I no longer see a beacon.");
    }

    @Override
    public void didDetermineStateForRegion(int state, Region region) {
        logToDisplay("Current region state is: " + (state == 1 ? "INSIDE" : "OUTSIDE ("+state+")"));
    }
    private void logToDisplay(String line) {
        cumulativeLog += (line + "\n");
        Log.d(TAG,line);
    }
    public String getLog() { return cumulativeLog; }

    //-
    @Override
    protected void onPause() {
        super.onPause();
        beaconManager.unbind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        beaconManager.bind(this);
    }

    @Override
    public void onBeaconServiceConnect() {

        RangeNotifier rangeNotifier = new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                if(getRegisternof()) { //도어락 등록 때 지문인증 난입 방지
                    if (beacons.size() > 0) {
                        Log.d(TAG, "didRangeBeaconsInRegion called with beacon count:  " + beacons.size());
                        Beacon firstBeacon = beacons.iterator().next();
                        logToDisplay("The first beacon " + firstBeacon.toString() + " is about " + firstBeacon.getDistance() + " meters away.");//d
                        //Log.d("발견된 비콘/addr", firstBeacon.getBluetoothAddress()); //d

                        Log.d("발견된 비콘/addr", firstBeacon.getBluetoothAddress()); //d
                        Log.d("발견된 비콘/distance", String.valueOf(firstBeacon.getDistance())); //d

                        //for (int i = 0; i < beaconId.length; i++) {
                            if (firstBeacon.getDistance()<16) { //(firstBeacon.getDistance()<7) {
                            //if (beaconId[i].equals(firstBeacon.getBluetoothAddress()) ) {//도어락의 비콘address = 발견된 도어락의 address 이면
                                Log.d("발견된 비콘/addr", firstBeacon.getBluetoothAddress()); //d
                                Log.d("발견된 비콘/distance--------", String.valueOf(firstBeacon.getDistance())); //d
                                scanDeviceAddress = firstBeacon.getBluetoothAddress();

                                if (scanComplete == false) { //스캔이 처음이면
                                        scanComplete = true;
                                        Log.d(TAG, "scanComplete값값  " + scanComplete);

                                    if (fingerComplete == false) { //지문인증 안했으면 + 중복 지문 인증 방지
                                        Intent intent = new Intent(getApplicationContext(), TrueMainActivity.class );
                                        startActivity(intent);// 백그라운드에서 실행 시, 지문 인증 이후에 main출력
                                    }

                                    beaconfinThread(); //beaconSig.jsp를 부른다. (+ 지문인증화면 띄워주기)
                                    //Toast.makeText(getApplicationContext(), "도어락 잠금이 해제되었습니다.", Toast.LENGTH_SHORT).show();
                                } else { }
                            } else {
                                Log.d("scan", "Undetectable");
                            }
                       // }
                        //-------------------
                    }
                }//
            }
        };
        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
            beaconManager.addRangeNotifier(rangeNotifier);
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
            beaconManager.addRangeNotifier(rangeNotifier);
        } catch (RemoteException e) {   }
    }

    //foreground-------------------------
//비콘 리스트-------------
    public void beaconidlist(){ //*-*
        try {
            resultBeaconIds = new TrueMainBeaconidActivity().execute(Dglobal.getLoginID()).get();//
            Log.d("값b", "resultBeaconIds :" + resultBeaconIds);

            try {//가져와서, split, 배열에 넣기
                beaconId = resultBeaconIds.split(",");
            } catch (NullPointerException e){
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//----

    public static Boolean getRegisternof() { return registernof; }

    public static void setRegisternof(Boolean registernof) { TrueMainActivity.registernof = registernof; }

//    @Override
//    public void onBackPressed(){
//        //super.onBackPressed();
//        if(pressedTime ==0 ){
//            Toast.makeText(TrueMainActivity.this,"한번 더 누르면 종료합니다. ", Toast.LENGTH_LONG).show();
//            pressedTime = System.currentTimeMillis();
//        }else{
//            int seconds = (int)(System.currentTimeMillis() - pressedTime);
//            if(seconds > 2000){
//                pressedTime = 0;
//            }else{
//                finish();
//                setRegisternof(true);
//
//            }
//        }
//    }


}