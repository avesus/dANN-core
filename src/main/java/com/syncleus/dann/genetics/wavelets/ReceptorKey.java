/******************************************************************************
 *                                                                             *
 *  Copyright: (c) Syncleus, Inc.                                              *
 *                                                                             *
 *  You may redistribute and modify this source code under the terms and       *
 *  conditions of the Open Source Community License - Type C version 1.0       *
 *  or any later version as published by Syncleus, Inc. at www.syncleus.com.   *
 *  There should be a copy of the license included with this file. If a copy   *
 *  of the license is not included you are granted no right to distribute or   *
 *  otherwise use this file except through a legal and valid license. You      *
 *  should also contact Syncleus, Inc. at the information below if you cannot  *
 *  find a license:                                                            *
 *                                                                             *
 *  Syncleus, Inc.                                                             *
 *  2604 South 12th Street                                                     *
 *  Philadelphia, PA 19148                                                     *
 *                                                                             *
 ******************************************************************************/
package com.syncleus.dann.genetics.wavelets;

import java.util.Map;
import java.util.Map.Entry;

public class ReceptorKey extends AbstractKey
{
	public ReceptorKey()
	{
		super();
	}

	public ReceptorKey(final AbstractKey copy)
	{
		super(copy);
	}

	public ReceptorKey(final Map<Integer, Boolean> points)
	{
		super(points);
	}

	public ReceptorKey(final String keyString)
	{
		super(keyString);
	}

	public boolean binds(final SignalKey signal)
	{
		if( signal.getPoints().size() < this.getPoints().size() )
			return false;

		boolean matching;
		for(final Integer offsetPoint : signal.getPoints().keySet())
		{
			matching = true;
			Integer offset = null;
			for(final Entry<Integer, Boolean> point : this.getPoints().entrySet())
			{
				if( offset == null )
					offset = offsetPoint - point.getKey();

				final Boolean bindingValue = signal.getPoints().get(point.getKey() + offset);
				if( bindingValue == null )
				{
					matching = false;
					break;
				}
				else if( bindingValue.booleanValue() != point.getValue().booleanValue() )
				{
					matching = false;
					break;
				}
			}

			if( matching )
				return true;
		}

		return false;
	}

	@Override
	public ReceptorKey clone()
	{
		return (ReceptorKey) super.clone();
	}

	@Override
	public ReceptorKey mutate(final double deviation)
	{
		return (ReceptorKey) super.mutate(deviation);
	}
}
