package OwnSwingComponents.Filter;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class NumberFilter extends DocumentFilter {
    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        super.insertString(fb, offset, string, attr);
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attrs) throws BadLocationException {
        StringBuilder builder = new StringBuilder();
        for (char c : string.toCharArray()) {
            if (Character.isDigit(c)) {
                builder.append(c);
            }
        }
        super.replace(fb, offset, length, builder.toString(), attrs);
    }
}
