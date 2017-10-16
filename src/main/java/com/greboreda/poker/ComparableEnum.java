package com.greboreda.poker;

import java.util.Objects;

public interface ComparableEnum<T> {

	Integer getWeight();

	default Comparision compare(ComparableEnum<T> another) {
		if(this.getWeight() > another.getWeight()) {
			return Comparision.WIN;
		} else if(Objects.equals(this.getWeight(), another.getWeight())) {
			return Comparision.TIE;
		} else {
			return Comparision.LOOSE;
		}
	}

	default Boolean wins(ComparableEnum<T> another) {
		return compare(another).isWin();
	}

	default Boolean looses(ComparableEnum<T> another) {
		return compare(another).isLoose();
	}

	default Boolean ties(ComparableEnum<T> another) {
		return compare(another).isTie();
	}

}
