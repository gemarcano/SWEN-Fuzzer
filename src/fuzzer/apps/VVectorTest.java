package fuzzer.apps;

public class VVectorTest {
	private boolean mSuccess;
	private VVector mVector;
	
	public VVectorTest(VVector aVector)
	{
		mVector = aVector;
		mSuccess = mVector.test();
	}
	
	public boolean success()
	{
		return mSuccess;
	}
	public String getDescription()
	{
		return mVector.getDescription();
	}
}
