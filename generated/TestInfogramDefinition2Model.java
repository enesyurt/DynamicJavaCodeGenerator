package test.model.common;
import java.util.ArrayList;

public class TestInfogramDefinitionModel {

    private long Id;
    private short ItemId;
    private TestInfogramItemNameType ItemName;
    private TestInfogramEnum1Type Enum1;
    private TestInfogramEnum2Type Enum2;
    private double Percentage;
    private TestInfogramPointListType PointList;
    private short PointListSize;

     // Getters and Setters 
    public long getId() {
        return Id;
    }
    public void setId(long Id) {
        this.Id = Id;
    }
    public short getItemId() {
        return ItemId;
    }
    public void setItemId(short ItemId) {
        this.ItemId = ItemId;
    }
    public TestInfogramItemNameType getItemName() {
        return ItemName;
    }
    public void setItemName(TestInfogramItemNameType ItemName) {
        this.ItemName = ItemName;
    }
    public TestInfogramEnum1Type getEnum1() {
        return Enum1;
    }
    public void setEnum1(TestInfogramEnum1Type Enum1) {
        this.Enum1 = Enum1;
    }
    public TestInfogramEnum2Type getEnum2() {
        return Enum2;
    }
    public void setEnum2(TestInfogramEnum2Type Enum2) {
        this.Enum2 = Enum2;
    }
    public double getPercentage() {
        return Percentage;
    }
    public void setPercentage(double Percentage) {
        this.Percentage = Percentage;
    }
    public TestInfogramPointListType getPointList() {
        return PointList;
    }
    public void setPointList(TestInfogramPointListType PointList) {
        this.PointList = PointList;
    }
    public short getPointListSize() {
        return PointListSize;
    }
    public void setPointListSize(short PointListSize) {
        this.PointListSize = PointListSize;
    }
}
