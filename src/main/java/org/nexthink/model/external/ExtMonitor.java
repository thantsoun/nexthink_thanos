package org.nexthink.model.external;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * The model for monitor that comes from external sources (json, xml, etc)
 */
public class ExtMonitor {

    @SerializedName("monitor_name")
    private String name;
    @SerializedName("monitor_serial_number")
    private String serialNumber;
    @SerializedName("monitor_vendor")
    private String vendor;
    @SerializedName("monitor_diagonal")
    private String diagonal;
    @SerializedName("monitor_max_horizontal")
    private String horizontal;
    @SerializedName("monitor_max_vertical")
    private String vertical;

    // Emtpy constructor needed to work with frameworks
    public ExtMonitor() {
    }
    
    public ExtMonitor(String name, String serialNumber, String vendor, String diagonal, String horizontal, String vertical) {
        this.name = name;
        this.serialNumber = serialNumber;
        this.vendor = vendor;
        this.diagonal = diagonal;
        this.horizontal = horizontal;
        this.vertical = vertical;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getDiagonal() {
        return diagonal;
    }

    public void setDiagonal(String diagonal) {
        this.diagonal = diagonal;
    }

    public String getHorizontal() {
        return horizontal;
    }

    public void setHorizontal(String horizontal) {
        this.horizontal = horizontal;
    }

    public String getVertical() {
        return vertical;
    }

    public void setVertical(String vertical) {
        this.vertical = vertical;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExtMonitor extMonitor = (ExtMonitor) o;
        return Objects.equals(name, extMonitor.name) && Objects.equals(serialNumber, extMonitor.serialNumber) && Objects.equals(vendor, extMonitor.vendor) && Objects.equals(diagonal, extMonitor.diagonal) && Objects.equals(horizontal, extMonitor.horizontal) && Objects.equals(vertical, extMonitor.vertical);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, serialNumber, vendor, diagonal, horizontal, vertical);
    }

    @Override
    public String toString() {
        return "Monitor{" +
                "name='" + name + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", vendor='" + vendor + '\'' +
                ", diagonal='" + diagonal + '\'' +
                ", horizontal='" + horizontal + '\'' +
                ", vertical='" + vertical + '\'' +
                '}';
    }
}
