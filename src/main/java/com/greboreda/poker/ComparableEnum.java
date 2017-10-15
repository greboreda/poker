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

	default Boolean wins(T another) {
		return compare(another).isWin();
	}

	default Boolean looses(T another) {
		return compare(another).isLoose();
	}

	default Boolean ties(T another) {
		return compare(another).isTie();
	}

}
