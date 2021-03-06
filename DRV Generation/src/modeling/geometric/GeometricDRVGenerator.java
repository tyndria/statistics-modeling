package modeling.geometric;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import modeling.Moments;
import modeling.mcgenerator.MCGenerator;

public class GeometricDRVGenerator implements Constants{
	private List<Long> generatedSequence;
	private MCGenerator mcGenerator;
	private Moments moments = new Moments();
	private double P = Constants.P;
	int n;
	
	public GeometricDRVGenerator() {	
		generatedSequence = new ArrayList<Long>();
		mcGenerator = new MCGenerator(modeling.Constants.A_1, modeling.Constants.C_1);
	}
	
	public void setP(double p) {
		this.P = p;
	}
	
	public void generate(int n) {
		this.n = n;
		generatedSequence.clear();
		mcGenerator.generate(n);

		for (int i = 0; i < n; i ++) {
			generatedSequence.add(getDiscreteRV(mcGenerator.get(i + 1)));
		}
	}
	
	public long get(int index) {
		return generatedSequence.get(index - 1);
	}
	
	public List getGeneratedSequence() {
		return this.generatedSequence;
	}
	
	public double getRealMathExpectation() {
		return 1d / P;
	}
	
	public double getSkewness() {
		List<Double> sequence = generatedSequence.stream().map(Double::valueOf).collect(Collectors.toList());
		return moments.getSkewness(sequence);
	}
	
	public double getRealSkewness() {
		return (2 - P) / Math.sqrt(1 - P);
	}
	
	public double getKurtosis() {
		List<Double> sequence = generatedSequence.stream().map(Double::valueOf).collect(Collectors.toList());
		return moments.getKurtosis(sequence);
	}
	
	public double getRealKurtosis() {
		return (6 + Math.pow(P, 2) / (1 - P));
	}
	
	public double getMathExpectation() {
		List<Double> sequence = generatedSequence.stream().map(Double::valueOf).collect(Collectors.toList());
		return moments.getMathExpectation(sequence);
	}
	
	public double getRealDispersion() {
		return (double)Q / Math.pow(P, 2);
	}
	
	public double getDispersion() {
		List<Double> sequence = generatedSequence.stream().map(Double::valueOf).collect(Collectors.toList());
		return moments.getDispersion(sequence, getMathExpectation());
	}
	
	public long getDiscreteRV(double rv) {
		return (long) Math.ceil(Math.log(rv) / Math.log(Q));
	}
}
