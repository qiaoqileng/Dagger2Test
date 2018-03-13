package com.qql.dagger.recommend.activity;

import android.view.View;
import android.widget.EditText;

import com.qql.dagger.recommend.R;
import com.qql.dagger.recommend.base.BaseActivity;
import com.qql.dagger.recommend.presenter.JoinUsPresenter;
import com.qql.dagger.recommend.presenter.contract.JoinUsContract;
import com.qql.dagger.recommend.utils.SnackbarUtil;
import com.qql.dagger.recommend.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 申请入驻
 * Created by qql on 2018/2/27.
 */
public class JoinUsActivity extends BaseActivity<JoinUsPresenter> implements JoinUsContract.View{
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.QQ)
    EditText QQ;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.shop_brand)
    EditText shop_brand;
    @BindView(R.id.shop_url)
    EditText shop_url;
    @BindView(R.id.shop_taobao_url)
    EditText shop_taobao_url;
    @BindView(R.id.shop_type)
    EditText shop_type;
    @BindView(R.id.shop_style)
    EditText shop_style;
    @Override
    public void showError(String msg) {

    }

    @Override
    public void joinResult(boolean success) {
        if (success){
            ToastUtil.show("welcome to FunHouse");
            finish();
        }
    }

    @OnClick(R.id.confirm)
    public void submit(View view){
        Map<String, String> params = new HashMap<>();
        params.put("user.name",username.getText().toString());
        params.put("user.qq",QQ.getText().toString());
        params.put("user.email",email.getText().toString());
        params.put("user.shop_brand",shop_brand.getText().toString());
        params.put("user.shop_url",shop_url.getText().toString());
        params.put("user.shop_taobao_url",shop_taobao_url.getText().toString());
        params.put("user.shop_type",shop_type.getText().toString());
        params.put("user.shop_style",shop_style.getText().toString());
        mPresenter.submit(params);
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_join_us;
    }

    @Override
    protected void initEventAndData() {
        setTitle(R.string.join_in);
    }
}
