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
        if (capacity < 0) throw new IllegalArgumentException("Capacity cannot be negative: " + capacity);
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
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E get(int index) {
        rangeCheck(index);
        return data[index];
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(o, data[i])) return i;
        }
        return -1;
    }

    @Override
    public E set(int index, E element) {
        rangeCheck(index);
        E old = data[index];
        data[index] = element;
        return old;
    }

    @Override
    public boolean add(E e) {
        modCount++;
        ensureCapacity(size + 1);
        data[size++] = e;
        return true;
    }

    @Override
    public void add(int index, E element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        modCount++;
        ensureCapacity(size + 1);
        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = element;
        size++;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        Object[] a = c.toArray();
        int numNew = a.length;
        if (numNew == 0) return false;

        modCount++;
        ensureCapacity(size + numNew);
        System.arraycopy(a, 0, data, size, numNew);
        size += numNew;
        return true;
    }

    @Override
    public E remove(int index) {
        rangeCheck(index);
        modCount++;
        E oldValue = data[index];
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(data, index + 1, data, index, numMoved);
        }
        data[--size] = null; // Clean for GC
        return oldValue;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index >= 0) {
            remove(index);
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        modCount++;
        Arrays.fill(data, 0, size, null);
        size = 0;
    }

    public void pushBack(E element) {
        add(element);
    }

    public void pushFront(E element) {
        add(0, element);
    }

    public E popFront() {
        return isEmpty() ? null : remove(0);
    }

    public E popBack() {
        return isEmpty() ? null : remove(size - 1);
    }

    public void reverse() {
        modCount++;
        for (int i = 0, j = size - 1; i < j; i++, j--) {
            E temp = data[i];
            data[i] = data[j];
            data[j] = temp;
        }
    }

    public void shuffle() {
        modCount++;
        Random rnd = new Random();
        for (int i = size - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            E temp = data[index];
            data[index] = data[i];
            data[i] = temp;
        }
    }

    public void trimToSize() {
        modCount++;
        if (size < data.length) {
            data = (size == 0) ? emptyArray() : Arrays.copyOf(data, size);
        }
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

    @SuppressWarnings("unchecked")
    private E[] emptyArray() {
        return (E[]) new Object[0];
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
