package com.sun.numaddsub;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by SHW on 2017/6/1.
 * 加减器
 */

public class NumAddSub extends LinearLayout implements View.OnClickListener, View.OnLongClickListener {

    private final Context context;

    /*最小值*/
    private int mMin = 0;

    /*最大值*/
    private int mMax = 100;

    /*按钮字体大小*/
    private int mPressSize = 14;

    /*数字字体大小*/
    private int mNumSize = 14;

    /*数值是否可编辑*/
    private boolean mCanEditNum = false;

    /*增加按钮是否可长按*/
    private boolean mCanLongPress = false;

    private TextView mTxtNum;
    private TextView mBtnAdd;
    private TextView mBtnSub;

    private OnBtnClickListener onBtnClickListener;

    private int value;

    public NumAddSub(Context context) {
        this(context, null);
    }

    public NumAddSub(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumAddSub(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        initView();

        initViewListener();

    }

    private void initView() {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_num_add_sub, this, true);

        mTxtNum = (TextView) view.findViewById(R.id.txt_num);
        mBtnAdd = (TextView) view.findViewById(R.id.btn_add);
        mBtnSub = (TextView) view.findViewById(R.id.btn_sub);
    }


    public void setMin(int mMin) {
        this.mMin = mMin;
    }

    public void setMax(int mMax) {
        this.mMax = mMax;
    }

    public void setPressSize(int mPressSize) {
        this.mPressSize = mPressSize;
    }

    public void setNumSize(int mNumSize) {
        this.mNumSize = mNumSize;
    }

    public void canEditNum(boolean mCanEditNum) {
        this.mCanEditNum = mCanEditNum;
    }

    public void canLongPress(boolean mCanLongPress) {
        this.mCanLongPress = mCanLongPress;
    }


    public void setValue(int input) {

        if (input < mMin) input = mMin;
        else if (input > mMax) input = mMax;

        this.value = input;

        mTxtNum.setText(value + "");
    }


    private void initViewListener() {
        mTxtNum.setTextSize((float) mNumSize);
        mBtnAdd.setTextSize((float) mPressSize);
        mBtnSub.setTextSize((float) mPressSize);

        mBtnAdd.setOnClickListener(this);
        mBtnSub.setOnClickListener(this);

        if (mCanEditNum) {
            mTxtNum.setOnClickListener(this);
        }
        if (mCanLongPress) {
            mBtnAdd.setOnLongClickListener(this);
            mBtnSub.setOnLongClickListener(this);
        }

        checkValue(getValue());
    }


    /**
     * @param view 点击事件
     */
    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id) {
            case R.id.txt_num:

                showEditDialog();

                break;

            case R.id.btn_add:

                numAdd();
                if (onBtnClickListener != null)
                    onBtnClickListener.OnAddClick(this, view, this.value);

                break;

            case R.id.btn_sub:

                numSub();
                if (onBtnClickListener != null)
                    onBtnClickListener.OnSubClick(this, view, this.value);

                break;
        }


    }


    private void showEditDialog() {

    }

    private void numAdd() {

        getValue();

        if (value < mMax) value++;

        mTxtNum.setText(value + "");

        checkValue(this.value);
    }


    private void numSub() {

        getValue();

        if (value > mMin) value--;

        mTxtNum.setText(value + "");

        checkValue(this.value);
    }


    public int getValue() {

        String txtNum = mTxtNum.getText().toString();

        //默认为零
        if (TextUtils.isEmpty(txtNum))
            this.value = 0;
        else
            this.value = Integer.parseInt(txtNum);

        return this.value;
    }


    /**
     * 检测数值是否越界并设置按钮
     */
    private void checkValue(int currentValue) {

        if (currentValue >= mMax) {
            mBtnAdd.setEnabled(false);
            mBtnSub.setEnabled(true);
        } else if (currentValue <= mMin) {
            mBtnAdd.setEnabled(true);
            mBtnSub.setEnabled(false);
        } else {
            mBtnAdd.setEnabled(true);
            mBtnSub.setEnabled(true);
        }

    }


    /**
     * 点击按钮的回调接口
     */
    public interface OnBtnClickListener {

        void OnAddClick(NumAddSub numAddSub, View view, int value);

        void OnSubClick(NumAddSub numAddSub, View view, int value);

    }

    public void setOnBtnClickListener(OnBtnClickListener onBtnClickListener) {

        this.onBtnClickListener = onBtnClickListener;
    }


    /**
     * 长按按钮持续增减
     */
    @Override
    public boolean onLongClick(View view) {

        return false;

    }

}
