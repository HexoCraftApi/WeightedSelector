/*
 * Copyright 2016 hexosse
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import Helpers.InputBuilder;
import Helpers.ProbabilityHelpers;
import com.github.hexocraftapi.weighted.selector.Extensions.ExtensionMethods;
import com.github.hexocraftapi.weighted.selector.WeightedItem;
import com.github.hexocraftapi.weighted.selector.WeightedSelector;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * @author <b>Hexosse</b> (<a href="https://github.com/hexosse">on GitHub</a>))
 */
public class ProbabilitySimpleTests
{
	// This test runs a million trials with randomized parameters, and tests to make sure the
	// distribution of selections is somewhat close to the weight proportions. For instance,
	// if one item's weight makes up 33% of a test's total weight, after a million tests it
	// should be selected about 33% of the time.

	private final int Trials = 1000000; //We do a lot of tests.

	//AcceptableDeviation gives us some margin for small statistical anomolies. For instance,
	//given the example above, maybe the item is selected 34% or even 37% of the time. That's an acceptable
	//abnormality. We could close the gap by running even more tests.
	private final int AcceptableDeviation = 4;

	//Range of weighted items going in.
	private final int MinInputs = 2;
	private final int MaxInputs = 4;

	//Range of weights each item can have.
	private final int MinWeight = 1;
	private final int MaxWeight = 3;


	@Test
	public void test()
	{
		List<WeightedItem<String>> Inputs = InputBuilder.CreateInputs(MinInputs, MaxInputs, MinWeight, MaxWeight);
		WeightedSelector<String> Selector = new WeightedSelector<String>();
		Selector.Add(Inputs);

		ProbabilityHelpers Helper = new ProbabilityHelpers(Selector, Inputs, Trials, AcceptableDeviation);

		System.out.println("Running " + Trials + " trials with " + Selector.getReadOnlyCollection().size() + " items (total weight: " + ExtensionMethods.totalWeight(Selector) + ")");

		Map<String, Integer> ResultCounter = Helper.RunTrialsAndCountResults();

		for(Map.Entry<String, Integer> Key : ResultCounter.entrySet())
			Helper.ExamineMetricsForKey(Key.getKey());

		assertTrue("Expected " + Inputs.size() + " outputs, actual: " + ResultCounter.entrySet().size() + ". Details: " + Helper.GetErrorMessage() + ""
		, ResultCounter.entrySet().size() == Inputs.size());
	}

}
