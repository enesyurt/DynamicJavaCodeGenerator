package test.model.common;

import test.LogHelper;

public class GeoPositionTypeModel {

    private double Latitude;
    private double Longitude;
    private double AltitudeDepth;
    private TestInfogramEnum1Type EnumValue;

    // Getters and Setters
    public double getLatitude() {
        return Latitude;
    }
    public void setLatitude(double Latitude) {
        this.Latitude = Latitude;
    }
    public double getLongitude() {
        return Longitude;
    }
    public void setLongitude(double Longitude) {
        this.Longitude = Longitude;
    }
    public double getAltitudeDepth() {
        return AltitudeDepth;
    }
    public void setAltitudeDepth(double AltitudeDepth) {
        this.AltitudeDepth = AltitudeDepth;
    }
    public TestInfogramEnum1Type getEnumValue() {
        return EnumValue;
    }
    public void setEnumValue(TestInfogramEnum1Type EnumValue) {
        this.EnumValue = EnumValue;
    }
}
