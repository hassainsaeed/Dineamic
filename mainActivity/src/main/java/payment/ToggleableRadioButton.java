package payment;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by Mais on 18/05/2016.
 */
public class ToggleableRadioButton extends RadioButton {

    public ToggleableRadioButton(Context context) {
        super(context);
        // this.toggle();
    }

    public ToggleableRadioButton(Context context, AttributeSet attrs) {

        super(context, attrs);
        //this.toggle();
    }

    @Override
    public void toggle() {
        if(isChecked()) {
            setChecked(false);
        } else {
            setChecked(true);
        }
    }

}