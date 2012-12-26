package com.example.android_swfopener_demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViewById(R.id.button1).setOnClickListener(clickListener);
        findViewById(R.id.button2).setOnClickListener(clickListener);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button1: {
                    onButton1Clicked();
                    break;
                }
                case R.id.button2: {
                    onButton2Clicked();
                    break;
                }
            }
        }


    };

    private void onButton1Clicked() {
        File localSwfPath = getTestLocalSwfPath();
        SwfVideo[] swfVideos = new SwfVideo[]{
                new SwfVideo("Swf Title 1", localSwfPath.getAbsolutePath()),
                new SwfVideo("Swf Title 2", "", "http://android-swfopener-demo.googlecode.com/files/The_Fancy_Pants_Aduenture.swf"),
                new SwfVideo("Swf Title 3", "", "http://www.hianzuo.com/tf/test.swf"),
        };
        onPlaySwfList(swfVideos);
    }

    private void onButton2Clicked() {
        File localSwfPath = getTestLocalSwfPath();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setClassName("com.ryan.swf.opener", "com.ryan.swf.opener.MainActivity");
        intent.setData(Uri.fromFile(localSwfPath));
        startActivity(intent);
    }

    private void onPlaySwfList(SwfVideo... swfVideos) {
        if (checkAndDownloadSwfPlayer()) {
            StringBuilder playList = new StringBuilder("");
            for (SwfVideo swf : swfVideos) {
                playList.append("t=").append(swf.title).append("|")
                        .append("lp=").append(swf.localPath).append("|")
                        .append("np=").append(swf.netUrl).append("\n");

            }
            Intent intent = new Intent();
            intent.setClassName("com.ryan.swf.opener", "com.ryan.swf.opener.MainActivity");
            intent.putExtra("play_swf_list", playList.toString());
            startActivity(intent);
        }
    }

    private boolean checkAndDownloadSwfPlayer() {
        if (-1 == contains(getPackageManager(), "com.ryan.swf.opener")) {
            new AlertDialog.Builder(this)
                    .setTitle("Alert")
                    .setMessage(Html.fromHtml("<br/>You need install SWF Opener first.<br/>"))
                    .setNeutralButton("Install SWF Opener", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Context mContext = MyActivity.this;
                            File cacheDir = new File("/sdcard/android-swfopener-demo/");
                            cacheDir.mkdirs();
                            File cachePath = new File(cacheDir, System.currentTimeMillis() + ".apk");
                            retrieveApkFromAssets(mContext, "swfopener.apk", cachePath.getAbsolutePath());
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setDataAndType(Uri.fromFile(cachePath), "application/vnd.android.package-archive");
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                    .setCancelable(true)
                    .create()
                    .show();
            return false;
        } else {
            return true;
        }
    }


    public static boolean retrieveApkFromAssets(Context context, String fileName, String path) {
        boolean bRet = false;
        try {
            InputStream is = context.getAssets().open(fileName);

            File file = new File(path);
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);

            byte[] temp = new byte[1024];
            int i = 0;
            while ((i = is.read(temp)) > 0) {
                fos.write(temp, 0, i);
            }

            fos.close();
            is.close();

            bRet = true;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return bRet;
    }

    public static int contains(PackageManager pm, String pack) {
        if (null == pm || pack.trim().length() == 0) {
            return -1;
        }
        List<PackageInfo> list = pm.getInstalledPackages(0);
        for (PackageInfo packageInfo : list) {
            if (packageInfo.packageName.equals(pack)) {
                return packageInfo.versionCode;
            }
        }
        return -1;
    }

    public File getTestLocalSwfPath() {
        File cacheDir = new File("/sdcard/android-swfopener-demo/");
        cacheDir.mkdirs();
        File cacheSwfFile = new File(cacheDir, "test.swf");
        retrieveApkFromAssets(this, "test.swf", cacheSwfFile.getAbsolutePath());
        return cacheSwfFile;
    }
}
