package com.greboreda.poker.hand.rank.onepair;

import com.greboreda.poker.Comparision;
import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.rank.Rank;
import org.apache.commons.lang3.NotImplementedException;

import java.util.Arrays;

public class OnePair implements Rank {

	private final Value value;
	private final Value highKicker;
	private final Value secondKicker;
	private final Value thirdKicker;

	private OnePair(Value value, Value highKicker, Value secondKicker, Value thirdKicker) {
		check(value, highKicker, secondKicker, thirdKicker);
		this.value = value;
		this.highKicker = highKicker;
		this.secondKicker = secondKicker;
		this.thirdKicker = thirdKicker;
	}

	private void check(Value value, Value highKicker, Value secondKicker, Value thirdKicker) {
		if(!Value.areDistinct(Arrays.asList(value, highKicker, secondKicker, thirdKicker))) {
			throw new IllegalStateException("value and kickers must be different");
		}
		if(!highKicker.wins(secondKicker) || !secondKicker.wins(thirdKicker)) {
			throw new IllegalStateException("kickers order is not valid");
		}
	}

	public Value getValue() {
		return value;
	}

	public Value getHighKicker() {
		return highKicker;
	}

	public Value getSecondKicker() {
		return secondKicker;
	}

	public Value getThirdKicker() {
		return thirdKicker;
	}

	@Override
	public RankValue getRankValue() {
		return RankValue.ONE_PAIR;
	}

	@Override
	public Comparision compare(Rank another) {
		throw new NotImplementedException("not implemented yet");
	}

	public static OnePairBuilder create() {
		return new OnePairBuilder();
	}

	public static class OnePairBuilder {
		@FunctionalInterface
		public interface AddHighKicker {
			AddSecondKicker withHighKiker(Value highKicker);
		}
		@FunctionalInterface
		public interface AddSecondKicker {
			AddThirdKicker withSecondKicker(Value secondKicker);
		}
		@FunctionalInterface
		public interface AddThirdKicker {
			Builder withThirdKicker(Value thirdKicker);
		}
		@FunctionalInterface
		public interface Builder {
			OnePair build();
		}
		private OnePairBuilder() {

		}
		public AddHighKicker of(Value of) {
			return highKicker -> secondKicker -> thirdKicker ->  () -> new OnePair(of, highKicker, secondKicker, thirdKicker);
		}
	}
}
