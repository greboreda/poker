package com.greboreda.poker.card;

import static com.greboreda.poker.card.CardBuilder.CardCreator.Offer.askSuitFor;

public class CardBuilder {
	
	@FunctionalInterface
	public interface AddSuit {
		Builder withSuit(Suit suit);
	}
	@FunctionalInterface
	public interface Builder {
		Card build();
	}
	private CardBuilder() {

	}
	public AddSuit withValue(Value value) {
		return suit -> () -> new Card(value, suit);
	}

	public static CardBuilder aCard() {
		return new CardBuilder();
	}

	/**
	 * This is an experiment, gives a lot of semantics about Card creation
	 * to make easy create Cards on demand in tests
	 * but has the inconvenient that an instance of CardCreator creates
	 * all possible cards to use only one.
	 */
	public static class CardCreator {

		private final CardBuilder aCard = aCard();

		public static CardCreator a() {
			return new CardCreator();
		}

		public final CardCreator.Offer ACE = askSuitFor(aCard.withValue(Value.ACE));
		public final CardCreator.Offer KING = askSuitFor(aCard.withValue(Value.KING));
		public final CardCreator.Offer QUEEN = askSuitFor(aCard.withValue(Value.QUEEN));
		public final CardCreator.Offer JACK = askSuitFor(aCard.withValue(Value.JACK));
		public final CardCreator.Offer TEN = askSuitFor(aCard.withValue(Value.TEN));
		public final CardCreator.Offer NINE = askSuitFor(aCard.withValue(Value.NINE));
		public final CardCreator.Offer EIGHT = askSuitFor(aCard.withValue(Value.EIGHT));
		public final CardCreator.Offer SEVEN = askSuitFor(aCard.withValue(Value.SEVEN));
		public final CardCreator.Offer SIX = askSuitFor(aCard.withValue(Value.SIX));
		public final CardCreator.Offer FIVE = askSuitFor(aCard.withValue(Value.FIVE));
		public final CardCreator.Offer FOUR = askSuitFor(aCard.withValue(Value.FOUR));
		public final CardCreator.Offer THREE = askSuitFor(aCard.withValue(Value.THREE));
		public final CardCreator.Offer TWO = askSuitFor(aCard.withValue(Value.TWO));

		public static class Offer {
			public final CardCreator.SuitCreator of;
			private Offer(CardCreator.SuitCreator of) {
				this.of = of;
			}
			static CardCreator.Offer askSuitFor(AddSuit addSuit) {
				return new CardCreator.Offer(new CardCreator.SuitCreator(addSuit));
			}
		}

		public static class SuitCreator {
			public final Card SPADES;
			public final Card DIAMONDS;
			public final Card CLUBS;
			public final Card HEARTS;
			private SuitCreator(AddSuit addSuit) {
				SPADES = addSuit.withSuit(Suit.SPADES).build();
				DIAMONDS = addSuit.withSuit(Suit.DIAMONDS).build();
				CLUBS = addSuit.withSuit(Suit.CLUBS).build();
				HEARTS = addSuit.withSuit(Suit.HEARTS).build();
			}
		}
	}
}

