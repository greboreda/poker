package com.greboreda.poker.hand;

import com.greboreda.poker.card.Card;
import com.greboreda.poker.card.Suit;
import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.Hand;

public class HandFactory {

	public static Hand createRoyalFlush(Suit suit) {
		final Card ace = Card.create().withValue(Value.ACE).withSuit(suit).build();
		final Card king = Card.create().withValue(Value.KING).withSuit(suit).build();
		final Card queen = Card.create().withValue(Value.QUEEN).withSuit(suit).build();
		final Card jack = Card.create().withValue(Value.JACK).withSuit(suit).build();
		final Card ten = Card.create().withValue(Value.TEN).withSuit(suit).build();
		return new Hand(ace, king, queen, jack, ten);
	}

	public static Hand createFourOfAKind(Value value, Suit kickerSuit, Value kickerValue) {
		final Card quad1 = Card.create().withValue(value).withSuit(Suit.HEARTS).build();
		final Card quad2 = Card.create().withValue(value).withSuit(Suit.CLUBS).build();
		final Card quad3 = Card.create().withValue(value).withSuit(Suit.DIAMONDS).build();
		final Card quad4 = Card.create().withValue(value).withSuit(Suit.SPADES).build();
		final Card kicker = Card.create().withValue(kickerValue).withSuit(kickerSuit).build();
		return new Hand(quad1, quad2, quad3, quad4, kicker);
	}

	public static Hand createFullHouse(Value valueOfTrio, Value valueOfPair) {
		final Card trio1 = Card.create().withValue(valueOfTrio).withSuit(Suit.DIAMONDS).build();
		final Card trio2 = Card.create().withValue(valueOfTrio).withSuit(Suit.CLUBS).build();
		final Card trio3 = Card.create().withValue(valueOfTrio).withSuit(Suit.SPADES).build();
		final Card pair1 = Card.create().withValue(valueOfPair).withSuit(Suit.HEARTS).build();
		final Card pair2 = Card.create().withValue(valueOfPair).withSuit(Suit.SPADES).build();
		return new Hand(trio1, trio2, trio3, pair1, pair2);
	}


}