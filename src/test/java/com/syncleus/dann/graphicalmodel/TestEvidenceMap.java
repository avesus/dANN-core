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
package com.syncleus.dann.graphicalmodel;

import java.util.*;
import com.syncleus.dann.graphicalmodel.bayesian.MutableBayesianAdjacencyNetwork;
import org.junit.*;

public class TestEvidenceMap
{
	private static enum TestEnum
	{
		TRUE, FALSE
	}

	@Test
	public void testOverall()
	{
		final MutableBayesianAdjacencyNetwork network = new MutableBayesianAdjacencyNetwork();
		final GraphicalModelNode<TestEnum> influence1 = new SimpleGraphicalModelNode<TestEnum>(TestEnum.FALSE);
		final GraphicalModelNode<TestEnum> influence2 = new SimpleGraphicalModelNode<TestEnum>(TestEnum.FALSE);
		final GraphicalModelNode<TestEnum> influence3 = new SimpleGraphicalModelNode<TestEnum>(TestEnum.FALSE);

		final Set<GraphicalModelNode> nodes = new HashSet<GraphicalModelNode>();
		nodes.add(influence1);
		nodes.add(influence2);
		nodes.add(influence3);

		final EvidenceMap<TestEnum> evidence = new EvidenceMap<TestEnum>(nodes);

		//train some values
		influence1.setState(TestEnum.TRUE);
		influence2.setState(TestEnum.TRUE);
		influence3.setState(TestEnum.TRUE);
		evidence.incrementState(nodes, TestEnum.FALSE);
		evidence.incrementState(nodes, TestEnum.FALSE);
		evidence.incrementState(nodes, TestEnum.FALSE);
		evidence.incrementState(nodes, TestEnum.TRUE);

		influence1.setState(TestEnum.TRUE);
		influence2.setState(TestEnum.FALSE);
		influence3.setState(TestEnum.TRUE);
		evidence.incrementState(nodes, TestEnum.FALSE);
		evidence.incrementState(nodes, TestEnum.FALSE);
		evidence.incrementState(nodes, TestEnum.FALSE);
		evidence.incrementState(nodes, TestEnum.TRUE);

		influence1.setState(TestEnum.FALSE);
		influence2.setState(TestEnum.TRUE);
		influence3.setState(TestEnum.TRUE);
		evidence.incrementState(nodes, TestEnum.FALSE);
		evidence.incrementState(nodes, TestEnum.FALSE);
		evidence.incrementState(nodes, TestEnum.FALSE);
		evidence.incrementState(nodes, TestEnum.TRUE);

		influence1.setState(TestEnum.TRUE);
		influence2.setState(TestEnum.TRUE);
		influence3.setState(TestEnum.FALSE);
		evidence.incrementState(nodes, TestEnum.FALSE);
		evidence.incrementState(nodes, TestEnum.FALSE);
		evidence.incrementState(nodes, TestEnum.FALSE);
		evidence.incrementState(nodes, TestEnum.TRUE);

		influence1.setState(TestEnum.TRUE);
		influence2.setState(TestEnum.FALSE);
		influence3.setState(TestEnum.FALSE);
		evidence.incrementState(nodes, TestEnum.FALSE);
		evidence.incrementState(nodes, TestEnum.TRUE);
		evidence.incrementState(nodes, TestEnum.TRUE);
		evidence.incrementState(nodes, TestEnum.TRUE);

		influence1.setState(TestEnum.FALSE);
		influence2.setState(TestEnum.TRUE);
		influence3.setState(TestEnum.FALSE);
		evidence.incrementState(nodes, TestEnum.FALSE);
		evidence.incrementState(nodes, TestEnum.TRUE);
		evidence.incrementState(nodes, TestEnum.TRUE);
		evidence.incrementState(nodes, TestEnum.TRUE);

		influence1.setState(TestEnum.FALSE);
		influence2.setState(TestEnum.FALSE);
		influence3.setState(TestEnum.TRUE);
		evidence.incrementState(nodes, TestEnum.FALSE);
		evidence.incrementState(nodes, TestEnum.TRUE);
		evidence.incrementState(nodes, TestEnum.TRUE);
		evidence.incrementState(nodes, TestEnum.TRUE);

		influence1.setState(TestEnum.FALSE);
		influence2.setState(TestEnum.FALSE);
		influence3.setState(TestEnum.FALSE);
		evidence.incrementState(nodes, TestEnum.FALSE);
		evidence.incrementState(nodes, TestEnum.TRUE);
		evidence.incrementState(nodes, TestEnum.TRUE);
		evidence.incrementState(nodes, TestEnum.TRUE);

		//test thevalues
		influence1.setState(TestEnum.FALSE);
		influence2.setState(TestEnum.FALSE);
		influence3.setState(TestEnum.FALSE);
		StateEvidence<TestEnum> stateEvidence = evidence.get(nodes);
		Assert.assertTrue("incorrect percentage!", Math.abs(stateEvidence.getPercentage(TestEnum.TRUE) - 0.75) < 0.0001);
		Assert.assertTrue("incorrect percentage!", Math.abs(stateEvidence.getPercentage(TestEnum.FALSE) - 0.25) < 0.0001);
		Assert.assertTrue("incorrect total evidence!", stateEvidence.getTotalEvidence() == 4);
		Assert.assertTrue("incorrect individual evidence!", stateEvidence.get(TestEnum.TRUE) == 3);
		Assert.assertTrue("incorrect individual evidence!", stateEvidence.get(TestEnum.FALSE) == 1);

		influence1.setState(TestEnum.TRUE);
		influence2.setState(TestEnum.FALSE);
		influence3.setState(TestEnum.FALSE);
		stateEvidence = evidence.get(nodes);
		Assert.assertTrue("incorrect percentage!", Math.abs(stateEvidence.getPercentage(TestEnum.TRUE) - 0.75) < 0.0001);
		Assert.assertTrue("incorrect percentage!", Math.abs(stateEvidence.getPercentage(TestEnum.FALSE) - 0.25) < 0.0001);
		Assert.assertTrue("incorrect total evidence!", stateEvidence.getTotalEvidence() == 4);
		Assert.assertTrue("incorrect individual evidence!", stateEvidence.get(TestEnum.TRUE) == 3);
		Assert.assertTrue("incorrect individual evidence!", stateEvidence.get(TestEnum.FALSE) == 1);

		influence1.setState(TestEnum.FALSE);
		influence2.setState(TestEnum.TRUE);
		influence3.setState(TestEnum.FALSE);
		stateEvidence = evidence.get(nodes);
		Assert.assertTrue("incorrect percentage!", Math.abs(stateEvidence.getPercentage(TestEnum.TRUE) - 0.75) < 0.0001);
		Assert.assertTrue("incorrect percentage!", Math.abs(stateEvidence.getPercentage(TestEnum.FALSE) - 0.25) < 0.0001);
		Assert.assertTrue("incorrect total evidence!", stateEvidence.getTotalEvidence() == 4);
		Assert.assertTrue("incorrect individual evidence!", stateEvidence.get(TestEnum.TRUE) == 3);
		Assert.assertTrue("incorrect individual evidence!", stateEvidence.get(TestEnum.FALSE) == 1);

		influence1.setState(TestEnum.FALSE);
		influence2.setState(TestEnum.FALSE);
		influence3.setState(TestEnum.TRUE);
		stateEvidence = evidence.get(nodes);
		Assert.assertTrue("incorrect percentage!", Math.abs(stateEvidence.getPercentage(TestEnum.TRUE) - 0.75) < 0.0001);
		Assert.assertTrue("incorrect percentage!", Math.abs(stateEvidence.getPercentage(TestEnum.FALSE) - 0.25) < 0.0001);
		Assert.assertTrue("incorrect total evidence!", stateEvidence.getTotalEvidence() == 4);
		Assert.assertTrue("incorrect individual evidence!", stateEvidence.get(TestEnum.TRUE) == 3);
		Assert.assertTrue("incorrect individual evidence!", stateEvidence.get(TestEnum.FALSE) == 1);



		influence1.setState(TestEnum.FALSE);
		influence2.setState(TestEnum.TRUE);
		influence3.setState(TestEnum.TRUE);
		stateEvidence = evidence.get(nodes);
		Assert.assertTrue("incorrect percentage!", Math.abs(stateEvidence.getPercentage(TestEnum.TRUE) - 0.25) < 0.0001);
		Assert.assertTrue("incorrect percentage!", Math.abs(stateEvidence.getPercentage(TestEnum.FALSE) - 0.75) < 0.0001);
		Assert.assertTrue("incorrect total evidence!", stateEvidence.getTotalEvidence() == 4);
		Assert.assertTrue("incorrect individual evidence!", stateEvidence.get(TestEnum.TRUE) == 1);
		Assert.assertTrue("incorrect individual evidence!", stateEvidence.get(TestEnum.FALSE) == 3);

		influence1.setState(TestEnum.TRUE);
		influence2.setState(TestEnum.FALSE);
		influence3.setState(TestEnum.TRUE);
		stateEvidence = evidence.get(nodes);
		Assert.assertTrue("incorrect percentage!", Math.abs(stateEvidence.getPercentage(TestEnum.TRUE) - 0.25) < 0.0001);
		Assert.assertTrue("incorrect percentage!", Math.abs(stateEvidence.getPercentage(TestEnum.FALSE) - 0.75) < 0.0001);
		Assert.assertTrue("incorrect total evidence!", stateEvidence.getTotalEvidence() == 4);
		Assert.assertTrue("incorrect individual evidence!", stateEvidence.get(TestEnum.TRUE) == 1);
		Assert.assertTrue("incorrect individual evidence!", stateEvidence.get(TestEnum.FALSE) == 3);

		influence1.setState(TestEnum.TRUE);
		influence2.setState(TestEnum.TRUE);
		influence3.setState(TestEnum.FALSE);
		stateEvidence = evidence.get(nodes);
		Assert.assertTrue("incorrect percentage!", Math.abs(stateEvidence.getPercentage(TestEnum.TRUE) - 0.25) < 0.0001);
		Assert.assertTrue("incorrect percentage!", Math.abs(stateEvidence.getPercentage(TestEnum.FALSE) - 0.75) < 0.0001);
		Assert.assertTrue("incorrect total evidence!", stateEvidence.getTotalEvidence() == 4);
		Assert.assertTrue("incorrect individual evidence!", stateEvidence.get(TestEnum.TRUE) == 1);
		Assert.assertTrue("incorrect individual evidence!", stateEvidence.get(TestEnum.FALSE) == 3);

		influence1.setState(TestEnum.TRUE);
		influence2.setState(TestEnum.TRUE);
		influence3.setState(TestEnum.TRUE);
		stateEvidence = evidence.get(nodes);
		Assert.assertTrue("incorrect percentage!", Math.abs(stateEvidence.getPercentage(TestEnum.TRUE) - 0.25) < 0.0001);
		Assert.assertTrue("incorrect percentage!", Math.abs(stateEvidence.getPercentage(TestEnum.FALSE) - 0.75) < 0.0001);
		Assert.assertTrue("incorrect total evidence!", stateEvidence.getTotalEvidence() == 4);
		Assert.assertTrue("incorrect individual evidence!", stateEvidence.get(TestEnum.TRUE) == 1);
		Assert.assertTrue("incorrect individual evidence!", stateEvidence.get(TestEnum.FALSE) == 3);
	}
}
