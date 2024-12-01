package list;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MyArrayListTest {

    @Test
    void defaultConstructor() {
        MyArrayList<Integer> list = new MyArrayList<>();

        assertAll("Проверка конструктора по умолчанию", () -> assertNotNull(list), () -> assertEquals(0, list.size()));
    }

    @Test
    void constructorWithSetSize() {
        MyArrayList<Integer> list = new MyArrayList<>(15);

        assertAll("Проверка конструктора с заданием размера", () -> assertNotNull(list), () -> assertTrue(list.isEmpty()), () -> assertThrows(IllegalArgumentException.class, () -> new MyArrayList<>(-1)));
    }

    @Test
    void constructorWithCollection() {
        List<String> strings = new ArrayList<>(Arrays.asList("string1", "string2", "string3"));
        MyArrayList<String> list = new MyArrayList<>(strings);

        assertAll("Проверка конструктора с заданием коллекции", () -> assertNotNull(list), () -> assertEquals(3, list.size()), () -> assertEquals(strings.get(0), list.get(0)), () -> assertEquals(strings.get(1), list.get(1)), () -> assertEquals(strings.get(2), list.get(2)));
    }

    @Test
    void constructorWithEmptyCollection() {
        List<String> strings = new ArrayList<>();
        MyArrayList<String> list = new MyArrayList<>(strings);

        assertAll("Проверка конструктора с заданием пустой коллекции", () -> assertNotNull(list), () -> assertTrue(list.isEmpty()));
    }

    @Test
    void constructorWithArray() {
        Integer[] array = {1, 2, 3};
        MyArrayList<Integer> list = new MyArrayList<>(array);

        assertAll("Проверка конструктора с заданием массива", () -> assertNotNull(array), () -> assertEquals(3, list.size()), () -> assertEquals(1, list.get(0)), () -> assertEquals(2, list.get(1)), () -> assertEquals(3, list.get(2)));
    }

    @Test
    public void ConstructorWithEmptyArray() {
        Integer[] array = {};
        MyArrayList<Integer> list = new MyArrayList<>(array);

        assertAll("Проверка конструктора с заданием пустого массива", () -> assertTrue(list.isEmpty()), () -> assertThrows(IndexOutOfBoundsException.class, () -> list.get(0)));
    }


    @Test
    void addElement() {
        MyArrayList<Integer> list = new MyArrayList<>(10);
        list.add(123);
        MyArrayList<Integer> emptyList = new MyArrayList<>(0);
        emptyList.add(1);

        assertAll("Проверка добавления элемента в коне списка", () -> assertEquals(1, list.size()), () -> assertEquals(123, list.get(0)), () -> assertEquals(1, emptyList.get(0)));
    }

    @Test
    void addElementAtIndex() {
        MyArrayList<Integer> list = new MyArrayList<>(Arrays.asList(1, 2, 3, 4));
        list.add(100, 2);

        assertAll("Проверка добавления элемента по индексу", () -> assertEquals(5, list.size()), () -> assertEquals(100, list.get(2)), () -> assertThrows(IndexOutOfBoundsException.class, () -> list.add(6, 6)), () -> assertThrows(IndexOutOfBoundsException.class, () -> list.add(6, -1)));
    }

    @Test
    void removeElementAtIndex() {
        MyArrayList<Integer> list = new MyArrayList<>(Arrays.asList(1, 2, 3, 4));
        list.remove(1);

        assertAll("Проверка добавления элемента по индексу", () -> assertEquals(3, list.size()), () -> assertEquals(1, list.get(0)), () -> assertEquals(3, list.get(1)), () -> assertEquals(4, list.get(2)), () -> assertThrows(IndexOutOfBoundsException.class, () -> list.remove(4)), () -> assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1)));
    }

    @Test
    void cleanList() {
        MyArrayList<Integer> list = new MyArrayList<>(Arrays.asList(1, 2, 3, 4));
        list.clean();

        assertTrue(list.isEmpty());
    }

    @Test
    void getElement() {
        MyArrayList<Integer> list = new MyArrayList<>(Arrays.asList(1, 2, 3));
        MyArrayList<Integer> emptyList = new MyArrayList<>();

        assertAll(() -> assertEquals(1, list.get(0)), () -> assertEquals(2, list.get(1)), () -> assertEquals(3, list.get(2)), () -> assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1)), () -> assertThrows(IndexOutOfBoundsException.class, () -> list.get(3)), () -> assertThrows(IndexOutOfBoundsException.class, () -> emptyList.get(0)));
    }

    @Test
    void setElement() {
        MyArrayList<Integer> list = new MyArrayList<>(Arrays.asList(1, 2, 3));
        list.set(100, 0);

        assertAll(() -> assertEquals(100, list.get(0)), () -> assertEquals(3, list.size()), () -> assertThrows(IndexOutOfBoundsException.class, () -> list.set(1, 5)), () -> assertThrows(IndexOutOfBoundsException.class, () -> list.set(1, -1)));
    }

    @Test
    void toArrayWithElements() {
        MyArrayList<Integer> list = new MyArrayList<>(Arrays.asList(1, 2, 3));
        Integer[] expectedArray = {1, 2, 3};

        assertArrayEquals(expectedArray, list.toArray(new Integer[0]));
    }

    @Test
    void quickSortWithoutComparator() {
        MyArrayList<Integer> list = new MyArrayList<>(Arrays.asList(1, 100, 50, 3, 8));
        list.quickSort();
        Integer[] expextedArray = {1, 3, 8, 50, 100};

        assertArrayEquals(expextedArray, list.toArray(new Integer[0]));
    }

    @Test
    void quickSortWithComparator() {
        MyArrayList<Integer> list = new MyArrayList<>(Arrays.asList(1, 100, 50, 3, 8));
        Comparator<Integer> comparator = (a, b) -> Integer.compare(b, a);
        list.quickSort(comparator);
        Integer[] expextedArray = {100, 50, 8, 3, 1};

        assertArrayEquals(expextedArray, list.toArray(new Integer[0]));
    }

    @Test
    void quickSortWithoutComparatorNonComparable() {
        MyArrayList<Object> list = new MyArrayList<>();
        list.add(new Object());

        assertThrows(IllegalArgumentException.class, () -> list.quickSort());
    }

    @Test
    void workWithBigSize() {
        MyArrayList<Integer> myList = new MyArrayList<>(1000);
        List<Integer> list = new ArrayList<>(1000);

        Random random = new Random();

        for (int i = 0; i < 1000; i++) {
            int a = random.nextInt(200);
            list.add(a);
            myList.add(a);
        }

        assertArrayEquals(list.toArray(new Integer[0]), myList.toArray(new Integer[0]));

        list.sort(null);
        myList.quickSort();

        assertArrayEquals(list.toArray(new Integer[0]), myList.toArray(new Integer[0]));

        for (int j = 0; j < 50; j++) {
            int indexDelete = random.nextInt(900);
            list.remove(indexDelete);
            myList.remove(indexDelete);
        }

        assertArrayEquals(list.toArray(new Integer[0]), myList.toArray(new Integer[0]));
        assertEquals(list.get(161), myList.get(161));

        list.set(99, 1111);
        myList.set(1111, 99);

        assertArrayEquals(list.toArray(new Integer[0]), myList.toArray(new Integer[0]));

        myList.clean();
        list.clear();
        System.out.println(list.size());

        assertEquals(list.size(), myList.size());
    }
}