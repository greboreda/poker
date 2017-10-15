package com.greboreda.poker.hand.rank.flush;

import com.greboreda.poker.Comparision;
import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.rank.Rank;
import org.apache.commons.lang3.NotImplementedException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


public class Flush implements Rank {

	private final Value highKicker;
	private final Value secondKicker;
	private final Value thirdKicker;
	private final Value fourthKicker;
	private final Value fifthKicker;

	private Flush(Value highKicker, Value secondKicker, Value thirdKicker, Value fourthKicker, Value fifthKicker) {
		checkAreDistinctAndNotConsecutive(highKicker, secondKicker, thirdKicker, fourthKicker, fifthKicker);
		checkKickersOrder(highKicker, secondKicker, thirdKicker, fourthKicker, fifthKicker);
		this.highKicker = highKicker;
		this.secondKicker = secondKicker;
		this.thirdKicker = thirdKicker;
		this.fourthKicker = fourthKicker;
		this.fifthKicker = fifthKicker;
	}

	private void checkKickersOrder(Value highKicker, Value secondKicker, Value thirdKicker, Value fourthKicker, Value fifthKicker) {
		if( !highKicker.wins(secondKicker)
				|| !secondKicker.wins(thirdKicker)
				|| !thirdKicker.wins(fourthKicker)
				|| !fourthKicker.wins(fifthKicker) ) {
			throw new IllegalStateException("kickers order is not valid");
		}
	}

	private void checkAreDistinctAndNotConsecutive(Value highKicker, Value secondKicker, Value thirdKicker, Value fourthKicker, Value fifthKicker) {
		final List<Value> values = Arrays.asList(highKicker, secondKicker, thirdKicker, fourthKicker, fifthKicker);
		if(!Value.areDistinctAndNotConsecutive(values)) {
			throw new IllegalStateException("all kickers must be different and not consecutive");
		}
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

	public Value getFourthKicker() {
		return fourthKicker;
	}

	public Value getFifthKicker() {
		return fifthKicker;
	}

	@Override
	public RankValue getRankValue() {
		return RankValue.FLUSH;
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
		public interface AddSecondKicker {
			AddThirdKicker withSecondKicker(Value value);
		}
		@FunctionalInterface
		public interface AddThirdKicker {
			AddFourthKicker withThirdKicker(Value value);
		}
		@FunctionalInterface
		public interface AddFourthKicker {
			AddFifthKicker withFourthKicker(Value value);
		}
		@FunctionalInterface
		public interface AddFifthKicker {
			Builder withFifthKicker(Value value);
		}
		@FunctionalInterface
		public interface Builder {
			Flush build();
		}
		private StraightFlushBuilder() {

		}
		public AddSecondKicker withHighKicker(Value value) {
			return second -> third -> fourth -> fifth -> () -> new Flush(value, second, third, fourth, fifth);
		}
	}

}
