package aeonlabs.barcodescanner.common.libraries.model;

import java.io.Serializable;


public class BarCode implements Serializable {
    private String barcodeId ;
    private String barcodeNumber ;
    private  String scanTime;
    private String scanDate ;
    private String longitude ;
    private  String latitude;
    private String sent;
    private String archived;


    public String getBarcodeId() {
        return barcodeId;
    }
    public void setBarcodeId(String barcodeId) {
        this.barcodeId = barcodeId;
    }

    public String getBarcodeNumber() {
        return barcodeNumber;
    }
    public void setBarcodeNumber(String barcodeNumber) {
        this.barcodeNumber = barcodeNumber;
    }

    public String getScanTime() {
        return scanTime;
    }
    public void setScanTime(String scanTime) {
        this.scanTime = scanTime;
    }

    public String getScanDate() {
        return scanDate;
    }
    public void setScanDate(String scanDate) {
        this.scanDate = scanDate;
    }

    public String getLatitude() {
        return latitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLongitude() {
        return longitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getSent() {
        return sent;
    }
    public void setSent(String sent) {
        this.sent = sent;
    }

    public String getArchived() {
        return archived;
    }
    public void setArchived(String archived) {
        this.archived = archived;
    }

    public BarCode(String barcodeNumber, String scanTime, String scanDate, String latitude, String longitude, String sent, String archived) {
        this.barcodeNumber = barcodeNumber;
        this.scanTime = scanTime;
        this.scanDate = scanDate;
        this.latitude=latitude;
        this.longitude=longitude;
        this.sent=sent;
        this.archived=archived;
    }

    public BarCode(){

    }
}
