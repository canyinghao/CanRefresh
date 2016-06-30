package com.canyinghao.canrefresh.shapeloading;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.canyinghao.canrefresh.R;

public class ShapeLoadingView extends View {


    private static final float genhao3 = 1.7320508075689f;
    private static final float mTriangle2Circle = 0.25555555f;

    private Shape mShape = Shape.SHAPE_RECT;


    private int[] colors;


    public boolean mIsLoading = false;


    private Paint mPaint;

    private float mControlX = 0;
    private float mControlY = 0;

    private float mAnimPercent;


    private float mMagicNumber = 0.55228475f;

    public ShapeLoadingView(Context context) {
        this(context, null);

    }

    public ShapeLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public ShapeLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }


    private void init() {

        colors = new int[]{getResources().getColor(R.color.google_red), getResources().getColor(R.color.google_blue), getResources().getColor(R.color.google_green)};


        mPaint = new Paint();
        mPaint.setColor(colors[0]);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);


        reset();
    }

    public void reset() {


        mIsLoading = false;
        mShape = Shape.SHAPE_RECT;
        mAnimPercent = 0;

        invalidate();

    }

    public void setColors(int... colors) {

        if (colors.length >= 3) {
            this.colors = colors;

        }

    }


    @Override
    protected void onDraw(Canvas canvas) {


        if (getVisibility() == GONE) {
            return;
        }


        switch (mShape) {
            case SHAPE_TRIANGLE:

                if (mIsLoading) {
                    mAnimPercent += 0.1611113;
                    // triangle to circle
                    Path path = new Path();
                    path.moveTo(relativeXFromView(0.5f), relativeYFromView(0f));

                    if (mAnimPercent >= 1) {
                        mShape = Shape.SHAPE_CIRCLE;
                        mIsLoading = false;
                        mAnimPercent = 1;
                    }
                    float controlX = mControlX - relativeXFromView(mAnimPercent * mTriangle2Circle)
                            * genhao3;

                    float controlY = mControlY - relativeYFromView(mAnimPercent * mTriangle2Circle);


                    path.quadTo(relativeXFromView(1) - controlX, controlY, relativeXFromView(0.5f + genhao3 / 4), relativeYFromView(0.75f));


                    path.quadTo(relativeXFromView(0.5f), relativeYFromView(0.75f + 2 * mAnimPercent * mTriangle2Circle), relativeXFromView(0.5f - genhao3 / 4), relativeYFromView(0.75f));

                    path.quadTo(controlX, controlY, relativeXFromView(0.5f), relativeYFromView(0f));
                    path.close();
                    canvas.drawPath(path, mPaint);

                    invalidate();

                } else {
                    Path path = new Path();
                    mPaint.setColor(colors[1]);

                    path.moveTo(relativeXFromView(0.5f), relativeYFromView(0f));
                    path.lineTo(relativeXFromView(1), relativeYFromView(genhao3 / 2f));
                    path.lineTo(relativeXFromView(0), relativeYFromView(genhao3 / 2f));

                    mControlX = relativeXFromView(0.5f - genhao3 / 8.0f);
                    mControlY = relativeYFromView(3 / 8.0f);
                    mAnimPercent = 0;
                    path.close();
                    canvas.drawPath(path, mPaint);

                }
                break;
            case SHAPE_CIRCLE:


                if (mIsLoading) {
                    float magicNumber = mMagicNumber + mAnimPercent;
                    mAnimPercent += 0.12;
                    if (magicNumber + mAnimPercent >= 1.9f) {
                        mShape = Shape.SHAPE_RECT;
                        mIsLoading = false;
                    }

                    Path path = new Path();

                    path.moveTo(relativeXFromView(0.5f), relativeYFromView(0f));
                    path.cubicTo(relativeXFromView(0.5f + magicNumber / 2), relativeYFromView(0f),
                            relativeXFromView(1), relativeYFromView(0.5f - magicNumber / 2),
                            relativeXFromView(1f), relativeYFromView(0.5f));
                    path.cubicTo(
                            relativeXFromView(1), relativeXFromView(0.5f + magicNumber / 2),
                            relativeXFromView(0.5f + magicNumber / 2), relativeYFromView(1f),
                            relativeXFromView(0.5f), relativeYFromView(1f));
                    path.cubicTo(relativeXFromView(0.5f - magicNumber / 2), relativeXFromView(1f),
                            relativeXFromView(0), relativeYFromView(0.5f + magicNumber / 2),
                            relativeXFromView(0f), relativeYFromView(0.5f));
                    path.cubicTo(relativeXFromView(0f), relativeXFromView(0.5f - magicNumber / 2),
                            relativeXFromView(0.5f - magicNumber / 2), relativeYFromView(0),
                            relativeXFromView(0.5f), relativeYFromView(0f));


                    path.close();
                    canvas.drawPath(path, mPaint);


                    invalidate();
                } else {


                    mPaint.setColor(colors[2]);
                    Path path = new Path();

                    float magicNumber = mMagicNumber;
                    path.moveTo(relativeXFromView(0.5f), relativeYFromView(0f));
                    path.cubicTo(relativeXFromView(0.5f + magicNumber / 2), 0,
                            relativeXFromView(1), relativeYFromView(magicNumber / 2),
                            relativeXFromView(1f), relativeYFromView(0.5f));
                    path.cubicTo(
                            relativeXFromView(1), relativeXFromView(0.5f + magicNumber / 2),
                            relativeXFromView(0.5f + magicNumber / 2), relativeYFromView(1f),
                            relativeXFromView(0.5f), relativeYFromView(1f));
                    path.cubicTo(relativeXFromView(0.5f - magicNumber / 2), relativeXFromView(1f),
                            relativeXFromView(0), relativeYFromView(0.5f + magicNumber / 2),
                            relativeXFromView(0f), relativeYFromView(0.5f));
                    path.cubicTo(relativeXFromView(0f), relativeXFromView(0.5f - magicNumber / 2),
                            relativeXFromView(0.5f - magicNumber / 2), relativeYFromView(0),
                            relativeXFromView(0.5f), relativeYFromView(0f));
                    mAnimPercent = 0;

                    path.close();
                    canvas.drawPath(path, mPaint);


                }

                break;
            case SHAPE_RECT:


                if (mIsLoading) {


                    mAnimPercent += 0.15;
                    if (mAnimPercent >= 1) {
                        mShape = Shape.SHAPE_TRIANGLE;
                        mIsLoading = false;
                        mAnimPercent = 1;
                    }
                    Path path = new Path();
                    path.moveTo(relativeXFromView(0.5f * mAnimPercent), 0);
                    path.lineTo(relativeYFromView(1 - 0.5f * mAnimPercent), 0);
                    float distanceX = (mControlX) * mAnimPercent;
                    float distanceY = (relativeYFromView(1f) - mControlY) * mAnimPercent;

                    path.lineTo(relativeXFromView(1f) - distanceX, relativeYFromView(1f) - distanceY);
                    path.lineTo(relativeXFromView(0f) + distanceX, relativeYFromView(1f) - distanceY);

                    path.close();


                    canvas.drawPath(path, mPaint);
                    invalidate();

                } else {


                    mPaint.setColor(colors[0]);
                    mControlX = relativeXFromView(0.5f - genhao3 / 4);
                    mControlY = relativeYFromView(0.75f);
                    Path path = new Path();
                    path.moveTo(relativeXFromView(0f), relativeYFromView(0f));
                    path.lineTo(relativeXFromView(1f), relativeYFromView(0f));
                    path.lineTo(relativeXFromView(1f), relativeYFromView(1f));
                    path.lineTo(relativeXFromView(0f), relativeYFromView(1f));
                    path.close();
                    mAnimPercent = 0;
                    canvas.drawPath(path, mPaint);

                }


                break;

        }

        super.onDraw(canvas);
    }


    private float relativeXFromView(float percent) {
        return getWidth() * percent;
    }

    private float relativeYFromView(float percent) {
        return getHeight() * percent;
    }


    public void changeShape() {
        mIsLoading = true;


        invalidate();

    }

    public enum Shape {
        SHAPE_TRIANGLE, SHAPE_RECT, SHAPE_CIRCLE
    }


    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);

        if (visibility == VISIBLE) {
            invalidate();
        }
    }

    public Shape getShape() {
        return mShape;
    }


    public void setShape(Shape mShape) {
        this.mShape = mShape;
    }
}
