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

import java.util.Set;

public abstract class AbstractHypergraph<
	  	NE extends Hypergraph.NodeEndpoint<?>,
	  	EE extends Hypergraph.EdgeEndpoint<? extends Cloud<?>>> extends AbstractCloudGraph<NE,EE> implements Hypergraph<NE,EE>
{

	/**
	 * This will always return false.
	 *
	 * @return always returns false
	 */
	@Override
	public boolean hasMaximumAllowableRank()
	{
		return false;
	}

	/**
	 * Always returns -1 since rank is not limited.
	 *
	 * @return Always returns -1
	 */
	@Override
	public int getMaximumAllowableRank()
	{
		return -1;
	}

	/**
	 * Always returns false since rank is not limited.
	 *
	 * @return Always returns false
	 */
	@Override
	public boolean hasMinimumAllowableRank()
	{
		return false;
	}

	/**
	 * Always returns -1 since rank is not limited.
	 * @return always -1 since the rank is not limited.
	 */
	@Override
	public int getMinimumAllowableRank()
	{
		return -1;
	}

	@Override
	protected AbstractHypergraph<NE, EE> clone()
	{
		return (AbstractHypergraph<NE, EE>) super.clone();
	}
}
