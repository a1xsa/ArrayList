package list;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

/**
 * Класс MyArrayList представляет собой собственную реализацию списка с динамическим изменением размера.
 * Он поддерживает базовые операции коллекции, такие как добавление, удаление, получение элементов,
 * а также сортировку с использованием алгоритма быстрой сортировки.
 *
 * @param <T> Тип элементов в списке.
 * @author Alexey Tsvetkov
 */
public class MyArrayList<T> {
    private int size;
    private static final int default_size = 10;
    private Object[] elements;

    /**
     * Конструктор для создания списка с заданным размером.
     *
     * @param initSize Начальный размер списка.
     * @throws IllegalArgumentException если размер, введенный пользователем, меньше 0.
     */
    public MyArrayList(int initSize) {
        if (initSize >= 0) {
            elements = new Object[initSize];
        } else {
            throw new IllegalArgumentException("Размер списка не может быть отрицательным!");
        }
    }

    /**
     * Конструктор для создания списка с размером по умолчанию.
     */
    public MyArrayList() {
        elements = new Object[default_size];
    }

    /**
     * Конструктор для создания списка на основе переданной коллекции.
     *
     * @param col Коллекция для копирования.
     */
    public MyArrayList(Collection<? extends T> col) {
        if (!col.isEmpty()) {
            Object[] mas = col.toArray();
            elements = Arrays.copyOf(mas, mas.length, Object[].class);
            size = col.size();
        } else {
            elements = new Object[0];
        }
    }

    /**
     * Конструктор для создания списка на основе массива.
     *
     * @param array Массив элементов.
     */
    public MyArrayList(T[] array) {
        if (array.length != 0) {
            elements = Arrays.copyOf(array, array.length, Object[].class);
            size = array.length;
        } else {
            elements = new Object[0];
        }
    }

    /**
     * Возвращает количество элементов в листе.
     *
     * @return Количество элементов в коллекции.
     */
    public int size() {
        return size;
    }

    /**
     * Проверка корректности индекса.
     *
     * @param index Индекс для проверки.
     * @return {@code true}, если индекс корректен.
     * @throws IndexOutOfBoundsException если индекс выходит за пределы массива.
     */
    private boolean checkIndex(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Индекс " + index + " выходит за пределы. Размер равен " + size);
        }
        return true;
    }

    /**
     * Проверка корректности индекса для добавления элемента.
     *
     * @param index Индекс для проверки.
     * @return {@code  true}, если индекс корректен.
     * @throws IndexOutOfBoundsException если индекс выходит за пределы.
     */
    private boolean checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Индекс " + index + " выходит за пределы. Размер равен " + size);
        }
        return true;
    }

    /**
     * Изменение размера массива при его изменении(добавлении элемента).
     */
    private void reform() {
        if (size == elements.length) {
            if (elements.length != 0) {
                elements = Arrays.copyOf(elements, (int) (elements.length * 1.5));
            } else {
                elements = new Object[default_size];
            }
        }
    }

    /**
     * Добавление элемента в конец списка.
     *
     * @param element Элемент для добавления.
     */
    public void add(T element) {
        reform();
        elements[size++] = element;
    }

    /**
     * Добавление элемента в список по индексу.
     *
     * @param element Элемент для добавления.
     * @param index   Индекс для добавления элемента.
     * @throws IndexOutOfBoundsException если индекс выходит за пределы.
     */
    public void add(T element, int index) {
        checkIndexForAdd(index);
        reform();
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }

    /**
     * Получение элемента по индексу.
     *
     * @param index Индекс элемента.
     * @return Элемент по указанному индексу.
     * @throws IndexOutOfBoundsException если индекс выходит за пределы.
     */
    public T get(int index) {
        checkIndex(index);
        return (T) elements[index];
    }

    /**
     * Удаление элемента по индексу.
     *
     * @param index Индекс элемента для удаления.
     * @throws IndexOutOfBoundsException если индекс выходит за пределы.
     */
    public void remove(int index) {
        checkIndex(index);
        System.arraycopy(elements, index + 1, elements, index, size - index - 1);
        size--;
    }

    /**
     * Очистка коллекции.
     */
    public void clean() {
        Arrays.fill(elements, null);
        size = 0;
    }

    /**
     * Замена элемента в списке по индексу.
     *
     * @param element Новый элемент.
     * @param index   Индекс для замены.
     * @throws IndexOutOfBoundsException если индекс выходит за пределы.
     */
    public void set(T element, int index) {
        checkIndex(index);
        elements[index] = element;

    }

    /**
     * Разбиение массива для алгоритма быстрой сортировки.
     *
     * @param low        Начальный индекс.
     * @param high       Конечный индекс.
     * @param arraySort  Массив для сортировки.
     * @param comparator Компаратор для сравнения элементов.
     * @return Индекс опорного элемента.
     */
    private int partition(int low, int high, T[] arraySort, Comparator<? super T> comparator) {
        int middle = low + (high - low) / 2;
        T pivot = arraySort[middle];

        T temp = arraySort[middle];
        arraySort[middle] = arraySort[high];
        arraySort[high] = temp;

        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (comparator.compare(arraySort[j], pivot) < 0) {
                i++;
                temp = arraySort[i];
                arraySort[i] = arraySort[j];
                arraySort[j] = temp;
            }
        }
        temp = arraySort[i + 1];
        arraySort[i + 1] = arraySort[high];
        arraySort[high] = temp;
        return i + 1;
    }

    /**
     * Рекурсивный алгоритм быстрой сортировки с использованием компаратора.
     *
     * @param low        Начальный индекс.
     * @param high       Конечный индекс.
     * @param arraySort  Массив для сортировки.
     * @param comparator Компаратор для сравнения элементов.
     */
    private void quickSortSystem(int low, int high, T[] arraySort, Comparator<? super T> comparator) {
        if (low < high) {
            int pivotIndex = partition(low, high, arraySort, comparator);
            quickSortSystem(low, pivotIndex - 1, arraySort, comparator);
            quickSortSystem(pivotIndex + 1, high, arraySort, comparator);
        }
    }

    /**
     * Сортировка элементов списка с использованием естественного порядка.
     * Если элементы не реализуют Comparable, будет выброшено исключение.
     *
     * @throws IllegalArgumentException если элементы не реализуют Comparable.
     */
    public void quickSort() {
        for (Object obj : elements) {
            if (!(obj instanceof Comparable)) {
                throw new IllegalArgumentException("Элемент не реализует Comparable: " + obj);
            }
        }
        if (size > 1) {
            T[] arraySort = (T[]) elements;
            quickSortSystem(0, size - 1, arraySort, (Comparator<? super T>) Comparator.naturalOrder());
            elements = arraySort;
        }
    }

    /**
     * Сортировка элементов списка с использованием переданного компаратора.
     *
     * @param comparator Компаратор для сравнения элементов.
     */
    public void quickSort(Comparator<? super T> comparator) {
        if (size > 1) {
            T[] arraySort = (T[]) elements;
            quickSortSystem(0, size - 1, arraySort, comparator);
            elements = arraySort;
        }
    }

    /**
     * Возвращает массив, содержащий все элементы коллекци.
     *
     * @return {@code T[]}, содержащий элементы листа.
     */
    public T[] toArray(T[] a) {
        return (T[]) Arrays.copyOf(elements, size, a.getClass());
    }

    /**
     * Проверка пустоты коллекии.
     *
     * @return {@code true} если коллекция не содержит элементов.
     */
    public boolean isEmpty() {
        return size == 0;
    }
}
