package aeonlabs.barcodescanner.common.libraries.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import aeonlabs.barcodescanner.common.libraries.R;


public class AboutFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater dialogInflater = getActivity().getLayoutInflater();
        View openSourceLicensesView = dialogInflater.inflate(R.layout.fragment_about, null);

        TextView webaddr=openSourceLicensesView.findViewById(R.id.webAddress);

        webaddr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.aeonlabs.solutions"));
                startActivity(browserIntent);
            }
        });

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setView(openSourceLicensesView)
                .setTitle((getString(R.string.dialog_title_about)))
                .setNeutralButton(android.R.string.ok, null);

        return dialogBuilder.create();
    }
}