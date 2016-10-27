package com.github.hexocraftapi.weighted.selector.Extensions;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author <b>Hexosse</b> (<a href="https://github.com/hexosse">on GitHub</a>))
 */
public class ExtensionMethods
{
	public static <T> int count(WeightedSelector<T> weightedSelector)
	{
		return weightedSelector.getItems().size();
	}

	public static <T> int maxWeight(WeightedSelector<T> weightedSelector)
	{
		if(weightedSelector.getItems().size() == 0)
			return 0;

		List<WeightedItem<T>> items = weightedSelector.getItems();
		WeightedItem<T> item = items.get(0);
		int max = item.weight;
		for(int i = 1; i < items.size(); i++)
		{
			item = items.get(i);
			if(item.weight > max)
				max = item.weight;
		}
		return max;
	}

	public static <T> int minWeight(WeightedSelector<T> weightedSelector)
	{
		if(weightedSelector.getItems().size() == 0)
			return 0;

		List<WeightedItem<T>> items = weightedSelector.getItems();
		WeightedItem<T> item = items.get(0);
		int min = item.weight;
		for(int i = 1; i < items.size(); i++)
		{
			item = items.get(i);
			if(item.weight < min)
				min = item.weight;
		}
		return min;
	}

	public static <T> int totalWeight(WeightedSelector<T> weightedSelector)
	{
		if(weightedSelector.getItems().size() == 0)
			return 0;

		List<WeightedItem<T>> items = weightedSelector.getItems();
		int sum = 0;
		for(WeightedItem<T> item : items)
			sum += item.weight;
		return sum;
	}

	public static <T> int averageWeight(WeightedSelector<T> weightedSelector)
	{
		if(weightedSelector.getItems().size() == 0)
			return 0;

		return (int) totalWeight(weightedSelector) / count(weightedSelector);
	}

	public static <T> List<WeightedItem<T>> listByWeightDescending(WeightedSelector<T> weightedSelector)
	{
		List<WeightedItem<T>> sortedList = new ArrayList<>(weightedSelector.getItems());
		Collections.sort(sortedList, new Comparator<WeightedItem<T>>()
		{
			@Override
			public int compare(WeightedItem<T> o1, WeightedItem<T> o2)
			{
				return o2.weight - o1.weight; // Descending
			}
		});

		return sortedList;
	}

	public static <T> List<WeightedItem<T>> listByWeightAscending(WeightedSelector<T> weightedSelector)
	{
		List<WeightedItem<T>> sortedList = new ArrayList<>(weightedSelector.getItems());
		Collections.sort(sortedList, new Comparator<WeightedItem<T>>()
		{
			@Override
			public int compare(WeightedItem<T> o1, WeightedItem<T> o2)
			{
				return o1.weight - o2.weight; // Ascending
			}
		});

		return sortedList;
	}
}
