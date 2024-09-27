package vn.md18.fsquareapplication.utils;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

public final class ScreenUtils {

    private ScreenUtils() {
        // This class is not publicly instantiable
    }

    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * Set Window status and navigation bar color and visibility
     * @param window
     * @param statusbarColor
     * @param navbarColor
     */
    public static void setWindowStatusNav(android.view.Window window, int statusbarColor, int navbarColor) {

        int flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT || Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT_WATCH) {
            window.getAttributes().flags |= flags;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int uiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            window.getDecorView().setSystemUiVisibility(uiVisibility);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.getAttributes().flags &= ~flags;

            window.setStatusBarColor(statusbarColor);
            window.setNavigationBarColor(navbarColor);
        }
    }

    /**
     * Set Window status bar color and visibility
     * @param window
     * @param statusbarColor
     */
    public static void setWindowStatus(android.view.Window window, int statusbarColor) {

        int flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT || Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT_WATCH) {
            window.getAttributes().flags |= flags;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int uiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            window.getDecorView().setSystemUiVisibility(uiVisibility);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.getAttributes().flags &= ~flags;

            window.setStatusBarColor(statusbarColor);
        }
    }
}
