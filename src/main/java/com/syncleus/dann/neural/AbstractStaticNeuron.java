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
package com.syncleus.dann.neural;

public abstract class AbstractStaticNeuron extends AbstractNeuron
{
	private static final long serialVersionUID = 4752462697390024068L;
	private final double output;

	protected AbstractStaticNeuron(final Brain brain, final double constantOutput)
	{
		super(brain);
		this.output = constantOutput;
	}

	@Override
	public void tick()
	{
		//TODO fix this, bad typing
//		for(final Synapse current : this.getBrain().getTraversableEdges(this))
//			current.setInput(this.output);
		for(final Object current : this.getBrain().getTraversableEdges(this))
			((Synapse)current).setInput(this.output);
	}

	@Override
	protected double getOutput()
	{
		return this.output;
	}
}
