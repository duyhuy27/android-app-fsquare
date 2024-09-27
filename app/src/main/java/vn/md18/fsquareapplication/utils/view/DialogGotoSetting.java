package vn.md18.fsquareapplication.utils.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import vn.md18.fsquareapplication.R;
import vn.md18.fsquareapplication.databinding.DialogGotoSettingsBinding;


public class DialogGotoSetting extends Dialog {
    private GotoSettingDialogCallback mCallback;
    private GotoSettingDialogCallback2 mCallback2;

    @SuppressLint("UseCompatLoadingForDrawables")
    public DialogGotoSetting(@NonNull Context context, int themeResId, String title, String description) {
        super(context, themeResId);
        DialogGotoSettingsBinding binding = DialogGotoSettingsBinding.inflate(LayoutInflater.from(context));
        setContentView(binding.getRoot());

        if (getWindow() != null) {
            getWindow().setBackgroundDrawable(context.getDrawable(R.color.transparent));
            getWindow().setLayout(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        }
        setCanceledOnTouchOutside(true);

        binding.tvTitle.setText(title);
        binding.tvDescription.setText(description);

        binding.tvNotNow.setOnClickListener(view -> {
            if (mCallback2 != null) {
                mCallback2.onNotNow();
            }
            dismiss();
        });

        binding.tvGoToSetting.setOnClickListener(view -> {
            if (mCallback != null) {
                mCallback.onGotoSetting();
            }
            if (mCallback2 != null) {
                mCallback2.onGotoSetting();
            }
            dismiss();
        });
    }


    public void setCallback(GotoSettingDialogCallback callback) {
        mCallback = callback;
    }

    public void setCallback2(GotoSettingDialogCallback2 callback) {
        mCallback2 = callback;
    }

    public interface GotoSettingDialogCallback {
        void onGotoSetting();
    }

    public interface GotoSettingDialogCallback2 {
        void onGotoSetting();

        void onNotNow();
    }
}
