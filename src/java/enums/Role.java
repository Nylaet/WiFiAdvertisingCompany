/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enums;

/**
 *
 * @author Panker-RDP
 */
public enum Role {
    ADMIN("Администратор"), MANAGER("Менеджер"), CLIENT("Клиент");
    private String name;
    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
