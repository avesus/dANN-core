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

import java.util.Map;
import java.util.Set;

public interface MutableCloudGraph<
	  	N,
	  	NE extends MutableCloudGraph.NodeEndpoint<N>,
	  	E extends Cloud<? extends Cloud.Endpoint<? extends N>>,
	  	EE extends MutableCloudGraph.EdgeEndpoint<E>
	  >  extends JoinableCloudGraph<N,NE,E,EE>, PartibleCloudGraph<NE,EE>, AssignableCloudGraph<NE,EE>, MutableCloud<N,NE>
{
	interface Endpoint<
		  	T
		  >
		  extends JoinableCloudGraph.Endpoint<T>, PartibleCloudGraph.Endpoint<T>, AssignableCloudGraph.Endpoint<T>, MutableCloud.Endpoint<T>
	{
	};

	interface NodeEndpoint<
		  	T
	  > extends JoinableCloudGraph.NodeEndpoint<T>, PartibleCloudGraph.NodeEndpoint<T>, AssignableCloudGraph.NodeEndpoint<T>, Endpoint<T>
	{
	};

	interface EdgeEndpoint<
		  	T extends Cloud<?>
		> extends JoinableCloudGraph.EdgeEndpoint<T>, PartibleCloudGraph.EdgeEndpoint<T>, AssignableCloudGraph.EdgeEndpoint<T>, Endpoint<T>
	{
	};

	EndpointSets<NE,EE> reconfigure(Set<? extends N> addNodes, Set<? extends E> addEdges, Set<? extends Endpoint<?>> disconnectEndpoints) throws InvalidGraphException;
	EndpointSets<NE,EE> reconfigure(Map<? extends N,? extends Integer> addNodes, Map<? extends E,? extends Integer> addEdges, Set<? extends Endpoint<?>> disconnectEndpoints) throws InvalidGraphException;
}