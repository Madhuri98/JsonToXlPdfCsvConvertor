package clock.alarm.com.xltojsonconvertor.helper;

import android.widget.EditText;

/**
 * Created by Madhuri on 26/09/18.
 */
public class Validation {

    public static boolean isEmpty(EditText et) {
        String str = et.getText().toString();
        return str.isEmpty() || str.equals("");
    }

    public static boolean isNum(EditText et) {
        return (et.getText().toString().trim().matches( "^[0-9]*$"));
    }

}
