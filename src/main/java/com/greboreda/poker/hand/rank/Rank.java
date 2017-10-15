package com.greboreda.poker.hand.rank;

import com.greboreda.poker.ComparableEnum;
import com.greboreda.poker.hand.Hand;
import com.greboreda.poker.Comparision;

import java.util.Objects;
import java.util.Optional;

public interface Rank {

	RankValue getRankValue();

	Comparision compare(Rank another);

	enum RankValue implements ComparableEnum<RankValue> {
		ROYAL_FLUSH(10),
		STRAIGHT_FLUSH(9),
		FOUR_OF_A_KIND(8),
		FULL_HOUSE(7),
		FLUSH(6),
		STRAIGHT(5),
		THREE_OF_A_KIND(4),
		TWO_PAIRS(3),
		ONE_PAIR(2),
		HIGH_CARD(1);

		private Integer weight;

		RankValue(Integer weight) {
			this.weight = weight;
		}

		public Integer getWeight() {
			return weight;
		}
	}

	interface RankCalculator<T extends Rank> {
		Optional<T> calculateRank(Hand hand);
	}

}
