package com.wese.weseaddons.assetregister.pojo;

import com.wese.weseaddons.interfaces.PojoInterface;

public class AssetCategory implements PojoInterface{

    private String code ;
    private AssetClass assetClass;
    private String description ;
    private String cost ;
    private String accDepr ;
    private String depr ;
    private String revalue ;
    private String impairement ;
    private String profit ;
    private String defTaxBas ;
    private String defTaxIs ;

    @Override
    public String getSchema(){
        return "m_asset_categories";
    }

    public AssetCategory() {
    }

    public AssetCategory(String code, AssetClass assetClass, String description, String cost, String accDepr, String depr, String revalue, String impairement, String profit, String defTaxBas, String defTaxIs) {
        this.code = code;
        this.assetClass = assetClass;
        this.description = description;
        this.cost = cost;
        this.accDepr = accDepr;
        this.depr = depr;
        this.revalue = revalue;
        this.impairement = impairement;
        this.profit = profit;
        this.defTaxBas = defTaxBas;
        this.defTaxIs = defTaxIs;
    }

    @Override
    public Long getId() {
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public AssetClass getAssetClass() {
        return assetClass;
    }

    public void setAssetClass(AssetClass assetClass) {
        this.assetClass = assetClass;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getAccDepr() {
        return accDepr;
    }

    public void setAccDepr(String accDepr) {
        this.accDepr = accDepr;
    }

    public String getDepr() {
        return depr;
    }

    public void setDepr(String depr) {
        this.depr = depr;
    }

    public String getRevalue() {
        return revalue;
    }

    public void setRevalue(String revalue) {
        this.revalue = revalue;
    }

    public String getImpairement() {
        return impairement;
    }

    public void setImpairement(String impairement) {
        this.impairement = impairement;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getDefTaxBas() {
        return defTaxBas;
    }

    public void setDefTaxBas(String defTaxBas) {
        this.defTaxBas = defTaxBas;
    }

    public String getDefTaxIs() {
        return defTaxIs;
    }

    public void setDefTaxIs(String defTaxIs) {
        this.defTaxIs = defTaxIs;
    }
}
