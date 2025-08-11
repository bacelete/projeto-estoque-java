package com.myapp.estoque.enums;

public enum TipoUsuario {
    ADMIN("admin"),
    USER("user");

    private String role;

    TipoUsuario(String role) {
        this.role = role;
    }

    public String getTipo() {
        return role;
    }
}
