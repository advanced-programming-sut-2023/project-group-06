package org.example.View;

import org.example.View.GameMenus.ShopMenu;

public enum MenuType {
    SHOP_MENU(new ShopMenu()),
    LOGIN_MENU(new LoginMenu()),
    SIGN_UP_MENU(new SignUpMenu()),
    START_MENU(new StartMenu()),
    MAIN_MENU(new MainMenu());

    public Menu menu;
    MenuType(Menu menu){
        this.menu = menu;
    }
}
