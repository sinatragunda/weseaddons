package com.wese.weseaddons.assetregister.pojo;

public class DefferedTaxation {

    private double ytdMovement ;
    private double closingBalance ;
    private double mtdMovement ;

    public DefferedTaxation() {
    }

    public DefferedTaxation(double ytdMovement, double closingBalance, double mtdMovement) {
        this.ytdMovement = ytdMovement;
        this.closingBalance = closingBalance;
        this.mtdMovement = mtdMovement;
    }

    public double getYtdMovement() {
        return ytdMovement;
    }

    public void setYtdMovement(double ytdMovement) {
        this.ytdMovement = ytdMovement;
    }

    public double getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(double closingBalance) {
        this.closingBalance = closingBalance;
    }

    public double getMtdMovement() {
        return mtdMovement;
    }

    public void setMtdMovement(double mtdMovement) {
        this.mtdMovement = mtdMovement;
    }
}
