package com.greboreda.poker.hand.rank;

import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.rank.fourofakind.FourOfAKind;
import com.greboreda.poker.hand.rank.royalflush.RoyalFlush;
import com.greboreda.poker.hand.rank.straightflush.StraightFlush;

public class RankFactory {

	public static RoyalFlush createRoyalFlush() {
		return new RoyalFlush();
	}

	public static StraightFlush createStraightFlush() {
		return StraightFlush.create()
				.withHigh(Value.KING)
				.build();

	}

	public static FourOfAKind createFourOfAKind() {
		return FourOfAKind.create()
				.of(Value.ACE)
				.withKicker(Value.EIGHT)
				.build();
	}

	/*
	public static FullHouse createFullHouse() {

	}

	public static Flush createFlush() {

	}

	public static Straight createStraight() {

	}

	public static ThreeOfAKind createThreeOfAKind() {

	}

	public static TwoPair createTwoPair() {

	}

	public static OnePair createOnePair() {

	}

	public static HighCard createHighCard() {

	}
	*/
}
