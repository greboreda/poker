package com.greboreda.poker.rank.fullhouse;

import com.greboreda.poker.Card.Value;
import com.greboreda.poker.MatchResult;
import com.greboreda.poker.rank.Rank;
import org.apache.commons.lang3.Validate;

public class FullHouse implements Rank {

	public final Value value;
	public final Value over;

	private FullHouse(Value value, Value over) {
		Validate.notNull(value);
		Validate.notNull(over);
		if(value.equals(over)) {
			throw new IllegalStateException("value and over must be different");
		}
		this.value = value;
		this.over = over;
	}

	@Override
	public RankValue getRankValue() {
		return RankValue.FULL_HOUSE;
	}

	@Override
	public MatchResult compare(Rank another) {
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
