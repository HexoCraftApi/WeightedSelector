package com.github.hexocraftapi.weighted.selector.Algorithm;

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

import com.github.hexocraftapi.weighted.selector.WeightedSelector;
import com.github.hexocraftapi.weighted.selector.WeightedItem;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author <b>Hexosse</b> (<a href="https://github.com/hexosse">on GitHub</a>))
 */
abstract class SelectorBase<T>
{
	private final Random random = new Random();


	public SelectorBase()
	{
	}

	protected int seed(List<WeightedItem<T>> items)
	{
		int min = 1;
		int max = 0;
		for(WeightedItem<T> item : items)
			max += item.weight;

		// To generate a random value in range [Min,Max] use this formula
		// 		Min + ((int)nextDouble() * ((Max - Min) + 1))
		return min + (int)(random.nextDouble() * ((max - min) + 1));
	}

	// Execute selection using the binary search algorithm, which is the fastest way.
	protected WeightedItem<T> select(WeightedSelector<T> weightedSelector)
	{
		List<WeightedItem<T>> items = weightedSelector.getItems();

		if(items.size() == 0)
			throw new IllegalArgumentException("Tried to do a select, but WeightedItems was empty.");

		// Choose an item based on each item's proportion of the total weight.
		int Seed = seed(items);

		return binarySearch(weightedSelector, Seed);
	}

	// Select, and force the slower Linear Search algorithm.
	protected WeightedItem<T> selectLinear(WeightedSelector<T> weightedSelector)
	{
		List<WeightedItem<T>> items = weightedSelector.getItems();

		// Note that this is only really useful for multiselect with !allowduplicates, which removes
		// items from the list as it goes. Just haven't put the effort into getting binary search to work
		// in those conditions.

		if(items.size() == 0)
			throw new IllegalArgumentException("Tried to do a select, but WeightedItems was emtpy.");

		// Choose an item based on each item's proportion of the total weight.
		int Seed = seed(items);

		return linearSearch(weightedSelector, Seed);
	}


	private WeightedItem<T> binarySearch(WeightedSelector<T> weightedSelector, int seed)
	{
		List<WeightedItem<T>> items = weightedSelector.getItems();
		int index = Arrays.binarySearch(weightedSelector.getCumulativeWeights(), seed);

		// If there's a near match, IE our array is (1, 5, 9) and we search for 3, binarySearch
		// returns a negative number that is one less than the first index great than our search.
		if (index < 0)
			index = -index - 1;

		return items.get(index);
	}

	private WeightedItem<T> linearSearch(WeightedSelector<T> weightedSelector, int seed)
	{
		List<WeightedItem<T>> items = weightedSelector.getItems();
		int RunningCount = 0;

		for(WeightedItem<T> item : items)
		{
			RunningCount += item.weight;

			if(seed <= RunningCount)
				return item;
		}

		throw new IllegalArgumentException("There was no result during SimpleSearch. This should never happen.");
	}
}
