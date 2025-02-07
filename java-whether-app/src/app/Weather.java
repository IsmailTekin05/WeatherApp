package app;

public enum Weather {
    Sunny, Cloudy, PartlyCloudy, MostlySunny, SunnyWithLightClouds,
    CloudyWithSunnyIntervals, LightRain, ModerateRain, HeavyRain, Thunderstorm,
    SunnyAndLightRain, SunnyAndModerateRain, SunnyAndHeavyRain, TemperatureDrop, Snow,
    Windy, SunnyAndWindy, Foggy, CloudyAndCold, Stormy;


    @Override
    public String toString() {
        switch (this) {
            case Sunny: return "Sunny";
            case Cloudy: return "Cloudy";
            case PartlyCloudy: return "Partly Cloudy";
            case MostlySunny: return "Mostly Sunny";
            case SunnyWithLightClouds: return "Sunny with Light Clouds";
            case CloudyWithSunnyIntervals: return "Cloudy with Sunny Intervals";
            case LightRain: return "Light Rain";
            case ModerateRain: return "Moderate Rain";
            case HeavyRain: return "Heavy Rain";
            case Thunderstorm: return "Thunderstorm";
            case SunnyAndLightRain: return "Sunny and Light Rain";
            case SunnyAndModerateRain: return "Sunny and Moderate Rain";
            case SunnyAndHeavyRain: return "Sunny and Heavy Rain";
            case TemperatureDrop: return "Temperature Drop";
            case Snow: return "Snow";
            case Windy: return "Windy";
            case SunnyAndWindy: return "Sunny and Windy";
            case Foggy: return "Foggy";
            case CloudyAndCold: return "Cloudy and Cold";
            case Stormy: return "Stormy";
            default: return "Unknown";
        }
    }

    public static Weather toWeather(int weatherCode) {
        switch (weatherCode) {
            case 0:
                return Sunny;
            case 1:
                return SunnyWithLightClouds;
            case 2:
                return PartlyCloudy;
            case 3:
                return Cloudy;
            case 4:
                return Cloudy;
            case 51: // Drizzle
            case 53: // Drizzle
            case 55: // Drizzle
            case 56: // Freezing Drizzle
            case 57: // Freezing Drizzle
            case 61: // Slight rain
            case 66: // Light freezing rain
                return LightRain;
            case 63: // Moderate rain
            case 67: // Heavy freezing rain
                return ModerateRain;
            case 65: // Heavy rain
                return HeavyRain;
            case 71: // Slight snow fall
            case 73: // Moderate snow fall
            case 75: // Heavy snow fall
            case 77: // Snow grains
            case 85: // Slight snow showers
            case 86: // Heavy snow showers
                return Snow;
            case 80: // Slight rain showers
                return LightRain;
            case 81: // Moderate rain showers
                return ModerateRain;
            case 82: // Violent rain showers
                return HeavyRain;
            case 95: // Thunderstorm
            case 96: // Thunderstorm with slight hail
            case 99: // Thunderstorm with heavy hail
                return Thunderstorm;
            default:
                return Cloudy; // Default to Cloudy if code is not found
        }
    }

    public int getRow() {
        // Determine row for each weather condition (0-indexed)
        switch (this) {
            case Sunny: return 0;
            case Cloudy: return 0;
            case PartlyCloudy: return 0;
            case MostlySunny: return 0;
            case SunnyWithLightClouds: return 0;
            case CloudyWithSunnyIntervals: return 1;
            case LightRain: return 1;
            case ModerateRain: return 1;
            case HeavyRain: return 1;
            case Thunderstorm: return 1;
            case SunnyAndLightRain: return 2;
            case SunnyAndModerateRain: return 2;
            case SunnyAndHeavyRain: return 2;
            case TemperatureDrop: return 2;
            case Snow: return 2;
            case Windy: return 3;
            case SunnyAndWindy: return 3;
            case Foggy: return 3;
            case CloudyAndCold: return 3;
            case Stormy: return 3;
            default: return 0;
        }
    }

    public int getCol() {
        // Determine column for each weather condition (0-indexed)
        switch (this) {
            case Sunny: return 0;
            case Cloudy: return 1;
            case PartlyCloudy: return 2;
            case MostlySunny: return 3;
            case SunnyWithLightClouds: return 4;
            case CloudyWithSunnyIntervals: return 0;
            case LightRain: return 1;
            case ModerateRain: return 2;
            case HeavyRain: return 3;
            case Thunderstorm: return 4;
            case SunnyAndLightRain: return 0;
            case SunnyAndModerateRain: return 1;
            case SunnyAndHeavyRain: return 2;
            case TemperatureDrop: return 3;
            case Snow: return 4;
            case Windy: return 0;
            case SunnyAndWindy: return 1;
            case Foggy: return 2;
            case CloudyAndCold: return 3;
            case Stormy: return 4;
            default: return 0;
        }
    }
}
