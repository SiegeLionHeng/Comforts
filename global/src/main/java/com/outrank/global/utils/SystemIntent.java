package com.outrank.global.utils;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.provider.Settings;


import com.outrank.global.global.AppGlobal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Gabriel on 2019/12/12.
 * Email 17600284843@163.com
 * Description: 设置系统各大厂商权限
 */
public class SystemIntent {
    public static enum MobileType {
        HUAWEI("huawei"), XIAOMI("xiaomi"), SANXING("samsung"), MEIZU("meizu"), LESHI(
                "letv"), OTHER("other"), LENOVO("lenovo"), OPPO("oppo");
        private String brand;

        MobileType(String brand) {
            this.brand = brand;
        }

        public static MobileType create(String brand) {
            if (brand.startsWith(HUAWEI.brand)) {
                return HUAWEI;
            } else if (brand.startsWith(XIAOMI.brand)) {
                return XIAOMI;
            } else if (brand.startsWith(SANXING.brand)) {
                return SANXING;
            } else if (brand.startsWith(MEIZU.brand)) {
                return MEIZU;
            } else if (brand.startsWith(LESHI.brand)) {
                return LESHI;
            } else if (brand.startsWith(LENOVO.brand)) {
                return LENOVO;
            } else if (brand.startsWith(OPPO.brand)) {
                return OPPO;
            } else {
                return OTHER;
            }
        }

        public String getBrand() {
            return brand;
        }

    }

    /**
     * 获取手机型号
     */
    public static MobileType getMobileType() {
        String brand = Build.BRAND;
        return MobileType.create(brand.toLowerCase());
    }

    /**
     * 获取【华为】系统权限管理页面
     */
    private static Intent getHuaweiPerMissionManagerIntent() {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName comp = new ComponentName("com.huawei.systemmanager",
                "com.huawei.permissionmanager.ui.MainActivity");// 华为权限管理
        intent.setComponent(comp);
        return intent;
    }

    /**
     * 获取【小米】系统权限管理页面
     */
    private static Intent getXiaoMiPerMissionManagerIntent() {
        Intent intent = new Intent();
        intent.setAction("miui.intent.action.APP_PERM_EDITOR");
        intent.setClassName("com.miui.securitycenter",
                "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        intent.putExtra("extra_pkgname",
                AppGlobal.getInstance().getPackageName());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    /**
     * 获取【魅族】系统权限管理页面
     */
    private static Intent getMeiZuPerMissionManagerIntent() {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("packageName", AppGlobal.getInstance().getPackageName());
        return intent;
    }

    /**
     * 获取【乐视】系统权限管理页面
     */
    private static Intent getLeShiPerMissionManagerIntent() {
        boolean hasLetvsafe = false;
        try {
            ApplicationInfo info = AppGlobal.getInstance().getPackageManager()
                    .getApplicationInfo("com.letv.android.letvsafe",
                            PackageManager.GET_UNINSTALLED_PACKAGES);
            hasLetvsafe = info != null;
        } catch (NameNotFoundException e) {
            hasLetvsafe = false;
        }
        if (hasLetvsafe) {
            String ACTION_PERMISSION_AUTOBOOT = "com.letv.android.permissionandapps";
            Intent intent = new Intent(ACTION_PERMISSION_AUTOBOOT);
            return intent;
            // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
            // Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        return null;
    }

    /**
     * 适配【乐视】手机自启动页面
     */
    private static Intent getLeShiAutoBootIntent() {
        boolean hasLetvsafe = false;
        try {
            ApplicationInfo info = AppGlobal.getInstance().getPackageManager()
                    .getApplicationInfo("com.letv.android.letvsafe",
                            PackageManager.GET_UNINSTALLED_PACKAGES);
            hasLetvsafe = info != null;
        } catch (NameNotFoundException e) {
            hasLetvsafe = false;
        }
        if (hasLetvsafe) {
            String ACTION_PERMISSION_AUTOBOOT = "com.letv.android.permissionautoboot";
            Intent intent = new Intent(ACTION_PERMISSION_AUTOBOOT);
            return intent;
        }
        return getSettingsIntent();
    }

    public static String getSystemProperty(String propName) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(
                    new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                }
            }
        }
        return line;
    }

    /**
     * 适配【小米】手机自启动页面
     */
    private static Intent getXiaomiAutoBootIntent() {
        String miuiVersion = getSystemProperty("ro.miui.ui.version.name");
        if (miuiVersion.compareTo("V5") > 0) {// MIUI版本大于V5就需要走新的流程
            try {
                Intent localIntent = new Intent();
                localIntent
                        .setComponent(new ComponentName(
                                "com.miui.securitycenter",
                                "com.miui.permcenter.autostart.AutoStartManagementActivity"));
                return localIntent;
            } catch (Exception localException) {
            }
        } else {
            PackageManager pm = AppGlobal.getInstance().getPackageManager();
            PackageInfo info = null;
            try {
                info = pm.getPackageInfo(
                        AppGlobal.getInstance().getPackageName(), 0);
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            intent.setClassName("com.android.settings",
                    "com.miui.securitycenter.permission.AppPermissionsEditor");
            intent.putExtra("extra_package_uid", info.applicationInfo.uid);
            return intent;
        }
        return getSettingsIntent();
    }

    /**
     * 适配【华为】手机自启动页面
     */
    private static void getHuaweiAutoBootIntent() {
        try {
            // 华为大坑，不能直接用Intent来启动，会启不起来
            String cmd = "am start -n com.huawei.systemmanager/.optimize.process.ProtectActivity";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                cmd += " --user " + getUserSerial();
            }
            Runtime.getRuntime().exec(cmd);
        } catch (IOException ex) {
        }
    }

    private static String getUserSerial() {
        // noinspection ResourceType
        @SuppressLint("WrongConstant") Object userManager = AppGlobal.getInstance().getSystemService("user");
        if (userManager == null) {
            return "";
        }

        try {
            Method myUserHandleMethod = android.os.Process.class.getMethod(
                    "myUserHandle", (Class<?>[]) null);
            Object myUserHandle = myUserHandleMethod.invoke(
                    android.os.Process.class, (Object[]) null);
            if (myUserHandle != null) {
                Method getSerialNumberForUser = userManager.getClass().getMethod(
                        "getSerialNumberForUser", myUserHandle.getClass());
                long userSerial = (Long) getSerialNumberForUser.invoke(userManager, myUserHandle);
                return String.valueOf(userSerial);
            }
        } catch (NoSuchMethodException | IllegalArgumentException | IllegalAccessException | InvocationTargetException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    /**
     * 获取【设置】系统权限管理页面
     */
    private static Intent getSettingsIntent() {
        return new Intent(Settings.ACTION_SETTINGS);
    }

    /**
     * 启动系统白名单管理页面
     */
    public static Intent getAutoBootManagerIntent() {
        Intent intent = null;
        MobileType mobileType = getMobileType();
        switch (mobileType) {
            case HUAWEI:
                getHuaweiAutoBootIntent();
                break;
            case XIAOMI:
                intent = getXiaomiAutoBootIntent();
                break;
            case MEIZU:
                break;
            case LESHI:
                intent = getLeShiAutoBootIntent();
                break;
            default:
                intent = getSettingsIntent();
                break;
        }
        return intent;
    }

    /**
     * 启动系统权限管理页面
     */
    public static Intent getPermissionManagerIntent() {
        Intent intent = null;
        MobileType mobileType = getMobileType();
        switch (mobileType) {
            case HUAWEI:
                intent = getHuaweiPerMissionManagerIntent();
                break;
            case XIAOMI:
                intent = getXiaoMiPerMissionManagerIntent();
                break;
            case MEIZU:
                intent = getMeiZuPerMissionManagerIntent();
                break;
            case LESHI:
                intent = getLeShiPerMissionManagerIntent();
                break;
            default:
                intent = getSettingsIntent();
                break;
        }
        return intent;
    }
}
