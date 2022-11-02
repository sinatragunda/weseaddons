/*Created by Sinatra Gunda
  At 5:49 AM on 2/25/2020 */

package com.wese.weseaddons.crb.pojo;

import com.wese.weseaddons.enumerations.TYPE_OF_RESOURCE;

public class SyncWithCrb {

    private long id ;
    private long resourceId ;
    private TYPE_OF_RESOURCE typeOfResource ;

    public SyncWithCrb() {
    }

    public SyncWithCrb(long id, long resourceId, TYPE_OF_RESOURCE typeOfResource) {
        this.id = id;
        this.resourceId = resourceId;
        this.typeOfResource = typeOfResource;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getResourceId() {
        return resourceId;
    }

    public void setResourceId(long resourceId) {
        this.resourceId = resourceId;
    }

    public TYPE_OF_RESOURCE getTypeOfResource() {
        return typeOfResource;
    }

    public void setTypeOfResource(TYPE_OF_RESOURCE typeOfResource) {
        this.typeOfResource = typeOfResource;
    }
}
