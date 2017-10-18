package com.greboreda.poker.hand.util;

import com.greboreda.poker.hand.Hand;

import static com.greboreda.poker.card.CardBuilder.CardCreator.a;
import static com.greboreda.poker.hand.util.HandBuilder.hand;

public class HandRepository {

	public static Hand aRoyalFlush() {
		return hand()
				.with(a().ACE.of.DIAMONDS)
				.with(a().KING.of.DIAMONDS)
				.with(a().QUEEN.of.DIAMONDS)
				.with(a().JACK.of.DIAMONDS)
				.with(a().TEN.of.DIAMONDS);
	}
	public static Hand aStraightFlush() {
		return hand()
				.with(a().KING.of.DIAMONDS)
				.with(a().QUEEN.of.DIAMONDS)
				.with(a().JACK.of.DIAMONDS)
				.with(a().TEN.of.DIAMONDS)
				.with(a().NINE.of.DIAMONDS);
	}
	public static Hand aFourOfAKind() {
		return hand()
				.with(a().ACE.of.DIAMONDS)
				.with(a().ACE.of.SPADES)
				.with(a().ACE.of.CLUBS)
				.with(a().ACE.of.HEARTS)
				.with(a().KING.of.DIAMONDS);
	}
	public static Hand aFullHouse() {
		return hand()
				.with(a().ACE.of.DIAMONDS)
				.with(a().ACE.of.SPADES)
				.with(a().ACE.of.CLUBS)
				.with(a().KING.of.HEARTS)
				.with(a().KING.of.DIAMONDS);
	}
	public static Hand aFlush() {
		return hand()
				.with(a().TEN.of.CLUBS)
				.with(a().EIGHT.of.CLUBS)
				.with(a().SIX.of.CLUBS)
				.with(a().FOUR.of.CLUBS)
				.with(a().TWO.of.CLUBS);
	}
	public static Hand aStraight() {
		return hand()
				.with(a().SEVEN.of.DIAMONDS)
				.with(a().SIX.of.SPADES)
				.with(a().FIVE.of.CLUBS)
				.with(a().FOUR.of.HEARTS)
				.with(a().THREE.of.DIAMONDS);
	}
	public static Hand aThreeOfAKind() {
		return hand()
				.with(a().ACE.of.DIAMONDS)
				.with(a().ACE.of.SPADES)
				.with(a().ACE.of.CLUBS)
				.with(a().KING.of.HEARTS)
				.with(a().TWO.of.DIAMONDS);
	}
	public static Hand aTwoPair() {
		return hand()
				.with(a().ACE.of.DIAMONDS)
				.with(a().ACE.of.SPADES)
				.with(a().TWO.of.CLUBS)
				.with(a().KING.of.HEARTS)
				.with(a().KING.of.DIAMONDS);
	}
	public static Hand aOnePair() {
		return hand()
				.with(a().ACE.of.DIAMONDS)
				.with(a().ACE.of.SPADES)
				.with(a().TWO.of.CLUBS)
				.with(a().KING.of.HEARTS)
				.with(a().FOUR.of.DIAMONDS);
	}
	public static Hand aHighCard() {
		return hand()
				.with(a().TEN.of.DIAMONDS)
				.with(a().SEVEN.of.SPADES)
				.with(a().TWO.of.CLUBS)
				.with(a().KING.of.HEARTS)
				.with(a().FOUR.of.DIAMONDS);
	}
}
