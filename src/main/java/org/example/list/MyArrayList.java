package org.example.list;

import java.util.*;

public class MyArrayList<E> extends AbstractList<E> implements RandomAccess {
    private E[] data;
    private int size;

    public static <T> java.util.stream.Collector<T, ?, MyArrayList<T>> toMyArrayList() {
        return java.util.stream.Collector.of(
                MyArrayList::new,
                MyArrayList::add,
                (left, right) -> {
                    left.addAll(right);
                    return left;
                }
        );
    }

    @SuppressWarnings("unchecked")
    public MyArrayList(int capacity) {
        if (capacity < 0) throw new IllegalArgumentException("Capacity cannot be negative");
        this.data = (E[]) new Object[capacity];
        this.size = 0;
    }

    public MyArrayList() {
        this(10);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E get(int index) {
        rangeCheck(index);
        return data[index];
    }

    @Override
    public E set(int index, E element) {
        rangeCheck(index);
        E old = data[index];
        data[index] = element;
        return old;
    }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity > data.length) {
            int newCapacity = data.length + (data.length >> 1) + 1;
            if (newCapacity < minCapacity) newCapacity = minCapacity;
            data = Arrays.copyOf(data, newCapacity);
        }
    }

    private void rangeCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    public void pushBack(E element) {
        ensureCapacity(size + 1);
        data[size++] = element;
    }

    public void pushFront(E element) {
        insert(element, 0);
    }

    public void insert(E element, int index) {
        if (index < 0) index = 0;
        if (index > size) index = size;

        ensureCapacity(size + 1);
        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = element;
        size++;
    }

    public E popFront() {
        if (isEmpty()) return null;
        E element = data[0];
        removeAt(0);
        return element;
    }

    public E popBack() {
        if (isEmpty()) return null;
        E element = data[size - 1];
        removeAt(size - 1);
        return element;
    }

    public void removeAt(int index) {
        rangeCheck(index);
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(data, index + 1, data, index, numMoved);
        }
        data[--size] = null; // Помогаем GC
    }

    @Override
    public boolean add(E e) {
        pushBack(e);
        return true;
    }

    public void clear() {
        Arrays.fill(data, 0, size, null);
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @SuppressWarnings("unchecked")
    public void trimToSize() {
        if (size < data.length) {
            data = (size == 0) ? (E[]) new Object[0] : Arrays.copyOf(data, size);
        }
    }

    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(o, data[i])) return i;
        }
        return -1;
    }

    public void reverse() {
        for (int i = 0, j = size - 1; i < j; i++, j--) {
            E temp = data[i];
            data[i] = data[j];
            data[j] = temp;
        }
    }

    public void shuffle() {
        Random rnd = new Random();
        for (int i = size - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            E a = data[index];
            data[index] = data[i];
            data[i] = a;
        }
    }

    @Override
    public String toString() {
        if (size == 0) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(data[i]);
            if (i < size - 1) sb.append(", ");
        }
        return sb.append("]").toString();
    }
}
