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

import com.github.hexocraftapi.weighted.selector.WeightedItem;

import java.util.List;

/**
 * @author <b>Hexosse</b> (<a href="https://github.com/hexosse">on GitHub</a>))
 */
public class BinarySearchOptimizer
{
	public static <T> int[] getCumulativeWeights(List<WeightedItem<T>> items)
	{
		// For binary search, we do some setup ahead of time here... We need to build an array of
		// cumulative weights. So if our items had weights of: 3, 5, 3, 2 the array would be: 3, 8, 11, 13

		int weight = 0;
		int index = 0;
		//int[] result = new int[items.size() + 1];
		int[] result = new int[items.size()];

		for(WeightedItem<T> Item : items)
		{
			weight += Item.weight;
			result[index] = weight;
			index++;
		}

		return result;
	}
}
