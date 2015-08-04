package openthinks.vimixer.ui.util;

import javafx.util.StringConverter;

public class NumberStringConverter extends StringConverter<Number> {

	@Override
	public String toString(Number object) {
		return String.valueOf(object.intValue());
	}

	@Override
	public Number fromString(String string) {
		return Integer.valueOf(string);
	}
}