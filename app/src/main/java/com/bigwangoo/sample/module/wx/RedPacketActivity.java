package com.bigwangoo.sample.module.wx;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigwangoo.sample.R;
import com.tianxiabuyi.kit.R2;
import com.tianxiabuyi.kit.ui.activity.BaseActivity;

import butterknife.BindView;

/**
 * Created by YaoDong.Wang on 2017/8/17.
 */
public class RedPacketActivity extends BaseActivity {
    //Collections.reverse(mData);

    @BindView(R.id.tv_hint)
    TextView tvHint;
    @BindView(R.id.edt_text)
    EditText edtText;

    final double maxMoney = 999;
    final double minMoney = 200;//0.01
    final String[] textChanged = {""};//textChanged的值
    final String[] beforeTextChanged = {""};//beforeTextChanged值

    public static void newInstance(Context context) {
        context.startActivity(new Intent(context, RedPacketActivity.class));
    }

    @Override
    public int getViewByXml() {
        return R.layout.apps_activity_wx_red;
    }

    @Override
    public void initView() {
        // edtText.setSelection(edtMoney.getHint().toString().length());
        edtText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                beforeTextChanged[0] = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textChanged[0] = s.toString().substring(start, start + count);
            }

            @Override
            public void afterTextChanged(Editable s) {
                textWatch(s, beforeTextChanged[0], textChanged[0], maxMoney, minMoney);
            }
        });
    }

    @Override
    public void initData() {

    }

    /**
     * 文本监听
     *
     * @param s                 afterTextChanged的值
     * @param beforeTextChanged beforeTextChanged值
     * @param textChanged       textChanged的值
     * @param maxMoney          最大值
     * @param minMoney          最小值
     */
    private void textWatch(Editable s, String beforeTextChanged, String textChanged, double maxMoney, double minMoney) {
        if (s.length() != 0) {
            tvHint.setVisibility(View.GONE);
        } else {
            tvHint.setVisibility(View.VISIBLE);
        }

        String inputStr = s.toString();
        //拿到上一次文本中的小数点下标，用来判断是否已有小数点
        int posDotOld = beforeTextChanged.indexOf(".");
        int posDot = inputStr.indexOf(".");//拿到小数点的index下标
        int posZero = inputStr.indexOf("0");//拿到0的index下标

//        if (posZero == 0 && inputStr.length() == 2 && !textChanged.equals(".")) {
//            // 如果第一位是0，并且输入了第二位且第二位不是小数点，移除第一位0
//            inputStr = s.delete(0, 1).toString();
//        }

        // 首位为点
        if (posDot == 0) {
            edtText.setText("0.");
            edtText.setSelection(2);
            return;
        }
        // 1个点
        if (posDotOld != -1 && textChanged.equals(".")) {
            //1.之间已经输入过小数点，现在输入的如果还是小数点，移除
            //2.第一位就输入小数点，移除
            inputStr = s.delete(s.length() - 1, s.length()).toString();
        }
        // 最大金额
        if (inputStr.length() > 0) {
            Double inputMoney = Double.valueOf(inputStr);
            if(inputMoney<100000){

            }
            if (inputMoney > maxMoney) {
                String maxMoneyStr = String.valueOf(maxMoney);
                //您输入的金额过大
                Toast.makeText(this, "金额最大" + maxMoney + "元", Toast.LENGTH_SHORT).show();
//                edtText.setText(maxMoneyStr);
//                edtText.setSelection(maxMoneyStr.length());
            }
//
//            if (minMoney >= 1) {
//                if (inputMoney < minMoney) {
//                    String minMoneyStr = String.valueOf(minMoney);
//                    //您输入的金额过小
//                    Toast.makeText(this, "金额最小" + minMoney + "元", Toast.LENGTH_SHORT).show();
//                    edtText.setText(minMoneyStr);
//                    edtText.setSelection(minMoneyStr.length());
//                }
//            } else {
//                String minMoneyStr = String.valueOf(minMoney);
//                if (inputStr.length() > minMoneyStr.length() - 1 && inputMoney < minMoney) {
//                    //您输入的金额过小
//                    Toast.makeText(this, "金额最小" + minMoney + "元", Toast.LENGTH_SHORT).show();
//                    edtText.setText(minMoneyStr);
//                    edtText.setSelection(minMoneyStr.length());
//                }
//            }
        }
        //如果没有小数点，就跳出
        if (posDot <= 0) {
            return;
        }
        // 2位小数
        if (inputStr.length() - posDot - 1 > 2) {
            s.delete(posDot + 3, posDot + 4); // [)
        }
    }

}
