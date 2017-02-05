package com.llneverland.appoderfood.Fragment;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;

import com.llneverland.appoderfood.R;

/**
 * Created by Windows 8.1 on 1/25/2017.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @TargetApi(Build.VERSION_CODES.N)
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int iNam= calendar.get(calendar.YEAR);
        int iThang = calendar.get(calendar.MONTH);
        int iNgay= calendar.get(calendar.DATE);

        return new DatePickerDialog(getActivity(),this, iNgay, iThang, iNam);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        EditText edNgaySinh = (EditText) getActivity().findViewById(R.id.edNgaySinh);
        String sNgaySinh= dayOfMonth + "/" + (month +1) + "/" +year;
        edNgaySinh.setText(sNgaySinh);
    }
}
