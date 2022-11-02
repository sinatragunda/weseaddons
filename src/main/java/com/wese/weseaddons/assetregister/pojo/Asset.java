package com.wese.weseaddons.assetregister.pojo;

import com.wese.weseaddons.assetregister.enumeration.ASSET_TYPE;

public class Asset {

    private String assetNumber ;
    private String assetIdNumber ;
    private String description ;
    private ASSET_TYPE assetType ;
    private AssetCategory assetCategory ;
    private TaxCode taxCode ;
    private InputErrorCode inputErrorCode ;
    private DefferedTaxation defferedTaxation ;


    public Asset() {
    }

    public Asset(String assetNumber, String assetIdNumber, String description, ASSET_TYPE assetType, AssetCategory assetCategory, TaxCode taxCode, InputErrorCode inputErrorCode, DefferedTaxation defferedTaxation) {
        this.assetNumber = assetNumber;
        this.assetIdNumber = assetIdNumber;
        this.description = description;
        this.assetType = assetType;
        this.assetCategory = assetCategory;
        this.taxCode = taxCode;
        this.inputErrorCode = inputErrorCode;
        this.defferedTaxation = defferedTaxation;
    }

    public String getAssetNumber() {
        return assetNumber;
    }

    public void setAssetNumber(String assetNumber) {
        this.assetNumber = assetNumber;
    }

    public String getAssetIdNumber() {
        return assetIdNumber;
    }

    public void setAssetIdNumber(String assetIdNumber) {
        this.assetIdNumber = assetIdNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ASSET_TYPE getAssetType() {
        return assetType;
    }

    public void setAssetType(ASSET_TYPE assetType) {
        this.assetType = assetType;
    }

    public AssetCategory getAssetCategory() {
        return assetCategory;
    }

    public void setAssetCategory(AssetCategory assetCategory) {
        this.assetCategory = assetCategory;
    }

    public TaxCode getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(TaxCode taxCode) {
        this.taxCode = taxCode;
    }

    public InputErrorCode getInputErrorCode() {
        return inputErrorCode;
    }

    public void setInputErrorCode(InputErrorCode inputErrorCode) {
        this.inputErrorCode = inputErrorCode;
    }

    public DefferedTaxation getDefferedTaxation() {
        return defferedTaxation;
    }

    public void setDefferedTaxation(DefferedTaxation defferedTaxation) {
        this.defferedTaxation = defferedTaxation;
    }
}
