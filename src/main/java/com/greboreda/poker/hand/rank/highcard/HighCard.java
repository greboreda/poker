package com.greboreda.poker.hand.rank.highcard;

import com.greboreda.poker.Comparision;
import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.rank.Rank;
import org.apache.commons.lang3.NotImplementedException;

import java.util.Arrays;
import java.util.List;

public class HighCard implements Rank {

	private final Value high;
	private final Value second;
	private final Value third;
	private final Value fourth;
	private final Value fifth;

	private HighCard(Value high, Value second, Value third, Value fourth, Value fifth) {
		checkAreDistinctAndNotConsecutive(high, second, third, fourth, fifth);
		checkOrder(high, second, third, fourth, fifth);
		this.high = high;
		this.second = second;
		this.third = third;
		this.fourth = fourth;
		this.fifth = fifth;
	}

	private void checkOrder(Value high, Value second, Value third, Value fourth, Value fifth) {
		if(!high.wins(second) || !second.wins(third) || !third.wins(fourth) || !fourth.wins(fifth)) {
			throw new IllegalStateException("values order is not valid");
		}
	}

	private void checkAreDistinctAndNotConsecutive(Value highKicker, Value secondKicker, Value thirdKicker, Value fourthKicker, Value fifthKicker) {
		final List<Value> values = Arrays.asList(highKicker, secondKicker, thirdKicker, fourthKicker, fifthKicker);
		if(!Value.areDistinctAndNotConsecutive(values)) {
			throw new IllegalStateException("all kickers must be different and not consecutive");
		}
	}

	public Value getHigh() {
		return high;
	}

	public Value getSecond() {
		return second;
	}

	public Value getThird() {
		return third;
	}

	public Value getFourth() {
		return fourth;
	}

	public Value getFifth() {
		return fifth;
	}

	@Override
	public RankValue getRankValue() {
		return RankValue.HIGH_CARD;
	}

	@Override
	public Comparision compare(Rank another) {
		throw new NotImplementedException("not implemented yet");
	}

	public static HighCardBuilder create() {
		return new HighCardBuilder();
	}

	public static class HighCardBuilder {
		@FunctionalInterface
		public interface AddSecond{
			AddThird withSecond(Value second);
		}
		@FunctionalInterface
		public interface AddThird{
			AddFourth withThird(Value third);
		}
		@FunctionalInterface
		public interface AddFourth{
			AddFifth withFourth(Value fourth);
		}
		@FunctionalInterface
		public interface AddFifth{
			Builder withFifth(Value fifth);
		}
		@FunctionalInterface
		public interface Builder {
			HighCard build();
		}
		private HighCardBuilder() {

		}
		public AddSecond of(Value of) {
			return second -> third -> fourth -> fifth -> () -> new HighCard(of, second, third, fourth, fifth);
		}
	}
}
