package so.siva.telegram.bot.got_t_bot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "house.cards", ignoreUnknownFields = false)
public class InfoHouseCardsCommandsConfig {

    private Map<String, Deck> deck;

    public Map<String, Deck> getDeck() {
        return deck;
    }

    public void setDeck(Map<String, Deck> deck) {
        this.deck = deck;
    }
    public static class Deck {
        private List<House> houses;

        public List<House> getHouses() {
            return houses;
        }

        public void setHouses(List<House> houses) {
            this.houses = houses;
        }
    }

    public static class House{
        private String houseName;
        private List<Card> cards;

        public String getHouseName() {
            return houseName;
        }

        public void setHouseName(String houseName) {
            this.houseName = houseName;
        }

        public List<Card> getCards() {
            return cards;
        }

        public void setCards(List<Card> cards) {
            this.cards = cards;
        }
    }

    public static class Card{
        private String cardName;
        private String fileId;

        public String getCardName() {
            return cardName;
        }

        public void setCardName(String cardName) {
            this.cardName = cardName;
        }

        public String getFileId() {
            return fileId;
        }

        public void setFileId(String fileId) {
            this.fileId = fileId;
        }
    }
}
