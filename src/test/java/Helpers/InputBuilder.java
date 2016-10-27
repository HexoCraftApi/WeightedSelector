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

import com.github.hexocraftapi.weighted.selector.WeightedItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author <b>Hexosse</b> (<a href="https://github.com/hexosse">on GitHub</a>))
 */
public class InputBuilder
{
	public static Random random = new Random();


	public static int random(int min, int max)
	{
		return min + (int)(random.nextDouble() * ((max - min) + 1));
	}

	public static List<WeightedItem<String>> CreateInputs(int minInputs, int maxInputs, int minWeight, int maxWeight)
	{
		int InputCount = random(minInputs, maxInputs);
		List<WeightedItem<String>> Result = new ArrayList<>();

		for (int i = 1; i <= InputCount; i++)
		{
			WeightedItem<String> Item = new WeightedItem<String>(GetInputName(), random(minWeight, maxWeight));
			Result.add(Item);
		}

		return Result;
	}

	private static String GetInputName()
	{
		return UUID.randomUUID().toString();
	}
}
