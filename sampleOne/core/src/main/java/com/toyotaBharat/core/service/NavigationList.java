package com.toyotaBharat.core.service;

import java.util.List;

import com.toyotaBharat.core.models.NavigationListModel;

public interface NavigationList {
      List<NavigationListModel> getNavList (String carModelName);   
}
