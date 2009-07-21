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

import com.syncleus.dann.genetics.Chromatid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class WaveletChromatid implements Chromatid<WaveletGene>
{
	private ArrayList<WaveletGene> sequencedGenes;
	private ArrayList<PromoterGene> promoters;
	private ArrayList<SignalGene> localSignalGenes;
	private ArrayList<ExternalSignalGene> externalSignalGenes;

	private int centromerePosition;

	private double mutability;

	public WaveletChromatid(WaveletChromatid copy)
	{
		this.centromerePosition = copy.centromerePosition;
		this.mutability = copy.mutability;
		
		this.sequencedGenes = new ArrayList<WaveletGene>();
		this.promoters = new ArrayList<PromoterGene>();
		this.localSignalGenes = new ArrayList<SignalGene>();
		this.externalSignalGenes = new ArrayList<ExternalSignalGene>();

		for(WaveletGene currentGene : copy.sequencedGenes)
			this.sequencedGenes.add(currentGene.clone());
		for(PromoterGene currentGene : copy.promoters)
			this.promoters.add(currentGene.clone());
		for(SignalGene currentGene : copy.localSignalGenes)
			this.localSignalGenes.add(currentGene.clone());
		for(ExternalSignalGene currentGene : copy.externalSignalGenes)
			this.externalSignalGenes.add(currentGene.clone());
	}

	public boolean bind(SignalKeyConcentration concentration, boolean isExternal)
	{
		return false;
	}

	public int getCentromerePosition()
	{
		return this.centromerePosition;
	}

	public List<WaveletGene> getGenes()
	{
		return Collections.unmodifiableList(this.sequencedGenes);
	}

	public List<PromoterGene> getPromoterGenes()
	{
		return Collections.unmodifiableList(this.promoters);
	}

	public List<SignalGene> getLocalSignalGenes()
	{
		return Collections.unmodifiableList(this.localSignalGenes);
	}

	public List<ExternalSignalGene> getExternalSignalGenes()
	{
		return Collections.unmodifiableList(this.externalSignalGenes);
	}

	public List<WaveletGene> crossover(int point)
	{
		int index = point + this.centromerePosition;

		if((index < 0)||(index > this.sequencedGenes.size()))
			return null;
		if((index == 0)||(index == this.sequencedGenes.size()))
			return Collections.unmodifiableList(new ArrayList<WaveletGene>());

		if(point < 0)
			return Collections.unmodifiableList(this.sequencedGenes.subList(0, index));
		else
			return Collections.unmodifiableList(this.sequencedGenes.subList(index, this.sequencedGenes.size()));
	}

	public void crossover(List<WaveletGene> geneticSegment, int point)
	{
		int index = point + this.centromerePosition;

		if((index < 0)||(index > this.sequencedGenes.size()))
			throw new IllegalArgumentException("point is out of range for crossover");

		//calculate new centromere position
		int newCentromerePostion = this.centromerePosition - (index - geneticSegment.size());

		//create new sequence of genes after crossover
		ArrayList<WaveletGene> newGenes;
		List<WaveletGene> oldGenes;
		if(point < 0 )
		{
			newGenes = new ArrayList<WaveletGene>(geneticSegment);
			newGenes.addAll(this.sequencedGenes.subList(index, this.sequencedGenes.size()));

			oldGenes = this.sequencedGenes.subList(0, index);
		}
		else
		{
			newGenes = new ArrayList<WaveletGene>(this.sequencedGenes.subList(0, index));
			newGenes.addAll(geneticSegment);

			oldGenes = this.sequencedGenes.subList(index, this.sequencedGenes.size());
		}
		
		//remove displaced genes from specific gene type lists
		for(WaveletGene oldGene : oldGenes)
		{
			if(oldGene instanceof PromoterGene)
				this.promoters.remove(oldGene);
			else if(oldGene instanceof ExternalSignalGene)
				this.externalSignalGenes.remove(oldGene);
			else if(oldGene instanceof SignalGene)
				this.localSignalGenes.remove(oldGene);
		}
		
		//add new genes to the specific gene type list
		for(WaveletGene newGene : geneticSegment)
		{
			if(newGene instanceof PromoterGene)
				this.promoters.add((PromoterGene)newGene);
			else if(newGene instanceof ExternalSignalGene)
				this.externalSignalGenes.add((ExternalSignalGene)newGene);
			else if(newGene instanceof SignalGene)
				this.localSignalGenes.add((SignalGene)newGene);
		}

		//update sequence genes to use the new genes
		this.sequencedGenes = newGenes;
		this.centromerePosition = newCentromerePostion;
	}

	@Override
	public WaveletChromatid clone()
	{
		return new WaveletChromatid(this);
	}

	public void mutate(Set<Key> keyPool)
	{
		for(WaveletGene currentGene : this.sequencedGenes)
			currentGene.mutate(keyPool);
	}
}