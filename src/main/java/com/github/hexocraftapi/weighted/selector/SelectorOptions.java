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

/**
 * @author <b>Hexosse</b> (<a href="https://github.com/hexosse">on GitHub</a>))
 */
public class SelectorOptions
{
	// This only impacts MultiSelect. Turning it on will allow result set to contain duplicates, otherwise
	// we will never return the same item twice.
	private boolean duplicatesAllowed = false;

	// If this is false and you add an item with a weight of zero or less, that will throw an exception. If it's true,
	// the item will just be ignored (not added). This is often convenient.
	private boolean dropZeroWeightItems = false;

	public SelectorOptions()
	{
	}

	public boolean isDuplicatesAllowed() { return duplicatesAllowed; }
	public void setDuplicatesAllowed(boolean duplicatesAllowed) { this.duplicatesAllowed = duplicatesAllowed; }

	public boolean isDropZeroWeightItems() { return dropZeroWeightItems; }
	public void setDropZeroWeightItems(boolean dropZeroWeightItems) { this.dropZeroWeightItems = dropZeroWeightItems; }
}
