package com.greboreda.poker;

public enum Comparision {

	WIN,
	LOOSE,
	TIE;

	public Boolean isWin() {
		return this.equals(WIN);
	}

	public Boolean isLoose() {
		return this.equals(LOOSE);
	}

	public Boolean isTie() {
		return this.equals(TIE);
	}
}
