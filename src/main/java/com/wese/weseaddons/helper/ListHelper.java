/*Created by Sinatra Gunda
  At 23:31 on 7/22/2020 */

package com.wese.weseaddons.helper;

import java.util.List;

public class ListHelper {

    public static <T> T get(List<T> list , int index){

        if(list.isEmpty()){
            return null ;
        }

        int size = list.size() ;

        if(index > (size-1)){
            return null ;
        }

        return list.get(index);
    }
}
