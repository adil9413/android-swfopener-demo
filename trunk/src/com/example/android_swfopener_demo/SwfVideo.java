package com.example.android_swfopener_demo;

/**
 * Created with IntelliJ IDEA.
 * User: Ryan
 * Date: 12-12-26
 * Time: 上午10:28
 */
public class SwfVideo {
    public String title = "";
    public String localPath = "";
    public String netUrl = "";

    public SwfVideo(String title, String localPath, String netUrl) {
        this.title = title;
        this.localPath = localPath;
        this.netUrl = netUrl;
    }

    public SwfVideo(String title, String localPath) {
        this.title = title;
        this.localPath = localPath;
    }
}
