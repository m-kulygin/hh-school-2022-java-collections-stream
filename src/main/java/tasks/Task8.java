package tasks;

import common.Person;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
А теперь о горьком
Всем придется читать код
А некоторым придется читать код, написанный мною
Сочувствую им
Спасите будущих жертв, и исправьте здесь все, что вам не по душе!
P.S. функции тут разные и рабочие (наверное), но вот их понятность и эффективность страдает (аж пришлось писать комменты)
P.P.S Здесь ваши правки желательно прокомментировать (можно на гитхабе в пулл реквесте)
 */
public class Task8 {

  // ИЗМЕНЕНО: убрано поле класса count, которое использовалось лишь локально в методе countEven(...).

  //Не хотим выдывать апи нашу фальшивую персону, поэтому конвертим начиная со второй
  // ИЗМЕНЕНО: метод переписан на стримах с добавлением null-case проверок.
  public List<String> getNames(List<Person> persons) {
    return Optional.ofNullable(persons)
            .orElseGet(Collections::emptyList)
            .stream()
            .filter(Objects::nonNull)
            .map(Person::getFirstName)
            .collect(Collectors.toList());
  }

  //ну и различные имена тоже хочется
  // ИЗМЕНЕНО: убрано излишнее использование стримов на более простой и читаемый вариант.
  // Null-case проверки делать не нужно, т.к. они и так проводятся при вызове getNames(persons)
  public Set<String> getDifferentNames(List<Person> persons) {
    return new HashSet<>(getNames(persons));
  }

  //Для фронтов выдадим полное имя, а то сами не могут
  // ИЗМЕНЕНО: метод переписан под единый вызов функции format.
  // Добавлены null-case проверки.
  public String convertPersonToString(Person person) {
    return person == null ? "" :
            String.format("%s %s %s",
                    Optional.ofNullable(person.getSecondName()).orElse(""),
                    Optional.ofNullable(person.getFirstName()).orElse(""),
                    Optional.ofNullable(person.getMiddleName()).orElse(""));
  }

  // словарь id персоны -> ее имя
  // ИЗМЕНЕНО: метод переписан на стримах с добавлением null-case проверок.
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    return Optional.ofNullable(persons)
            .stream()
            .flatMap(Collection::stream)
            .filter(Objects::nonNull)
            .collect(Collectors.toMap(Person::getId, this::convertPersonToString));
  }

  // есть ли совпадающие в двух коллекциях персоны?
  // ИЗМЕНЕНО: двойной цикл с проверкой всех элементов попарно заменён на более читаемый метод disjoint.
  // Добавлены null-case проверки.
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    boolean result = false;
    try {
      result = !Collections.disjoint(persons1, persons2);
    } catch (Exception e) { System.err.println("EXCEPTION: " + e.getMessage()); }
    return result;
  }

  //...
  // ИЗМЕНЕНО: метод, подсчитывающий количество чётных значений в целочисленном стриме.
  // Использование локальной переменной count с forEach-проходом по стриму заменено на более читаемый встроенный метод count().
  public long countEven(Stream<Integer> numbers) // убрано ненужное внутреннее поле count; избыточная манипуляция с этим полем заменена на встроенный метод count
  {
    return numbers.filter(num -> num % 2 == 0).count();
  }
}
