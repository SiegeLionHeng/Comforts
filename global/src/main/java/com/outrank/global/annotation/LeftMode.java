package com.outrank.global.annotation;

import androidx.annotation.IntDef;


import com.outrank.global.view.Topbar;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({Topbar.TOPBAR_LEFT_MODE_BACK, Topbar.TOPBAR_LEFT_MODE_MORE})
@Retention(RetentionPolicy.SOURCE)
public @interface LeftMode {

}