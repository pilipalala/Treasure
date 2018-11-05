package com.wyj.materialdesign.toolbar;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

public class ToolBarActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener, View.OnClickListener, TextWatcher {

    @BindView(R.id.toolbar1)
    Toolbar toolbar1;
    @BindView(R.id.toolbar2)
    Toolbar toolbar2;
    @BindView(R.id.setting)
    ImageView setting;
    @BindView(R.id.account)
    EditText accountEt;
    @BindView(R.id.accountinput)
    TextInputLayout accountinput;
    @BindView(R.id.password)
    EditText passwordEt;
    @BindView(R.id.passwordinput)
    TextInputLayout passwordinput;
    @BindView(R.id.account_sign_in_button)
    Button accountSignInButton;
    @BindView(R.id.account_tiet)
    TextInputEditText accountTiet;
    @BindView(R.id.password_tiet)
    TextInputEditText passwordTiet;
    @BindView(R.id.btn_login)
    Button btnLogin;
    private String account, passwd;


    @Override
    protected int getContentViewID() {
        return R.layout.activity_tool_bar;
    }

    @Override
    protected void initData() {
        toolbar1.setNavigationIcon(R.mipmap.icon_top_back);
        toolbar1.setTitle("主标题");
        toolbar1.setSubtitle("子标题");
        toolbar1.inflateMenu(R.menu.base_toolbar_menu);
        /*修改toolbar右上方三个点*/
//        toolbar1.setOverflowIcon(getResources().getDrawable(R.drawable.abc_ic_menu_moreoverflow_mtrl_alpha));
        toolbar1.setOnMenuItemClickListener(this);
        toolbar1.setNavigationOnClickListener(this);

        toolbar2.setTitle("Title");
        toolbar2.setNavigationIcon(R.mipmap.icon_top_back);
        toolbar2.setNavigationOnClickListener(this);

        accountEt.addTextChangedListener(this);
        passwordEt.addTextChangedListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int menuItemId = item.getItemId();
        if (menuItemId == R.id.action_search) {
            Toast.makeText(ToolBarActivity.this, "无线", Toast.LENGTH_SHORT).show();

        } else if (menuItemId == R.id.action_notification) {
            Toast.makeText(ToolBarActivity.this, "星星", Toast.LENGTH_SHORT).show();

        } else if (menuItemId == R.id.action_item1) {
            Toast.makeText(ToolBarActivity.this, "item_01", Toast.LENGTH_SHORT).show();

        } else if (menuItemId == R.id.action_item2) {
            Toast.makeText(ToolBarActivity.this, "item_02", Toast.LENGTH_SHORT).show();

        }
        return true;
    }

    @Override
    public void onClick(View view) {
        finish();
    }


    @OnClick(value = {R.id.setting, R.id.account_sign_in_button,R.id.btn_login})
    public void onOtherClick(View view) {
        switch (view.getId()) {
            case R.id.setting:
                Toast.makeText(ToolBarActivity.this, "设置", Toast.LENGTH_SHORT).show();
                break;
            case R.id.account_sign_in_button:
                account = accountEt.getText().toString().trim();
                passwd = passwordEt.getText().toString().trim();
                if (TextUtils.isEmpty(account) || !isAccountValid(account)) {
                    accountinput.setError("无效手机号");
                }
                if (TextUtils.isEmpty(passwd) || !isPasswordValid(passwd)) {
                    passwordinput.setError("密码不少于6位");
                }
                break;
            case R.id.btn_login:
                account = accountTiet.getText().toString().trim();
                passwd = passwordTiet.getText().toString().trim();
                if (TextUtils.isEmpty(account) || !isAccountValid(account)) {
                    accountTiet.setError("无效手机号");
                }
                if (TextUtils.isEmpty(passwd) || !isPasswordValid(passwd)) {
                    passwordTiet.setError("密码不少于6位");
                }
                break;
        }
    }

    private boolean isAccountValid(String name) {
        //TODO: Replace this with your own logic
        Pattern pattern = Pattern.compile("^(13[0-9]|14[5|7]|15\\d|17[6|7]|18[\\d])\\d{8}$");
        return pattern.matcher(name).matches();
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 6;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (accountinput.getError() != null) {
            accountinput.setError(null);
        } else if (passwordinput.getError() != null) {
            passwordinput.setError(null);
        }
    }

}
