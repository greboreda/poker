package com.greboreda.poker.hand;

import com.greboreda.poker.Comparision;
import com.greboreda.poker.card.Card;
import com.greboreda.poker.card.Suit;
import com.greboreda.poker.card.Value;
import com.greboreda.poker.hand.rank.Rank;
import com.greboreda.poker.hand.rank.Rank.RankValue;
import com.greboreda.poker.hand.rank.RankFactory;
import org.apache.commons.lang3.Validate;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class Hand {

	public static final Integer NUMBER_OF_CARDS = 5;

	private final Set<Card> cards;
	private final Rank rank;

	public Hand(Card card1, Card card2, Card card3, Card card4, Card card5) {
		final Card[] params = {card1, card2, card3, card4, card5};
		Validate.noNullElements(params);
		cards = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(params)));
		if (!allCardsHaveDistinctValue()) {
			throw new IllegalStateException("cards must be different each other");
		}
		rank = RankFactory.retrieveRank(this);
	}

	public RankValue getRank() {
		return rank.getRankValue();
	}

	public Comparision compare(Hand another) {
		Validate.notNull(another);
		return this.rank.compare(another.rank);
	}

	public Set<Suit> getDistinctSuits() {
		return cards.stream()
				.map(Card::getSuit)
				.collect(toSet());
	}

	public Set<Value> getCardsValues() {
		return cards.stream()
				.map(Card::getValue)
				.collect(toSet());
	}

	public Set<Value> findValuesRepeated(Integer times) {
		return cards.stream()
				.collect(groupingBy(Card::getValue, counting()))
				.entrySet()
				.stream()
				.filter(e -> e.getValue().equals(new Long(times)))
				.map(Entry::getKey)
				.collect(toSet());
	}

	public Boolean allCardsHaveDistinctValue() {
		return cards.size() == NUMBER_OF_CARDS;
	}

	public Boolean cardsHaveConsecutiveValue() {
		return Value.areConsecutive(cards.stream()
				.map(Card::getValue)
				.collect(toList()));
	}
}
