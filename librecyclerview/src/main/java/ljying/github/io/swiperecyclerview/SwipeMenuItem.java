package ljying.github.io.swiperecyclerview;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

/**
 * Description: 侧滑菜单条目
 * @author Li Jianying
 * @version 2.0
 * @since 2016/7/26
 */
public class SwipeMenuItem {

    private Context mContext;
    private String title;
    private Drawable icon;
    private Drawable background;
    private int titleColor = -1;
    private int titleSize = -1;
    private int width = -2;
    private int height = -2;

    public SwipeMenuItem(Context context) {
        mContext = context;
    }

    public SwipeMenuItem setTextSize(int titleSize) {
        this.titleSize = titleSize;
        return this;
    }

    public int getTextSize() {
        return titleSize;
    }

    public SwipeMenuItem setTextColor(int titleColor) {
        this.titleColor = titleColor;
        return this;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public SwipeMenuItem setText(String title) {
        this.title = title;
        return this;
    }

    public SwipeMenuItem setText(int resId) {
        setText(mContext.getString(resId));
        return this;
    }

    public String getText() {
        return title;
    }

    public SwipeMenuItem setImage(Drawable icon) {
        this.icon = icon;
        return this;
    }

    public SwipeMenuItem setImage(int resId) {
        return setImage(ResCompat.getDrawable(mContext, resId));
    }

    public Drawable getImage() {
        return icon;
    }

    public Drawable getBackground() {
        return background;
    }

    public SwipeMenuItem setBackgroundDrawable(Drawable background) {
        this.background = background;
        return this;
    }

    public SwipeMenuItem setBackgroundDrawable(int resId) {
        this.background = ResCompat.getDrawable(mContext, resId);
        return this;
    }

    public SwipeMenuItem setBackgroundColor(int color) {
        this.background = new ColorDrawable(color);
        return this;
    }

    public int getWidth() {
        return width;
    }

    public SwipeMenuItem setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public SwipeMenuItem setHeight(int height) {
        this.height = height;
        return this;
    }
}
