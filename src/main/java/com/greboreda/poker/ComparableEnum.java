package com.greboreda.poker;

import java.util.Objects;

public interface ComparableEnum<T extends ComparableEnum> {

	Integer getWeight();

	default Comparision compare(T another) {
		if(this.getWeight() > another.getWeight()) {
			return Comparision.WIN;
		} else if(Objects.equals(this.getWeight(), another.getWeight())) {
			return Comparision.TIE;
		} else {
			return Comparision.LOOSE;
		}

	}
}
