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

/**
 * An AbstractDirectedAdjacencyGraph is a DirectedGraph implemented using adjacency lists.
 *
 * @since 2.0
 * @param <N> The node type
 * @param <E> The type of edge for the given node type
 */
public abstract class AbstractDirectedAdjacencyGraph<N, E extends DirectedEdge<N>> extends AbstractBidirectedAdjacencyGraph<N, E> implements DirectedGraph<N, E>
{
    /**
     * Creates a new graph with no edges and no adjacencies.
     * nodeContext and edgeContext is enabled.
     */
	protected AbstractDirectedAdjacencyGraph()
	{
		super();
	}

    /**
     * Creates a new graph as a copy of the current Graph.
     * nodeContext is enabled.
     * @param copyGraph The Graph to copy
     */
	protected AbstractDirectedAdjacencyGraph(final Graph<N, E> copyGraph)
	{
		super(copyGraph.getNodes(), copyGraph.getEdges());
	}

    /**
     * Creates a new graph from the given list of nodes, and
     * the given list of Edges.
     * The adjacency lists are created from this structure. nodeContext is
     * enabled.
     *
     * @param nodes The set of all nodes
     * @param edges The set of all ourEdges
     */
	protected AbstractDirectedAdjacencyGraph(final Set<N> nodes, final Set<E> edges)
	{
		super(nodes, edges);
	}

}
