import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Example {
    public static void main(String[] args) {
        Card card1 = new Card("123", LocalDate.now(), "123");
        Card card2 = new Card("234", LocalDate.now(), "234");
        Person person = new Person("Alex", 43, List.of(card1, card2));

        String personName = person.getName();
        int personAge = person.getAge();
        List<Card> cards = person.getCards();

        personName = "123";
        personAge = 12;
        cards.removeFirst();

        System.out.println(person);
    }
}

class Person {
    private String name;
    private int age;
    private List<Card> cards;

    public Person(String name, int age, List<Card> cards) {
        this.name = name;
        this.age = age;
        this.cards = new ArrayList<>(cards);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

//    addCard()
//    removeCard()

    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", cards=" + cards +
                '}';
    }
}

class Card {
    private String cardNumber;
    private LocalDate expiryDate;
    private String cvv;

    public Card(String cardNumber, LocalDate expiryDate, String cvv) {
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }
}
