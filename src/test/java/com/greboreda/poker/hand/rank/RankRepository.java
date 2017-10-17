package com.greboreda.poker.hand.rank;

import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.rank.flush.Flush;
import com.greboreda.poker.hand.rank.fourofakind.FourOfAKind;
import com.greboreda.poker.hand.rank.fullhouse.FullHouse;
import com.greboreda.poker.hand.rank.highcard.HighCard;
import com.greboreda.poker.hand.rank.onepair.OnePair;
import com.greboreda.poker.hand.rank.royalflush.RoyalFlush;
import com.greboreda.poker.hand.rank.straight.Straight;
import com.greboreda.poker.hand.rank.straightflush.StraightFlush;
import com.greboreda.poker.hand.rank.threeofakind.ThreeOfAKind;
import com.greboreda.poker.hand.rank.twopair.TwoPair;

public class RankRepository {

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


	public static FullHouse createFullHouse() {
		return FullHouse.create()
				.of(Value.ACE)
				.over(Value.TEN)
				.build();
	}

	public static Flush createFlush() {
		return Flush.create()
				.withKicker(Value.ACE)
				.withKicker(Value.TEN)
				.withKicker(Value.SIX)
				.withKicker(Value.THREE)
				.withKicker(Value.TWO)
				.build();
	}

	public static Straight createStraight() {
		return Straight.create()
				.withHigh(Value.ACE)
				.build();
	}

	public static ThreeOfAKind createThreeOfAKind() {
		return ThreeOfAKind.create()
				.of(Value.THREE)
				.withHighKicker(Value.KING)
				.withLowKicker(Value.TEN)
				.build();
	}

	public static TwoPair createTwoPair() {
		return TwoPair.create()
				.withHighPair(Value.TEN)
				.withLowPair(Value.THREE)
				.withKicker(Value.SIX)
				.build();
	}

	public static OnePair createOnePair() {
		return OnePair.create()
				.of(Value.TWO)
				.withHighKiker(Value.KING)
				.withSecondKicker(Value.TEN)
				.withThirdKicker(Value.THREE)
				.build();
	}

	public static HighCard createHighCard() {
		return HighCard.create()
				.of(Value.KING)
				.withSecond(Value.TEN)
				.withThird(Value.SEVEN)
				.withFourth(Value.THREE)
				.withFifth(Value.TWO)
				.build();
	}

}
