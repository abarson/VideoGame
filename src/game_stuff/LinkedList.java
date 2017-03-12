package game_stuff;

public class LinkedList<E> {
	private Node current;
	private Node next;
	private int index;
	private int size;
	private Node[]list=new Node[10];
	public LinkedList(){
		current = null;
		next = null;
		index = 0;
		size = 0;
	}
	public void add(Node item){
		if (size == 0){
			list[index]=item;
			current = item;
			size++;
		}
		else{
			list[index]=item;
			current.next = item;
			current = item;
			size++;
		}
	}
	public boolean hasNext(){
		return current.next != null;
	}
	public void next(){
		current = current.next;
	}
	private static class Node<E> {
		E element;
		Node<E> next;

		public Node(E element) {
			this.element = element;
		}
	}
}
