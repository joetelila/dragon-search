package sample;
import javafx.beans.property.SimpleStringProperty;

public class TableProps {
    private final SimpleStringProperty tool;
    private final SimpleStringProperty chart;

    public TableProps(String tool, String chart) {
        this.tool = new SimpleStringProperty(tool);
        this.chart = new SimpleStringProperty(chart);
    }

    public String getTool() {
        return tool.get();
    }

    public SimpleStringProperty toolProperty() {
        return tool;
    }

    public void setTool(String tool) {
        this.tool.set(tool);
    }

    public String getChart() {
        return chart.get();
    }

    public SimpleStringProperty chartProperty() {
        return chart;
    }

    public void setChart(String chart) {
        this.chart.set(chart);
    }
}