
package com.wese.weseaddons.helper ;

import com.wese.weseaddons.interfaces.IdIndexedClass;
import com.wese.weseaddons.interfaces.Listable;

import java.util.Iterator;

public class IdListHelper{

	public static IdIndexedClass find(Listable listable, long id){

		IdIndexedClass idIndexedClass = null ;

		Iterator iterator = listable.getList().iterator();

		while(iterator.hasNext()){

			idIndexedClass = (IdIndexedClass) iterator.next();

			if(id==idIndexedClass.getId()){

				return idIndexedClass ;
			}

		}

		return null ;

	}
}