package com.app.tosstra.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AllJobsToDriver implements Serializable {
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Data> data = null;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data implements Serializable{

        @SerializedName("jobId")
        @Expose
        private String jobId;

        @SerializedName("driverlatitude")
        @Expose
        private Double driverlatitude;

        @SerializedName("driverlongitude")
        @Expose
        private Double driverlongitude;

        @SerializedName("dispatcherId")
        @Expose
        private String dispatcherId;
        @SerializedName("driverId")
        @Expose
        private String driverId;
        @SerializedName("offerForSelectedDrivers")
        @Expose
        private String offerForSelectedDrivers;
        @SerializedName("workStartStatus")
        @Expose
        private String workStartStatus;
        @SerializedName("rateType")
        @Expose
        private String rateType;
        @SerializedName("rate")
        @Expose
        private String rate;
        @SerializedName("pupStreet")
        @Expose
        private String pupStreet;
        @SerializedName("pupCity")
        @Expose
        private String pupCity;
        @SerializedName("pupState")
        @Expose
        private String pupState;
        @SerializedName("pupZipcode")
        @Expose
        private String pupZipcode;
        @SerializedName("drpStreet")
        @Expose
        private String drpStreet;
        @SerializedName("drpCity")
        @Expose
        private String drpCity;
        @SerializedName("drpState")
        @Expose
        private String drpState;
        @SerializedName("drpZipcode")
        @Expose
        private String drpZipcode;
        @SerializedName("dateFrom")
        @Expose
        private String dateFrom;
        @SerializedName("dateTo")
        @Expose
        private String dateTo;
        @SerializedName("startTime")
        @Expose
        private String startTime;
        @SerializedName("endTime")
        @Expose
        private String endTime;
        @SerializedName("additinal_Instructions")
        @Expose
        private String additinalInstructions;
        @SerializedName("jobCompleteStatus")
        @Expose
        private String jobCompleteStatus;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("firstName")
        @Expose
        private String firstName;
        @SerializedName("lastName")
        @Expose
        private String lastName;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("profileImg")
        @Expose
        private String profileImg;
        @SerializedName("companyName")
        @Expose
        private String companyName;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("dotNumber")
        @Expose
        private String dotNumber;
        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("longitude")
        @Expose
        private String longitude;
        @SerializedName("userType")
        @Expose
        private String userType;
        @SerializedName("SubUserType")
        @Expose
        private String subUserType;
        @SerializedName("phone")
        @Expose
        private Object phone;
        @SerializedName("onlineStatus")
        @Expose
        private String onlineStatus;

        @SerializedName("puplatitude")
        @Expose
        private double puplatitude;

        @SerializedName("puplongitude")
        @Expose
        private double puplongitude;

        @SerializedName("drplatitude")
        @Expose
        private double drplatitude;

        @SerializedName("drplongitude")
        @Expose
        private double drplongitude;


        public Double getDriverlatitude() {
            return driverlatitude;
        }

        public void setDriverlatitude(Double driverlatitude) {
            this.driverlatitude = driverlatitude;
        }

        public Double getDriverlongitude() {
            return driverlongitude;
        }

        public void setDriverlongitude(Double driverlongitude) {
            this.driverlongitude = driverlongitude;
        }

        public double getPuplatitude() {
            return puplatitude;
        }

        public void setPuplatitude(double puplatitude) {
            this.puplatitude = puplatitude;
        }

        public double getPuplongitude() {
            return puplongitude;
        }

        public void setPuplongitude(double puplongitude) {
            this.puplongitude = puplongitude;
        }

        public double getDrplatitude() {
            return drplatitude;
        }

        public void setDrplatitude(double drplatitude) {
            this.drplatitude = drplatitude;
        }

        public double getDrplongitude() {
            return drplongitude;
        }

        public void setDrplongitude(double drplongitude) {
            this.drplongitude = drplongitude;
        }





        public String getJobId() {
            return jobId;
        }

        public void setJobId(String jobId) {
            this.jobId = jobId;
        }

        public String getDispatcherId() {
            return dispatcherId;
        }

        public void setDispatcherId(String dispatcherId) {
            this.dispatcherId = dispatcherId;
        }

        public String getDriverId() {
            return driverId;
        }

        public void setDriverId(String driverId) {
            this.driverId = driverId;
        }

        public String getOfferForSelectedDrivers() {
            return offerForSelectedDrivers;
        }

        public void setOfferForSelectedDrivers(String offerForSelectedDrivers) {
            this.offerForSelectedDrivers = offerForSelectedDrivers;
        }

        public String getWorkStartStatus() {
            return workStartStatus;
        }

        public void setWorkStartStatus(String workStartStatus) {
            this.workStartStatus = workStartStatus;
        }

        public String getRateType() {
            return rateType;
        }

        public void setRateType(String rateType) {
            this.rateType = rateType;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getPupStreet() {
            return pupStreet;
        }

        public void setPupStreet(String pupStreet) {
            this.pupStreet = pupStreet;
        }

        public String getPupCity() {
            return pupCity;
        }

        public void setPupCity(String pupCity) {
            this.pupCity = pupCity;
        }

        public String getPupState() {
            return pupState;
        }

        public void setPupState(String pupState) {
            this.pupState = pupState;
        }

        public String getPupZipcode() {
            return pupZipcode;
        }

        public void setPupZipcode(String pupZipcode) {
            this.pupZipcode = pupZipcode;
        }

        public String getDrpStreet() {
            return drpStreet;
        }

        public void setDrpStreet(String drpStreet) {
            this.drpStreet = drpStreet;
        }

        public String getDrpCity() {
            return drpCity;
        }

        public void setDrpCity(String drpCity) {
            this.drpCity = drpCity;
        }

        public String getDrpState() {
            return drpState;
        }

        public void setDrpState(String drpState) {
            this.drpState = drpState;
        }

        public String getDrpZipcode() {
            return drpZipcode;
        }

        public void setDrpZipcode(String drpZipcode) {
            this.drpZipcode = drpZipcode;
        }

        public String getDateFrom() {
            return dateFrom;
        }

        public void setDateFrom(String dateFrom) {
            this.dateFrom = dateFrom;
        }

        public String getDateTo() {
            return dateTo;
        }

        public void setDateTo(String dateTo) {
            this.dateTo = dateTo;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getAdditinalInstructions() {
            return additinalInstructions;
        }

        public void setAdditinalInstructions(String additinalInstructions) {
            this.additinalInstructions = additinalInstructions;
        }

        public String getJobCompleteStatus() {
            return jobCompleteStatus;
        }

        public void setJobCompleteStatus(String jobCompleteStatus) {
            this.jobCompleteStatus = jobCompleteStatus;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getProfileImg() {
            return profileImg;
        }

        public void setProfileImg(String profileImg) {
            this.profileImg = profileImg;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getDotNumber() {
            return dotNumber;
        }

        public void setDotNumber(String dotNumber) {
            this.dotNumber = dotNumber;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getSubUserType() {
            return subUserType;
        }

        public void setSubUserType(String subUserType) {
            this.subUserType = subUserType;
        }

        public Object getPhone() {
            return phone;
        }

        public void setPhone(Object phone) {
            this.phone = phone;
        }

        public String getOnlineStatus() {
            return onlineStatus;
        }

        public void setOnlineStatus(String onlineStatus) {
            this.onlineStatus = onlineStatus;
        }


        }
    }
