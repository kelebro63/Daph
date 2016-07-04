package smontiel.daph.sample;

import android.app.Application;
import android.content.Context;

import smontiel.daph.Daph;

public class AppSample extends Application
{
	private static Context ctx;
	private static Daph daph;

	@Override
    public void onCreate() {
        super.onCreate();

		this.ctx = this.getApplicationContext();
		this.daph = new Daph();
		daph.modules(new SampleModule());
	}

	public static Context getContext() {
		return ctx;
	}
	
	public static Daph getDaph() {
		return daph;
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}
}

