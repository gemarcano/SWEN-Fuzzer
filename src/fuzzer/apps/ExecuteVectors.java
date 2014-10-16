package fuzzer.apps;

import java.util.ArrayList;
import java.util.List;

import fuzzer.apps.VVector.VVector;

public class ExecuteVectors {
	private List<VVector> mVectors;
	public ExecuteVectors(List<VVector> aVectors){
		mVectors = aVectors;
	}
	
	public List<Boolean> execute()
	{
		List<Boolean> results = new ArrayList<Boolean>();
		for (VVector vector : mVectors)
		{
			System.out.println("Executing " + vector.getName());
			results.add(vector.test());
		}
		return results;
	}
}
