package com.greboreda.poker.hand.rank.fourofakind;

import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.rank.Rank;
import org.apache.commons.lang3.Validate;

import java.util.Arrays;

public class FourOfAKind implements Rank {

	private final Value value;
	private final Value kicker;

	private FourOfAKind(Value value, Value kicker) {
		Validate.notNull(value);
		Validate.notNull(kicker);
		if(!Value.areDistinct(Arrays.asList(value, kicker))) {
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
