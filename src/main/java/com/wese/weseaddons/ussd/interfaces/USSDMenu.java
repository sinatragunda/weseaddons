package com.wese.weseaddons.ussd.interfaces;

import com.wese.weseaddons.ussd.helper.MenuAttributes;

import java.util.List;

public interface USSDMenu {

    String getMenuList();
    List<String> getMenuAsStringList();
    boolean isOptionExecutable(int option);
    String getExecutableClassName(int option);
    List<MenuAttributes> getMenuAttributesList();

    /// some code above can be blanked out

}
