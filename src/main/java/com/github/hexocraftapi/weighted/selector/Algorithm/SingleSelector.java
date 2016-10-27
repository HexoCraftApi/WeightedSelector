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

/**
 * @author <b>Hexosse</b> (<a href="https://github.com/hexosse">on GitHub</a>))
 */
public class SingleSelector<T> extends SelectorBase<T>
{
	private final WeightedSelector<T> weightedSelector;


	public SingleSelector(WeightedSelector<T> weightedSelector)
	{
		this.weightedSelector = weightedSelector;
	}


	public T select()
	{
		return select(this.weightedSelector).value;
	}
}
