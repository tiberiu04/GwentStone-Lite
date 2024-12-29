package utils;

public final class Exceptions {
    public static final String NOT_ENOUGH_MANA_TO_PLACE_CARD =
                        "Not enough mana to place card on table.";

    public static final String ROW_IS_FULL =
                        "Cannot place card on table since row is full.";

    public static final String ROW_NOT_FROM_ENEMY =
                        "Chosen row does not belong to the enemy.";

    public static final String NO_STEAL_ROW_FULL =
                        "Cannot steal enemy card since the player's row is full.";

    public static final String NO_CARD_AT_POSITION =
                        "No card available at that position.";

    public static final String ATTACK_OWN_CARD =
                        "Attacked card does not belong to the enemy.";

    public static final String CARD_ALREADY_ATTACKED =
                        "Attacker card has already attacked this turn.";

    public static final String FROZEN_ATTACKER =
                        "Attacker card is frozen.";

    public static final String TANK_CARD_NOT_ATTACKED =
                        "Attacked card is not of type 'Tank'.";

    public static final String CARD_NOT_OWN =
                        "Attacked card does not belong to the current player.";

    public static final String NOT_ENOUGH_MANA_FOR_HERO =
                        "Not enough mana to use hero's ability.";

    public static final String HERO_ALREADY_ATTACKED =
                        "Hero has already attacked this turn.";

    public static final String ROW_DOES_NOT_BELONG_TO_ENEMY =
                        "Selected row does not belong to the enemy.";

    public static final String ROW_IS_NOT_OWN =
                        "Selected row does not belong to the current player.";
    private Exceptions() { }
}
