package com.outrank.global.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.outrank.global.R;
import com.outrank.global.global.AppGlobal;
import com.outrank.global.interfaces.FilePath;
import com.outrank.global.global.SpKey;
import com.outrank.global.net.ApiStrategy;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Gabriel on 2019/12/12.
 * Email 17600284843@163.com
 * Description: 工具方法
 */
public class Tools {
    /**
     * 获取到资源文件中的字符串
     *
     * @param stringId
     * @return
     */
    public static String gString(Context context, @StringRes int stringId) {
        return context.getApplicationContext().getResources().getString(stringId);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(float pxValue) {
        final float scale = AppGlobal.getInstance().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int readDimenPx(int dimensId) {
        Resources resources = AppGlobal.getInstance().getResources();
        float dpValue = resources.getDimensionPixelSize(dimensId);
        return (int) dpValue;
    }


    /**
     * 是否可以设置浸入式
     *
     * @return
     */
    public static boolean isResetStatusBar() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && SpUtils.getInstance().get(SpKey.IS_SUPPORT_TRANSLUCENT, false);
    }

    /**
     * 获取手机状态栏高度
     */
    public static int getStatusBarHeight() {
        int result = 0;
        Resources resources = AppGlobal.getInstance().getResources();
        int resourceId = resources.getIdentifier("status_bar_height",
                "dimen", "android");
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 改变通知栏字体颜色，可以改变则改变并返回true，反之返回false
     */
    public static boolean changeSystemBarTextColor(Activity activity) {
        // 如果要改变系统栏的文字为黑色，则进行如下设置
        boolean isSupportTranslucentMobileType = SpUtils.getInstance().get(SpKey.IS_SUPPORT_TRANSLUCENT, false);
        if (isSupportTranslucentMobileType) {
            SystemIntent.MobileType mobileType = SystemIntent.getMobileType();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {// Android6.0支持沉浸式
                activity.getWindow().getDecorView().setSystemUiVisibility(
                        0x00000400 | 0x00002000);
            } else if (SystemIntent.MobileType.XIAOMI.equals(mobileType)) {// 设置小米MIUI6以上支持沉浸式
                String miuiVersion = SystemIntent
                        .getSystemProperty("ro.miui.ui.version.name");
                boolean supportMiui = miuiVersion.compareTo("V6") > 0;
                if (supportMiui) {// 支持的miui
                    MIUISetStatusBarLightMode(activity.getWindow(), true);
                } else {// 不支持的MIUI则直接跳出循环
                    return false;
                }
            } else if (SystemIntent.MobileType.MEIZU.equals(mobileType)) {// 设置魅族手机支持沉浸式，由于flyme4.0手机很少，因此不专门对这些手机做适配
                FlymeSetStatusBarLightMode(activity.getWindow(), true);
            } else if (SystemIntent.MobileType.LESHI.equals(mobileType)) {// 乐视手机支持沉浸式
            } else {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    private static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class
                        .forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams
                        .getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags",
                        int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);// 状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);// 清除黑色字体
                }
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格，Flyme4.0以上 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    private static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * 获取屏幕的宽度
     *
     * @return
     */
    public static int getScreenWidth() {
        WindowManager manager = (WindowManager) AppGlobal.getInstance()
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }

    /**
     * 配置附件缩略图的尺寸大小
     * 该值一般应根据屏幕尺寸来确定，默认值为 Screen.width / 2
     *
     * @return
     */
    public static int getImageMaxEdge() {
        return (int) (165.0 / 320.0 * Tools.getScreenWidth());
    }


    /**
     * 获取屏幕的高度
     *
     * @return
     */
    public static int getScreenHeight() {
        WindowManager manager = (WindowManager) AppGlobal.getInstance()
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getHeight();
    }


    /**
     * 格式化大数字
     *
     * @return
     */
    public static String formatLongNumber(String number) {
        DecimalFormat decimalFormat = new DecimalFormat("0.000");
        String result;
        Float floatNumber = Float.valueOf(number);
        if (floatNumber < 100000) {//小于10万
            result = decimalFormat.format(floatNumber);
        } else {
            if (number.contains(".")) {
                number = number.substring(0, number.indexOf("."));
            }
            long rewardLong = Long.valueOf(number);
            if (floatNumber < 100000000) {
                result = decimalFormat.format(rewardLong / 10000f) + "万";
            } else {
                result = decimalFormat.format(rewardLong / 100000000f) + "亿";
            }
        }
        return result;
    }


    /**
     * 将view的
     *
     * @param view
     * @param pictureName
     */
    public static String viewSaveToImage(View view, String pictureName) {
        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        view.setDrawingCacheBackgroundColor(Color.WHITE);
        // 把一个View转换成图片
        Bitmap bitmap = loadBitmapFromView(view);

        String filePath = saveToGallery(view.getContext(), pictureName, bitmap);
        view.destroyDrawingCache();
        return filePath;
    }


    public static String saveToGallery(Context context, String fileName, Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return saveImageWithAndroidQ(context, fileName, bitmap);
        } else {
            return saveImage(context, fileName, bitmap);
        }
    }

    private static String saveImage(Context context, String fileName, Bitmap bitmap) {
        //Paperless  自定义的文件夹名称
        File appDir = new File(FilePath.ROOT_PATH);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        //文件的名称设置为 系统时间.jpg
        File file = new File(appDir, fileName);
        FileOutputStream fos = null;
        try {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int options = 80;
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            while (baos.toByteArray().length / 1024 > 100) {
                baos.reset();
                options -= 10;
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            }
            fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        // 其次把文件插入到系统图库
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        Uri uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
        return file.getPath();
    }

    private static String saveImageWithAndroidQ(Context context, String fileName, Bitmap bitmap) {
        //Paperless  自定义的文件夹名称
        File pictureDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if (!pictureDir.exists()) {
            pictureDir.mkdir();
        }
        //文件的名称设置为 系统时间.jpg
        File file = new File(pictureDir, fileName);
        FileOutputStream fos = null;
        try {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int options = 80;
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            while (baos.toByteArray().length / 1024 > 100) {
                baos.reset();
                options -= 10;
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            }
            fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 其次把文件插入到系统图库
        ContentValues values = new ContentValues();
//        values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
        values.put(MediaStore.Images.Media.DESCRIPTION, "This is an image");
        values.put(MediaStore.Images.Media.DISPLAY_NAME, file.getName());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        values.put(MediaStore.Images.Media.TITLE, "Image.png");
        values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);

        Uri insertUri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//        // 最后通知图库更新
//        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));

        BufferedInputStream inputStream = null;
        OutputStream os = null;
        boolean result = false;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(file));
            if (insertUri != null) {
                os = context.getContentResolver().openOutputStream(insertUri);
            }
            if (os != null) {
                byte[] buffer = new byte[1024 * 4];
                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    os.write(buffer, 0, len);
                }
                os.flush();
            }
            result = true;
        } catch (IOException e) {
            result = false;
        } finally {
            try {
                inputStream.close();
                os.flush();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return file.getPath();
    }

    public static Bitmap loadBitmapFromView(View v) {
        int w = v.getWidth();
        int h = v.getHeight();
        Log.e("Tools", w + "/" + h);

        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);

        c.drawColor(Color.WHITE);
        /** 如果不设置canvas画布为白色，则生成透明 */

        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        v.draw(c);

        return bmp;
    }

    /**
     * 获取已安装的app列表
     */
    public static String getInstalledApp() {
        JSONArray array = new JSONArray();
        try {
            // 获取已经安装的所有应用, PackageInfo　系统类，包含应用信息
            PackageManager packageManager = AppGlobal.getInstance().getPackageManager();
            List<PackageInfo> packages = packageManager.getInstalledPackages(0);
            int size = packages.size();
            for (int i = 0; i < size; i++) {
                PackageInfo packageInfo = packages.get(i);
                boolean isSystemApp = (packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
                if (isSystemApp) {
                    array.add(packageInfo.packageName);
                }
            }
        } catch (Exception e) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("errorMsg", e.getMessage());
            array.add(jsonObject);
        }
        return array.toJSONString();
    }

    /**
     * 程序是否在前台运行
     */
    public static boolean isAppOnForeground() {
        boolean isAppOnForeground = false;
        ActivityManager activityManager = (ActivityManager) AppGlobal.getInstance()
                .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = AppGlobal.getInstance().getPackageName();
        //获取Android设备中所有正在运行的App
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses != null) {
            for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                if (appProcess.processName.equals(packageName) && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    isAppOnForeground = true;
                    break;
                }
            }
            appProcesses.clear();
        }
        return isAppOnForeground;
    }

//    /**
//     * 用户手机上安装了真实的系统app，认定不是模拟器
//     *
//     * @return
//     */
//    public static boolean hasRealSystemApp() {
//        String path = Tools.copyAssectsToData("http_pom");//复制到data目录
//        String appList = Tools.readStringFromFile(path);
//        List<String> realApps = JSON.parseArray(AES.decode(appList), String.class);
//        String systemApp = getInstalledApp();
//        if (realApps != null) {
//            for (String realApp : realApps) {
//                if (systemApp.contains(realApp)) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }

    public static String readStringFromFile(String filePath) {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            in = new FileInputStream(filePath);//文件名
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }

    /**
     * 复制assects目录下的文件到app data目录里
     *
     * @param assetsName assects文件名
     * @return data目录下的文件路径
     */
    public static String copyAssectsToData(String assetsName) {
        InputStream in = null;
        FileOutputStream out = null;
        String path = AppGlobal.getInstance().getApplicationContext().getFilesDir()
                .getAbsolutePath() + "/" + assetsName; // data/data目录
        File file = new File(path);
        if (!file.exists()) {
            try {
                in = AppGlobal.getInstance().getAssets().open(assetsName); // 从assets目录下复制
                out = new FileOutputStream(file);
                int length = -1;
                byte[] buf = new byte[1024];
                while ((length = in.read(buf)) != -1) {
                    out.write(buf, 0, length);
                }
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
        }
        return path;
    }


    /**
     * 判断是否是正确的资金密码
     *
     * @param pwd
     * @return
     */
    public static boolean isLegalPwd(String pwd) {
        if (pwd == null) {
            return false;
        }
//        String regEx1 = "^(?![0-9]+$)(?![a-z]+$)(?![A-Z]+$)[0-9A-Za-z]{8,16}$";
        String regEx1 = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(pwd);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取到图片的地址 设置到文件中
     *
     * @param imageName
     * @return
     */
    public static String getImageUriPath(String imageName) {
        return Environment.getExternalStorageDirectory() + "/images/" + imageName + ".jpg";
    }


    public static String subZeroAndDot(String s) {
        if (TextUtils.isEmpty(s)) {
            return "0";
        }

        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    @SuppressLint("NewApi")
    public static boolean isNotificationEnabled(Context context) {
        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        Class appOpsClass = null;
        /* Context.APP_OPS_MANAGER */
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod("checkOpNoThrow", Integer.TYPE, Integer.TYPE,
                    String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField("OP_POST_NOTIFICATION");

            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static boolean isNotificationEnableds(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //8.0手机以上
            if (((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).getImportance() == NotificationManager.IMPORTANCE_NONE) {
                return false;
            }
        }

        String CHECK_OP_NO_THROW = "checkOpNoThrow";
        String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        Class appOpsClass = null;

        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
                    String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static int currentDraw(int position) {
        if (position == 0) {
            return R.drawable.ic_square;
        } else if (position == 1) {
            return R.drawable.ic_dynamic;
        } else if (position == 2) {
            return R.drawable.ic_write;
        } else if (position == 3) {
            return R.drawable.ic_message;
        } else {
            return R.drawable.ic_mine;
        }
    }

    /**
     * 获取自定义字体
     *
     * @param keyPath
     * @return
     */
    public static Typeface fonts(String keyPath) {
        return Typeface.createFromAsset(AppGlobal.getInstance().getAssets(), keyPath);
    }

    /**
     * 获取随机图片地址
     *
     * @return
     */
    public static List<String> getImgUrls() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Random random = new Random();
            int anInt = random.nextInt(10);
            String[] array = Res.array(R.array.img_plaza_array);
            String imgUrl = array[anInt];
            list.add(ApiStrategy.baseUrl.concat(imgUrl));
        }
        return list;
    }
}
