package com.greboreda.poker.hand.rank.straight;

import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.rank.Rank;
import org.apache.commons.lang3.Validate;

public class Straight implements Rank {

	private final Value high;

	private Straight(Value high) {
		Validate.notNull(high);
		this.high = high;
	}

	public Value getHigh() {
		return high;
	}

	@Override
	public RankValue getRankValue() {
		return RankValue.STRAIGHT;
	}

	public static StraightBuilder create() {
		return new StraightBuilder();
	}

	public static class StraightBuilder {
		@FunctionalInterface
		public interface Builder {
			Straight build();
		}
		private StraightBuilder() {

		}
		public Builder withHigh(Value high) {
			return () -> new Straight(high);
		}
	}

}
