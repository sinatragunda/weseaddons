package com.wese.weseaddons.bereaudechange.helper;

import com.wese.weseaddons.bereaudechange.impl.FxDAOService;

public class ForeignKeyHelper {

    public static Object get(FxDAOService fxDAOService ,long id){

        Object object = fxDAOService.find(id);
        return object ;

    }

}
