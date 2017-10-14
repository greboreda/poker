package com.greboreda.poker.hand.rank;

import com.greboreda.poker.card.Suit;
import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.HandFactory;
import com.greboreda.poker.hand.rank.fourofakind.FourOfAKind;
import com.greboreda.poker.hand.rank.royalflush.RoyalFlush;

public class RankFactory {

	public static RoyalFlush createRoyalFlush() {
		return new RoyalFlush();
	}

	public static FourOfAKind createFourOfAKind() {
		return (FourOfAKind) HandFactory.createFourOfAKind(Value.ACE, Suit.DIAMONDS, Value.KING).getRank();
	}

}
