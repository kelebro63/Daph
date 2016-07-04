package smontiel.daph.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import smontiel.daph.Inject;
import smontiel.daph.Daph;

public class MainActivity extends Activity 
{
	@Inject private Counter counter;
	private Printer printer;
	
	private Button btnOpenActivity;
	private Button clicker;
	private TextView textCounter;
	Daph dap;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		dap = ((AppSample)getApplication())
			.getDaph();
		dap.inject(this);
		printer = dap.instance(Printer.class);
		
		initializeViews();
		getActionBar().setTitle(printer.getLogger().getLog());
    }

	private void initializeViews() {
		btnOpenActivity = (Button) findViewById(R.id.btn_open_activity);
		btnOpenActivity.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					startActivity(SecondActivity.getOpenIntent(MainActivity.this));
				}
			});
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
}
