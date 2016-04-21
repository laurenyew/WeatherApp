package laurenyew.weatherapp.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import laurenyew.weatherapp.R;
import laurenyew.weatherapp.cache.ZipcodeCache;
import laurenyew.weatherapp.constants.Regex;

/**
 * Created by laurenyew on 4/21/16.
 * <p/>
 * Utility class to create basic alert dialogs
 */
public class AlertDialogUtil {

    /**
     * Generic Helper method to create the alert dialog with input, submit, and cancel if requested
     *
     * @param context
     * @param needsInput
     * @param title
     * @param message
     * @param view
     * @param submitButtonTitle
     * @param cancelButtonTitle
     * @param submitListener
     * @param cancelListener
     * @return
     */
    public static AlertDialog createAlertDialog(Context context, boolean needsInput, String title, String message, View view, String submitButtonTitle, String cancelButtonTitle, DialogInterface.OnClickListener submitListener, DialogInterface.OnClickListener cancelListener) {
        final android.support.v7.app.AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.AppTheme_Dialog));
        if (title != null) {
            dialogBuilder.setTitle(title);
        }
        if (message != null) {
            dialogBuilder.setMessage(message);
        }

        if (needsInput) {
            dialogBuilder.setView(view);
        }

        if (submitButtonTitle != null) {
            dialogBuilder.setPositiveButton(submitButtonTitle, (submitListener == null) ? getEmptyOnClickListener() : submitListener);
        }

        if (cancelButtonTitle != null) {
            dialogBuilder.setNegativeButton(cancelButtonTitle, (cancelListener == null) ? getEmptyOnClickListener() : cancelListener);
        }

        AlertDialog dialog = dialogBuilder.create();
        return dialog;
    }

    /**
     * Helper method to create the add zipcode alert dialog with the appropriate listeners
     *
     * @param context
     * @param title
     * @param viewResource
     * @param submitButtonTitle
     * @param cancelButtonTitle
     * @return
     */
    public static void showAddZipcodeAlertDialog(final Context context, String title, int viewResource, String submitButtonTitle, String cancelButtonTitle) {

        //Create the layout view with the input text
        LayoutInflater inflater = LayoutInflater.from(context);
        final View dialogInputView = inflater.inflate(viewResource, null);

        //Use the generic helper to generate an alert dialog
        final AlertDialog dialog = createAlertDialog(context, true, title, null, dialogInputView, submitButtonTitle, cancelButtonTitle, null, null);
        dialog.show();

        //Set the onclick listener for submit after so we don't dismiss the dialog
        View.OnClickListener submitOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText inputText = (EditText) dialogInputView.findViewById(R.id.dialog_input_text);
                String zipcode = inputText.getText().toString();
                //Check for invalid zipcode
                if (!zipcode.matches(Regex.ZIPCODE_REGEX)) {
                    //If not valid, show an error
                    TextView errorText = (TextView) dialogInputView.findViewById(R.id.dialog_input_error);
                    errorText.setVisibility(View.VISIBLE);

                } else {
                    //Add to cache/database
                    ZipcodeCache.getInstance().addZipcode(zipcode);
                    dialog.dismiss();

                    //Go to detail view for zipcode
                    CommonlyUsedIntents.openWeatherDetailActivity(context, zipcode);
                }

            }
        };
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(submitOnClickListener);
    }

    private static DialogInterface.OnClickListener getEmptyOnClickListener() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Do nothing..
            }
        };
    }
}
