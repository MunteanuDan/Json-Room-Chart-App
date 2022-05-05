package eu.ase.tema2android.utilChart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.Map;
import java.util.Random;

import eu.ase.tema2android.R;

public class ChartView extends View {

    private final Context context;
    private final Map<String, Integer> source;
    private final Paint paint;
    private final Random random;

    public ChartView(Context context, Map<String, Integer> source) {
        super(context);
        this.context = context;
        this.source = source;
        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.paint.setColor(Color.BLACK);
        this.random = new Random();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (source.isEmpty()) {
            return;
        }
        //latimea unei bare
        float widthBar = getWidth() / source.size();
        //valoarea maxima
        int maxValue = getMaxValue();
        drawValues(canvas, widthBar, maxValue);
    }

    private void drawValues(Canvas canvas, float widthBar, int maxValue) {
        //trasare grafic
        int currentBarPosition = 0;
        for (String label : source.keySet()) {
            //valoarea curenta a barei
            int value = source.get(label);
            //generare culoare bara
            int color = generateColor();
            paint.setColor(color);
            drawBar(canvas, widthBar, maxValue, currentBarPosition, value);
            drawLegend(canvas, widthBar, currentBarPosition, label, value);
            currentBarPosition++;
        }
    }

    private int generateColor() {
        return Color.argb(100,
                1 + random.nextInt(254),
                1 + random.nextInt(254),
                1 + random.nextInt(254));
    }

    private void drawLegend(Canvas canvas, float widthBar, int currentBarPosition, String label, int value) {
        //trasare legenda. format: <label> - <value>
        paint.setColor(Color.BLACK);
        paint.setTextSize((float) (0.25 * widthBar));
        float x = (float) ((currentBarPosition + 0.5) * widthBar);
        float y = (float) (0.95 * getHeight());
        canvas.rotate(270, x, y);
        canvas.drawText(context.getString(R.string.chart_legend_template, label, value), x, y, paint); // sa scriem textul pe grafice
        canvas.rotate(-270, x, y);
    }

    private void drawBar(Canvas canvas, float widthBar, int maxValue, int currentBarPosition, int value) {
        //trasare bara
        float x1 = currentBarPosition * widthBar;
        float y1 = (1 - (float) value / maxValue) * getHeight();
        float x2 = x1 + widthBar;
        float y2 = getHeight();
        canvas.drawRect(x1, y1, x2, y2, paint);
    }

    private int getMaxValue() {
        int max = 0;
        for (Integer value : source.values()) {
            if (max < value) {
                max = value;
            }
        }
        return max;
    }
}
