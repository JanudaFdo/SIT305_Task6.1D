package com.example.updatedsmartlearnapp.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.updatedsmartlearnapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class UpgradeFragment extends Fragment {

    public UpgradeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_upgrade, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button btnStarter = view.findViewById(R.id.btn_starter);
        Button btnIntermediate = view.findViewById(R.id.btn_intermediate);
        Button btnAdvanced = view.findViewById(R.id.btn_advanced);

        btnStarter.setOnClickListener(v -> showPaymentDialog("Starter Plan"));
        btnIntermediate.setOnClickListener(v -> showPaymentDialog("Intermediate Plan"));
        btnAdvanced.setOnClickListener(v -> showPaymentDialog("Advanced Plan"));
    }

    private void showPaymentDialog(String planName) {
        BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
        View sheet = getLayoutInflater().inflate(R.layout.dialog_payment_sheet, null);
        dialog.setContentView(sheet);

        LinearLayout optionGPay = sheet.findViewById(R.id.option_google_pay);
        LinearLayout optionVisa = sheet.findViewById(R.id.option_visa);
        TextView labelGPay = sheet.findViewById(R.id.label_gpay);
        TextView labelVisa = sheet.findViewById(R.id.label_visa);

        // Default selection
        optionGPay.setBackgroundResource(R.drawable.bg_selected);
        labelGPay.setTypeface(null, Typeface.BOLD);

        optionGPay.setOnClickListener(v -> {
            optionGPay.setBackgroundResource(R.drawable.bg_selected);
            labelGPay.setTypeface(null, Typeface.BOLD);

            optionVisa.setBackgroundResource(R.drawable.bg_unselected);
            labelVisa.setTypeface(null, Typeface.NORMAL);
        });

        optionVisa.setOnClickListener(v -> {
            optionVisa.setBackgroundResource(R.drawable.bg_selected);
            labelVisa.setTypeface(null, Typeface.BOLD);

            optionGPay.setBackgroundResource(R.drawable.bg_unselected);
            labelGPay.setTypeface(null, Typeface.NORMAL);
        });

        Button payNow = sheet.findViewById(R.id.btn_pay_now);

        payNow.setBackgroundTintList(null);
        payNow.setText("Pay for " + planName);

        payNow.setOnClickListener(v -> {
            Toast.makeText(getContext(), "✅ " + planName + " purchased successfully!", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        dialog.show();
    }

}
