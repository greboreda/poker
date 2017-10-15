package com.greboreda.poker.hand.rank.straightflush;

import com.greboreda.poker.Comparision;
import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.rank.Rank;
import org.apache.commons.lang3.NotImplementedException;

public class StraightFlush implements Rank {

	private final Value high;

	private StraightFlush(Value high) {
		this.high = high;
	}

	public Value getHigh() {
		return high;
	}

	@Override
	public RankValue getRankValue() {
		return RankValue.STRAIGHT_FLUSH;
	}

	@Override
	public Comparision compare(Rank another) {
		throw new NotImplementedException("not implemented yet");
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
