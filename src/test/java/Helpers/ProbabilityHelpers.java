package Helpers;

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

import com.github.hexocraftapi.weighted.selector.Extensions.ExtensionMethods;
import com.github.hexocraftapi.weighted.selector.WeightedItem;
import com.github.hexocraftapi.weighted.selector.WeightedSelector;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * @author <b>Hexosse</b> (<a href="https://github.com/hexosse">on GitHub</a>))
 */
public class ProbabilityHelpers
{

	private Map<String, Integer>       ResultCounter;
	private List<WeightedItem<String>> Inputs;
	private WeightedSelector<String>   Selector;
	private int                        Trials;
	private int                        AcceptableDeviation;

	public ProbabilityHelpers(WeightedSelector<String> selector, List<WeightedItem<String>> inputs, int trials, int acceptableDeviation)
	{
		this.Selector = selector;
		this.Inputs = inputs;
		this.Trials = trials;
		this.AcceptableDeviation = acceptableDeviation;
	}

	public Map<String, Integer> RunTrialsAndCountResults()
	{
		//Do [trials] selections, and dump the number of hits for each item into a dictionary.
		Map<String, Integer> Results = new HashMap();

		for(int i = 0; i < Trials; i++)
		{
			String Decision = Selector.Select();

			if(!Results.containsKey(Decision))
				Results.put(Decision, 1);
			else
				Results.put(Decision, Results.get(Decision) + 1);
		}

		this.ResultCounter = Results;

		return Results;
	}

	public static double round(double value, int places)
	{
		if(places < 0) throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public void ExamineMetricsForKey(String key)
	{
		double WeightProportion = GetWeightProportion(key);
		double SelectionProportion = GetSelectionProportion(key);

		System.out.println(key);
		System.out.println("     Hits: " + ResultCounter.get(key) + " (" + round(SelectionProportion, 3) + "% of total)     Weight " + GetWeight(key) + " (" + round(WeightProportion, 3) + "% of total)");

		assertTrue(	"Expected between " + Double.toString(SelectionProportion - AcceptableDeviation) + "% and " + Double.toString(SelectionProportion + AcceptableDeviation) + "%. Actual: " + Double.toString(WeightProportion) + "%"
					,WeightProportion >= (SelectionProportion - AcceptableDeviation)
					   && WeightProportion <= (SelectionProportion + AcceptableDeviation));
	}

	private double GetWeightProportion(String key)
	{
		//What % of the total weight does this key's weight represent?
		return ((double)ResultCounter.get(key) / Trials) * 100;
	}

	private double GetSelectionProportion(String key)
	{
		//Over all of our tests, how many times did we select this key?
		int Total = ExtensionMethods.totalWeight(Selector);

		for(WeightedItem<String> item : Selector.getReadOnlyCollection())
		{
			if(item.value==key)
				return ((double)item.weight / Total) * 100;
		}

		return 0;
	}

	private double GetWeight(String key)
	{
		for(WeightedItem<String> item : Selector.getReadOnlyCollection())
		{
			if(item.value==key)
				return item.weight;
		}

		return 0;
	}


	public String GetErrorMessage()
	{
		//If an item didn't generate any hits at all, it won't show up in ResultCounter. This grabs some details.
		StringBuilder Builder = new StringBuilder();

		for(WeightedItem<String> Key : Inputs)
		{
			if (!ResultCounter.containsKey(Key.value))
				Builder.append("Missing "+Key.value+", Weight: "+Key.weight+"\r");
		}

		return Builder.toString();
	}

}
