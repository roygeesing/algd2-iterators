package ch.fhnw.algd2.collections.list.linkedlist;

import ch.fhnw.algd2.collections.list.MyAbstractList;

/**
 * Additional Tests: - Order (list-semantic: Tail adding) by reference - add
 * element at specific position (indexoutofbounds check) - remove element from
 * specific position (indexoutofbounds check)
 * 
 * @author Michael
 */
public class SLL_D_Test_Complete extends Abstract_D_ListTest_Complete {
	@Override
	protected <T> MyAbstractList<T> getListInstance() {
		return new SinglyLinkedList<T>();
	}
}
