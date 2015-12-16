package jat.imview.ui.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by bulat on 16.12.15.
 */
public class GalleryViewPager extends ViewPager {
    public GalleryViewPager(Context context) {
        super(context);
    }

    public GalleryViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v.getVisibility() != VISIBLE) {
            return false;
        }
        if (v instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) v;
            int scrollX = v.getScrollX();
            int scrollY = v.getScrollY();
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; --childCount) {
                View childAt = viewGroup.getChildAt(childCount);
                if (x + scrollX >= childAt.getLeft() && x + scrollX < childAt.getRight() && y + scrollY >= childAt.getTop() && y + scrollY < childAt.getBottom()) {
                    if (canScroll(childAt, true, dx, (x + scrollX) - childAt.getLeft(), (y + scrollY) - childAt.getTop())) {
                        return true;
                    }
                }
            }
        }
        return super.canScroll(v, checkV, dx, x, y);
    }
}
