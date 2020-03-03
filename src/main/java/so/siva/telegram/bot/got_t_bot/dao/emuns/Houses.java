package so.siva.telegram.bot.got_t_bot.dao.emuns;

public enum Houses {
    BARATHEON("Баратеон", "bar"),
    LANNISTER("Ланнистер", "lan"),
    STARK("Старк", "str"),
    GREYJOY("Грейджой", "grj"),
    TYRELL("Тирелл", "tyr"),
    MARTELL("Мартелл", "mar"),
    ARRYN("Аррен", "arr"),
    TARGARIEN("Таргариен", "tar");

    private String rusName;

    /**
     * Для консольных команд
     */
    private String domain;

    Houses(String rusName, String domain) {
        this.rusName = rusName;
        this.domain = domain;
    }

    public Houses getKey(){
        return this.getKey();
    }

    public String getRusName(){
        return this.rusName;
    }

    public String getDomain() {
        return this.domain;
    }
}
