package de.fionera.storagenetwork.menu;

public interface CustomMenuTemplate {
    default void registerMenu(CustomMenuRegistry registry) {}

    default CustomMenu renderMenu(CustomMenuRegistry registry) {
        return null;
    }
}
