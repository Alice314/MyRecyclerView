package com.wusui.myrecyclerview;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.State;
import android.util.Log;
import android.view.View;
import android.support.v7.widget.RecyclerView;

/**
 * Created by fg on 2016/2/2.
 */
public class Decorations extends RecyclerView.ItemDecoration{
    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    private Drawable mDivider;

    private int mOrientation;

    public Decorations(Context context, int orientation) {
    /*Parameters
    context	:Current context, will be used to access resources.
    orientation	:Layout orientation. Should be HORIZONTAL or VERTICAL.
    reverseLayout:	When set to true, layouts from end to start.*/
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
        setOrientation(orientation);
    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent,State state) {

        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }/*Draw any appropriate decorations into the Canvas supplied to the RecyclerView.
         Any content drawn by this method will be drawn before the item views are drawn, and will thus appear underneath the views.
            Parameters
            c	Canvas to draw into
            parent	RecyclerView this ItemDecoration is drawing into
            state	The current state of RecyclerView*/
    }


    public void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            android.support.v7.widget.RecyclerView v = new android.support.v7.widget.RecyclerView(parent.getContext());
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        if (mOrientation == VERTICAL_LIST) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());//Return the intrinsic（固有的，本质的） height of the underlying drawable object.
            // Returns -1 if it has no intrinsic height, such as with a solid color.
        } else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }
    /*Retrieve(取回，恢复) any offsets for the given item. Each field of outRect specifies the number of pixels that the item view should be inset by,
    similar to padding or margin. The default implementation sets the bounds of outRect to 0 and returns.
    If this ItemDecoration does not affect the positioning of item views,
    it should set all four fields of outRect (left, top, right, bottom) to zero before returning.
    If you need to access Adapter for additional data,
    you can call getChildAdapterPosition(View) to get the adapter position of the View.

    Parameters
    outRect	Rect to receive the output.
    view	The child view to decorate
    parent	RecyclerView this ItemDecoration is decorating
    state	The current state of RecyclerView.*/
}
