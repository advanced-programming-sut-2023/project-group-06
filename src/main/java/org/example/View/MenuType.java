package org.example.View;

import org.example.View.GameMenus.*;

public enum MenuType {
    SHOP_MENU(new ShopMenu()),
    LOGIN_MENU(new LoginMenu()),
    SIGN_UP_MENU(new SignUpMenu()),
    START_MENU(new StartMenu()),
    MAIN_MENU(new MainMenu()),
    GAME_MENU(new GameMenu()),
    PROFILE_MENU(new ProfileMenu()),
    MAP_MENU(new MapMenu()),
    TRADE_MENU(new TradeMenu()),
    KINGDOM_MENU(new KingdomMenu()),
    ;

    public Menu menu;
    MenuType(Menu menu){
        this.menu = menu;
    }
}
