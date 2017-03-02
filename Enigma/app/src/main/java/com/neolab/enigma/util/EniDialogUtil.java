package com.neolab.enigma.util;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;

/**
 * Dialog Util
 *
 * @author Pika.
 */
public class EniDialogUtil {

    /** The key of the message to be set on the Bundle  */
    private static final String DIALOG_MESSAGE = "com.neolab.enigma.util.EniDialogUtil_MESSAGE";

    /**
     * Display dialog with OK/Cancel button
     *
     * <p>
     * Display from fragment
     * </p>
     *
     * @param fragmentManager FragmentManager
     * @param listenerFragment Fragment notifying OK / cancel event
     * @param message Message to be displayed in the dialog
     * @param tag Fragment's tag
     */
    public static void showAlertDialog(
            final FragmentManager fragmentManager,
            final Fragment listenerFragment,
            final String message,
            final String tag) {
        final DialogFragment newFragment = new EniAlertDialogFragment();
        final Bundle msg = new Bundle();
        msg.putString(DIALOG_MESSAGE, message);
        newFragment.setArguments(msg);
        newFragment.setTargetFragment(listenerFragment, 0);
        newFragment.show(fragmentManager, tag);
    }

    /**
     * Dialog fragment
     */
    public static class EniAlertDialogFragment extends DialogFragment {

        private DialogInterface.OnClickListener mOnClickListener = null;

        @Override
        public Dialog onCreateDialog(final Bundle savedInstanceState) {
            final Fragment targetFragment = getTargetFragment();
            if (targetFragment instanceof DialogInterface.OnClickListener) {
                mOnClickListener = (DialogInterface.OnClickListener) targetFragment;
            }

            // Use the Builder class for convenient dialog construction
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(getArguments().getString(DIALOG_MESSAGE));
            builder.setPositiveButton("OK", mOnClickListener);
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }

    private EniDialogUtil(){
    }
}
