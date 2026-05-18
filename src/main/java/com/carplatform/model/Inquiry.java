package com.carplatform.model;

public class Inquiry {

    public enum Status { PENDING, RESPONDED, CLOSED }

    private String inquiryId;
    private String carId;
    private String buyerId;
    private String sellerId;
    private String message;
    private String contactEmail;
    private String contactPhone;
    private Status status;
    private String submittedDate;
    private String responseMessage;
    private String responseDate;

    public Inquiry() {
        this.status = Status.PENDING;
    }

    public Inquiry(String inquiryId, String carId, String buyerId, String sellerId,
                   String message, String contactEmail, String contactPhone, String submittedDate) {
        this.inquiryId     = inquiryId;
        this.carId         = carId;
        this.buyerId       = buyerId;
        this.sellerId      = sellerId;
        this.message       = message;
        this.contactEmail  = contactEmail;
        this.contactPhone  = contactPhone;
        this.submittedDate = submittedDate;
        this.status        = Status.PENDING;
    }

    public String toFileString() {
        return inquiryId + "|" + carId + "|" + buyerId + "|" + sellerId + "|"
                + message + "|" + contactEmail + "|" + contactPhone + "|"
                + submittedDate + "|" + status.name() + "|"
                + (responseMessage != null ? responseMessage : "N/A") + "|"
                + (responseDate    != null ? responseDate    : "N/A");
    }

    public static Inquiry fromFileString(String line) {
        String[] p = line.split("\\|", -1);
        if (p.length < 11) return null;
        Inquiry inq = new Inquiry(p[0], p[1], p[2], p[3], p[4], p[5], p[6], p[7]);
        inq.setStatus(Status.valueOf(p[8]));
        inq.setResponseMessage("N/A".equals(p[9])  ? null : p[9]);
        inq.setResponseDate   ("N/A".equals(p[10]) ? null : p[10]);
        return inq;
    }

    public String getInquiryId()                          { return inquiryId; }
    public void   setInquiryId(String id)                 { this.inquiryId = id; }

    public String getCarId()                              { return carId; }
    public void   setCarId(String carId)                  { this.carId = carId; }

    public String getBuyerId()                            { return buyerId; }
    public void   setBuyerId(String buyerId)              { this.buyerId = buyerId; }

    public String getSellerId()                           { return sellerId; }
    public void   setSellerId(String sellerId)            { this.sellerId = sellerId; }

    public String getMessage()                            { return message; }
    public void   setMessage(String message)              { this.message = message; }

    public String getContactEmail()                       { return contactEmail; }
    public void   setContactEmail(String e)               { this.contactEmail = e; }

    public String getContactPhone()                       { return contactPhone; }
    public void   setContactPhone(String p)               { this.contactPhone = p; }

    public Status getStatus()                             { return status; }
    public void   setStatus(Status status)                { this.status = status; }

    public String getSubmittedDate()                      { return submittedDate; }
    public void   setSubmittedDate(String d)              { this.submittedDate = d; }

    public String getResponseMessage()                    { return responseMessage; }
    public void   setResponseMessage(String r)            { this.responseMessage = r; }

    public String getResponseDate()                       { return responseDate; }
    public void   setResponseDate(String d)               { this.responseDate = d; }
}
