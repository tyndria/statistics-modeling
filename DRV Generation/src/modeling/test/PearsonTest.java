package modeling.test;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import org.apache.commons.math3.distribution.GeometricDistribution;

import modeling.Utils;
import modeling.geometric.Constants;

public class PearsonTest {
	Utils utils = new Utils();
	private Map<Double, Integer> frequencyMap = new TreeMap();
	private GeometricDistribution geometricDistribution = new GeometricDistribution(Constants.P);
	private ChiSquaredDistribution chiDistribution;
	double e = 0.005;
	
	public boolean run(List<Double> generatedSequence) {
		frequencyMap = utils.countDistrubution(generatedSequence);
		int n = generatedSequence.size();
		double sqrtChi = frequencyMap.keySet().stream().mapToDouble((rv) -> {
			int freq = frequencyMap.get(rv);
			double p = geometricDistribution.cumulativeProbability((int)Math.round(rv));
			return Math.pow(freq - p * n, 2) / (p * n);
		}).sum();
		
		int degreesOfFreedom = frequencyMap.size() - 1;
		chiDistribution = new ChiSquaredDistribution(degreesOfFreedom);
		return (1 - chiDistribution.cumulativeProbability(sqrtChi)) < e;
	}
}
