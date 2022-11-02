package com.wese.weseaddons.helper ;

import com.wese.weseaddons.interfaces.ListingFamily;


import java.lang.reflect.Method;
import java.lang.reflect.Field ;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FunctionNameListing{

	public static Map<String ,String> get(ListingFamily listingFamily){

		String className = listingFamily.getClass().getSimpleName();

	    Field[] fields = listingFamily.getClass().getDeclaredFields();

	    Map<String ,String> map = new HashMap<>();

	    for(Field f : fields){

	    	String variableName = f.getName() ;
			String methodName = getCorrespondingFunction(variableName);

	    	Class<?> c = f.getType();

	    	if(getWrapperTypes().contains(c)){

				map.putIfAbsent(variableName ,methodName);
				continue;
			}

			System.err.println("Name and package are "+c.getName()+" and variable name is "+methodName);
			map.put(c.getName() ,methodName);

	    }

        Map<String ,Map> objectMap = new HashMap<>();
		objectMap.put(className ,map);

        return map;
		
	}

	public static String getCorrespondingFunction(String variableName){

		StringBuilder stringBuilder = new StringBuilder("get");
		String val = detectCamelCasing(variableName);
		stringBuilder.append(val);

		return stringBuilder.toString();

	}


	public static String detectCamelCasing(String variableName){

		char[] chars = variableName.toCharArray();
		StringBuilder stringBuilder = new StringBuilder();
		chars[0] = Character.toUpperCase(chars[0]);

		stringBuilder.append(chars);

		return stringBuilder.toString();

	}

	public static boolean isClassGetter(String variableName){

		boolean hasDot = variableName.contains(".");
		return hasDot ;
	}


	private static Set<Class<?>> getWrapperTypes()
	{
		Set<Class<?>> ret = new HashSet<Class<?>>();
		ret.add(Boolean.class);
		ret.add(Character.class);
		ret.add(Byte.class);
		ret.add(Short.class);
		ret.add(Integer.class);
		ret.add(Long.class);
		ret.add(Float.class);
		ret.add(Double.class);
		ret.add(Void.class);
		ret.add(Enum.class);
		ret.add(String.class);

		return ret;
	}



}