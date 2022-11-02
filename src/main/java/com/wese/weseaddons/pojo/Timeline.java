/*

    Created by Sinatra Gunda
    At 9:24 AM on 6/22/2021

*/
package com.wese.weseaddons.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Timeline {

    public Integer submittedOnDate[];
    public String submittedByUsername;
    public String submittedByFirstname;
    public String submittedByLastname;
    public Integer approvedDate[];
    public String approvedByUsername;
    public String approvedByFirstname;
    public String approvedByLastname;
    public Integer activatedDate[];
    public Integer expectedMaturityDate[] ;


    public Timeline(){}

    public Integer[] getSubmittedOnDate() {
        return submittedOnDate;
    }

    public void setSubmittedOnDate(Integer[] submittedOnDate) {
        this.submittedOnDate = submittedOnDate;
    }

    public String getSubmittedByUsername() {
        return submittedByUsername;
    }

    public void setSubmittedByUsername(String submittedByUsername) {
        this.submittedByUsername = submittedByUsername;
    }

    public String getSubmittedByFirstname() {
        return submittedByFirstname;
    }

    public void setSubmittedByFirstname(String submittedByFirstname) {
        this.submittedByFirstname = submittedByFirstname;
    }

    public String getSubmittedByLastname() {
        return submittedByLastname;
    }

    public void setSubmittedByLastname(String submittedByLastname) {
        this.submittedByLastname = submittedByLastname;
    }

    public Integer[] getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Integer[] approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getApprovedByUsername() {
        return approvedByUsername;
    }

    public void setApprovedByUsername(String approvedByUsername) {
        this.approvedByUsername = approvedByUsername;
    }

    public String getApprovedByFirstname() {
        return approvedByFirstname;
    }

    public void setApprovedByFirstname(String approvedByFirstname) {
        this.approvedByFirstname = approvedByFirstname;
    }

    public String getApprovedByLastname() {
        return approvedByLastname;
    }

    public void setApprovedByLastname(String approvedByLastname) {
        this.approvedByLastname = approvedByLastname;
    }

    public Integer[] getActivatedDate() {
        return activatedDate;
    }

    public void setActivatedDate(Integer[] activatedDate) {
        this.activatedDate = activatedDate;
    }

    public Integer[] getExpectedMaturityDate() {
        return expectedMaturityDate;
    }

    public void setExpectedMaturityDate(Integer[] expectedMaturityDate) {
        this.expectedMaturityDate = expectedMaturityDate;
    }
}
