package com.andreabaccega.formedittextvalidator;

import android.text.TextUtils;
import android.widget.EditText;

/**
 * A validator that returns true only if the input field contains only numbers.
 * and between from and to
 * 
 * @author Andrea Baccega <me@andreabaccega.com>
 * 
 */
public class NumericBetweenValidator extends Validator {
	double str;
	double from;
	double to;

	public NumericBetweenValidator(String _customErrorMessage, double from,
			double to) {
		super(_customErrorMessage);
		this.from = from;
		this.to = to;
	}

	public boolean isValid(EditText et) {
		if (TextUtils.isEmpty(et.getText().toString())) {
			str = 0;
		} else {
			str = Double.parseDouble(et.getText().toString());
		}
		return str >= from && str <= to;
	}
}
