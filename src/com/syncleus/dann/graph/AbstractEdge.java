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
package com.syncleus.dann.graph;

import java.io.StringWriter;
import java.util.*;

public abstract class AbstractEdge<N> implements Edge<N>
{
	private final List<N> nodes;

	protected AbstractEdge(final List<N> nodes)
	{
		this.nodes = Collections.unmodifiableList(new ArrayList<N>(nodes));
	}

	protected AbstractEdge(final N... nodes)
	{
		final List<N> newNodes = new ArrayList<N>();
		for(N node : nodes)
			newNodes.add(node);
		this.nodes = Collections.unmodifiableList(newNodes);
	}

	public boolean isTraversable(final N node)
	{
		return (! this.getTraversableNodes(node).isEmpty());
	}

	public final List<N> getNodes()
	{
		return this.nodes;
	}

	@Override
	public String toString()
	{
		StringBuilder outString = null;
		for(N node : this.nodes)
		{
			if(outString == null)
				outString = new StringBuilder(node.toString());
			else
				outString.append(":" + node);
		}
		return outString.toString();
	}
}
