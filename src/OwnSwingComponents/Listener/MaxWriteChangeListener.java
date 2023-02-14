package OwnSwingComponents.Listener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import GUI.FrameStatics;

public class MaxWriteChangeListener implements ChangeListener {

	public static int value = 0;
	public static int multi = 0;
	@Override
	public void stateChanged(ChangeEvent e) {
		MaxWriteChangeListener.change();
	}

	public static void change() {
		MaxWriteChangeListener.multi = 0;
		
		if(FrameStatics.chbxRedWrite.isSelected()) MaxWriteChangeListener.multi++;
		
		if(FrameStatics.chbxGreenWrite.isSelected()) MaxWriteChangeListener.multi++;
		
		if(FrameStatics.chbxBlueWrite.isSelected()) MaxWriteChangeListener.multi++;
		
		
		MaxWriteChangeListener.value = (FrameStatics.widthOriginalImage * FrameStatics.heightOriginalImage)*multi;

			FrameStatics.lblMaxChars
					.setText("Max Chars To Add: " + (value - FrameStatics.txtAreaWrite.getText().length()));

	}


}
