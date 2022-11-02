package com.wese.weseaddons.helper;


import com.wese.weseaddons.enumerations.CLASS_TYPE;
import com.wese.weseaddons.interfaces.ExecutableClass;
import com.wese.weseaddons.interfaces.ListingFamily;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class ExecuteMethod{


    public static Object invoke(ExecutableClass executableClass , String className ,String methodName) {

        Method method = null ;

        /// the we should execute what ever function is here
        try {
            
            System.out.print("Executable class is "+executableClass.toString());
            
            System.out.println("Method to excute is "+methodName+" class name is "+className);

            method = executableClass.getClass().getMethod(methodName);
            return method.invoke(executableClass);

       }

        catch (IllegalAccessException e) {
            // TODO: handle exception
        }

        catch (NoSuchMethodException n){
            n.printStackTrace();
        }

        catch (InvocationTargetException i){
            i.printStackTrace();
        }

        return null ;
    }

    public static Object classCasting(String className , CLASS_TYPE classType){

        Class<?> classObject = null ;
        ExecutableClass executableClass = null ;
        ListingFamily listingFamily = null ;

        try{

            classObject = Class.forName(className);

            switch (classType){
                case LISTING:
                    listingFamily = (ListingFamily)classObject.newInstance();
                    return listingFamily ;

                case EXECUTABLE:
                    executableClass = (ExecutableClass) classObject.newInstance();
                    return executableClass ;
            }

        }

        catch (ClassNotFoundException | InstantiationException | IllegalAccessException e){

            e.printStackTrace();

        }

        return null;
    }


}

