package unefa.app.david.com.unefaapp.Custom;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import unefa.app.david.com.unefaapp.R;

/**
 * Created by leonardis on 1/2/17.
 */

public class Dialogs extends Dialog implements
        android.view.View.OnClickListener {

    public Activity activity;
    public ImageView image;
    public TextView title, description, progressText, negativeText, positiveText;
    public LinearLayout layoutProgress, layoutButtons, layoutButtonPositive, layoutButtonNegative, layoutAux;
    public Spinner spinnerTypoUser, spinnerCareer;

    public Dialogs(Activity activity) {
        super(activity);
        // TODO Auto-generated constructor stub
        this.activity = activity;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);

        image = (ImageView) findViewById(R.id.custom_dialog_header_img);
        title = (TextView) findViewById(R.id.custom_dialog_title);
        description = (TextView) findViewById(R.id.custom_dialog_description);
        progressText = (TextView) findViewById(R.id.custom_dialog_progress_text);
        negativeText = (TextView) findViewById(R.id.custom_dialog_button_negative_text);
        positiveText = (TextView) findViewById(R.id.custom_dialog_button_positive_text);
        layoutProgress = (LinearLayout) findViewById(R.id.custom_dialog_layout_progress);
        layoutButtons = (LinearLayout) findViewById(R.id.custom_dialog_layout_buttons);
        layoutButtonPositive = (LinearLayout) findViewById(R.id.custom_dialog_button_positive);
        layoutButtonNegative = (LinearLayout) findViewById(R.id.custom_dialog_button_negative);
        layoutAux = (LinearLayout) findViewById(R.id.custom_dialog_layout_aux);

        spinnerTypoUser = (Spinner) findViewById(R.id.custom_dialog_typo_spinner);
        spinnerCareer = (Spinner) findViewById(R.id.custom_dialog_career_spinner);

        layoutButtonNegative.setOnClickListener(this);

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.custom_dialog_button_negative:
                dismiss();
                break;
        }
    }
}
