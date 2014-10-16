package fuzzer.apps;

import java.util.ArrayList;
import java.util.List;

import fuzzer.apps.VVector.VVector;

public class ExecuteVectors {
	private List<VVector> mVectors;
	public ExecuteVectors(List<String> aVectors){
		mVectors = new ArrayList<VVector>();
	}
	
	public void execute()
	{
		for (VVector vector : mVectors)
		{
			vector.test();
		}
	}
}
