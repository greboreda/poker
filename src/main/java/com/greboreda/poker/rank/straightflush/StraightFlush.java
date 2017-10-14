package com.greboreda.poker.rank.straightflush;

import com.greboreda.poker.card.Value;
import com.greboreda.poker.Comparision;
import com.greboreda.poker.rank.Rank;

public class StraightFlush implements Rank {

	private final Value highValue;
	private final Value secondValue;
	private final Value thirdValue;
	private final Value fourthValue;
	private final Value fifthValue;

	public StraightFlush(Value highValue, Value secondValue, Value thirdValue, Value fourthValue, Value fifthValue) {
		this.highValue = highValue;
		this.secondValue = secondValue;
		this.thirdValue = thirdValue;
		this.fourthValue = fourthValue;
		this.fifthValue = fifthValue;
	}

	public Value getHighValue() {
		return highValue;
	}

	public Value getSecondValue() {
		return secondValue;
	}

	public Value getThirdValue() {
		return thirdValue;
	}

	public Value getFourthValue() {
		return fourthValue;
	}

	public Value getFifthValue() {
		return fifthValue;
	}

	@Override
	public RankValue getRankValue() {
		return RankValue.STRAIGHT_FLUSH;
	}

	@Override
	public Comparision compare(Rank another) {
		return null;
	}

	public static StraightFlushBuilder create() {
		return new StraightFlushBuilder();
	}

	public static class StraightFlushBuilder {
		@FunctionalInterface
		public interface AddSecondValue {
			AddThirdValue withSecondValue(Value value);
		}
		@FunctionalInterface
		public interface AddThirdValue {
			AddFourthValue withThirdValue(Value value);
		}
		@FunctionalInterface
		public interface AddFourthValue {
			AddFifthValue withFourthValue(Value value);
		}
		@FunctionalInterface
		public interface AddFifthValue {
			Builder withFifthValue(Value value);
		}
		@FunctionalInterface
		public interface Builder {
			StraightFlush build();
		}
		public AddSecondValue withHighValue(Value value) {
			return second -> third -> fourth -> fifth -> () -> new StraightFlush(value, second, third, fourth, fifth);
		}
	}
}
