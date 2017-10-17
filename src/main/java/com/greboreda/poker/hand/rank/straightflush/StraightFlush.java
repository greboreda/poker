package com.greboreda.poker.hand.rank.straightflush;

import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.rank.Rank;

public class StraightFlush implements Rank {

	private final Value high;

	private StraightFlush(Value high) {
		if(high.equals(Value.ACE)) {
			throw new IllegalArgumentException("straight flush can not have ACE as high value");
		}
		this.high = high;
	}

	public Value getHigh() {
		return high;
	}

	@Override
	public RankValue getRankValue() {
		return RankValue.STRAIGHT_FLUSH;
	}

	public static StraightFlushBuilder create() {
		return new StraightFlushBuilder();
	}

	public static class StraightFlushBuilder {
		@FunctionalInterface
		public interface Builder {
			StraightFlush build();
		}
		private StraightFlushBuilder() {

		}
		public Builder withHigh(Value high) {
			return () -> new StraightFlush(high);
		}
	}
}
