package fuzzer.apps.VVector;

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
}
