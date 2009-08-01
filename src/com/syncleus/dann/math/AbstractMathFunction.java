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
package com.syncleus.dann.math;

import java.security.InvalidParameterException;
import java.util.Hashtable;

public abstract class AbstractMathFunction
{
    private double[] parameters;
    private String[] parameterNames;
    private final Hashtable<String,Integer> indexNames = new Hashtable<String,Integer>();

	protected AbstractMathFunction(AbstractMathFunction copy)
	{
		this.parameters = copy.parameters.clone();
		this.parameterNames = copy.parameterNames.clone();
		this.indexNames.putAll(copy.indexNames);
	}

    protected AbstractMathFunction(String[] parameterNames)
    {
        if(parameterNames.length <= 0)
            return;
        
        this.parameters = new double[parameterNames.length];
        this.parameterNames = parameterNames.clone();
        
        for(int index = 0; index < this.parameterNames.length; index++)
        {
            this.indexNames.put(this.parameterNames[index], Integer.valueOf(index));
        }
    }
    
    public final String[] getParameterNames()
    {
        return this.parameterNames.clone();
    }
    
    protected static String[] combineLabels(String[] first, String[] second)
    {
        String[] result = new String[first.length + second.length];
        int resultIndex = 0;
        
        for(int firstIndex = 0; firstIndex < first.length; firstIndex++)
        {
            result[resultIndex] = first[firstIndex];
            resultIndex++;
        }
        for(int secondIndex = 0; secondIndex < second.length; secondIndex++)
        {
            result[resultIndex] = second[secondIndex];
            resultIndex++;
        }
        
        return result;
    }
    
    public final void setParameter(int parameterIndex, double value)
    {
        if(parameterIndex >= parameters.length || parameterIndex < 0)
            throw new InvalidParameterException("parameterIndex of " + parameterIndex + " is out of range");
        
        this.parameters[parameterIndex] = value;
    }

	public final void setParameter(String parameterName, double value)
	{
		this.setParameter(this.getParameterNameIndex(parameterName), value);
	}
    
    public final double getParameter(int parameterIndex)
    {
        if(parameterIndex >= parameters.length || parameterIndex < 0)
            throw new InvalidParameterException("parameterIndex out of range");
        
        return this.parameters[parameterIndex];
    }

	public final double getParameter(String parameterName)
	{
		return this.getParameter(this.getParameterNameIndex(parameterName));
	}
    
    public final String getParameterName(int parameterIndex)
    {
        if( parameterIndex >= this.parameterNames.length || parameterIndex < 0 )
            throw new InvalidParameterException("parameterIndex is not within range");
        
        return this.parameterNames[parameterIndex];
    }
    
    public final int getParameterNameIndex(String parameterName)
    {
        if( this.indexNames.containsKey(parameterName) == false)
            throw new InvalidParameterException("parameterName: " + parameterName + " does not exist");
        
        return this.indexNames.get(parameterName).intValue();
    }
    
    public final int getParameterCount()
    {
        return this.parameters.length;
    }

	@Override
	@SuppressWarnings("unchecked")
    public AbstractMathFunction clone() throws CloneNotSupportedException
	{
		AbstractMathFunction copy = (AbstractMathFunction) super.clone();
		copy.indexNames.putAll(this.indexNames);
		copy.parameterNames = this.parameterNames.clone();
		copy.parameters = this.parameters.clone();
		return copy;
	}

    public abstract double calculate();
    public abstract String toString();
}