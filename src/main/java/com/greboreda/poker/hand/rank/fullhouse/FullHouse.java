package com.greboreda.poker.hand.rank.fullhouse;

import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.rank.Rank;
import org.apache.commons.lang3.Validate;

public class FullHouse implements Rank {

	private final Value trips;
	private final Value over;

	private FullHouse(Value trips, Value over) {
		Validate.notNull(trips);
		Validate.notNull(over);
		if(trips.equals(over)) {
			throw new IllegalStateException("trips and over must be different");
		}
		this.trips = trips;
		this.over = over;
	}

	public Value getTrips() {
		return trips;
	}

	public Value getOver() {
		return over;
	}

	@Override
	public RankValue getRankValue() {
		return RankValue.FULL_HOUSE;
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
