package com.greboreda.poker.hand.rank;

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

import static com.greboreda.poker.card.Value.ACE;
import static com.greboreda.poker.card.Value.EIGHT;
import static com.greboreda.poker.card.Value.KING;
import static com.greboreda.poker.card.Value.SEVEN;
import static com.greboreda.poker.card.Value.SIX;
import static com.greboreda.poker.card.Value.TEN;
import static com.greboreda.poker.card.Value.THREE;
import static com.greboreda.poker.card.Value.TWO;
import static com.greboreda.poker.card.Value.valueStream;

public class RankRepository {

	public static RoyalFlush createRoyalFlush() {
		return new RoyalFlush();
	}

	public static StraightFlush createStraightFlush() {
		return StraightFlush.create()
				.withHigh(valueStream()
						.filter(v -> v != ACE)
						.filter(v -> v.getWeight() >= SIX.getWeight())
						.findAny()
						.orElse(KING))
				.build();
	}

	public static FourOfAKind createFourOfAKind() {
		return FourOfAKind.create()
				.of(ACE)
				.withKicker(EIGHT)
				.build();
	}

	public static FullHouse createFullHouse() {
		return FullHouse.create()
				.of(ACE)
				.over(TEN)
				.build();
	}

	public static Flush createFlush() {
		return Flush.create()
				.withKicker(ACE)
				.withKicker(TEN)
				.withKicker(SIX)
				.withKicker(THREE)
				.withKicker(TWO)
				.build();
	}

	public static Straight createStraight() {
		return Straight.create()
				.withHigh(ACE)
				.build();
	}

	public static ThreeOfAKind createThreeOfAKind() {
		return ThreeOfAKind.create()
				.of(THREE)
				.withHighKicker(KING)
				.withLowKicker(TEN)
				.build();
	}

	public static TwoPair createTwoPair() {
		return TwoPair.create()
				.withHighPair(TEN)
				.withLowPair(THREE)
				.withKicker(SIX)
				.build();
	}

	public static OnePair createOnePair() {
		return OnePair.create()
				.of(TWO)
				.withHighKiker(KING)
				.withSecondKicker(TEN)
				.withThirdKicker(THREE)
				.build();
	}

	public static HighCard createHighCard() {
		return HighCard.create()
				.of(KING)
				.withSecond(TEN)
				.withThird(SEVEN)
				.withFourth(THREE)
				.withFifth(TWO)
				.build();
	}

}
