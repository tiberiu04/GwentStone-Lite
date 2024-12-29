package utils;

import fileio.CardInput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Player {
    private int gamesPlayed = 0;

    private Card currentHero;

    private int mana;
    private int manaIncrement = 1;

    private int deckCount;
    private int cardsInDeckCount;
    private int currentDeckIndex;
    private ArrayList<Card> currentDeck;
    private ArrayList<ArrayList<Card>> deckList;

    private ArrayList<Card> cardsInHand = new ArrayList<>();
    private ArrayList<Card> cardsInFrontRow = new ArrayList<>();
    private ArrayList<Card> cardsInBackRow = new ArrayList<>();

    public Player() { }

    public int getMana() {
        return mana;
    }

    public void setMana(final int mana) {
        this.mana = mana;
    }

    public void incrementMana() {
        if (manaIncrement < Constants.TEN) {
            manaIncrement++;
        }
        mana += manaIncrement;
    }

    public void setManaIncrement(final int manaIncrement) {
        this.manaIncrement = manaIncrement;
    }

    public void subtractMana(final int consumedMana) {
        this.mana -= consumedMana;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(final int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public void setCardsInDeckCount(final int cardsInDeckCounts) {
        this.cardsInDeckCount = cardsInDeckCounts;
    }

    public void setDeckCount(final int deckCount) {
        this.deckCount = deckCount;
    }

    public int getCurrentDeckIndex() {
        return currentDeckIndex;
    }

    public void setCurrentDeckIndex(final int currentDeckIndex) {
        this.currentDeckIndex = currentDeckIndex;
    }

    public ArrayList<Card> getCurrentDeck() {
        return currentDeck;
    }

    public void setCurrentDeck(final ArrayList<Card> currentDeck) {
        this.currentDeck = currentDeck;
    }

    public ArrayList<ArrayList<Card>> getDeckList() {
        return deckList;
    }


    public void setDeckList(final ArrayList<ArrayList<CardInput>> deckList) {
        this.deckList = new ArrayList<>();
        for (ArrayList<CardInput> deck : deckList) {
            ArrayList<Card> parsedDeck = new ArrayList<>();

            for (CardInput card : deck) {
                parsedDeck.add(getParsedCard(card));
            }

            this.deckList.add(parsedDeck);
        }
    }

    public void resetCards() {
        cardsInHand = new ArrayList<>();
        cardsInFrontRow = new ArrayList<>();
        cardsInBackRow = new ArrayList<>();
    }

    public ArrayList<Card> getCardsInHand() {
        return cardsInHand;
    }


    public ArrayList<Card> getCardsInFrontRow() {
        return cardsInFrontRow;
    }

    public ArrayList<Card> getCardsInBackRow() {
        return cardsInBackRow;
    }

    public Card getCurrentHero() {
        return currentHero;
    }

    public void setCurrentHero(final Card currentHero) {
        this.currentHero = currentHero;
    }


    public void addCardInHand() {
        if (!currentDeck.isEmpty()) {
            cardsInHand.add(currentDeck.get(Constants.ZERO));
            removeCardFromDeck(Constants.ZERO);
        }
    }

    public void removeCardFromHand(final int index) {
        cardsInHand.remove(index);
    }

    public void placeCardWithIndex(final int index) {
        if (cardsInHand.isEmpty() || cardsInHand.size() <= index) {
            return;
        }

        Card card = cardsInHand.get(index);

        String cardName = card.getName();
        if (isCardEligibleForFrontRow(cardName)) {
            cardsInFrontRow.add(card);
        }

        if (isCardEligibleForBackRow(cardName)) {
            cardsInBackRow.add(card);
        }

        subtractMana(card.getMana());
        removeCardFromHand(index);
    }

    public boolean isCardEligibleForFrontRow(final String cardName) {
        return (cardName.matches(CardNames.THE_RIPPER)
                || cardName.matches(CardNames.MIRAJ)
                || cardName.matches(CardNames.GOLIATH)
                || cardName.matches(CardNames.WARDEN))
                && cardsInFrontRow.size() < Constants.FIVE;
    }

    public boolean isCardEligibleForBackRow(final String cardName) {
        return (cardName.matches(CardNames.SENTINEL)
                || cardName.matches(CardNames.BERSERKER)
                || cardName.matches(CardNames.THE_CURSED_ONE)
                || cardName.matches(CardNames.DISCIPLE))
                && cardsInBackRow.size() < Constants.FIVE;
    }

    public boolean isTheRowFullForCard(final String cardName) {
        return ((cardName.matches(CardNames.THE_RIPPER)
                || cardName.matches(CardNames.MIRAJ)
                || cardName.matches(CardNames.GOLIATH)
                || cardName.matches(CardNames.WARDEN))
                    && cardsInFrontRow.size() >= Constants.FIVE)
                || ((cardName.matches(CardNames.SENTINEL)
                || cardName.matches(CardNames.BERSERKER)
                || cardName.matches(CardNames.THE_CURSED_ONE)
                || cardName.matches(CardNames.DISCIPLE))
                    && cardsInBackRow.size() >= Constants.FIVE);
    }

    public void removeCardFromDeck(final int index) {
        if (!currentDeck.isEmpty() && currentDeck.size() > index) {
            currentDeck.remove(index);
        }
    }

    public void shuffleDeck(final String seed) {
        Random random = new Random(Long.parseLong(seed));
        Collections.shuffle(currentDeck, random);
    }

    public int getCardType(final String name) {
        switch (name) {
            case CardNames.SENTINEL, CardNames.GOLIATH,
                 CardNames.BERSERKER, CardNames.WARDEN,
                 CardNames.THE_RIPPER, CardNames.MIRAJ,
                 CardNames.THE_CURSED_ONE, CardNames.DISCIPLE:
                return Constants.ONE;

            case CardNames.LORD_ROYCE, CardNames.KING_MUDFACE,
                 CardNames.EMPRESS_THORINA, CardNames.GENERAL_KOCIORAW:
                return Constants.TWO;

            default:
                return Constants.ZERO;
        }
    }


    public Card getParsedCard(final CardInput card) {
        Card parsedCard;

        int cardType = getCardType(card.getName());
        if (cardType == Constants.ONE)
            parsedCard = new Minion();
        else
            parsedCard = new Hero();

        parsedCard.setName(card.getName());
        parsedCard.setHealth((cardType == Constants.TWO ? Constants.THIRTY : card.getHealth());
        parsedCard.setMana(card.getMana());
        parsedCard.setAttackDamage(card.getAttackDamage());
        parsedCard.setDescription(card.getDescription());
        parsedCard.setColors(card.getColors());

        return parsedCard;
    }

    public void removeStunFromCards() {

        for (Card card : cardsInBackRow) {
            card.setStunned(false);
        }

        for (Card card : cardsInFrontRow) {
            card.setStunned(false);
        }
    }

    public void resetCardAttacks() {
        for (Card card : cardsInBackRow) {
            card.setHasAttackedThisTurn(false);
        }

        for (Card card : cardsInFrontRow) {
            card.setHasAttackedThisTurn(false);
        }
    }

    public boolean checkRowStatus(final String row) {
        return (row.matches("backRow") ? cardsInBackRow.size() == Constants.FIVE
                                             : cardsInFrontRow.size() == Constants.FIVE);
    }

    public void replaceDeck(final ArrayList<Card> deck) {
        ArrayList<Card> newDeck = new ArrayList<>();

        for (Card card : deck) {
            Card newCard;
            if ((getCardType(card.getName()) == 1)
                newCard = new Minion();
            newCard.setName(card.getName());
            newCard.setMana(card.getMana());
            newCard.setHealth(card.getHealth());
            newCard.setDescription(card.getDescription());
            newCard.setColors(card.getColors());
            newCard.setAttackDamage(card.getAttackDamage());

            newDeck.add(newCard);
        }

        this.currentDeck = newDeck;
    }
}
