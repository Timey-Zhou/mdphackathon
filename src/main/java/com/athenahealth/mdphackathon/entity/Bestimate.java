package com.athenahealth.mdphackathon.entity;

public class Bestimate {
    private int universalClaimCount;
    private int thisInsuranceClaimCount;
    private String insurance;
    private String cpt;

    public Bestimate(int universalClaimCount, int thisInsuranceClaimCount, String insurance,
                     String cpt) {
        this.universalClaimCount = universalClaimCount;
        this.thisInsuranceClaimCount = thisInsuranceClaimCount;
        this.insurance = insurance;
        this.cpt = cpt;
    }

    public int getUniversalClaimCount() {
        return universalClaimCount;
    }

    public void setUniversalClaimCount(int universalClaimCount) {
        this.universalClaimCount = universalClaimCount;
    }

    public int getThisInsuranceClaimCount() {
        return thisInsuranceClaimCount;
    }

    public void setThisInsuranceClaimCount(int thisInsuranceClaimCount) {
        this.thisInsuranceClaimCount = thisInsuranceClaimCount;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public String getCpt() {
        return cpt;
    }

    public void setCpt(String cpt) {
        this.cpt = cpt;
    }
}