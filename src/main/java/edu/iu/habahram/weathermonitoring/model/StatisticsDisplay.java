package edu.iu.habahram.weathermonitoring.model;

import org.springframework.stereotype.Component;

@Component
public class StatisticsDisplay implements Observer, DisplayElement {
    private float totalTemp = 0;

    private float average = 0;
    private int count = 0;
    private float tempMin = 1000000000;
    private float tempMax = 0;
    private Subject weatherData;

    public StatisticsDisplay(Subject weatherData) {
        this.weatherData = weatherData;
    }    

    @Override
    public String display() {
        String html = "";
        html += String.format("<div style=\"background-image: " +
                "url(/images/sky.webp); " +
                "height: 400px; " +
                "width: 647.2px;" +
                "display:flex;flex-wrap:wrap;justify-content:center;align-content:center;" +
                "\">");
        html += "<section>";
        html += String.format("<label>Avg temp.: %s</label><br />", average);
        html += String.format("<label>Min temp.: %s</label><br />", tempMin);
        html += String.format("<label>Max temp.: %s</label><br />", tempMax);
        html += "</section>";
        html += "</div>";
        return html;
    }

    private void caalculateAverage(){
        count++;
        average = totalTemp / count;
    }
    
    public String name() {
        return "Statistics Display";
    }

    public String id() {
        return "statistics";
    }

    @Override
    public void update(float temperature, float humidity, float pressure) {

        totalTemp += temperature;
        caalculateAverage();

        if(temperature > tempMax){
            this.tempMax = temperature;
        }
        if(temperature < tempMin){
            this.tempMin = temperature;
        }
    }

    public void subscribe() {
        weatherData.registerObserver(this);
    }

    public void unsubscribe() {
        weatherData.removeObserver(this);
    }

}