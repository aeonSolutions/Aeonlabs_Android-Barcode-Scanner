package aeonlabs.barcodescanner.common.libraries.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import aeonlabs.barcodescanner.common.libraries.R;

public class OpenDataFragment extends DialogFragment {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            LayoutInflater dialogInflater = getActivity().getLayoutInflater();
            View openSourceLicensesView = dialogInflater.inflate(R.layout.fragment_open_data, null);

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
            dialogBuilder.setView(openSourceLicensesView)
                    .setTitle((getString(R.string.dialog_title_open_data)))
                    .setNeutralButton(android.R.string.ok, null);

            return dialogBuilder.create();
        }
    }