package GUI;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderValueUpdater {
	
	public void updateSliderLabel(JSlider slider, JLabel label) {
		slider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				label.setText(""+slider.getValue());
				
			}
		});
	}

}
