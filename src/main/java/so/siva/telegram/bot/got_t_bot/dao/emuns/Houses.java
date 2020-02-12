package so.siva.telegram.bot.got_t_bot.dao.emuns;

public enum Houses {
    BARATHEON("Баратеон"),
    LANNISTER("Ланнистер"),
    STARK("Старк"),
    GREYJOY("Грейджой"),
    TYRELL("Тирелл"),
    MARTELL("Мартелл"),
    ARRYN("Аррен"),
    TARGARIEN("Таргариен");

    private String rusName;

    Houses(String rusName) {
        this.rusName = rusName;
    }

    public Houses getKey(){
        return this.getKey();
    }

    public String getRusName(){
        return this.rusName;
    }
}
