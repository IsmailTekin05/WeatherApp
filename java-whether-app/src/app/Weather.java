package app;

import java.awt.*;

public enum Weather {
    /*
    Sunny: Clear sky with a bright sun.
    Cloudy: Overcast sky with full cloud cover.
    Partly Cloudy: Sun and clouds present (multiple variations in the image suggest different degrees of partly cloudy).
    Mostly Sunny: Predominantly sunny with a few clouds.
    Sunny with Light Clouds: Sun with wispy, scattered clouds.
    Cloudy with Sunny Intervals: Mostly cloudy with occasional glimpses of the sun.
    Light Rain: Cloud with a few raindrops (shown as "1111" in the image, likely representing falling rain).
    Moderate Rain: Cloud with more intense raindrops (more "1111" lines).
    Heavy Rain: Cloud with the most intense raindrops (most "1111" lines).
    Thunderstorm: Cloud with lightning symbols.
    Sunny and Light Rain: Sun and cloud together with a few raindrops.
    Sunny and Moderate Rain: Sun and cloud together with more intense raindrops.
    Sunny and Heavy Rain: Sun and cloud together with the most intense raindrops.
    Temperature Drop (Cold): Thermometer with a low temperature indicated by a snowflake.
    Snow: Snowflake symbol.
    Windy: Cloud with strong wind indicated by motion lines.
    Sunny and Windy: Sun with wind indicated by motion lines.
    Foggy: Cloud with a hazy effect (represented by a lighter cloud symbol).
    Cloudy and Cold: Cloud with a snowflake, indicating cold and overcast conditions.
    Stormy: Dark cloud with lightning and rain (combining thunderstorm and heavy rain elements).
    */
    Sunny, Cloudy, PartlyCloudy, MostlySunny, SunnyWithLightClouds,
    CloudyWithSunnyIntervals, LightRain, ModerateRain, HeavyRain, Thunderstorm,
    SunnyAndLightRain, SunnyAndModerateRain, SunnyAndHeavyRain, TemperatureDrop, Snow,
    Windy, SunnyAndWindy, Foggy, CloudyAndCold, Stormy;

    public Color getColor() {
        switch (this) {
            case Sunny: return Color.YELLOW;
            case Cloudy: return Color.GRAY;
            case PartlyCloudy: return Color.LIGHT_GRAY;
            case MostlySunny: return Color.ORANGE;
            case SunnyWithLightClouds: return Color.YELLOW;
            case CloudyWithSunnyIntervals: return Color.GRAY;
            case LightRain: return Color.BLUE;
            case ModerateRain: return Color.BLUE;
            case HeavyRain: return Color.BLUE;
            case Thunderstorm: return Color.BLUE;
            case SunnyAndLightRain: return Color.BLUE;
            case SunnyAndModerateRain: return Color.BLUE;
            case SunnyAndHeavyRain: return Color.BLUE;
            case TemperatureDrop: return Color.CYAN;
            case Snow: return Color.WHITE;
            case Windy: return Color.GREEN;
            case SunnyAndWindy: return Color.GREEN;
            case Foggy: return Color.LIGHT_GRAY;
            case CloudyAndCold: return Color.LIGHT_GRAY;
            case Stormy: return Color.BLACK;
            default: return Color.BLACK;
        }
    }
}
