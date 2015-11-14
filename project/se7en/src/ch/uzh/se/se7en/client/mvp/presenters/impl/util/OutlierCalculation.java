package ch.uzh.se.se7en.client.mvp.presenters.impl.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


import ch.uzh.se.se7en.client.mvp.model.DataTableEntity;

public class OutlierCalculation {

	private static double quartil(List<DataTableEntity> entities, int quart)
	{
		//Algorithm used from: https://support.microsoft.com/en-us/kb/103493
		int k = (int) (((quart/4.0) * entities.size())+1);
		double f = ((quart/4.0) * entities.size())+1 - k;

		return entities.get(k).getValue() + (f*(entities.get(k+1).getValue()-entities.get(k).getValue()));
	}

	public static double[] calcOutlierBoundaries(List<DataTableEntity> entities)
	{
		//Comparator to sort the list of DataTableEntity
		Comparator<DataTableEntity> comp = new Comparator<DataTableEntity>(){
			@Override
			public int compare(DataTableEntity o1, DataTableEntity o2) {
				int val1 = o1.getValue();
				int val2 = o2.getValue();
				if(val1 < val2)
				{
					return -1;
				}
				else if (val1>val2)
				{
					return 1;
				}
				else //val1==val2
				{
					return 0;
				}
			}

		};
		//Sorting the entities
		Collections.sort(entities, comp);

		double[] outlierBoundaries = new double[2]; //index 0 = lowerOutlierBoundary, index 1 = upperOutlierBoundary
		
		//calculating q1
		double q1 = quartil(entities, 1);
		
		//calculating q3
		double q3 = quartil(entities, 3);
		
		double iqa = q3-q1;
		
		double minValue = q1 - 1.5*iqa;
		double maxValue = 1.5*iqa + q3;
		
		outlierBoundaries[0] = minValue;
		outlierBoundaries[1] = maxValue;
		return outlierBoundaries;
	}

}
