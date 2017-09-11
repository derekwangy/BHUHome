package com.bh.uhome.bhuhome.util;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

public class PathUtil {
	/* 下载apk保存路径  */
	private static final String PATH = "/ApkDownloads/";
	
	/**下载APK路径**/
	public static String ApkDownPath = "";
	
	/**
	 * 获取apk下载保存目录
	 * **/
	public static File saveDirectory(Context context) {
		String path = Environment.getExternalStorageDirectory().toString()
				+ PATH;
		// 判断sd卡是否存在  /mnt/sdcard/ApkDownloads/
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			path = context.getFilesDir().getAbsolutePath();
			authorization(path);// 授权访问目录权限
		}
		return new File(path);
	}

	/**
	 * 更改文件权限为 777（可讀，可寫，可執行）
	 * **/
	public static void authorization(String path) {
		try {
//			System.out.println("给目录 " + path  + " 授权");
			Runtime.getRuntime().exec("chmod 777 " + path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean deleteFile(File dir) {
		if (dir.isDirectory()) {
			File[] listFiles = dir.listFiles();
			for (int i = 0; i < listFiles.length && deleteFile(listFiles[i]); i++) {
			}
		}
		if (!dir.getName().equals("IdealMSC")
				|| !dir.getName().equals(PathUtil.ApkDownPath))
			return dir.delete();
		else
			return true;
	}
	
	/**
	 * 卸载apk应用
	 * @param mContext
	 * @param apkName
	 */
	public static void unInstallApk(Context mContext,String apkName){
		//"package:com.demo.CanavaCancel"    
		Uri packageURI = Uri.parse("package:"+apkName);   
		Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);   
		mContext.startActivity(uninstallIntent);
	}
	
	/**
	* 获取apk包的信息：版本号，名称，图标等
	* @param absPath apk包的绝对路径
	* @param context 
	*/
	public static void getApkInfo(String absPath,Context context) {
			
		PackageManager pm = context.getPackageManager();
		PackageInfo pkgInfo = pm.getPackageArchiveInfo(absPath,PackageManager.GET_ACTIVITIES);
		if (pkgInfo != null) {
			ApplicationInfo appInfo = pkgInfo.applicationInfo;
			/* 必须加这两句，不然下面icon获取是default icon而不是应用包的icon */
			appInfo.sourceDir = absPath;
			appInfo.publicSourceDir = absPath;
			
			String appName = pm.getApplicationLabel(appInfo).toString();// 得到应用名
			String packageName = appInfo.packageName; // 得到包名
			String version = pkgInfo.versionName; // 得到版本信息
			/* icon1和icon2其实是一样的 */
			Drawable icon1 = pm.getApplicationIcon(appInfo);// 得到图标信息
			Drawable icon2 = appInfo.loadIcon(pm);
			String pkgInfoStr = String.format("PackageName:%s, Vesion: %s, AppName: %s", packageName, version, appName);
			Log.i("TAG", String.format("PkgInfo: %s", pkgInfoStr));
		}
	}


}
