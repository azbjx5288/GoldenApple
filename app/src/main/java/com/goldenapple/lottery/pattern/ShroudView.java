package com.goldenapple.lottery.pattern;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.goldenapple.lottery.R;
import com.goldenapple.lottery.app.GoldenAppleApp;
import com.goldenapple.lottery.component.DiscreteSeekBar;
import com.goldenapple.lottery.component.FlowRadioGroup;
import com.goldenapple.lottery.component.QuantityView;
import com.goldenapple.lottery.component.QuantityView.OnQuantityChangeListener;
import com.goldenapple.lottery.data.UserInfo;
import com.goldenapple.lottery.fragment.ChaseFragment;
import com.goldenapple.lottery.material.ShoppingCart;

/**
 * 购物车多倍操作
 * Created by ACE-PC on 2016/2/5.
 */
public class ShroudView {

    private static final String TAG = ShroudView.class.getSimpleName();
    private QuantityView doubleText;    //倍数

    private Button chaseButton;
    private OnModeItemChosenListener modeChosenListener;
    private OnChaseFragmentListener onChaseFragmentListener;

    public ShroudView(View view) {
        doubleText = view.findViewById(R.id.double_number_view);
        chaseButton = view.findViewById(R.id.chase_button);

        doubleText.setMinQuantity(1);
        doubleText.setMaxQuantity(5000);
        doubleText.setQuantity(ShoppingCart.getInstance().getMultiple());

        doubleText.setOnQuantityChangeListener(new OnQuantityChangeListener() {

            @Override
            public void onQuantityChanged(int newQuantity, boolean programmatically) {
                if (modeChosenListener != null)
                    modeChosenListener.onModeItemChosen(newQuantity, 0);
            }

            @Override
            public void onLimitReached() {

            }
        });

        chaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onChaseFragmentListener != null) {
                    onChaseFragmentListener.onClickFragmentListener();
                }
            }
        });
    }

    public void setModeChosenListener(OnModeItemChosenListener modeChosenListener) {
        this.modeChosenListener = modeChosenListener;
    }

    public void setOnChaseFragmentListener(OnChaseFragmentListener onChaseFragmentListener) {
        this.onChaseFragmentListener = onChaseFragmentListener;
    }

    /**
     * 选中监听器
     */
    public interface OnModeItemChosenListener {
        void onModeItemChosen(int multiple, int chaseadd);
    }

    public interface OnChaseFragmentListener {
        void onClickFragmentListener();
    }
}
