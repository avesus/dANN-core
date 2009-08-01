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
package com.syncleus.dann.graph.hyperassociativemap;
import com.syncleus.dann.math.Hyperpoint;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * Represents a collection of interconnected hyperassociative map nodes.
 *
 *
 * @author Syncleus, Inc.
 * @since 1.0
 *
 */
public abstract class AbstractHyperassociativeMap implements Serializable
{
	/**
	 * HashSet of all the nodes in this map.
	 *
	 * @since 1.0
	 */
    protected HashSet<HyperassociativeNode> nodes = new HashSet<HyperassociativeNode>();

	private int dimensions;
	private ThreadPoolExecutor threadExecutor;

	private static class Align implements Callable<Hyperpoint>
	{
		private HyperassociativeNode node;

		public Align(HyperassociativeNode node)
		{
			this.node = node;
		}

		public Hyperpoint call()
		{
			this.node.align();
			return this.node.getLocation();
		}
	}

	/**
	 * Initializes a HyperassociativeMap of the specified dimensions.
	 *
	 *
	 * @param dimensions The number of dimensions for this map.
	 * @param threadExecutor the threadExecutor used to manage threads.
	 * @since 1.0
	 */
	public AbstractHyperassociativeMap(int dimensions, ThreadPoolExecutor threadExecutor)
	{
		this.dimensions = dimensions;
		this.threadExecutor = threadExecutor;
	}

	/**
	 * Initializes a HyperassociativeMap of the specified dimensions.
	 *
	 *
	 * @param dimensions The number of dimensions for this map.
	 * @since 1.0
	 */
	public AbstractHyperassociativeMap(int dimensions)
	{
		this.dimensions = dimensions;
		this.threadExecutor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().availableProcessors()*5, 20, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
	}

	/**
	 * Gets the number of dimensions for this map.
	 *
	 *
	 * @return The number of dimensions for this map.
	 * @since 1.0
	 */
	public final int getDimensions()
	{
		return this.dimensions;
	}

	/**
	 * Gets all the nodes contained within this map.
	 *
	 *
	 * @return An unmodifiable Set of all the nodes in this map.
	 * @since 1.0
	 */
    public final Set<HyperassociativeNode> getNodes()
    {
        return Collections.unmodifiableSet(this.nodes);
    }

	/**
	 * Aligns all the nodes in this map by a single step.
	 *
	 *
	 * @since 1.0
	 */
    public void align()
    {
		//align all nodes in parallel
		ArrayList<Future<Hyperpoint>> futures = new ArrayList<Future<Hyperpoint>>();
		for(HyperassociativeNode node : this.nodes)
			futures.add(this.threadExecutor.submit(new Align(node)));

		//wait for all nodes to finish aligning and calculate new center point
		Hyperpoint center = new Hyperpoint(this.dimensions);
		try
		{
			for(Future<Hyperpoint> future : futures)
			{
				Hyperpoint newPoint = future.get();
				for(int dimensionIndex = 1; dimensionIndex <= this.dimensions; dimensionIndex++)
					center.setCoordinate(center.getCoordinate(dimensionIndex) + newPoint.getCoordinate(dimensionIndex), dimensionIndex);
			}
		}
		catch(InterruptedException caughtException)
		{
			throw new AssertionError("Unexpected interuption. Get should block indefinately");
		}
		catch(ExecutionException caughtException)
		{
			throw new AssertionError("Unexpected execution exception. Get should block indefinately");
		}

		for(int dimensionIndex = 1; dimensionIndex <= this.dimensions; dimensionIndex++)
			center.setCoordinate(center.getCoordinate(dimensionIndex)/((double)this.nodes.size()),dimensionIndex);

		for(HyperassociativeNode node : nodes)
		{
			node.recenter(center);
		}
    }
}