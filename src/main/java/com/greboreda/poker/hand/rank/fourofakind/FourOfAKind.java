package com.greboreda.poker.hand.rank.fourofakind;

import com.greboreda.poker.card.Value;
import com.greboreda.poker.Comparision;
import com.greboreda.poker.hand.rank.Rank;
import org.apache.commons.lang3.Validate;

public class FourOfAKind implements Rank {

	private final Value value;
	private final Value kicker;

	private FourOfAKind(Value value, Value kicker) {
		Validate.notNull(value);
		Validate.notNull(kicker);
		if(value.equals(kicker)) {
			throw new IllegalStateException("value and kicker must be different");
		}
		this.value = value;
		this.kicker = kicker;
	}

	public Value getValue() {
		return value;
	}

	public Value getKicker() {
		return kicker;
	}

	@Override
	public RankValue getRankValue() {
		return RankValue.FOUR_OF_A_KIND;
	}

	@Override
	public Comparision compare(Rank another) {
		Validate.notNull(another);
		final boolean anotherIsRoyalFlush = RankValue.ROYAL_FLUSH.equals(another.getRankValue());
		final boolean anotherIsFourOfAKind = RankValue.FOUR_OF_A_KIND.equals(another.getRankValue());
		if(anotherIsRoyalFlush) {
			return Comparision.LOOSE;
		} else if (!anotherIsFourOfAKind) {
			return Comparision.WIN;
		}
		return compareWithAnotherFourOfAKind((FourOfAKind) another);
	}

	private Comparision compareWithAnotherFourOfAKind(FourOfAKind anotherFourOfAKind) {
		final Comparision valueComparision = this.getValue().compare(anotherFourOfAKind.getValue());
		if(valueComparision.isWin() || valueComparision.isLoose()) {
			return valueComparision;
		}
		return this.getKicker().compare(anotherFourOfAKind.getKicker());
	}

	public static FourOfAKindBuilder create() {
		return new FourOfAKindBuilder();
	}

	public static class FourOfAKindBuilder {
		@FunctionalInterface
		public interface AddKicker {
			Builder withKicker(Value value);
		}
		@FunctionalInterface
		public interface Builder {
			FourOfAKind build();
		}
		private FourOfAKindBuilder() {

		}
		public AddKicker of(Value value) {
			return kicker -> () -> new FourOfAKind(value, kicker);
		}
	}
}
