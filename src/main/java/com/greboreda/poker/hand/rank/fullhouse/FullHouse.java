package com.greboreda.poker.hand.rank.fullhouse;

import com.greboreda.poker.card.Value;
import com.greboreda.poker.Comparision;
import com.greboreda.poker.hand.rank.Rank;
import org.apache.commons.lang3.Validate;

public class FullHouse implements Rank {

	private final Value value;
	private final Value over;

	private FullHouse(Value value, Value over) {
		Validate.notNull(value);
		Validate.notNull(over);
		if(value.equals(over)) {
			throw new IllegalStateException("value and over must be different");
		}
		this.value = value;
		this.over = over;
	}

	public Value getValue() {
		return value;
	}

	public Value getOver() {
		return over;
	}

	@Override
	public RankValue getRankValue() {
		return RankValue.FULL_HOUSE;
	}

	@Override
	public Comparision compare(Rank another) {
		return null;
	}

	public static FullHouseBuilder create() {
		return new FullHouseBuilder();
	}

	public static class FullHouseBuilder {

		@FunctionalInterface
		public interface AddOver {
			Builder over(Value value);
		}
		@FunctionalInterface
		public interface Builder {
			FullHouse build();
		}

		private FullHouseBuilder() {

		}

		public AddOver of(Value value) {
			return over -> () -> new FullHouse(value, over);
		}
	}

}
