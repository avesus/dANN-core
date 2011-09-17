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
import com.sun.xml.internal.org.jvnet.fastinfoset.ExternalVocabulary;

/**
 * Represents a graph as a collection of nodes connected by edges. A graph does
 * not need to contain any nodes or edges however if there is at least one edge
 * then there must be at least one node. There can, however, be one or more
 * nodes with no edges present. Each edge must have 2 or more nodes it connects,
 * however they do not need to be different nodes. The implementation defines if
 * and how a graph can be traversed across nodes and edges.
 *
 * @author Jeffrey Phillips Freeman
 * @since 2.0
 * @param <N> The node type
 * @param <E> The type of edge for the given node type
 */
public interface Graph<
	  	A,
	  	N,
	  	E extends Cloud<N,? extends Cloud.Endpoint<? extends N>>,
	  	AE extends Graph.Endpoint<A, N, E>,
	  	NE extends Graph.NodeEndpoint<N, E>,
	  	EE extends Graph.EdgeEndpoint<N, E>
	  > extends Cloud<A,AE>
{
	interface Endpoint<
			  T,
			  N,
			  E extends Cloud<N,? extends Cloud.Endpoint<? extends N>>
		  >
		  extends Cloud.Endpoint<T>
	{
		Set<? extends Graph.Endpoint<?, N,E>> getAdjacent();
		Set<? extends Graph.Endpoint<?, N,E>> getTraversableAdjacentTo();
		Set<? extends Graph.Endpoint<?, N,E>> getTraversableAdjacentFrom();

		Set<? extends Graph.NodeEndpoint<N, E>> getAdjacentNodes();
		Set<? extends Graph.NodeEndpoint<N, E>> getTraversableAdjacentNodesTo();
		Set<? extends Graph.NodeEndpoint<N, E>> getTraversableAdjacentNodesFrom();

		Set<? extends Graph.EdgeEndpoint<N, E>> getAdjacentEdges();
		Set<? extends Graph.EdgeEndpoint<N, E>> getTraversableAdjacentEdgesTo();
		Set<? extends Graph.EdgeEndpoint<N, E>> getTraversableAdjacentEdgesFrom();
	};

	interface NodeEndpoint<
		  N,
		  E extends Cloud<N,? extends Cloud.Endpoint<? extends N>>
	  > extends Graph.Endpoint<N, N, E>
	{
	};

	interface EdgeEndpoint<
		  	N,
		  	E extends Cloud<N,? extends Cloud.Endpoint<? extends N>>
		> extends Graph.Endpoint<E, N, E>
	{
	};

	Set<EE> getEdgeEndpoints();
	Set<EE> getEdgeEndpoints(Cloud<?,? extends Cloud.Endpoint<?>> cloud);

	Set<NE> getNodeEndpoints();
	Set<NE> getNodeEndpoints(Object node);

	/**
	 * Get a set of all nodes in the graph.
	 *
	 * @return An unmodifiable set of all nodes in the graph.
	 * @since 2.0
	 */
	Set<N> getNodes();
	/**
	 * Get a set of all edges in the graph. Two edges in the set, and in the graph,
	 * may have the same end points unless equals in the edges used by this graph
	 * restrict that possiblity.
	 *
	 * @return An unmodifiable set of a all edges in the graph.
	 * @since 2.0
	 */
	Set<E> getEdges();
	/**
	 * Get a list of all nodes adjacent to the specified node. All edges connected
	 * to this node has its other end points added to the list returned. The
	 * specified node itself will appear in the list once for every loop. If there
	 * are multiple edges connecting node with a particular end point it will
	 * appear multiple times in the list, once for each hop to the end point.
	 *
	 * @param node The whose neighbors are to be returned.
	 * @return A list of all nodes adjacent to the specified node, empty set if the
	 *         node has no edges.
	 * @since 2.0
	 */
	Set<N> getAdjacentNodes(Object node);
	/**
	 * Get a set of all edges which is connected to node (adjacent). You may not be
	 * able to traverse from the specified node to all of these edges returned. If
	 * you only want edges you can traverse then see getTraversableAdjacentEdges.
	 *
	 * @param node the end point for all edges to retrieve.
	 * @return An unmodifiable set of all edges that has node as an end point.
	 * @throws IllegalArgumentException if specified node is not in the graph.
	 * @since 2.0
	 */
	Set<E> getAdjacentEdges(Object node);

	Set<E> getTraversableEdgesFrom(Object source);
	Set<E> getTraversableEdgesFrom(Cloud<?,? extends Cloud.Endpoint<?>> source);
	Set<E> getTraversableEdgesTo(Object destination);
	Set<E> getTraversableEdgesTo(Cloud<?,? extends Cloud.Endpoint<?>> destination);

	Set<N> getTraversableNodesFrom(Object source);
	Set<N> getTraversableNodesFrom(Cloud<?,? extends Cloud.Endpoint<?>> source);
	Set<N> getTraversableNodesTo(Object destination);
	Set<N> getTraversableNodesTo(Cloud<?,? extends Cloud.Endpoint<?>> destination);

	Set<E> getTraversableAdjacentEdgesFrom(Object source);
	Set<E> getTraversableAdjacentEdgesFrom(Cloud<?,? extends Cloud.Endpoint<?>> source);
	Set<E> getTraversableAdjacentEdgesTo(Object destination);
	Set<E> getTraversableAdjacentEdgesTo(Cloud<?,? extends Cloud.Endpoint<?>> destination);

	Set<N> getTraversableAdjacentNodesFrom(Object source);
	Set<N> getTraversableAdjacentNodesFrom(Cloud<?,? extends Cloud.Endpoint<?>> source);

	/**
	 * Get a list of all reachable nodes adjacent to node. All edges connected to
	 * node and is traversable from node will have its destination node(s) added to
	 * the returned list. node itself will appear in the list once for every loop.
	 * If there are multiple edges connecting node with a particular end point then
	 * the end point will appear multiple times in the list, once for each hop to
	 * the end point.
	 *
	 * @param destination The whose traversable neighbors are to be returned.
	 * @return A list of all nodes adjacent to the specified node and traversable
	 *         from the spevified node, empty set if the node has no edges.
	 * @since 2.0
	 */
	Set<N> getTraversableAdjacentNodesTo(Object destination);

	/**
	 * Get a set of all edges which you can traverse from node. Of course node will
	 * always be an end point for each edge returned. Throws an
	 * IllegalArgumentException if node is not in the graph.
	 *
	 * @param destination edges returned will be traversable from this node.
	 * @return An unmodifiable set of all edges that can be traversed from node.
	 * @since 2.0
	 */
	Set<N> getTraversableAdjacentNodesTo(Cloud<?,? extends Cloud.Endpoint<?>> destination);
}
