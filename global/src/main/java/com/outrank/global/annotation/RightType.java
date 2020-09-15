package com.outrank.global.annotation;

import androidx.annotation.IntDef;


import com.outrank.global.view.Topbar;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@IntDef({Topbar.TOPBAR_TYPE_NONE, Topbar.TOPBAR_TYPE_IMG, Topbar.TOPBAR_TYPE_TEXT, Topbar.TOPBAR_TYPE_IMG_TEXT})
@Retention(RetentionPolicy.SOURCE)
public @interface RightType {

}

