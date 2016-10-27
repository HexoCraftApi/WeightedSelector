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

import java.util.ArrayList;
import java.util.List;

/**
 * @author <b>Hexosse</b> (<a href="https://github.com/hexosse">on GitHub</a>))
 */
public class MultiSelector<T> extends SelectorBase<T>
{
	private final WeightedSelector<T> weightedSelector;


	public MultiSelector(WeightedSelector<T> weightedSelector)
	{
		this.weightedSelector = weightedSelector;
	}


	public List<T> select(int count)
	{
		validate(count);

		// Create a shallow clone of the our items, because we're going to be removing
		// items from the list in some cases, and we want to preserve the original.
		List<WeightedItem<T>> items = new ArrayList<>(weightedSelector.getItems());
		List<T> resultList = new ArrayList<>();

		do
		{
			WeightedItem<T> item = null;

			//Use binary search if we can.
			if(weightedSelector.getOptions().isDuplicatesAllowed())
				item = select(weightedSelector);
			//Force linear search, since AllowDuplicates currently breaks binary search.
			else
				item = selectLinear(weightedSelector);

			resultList.add(item.value);

			if(!weightedSelector.getOptions().isDuplicatesAllowed())
				items.remove(item);
		}
		while(resultList.size() < count);

		return resultList;
	}

	private void validate(int count)
	{
		if(count <= 0)
			throw new IllegalArgumentException("Count must be > 0.");

		List<WeightedItem<T>> items = weightedSelector.getItems();

		if(items.size() == 0)
			throw new IllegalArgumentException("There were no items to select from.");

		if(!weightedSelector.getOptions().isDuplicatesAllowed() && items.size() < count)
			throw new IllegalArgumentException("There aren't enough items in the collection to take " + count);
	}
}
