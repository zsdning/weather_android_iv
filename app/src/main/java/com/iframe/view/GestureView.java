package com.iframe.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.iframe.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author suetming (suetming.ma@creditcloud.com)
 *         <p>
 *         创建时间：2013-8-21 上午9:29:39
 */
public class GestureView extends View {

    Drawable normal;

    Drawable unSelected;

    Drawable selected;

    Drawable[] normalArrows = new Drawable[9];
    Drawable[] selectedArrows = new Drawable[9];

    Node[] nodes = new Node[9];

    List<Node> selectedNodes = new ArrayList<Node>();

    PointF start = new PointF();
    PointF end = new PointF();

    String code = "";

    String gestureCode = "";

    Paint paint;

    OnCodeChangedListener codeListener;

    int wPoint, hPoint, wArrowPoint, hArrowPoint;

    double hLength, wLength;

    int gestureColor;

    int percent;

    int correction;

    int gestureWidth;

    boolean showArrow = false;

    public GestureView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init(context, attrs);
    }

    public GestureView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.App);

        try {
            showArrow = typedArray.getBoolean(R.styleable.App_showArrow, false);

            normal = typedArray.getDrawable(R.styleable.App_normal);
            selected = typedArray.getDrawable(R.styleable.App_selected);

            unSelected = typedArray.getDrawable(R.styleable.App_normal);

            gestureColor = typedArray.getColor(R.styleable.App_gestureColor, getResources().getColor(R.color.gesture));

            code = typedArray.getString(R.styleable.App_gestureCode);
            gestureWidth = typedArray.getDimensionPixelSize(R.styleable.App_gestureWidth, 20);
            percent = typedArray.getInt(R.styleable.App_percent, 60);

            correction = typedArray.getInt(R.styleable.App_correction, 40);

            wPoint = normal.getIntrinsicWidth();
            hPoint = normal.getIntrinsicHeight();

            if (showArrow)
                initArrows(typedArray);
        } catch (Exception e) {
            typedArray.recycle();
        }

        paint = new Paint();
        paint.setColor(gestureColor);
        paint.setStrokeWidth(gestureWidth);

        for (int i = 0; i < 9; i++) {
            Node node = new Node();
            node.bounds = new RectF();
            node.index = i;
            node.code = i;
            nodes[i] = node;
        }
    }

    private void initArrows(TypedArray typedArray) {
        normalArrows[0] = typedArray.getDrawable(R.styleable.App_normal_0);
        normalArrows[1] = typedArray.getDrawable(R.styleable.App_normal_1);
        normalArrows[2] = typedArray.getDrawable(R.styleable.App_normal_2);
        normalArrows[3] = typedArray.getDrawable(R.styleable.App_normal_3);
        normalArrows[5] = typedArray.getDrawable(R.styleable.App_normal_5);
        normalArrows[6] = typedArray.getDrawable(R.styleable.App_normal_6);
        normalArrows[7] = typedArray.getDrawable(R.styleable.App_normal_7);
        normalArrows[8] = typedArray.getDrawable(R.styleable.App_normal_8);

        selectedArrows[0] = typedArray.getDrawable(R.styleable.App_selected_0);
        selectedArrows[1] = typedArray.getDrawable(R.styleable.App_selected_1);
        selectedArrows[2] = typedArray.getDrawable(R.styleable.App_selected_2);
        selectedArrows[3] = typedArray.getDrawable(R.styleable.App_selected_3);
        selectedArrows[5] = typedArray.getDrawable(R.styleable.App_selected_5);
        selectedArrows[6] = typedArray.getDrawable(R.styleable.App_selected_6);
        selectedArrows[7] = typedArray.getDrawable(R.styleable.App_selected_7);
        selectedArrows[8] = typedArray.getDrawable(R.styleable.App_selected_8);

        wArrowPoint = normalArrows[0].getIntrinsicWidth();
        hArrowPoint = normalArrows[0].getIntrinsicHeight();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        for (int i = 1; i < selectedNodes.size(); i++) {
            Node s = selectedNodes.get(i - 1);
            Node e = selectedNodes.get(i);

            canvas.drawLine(s.bounds.centerX(), s.bounds.centerY(), e.bounds.centerX(), e.bounds.centerY(), paint);
        }

        for (int i = 0; i < 9; i++) {
            Node node = nodes[i];
            switch (node.selected) {
                case R.styleable.App_selected:
                    selected.setBounds((int) node.bounds.left, (int) node.bounds.top, (int) node.bounds.right,
                            (int) node.bounds.bottom);
                    selected.draw(canvas);

                    if (node.arrow != -1 && showArrow) {

                        Rect bounds = getArrowBounds(node.arrow, selected.getBounds());

                        selectedArrows[node.arrow].setBounds(bounds);
                        selectedArrows[node.arrow].draw(canvas);
                    }
                    break;
                case R.styleable.App_normal:
                    normal.setBounds((int) node.bounds.left, (int) node.bounds.top, (int) node.bounds.right,
                            (int) node.bounds.bottom);
                    normal.draw(canvas);

                    if (node.arrow != -1 && showArrow) {

                        Rect bounds = getArrowBounds(node.arrow, normal.getBounds());

                        normalArrows[node.arrow].setBounds(bounds);
                        normalArrows[node.arrow].draw(canvas);
                    }
                    break;
                default:
                    if (unSelected != null) {
                        unSelected.setBounds((int) node.bounds.left, (int) node.bounds.top, (int) node.bounds.right,
                                (int) node.bounds.bottom);
                        unSelected.draw(canvas);
                    }
                    break;
            }

        }

        if (start.x != 0 && start.y != 0)
            canvas.drawLine(start.x, start.y, end.x, end.y, paint);
        super.onDraw(canvas);
    }

    private Rect getArrowBounds(int arrow, Rect bounds) {
        int x = bounds.left;
        int y = bounds.top;
        double w = bounds.width() / 3.0;
        double h = bounds.height() / 3.0;

        double deltaW = w * correction / 100;
        double deltaH = h * correction / 100;

        double deltaX = (w - wArrowPoint) / 2.0;
        double deltaY = (h - hArrowPoint) / 2.0;

        int left = (int) (x + deltaX + arrow % 3 * w);
        int top = (int) (y + deltaY + arrow / 3 * h);

        switch (arrow) {
            case 0:
                left += deltaW;
                top += deltaH;
                break;
            case 2:
                left -= deltaW;
                top += deltaH;
                break;
            case 6:
                left += deltaW;
                top -= deltaH;
                break;
            case 8:
                left -= deltaW;
                top -= deltaH;
                break;
            default:
                break;
        }
        return new Rect(left, top, left + wArrowPoint, top + hArrowPoint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                onDown(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                onMove(x, y);
                // Log.i("logtime", "action move, "
                // + new
                // SimpleDateFormat("yyyy-MM-dd-hh-mm-ss-SSS").format(System.currentTimeMillis()));
                break;
            case MotionEvent.ACTION_UP:
                // Log.i("logtime",
                // "action up, " + new
                // SimpleDateFormat("yyyy-MM-dd-hh-mm-ss-SSS").format(System.currentTimeMillis()));
                onUp(x, y);
                break;
            default:
                break;
        }
        return true;
    }

    private void onUp(float x, float y) {
        if (!TextUtils.isEmpty(gestureCode) && codeListener != null) {
            codeListener.gestureCode(gestureCode);
        }

        clear();
        invalidate();
    }

    private void onMove(float x, float y) {
        if (selectedNodes.size() == 9) {
            return;
        }

        end = new PointF(x, y);

        for (int i = 0; i < 9; i++) {
            Node node = nodes[i];

            float w = (float) (node.bounds.width() * percent / 100.0);
            float h = (float) (node.bounds.height() * percent / 100.0);
            float cx = node.bounds.centerX();
            float cy = node.bounds.centerY();

            RectF rc = new RectF(cx - w / 2, cy - h / 2, cx + w / 2, cy + h / 2);

            if (!rc.contains(x, y)) {
                continue;
            }

            node.selected = R.styleable.App_selected;

            if (!selectedNodes.contains(node)) {
                if (!selectedNodes.isEmpty()) {
                    Node s = selectedNodes.get(selectedNodes.size() - 1);
                    insertNode(s, node);

                }

                gestureCode += node.code;
                selectedNodes.add(node);
                start = new PointF(node.bounds.centerX(), node.bounds.centerY());
            }
        }

        Node src = null;
        for (Node dst : selectedNodes) {
            if (src == null) {
                src = dst;
                continue;
            }
            int index = dst.index - src.index;
            resetArrow(src, index);
            src = dst;
        }

        if (!selectedNodes.isEmpty()) {
            selectedNodes.get(selectedNodes.size() - 1).arrow = -1;
        }

        invalidate();
    }

    private void resetArrow(Node src, int index) {
        switch (index) {
            case 1:
                src.arrow = 5;
                break;
            case -1:
                src.arrow = 3;
                break;
            case 3:
                src.arrow = 7;
                break;
            case -3:
                src.arrow = 1;
                break;
            case 4:
                src.arrow = 8;
                break;
            case -4:
                src.arrow = 0;
                break;
            case 2:
                src.arrow = 6;
                break;
            case -2:
                src.arrow = 2;
                break;
            default:
                break;
        }
    }

    private void insertNode(Node s, Node node) {
        int max = Math.max(s.index, node.index);
        int min = Math.min(s.index, node.index);

        if (min == 0 && (max == 2 || max == 6 || max == 8)) {
            Node m = nodes[(s.index + node.index) / 2];
            gestureCode += m.code;
            m.selected = R.styleable.App_selected;
            selectedNodes.add(m);
        } else if (min == 1 && max == 7) {
            Node m = nodes[(s.index + node.index) / 2];
            gestureCode += m.code;
            m.selected = R.styleable.App_selected;
            selectedNodes.add(m);
        } else if (min == 3 && max == 5) {
            Node m = nodes[(s.index + node.index) / 2];
            gestureCode += m.code;
            m.selected = R.styleable.App_selected;
            selectedNodes.add(m);
        } else if (min == 6 && max == 8) {
            Node m = nodes[(s.index + node.index) / 2];
            gestureCode += m.code;
            m.selected = R.styleable.App_selected;
            selectedNodes.add(m);
        } else if (min == 2 && max == 6) {
            Node m = nodes[(s.index + node.index) / 2];
            gestureCode += m.code;
            m.selected = R.styleable.App_selected;
            selectedNodes.add(m);
        } else if (min == 2 && max == 8) {
            Node m = nodes[(s.index + node.index) / 2];
            gestureCode += m.code;
            m.selected = R.styleable.App_selected;
            selectedNodes.add(m);
        }
    }

    private void onDown(float x, float y) {
        for (Node node : selectedNodes) {
            node.selected = R.styleable.App_unselected;
        }
        selectedNodes.clear();

        for (int i = 0; i < 9; i++) {
            Node node = nodes[i];
            float w = (float) (node.bounds.width() * percent / 100.0);
            float h = (float) (node.bounds.height() * percent / 100.0);
            float cx = node.bounds.centerX();
            float cy = node.bounds.centerY();

            RectF rc = new RectF(cx - w / 2, cy - h / 2, cx + w / 2, cy + h / 2);

            if (!rc.contains(x, y)) {
                continue;
            }

            start = new PointF(node.bounds.centerX(), node.bounds.centerY());
            end = start;

            node.selected = R.styleable.App_selected;
            gestureCode += node.code;
            selectedNodes.add(node);
        }

        invalidate();
    }

    public void clear() {

        if (TextUtils.isEmpty(code)) {
            selectedNodes.clear();
            for (int i = 0; i < 9; i++) {
                Node node = nodes[i];
                node.selected = R.styleable.App_unselected;
            }
        } else if (code.contentEquals(gestureCode)) {
            for (Node node : selectedNodes) {
                node.selected = R.styleable.App_normal;
            }

        } else {
            for (Node node : selectedNodes) {
                node.selected = R.styleable.App_selected;
            }
        }

        gestureCode = "";

        end = new PointF();
        start = new PointF();

        invalidate();
    }

    public void reset() {
        selectedNodes.clear();
        for (int i = 0; i < 9; i++) {
            Node node = nodes[i];
            node.selected = R.styleable.App_unselected;
        }

        gestureCode = "";

        end = new PointF();
        start = new PointF();

        invalidate();
    }

    public void setCode(String code) {
        this.code = code;
    }

    public class Node {
        public int selected = R.styleable.App_unselected;
        public int code;
        public RectF bounds = new RectF();
        public int index;
        public int arrow = -1;
    }

    public interface OnCodeChangedListener {
        void gestureCode(String code);
    }

    public void setOnCodeChangedListener(OnCodeChangedListener listener) {
        codeListener = listener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int w = getMeasuredWidth(), h = w;

        setMeasuredDimension(w, w); // Snap to width

        int paddingTop = getPaddingTop();

        int paddingBottom = getPaddingBottom();

        int paddingLeft = getPaddingLeft();

        int paddingRight = getPaddingRight();

        hLength = (h - paddingBottom - paddingTop) / 3.0;
        wLength = (w - paddingLeft - paddingRight) / 3.0;

        double x = (wLength - wPoint) / 2;
        double y = (hLength - hPoint) / 2;

        for (int i = 0; i < 9; i++) {
            nodes[i].bounds.set((int) (x + i % 3 * wLength), (int) (y + (i / 3) * hLength),
                    (int) (x + i % 3 * wLength + wPoint), (int) (y + (i / 3) * hLength + hPoint));
        }
    }
}