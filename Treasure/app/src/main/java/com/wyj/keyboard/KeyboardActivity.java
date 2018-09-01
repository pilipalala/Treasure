package com.wyj.keyboard;

import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.activity.MainActivity;

import java.security.Key;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class KeyboardActivity extends BaseActivity {

    @BindView(R.id.password_view)
    PasswordInputView passwordView;
    @BindView(R.id.edit_keyboard)
    EditText editKeyboard;
    @BindView(R.id.keyboard_view)
    KeyboardView keyboardView;

    @Override
    protected int initView() {
        return R.layout.activity_keyboard;
    }

    @Override
    protected void initData() {
        passwordView.setInputType(InputType.TYPE_NULL);
        editKeyboard.setInputType(InputType.TYPE_NULL);
        passwordView.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                KeyboardUtil.shared(KeyboardActivity.this, passwordView).showKeyboard();
        });
        editKeyboard.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                KeyboardUtil.shared(KeyboardActivity.this, editKeyboard).showKeyboard();

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KeyboardUtil.shared(KeyboardActivity.this, editKeyboard).hideKeyboard();
    }
}
