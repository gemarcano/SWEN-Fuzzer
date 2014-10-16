package fuzzer.apps.VVector;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.SimpleTimeLimiter;

public abstract class VVector {
	public abstract boolean test();
	public final String getDescription(){
		return mName + ": " + mMessage;
	}
	
	private final String mName;
	private String mMessage;
	VVector(String aName)
	{
		mName = aName;
	}
	
	protected final void loadDescription(String aMessage)
	{
		mMessage = aMessage;
	}
	
	public final String getName()
	{
		return mName;
	}
	
	public boolean testWithTimeout(int timeout) {
		SimpleTimeLimiter limiter = new SimpleTimeLimiter();
		boolean result = false;
		try {
			limiter.callWithTimeout(new Callable<Boolean>() {
				public Boolean call() {
					return test();
				}
			}, timeout, TimeUnit.MILLISECONDS, false);
		} catch (Exception e) {
			result = true;
			loadDescription("Timed out with " + Integer.toString(timeout) + " ms.");
		}
		return result;
	}
}
