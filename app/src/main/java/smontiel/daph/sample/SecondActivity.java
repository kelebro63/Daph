package smontiel.daph.sample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import smontiel.daph.Inject;

public class SecondActivity extends Activity
{
	@Inject private Counter counter;
	
	private Button clicker;
	private TextView textCounter;
	
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);

		((AppSample)getApplication())
			.getDaph().inject(this);
		initializeViews();
    }

	private void initializeViews() {
		textCounter = (TextView) findViewById(R.id.counter);
		
		clicker = (Button) findViewById(R.id.btn_clicker);
		clicker.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					counter.increment();
					textCounter.setText("Counter: " + counter.getCount());
				}
			});
	}

	@Override
	protected void onResume() {
		super.onResume();
		textCounter.setText("Counter: " + counter.getCount());
	}
	
	public static Intent getOpenIntent(Context context) {
		return new Intent(context, SecondActivity.class);
	}
}
