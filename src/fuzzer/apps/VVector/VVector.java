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
		mMessage = mMessage;
	}
	
	public final String getName()
	{
		return mName;
	}
	
	public boolean testWithTimeout(int timeout) {
		SimpleTimeLimiter limiter = new SimpleTimeLimiter();
		try {
			limiter.callWithTimeout(new Callable<Boolean>() {
				public Boolean call() {
					test();
					return true;
				}
			}, timeout, TimeUnit.SECONDS, false);
			return true;
		} catch (Exception e) {
		}
		return false;
	}
}
