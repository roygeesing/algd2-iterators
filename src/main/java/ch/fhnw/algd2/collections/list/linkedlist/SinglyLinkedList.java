package ch.fhnw.algd2.collections.list.linkedlist;

import java.util.*;

import ch.fhnw.algd2.collections.list.MyAbstractList;

public class SinglyLinkedList<E> extends MyAbstractList<E> {
	// variable int modCount already declared by AbstractList<E>
	private int size = 0;
	private Node<E> first;
	private int version = 0;

	@Override
	public boolean add(E e) {
		Node<E> last = first;
		Node<E> previous = null;
		while (last != null) {
			previous = last;
			last = last.next;
		}

		if (previous != null) {
			previous.next = new Node<>(e);
		} else {
			first = new Node<>(e);
		}

		size++;
		version++;
		return true;
	}

	@Override
	public boolean contains(Object o) {
		Node<E> current = first;
		while (current != null) {
			if (Objects.equals(current.elem, o)) {
				return true;
			}
			current = current.next;
		}
		return false;
	}

	@Override
	public boolean remove(Object o) {
		Node<E> current = first;
		Node<E> previous = null;

		while (current != null && !Objects.equals(current.elem, o)) {
			previous = current;
			current = current.next;
		}

		if (current == null) {
			return false;
		}

		if (previous != null) {
			previous.next = current.next;
		} else {
			first = current.next;
		}
		size--;
		version++;
		return true;
	}

	@Override
	public E get(int index) {
		if (index >= size || index < 0) {
			throw new IndexOutOfBoundsException();
		}

		Node<E> current = first;
		for (int i = 0; i < index; i++) {
			current = current.next;
		}

		return current.elem;
	}

	@Override
	public void add(int index, E element) {
		if (index > size || index < 0) {
			throw new IndexOutOfBoundsException();
		}

		if (index == 0) {
			first = new Node<>(element, first);
			size++;
			version++;
			return;
		}

		Node<E> current = first;
		Node<E> previous = null;
		for (int i = 0; i < index; i++) {
			previous = current;
			current = current.next;
		}

		if (previous != null) {
			previous.next = new Node<>(element, current);
		} else {
			first = new Node<>(element);
		}
		size++;
		version++;
	}

	@Override
	public E remove(int index) {
		if (index >= size || index < 0) {
			throw new IndexOutOfBoundsException();
		}

		Node<E> current = first;
		Node<E> previous = null;
		for (int i = 0; i < index; i++) {
			previous = current;
			current = current.next;
		}

		if (previous != null) {
			previous.next = current.next;
		} else {
			first = current.next;
		}
		size--;
		version++;
		return current.elem;
	}

	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];
		int index = 0;
		Node<E> current = first;
		while (current != null) {
			array[index++] = current.elem;
			current = current.next;
		}
		return array;
	}

	@Override
	public int size() {
		return size;
	}

	private static class Node<E> {
		private final E elem;
		private Node<E> next = null;

		private Node(E elem) {
			this.elem = elem;
		}

		private Node(E elem, Node<E> next) {
			this.elem = elem;
			this.next = next;
		}
	}

	@Override
	public Iterator<E> iterator() {
		return new MyIterator();
	}

	private class MyIterator implements Iterator<E> {
		private int itVersion = version;
		private Node<E> previous = null;
		private Node<E> current = null;
		private Node<E> next = first;

		@Override
		public boolean hasNext() {
			return next != null;
		}

		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			if (itVersion != version) {
				throw new ConcurrentModificationException();
			}

			if (current != null) {
				previous = current;
			}
			current = next;
			next = current.next;
			return current.elem;
		}

		@Override
		public void remove() {
			if (current == null) {
				throw new IllegalStateException();
			} else if (previous == null) {
				first = next;
			} else {
				previous.next = next;
			}

			current = null;
			size--;
			itVersion++;
			version++;
		}
	}

	public static void main(String[] args) {
		SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		System.out.println(Arrays.toString(list.toArray()));
	}
}
