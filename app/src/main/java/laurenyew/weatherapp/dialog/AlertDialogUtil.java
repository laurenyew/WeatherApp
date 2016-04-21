package laurenyew.weatherapp.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;

import laurenyew.weatherapp.R;

/**
 * Created by laurenyew on 4/21/16.
 */
public class AlertDialogUtil {

    /**
     * Generic Helper method to create the alert dialog with input, submit, and cancel if requested
     *
     * @param context
     * @param needsInput
     * @param title
     * @param message
     * @param viewResource
     * @param submitButtonTitle
     * @param cancelButtonTitle
     * @param submitListener
     * @param cancelListener
     * @return
     */
    public static AlertDialog createAlertDialog(Context context, boolean needsInput, String title, String message, int viewResource, String submitButtonTitle, String cancelButtonTitle, DialogInterface.OnClickListener submitListener, DialogInterface.OnClickListener cancelListener) {
        final android.support.v7.app.AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.AppTheme_Dialog));
        if (title != null) {
            dialogBuilder.setTitle(title);
        }
        if (message != null) {
            dialogBuilder.setMessage(message);
        }

        if (needsInput) {
            dialogBuilder.setView(viewResource);
        }

        if (submitButtonTitle != null) {
            dialogBuilder.setPositiveButton(submitButtonTitle, submitListener);
        }

        if (cancelButtonTitle != null) {
            dialogBuilder.setNegativeButton(cancelButtonTitle, cancelListener);
        }

        AlertDialog dialog = dialogBuilder.create();
        return dialog;
    }

    /**
     * Helper method to create the add zipcode alert dialog with the appropriate listeners
     *
     * @param context
     * @param title
     * @param submitButtonTitle
     * @param cancelButtonTitle
     * @return
     */
    public static AlertDialog createAddZipcodeAlertDialog(Context context, String title, int viewResource, String submitButtonTitle, String cancelButtonTitle) {
        DialogInterface.OnClickListener submitOnClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("Clicked Submit");
                //TODO: Check the zipcode and make sure it is valid
                //TODO: If not valid, show an error
                //TODO: Add to cache/database
                //TODO: Go to detail view for zipcode
            }
        };

        DialogInterface.OnClickListener cancelOnClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("Clicked Cancel");
                dialog.dismiss();
            }
        };
        return createAlertDialog(context, true, title, null, viewResource, submitButtonTitle, cancelButtonTitle, submitOnClickListener, cancelOnClickListener);
    }

}
