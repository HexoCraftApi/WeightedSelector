package com.github.hexocraftapi.weighted.selector;

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

import com.github.hexocraftapi.weighted.selector.Algorithm.BinarySearchOptimizer;
import com.github.hexocraftapi.weighted.selector.Algorithm.MultiSelector;
import com.github.hexocraftapi.weighted.selector.Algorithm.SingleSelector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author <b>Hexosse</b> (<a href="https://github.com/hexosse">on GitHub</a>))
 */
public class WeightedSelector<T>
{
	private final List<WeightedItem<T>> Items = new ArrayList<WeightedItem<T>>();
	private final SelectorOptions Options;

	private int[] CumulativeWeights = null;    		//used for binary search.
	private Boolean IsCumulativeWeightsStale;       //forces recalc of CumulativeWeights any time our list of WeightedItems changes.


	public SelectorOptions getOptions() { return Options; }
	public int[] getCumulativeWeights() { return CumulativeWeights; }
	public List<WeightedItem<T>> getItems() { return Items; }


	public WeightedSelector()
	{
		this(null);
	}

	public WeightedSelector(SelectorOptions options)
	{
		this.Options = options!=null ? options :  new SelectorOptions();
		this.IsCumulativeWeightsStale = false;
	}


	public void Add(WeightedItem<T> item)
	{
		if(item.weight <= 0)
		{
			if(Options.isDropZeroWeightItems())
				return;
			else
				throw new IllegalArgumentException("Scores must be => 0.");
		}

		IsCumulativeWeightsStale = true;
		Items.add(item);
	}

	public void Add(List<WeightedItem<T>> items)
	{
		for(WeightedItem<T> Item : items)
			this.Add(Item);
	}

	public void Add(T item, int weight)
	{
		this.Add(new WeightedItem<T>(item, weight));
	}

	public void Remove(WeightedItem<T> item)
	{
		IsCumulativeWeightsStale = true;
		Items.remove(item);
	}


	// Execute the selection algorithm, returning one result.
	public T Select()
	{
		CalculateCumulativeWeights();

		SingleSelector<T> Selector = new SingleSelector<T>(this);
		return Selector.select();
	}

	// Execute the selection algorithm, returning multiple results.
	public List<T> SelectMultiple(int count)
	{
		CalculateCumulativeWeights();

		MultiSelector<T> Selector = new MultiSelector<T>(this);
		return Selector.select(count);
	}

	private void CalculateCumulativeWeights()
	{
		if (!IsCumulativeWeightsStale) //If it's not stale, we can skip this!
			return;

		IsCumulativeWeightsStale = false;
		CumulativeWeights = BinarySearchOptimizer.getCumulativeWeights(Items);
	}

	public List<WeightedItem<T>> getReadOnlyCollection()
	{
		List<WeightedItem<T>> readOnly = new ArrayList<>(Items);
		return Collections.unmodifiableList(readOnly);
	}

}
