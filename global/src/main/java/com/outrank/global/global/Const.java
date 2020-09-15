package com.outrank.global.global;

/**
 * Created by Gabriel on 2019/12/6.
 * Email 17600284843@163.com
 * Description: 常量存储类
 */
public class Const {

    /*-------------------------判断常量-------------------------*/

    public static final String CLOSE_SMART_REFRESH = "close_smart_refresh";

    public static final String KEY_IMG_URL_ATLAS = "key_img_url_atlas";
    public static final String KEY_WALL_PAPER = "key_wall_paper";

    /**
     * 选择图片弹窗
     */
    public static class AtlasSelect {
        public static final String ATLAS_SELECT_BACKGROUND = "atlas_select_background";  //头像横向比例
        public static final String ATLAS_SELECT_HEADER = "atlas_select_header";  //头像横向比例
        public static final String ATLAS_SELECT_CANCEL = "atlas_select_cancel";  //头像横向比例
    }


    /*-------------------------颜色常量-------------------------*/

    public static final int COLOR_PRIMARY = 0xFF0EA0FF; //主题色
    public static final int COLOR_TEXT_333 = 0xFF333333;
    public static final int COLOR_TEXT_999 = 0xFF999999;
    public static final int COLOR_TEXT_666 = 0xFF666666;
    public static final int COLOR_DIVIDER = 0xFFDDDDDD;
    public static final int COLOR_BLACK = 0xFF000000;


    /*-------------------------请求常量-------------------------*/

    public static final int REQUEST_TEST_GET_LOGIN = 1001;

    public static final int REQUEST_TEST_GET_DOCUMENT = 1002;

    /*-------------------------字体路径-------------------------*/

    public static class FONT {
        public static final String FONT_SHARP_WORD_STYLE_TYPEFACE = "font/sharp_word_style.ttf";

        public static final String FONT_DUANNINGMAOBI_TYPEFACE = "font/duanningmaobixingshu.ttf";// 段宁毛笔行书

        public static final String FONT_SQUARE_BOOK_LINE_SIMPLIFIED_TYPEFACE = "font/square_book_line_simplified.ttf";// 方正榜书行简体

        public static final String FONT_NEW_PEDICLE_PAPER_CUT_TYPEFACE = "font/new_pedicle_paper_cut.ttf";// 新蒂剪纸体

        public static final String FONT_FASHION_IN_BLACK_TYPEFACE = "font/fashion_in_black.ttf";// 时尚中黑简体

        public static final String FONT_TWO_STROKES_TYPEFACE = "font/two_strokes.ttf";// 邓玉二笔体
    }

    /*-------------------------消息标签-------------------------*/

    public static final String EVENTBUS_CHANGED_PERSONAL_BG_TAG = "eventbus_changed_personal_bg_tag";
    public static final String EVENTBUS_CHANGED_PERSONAL_HEADER_TAG = "eventbus_changed_personal_header_tag";
    public static final String EVENTBUS_PERSONAL_BG_ACTIVITY = "eventbus_personal_bg_activity";


}
