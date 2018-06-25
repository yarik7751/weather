package by.yarik.yarikweather.util;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import by.yarik.yarikweather.R;

public class DialogUtils {

    public static AlertDialog noConnection(
            Context context,
            DialogInterface.OnClickListener tryAgain,
            DialogInterface.OnClickListener closeApp) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_info, null);

        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvTitle.setText(R.string.error);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.DialogTheme);
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(R.string.try_again, tryAgain);
        alertDialogBuilder.setNegativeButton(R.string.close_app, closeApp);

        AlertDialog dialog = alertDialogBuilder.create();
        dialog.setOnShowListener((DialogInterface arg) -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getColor(R.color.negative_btn));
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getColor(R.color.positive_btn));
            }
        });
        return dialog;
    }
}
