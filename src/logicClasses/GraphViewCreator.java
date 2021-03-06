package logicClasses;

import displayActivities.*;
import android.graphics.Color;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.LineGraphView;
// we used http://www.jjoe64.com/p/graphview-library.html GraphView library in order to present the evolution of data through the years
public class GraphViewCreator {

	public static GraphViewSeries exampleSeries1;
	public static GraphViewSeries exampleSeries2;
	public static GraphView graphView;
	public static int ii = 0;

	private static String nameOfTheClassCallingThis;

	private static int iiIncreasingOn102;

	public GraphViewCreator() {
		graphViewCreator();
	}

	public static void setNameOfClassCallingGraphViewCreator(String className) {
		nameOfTheClassCallingThis = className;
	}

	public static void graphViewCreator() {

		GraphViewSeriesStyle seriesStyle = new GraphViewSeriesStyle();
		seriesStyle.color = Color.BLUE;

		GraphViewSeriesStyle seriesStyle2 = new GraphViewSeriesStyle();
		seriesStyle2.color = Color.RED;

		exampleSeries1 = new GraphViewSeries("", seriesStyle,
				new GraphViewData[] { new GraphViewData(QueryBuilder.years[ii],
						QueryBuilder.values[ii]) });
		if (nameOfTheClassCallingThis
				.equals("searchActivities.IndicatorSearchActivity")) {
			graphView = new LineGraphView(
					displayActivities.IndicatorActivity.graphLayout
							.getContext(),
					"");
		}

		if (nameOfTheClassCallingThis
				.equals("searchActivities.ComparisonSearchActivity")) {
			graphView = new LineGraphView(
					displayActivities.ComparisonActivity.graphViewLayout
							.getContext(),
					"");

			exampleSeries2 = new GraphViewSeries("", seriesStyle2,
					new GraphViewData[] { new GraphViewData(
							QueryBuilder.years[51 + ii],
							QueryBuilder.values[51 + ii]) });

			graphView.addSeries(exampleSeries2);
		}
		graphView.addSeries(exampleSeries1);

		while (ii != QueryBuilder.arrayNumber) {

			if (nameOfTheClassCallingThis
					.equals("searchActivities.IndicatorSearchActivity")) {
				exampleSeries1.appendData(new GraphViewData(
						QueryBuilder.years[ii], QueryBuilder.values[ii]),
						false, 1000);
			}

			QueryBuilder.arrayWithValuesForComparison
					.add(QueryBuilder.arrayWithValuesForComparisonForKeepingTheNullValues[ii]);

			if (nameOfTheClassCallingThis
					.equals("searchActivities.ComparisonSearchActivity")) {
				if (ii >= (0 + iiIncreasingOn102)
						&& ii <= (50 + iiIncreasingOn102)) {
					exampleSeries1.appendData(new GraphViewData(
							QueryBuilder.years[ii], QueryBuilder.values[ii]),
							false, 1000);
				}

				if (ii >= (51 + iiIncreasingOn102)
						&& ii <= (102 + iiIncreasingOn102)) {

					exampleSeries2.appendData(new GraphViewData(
							QueryBuilder.years[ii], QueryBuilder.values[ii]),
							false, 1000);
				}

				if (ii % 102 == 0 && ii != 0) {
					iiIncreasingOn102 += 102;
				}
			}

			ii++;

		}

		graphView.redrawAll();

		graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.BLACK);
		graphView.getGraphViewStyle().setVerticalLabelsColor(Color.BLACK);

		graphView.setScrollable(true);

		graphView.getGraphViewStyle().setVerticalLabelsWidth(250);

		if (nameOfTheClassCallingThis
				.equals("searchActivities.IndicatorSearchActivity")) {
			IndicatorActivity.graphLayout.removeAllViews();
		}
		if (nameOfTheClassCallingThis
				.equals("searchActivities.IndicatorSearchActivity")) {
			IndicatorActivity.graphLayout.addView(graphView);
		}

		if (nameOfTheClassCallingThis
				.equals("searchActivities.ComparisonSearchActivity")) {
			ComparisonActivity.graphViewLayout.removeAllViews();
		}
		if (nameOfTheClassCallingThis
				.equals("searchActivities.ComparisonSearchActivity")) {
			ComparisonActivity.graphViewLayout.addView(graphView);
		}

	}
}
