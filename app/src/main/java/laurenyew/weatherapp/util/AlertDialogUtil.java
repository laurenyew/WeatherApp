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
     * @param context           (calling view context -- used for popping up the dialog)
     * @param title             (title of the alert)
     * @param message           (alert message)
     * @param view              (custom view to put in the alert)
     * @param submitButtonTitle
     * @param cancelButtonTitle
     * @param submitListener
     * @param cancelListener
     * @return
     */
    public static AlertDialog createAlertDialog(Context context, String title, String message, View view, String submitButtonTitle, String cancelButtonTitle, DialogInterface.OnClickListener submitListener, DialogInterface.OnClickListener cancelListener) {
        final android.support.v7.app.AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.AppTheme_Dialog));
        if (title != null) {
            dialogBuilder.setTitle(title);
        }
        if (message != null) {
            dialogBuilder.setMessage(message);
        }

        if (view != null) {
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
     * @param activityContext   (calling view context -- used for popping up the dialog)
     * @param appContext        (Application context -- used for updating shared preferences)
     * @param title             (title of the view)
     * @param viewResource      (layout to use)
     * @param submitButtonTitle
     * @param cancelButtonTitle
     * @return
     */
    public static void showAddZipcodeAlertDialog(final Context activityContext, final Context appContext, String title, int viewResource, String submitButtonTitle, String cancelButtonTitle) {

        //Create the layout view with the input text
        LayoutInflater inflater = LayoutInflater.from(activityContext);
        final View dialogInputView = inflater.inflate(viewResource, null);

        //Use the generic helper to generate an alert dialog
        final AlertDialog dialog = createAlertDialog(activityContext, title, null, dialogInputView, submitButtonTitle, cancelButtonTitle, null, null);
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
                    ZipcodeCache.getInstance().addZipcode(appContext, zipcode);
                    dialog.dismiss();

                    //Go to detail view for zipcode
                    CommonlyUsedIntents.openWeatherDetailActivity(activityContext, zipcode);
                }

            }
        };
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(submitOnClickListener);
    }

    /**
     * Helper method, just returns an empty onClickListener
     * trying to avoid too much boiler plate code
     *
     * @return
     */
    private static DialogInterface.OnClickListener getEmptyOnClickListener() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Do nothing..
            }
        };
    }
}
