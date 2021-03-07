package br.edu.ifba.jogomemoria.user.enums;

public enum Authority {

    PANEL (1, "ROLE_PANEL"),
    PANEL_CARDS (2, "ROLE_PANEL_CARDS"),
    PANEL_USERS (3, "ROLE_PANEL_USERS");

    private Integer code;
    private String description;

    Authority(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static Authority toEnum (Integer code) {

        if (code == null) {
            return null;
        }

        for (Authority authority : Authority.values()) {

            if (authority.code.equals(code)) {
                return authority;
            }
        }

        throw new IllegalArgumentException("Código inválido: " + code);
    }
}
