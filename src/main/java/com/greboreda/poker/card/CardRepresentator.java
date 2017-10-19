package com.greboreda.poker.card;

import java.util.HashMap;
import java.util.Map;

import static com.greboreda.poker.card.Suit.CLUBS;
import static com.greboreda.poker.card.Suit.DIAMONDS;
import static com.greboreda.poker.card.Suit.HEARTS;
import static com.greboreda.poker.card.Suit.SPADES;
import static com.greboreda.poker.card.Value.ACE;
import static com.greboreda.poker.card.Value.EIGHT;
import static com.greboreda.poker.card.Value.FIVE;
import static com.greboreda.poker.card.Value.FOUR;
import static com.greboreda.poker.card.Value.JACK;
import static com.greboreda.poker.card.Value.KING;
import static com.greboreda.poker.card.Value.NINE;
import static com.greboreda.poker.card.Value.QUEEN;
import static com.greboreda.poker.card.Value.SEVEN;
import static com.greboreda.poker.card.Value.SIX;
import static com.greboreda.poker.card.Value.TEN;
import static com.greboreda.poker.card.Value.THREE;
import static com.greboreda.poker.card.Value.TWO;

public class CardRepresentator {

	private static final String more = "🂠🃟🂿🃏🂬🂼🃌🃜";
	private static final Map<Suit,Map<Value,String>> map = new HashMap<>();
	static {

		final Map<Value,String> spades = new HashMap<>();
		final Map<Value,String> clubs = new HashMap<>();
		final Map<Value,String> hearts = new HashMap<>();
		final Map<Value,String> diamonds = new HashMap<>();

		map.put(HEARTS, hearts);
		map.put(CLUBS, clubs);
		map.put(DIAMONDS, diamonds);
		map.put(SPADES, spades);

		spades.put(ACE,		"🂡");
		spades.put(TWO,		"🂢");
		spades.put(THREE,	"🂣");
		spades.put(FOUR,	"🂤");
		spades.put(FIVE,	"🂥");
		spades.put(SIX,		"🂦");
		spades.put(SEVEN,	"🂧");
		spades.put(EIGHT,	"🂨");
		spades.put(NINE,	"🂩");
		spades.put(TEN,		"🂪");
		spades.put(JACK,	"🂫");
		spades.put(QUEEN,	"🂭");
		spades.put(KING,	"🂮");

		hearts.put(ACE,		"🂱");
		hearts.put(TWO,		"🂲");
		hearts.put(THREE,	"🂳");
		hearts.put(FOUR,	"🂴");
		hearts.put(FIVE,	"🂵");
		hearts.put(SIX,		"🂶");
		hearts.put(SEVEN,	"🂷");
		hearts.put(EIGHT,	"🂸");
		hearts.put(NINE,	"🂹");
		hearts.put(TEN,		"🂺");
		hearts.put(JACK,	"🂻");
		hearts.put(QUEEN,	"🂽");
		hearts.put(KING,	"🂾");

		clubs.put(ACE,		"🃑");
		clubs.put(TWO,		"🃒");
		clubs.put(THREE,	"🃓");
		clubs.put(FOUR,		"🃔");
		clubs.put(FIVE,		"🃕");
		clubs.put(SIX,		"🃖");
		clubs.put(SEVEN,	"🃗");
		clubs.put(EIGHT,	"🃘");
		clubs.put(NINE,		"🃙");
		clubs.put(TEN,		"🃚");
		clubs.put(JACK,		"🃛");
		clubs.put(QUEEN,	"🃝");
		clubs.put(KING,		"🃞");

		diamonds.put(ACE,	"🃁");
		diamonds.put(TWO,	"🃂");
		diamonds.put(THREE,	"🃃");
		diamonds.put(FOUR,	"🃄");
		diamonds.put(FIVE,	"🃅");
		diamonds.put(SIX,	"🃆");
		diamonds.put(SEVEN,	"🃇");
		diamonds.put(EIGHT,	"🃈");
		diamonds.put(NINE,	"🃉");
		diamonds.put(TEN,	"🃊");
		diamonds.put(JACK,	"🃋");
		diamonds.put(QUEEN,	"🃍");
		diamonds.put(KING,	"🃎");
	}

	public static String getRepr(Value value, Suit suit) {
		return map.get(suit).get(value);
	}

}
