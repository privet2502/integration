package app.integration.models.enumiration;

import lombok.ToString;

@ToString

public enum FieldNames {
    datetime("Время оплаты", "datetime"),
    reminder("Остаток", "remainder");

    String name;
    String filed_name;

    FieldNames(String s, String filed_name) {
        this.name=s;
        this.filed_name=filed_name;
    }

    public String getName() {
        return name;
    }

    public String getFiled_name() {
        return filed_name;
    }
}
