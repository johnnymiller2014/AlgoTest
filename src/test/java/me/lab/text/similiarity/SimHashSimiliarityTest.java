package me.lab.text.similiarity;
import me.lab.text.similiarity.*;
import static org.junit.Assert.*;

import org.junit.Test;

import junit.framework.Assert;

public class SimHashSimiliarityTest {

	@Test
	public void testSimiliarity() {
		
		SimHashSimiliarity testClass = new SimHashSimiliarity();
		double similiarity= testClass.similiarity("The cat sat on the wall", "The cat sat on the wall");
		Assert.assertEquals(1.0, similiarity);
		
		double similiarity2= testClass.similiarity("The dog sat on the wall", "The cat sat on the wall");
		Assert.assertTrue(similiarity2<0.82 && similiarity2>0.81);
		
		double similiarity3= testClass.similiarity("The dog barked on the wall", "The cat sat on the wall");
		Assert.assertTrue(similiarity3<0.70 && similiarity3>0.69);
		
		double similiarity4= testClass.similiarity("More formally, a support vector machine constructs a hyperplane or set of hyperplanes in a high- or infinite-dimensional space, which can be used for classification, regression, or other tasks. Intuitively, a good separation is achieved by the hyperplane that has the largest distance to the nearest training-data point of any class (so-called functional margin), since in general the larger the margin the lower the generalization error of the classifier.", "More formally, a support vector machine constructs a REPLACED or set of HYPERPLANES_MODIFIED in a high- or infinite-dimensional space, which can be used for classification, regression, or other tasks. Intuitively, a good separation is achieved by the hyperplane that has the largest distance to the nearest training-data point ADDED of any class (so-called functional margin), since in general the larger the margin the lower the generalization error of the classifier.");
		Assert.assertTrue(similiarity4<0.95&&similiarity4>0.94);
	}

}
