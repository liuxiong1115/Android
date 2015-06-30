package com.lx.circleprogressbar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends Activity {
	private CircleprogressBar numberCircleProgressBar1;
	private SeekBar seekBar1;
	private View button1, button2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		numberCircleProgressBar1 = (CircleprogressBar) findViewById(R.id.numberCircleProgressBar1);
		seekBar1 = (SeekBar) findViewById(R.id.seekBar1);

		seekBar1.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				numberCircleProgressBar1.setProgress(progress);
			}
		});

		button1 = findViewById(R.id.button1);
		button2 = findViewById(R.id.button2);

		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				numberCircleProgressBar1.setMode(CircleprogressBar.MODE_FILL);
			}
		});

		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				numberCircleProgressBar1
						.setMode(CircleprogressBar.MODE_SCANNING);
			}
		});
	}
}
