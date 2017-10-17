package com.greboreda.poker.hand.rank;

import com.greboreda.poker.ComparableEnum;
import com.greboreda.poker.Comparision;
import org.apache.commons.lang3.Validate;

public interface Rank {

	RankValue getRankValue();

	default Comparision compare(Rank another) {
		Validate.notNull(another);
		final Comparision rankComparision = this.getRankValue().compare(another.getRankValue());
		if(!rankComparision.isTie()) {
			return rankComparision;
		}
		return RankComparatorFactory.create(this.getClass(),this, another).compare();
	}

	enum RankValue implements ComparableEnum<RankValue> {
		ROYAL_FLUSH(10),
		STRAIGHT_FLUSH(9),
		FOUR_OF_A_KIND(8),
		FULL_HOUSE(7),
		FLUSH(6),
		STRAIGHT(5),
		THREE_OF_A_KIND(4),
		TWO_PAIR(3),
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

}
