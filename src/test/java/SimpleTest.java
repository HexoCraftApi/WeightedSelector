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
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author <b>Hexosse</b> (<a href="https://github.com/hexosse">on GitHub</a>))
 */

public class SimpleTest
{
	@Test
	public void test()
	{
		WeightedSelector<Integer> Selector = new WeightedSelector<Integer>();

		Selector.Add(1, 1);
		int Result1 = Selector.Select();

		//There's only one choice - 1. It will always return.
		assertTrue(Result1 == 1);

		// Now re-use the same selector, but put an item in with so much weight that it will
		// always "win".
		Selector.Add(2, 5000000); //That's a heavy item.
		int Result2 = Selector.Select();

		assertTrue(Selector.getReadOnlyCollection().size() == 2);
		assertTrue(Result2 == 2);
	}

}
